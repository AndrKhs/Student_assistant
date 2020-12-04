package commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileNotFoundException;

public class RandomMusic extends Command {
    static final Logger log = LogManager.getLogger();

    /**
     * Метод для вывода рандомной музыки на команду "Слуйчаная музыка" от пользователя
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        try {
            send.sendAudio(message);
        } catch (FileNotFoundException e) {
            log.error(e);
        }
        return null;
    }
}
