package commands;

import bot.Bot;
import bot.BotMain;
import bot.Com;
import botAbility.Consol.RequestConsol;
import botAbility.Consol.botConsol;
import botAbility.FunctionsBot.BotFunctions;
import botAbility.FunctionsBot.DateBaseBot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class Deadline extends Command{

    private final static Logger log = Logger.getLogger(Deadline.class);
    @Override
    public String execute(Message message) {
        BotMain send = new Bot();
        botConsol request = new RequestConsol();
        BotFunctions getGroupList = new DateBaseBot();
        String idUser = message.getFrom().getId().toString();
        try {
            request.writeRequest(idUser, Com.Deadline.toString(), "");
            send.sendMsg(message, getGroupList.searchGroup());
            send.sendMsg(message, "Напишите мне существующую академическую группу");
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        return null;
    }
}
