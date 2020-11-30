package commands;

import bot.Bot;
import bot.BotMain;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileNotFoundException;

public class RandomMusic extends Command {
    private final static Logger log = Logger.getLogger(RandomMusic.class);
    @Override
    public String execute(Message message) {
        BotMain send = new Bot();
        try {
            send.sendAudio(message);
        } catch (FileNotFoundException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }
}
