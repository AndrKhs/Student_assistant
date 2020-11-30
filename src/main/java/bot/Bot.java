package bot;

import botAbility.FunctionsBot.*;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.DailyRollingFileAppender;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Bot extends TelegramLongPollingBot implements BotMain {
    /**
     * Констатные ошибки
     */
    final String errorInput = "Неккоректный ввод: ";
    /**
     * Констата именни бота
     */
    final String botName = "Помощник Студента";

    private final static Logger log = Logger.getLogger(Bot.class);


    public static void main(String[] args) {
        logger();

        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        BotAPI.setMap();
        try {
            botApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            log.error(e);
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
        BotFunctions functionsBot = new DateBaseBot();
        Message message = update.getMessage();
        BotLogic record = new BotAPI();
        if (message.getDocument() != null) sendMsg(message, "Я не распознаю файлы \uD83E\uDD16");
        if (message.getSticker() != null) sendMsg(message, "Я люблю текст и команды, а не стикеры \uD83D\uDE2C");
        if (message.getAudio() != null) {
            try {
                sendMsg(message, functionsBot.writeMusic(message.getAudio().getFileId()));
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
            }
        }
        if (message.getPhoto() != null) sendMsg(message, "Я не знаю что тут \uD83D\uDE2D");
        if (message.getVideo() != null) sendMsg(message, "Не люблю видео \uD83D\uDC94. Люблю команды \uD83D\uDE0D");
        if (message.getVoice() != null) sendMsg(message, "У меня нет ушек");
        if (message != null && message.hasText()) {
            record.consoleRecordingDeadline(message);
        }
    }


    /**
     * @param message Сообщение пользователся обратившийся к боту
     * @param text    текст которое бот отправить пользователю
     */
    public void sendMsg(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e);
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
        File fil = new File (sb.toString());
        FileInputStream fI = new FileInputStream(fil);
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        } catch (IOException e) {
            log.error(e);
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
            log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Устанавливает кнопки в телеграмм чат
     *
     * @param sendMessage сообщение бота для пользователя
     */
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
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
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
            log.error(e);
            e.printStackTrace();
        }
        return "";
    }

    private static void logger(){

        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

        PatternLayout layout = new PatternLayout();
        String conversionPattern = System.currentTimeMillis()+" " + "%d [%p] %L %c{-1} %M - %m%n";
        layout.setConversionPattern(conversionPattern);

        DailyRollingFileAppender rollingAppender = new DailyRollingFileAppender();
        rollingAppender.setFile("logs\\log_"+ date +".log");
        rollingAppender.setLayout(layout);
        rollingAppender.activateOptions();

        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(rollingAppender);
    }
}