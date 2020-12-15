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
 * Класс команды выбора способа удаления
 */
public class DeleteCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Метод для начала диалога с пользователем по удалению дедлайна
     * @param message
     */
    @Override
    public void execute(Message message) {
        Writers write = new Writers();
        log.info(message.getFrom().getUserName() + " request Delete");
        String idUser = message.getFrom().getId().toString();
        try {
            write.writeRequest(idUser, CommandsEnum.Back, "");
            write.writeRequest(idUser, CommandsEnum.CommandDelete, "");
            sendMsg.execute(message, Constant.CHOICE_REMOVE.getConstant());
        } catch (IOException e) {
            log.error(ConstantError.COMMAND_DELETE.gerError(), e);
        }
        return;
    }
}
