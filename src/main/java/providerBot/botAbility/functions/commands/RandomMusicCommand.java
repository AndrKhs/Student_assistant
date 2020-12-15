package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.send.SendMusic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Класс команды случайная музыка
 */
public class RandomMusicCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(RandomMusicCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMusic sendAudio = new SendMusic();
    /**
     * Метод для вывода рандомной музыки на команду "Слуйчаная музыка" от пользователя
     * @param message
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName() + " request RandomMusic");
        try {
            sendAudio.execute(message, "");
        } catch (Exception e) {
            log.error(ConstantError.COMMAND_RANDOM_MUSIC.gerError(),e);
        }
        return;
    }
}
