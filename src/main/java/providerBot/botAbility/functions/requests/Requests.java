package providerBot.botAbility.functions.requests;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.seekers.ISeekers;
import providerBot.botAbility.functions.seekers.Seekers;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.constants.CommandsEnum;
import providerBot.botAbility.functions.readers.Readers;
import providerBot.botAbility.functions.writers.Writers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;

/**
 * Класс для обработки состояния пользователя
 */
public class Requests implements IRequests {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Requests.class);
    /**
     * Набор для работы с файлами
     */
    private final Writers write = new Writers();
    private final Readers read = new Readers();
    /**
     * Константа для отправки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();

    /**
     * Нужна для получении листа групп
     */
    private final ISeekers getGroupList = new Seekers();


    @Override
    public void requestGroup(CommandsEnum command, Message message) {
        String mes = message.getText();
        String idUser = message.getFrom().getId().toString();
        String[] number = mes.split("-");
        if(number.length == 2){
            String lenOne = number[0];
            String lenTwo = number[1];
            try {
                if (lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    write.writeGroup(idUser,command,mes);
                    sendMsg.execute(message, getGroupList.searchDate(mes));
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

    @Override
    public void requestDeadline(CommandsEnum dateEnum, CommandsEnum groupEnum , CommandsEnum requestUser, Message message) {
        String mes = message.getText();
        String idUser = message.getFrom().getId().toString();
        try {
            Group group = read.readGroup(idUser, groupEnum);
            try {
                write.writeDate(idUser, dateEnum, mes);
            } catch (IOException e) {
                log.error(e.toString());
            }
            sendMsg.execute(message, getGroupList.searchDiscipline(group.group, mes));
            sendMsg.execute(message, Constant.WRITE_DISCIPLINE.getConstant());
        } catch (IOException e) {
            log.error(ConstantError.CREATED_DATE.gerError(),e);
        }
    }
}
