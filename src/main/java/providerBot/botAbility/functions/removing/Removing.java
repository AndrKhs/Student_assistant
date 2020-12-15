package providerBot.botAbility.functions.removing;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.essences.EndDate;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.constants.CommandsEnum;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для удаления файлов
 */
public class Removing implements IRemoving{
    /**
     * Константа для отправки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();

    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();

    @Override
    public void deleteRequest(String userId, CommandsEnum command) {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        File file = new File(sb.toString());
        if(file.exists() && !file.isDirectory())
            if(file.delete())return;
        return;
    }

    @Override
    public void deleteAllRequest(Message message) {
        String idUser = message.getFrom().getId().toString();
        for (CommandsEnum s : CommandsEnum.values()) { deleteRequest(idUser,s);}
    }

    @Override
    public void groupDelete(Message message, String action, String academyGroup){
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        String words = message.getText();
        if(action.equals("date")) words = academyGroup;
        String[] decryption = words.split("-");
        if(decryption.length == 2){
            String GroupCode = decryption[0];
            String GroupNumber = decryption[1];
            if (GroupCode.length() < 4 && GroupCode.length() > 1 && GroupNumber.length() == 6) {
                Path path = Paths.get(sb.toString());
                if (Files.exists(path)) {
                    File group = new File(sb.toString());
                    for (String s : group.list()) {
                        Object[] groups = s.split("_");
                        if(action.equals("date") && groups[0].equals(academyGroup) && groups[1].equals(message.getText())){
                            File file = new File(sb.append(s).toString());
                            if (file.delete()) {
                                sendMsg.execute(message,Constant.REMOVE.getConstant());
                                return;
                            }
                        }
                        if (groups[0].equals(message.getText())) {
                            File file = new File(sb.append(s).toString());
                            if (file.delete()) {
                                sendMsg.execute(message,Constant.REMOVE.getConstant());
                                return;
                            }
                        }
                    }
                }
            }
            else sendMsg.execute(message, ConstantError.INVALID_GROUP_INPUT.gerError());
        }
        else sendMsg.execute(message, ConstantError.INVALID_GROUP_INPUT.gerError());
    }

    @Override
    public String deleteDeadline(EndDate date, Group group, String message) {
        sb.setLength(0);
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(group.group)
                .append("_")
                .append(date.Date)
                .append("_")
                .append(message);
        File file = new File(builder.toString());
        if (file.delete()) {
            sb.append(Constant.REMOVE_DEADLINE.getConstant());
        } else {
            sb.append(ConstantError.NO_DEADLINE.gerError());
        }
        return sb.toString();
    }
}
