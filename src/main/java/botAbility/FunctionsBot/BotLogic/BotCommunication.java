package botAbility.FunctionsBot.BotLogic;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс для обращения к методам из BotAPI
 */
public interface BotCommunication {
    /**
     * Нужен для обращения к методам работы бота
     * @param message       сообщение пользователя обратившийся к боту
     */
    void consoleRecordingDeadline(Message message);
}
