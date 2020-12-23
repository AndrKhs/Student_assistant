package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.Commands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;

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
    public void execute(Message message) {
        log.info(message.getFrom().getUserName() + " request DeleteWholeDate");
        try {
            write.writeUserState(message, Commands.Back, "");
            write.writeUserState(message, Commands.DeleteWholeDate, "");
            sendMsg.execute(message, searcher.findGroup());
            sendMsg.execute(message, AppConstants.WRITE_GROUP_DEADLINE_DELETE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE_WHOLE_DEADLINE.toStringValue(), e);
        }
        return;
    }
}
