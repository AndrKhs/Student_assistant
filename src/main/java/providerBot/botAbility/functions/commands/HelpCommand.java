package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.functions.send.SendMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Класс команды помощи
 */
public class HelpCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(HelpCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Метод для ответа пользователю на команду "Помощь"
     * @param message
     */
    @Override
    public void execute(Message message) {
        log.info(message.getFrom().getUserName()+ " request Help");
        StringBuilder sb = new StringBuilder();
        sb.append(Constant.HELP_FIRST.getConstant())
                .append(Constant.HELP_SECOND.getConstant())
                .append(Constant.HELP_THIRD.getConstant())
                .append(Constant.HELP_FOURTH.getConstant())
                .append(Constant.HELP_FIFTH.getConstant())
                .append(Constant.HELP_SIXTH.getConstant())
                .append(Constant.HELP_SEVENTH.getConstant());
        sendMsg.execute(message, sb.toString());
        sb.delete(0, sb.length());
        return;
    }
}
