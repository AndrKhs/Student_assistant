package commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public class Help extends Command{
    /**
     * Метод для ответа пользователю на команду "Помощь"
     * @param message
     * @return
     */
    @Override
    public String execute(Message message) {
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
