package bot.checks.checkerTypeMessage;

import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.botLogic.IBotLogic;
import bot.botLogic.BotLogic;
import bot.sender.MessageSender;
import bot.writers.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

/**
 * Класс определения типа сообщений
 */
public class CheckerTypeMessage implements ICheckerTypeMessage {
    private static final IBotLogic record = new BotLogic();
    /**
     * Константа для отправки сообщения
     */
    private final MessageSender sendMsg = new MessageSender();
    /**
     * Константа для записи музыки
     */
    private final Writer write = new Writer();

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(CheckerTypeMessage.class);

    @Override
    public void CheckTypeMessage(Message message) {
        if (message.getDocument() != null)
            sendMsg.execute(message, AppConstants.MESSAGE_PROCESSING_FILE.toStringValue());
        if (message.getSticker() != null)
            sendMsg.execute(message, AppConstants.MESSAGE_PROCESSING_STICKER.toStringValue());
        if (message.getAudio() != null) {
            try {
                sendMsg.execute (message,write.writeMusic(message.getAudio().getFileId()));
            } catch (IOException e) {
                log.error(AppErrorConstants.SEND_MUSIC_USER.toStringValue(),e);
            }
        }
        if (message.getPhoto() != null)
            sendMsg.execute(message, AppConstants.MESSAGE_PROCESSING_PICTURES.toStringValue());
        if (message.getVideo() != null)
            sendMsg.execute(message, AppConstants.MESSAGE_PROCESSING_VIDEO.toStringValue());
        if (message.getVoice() != null)
            sendMsg.execute(message, AppConstants.MESSAGE_PROCESSING_VOICE.toStringValue());
        if (message.hasText()) {
            record.consoleRecordingDeadline(message);
        }
    }
}
