package bot.logic;

import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.User;

/**
 * Интерфейс логики бота
 */
public interface IBotLogic {

    /**
     * Нужен для обращения к методам работы бота
     * @param message       Сообщение пользователя, который обратился к боту
     */
    void consoleRecordingDeadline(Message message, User userDate) ;
}

