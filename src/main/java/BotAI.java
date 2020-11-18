import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface BotAI {
    /**
     * Нужен для обращении к методам работы бота
     * @param message сообщение пользовается обратившийся к пользователю
     * @return
     */
    String consoleRecordingDeadline(Message message);

    void setButtons(SendMessage sendMessage);

    String deadLine(Message message);
}
