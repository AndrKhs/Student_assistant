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
        log.info(message.getFrom().getUserName()+ " request Deadline");
        String idUser = message.getFrom().getId().toString();
        try {
            if (!getGroupList.searchGroup().equals("\nСписок акакдемических групп пуст")) {
                send.sendMsg(message, getGroupList.searchGroup());
                send.sendMsg(message, "Напишите мне существующую академическую группу");
                request.writeRequest(idUser, Commands.Back, "");
                request.writeRequest(idUser, Commands.Deadline, "");
            }
            else send.sendMsg(message, "Дедлайнов нет");
        } catch (IOException e) {
            log.error("Ошибка выполнения команды Deadline",e);
        }
        return null;
    }
}
