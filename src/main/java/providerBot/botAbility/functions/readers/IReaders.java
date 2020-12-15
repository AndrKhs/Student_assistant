package providerBot.botAbility.functions.readers;

import providerBot.botAbility.essences.EndDate;
import providerBot.botAbility.essences.Discipline;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.constants.CommandsEnum;

import java.io.IOException;

/**
 * Интерфейс дисериализации
 */
public interface IReaders {
    /**
     * Метод для поиск и дисериализация дедлайна
     * @param date      Сущность даты
     * @param group     Сущность группы
     * @param message   Сообщение пользователя обратившийся к боту   
     * @return          Содержание дедлайна
     * @throws IOException
     */
    String readDeadline(EndDate date, Group group, String message) throws IOException;

    /**
     * Метод для поиск и дисериализация группы
     * @param idUser        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Содержание группы
     * @throws IOException
     */
    Group readGroup(String idUser, CommandsEnum command) throws IOException;

    /**
     * Метод для поиск и дисериализация даты
     * @param idUser        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Содержание даты
     * @throws IOException
     */
    EndDate readDate(String idUser, CommandsEnum command) throws IOException;

    /**
     * Метод для поиск и дисериализация дисциплины
     * @param idUser        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Содержание дисциплины
     * @throws IOException
     */
    Discipline readDiscipline(String idUser, CommandsEnum command) throws IOException;
}
