package providerBot.botAbility.functions.commands;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Абстракция для выполнении команд
 */
public abstract class Command implements ICommand{
    /**
     * Абстракция команд
     * @param message       Сообщение пользователся обратившийся к боту
     */
    public abstract void execute(Message message);
}
