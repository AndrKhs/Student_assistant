package botAbility.FunctionsBot.BotAPI;

import botAbility.Console.*;
import botAbility.Console.FileRequest;
import botAbility.FunctionsBot.ProviderBot.BotProvider;
import botAbility.FunctionsBot.ProviderBot.DateBaseBot;
import botAbility.FunctionsBot.ProviderBot.TelegramProvider;
import commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import botAbility.Console.RequestConsole;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Класс для работы с вводом пользователя, так же добавляет возможность использовать кнопики вместо написания комманд
 */
public class BotAPI implements BotCommunication {

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(BotAPI.class);

    /**
     * Набор констант для обращения к методам из других клссов
     */
    private TelegramProvider send = new TelegramProvider();
    private botConsole request = new RequestConsole();
    private BotProvider botProvider = new DateBaseBot();

    /**
     * Набор сообщений об ошибках
     */
    final String errorInput = "Неккоректный ввод: ";
    final String errorUnknowCommand = "Неизвестная команда \uD83D\uDC68\u200D\uD83D\uDCBB\uD83E\uDDD1\u200D\uD83D\uDCBB";

    /**
     * Конструктор HashMap команд
     */
    private static final HashMap<String, Command> map = new HashMap<>();
    public BotAPI () {
        setCommands();
    }
    public void setCommands() {
        map.put("/start", new Start());
        map.put("помощь", new Help());
        map.put("добавить", new Add());
        map.put("удалить дедлайн", new Delete());
        map.put("случайная музыка", new RandomMusic());
        map.put("дедлайн", new Deadline());
        map.put("назад", new Back());
        map.put("удалить группу с дедлайнами", new DeleteGroup());
    }

