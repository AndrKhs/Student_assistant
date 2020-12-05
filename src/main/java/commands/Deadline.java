package commands;

import botAbility.FunctionsBot.BotAPI.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class Deadline extends Command{
    private static final Logger log = LoggerFactory.getLogger(Deadline.class);

    /**
     * Метод для начала диалога с пользователем по просмотру дедлайна
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        String idUser = message.getFrom().getId().toString();
        try {
            request.writeRequest(idUser, Commands.Deadline.toString(), "");
            send.sendMsg(message, getGroupList.searchGroup());
            send.sendMsg(message, "Напишите мне существующую академическую группу");
        } catch (IOException e) {
            log.error(e.toString());
        }
        return null;
    }
}
