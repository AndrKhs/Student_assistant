package commands;

import botAbility.FunctionsBot.BotLogic.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class DeleteDeadline extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteDeadline.class);

    /**
     * Метод для удаления конкретного дедлайна
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        log.info(message.getFrom().getUserName() + " request Delete");
        String idUser = message.getFrom().getId().toString();
        try {
            request.writeRequest(idUser, Commands.Back, "");
            request.writeRequest(idUser, Commands.Delete, "");
            send.sendMsg(message, getGroupList.searchGroup());
            send.sendMsg(message, "Напишите мне существующую академическую группу");
        } catch (IOException e) {
            log.error("Ошибка выполнения команды DeleteDeadline",e);
        }
        return null;
    }
}
