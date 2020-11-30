package commands;

import bot.Bot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.BotMain;

public class Help extends Command{
    @Override
    public String execute(Message message) {
        StringBuilder sb = new StringBuilder();
        BotMain send = new Bot();
        sb.append("\"Помощь\" - список команд")
                .append("\n")
                .append("\"Дедлайн\" - cписок дедлайнов")
                .append("\n")
                .append("\"Удалить\" - принудительно удаляет дедлайн")
                .append("\n")
                .append("\"Добавить\" - добавить дедлайн и домашнее задание")
                .append("\n")
                .append("\"Случайная музыка\" - рандомная музыка из плейлиста бота")
                .append("\n")
                .append("Отправьте музыку боту, чтобы он сохранил в свой плейлист");
        send.sendMsg(message, sb.toString());
        sb.delete(0, sb.length());
        return null;
    }
}
