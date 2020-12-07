package commands;

import botAbility.FunctionsBot.BotAPI.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;


public class DeleteWholeDate extends Command{
    private static final Logger log = LoggerFactory.getLogger(DeleteWholeDate.class);
    @Override
    public String execute(Message message) {
        String idUser = message.getFrom().getId().toString();
        log.info(message.getFrom().getUserName() + " request DeleteWholeDate");
        try {
            request.writeRequest(idUser, Commands.Back, "");
            request.writeRequest(idUser, Commands.DeleteWholeDate, "");
            send.sendMsg(message, "Напишите мне группу, дедлайн в которой вы хотите удалить");
        } catch (IOException e) {
            log.error("Ошибка выполнения команды DeleteWholeDate", e);
        }
        return null;
    }
}
