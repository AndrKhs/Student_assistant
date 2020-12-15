package providerBot.botAbility.functions.send;

import providerBot.TelegramProvider;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class Send extends TelegramProvider{
    /**
     * Абстракция сообщений
     * @param message    Сообщение пользователся обратившийся к боту
     * @param text       Ввод пользователя
     */
    public abstract void execute(Message message, String text);
}
