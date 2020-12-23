package bot.sender;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс абстракции отправки сообщений
 */
public interface ISend{
    /**
     * Абстракция сообщений
     * @param message    Сообщение пользователся обратившийся к боту
     * @param text       Ввод пользователя
     */
    void execute(Message message, String text);
}
