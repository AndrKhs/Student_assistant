package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.Commands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;

import java.io.IOException;

/**
 * Класс команды выбора способа удаления
 */
public class DeleteAllCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteAllCommand.class);

    /**
     * Метод для начала диалога с пользователем по удалению дедлайна
     * @param message
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName() + " request Delete");
        try {
            write.writeUserState(message, Commands.Back, "");
            write.writeUserState(message, Commands.CommandDelete, "");
            sendMsg.execute(message, AppConstants.CHOICE_REMOVE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE.toStringValue(), e);
        }
        return;
    }
}
