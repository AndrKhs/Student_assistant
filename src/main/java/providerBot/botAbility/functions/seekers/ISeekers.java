package providerBot.botAbility.functions.seekers;

import providerBot.botAbility.constants.CommandsEnum;

/**
 * Интерфейс поиска
 */
public interface ISeekers {
    /**
     * Метод для вывода списока групп
     * @return      список групп
     */
    String searchGroup();

    /**
     * Метод для поиска учебного предмета
     * @param group     Учебная группа
     * @param date      Дедлайн
     * @return          Учебный предмет
     */
    String searchDiscipline(String group, String date);

    /**
     * Метод для поиска даты дедлайна
     * @param group     Учебная группа
     * @return          Дедлайн
     */
    String searchDate(String group);

    /**
     * Метод для поиска запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Если запрос был найден - возращает имя файла с запросом(userId + commandNumber)
     */
    String searchRequest(String userId, CommandsEnum command);
}
