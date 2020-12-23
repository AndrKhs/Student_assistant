package providerBot.botAbility.functions.UserState;

import bot.checks.validate.IValidator;
import bot.checks.validate.Validator;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.model.IdUser;
import bot.seekers.ISearch;
import bot.seekers.Search;
import bot.sender.MessageSender;
import bot.model.Group;
import bot.constants.Commands;
import bot.readers.Reader;
import bot.writers.Writer;
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
    private final MessageSender sendMsg = new MessageSender();

    /**
     * Нужна для получении листа групп
     */
    private final ISearch search = new Search();
    /**
     * Константа для проверки
     */
    private final IValidator validate = new Validator();


    @Override
    public void getGroup(Commands command, Message message) {
        String mes = message.getText();
        IdUser user = new IdUser(message);
        String[] number = mes.split("-");
        if(number.length == 2){
            String lenOne = number[0];
            String lenTwo = number[1];
            try {
                if (lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    write.writeGroup(message,command,mes);
                    if(!validate.isExist(user.id, Commands.DeleteGroupFirst)){
                        sendMsg.execute(message, search.findDate(mes));
                        sendMsg.execute(message, AppConstants.WRITE_DATE_DEADLINE.toStringValue());
                    }else sendMsg.execute(message, "Напишите группу еще раз для подтверждения");
                }
            } catch (IOException e) {
                log.error(AppErrorConstants.CREATED_GROUP.toStringValue(),e);
            }
        }
        else {
            sendMsg.execute(message, AppErrorConstants.INVALID_GROUP_INPUT.toStringValue());
        }
    }

    public void getDeadline(Commands dateEnum, Commands groupEnum , Commands requestUser, Message message) {
        String mes = message.getText();
        IdUser user = new IdUser(message);
        try {
            Group group = read.readGroup(user.id, groupEnum);
            try {
                write.writeDate(message, dateEnum, mes);
            } catch (IOException e) {
                log.error(e.toString());
            }
            sendMsg.execute(message, search.findDiscipline(group.group, mes));
            sendMsg.execute(message, AppConstants.WRITE_DISCIPLINE.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.CREATED_DATE.toStringValue(),e);
        }
    }
}
