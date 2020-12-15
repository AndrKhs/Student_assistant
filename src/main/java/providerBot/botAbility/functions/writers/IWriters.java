package providerBot.botAbility.functions.writers;

import providerBot.botAbility.essences.EndDate;
import providerBot.botAbility.essences.Discipline;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.essences.HomeWork;
import providerBot.botAbility.constants.CommandsEnum;

import java.io.IOException;

/**
 * Интерфейс сериализации и записи файлов
 */
public interface IWriters {
    /**
     * Метод для сериализации начала функции
     * @param userId        Уникальный идентификатор пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeRequest(String userId, CommandsEnum command, String input) throws IOException;

    /**
     * Метод для сериализации группы
     * @param userId        Уникальный идентификатор пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeGroup(String userId, CommandsEnum command, String input) throws IOException ;

    /**
     * Метод для сериализации даты
     * @param userId        Уникальный идентификатор пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeDate(String userId, CommandsEnum command, String input) throws IOException;

    /**
     * Метод для сериализации дисциплины
     * @param userId        Уникальный идентификатор пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeDiscipline(String userId, CommandsEnum command, String input) throws IOException;

    /**
     * Метод для сериализации дедлайна
     * @param homeWork      Сущность домашнего задания
     * @param group         Сущность группы
     * @param discipline    Сущьность учебной дисциплины
     * @param date          Сущьность даты сдачи
     * @return              Дату
     * @throws IOException
     */
    String writeDeadline(HomeWork homeWork, Group group, Discipline discipline, EndDate date) throws IOException ;

    /**
     * Метод для записи музыки в файл
     * @param nameMusic     Код музыки
     * @return              Уведомление что музыка добавлена
     */
    String writeMusic(String nameMusic) throws IOException;
}