    /**
     * Метод для обращения к методам работы бота
     * @param message       сообщение пользователя обратившийся к боту
     * @return              null
     */
    public String consoleRecordingDeadline(Message message) {
        StringBuilder sb = new StringBuilder();
        String idUser = message.getFrom().getId().toString();
        String words = message.getText().toLowerCase();
        if (map.keySet().contains(words))
            deleteAllRequest(message);
        try {
            if (request.searchRequest(idUser, Commands.DateDelete).equals(idUser + Commands.DateDelete)) {
                FileRequest file = new FileRequest();

                file.setDateDelete(request.readRequest(idUser, Commands.DateDelete));
                file.setDisciplineDelete(message.getText());
                file.setGroupDelete(request.readRequest(idUser, Commands.GroupDelete));

                send.sendMsg(message, botProvider.deleteDeadline(file));

                deleteAllRequest(message);
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateDeadline).equals(idUser + Commands.DateDeadline)) {
                FileRequest file = new FileRequest();

                file.setDateDeadline(request.readRequest(idUser, Commands.DateDeadline));
                file.setDisciplineDeadline(message.getText());
                file.setGroupDeadline(request.readRequest(idUser, Commands.GroupDeadline));

                send.sendMsg(message, botProvider.readDeadline(file));

                deleteAllRequest(message);
                return null;

            }
            if (request.searchRequest(idUser, Commands.DisciplineAdd).equals(idUser + Commands.DisciplineAdd)) {
                FileRequest file = new FileRequest();

                file.setDataAdd(request.readRequest(idUser, Commands.DateAdd));
                file.setDisciplineAdd(request.readRequest(idUser, Commands.DisciplineAdd));
                file.setGroupAdd(request.readRequest(idUser, Commands.GroupAdd));

                sb.append("Дедлайн создан ").append(botProvider.writeFile(file, message.getText()));
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                deleteAllRequest(message);
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateAdd).equals(idUser + Commands.DateAdd)) {
                request.writeRequest(idUser, Commands.DisciplineAdd.toString(), message.getText());
                sb.append("Напишите мне что вам задали по ").append(request.readRequest(idUser, Commands.DisciplineAdd));
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateDelete).equals(idUser + Commands.DateDelete)) {
                request.writeRequest(idUser, Commands.DisciplineDelete, message.getText());
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateDeadline).equals(idUser + Commands.DateDeadline)) {
                request.writeRequest(idUser, Commands.DisciplineDeadline, message.getText());
                return null;
            }

        } catch (IOException e) {
            log.error(e.toString());
        }
        analyzeDate(message);
        return null;
    }

    /**
     * Метод для временного хранение введенной даты для создание дедлайна
     *
     * @param message       Сообщение пользователся обратившийся к боту
     * @return              null
     */
    private String analyzeDate(Message message) {
        String idUser = message.getFrom().getId().toString();
        if (request.searchRequest(idUser, Commands.GroupAdd).equals(idUser + Commands.GroupAdd)) {
            String mes = message.getText();
            if (checkDate(mes, message).equals("false")) return null;
            writeRequestDeadline(Commands.DateAdd, Commands.GroupAdd, Commands.Add, message);
            request.deleteRequest(idUser, Commands.Add.toString());
            return null;
        }
        if (request.searchRequest(idUser, Commands.GroupDelete).equals(idUser + Commands.GroupDelete)) {
            String mes = message.getText();
            if (checkDate(mes, message).equals("false")) return null;
            writeRequestDeadline(Commands.DateDelete, Commands.GroupDelete, Commands.Delete, message);
            request.deleteRequest(idUser, Commands.Delete.toString());
            return null;
        }
        if (request.searchRequest(idUser, Commands.GroupDeadline).equals(idUser + Commands.GroupDeadline)) {
            String mes = message.getText();
            if (checkDate(mes, message).equals("false")) return null;
            writeRequestDeadline(Commands.DateDeadline, Commands.GroupDeadline, Commands.Deadline, message);
            request.deleteRequest(idUser, Commands.Deadline.toString());
            return null;
        }
        analyzeGroup(message);
        return null;
    }

    /**
     * Метод
     * @param message   Сообщение пользователся обратившийся к боту
     * @return          null
     */
    private String analyzeGroup(Message message) {
        String idUser = message.getFrom().getId().toString();
        if (request.searchRequest(idUser, Commands.Deadline).equals(idUser + Commands.Deadline)) {
            writeRequestGroup(Commands.GroupDeadline, message);
            return null;
        }
        if (request.searchRequest(idUser, Commands.Delete).equals(idUser + Commands.Delete)) {
            writeRequestGroup(Commands.GroupDelete, message);
            return null;
        }
        if (request.searchRequest(idUser, Commands.Add).equals(idUser + Commands.Add)) {
            writeRequestGroup(Commands.GroupAdd, message);
            return null;
        }
        if(request.searchRequest(idUser, Commands.DeleteGroup).equals(idUser + Commands.DeleteGroup)) {
            gropeDelete(message);
            return null;
        }
        checkCommand(message);
        return null;
    }

    /**
     * Метод для проверки команды в HashMap
     * @param message       Сообщение пользователся обратившийся к боту
     */
    private void checkCommand(Message message) {
        Command command = map.get(message.getText().toLowerCase());
        if (command == null) send.sendMsg(message, errorUnknowCommand);
        else command.execute(message);
    }

    private String gropeDelete(Message message){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        String words = message.getText();
        String[] number = words.split("-");
        if(number.length == 2){
            String lenOne = number[0];
            String lenTwo = number[1];
            if (number.length == 2 && lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                Path path = Paths.get(sb.toString());
                if (Files.exists(path)) {
                    File groups = new File(sb.toString());
                    for (String s : groups.list()) {
                        String[] group = s.split("_");
                        if (group[0].equals(message.getText())) {
                            File file = new File(sb.append(s).toString());
                            if (file.delete()) {
                                send.sendMsg(message,"Удалил");
                                return null;
                            }
                        }
                    }
                }
            }
            else send.sendMsg(message, errorInput + "некоректный ввод группы");
        }
        else send.sendMsg(message, errorInput + "некоректный ввод группы");
        return null;
    }


    private void deleteAllRequest(Message message) {
        String idUser = message.getFrom().getId().toString();
        for (int i = 0; i < Commands.values().length; i++)
            request.deleteRequest(idUser, Commands.valueOf(i));
    }

    /**
     * Метод для сохранения учебной группы введенным пользователем
     * @param command       Команда запроса
     * @param message       Сообщение пользователся обратившийся к боту
     */
    private String writeRequestGroup(Object command, Message message) {
        String words = message.getText();
        String idUser = message.getFrom().getId().toString();
        String[] number = words.split("-");
        if(number.length == 2){
            String lenOne = number[0];
            String lenTwo = number[1];
            try {
                if (number.length == 2 && lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    request.writeRequest(idUser, command, words);
                    send.sendMsg(message, botProvider.searchDate(words));
                    send.sendMsg(message, "Напишите мне ДД.ММ.ГГГГ дедлайна");
                }
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
        else {
            send.sendMsg(message, errorInput + "Группа");
            deleteAllRequest(message);
        }
        return null;
    }

    /**
     * Метод для сохранения дедлайна введенным пользователем
     * @param date              Дедлайн
     * @param group             Учебная группа
     * @param requestUser       Запрос пользователя
     * @param message           Сообщение пользователся обратившийся к боту
     */
    private void writeRequestDeadline(Object date, Object group, Object requestUser, Message message) {
        String mes = message.getText();
        String idUser = message.getFrom().getId().toString();
        try {
            try {
                request.writeRequest(idUser, date, mes);
            } catch (IOException e) {
                log.error(e.toString());
            }
            send.sendMsg(message, botProvider.searchDiscipline(request.readRequest(idUser, group), mes));
            send.sendMsg(message, "Напишите мне название учебной дисциплины");
            request.deleteRequest(idUser, requestUser);
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    /**
     * Метод для проверки правильности ввода дедлайна
     *
     * @param mes           Текст сообщения пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     * @return              null
     */
    private String checkDate(String mes, Message message) {
        StringBuilder sb = new StringBuilder();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        final String[] word = mes.split("\\.");
        if (word.length != 3) {
            sb.append(send.errorInput).append("дедлайн");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        final int IntWords1 = Integer.parseInt(word[0]);
        if ((IntWords1 > 31) || (IntWords1 < 1)) {
            sb.append(send.errorInput).append("день");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        final int IntWords2 = Integer.parseInt(word[1]);
        if ((IntWords2 > 12) || (IntWords2 < 1)) {
            sb.append(send.errorInput).append("месяц");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        final int IntWords3 = Integer.parseInt(word[2]);
        if (IntWords3 < Integer.parseInt(year)) {
            sb.append(send.errorInput).append("год");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        for (String wor : word)
            if (wor.length() > 5) {
                sb.append(send.errorInput).append("дедлайн");
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return "false";
            }
        return "True";
    }
}