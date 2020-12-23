package bot.seekers;

/**
 * Интерфейс поиска
 */
public interface ISearch {

    /**
     * Метод для поиска учебного предмета
     * @param group     Учебная группа
     * @param date      Дедлайн
     * @return          Учебный предмет
     */
    String findDiscipline(String group, String date);

    /**
     * Метод для поиска даты дедлайна
     * @param group     Учебная группа
     * @return          Дедлайн
     */
    String findDate(String group);
}
