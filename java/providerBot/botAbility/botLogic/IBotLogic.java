package bot.botLogic;

import org.telegram.telegrambots.meta.api.objects.Message;
import bot.constants.Commands;

/**
 * Интерфейс логики бота
 */
public interface IBotLogic {

    /**
     * Нужен для обращения к методам работы бота
     * @param message       Сообщение пользователя, который обратился к боту
     */
    void consoleRecordingDeadline(Message message);

    /**
     * Метод для временного хранения введенной даты для создания дедлайна
     * @param message       Сообщение пользователя, который обратился к боту
     */
    void analyzeDate(Message message);

    /**
     * Метод для проверка даты
     * @param group     Группа
     * @param date      Дедлайн
     * @param command       Команда
     * @param message       Сообщение пользователя, который обратился к боту
     */
    void checkDate(Commands group, Commands date, Commands command, Message message);

    /**
     * Метод завершающий проверку состояния пользователя
     * @param message       Сообщение пользователя, который обратился к боту
     */
    void manipulations(Message message);
}
