package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.Commands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;

import java.io.IOException;

/**
 * Класс команды удаления дедалайна
 */
public class DeleteDeadlineCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteDeadlineCommand.class);
    /**
     * Метод для удаления конкретного дедлайна
     * @param message
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName() + " request Delete");
        try {
            write.writeUserState(message, Commands.Back, "");
            write.writeUserState(message, Commands.Delete, "");
            sendMsg.execute(message, searcher.findGroup());
            sendMsg.execute(message, AppConstants.WRITE_EXISTS_GROUP.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE_DEADLINE.toStringValue(),e);
        }
        return;
    }
}
