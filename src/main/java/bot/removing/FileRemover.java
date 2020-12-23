package bot.removing;

import bot.constants.AppCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.model.User;
import bot.sender.MessageSender;
import bot.model.Group;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;

/**
 * Класс для удаления файлов
 */
public class FileRemover implements IFileRemover {

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(FileRemover.class);
    /**
     * Константа для отправки сообщения
     */
    private final MessageSender messageSender = new MessageSender();

    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void group(Message message, User userDate) {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir"))
                .append("\\Files\\");
        File file = new File(sb.toString());
        try {
            for (String path : file.list()) {
                File fileHomeWork = new File(sb.append(path).toString());
                if (fileHomeWork.delete()) {
                    messageSender.execute(message, AppConstants.REMOVE.toStringValue());
                    allUserState(message, userDate);
                    return;
                } else messageSender.execute(message, AppErrorConstants.INVALID_GROUP_INPUT.toStringValue());

            }
        } catch (NullPointerException e) {
            log.error("Error not find file", e);
        }
    }

    @Override
    public String homeWork(String date, Group group, String message) {
        sb.setLength(0);
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(group.group)
                .append("_")
                .append(date)
                .append("_")
                .append(message);
        File file = new File(builder.toString());
        if (file.delete()) {
            sb.append(AppConstants.REMOVE_HOMEWORK.toStringValue());
        } else {
            sb.append(AppErrorConstants.NO_DEADLINE.toStringValue());
        }
        return sb.toString();
    }

    @Override
    public void userState(Message message, AppCommands command, User userDate) {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir"))
                .append("\\TimeDataBase\\")
                .append(userDate.getId())
                .append(command);
        File file = new File(sb.toString());
        if (file.exists() && !file.isDirectory())
            file.delete();
    }

    @Override
    public void allUserState(Message message, User userDate) {
        for (AppCommands s : AppCommands.values()) {
            userState(message, s, userDate);
        }
    }
}
