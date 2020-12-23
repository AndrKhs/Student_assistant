package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.AppConstants;

/**
 * Класс команды назад
 */
public class BackCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeadlineCommand.class);
    /**
     * Метод для начала диалога с пользователем по просмотру дедлайна
     * @param message
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName()+ " request back");
            sendMsg.execute(message, AppConstants.MOVE_BACK.toStringValue());
        return;
    }
}
