package bot.sender;

import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StickerSender extends Send{
    /**
     * Константа логирования
     */
    private static final Logger log = LoggerFactory.getLogger(MusicSender.class);
    @Override
    public void execute(Message message, String text) {
        log.info("Send Sticker " + message.getFrom().getUserName());
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(message.getChatId().toString());
        sendSticker
                .setSticker(text.equals("tail")
                ?AppConstants.STICKER_TAIL.toStringValue()
                :AppConstants.STICKER_HEAD.toStringValue());
        try {
            execute(sendSticker);
        } catch (TelegramApiException e) {
            log.error(AppErrorConstants.SEND_STICKER.toStringValue(), e);
        }
    }
}
