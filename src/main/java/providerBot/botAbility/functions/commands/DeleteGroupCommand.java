package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.functions.writers.Writers;
import providerBot.botAbility.constants.CommandsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

/**
 * Класс команды удаления группы
 */
public class DeleteGroupCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteGroupCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Метод нужен для удалении всех дедлайнов группы
     * @param message
     */
    @Override
    public void execute(Message message) {
        Writers write = new Writers();
        String idUser = message.getFrom().getId().toString();
        log.info(message.getFrom().getUserName() + " request DeleteGroup");
        try {
            write.writeRequest(idUser, CommandsEnum.Back, "");
            write.writeRequest(idUser, CommandsEnum.DeleteGroup, "");
            sendMsg.execute(message, Constant.WRITE_GROPE_DELETE.getConstant());
        } catch (IOException e) {
            log.error(ConstantError.COMMAND_DELETE_GROUP.gerError(), e);
        }
        return;
    }
}
