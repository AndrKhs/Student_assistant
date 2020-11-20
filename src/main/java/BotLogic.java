import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface BotLogic {
    /**
     * Нужен для обращении к методам работы бота
     *
     * @param message сообщение пользовается обратившийся к пользователю
     * @return
     */
    String consoleRecordingDeadline(Message message);

    /**
     * @param sendMessage Сообщение бота для пользователя
     *                    Устанавливает кнопки в телеграмм чат
     */
    void setButtons(SendMessage sendMessage);
}
