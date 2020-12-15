package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.functions.send.SendMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Класс команды назад
 */
public class BackCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeadlineCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Метод для начала диалога с пользователем по просмотру дедлайна
     * @param message
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName()+ " request back");
            sendMsg.execute(message, Constant.MOVE_BACK.getConstant());
        return;
    }
}
