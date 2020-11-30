package commands;

import bot.Bot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.BotMain;

public class Start extends Command {
    @Override
    public String execute( Message message) {
        StringBuilder sb = new StringBuilder();
        BotMain send = new Bot();
        sb.append("Привет ").append(message.getFrom().getFirstName())
                .append(", можешь обратиться ко мне за помощью!").append("\n")
                .append("\"Помощь\" - список команд");
        send.sendMsg(message, sb.toString());
        return null;
    }
}
