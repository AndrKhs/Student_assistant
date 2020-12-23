package bot.removing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.sender.MessageSender;
import bot.model.Group;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
    private final MessageSender sendMsg = new MessageSender();
    /**
     * Константа для удаления состояния пользователя
     */
    private final RemoverUserState userState = new RemoverUserState();

    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void group(Message message, String action, Group group) {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        String words = message.getText();
        if (action.equals("date")) words = group.group;
        String[] decryption = words.split("-");
        if (decryption.length == 2) {
            String GroupCode = decryption[0];
            String GroupNumber = decryption[1];
            if (GroupCode.length() < 4 && GroupCode.length() > 1 && GroupNumber.length() == 6) {
                if (Files.exists(Paths.get(sb.toString()))) {
                    File file = new File(sb.toString());
                    try {
                        for (String path : file.list()) {
                            Object[] groups = path.split("_");
                            if (action.equals("date") & groups[0].equals(group.group) & groups[1].equals(message.getText())) {
                                File fileHomeWork = new File(sb.append(path).toString());
                                if (fileHomeWork.delete()) {
                                    sendMsg.execute(message, AppConstants.REMOVE.toStringValue());
                                    userState.allUserState(message);
                                    return;
                                }
                            }
                            if (groups[0].equals(message.getText())) {
                                File fileHomeWork = new File(sb.append(path).toString());
                                if (fileHomeWork.delete()) {
                                    sendMsg.execute(message, AppConstants.REMOVE.toStringValue());
                                    userState.allUserState(message);
                                    return;
                                }
                            }
                        }
                    } catch (NullPointerException e) {
                        log.error("Error not find file", e);
                    }
                }
            }
        } else sendMsg.execute(message, AppErrorConstants.INVALID_GROUP_INPUT.toStringValue());
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
            sb.append(AppConstants.REMOVE_DEADLINE.toStringValue());
        } else {
            sb.append(AppErrorConstants.NO_DEADLINE.toStringValue());
        }
        return sb.toString();
    }
}
