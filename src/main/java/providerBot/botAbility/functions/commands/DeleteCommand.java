package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.functions.writers.Writer;
import providerBot.botAbility.constants.Commands;
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
        Writer write = new Writer();
        log.info(message.getFrom().getUserName() + " request Delete");
        try {
            write.request(message, Commands.Back, "");
            write.request(message, Commands.CommandDelete, "");
            sendMsg.execute(message, Constant.CHOICE_REMOVE.getConstant());
        } catch (IOException e) {
            log.error(ConstantError.COMMAND_DELETE.gerError(), e);
        }
        return;
    }
}
