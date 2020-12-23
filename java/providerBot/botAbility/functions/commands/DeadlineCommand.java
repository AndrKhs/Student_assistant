package bot.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.Commands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;

import java.io.IOException;

/**
 * Класс команды вывода домашнего задания
 */
public class DeadlineCommand extends Command{
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
        log.info(message.getFrom().getUserName()+ " request Deadline");
        try {
            write.writeUserState(message, Commands.Back, "");
            if (!searcher.findGroup().equals("\nСписок акакдемических групп пуст")) {
                write.writeUserState(message, Commands.Deadline, "");
                sendMsg.execute(message, searcher.findGroup());
                sendMsg.execute(message, AppConstants.WRITE_EXISTS_GROUP.toStringValue());
            }
            else sendMsg.execute(message, "Дедлайнов нет");
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DEADLINE.toStringValue(),e);
        }
        return;
    }
}
