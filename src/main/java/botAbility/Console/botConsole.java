package botAbility.Console;

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
     * @return id пользователя
     * @throws IOException
     */
    String writeRequest(String userId, Object command, String input) throws IOException;

}