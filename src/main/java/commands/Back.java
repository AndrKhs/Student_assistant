package commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;


public class Back extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Deadline.class);

    /**
     * Метод для начала диалога с пользователем по просмотру дедлайна
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        log.info(message.getFrom().getUserName()+ " request back");
            send.sendMsg(message, "Вернулся назад");
        return null;
    }
}
