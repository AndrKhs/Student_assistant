package bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.User;

/**
 * Интерфейс абстракции
 */
public interface ICommand {
    /**
     * Абстракция команд
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void execute(Message message, User userDate);
}
