package commands;

import botAbility.FunctionsBot.BotLogic.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class Delete extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Delete.class);

    /**
     * Метод для начала диалога с пользователем по удалению дедлайна
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        log.info(message.getFrom().getUserName() + " request Delete");
        String idUser = message.getFrom().getId().toString();
        try {
            request.writeRequest(idUser, Commands.Back, "");
            request.writeRequest(idUser, Commands.CommandDelete, "");
            send.sendMsg(message, "Выберите способ удаление");
        } catch (IOException e) {
            log.error("Ошибка выполнения команды Delete", e);
        }
        return null;
    }
}
