package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.AppErrorConstants;
import bot.model.User;
import bot.sender.MusicSender;

/**
 * Класс команды случайная музыка
 */
public class RandomMusicCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(RandomMusicCommand.class);
    /**
     * Константа для отпавки сообщения
     */
    private final MusicSender sendAudio = new MusicSender();
    /**
     * Метод для вывода рандомной музыки на команду "Слуйчаная музыка" от пользователя
     * @param message
     */
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + " request RandomMusic");
        try {
            sendAudio.execute(message, "");
        } catch (Exception e) {
            log.error(AppErrorConstants.COMMAND_RANDOM_MUSIC.toStringValue(),e);
        }
        return;
    }
}
