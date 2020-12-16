package providerBot.botAbility.functions.UserState;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.essences.User;
import providerBot.botAbility.functions.seekers.ISearch;
import providerBot.botAbility.functions.seekers.Search;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.constants.Commands;
import providerBot.botAbility.functions.readers.Reader;
import providerBot.botAbility.functions.writers.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;

/**
 * Класс для обработки состояния пользователя
 */
public class UserState implements IUserState {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(UserState.class);
    /**
     * Набор для работы с файлами
     */
    private final Writer write = new Writer();
    private final Reader read = new Reader();
    /**
     * Константа для отправки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();

    /**
     * Нужна для получении листа групп
     */
    private final ISearch getGroupList = new Search();


    @Override
    public void getGroup(Commands command, Message message) {
        String mes = message.getText();
        User user = new User(message);
        String[] number = mes.split("-");
        if(number.length == 2){
            String lenOne = number[0];
            String lenTwo = number[1];
            try {
                if (lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    write.group(message,command,mes);
                    sendMsg.execute(message, getGroupList.findDate(mes));
                    sendMsg.execute(message, Constant.WRITE_DATE_DEADLINE.getConstant());
                }
            } catch (IOException e) {
                log.error(ConstantError.CREATED_GROUP.gerError(),e);
            }
        }
        else {
            sendMsg.execute(message, ConstantError.INVALID_GROUP_INPUT.gerError());
        }
    }

    public void getDeadline(Commands dateEnum, Commands groupEnum , Commands requestUser, Message message) {
        String mes = message.getText();
        User user = new User(message);
        try {
            Group group = read.group(user.id, groupEnum);
            try {
                write.date(message, dateEnum, mes);
            } catch (IOException e) {
                log.error(e.toString());
            }
            sendMsg.execute(message, getGroupList.findDiscipline(group.group, mes));
            sendMsg.execute(message, Constant.WRITE_DISCIPLINE.getConstant());
        } catch (IOException e) {
            log.error(ConstantError.CREATED_DATE.gerError(),e);
        }
    }
}
