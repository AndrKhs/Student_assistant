package providerBot.botAbility.botLogic;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс логики бота
 */
public interface IBotLogic {
    /**
     * Нужен для обращения к методам работы бота
     * @param message       Сообщение пользователя обратившийся к боту
     */
    void consoleRecordingDeadline(Message message);
}
