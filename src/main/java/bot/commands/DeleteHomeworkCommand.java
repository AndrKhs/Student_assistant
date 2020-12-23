package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.AppCommands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.model.User;

import java.io.IOException;

/**
 * Класс команды удаления дедалайна
 */
public class DeleteHomeworkCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteHomeworkCommand.class);
    /**
     * Метод для удаления конкретного дедлайна
     * @param message
     */
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + " request Delete");
        try {
            write.writeUserState(message, AppCommands.Back, "", userDate);
            write.writeGroup(message, AppCommands.GroupDelete, userDate);
            messageSender.execute(message, searcher.findDate(userDate.getGroup().group));
            messageSender.execute(message, AppConstants.WRITE_DATE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE_DEADLINE.toStringValue(),e);
        }
        return;
    }
}
