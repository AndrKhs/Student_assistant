package providerBot.botAbility.functions.send;

import providerBot.botAbility.constants.ConstantError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.*;
import java.util.Random;

/**
 * Класс для отправки музыки
 */
public class SendMusic extends Send {
    /**
     * Константа логирования
     */
    private static final Logger log = LoggerFactory.getLogger(SendMusic.class);
    /**
     * Метод для отправки музыки
     * @param message    Сообщение пользователся обратившийся к боту
     * @throws FileNotFoundException
     */
    @Override
    public void execute(Message message, String text) {
        log.info("Send audio " + message.getFrom().getUserName());
        Random random = new Random();
        java.io.File file = new java.io.File(System.getProperty("user.dir") + "\\Music\\" + "music");
        FileInputStream fI = null;
        try {
            fI = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            log.error(ConstantError.FILE_NO_EXIST.gerError(), e);
        }
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
        } catch (IOException e) {
            log.error(ConstantError.READ_FILE_MUSIC.gerError(), e);
        }
        String[] music = resultStringBuilder.toString().split("@");
        int rand = random.nextInt(music.length);
        SendAudio sendAudio = new SendAudio();
        sendAudio.setChatId(message.getChatId().toString());
        sendAudio.setAudio(music[rand]);
        try {
            execute(sendAudio);
        } catch (TelegramApiException e) {
            log.error(ConstantError.SEND_AUDIO.gerError(), e);
        }
    }
}
