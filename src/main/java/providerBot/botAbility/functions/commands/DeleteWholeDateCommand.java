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
 * Класс команды удаление даты дедалайна
 */
public class DeleteWholeDateCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteWholeDateCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Метод нужен для удалении дедлайна по дате
     * @param message
     */
    @Override
    public void execute(Message message) {
        Writer write = new Writer();
        log.info(message.getFrom().getUserName() + " request DeleteWholeDate");
        try {
            write.request(message, Commands.Back, "");
            write.request(message, Commands.DeleteWholeDate, "");
            sendMsg.execute(message, Constant.WRITE_GROUP_DEADLINE_DELETE.getConstant());
        } catch (IOException e) {
            log.error(ConstantError.COMMAND_DELETE_WHOLE_DEADLINE.gerError(), e);
        }
        return;
    }
}
