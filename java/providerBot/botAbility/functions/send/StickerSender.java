package bot.sender;

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
        String stickerTail ="CAACAgIAAxkBAAJLQF_Qvw7iE9NWhipkim0pJYn5rK5oAAIBAAMrElolU6yx4uot2oweBA" ;
        String stickerHead = "CAACAgIAAxkBAAJLP1_QvvGqv6_d8Nd9e1PAalMwEugGAAICAAMrElolHaELi66oTuQeBA";
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(message.getChatId().toString());
        sendSticker.setSticker(text.equals("tail")?stickerTail:stickerHead);
        try {
            execute(sendSticker);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки музыки", e);
        }
    }
}
