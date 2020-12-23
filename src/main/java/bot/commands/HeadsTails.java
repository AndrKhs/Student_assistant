package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.User;
import bot.sender.StickerSender;

import java.util.Random;

public class HeadsTails extends Command{
    private static final Logger log = LoggerFactory.getLogger(HeadsTails.class);
    protected final StickerSender sendSticker = new StickerSender();
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + " request HeadsTails");
        Random random = new Random();
        int rand = random.nextInt(2);
        if (rand == 1){
            sendSticker.execute(message, "tail");
            messageSender.execute(message, "Орел");
        }else {
            sendSticker.execute(message, "head");
            messageSender.execute(message, "Решка");
        }
    }
}
