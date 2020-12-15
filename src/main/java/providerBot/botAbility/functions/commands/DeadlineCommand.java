package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.functions.seekers.ISeekers;
import providerBot.botAbility.functions.writers.Writers;
import providerBot.botAbility.constants.CommandsEnum;
import providerBot.botAbility.functions.seekers.Seekers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
/**
 * Класс команды удаления конкретного дедалайна
 */
public class DeadlineCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeadlineCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Нужна для получении листа групп
     */
    private final ISeekers getGroupList = new Seekers();
    /**
     * Метод для начала диалога с пользователем по просмотру дедлайна
     * @param message
     */
    @Override
    public void execute(Message message) {
        Writers write = new Writers();
        log.info(message.getFrom().getUserName()+ " request Deadline");
        String idUser = message.getFrom().getId().toString();
        try {
            write.writeRequest(idUser, CommandsEnum.Back, "");
            if (!getGroupList.searchGroup().equals("\nСписок акакдемических групп пуст")) {
                write.writeRequest(idUser, CommandsEnum.Deadline, "");
                sendMsg.execute(message, getGroupList.searchGroup());
                sendMsg.execute(message, Constant.WRITE_EXISTS_GROUP.getConstant());
            }
            else sendMsg.execute(message, "Дедлайнов нет");
        } catch (IOException e) {
            log.error(ConstantError.COMMAND_DEADLINE.gerError(),e);
        }
        return;
    }
}
