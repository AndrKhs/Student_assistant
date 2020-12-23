package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.AppConstants;

/**
 * Класс команды старт
 */
public class StartCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(StartCommand.class);
    /**
     * Метод для ответа пользователю на команду /start
     * @param message - Сообщение пользователя
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName() + " request /start");
        StringBuilder sb = new StringBuilder();
        sb.append(AppConstants.START_FIRST.toStringValue())
                .append(message.getFrom().getFirstName()).append(AppConstants.START_SECOND.toStringValue());
        sendMsg.execute(message, sb.toString());
        return;
    }
}
