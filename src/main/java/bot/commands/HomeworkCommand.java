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
 * Класс команды вывода домашнего задания
 */
public class HomeworkCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(HomeworkCommand.class);
    /**
     * Метод для начала диалога с пользователем по просмотру дедлайна
     * @param message
     */
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName()+ " request Deadline");
        try {
            write.writeUserState(message, AppCommands.Back, "", userDate);
            write.writeGroup(message, AppCommands.Deadline, userDate);
            messageSender.execute(message, searcher.findDate(userDate.getGroup().group));
            messageSender.execute(message, AppConstants.WRITE_DATE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DEADLINE.toStringValue(),e);
        }
        return;
    }
}
