package bot.commands;

import bot.constants.AppCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.model.User;

import java.io.IOException;

/**
 * Класс команды удаление даты дедалайна
 */
public class DeleteDateCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteDateCommand.class);
    /**
     * Метод нужен для удалении дедлайна по дате
     * @param message
     */
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + " request DeleteWholeDate");
        try {
            write.writeUserState(message, AppCommands.Back, "", userDate);
            write.writeGroup(message, AppCommands.DeleteWholeDate, userDate);
            messageSender.execute(message, searcher.findDate(userDate.getGroup().group));
            messageSender.execute(message, AppConstants.WRITE_DATE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE_WHOLE_DEADLINE.toStringValue(), e);
        }
        return;
    }
}
