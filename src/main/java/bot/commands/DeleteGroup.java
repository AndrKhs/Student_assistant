package bot.commands;

import bot.constants.AppCommands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.User;
import bot.removing.FileRemover;
import bot.removing.IFileRemover;

import java.io.File;
import java.io.IOException;

public class DeleteGroup extends Command{

    private final IFileRemover fileRemover = new FileRemover();
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
            write.writeGroup(message, AppCommands.DeleteGroup, userDate);
            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("user.dir")).append("\\user\\").append(userDate.getId());
            fileRemover.allUserState(message, userDate);
            File user = new File(sb.toString());
            user.delete();
            sendMsg.execute(message, AppConstants.RE_ENTER.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_DELETE_DEADLINE.toStringValue(),e);
        }
        return;
    }
}
