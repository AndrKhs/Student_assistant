package commands;

import botAbility.FunctionsBot.BotLogic.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class DeleteGroup extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(DeleteGroup.class);

    /**
     * Метод нужен для удалении всех дедлайнов группы
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        String idUser = message.getFrom().getId().toString();
        log.info(message.getFrom().getUserName() + " request DeleteGroup");
        try {
            request.writeRequest(idUser, Commands.Back, "");
            request.writeRequest(idUser, Commands.DeleteGroup, "");
            send.sendMsg(message, "Напишите мне группу которую хотите удалить");
        } catch (IOException e) {
            log.error("Ошибка выполнения команды DeleteGroup", e);
        }
        return null;
    }
}
