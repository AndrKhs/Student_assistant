package botAbility.FunctionsBot.BotAPI;

import botAbility.Consol.*;
import botAbility.Consol.FileRequest;
import botAbility.FunctionsBot.ProviderBot.BotProvider;
import botAbility.FunctionsBot.ProviderBot.DateBaseBot;
import botAbility.FunctionsBot.ProviderBot.TelegramProvider;
import commands.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import botAbility.Consol.RequestConsol;

import java.io.IOException;
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
    private static final Logger log = LogManager.getLogger(BotAPI.class);

    /**
     * Набор констант для обращения к методам из других клссов
     */
    private TelegramProvider send = new TelegramProvider();
    private botConsol request = new RequestConsol();
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
        map.put("удалить", new Delete());
        map.put("случайная музыка", new RandomMusic());
        map.put("дедлайн", new Deadline());
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
            deleteAllRequest(idUser);
        try {
            if (request.searchRequest(idUser, Commands.DateDelete.toString()).equals(idUser + Commands.DateDelete.toString())) {
                FileRequest file = new FileRequest();

                file.setDateDelete(request.readRequest(idUser, Commands.DateDelete.toString()));
                file.setDisciplineDelete(message.getText());
                file.setGroupDelete(request.readRequest(idUser, Commands.GroupDelete.toString()));

                send.sendMsg(message, botProvider.deleteDeadline(file));

                deleteAllRequest(idUser);
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateDeadline.toString()).equals(idUser + Commands.DateDeadline.toString())) {
                FileRequest file = new FileRequest();

                file.setDateDeadline(request.readRequest(idUser, Commands.DateDeadline.toString()));
                file.setDisciplineDeadline(message.getText());
                file.setGroupDeadline(request.readRequest(idUser, Commands.GroupDeadline.toString()));

                send.sendMsg(message, botProvider.readDeadline(file));

                deleteAllRequest(idUser);
                return null;

            }
            if (request.searchRequest(idUser, Commands.DisciplineAdd.toString()).equals(idUser + Commands.DisciplineAdd.toString())) {
                FileRequest file = new FileRequest();

                file.setDataAdd(request.readRequest(idUser, Commands.DateAdd.toString()));
                file.setDisciplineAdd(request.readRequest(idUser, Commands.DisciplineAdd.toString()));
                file.setGroupAdd(request.readRequest(idUser, Commands.GroupAdd.toString()));

                sb.append("Дедлайн создан ").append(botProvider.writeFile(file, message.getText()));
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                deleteAllRequest(idUser);
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateAdd.toString()).equals(idUser + Commands.DateAdd.toString())) {
                request.writeRequest(idUser, Commands.DisciplineAdd.toString(), message.getText());
                sb.append("Напишите мне что вам задали по ").append(request.readRequest(idUser, Commands.DisciplineAdd.toString()));
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateDelete.toString()).equals(idUser + Commands.DateDelete.toString())) {
                request.writeRequest(idUser, Commands.DisciplineDelete.toString(), message.getText());
                return null;
            }
            if (request.searchRequest(idUser, Commands.DateDeadline.toString()).equals(idUser + Commands.DateDeadline.toString())) {
                request.writeRequest(idUser, Commands.DisciplineDeadline.toString(), message.getText());
                return null;
            }

        } catch (IOException e) {
            log.error(e);
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
        if (request.searchRequest(idUser, Commands.GroupAdd.toString()).equals(idUser + Commands.GroupAdd.toString())) {
            String mes = message.getText();
            if (checkDate(mes, message).equals(null)) return null;
            writeRequestDeadline(Commands.DateAdd.toString(), Commands.GroupAdd.toString(), Commands.Add.toString(), message);
            request.deleteRequest(idUser, Commands.Add.toString());
            return null;
        }
        if (request.searchRequest(idUser, Commands.GroupDelete.toString()).equals(idUser + Commands.GroupDelete.toString())) {
            String mes = message.getText();
            if (checkDate(mes, message).equals(null)) return null;
            writeRequestDeadline(Commands.DateDelete.toString(), Commands.GroupDelete.toString(), Commands.Delete.toString(), message);
            request.deleteRequest(idUser, Commands.Delete.toString());
            return null;
        }
        if (request.searchRequest(idUser, Commands.GroupDeadline.toString()).equals(idUser + Commands.GroupDeadline.toString())) {
            String mes = message.getText();
            if (checkDate(mes, message).equals(null)) return null;
            writeRequestDeadline(Commands.DateDeadline.toString(), Commands.GroupDeadline.toString(), Commands.Deadline.toString(), message);
            request.deleteRequest(idUser, Commands.Deadline.toString());
            return null;
        }
        analyzeGroup(message);
        return null;
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
            return null;
        }
        final int IntWords1 = Integer.parseInt(word[0]);
        if ((IntWords1 > 31) || (IntWords1 < 1)) {
            sb.append(send.errorInput).append("день");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return null;
        }
        final int IntWords2 = Integer.parseInt(word[1]);
        if ((IntWords2 > 12) || (IntWords2 < 1)) {
            sb.append(send.errorInput).append("месяц");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return null;
        }
        final int IntWords3 = Integer.parseInt(word[2]);
        if (IntWords3 < Integer.parseInt(year)) {
            sb.append(send.errorInput).append("год");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return null;
        }
        for (String wor : word)
            if (wor.length() > 5) {
                sb.append(send.errorInput).append("дедлайн");
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return null;
            }
        return "True";
    }

    /**
     * Метод
     * @param message   Сообщение пользователся обратившийся к боту
     * @return          null
     */
    private String analyzeGroup(Message message) {
        String idUser = message.getFrom().getId().toString();
        if (request.searchRequest(idUser, Commands.Deadline.toString()).equals(idUser + Commands.Deadline.toString())) {
            writeRequestGroup(Commands.GroupDeadline.toString(), message);
            return null;
        }
        if (request.searchRequest(idUser, Commands.Delete.toString()).equals(idUser + Commands.Delete.toString())) {
            writeRequestGroup(Commands.GroupDelete.toString(), message);
            return null;
        }
        if (request.searchRequest(idUser, Commands.Add.toString()).equals(idUser + Commands.Add.toString())) {
            writeRequestGroup(Commands.GroupAdd.toString(), message);
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
        command.execute(message);
    }

    /**
     * Метод для удаления всех запросов
     * @param idUser        Уникальный индификатор пользователя
     */
    private void deleteAllRequest(String idUser) {
        for (int i = 0; i < Commands.values().length; i++)
            request.deleteRequest(idUser, Commands.valueOf(i).toString());
    }

    /**
     * Метод для сохранения учебной группы введенным пользователем
     * @param command       Команда запроса
     * @param message       Сообщение пользователся обратившийся к боту
     */
    private void writeRequestGroup(String command, Message message) {
        String words = message.getText();
        String idUser = message.getFrom().getId().toString();
        String[] number = words.split("-");
        String lenOne = number[0];
        String lenTwo = number[1];
        try {
            if (number.length == 2 && lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                request.writeRequest(idUser, command, words);
                send.sendMsg(message, botProvider.searchDate(words));
                send.sendMsg(message, "Напишите мне ДД.ММ.ГГГГ дедлайна");
            } else {
                StringBuilder s = new StringBuilder();
                s.append(errorInput).append("Группа");
                send.sendMsg(message, s.toString());
            }
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Метод для сохранения дедлайна введенным пользователем
     * @param date              Дедлайн
     * @param group             Учебная группа
     * @param requestUser       Запрос пользователя
     * @param message           Сообщение пользователся обратившийся к боту
     */
    private void writeRequestDeadline(String date, String group, String requestUser, Message message) {
        String mes = message.getText();
        String idUser = message.getFrom().getId().toString();
        try {
            try {
                request.writeRequest(idUser, date, mes);
            } catch (IOException e) {
                log.error(e);
            }
            send.sendMsg(message, botProvider.searchDiscipline(request.readRequest(idUser, group), mes));
            send.sendMsg(message, "Напишите мне название учебной дисциплины");
            request.deleteRequest(idUser, requestUser);
        } catch (IOException e) {
            log.error(e);
        }
    }
}