package providerBot.botAbility.functions.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс нужен для абстракции
 */
public interface ICommand {
    /**
     * Абстракция команд
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void execute(Message message);

}
