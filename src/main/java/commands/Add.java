package commands;

import botAbility.FunctionsBot.BotLogic.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class Add extends Command{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Add.class);

    /**
     * Метод для начала диалога с пользователем по добавлению дедлайна
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        String idUser = message.getFrom().getId().toString();
        log.info(message.getFrom().getUserName() + " request Add");
        try {
            request.writeRequest(idUser, Commands.Back, "");
            request.writeRequest(idUser, Commands.Add, "");
            send.sendMsg(message, getGroupList.searchGroup());
            send.sendMsg(message, "Напишите мне существующую академическую группу");
        } catch (IOException e) {
            log.error("Ошибка выполнения команды Add", e);
        }
        return null;
    }
}
