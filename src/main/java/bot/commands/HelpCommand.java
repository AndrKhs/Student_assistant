package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.AppConstants;
import bot.model.User;

/**
 * Класс команды помощи
 */
public class HelpCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(HelpCommand.class);
    /**
     * Метод для ответа пользователю на команду "Помощь"
     * @param message
     */
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName()+ " request Help");
        StringBuilder sb = new StringBuilder();
        sb.append(AppConstants.HELP_FIRST.toStringValue())
                .append(AppConstants.HELP_SECOND.toStringValue())
                .append(AppConstants.HELP_THIRD.toStringValue())
                .append(AppConstants.HELP_FOURTH.toStringValue())
                .append(AppConstants.HELP_FIFTH.toStringValue())
                .append(AppConstants.HELP_SIXTH.toStringValue())
                .append(AppConstants.HELP_SEVENTH.toStringValue())
                .append(AppConstants.HELP_EIGHTH.toStringValue())
                .append(AppConstants.HELP_NINTH.toStringValue());
        messageSender.execute(message, sb.toString());
        sb.delete(0, sb.length());
        return;
    }
}
