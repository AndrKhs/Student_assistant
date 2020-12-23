package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.Commands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;

import java.io.IOException;

/**
 * Класс команды удаления группы
 */
public class DeleteGroupCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteGroupCommand.class);
    /**
     * Метод нужен для удалении всех дедлайнов группы
     * @param message
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName() + " request DeleteGroup");
        try {
            write.writeUserState(message, Commands.Back, "");
            write.writeUserState(message, Commands.DeleteGroupFirst, "");
            sendMsg.execute(message, searcher.findGroup());
            sendMsg.execute(message, AppConstants.WRITE_GROPE_DELETE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE_GROUP.toStringValue(), e);
        }
        return;
    }
}
