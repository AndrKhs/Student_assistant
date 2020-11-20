import botAbility.Consol.*;
import botAbility.FunctionsBot.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.Random;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Bot extends TelegramLongPollingBot {
    /**
     * Констатные ошибки
     */
    final String errorInput = "Неккоректный ввод: ";
    final String errorUnknowCommand = "Неизвестная команда \uD83D\uDC68\u200D\uD83D\uDCBB\uD83E\uDDD1\u200D\uD83D\uDCBB";
    /**
     * Констата именни бота
     */
    final String botName = "Помощник Студента";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод для работы бота с чатом
     *
     * @param update - бновленные переменные после обращение пользователся к боту
     */
    @Override
    public void onUpdateReceived(Update update) {
        FunctionsBot functionsBot = new GetFunctionsBot();
        Message message = update.getMessage();
        BotLogic record = new ConsoleReader();
        String idUser = message.getFrom().getId().toString();
        if (message.getDocument() != null) sendMsg(message, "Я не распознаю файлы \uD83E\uDD16");
        if (message.getSticker() != null) sendMsg(message, "Я люблю текст и команды, а не стикеры \uD83D\uDE2C");
        if (message.getAudio() != null) {
            try {
                sendMsg(message, functionsBot.writeMusic(message.getAudio().getFileId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (message.getPhoto() != null) sendMsg(message, "Я не знаю что тут \uD83D\uDE2D");
        if (message.getVideo() != null) sendMsg(message, "Не люблю видео \uD83D\uDC94. Люблю команды \uD83D\uDE0D");
        if (message != null && message.hasText()) {
            String words = message.getText();
            if (record.consoleRecordingDeadline(message).equals("break"))
                words = "break";
            if (analyzeInput(idUser, words, message).equals("break"))
                words = "break";
            getCommand(words, idUser, message);
        }
    }

    private String getCommand(String words, String idUser, Message message) {
        botConsol request = new RequestConsol();
        FunctionsBot getGroupList = new GetFunctionsBot();
        StringBuilder sb = new StringBuilder();
        switch (words.toLowerCase()) {
            case "break":
                break;
            case "/start":
                sb.append("Привет ").append(message.getFrom().getFirstName())
                        .append(", можешь обратиться ко мне за помощью!").append("\n")
                        .append("\"Помощь\" - список команд");
                sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                break;
            case "помощь":
                sb.append("\"Помощь\" - список команд")
                        .append("\n")
                        .append("\"Дедлайн\" - cписок дедлайнов")
                        .append("\n")
                        .append("\"Удалить\" - принудительно удаляет дедлайн")
                        .append("\n")
                        .append("\"Добавить\" - добавить дедлайн и домашнее задание")
                        .append("\n")
                        .append("\"Случайная музыка\" - рандомная музыка из плейлиста бота")
                        .append("\n")
                        .append("Отправьте музыку боту, чтобы он сохранил в свой плейлист");
                sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                break;
            case "удалить":
                try {
                    request.writeRequest(idUser, "0", "");
                    sendMsg(message, getGroupList.searchGroup());
                    sendMsg(message, "Напишите мне существующую академическую группу");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "дедлайн":
                try {
                    request.writeRequest(idUser, "9", "");
                    ;
                    sendMsg(message, getGroupList.searchGroup());
                    sendMsg(message, "Напишите мне существующую академическую группу");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "добавить":
                try {
                    request.writeRequest(idUser, "1", "");
                    ;
                    sendMsg(message, getGroupList.searchGroup());
                    sendMsg(message, "Напишите мне существующую академическую группу или напишите новую (Пример: МЕН-292203)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "случайная музыка":
                try {
                    sendAudio(message);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                sendMsg(message, errorUnknowCommand);
                break;
        }
        return "";
    }

    private String analyzeInput(String idUser, String words, Message message) {
        botConsol request = new RequestConsol();
        FunctionsBot functionsBot = new GetFunctionsBot();

        try {
            if (request.searchRequest(idUser, "9").equals(idUser + "9")) {
                String[] number = words.split("-");
                String lenOne = number[0];
                String lenTwo = number[1];
                if (number.length == 2 && lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    sendMsg(message, functionsBot.searchDeadline(words));
                    request.deleteRequest(idUser, "9");
                    return "break";
                } else {
                    StringBuilder s = new StringBuilder();
                    s.append(errorInput).append("Группа");
                    sendMsg(message, s.toString());
                    return "break";
                }
            }
            if (request.searchRequest(idUser, "0").equals(idUser + "0")) {
                String[] number = words.split("-");
                String lenOne = number[0];
                String lenTwo = number[1];
                if (number.length == 2 && lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    request.writeRequest(idUser, "2", words);
                    sendMsg(message, "Напишите мне ДД.ММ.ГГГГ дедлайна, который вы хотите удалить");
                    return "break";
                } else {
                    StringBuilder s = new StringBuilder();
                    s.append(errorInput).append("Группа");
                    sendMsg(message, s.toString());
                    return "break";
                }
            }
            if (request.searchRequest(idUser, "1").equals(idUser + "1")) {
                String[] number = words.split("-");
                String lenOne = number[0];
                String lenTwo = number[1];
                if (number.length == 2 && lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    request.writeRequest(idUser, "3", words);
                    sendMsg(message, "Напишите мне ДД.ММ.ГГГГ дедлайна, который вы хотите создать или дополнить");
                    return "break";
                } else {
                    StringBuilder s = new StringBuilder();
                    s.append(errorInput).append("Группа");
                    sendMsg(message, s.toString());
                    return "break";
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param message Сообщение пользователся обратившийся к боту
     * @param text    текст которое бот отправить пользователю
     */
    public void sendMsg(Message message, String text) {
        BotLogic set = new ConsoleReader();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            set.setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод нужен для отправки случайной музыки
     *
     * @param message Сообщение пользователся обратившийся к боту
     * @throws FileNotFoundException
     */
    public void sendAudio(Message message) throws FileNotFoundException {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Music\\").append("music");
        File fil = new File(sb.toString());
        FileInputStream fI = new FileInputStream(fil);
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] music = resultStringBuilder.toString().split("@");
        int rand = random.nextInt(music.length);
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(message.getChatId().toString());
        sendAudio.setAudio(music[rand]);
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Имя бота
     *
     * @return возращает имя
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * @return Возращает token бота
     * @throws IOException
     */
    private String readToken() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Token.txt");
        File fi = new File(sb.toString());
        if (fi.exists()) {
            FileInputStream fI = new FileInputStream(fi);
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line);
                }
                return resultStringBuilder.toString();
            }
        }
        return "";
    }

    /**
     * Токен бота - нужен для связки кода с ботом в телеграмме
     *
     * @return возращает токен бота
     */
    @Override
    public String getBotToken() {
        try {
            return readToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}