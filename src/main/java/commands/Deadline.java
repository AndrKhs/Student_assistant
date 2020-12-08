package commands;

import botAbility.FunctionsBot.BotLogic.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class Deadline extends Command{
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
        log.info(message.getFrom().getUserName()+ " request Deadline");
        String idUser = message.getFrom().getId().toString();
        try {
            request.writeRequest(idUser, Commands.Back, "");
            if (!getGroupList.searchGroup().equals("\nСписок акакдемических групп пуст")) {
                request.writeRequest(idUser, Commands.Deadline, "");
                send.sendMsg(message, getGroupList.searchGroup());
                send.sendMsg(message, "Напишите мне существующую академическую группу");
            }
            else send.sendMsg(message, "Дедлайнов нет");
        } catch (IOException e) {
            log.error("Ошибка выполнения команды Deadline",e);
        }
        return null;
    }
}
