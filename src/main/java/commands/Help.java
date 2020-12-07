package commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

public class Help extends Command{
    private static final Logger log = LoggerFactory.getLogger(Help.class);
    /**
     * Метод для ответа пользователю на команду "Помощь"
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
        log.info(message.getFrom().getUserName()+ " request Help");
        StringBuilder sb = new StringBuilder();
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
