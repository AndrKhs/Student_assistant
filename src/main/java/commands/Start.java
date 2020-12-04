package commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public class Start extends Command {
    /**
     * Метод для ответа пользователю на команду /start
     * @param message - Сообщение пользователя
     * @return null
     */
    @Override
    public String execute( Message message) {
        StringBuilder sb = new StringBuilder();
        sb.append("Привет ").append(message.getFrom().getFirstName())
                .append(", можешь обратиться ко мне за помощью!").append("\n")
                .append("\"Помощь\" - список команд");
        send.sendMsg(message, sb.toString());
        return null;
    }
}
