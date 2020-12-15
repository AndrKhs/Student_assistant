package providerBot.botAbility.checks.checkerTypeMessage;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.botLogic.IBotLogic;
import providerBot.botAbility.botLogic.BotLogic;
import providerBot.botAbility.functions.writers.Writers;
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
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Константа для записи музыки
     */
    private final Writers write = new Writers();

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(CheckerTypeMessage.class);

    @Override
    public void CheckTypeMessage(Message message) {
        if (message.getDocument() != null)
            sendMsg.execute(message, Constant.MESSAGE_PROCESSING_FILE.getConstant());
        if (message.getSticker() != null)
            sendMsg.execute(message, Constant.MESSAGE_PROCESSING_STICKER.getConstant());
        if (message.getAudio() != null) {
            try {
                sendMsg.execute (message,write.writeMusic(message.getAudio().getFileId()));
            } catch (IOException e) {
                log.error(ConstantError.SEND_MUSIC_USER.gerError(),e);
            }
        }
        if (message.getPhoto() != null)
            sendMsg.execute(message, Constant.MESSAGE_PROCESSING_PICTURES.getConstant());
        if (message.getVideo() != null)
            sendMsg.execute(message, Constant.MESSAGE_PROCESSING_VIDEO.getConstant());
        if (message.getVoice() != null)
            sendMsg.execute(message, Constant.MESSAGE_PROCESSING_VOICE.getConstant());
        if (message.hasText()) {
            record.consoleRecordingDeadline(message);
        }
    }
}
