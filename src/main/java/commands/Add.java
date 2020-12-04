package commands;

import botAbility.FunctionsBot.BotAPI.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class Add extends Command{
    private static final Logger log = LogManager.getLogger(Add.class);
    @Override
    public String execute(Message message) {
        String idUser = message.getFrom().getId().toString();
        try {
            request.writeRequest(idUser, Commands.Add.toString(), "");
            send.sendMsg(message, getGroupList.searchGroup());
            send.sendMsg(message, "Напишите мне существующую академическую группу");
        } catch (IOException e) {
            log.error(e);
        }
        return null;
    }
}
