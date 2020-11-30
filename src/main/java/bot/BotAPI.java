package bot;

import botAbility.Consol.*;
import botAbility.FunctionsBot.BotFunctions;
import botAbility.FileRequest;
import botAbility.FunctionsBot.DateBaseBot;
import commands.*;
import org.telegram.telegrambots.meta.api.objects.Message;

import botAbility.Consol.RequestConsol;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Класс работающий с вводом пользователя, так же добавляет возможность использовать кнопики вместо написания комманд
 */
public class BotAPI implements BotLogic {

    Bot send = new Bot();
    private static HashMap <String,Command> map = new HashMap<>();
    private final static Logger log = Logger.getLogger(BotAPI.class);

    botConsol request = new RequestConsol();
    BotFunctions functionsBot = new DateBaseBot();

    final String errorInput = "Неккоректный ввод: ";
    final String errorUnknowCommand = "Неизвестная команда \uD83D\uDC68\u200D\uD83D\uDCBB\uD83E\uDDD1\u200D\uD83D\uDCBB";

    public String consoleRecordingDeadline(Message message) {
        StringBuilder sb = new StringBuilder();
        String idUser = message.getFrom().getId().toString();
        String words = message.getText().toLowerCase();
        ArrayList<String> command = new ArrayList<>();
        command.add("помощь");
        command.add("удалить");
        command.add("дедлайн");
        command.add("добавить");
        command.add("/start");
        command.add("случайная музыка");
        if (command.contains(words))
            deleteRequest(idUser);
        try {
            if (request.searchRequest(idUser, Com.DateDelete.toString()).equals(idUser + Com.DateDelete.toString())){
                FileRequest file = new FileRequest();

                file.setDateDelete(request.readRequest(idUser, Com.DateDelete.toString()));
                file.setDisciplineDelete(message.getText());
                file.setGroupDelete(request.readRequest(idUser, Com.GroupDelete.toString()));

                send.sendMsg(message, functionsBot.deleteFile(file));

                deleteRequest(idUser);
                return null;
            }
            if (request.searchRequest(idUser, Com.DateDeadline.toString()).equals(idUser + Com.DateDeadline.toString())){
                FileRequest file = new FileRequest();

                file.setDateDeadline(request.readRequest(idUser, Com.DateDeadline.toString()));
                file.setDisciplineDeadline(message.getText());
                file.setGroupDeadline(request.readRequest(idUser, Com.GroupDeadline.toString()));

                send.sendMsg(message, functionsBot.searchDeadline(file));

                deleteRequest(idUser);
                return null;

            }
            if (request.searchRequest(idUser, Com.DisciplineAdd.toString()).equals(idUser + Com.DisciplineAdd.toString())) {
                FileRequest file = new FileRequest();

                file.setDataAdd(request.readRequest(idUser, Com.DateAdd.toString()));
                file.setDisciplineAdd(request.readRequest(idUser, Com.DisciplineAdd.toString()));
                file.setGroupAdd(request.readRequest(idUser, Com.GroupAdd.toString()));

                sb.append("Дедлайн создан ").append(functionsBot.writeFile(file, message.getText()));
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                deleteRequest(idUser);
                return null;
            }
            try {
                if (request.searchRequest(idUser, Com.DateAdd.toString()).equals(idUser + Com.DateAdd.toString())) {
                        request.writeRequest(idUser, Com.DisciplineAdd.toString(), message.getText());
                    sb.append("Напишите мне что вам задали по ").append(request.readRequest(idUser, Com.DisciplineAdd.toString()));
                    send.sendMsg(message, sb.toString());
                    sb.delete(0, sb.length());
                    return null;
                }
                if (request.searchRequest(idUser, Com.DateDelete.toString()).equals(idUser + Com.DateDelete.toString())){
                    request.writeRequest(idUser,Com.DisciplineDelete.toString(), message.getText());
                    return null;
                }
                if (request.searchRequest(idUser, Com.DateDeadline.toString()).equals(idUser + Com.DateDeadline.toString())){
                    request.writeRequest(idUser,Com.DisciplineDeadline.toString(),message.getText());
                    return null;
                }

            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        analyzeDate(message);
        return null;
    }

    /**
     * Нужен для временного хранение введенной даты для создание дедлайна
     *
     * @param message Сообщение пользователся обратившийся к боту
     * @return возрощает "break" для последущего игнорирование switch case
     */
    private String analyzeDate(Message message) {
        String idUser = message.getFrom().getId().toString();
        try {
            if (request.searchRequest(idUser, Com.GroupAdd.toString()).equals(idUser + Com.GroupAdd.toString())){
                String mes = message.getText();
                if (checkDate(mes, message).equals(null)) return null;
                writeRequestDeadline(Com.DateAdd.toString(), Com.GroupAdd.toString() , Com.Add.toString(), message);
                request.deleteRequest(idUser, Com.Add.toString());
                return null;
            }
            if (request.searchRequest(idUser, Com.GroupDelete.toString()).equals(idUser + Com.GroupDelete.toString())){
                String mes = message.getText();
                if (checkDate(mes, message).equals(null)) return null;
                writeRequestDeadline(Com.DateDelete.toString(), Com.GroupDelete.toString() ,Com.Delete.toString() , message);
                request.deleteRequest(idUser, Com.Delete.toString());
                return null;
            }
            if (request.searchRequest(idUser, Com.GroupDeadline.toString()).equals(idUser + Com.GroupDeadline.toString())) {
                String mes = message.getText();
                if (checkDate(mes, message).equals(null)) return null;
                writeRequestDeadline(Com.DateDeadline.toString(), Com.GroupDeadline.toString(), Com.Deadline.toString(), message);
                request.deleteRequest(idUser, Com.Deadline.toString());
                return null;
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        analyzeGroup(message);
        return null;
    }


    /**
     * Нужен для проверки правильности ввода дедлайна
     *
     * @param mes     Текст сообщения пользователя
     * @param message Сообщение пользователся обратившийся к боту
     * @return возрощает "break" для последущего игнорирование switch case
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

    private String analyzeGroup(Message message) {
        String idUser = message.getFrom().getId().toString();
        try {
            if (request.searchRequest(idUser, Com.Deadline.toString()).equals(idUser + Com.Deadline.toString())){
                writeRequestGroup(Com.GroupDeadline.toString(), message);
                return null;
            }
            if (request.searchRequest(idUser,  Com.Delete.toString()).equals(idUser +  Com.Delete.toString())){
                writeRequestGroup(Com.GroupDelete.toString(), message);
                return null;
            }
            if (request.searchRequest(idUser,  Com.Add.toString()).equals(idUser + Com.Add.toString())){
                writeRequestGroup(Com.GroupAdd.toString(), message);
                return null;
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        checkCommand(message);
        return null;
    }

    protected static void setMap (){
        map.put("/start", new Start());
        map.put("помощь", new Help());
        map.put("добавить", new Add());
        map.put("удалить", new Delete());
        map.put("случайная музыка", new RandomMusic());
        map.put("дедлайн", new Deadline());
    }


    private void checkCommand(Message message) {
        Command command = map.get(message.getText().toLowerCase());
        if(command == null) send.sendMsg(message, errorUnknowCommand);
        command.execute(message);
    }

    private void deleteRequest(String idUser){
        try {
            for(int i = 0; i < Com.values().length; i++)
                request.deleteRequest(idUser,Com.valueOf(i).toString());
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    private String writeRequestGroup(String command, Message message){
        String words = message.getText();
        String idUser = message.getFrom().getId().toString();
        String[] number = words.split("-");
        String lenOne = number[0];
        String lenTwo = number[1];
        try {
            if (number.length == 2 && lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                request.writeRequest(idUser, command, words);
                send.sendMsg(message, functionsBot.searchDate(words));
                send.sendMsg(message, "Напишите мне ДД.ММ.ГГГГ дедлайна");
            } else {
                StringBuilder s = new StringBuilder();
                s.append(errorInput).append("Группа");
                send.sendMsg(message, s.toString());
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }

    private void writeRequestDeadline (String date, String group, String requestUser, Message message){
        String mes = message.getText();
        String idUser = message.getFrom().getId().toString();
        try {
            try {
                request.writeRequest(idUser, date, mes);
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
            }
            send.sendMsg(message, functionsBot.searchDiscipline(request.readRequest(idUser, group), mes));
            send.sendMsg(message, "Напишите мне название учебной дисциплины");
            request.deleteRequest(idUser, requestUser);
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }
}