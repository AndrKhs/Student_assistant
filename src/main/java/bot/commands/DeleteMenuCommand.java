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
 * Класс команды выбора способа удаления
 */
public class DeleteMenuCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteMenuCommand.class);

    /**
     * Метод для начала диалога с пользователем по удалению дедлайна
     * @param message
     */
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + " request Delete");
        try {
            write.writeUserState(message, AppCommands.Back, "", userDate);
            write.writeUserState(message, AppCommands.CommandDelete, "", userDate);
            messageSender.execute(message, AppConstants.CHOICE_REMOVE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE.toStringValue(), e);
        }
        return;
    }
}
