package bot.checks.validate;

import bot.constants.AppCommands;

/**
 * Интерфес проверок
 */
public interface IVerifier {

    /**
     * Метод для проверки существования файла состояния пользователя
     * @param idUser
     * @param command
     * @return
     */
    boolean isExist (String idUser, AppCommands command);

    /**
     * Метод для проверки дня
     * @param day       День
     * @return          True если день правльный
     */
    boolean checkDay(int day);

    /**
     * Метод для проверки месяца
     * @param month     Месяц
     * @return          True если месяц правльный
     */
    boolean checkMonth(int month);

    /**
     * Метод для проверки года
     * @param year      Год
     * @return          True если год правльный
     */
    boolean checkYear(int year);

    /**
     * Метод для проверки формата даты
     * @param dateLength       Дата по элементам ДД ММ ГГГГ
     * @return                 True если формат даты верный
     */
    boolean checkFormatDate(String[] dateLength);

    /**
     * Метод для проверки каждого элемента даты(день, месяц год) на правильность формата
     * @param dateLength       Дата по элементам ДД ММ ГГГГ
     * @return                 True если колличества чисел дней месяца и года верный
     */
    boolean checkLengthDate(String[] dateLength);

    /**
     * Метод для проверки существования пользователя в базе
     * @param idUser    Данные пользователя
     * @return          True если пользователь существует
     */
    boolean isExistUser (String idUser);

    /**
     * Проверка существования группы
     * @param group     Название группы
     * @return          True если группа существует
     */
    boolean checkGroup(String group);
}
