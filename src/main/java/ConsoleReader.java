import botAbility.Consol.*;
import botAbility.FunctionsBot.FunctionsBot;
import botAbility.FunctionsBot.GetFunctionsBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс работающий с вводном пользователя, так же добавляет возможность использовать кнопики вместо написания комманд
 */
public class ConsoleReader implements BotLogic {
    /**
     * Метод сохраняет ввод пользоватеся для дальнейшей обработки
     * А так же обрабатывает их что позволяет боту более правильно работать с дедлайнами
     *
     * @param message сообщение пользователся обратившийся к боту
     * @return
     */
    public String consoleRecordingDeadline(Message message) {
        FunctionsBot functionsBot = new GetFunctionsBot();
        Bot send = new Bot();
        botConsol request = new RequestConsol();
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
        if (command.contains(words)) {
            try {
                for (Integer k = 0; k != 6; k++) {
                    request.deleteRequest(idUser, k.toString());
                    request.deleteRequest(idUser, "9");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (request.searchRequest(idUser, "5").equals(idUser + "5")) {
                sb.append("Дедлайн создан ").append(functionsBot.writeFile(request.readRequest(idUser, "4"),
                        request.readRequest(idUser, "5"), message.getText(),
                        request.readRequest(idUser, "3")));
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                try {
                    for (Integer k = 3; k < 6; k++) {
                        request.deleteRequest(idUser, k.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "break";
            }
            if (request.searchRequest(idUser, "4").equals(idUser + "4")) {
                try {
                    request.writeRequest(idUser, "5", message.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append("Напишите мне что вам задали по ").append(request.readRequest(idUser, "5"));
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return "break";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (deadLine(message).equals("break"))
            return "break";
        return "";
    }

    /**
     * Устанавливает кнопки в телеграмм чат
     *
     * @param sendMessage сообщение бота для пользователя
     */
    @Override
    public synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Помощь"));
        keyboardFirstRow.add(new KeyboardButton("Удалить"));
        keyboardFirstRow.add(new KeyboardButton("Дедлайн"));
        keyboardFirstRow.add(new KeyboardButton("Добавить"));
        keyboardSecondRow.add(new KeyboardButton("Случайная музыка"));
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    /**
     * Нужен для временного хранение введенной даты для создание дедлайна
     *
     * @param message Сообщение пользователся обратившийся к боту
     * @return возрощает "break" для последущего игнорирование switch case
     */
    private String deadLine(Message message) {
        String idUser = message.getFrom().getId().toString();
        botConsol request = new RequestConsol();
        Bot send = new Bot();
        String mes = message.getText();
        StringBuilder sb = new StringBuilder();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        final String[] word = mes.split("\\.");
        try {
            sb.append(idUser).append("2");
            if (request.searchRequest(idUser, "2").equals(sb.toString())) {
                String files = request.readRequest(idUser, "2");
                sb.delete(0, sb.length());
                StringBuilder sbDel = new StringBuilder();
                sbDel.append(System.getProperty("user.dir")).append("\\Files\\").append(files).append("\\").append(mes);
                final File fi = new File(sbDel.toString());
                if (fi.delete()) {
                    sb.append("Дедлайн удален");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0, sb.length());
                } else {
                    sb.append(send.errorInput).append("Дедлайна не существует");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0, sb.length());
                }
                request.deleteRequest(idUser, "0");
                return "break";
            }
            sb.delete(0, sb.length());
            sb.append(idUser).append("3");
            if (request.searchRequest(idUser, "3").equals(sb.toString())) {
                if (checkDate(mes, message).equals("break")) return "break";
                sb.delete(0, sb.length());
                try {
                    request.writeRequest(idUser, "4", mes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.sendMsg(message, "Напишите мне название дисциплины, по которому вам что то задали");
                request.deleteRequest(idUser, "1");
                return "break";
            }
            sb.delete(0, sb.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Нужен для проверки правильности ввода дедлайна
     *
     * @param mes     Текст сообщения пользователя
     * @param message Сообщение пользователся обратившийся к боту
     * @return возрощает "break" для последущего игнорирование switch case
     */
    private String checkDate(String mes, Message message) {
        Bot send = new Bot();
        StringBuilder sb = new StringBuilder();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        final String[] word = mes.split("\\.");
        if (word.length != 3) {
            sb.append(send.errorInput).append("дедлайн");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "break";
        }
        final int IntWords1 = Integer.parseInt(word[0]);
        if ((IntWords1 > 31) || (IntWords1 < 1)) {
            sb.append(send.errorInput).append("день");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "break";
        }
        final int IntWords2 = Integer.parseInt(word[1]);
        if ((IntWords2 > 12) || (IntWords2 < 1)) {
            sb.append(send.errorInput).append("месяц");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "break";
        }
        final int IntWords3 = Integer.parseInt(word[2]);
        if (IntWords3 < Integer.parseInt(year)) {
            sb.append(send.errorInput).append("год");
            send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "break";
        }
        for (String wor : word)
            if (wor.length() > 5) {
                sb.append(send.errorInput).append("дедлайн");
                send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return "break";
            }
        return "";
    }
}