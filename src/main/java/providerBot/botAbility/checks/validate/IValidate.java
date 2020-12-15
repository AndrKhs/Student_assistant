package providerBot.botAbility.checks.validate;

import providerBot.botAbility.constants.CommandsEnum;

/**
 * Интерфес проверок
 */
public interface IValidate {

    /**
     * Метод для проверки существования файла состояния пользователя
     * @param idUser
     * @param command
     * @return
     */
    boolean isExist (String idUser, CommandsEnum command);

    /**
     *
     * @param day       День
     * @return          True если день правльный
     */
    boolean checkDay(int day);
    /**
     *
     * @param month     Месяц
     * @return          True если месяц правльный
     */
    boolean checkMonth(int month);

    /**
     *
     * @param year      Год
     * @return          True если год правльный
     */
    boolean checkYear(int year);

    /**
     *
     * @param dateLength       Дата по элементам ДД ММ ГГГГ
     * @return                 True если формат даты верный
     */
    boolean checkFormatDate(String[] dateLength);

    /**
     *
     * @param dateLength       Дата по элементам ДД ММ ГГГГ
     * @return                 True если колличества чисел дней месяца и года верный
     */
    boolean checkLengthDate(String[] dateLength);
}
