package bot.checks.checkerTypeMessage;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс провекри типа сообщения
 */
public interface IMessageListener {
    /**
     * Метод для определения типа сообщения
     * @param message       Сообщение пользователя обратившийся к боту
     */
    void checkTypeMessage(Message message);
}
