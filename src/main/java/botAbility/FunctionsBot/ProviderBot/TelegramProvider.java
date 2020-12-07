package botAbility.FunctionsBot.ProviderBot;

import botAbility.Console.FileRequest;
import botAbility.Console.RequestConsole;
import botAbility.Console.botConsole;
import botAbility.FunctionsBot.BotAPI.BotAPI;
import botAbility.FunctionsBot.BotAPI.BotCommunication;
import botAbility.FunctionsBot.BotAPI.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelegramProvider extends TelegramLongPollingBot implements BotProvider {

    protected BotProvider getGroupList = new DateBaseBot();

    /**
     * Набор сообщений об ошибках
     */
    public final String errorInput = "Неккоректный ввод: ";

    /**
     * Констата именни бота
     */
    final String botName = "Помощник Студента";

    /**
     * Константа для объявление конструктора
     */
    private BotProvider botProvider = new DateBaseBot();

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(TelegramProvider.class);

    /**
     * Метод для работы бота с чатом
     *
     * @param update - обновленные переменные после обращение пользователся к боту
     */
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        BotCommunication record = new BotAPI();
        if (message.getDocument() != null) sendMsg(message, "Я не распознаю файлы \uD83E\uDD16");
        if (message.getSticker() != null) sendMsg(message, "Я люблю текст и команды, а не стикеры \uD83D\uDE2C");
        if (message.getAudio() != null) {
            try {
                sendMsg(message,botProvider.writeMusic(message.getAudio().getFileId()));
            } catch (IOException e) {
                log.error(e.toString());
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
     * Метод для отправки сообщения
     * @param message   Сообщение пользователся обратившийся к боту
     * @param text      Текст для ответа на сообщение пользователя
     */
    public void sendMsg(Message message, String text) {
        String idUser = message.getFrom().getId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage, idUser, message.getText());
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.toString());
        }
    }

    /**
     * Метод для отправки аудио
     * @param message    Сообщение пользователся обратившийся к боту
     * @throws FileNotFoundException
     */
    public void sendAudio(Message message) throws FileNotFoundException {
        log.info("Send audio " + message.getFrom().getUserName());
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Music\\").append("music");
        File file = new File(sb.toString());
        FileInputStream fI = new FileInputStream(file);
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
        String[] music = resultStringBuilder.toString().split("@");
        int rand = random.nextInt(music.length);
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(message.getChatId().toString());
        sendAudio.setAudio(music[rand]);
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            log.error(e.toString());
        }
    }

    /**
     * Устанавливает кнопки в телеграмм чат
     * @param sendMessage сообщение бота для пользователя
     */
    private synchronized void setButtons(SendMessage sendMessage, String idUser, String messege) {
        botConsole request = new RequestConsole();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardTriadRow = new KeyboardRow();
        for(int i = 0; i < 3; i++){
            if(request.searchRequest(idUser, Commands.valueOf(i)).equals(idUser + Commands.valueOf(i))){
                String[] keyGroup= getGroupList.searchGroup().split("\n");
                for(int j = 2; j < keyGroup.length; j++) {
                    keyboardFirstRow.add(new KeyboardButton(keyGroup[j]));
                }
                keyboardSecondRow.add(new KeyboardButton("Назад"));
            }
        }
        for(int i = 3; i < 6; i++){
            if(request.searchRequest(idUser, Commands.valueOf(i)).equals(idUser + Commands.valueOf(i))){
                keyboardFirstRow.clear();
                keyboardSecondRow.clear();
                keyboardTriadRow.clear();
                String[] keyDate = getGroupList.searchDate(messege).split("\n");
                for(int j = 2; j < keyDate.length; j++) {
                    if(j<10) keyboardFirstRow.add(new KeyboardButton(keyDate[j]));
                    if(j>9 & j<19) keyboardSecondRow.add(new KeyboardButton(keyDate[j]));
                }
                keyboardTriadRow.add(new KeyboardButton("Назад"));
            }
        }
        for(int i = 6; i < 9; i++){
            if(request.searchRequest(idUser, Commands.valueOf(i)).equals(idUser + Commands.valueOf(i))){
                keyboardFirstRow.clear();
                keyboardSecondRow.clear();
                keyboardTriadRow.clear();
                String[] keyDate = new String[0];
                try {
                    keyDate = getGroupList.searchDiscipline(request.readRequest(idUser, Commands.valueOf(i-3)),messege).split("\n");
                } catch (IOException e) {
                    log.error("Чет с кнопкой", e);
                }
                for(int j = 2; j < keyDate.length; j++) {
                if(j<10) keyboardFirstRow.add(new KeyboardButton(keyDate[j]));
                if(j>9 & j<19) keyboardSecondRow.add(new KeyboardButton(keyDate[j]));
            }
            keyboardTriadRow.add(new KeyboardButton("Назад"));
            }
        }
        if(request.searchRequest(idUser, Commands.DeleteGroup).equals(idUser + Commands.DeleteGroup)){
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardTriadRow.clear();
            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("user.dir")).append("\\Files\\");
            Path path = Paths.get(sb.toString());
            if (Files.exists(path)) {
                File groups = new File(sb.toString());
                for (String s : groups.list()) {
                    String[] group = s.split("_");
                    keyboardFirstRow.add(new KeyboardButton(group[0]));
                }
            }
            keyboardTriadRow.add(new KeyboardButton("Назад"));
        }
        if(!request.searchRequest(idUser, Commands.Back).equals(idUser + Commands.Back)) {
            keyboardFirstRow.add(new KeyboardButton("Помощь"));
            keyboardFirstRow.add(new KeyboardButton("Дедлайн"));
            keyboardFirstRow.add(new KeyboardButton("Добавить"));
            keyboardSecondRow.add(new KeyboardButton("Случайная музыка"));
            keyboardTriadRow.add(new KeyboardButton("Удалить дедлайн"));
            keyboardTriadRow.add(new KeyboardButton("Удалить группу с дедлайнами"));
        }
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardTriadRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    /**
     * Имя бота
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
                log.error(e.toString());
            }
        }
        return "";
    }

    /**
     * Токен бота - нужен для связки кода с ботом в телеграмме
     * @return возращает токен бота
     */
    public String getBotToken() {
        try {
            return readToken();
        } catch (IOException e) {
            log.error(e.toString());
        }
        return "Error";
    }

    @Override
    public String writeFile(FileRequest file, String writed) throws IOException {
        return null;
    }

    @Override
    public String readDeadline(FileRequest file) throws IOException {
        return null;
    }

    @Override
    public String writeMusic(String nameMusic) throws IOException {
        return null;
    }

    @Override
    public String deleteDeadline(FileRequest file) {
        return null;
    }

    @Override
    public String searchGroup() {
        return null;
    }

    @Override
    public String searchDiscipline(Object group, String date) {
        return null;
    }

    @Override
    public String searchDate(Object group) {
        return null;
    }
}
