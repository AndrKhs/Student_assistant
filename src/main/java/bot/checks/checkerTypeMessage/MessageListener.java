package bot.checks.checkerTypeMessage;

import bot.checks.validate.IVerifier;
import bot.checks.validate.Verifier;
import bot.constants.*;
import bot.readers.IReader;
import bot.readers.Reader;
import bot.sender.MessageSender;
import bot.logic.IBotLogic;
import bot.logic.BotLogic;
import bot.writers.IWriter;
import bot.writers.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

/**
 * Класс определения типа сообщений
 */
public class MessageListener implements IMessageListener {
    private static final IBotLogic record = new BotLogic();
    /**
     * Константа для отправки сообщения
     */
    private final MessageSender messageSender = new MessageSender();

    /**
     * Набор вспомогательных сущностей для работы с файлами
     */
    private final IWriter write = new Writer();
    private final IReader read = new Reader();

    /**
     * Вспомогательная сущность для проверки введенных значений
     */
    private final IVerifier validator = new Verifier();

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(MessageListener.class);

    @Override
    public void checkTypeMessage(Message message) {
        if (message.getDocument() != null)
            messageSender.execute(message, AppConstants.MESSAGE_PROCESSING_FILE.toStringValue());
        if (message.getSticker() != null)
            messageSender.execute(message, AppConstants.MESSAGE_PROCESSING_STICKER.toStringValue());
        if (message.getAudio() != null) {
            try {
                messageSender.execute(message, write.writeMusic(message.getAudio().getFileId()));
            } catch (IOException e) {
                log.error(AppErrorConstants.SEND_MUSIC_USER.toStringValue(), e);
            }
        }
        if (message.getPhoto() != null)
            messageSender.execute(message, AppConstants.MESSAGE_PROCESSING_PICTURES.toStringValue());
        if (message.getVideo() != null)
            messageSender.execute(message, AppConstants.MESSAGE_PROCESSING_VIDEO.toStringValue());
        if (message.getVoice() != null)
            messageSender.execute(message, AppConstants.MESSAGE_PROCESSING_VOICE.toStringValue());
        if (message.hasText()) {
            String mes = message.getText().toLowerCase();
            if (mes.equals(AppConstants.COMMAND_START.toStringValue())) {
                StringBuilder sb = new StringBuilder();
                sb.append(AppConstants.START_FIRST.toStringValue())
                        .append(message.getFrom().getFirstName()).append(AppConstants.START_SECOND.toStringValue());
                messageSender.execute(message, sb.toString());
            }
            if (!validator.isExistUser(message.getFrom().getId().toString())) {
                if (validator.checkGroup(mes))
                    try {
                        write.writeUserDate(message, mes);
                        messageSender.execute(message, AppConstants.GROUP_SAVE.toStringValue());
                        return;
                    } catch (IOException e) {
                        log.error(AppErrorConstants.CREATED_GROUP.toStringValue(), e);
                    }
                else if(!mes.equals(AppConstants.COMMAND_START.toStringValue())){
                    messageSender.execute(message, AppErrorConstants.UNKNOWN_GROUP.toStringValue());
                    return;
                }
            }
            try {
                record.consoleRecordingDeadline(message, read.readUserDate(message.getFrom().getId().toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
