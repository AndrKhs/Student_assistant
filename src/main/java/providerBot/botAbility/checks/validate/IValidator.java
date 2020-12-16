package providerBot.botAbility.checks.validate;

import providerBot.botAbility.constants.Commands;

/**
 * Интерфес проверок
 */
public interface IValidator {

    /**
     * Метод для проверки существования файла состояния пользователя
     * @param idUser
     * @param command
     * @return
     */
    boolean isExist (String idUser, Commands command);

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
}
