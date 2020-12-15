package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.functions.send.SendMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Класс команды старт
 */
public class StartCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(StartCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Метод для ответа пользователю на команду /start
     * @param message - Сообщение пользователя
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName() + " request /start");
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.START_SECOND.getConstant()).append(message.getFrom().getFirstName())
                .append(Constant.START_FIRST.getConstant());
        sendMsg.execute(message, sb.toString());
        return;
    }
}
