package commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileNotFoundException;

public class RandomMusic extends Command {
    private static final Logger log = LoggerFactory.getLogger(RandomMusic.class);

    /**
     * Метод для вывода рандомной музыки на команду "Слуйчаная музыка" от пользователя
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        log.info(message.getFrom().getUserName() + " request RandomMusic");
        try {
            send.sendAudio(message);
        } catch (FileNotFoundException e) {
            log.error("Ошибка выполнения команды RandomMusic",e);
        }
        return null;
    }
}
