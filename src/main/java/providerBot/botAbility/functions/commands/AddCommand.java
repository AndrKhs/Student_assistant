package providerBot.botAbility.functions.commands;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.functions.seekers.ISearch;
import providerBot.botAbility.functions.writers.Writer;
import providerBot.botAbility.constants.Commands;
import providerBot.botAbility.functions.seekers.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

/**
 * Класс команды добавления дедлайна
 */
public class AddCommand extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(AddCommand.class);
    /**
     * Нужна для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();
    /**
     * Нужна для получении листа групп
     */
    private final ISearch getGroupList = new Search();
    /**
     * Метод для начала диалога с пользователем по добавлению дедлайна
     * @param message
     */
    @Override
    public void execute(Message message) {
        Writer write = new Writer();
        log.info(message.getFrom().getUserName() + " request Add");
        try {
            write.request(message, Commands.Back, "");
            write.request(message, Commands.Add, "");
            sendMsg.execute(message, getGroupList.findGroup());
            sendMsg.execute(message, Constant.WRITE_EXISTS_GROUP.getConstant());
        } catch (IOException e) {
            log.error(ConstantError.COMMAND_ADD.gerError(), e);
        }
        return;
    }
}
