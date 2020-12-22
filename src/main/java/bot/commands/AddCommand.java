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
 * Класс команды добавления дедлайна
 */
public class AddCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(AddCommand.class);
    /**
     * Метод для начала диалога с пользователем по добавлению дедлайна
     * @param message
     */
    @Override
    public void execute(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + " request Add");
        try {
            write.writeUserState(message, AppCommands.Back, "", userDate);
            write.writeGroup(message, AppCommands.Add, userDate);
            sendMsg.execute(message, searcher.findDate(userDate.getGroup().group));
            sendMsg.execute(message, AppConstants.WRITE_DATE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_ADD.toStringValue(), e);
        }
        return;
    }
}
