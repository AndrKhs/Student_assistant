package botAbility.Console;

import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

/**
 * Интерфейс для обращения к методам из requestConsol
 */
public interface botConsole {

    /**
     * Метод для поиска запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Если запрос был найден - возращает имя файла с запросом(userId + commandNumber)
     */
    String searchRequest(String userId, Object command);

    /**
     * Метод для удаления запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       команда
     */
    void deleteRequest(String userId, Object command);

    /**
     * Метод для прочитать содержание запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Содержание запроса
     * @throws IOException
     */
    Object readRequest(String userId, Object command) throws IOException;

    /**
     * Метод для сохранения запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @param input         Запрос
     * @throws IOException
     */
    void writeRequest(String userId, Object command, String input) throws IOException;

    /**
     * Метод нужен для удалении всех запросов пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void deleteAllRequest(Message message);

    /**
     * Метод для сохранения учебной группы введенным пользователем
     * @param command       Команда запроса
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void writeRequestGroup(Object command, Message message);

    /**
     * Метод для сохранения дедлайна введенным пользователем
     * @param date              Дедлайн
     * @param group             Учебная группа
     * @param requestUser       Запрос пользователя
     * @param message           Сообщение пользователся обратившийся к боту
     */
    void writeRequestDeadline(Object date, Object group, Object requestUser, Message message);
}