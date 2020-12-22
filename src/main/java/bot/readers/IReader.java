package bot.readers;

import bot.constants.AppCommands;
import bot.model.Discipline;
import bot.model.Group;
import bot.model.User;

import java.io.IOException;

/**
 * Интерфейс дисериализации
 */
public interface IReader {
    /**
     * Метод для поиск и дисериализация дедлайна
     * @return          Содержание дедлайна
     * @throws IOException
     */
    String readDeadline(String date, String message, User userDate) throws IOException;

    /**
     * Метод для поиск и дисериализация группы
     * @return          Содержание группы
     * @throws IOException
     */
    Group readGroup(String idUser, AppCommands command) throws IOException;

    /**
     * Метод для поиск и дисериализация даты
     * @return          Содержание даты
     * @throws IOException
     */
    String readDate(String idUser, AppCommands command) throws IOException;

    /**
     * Метод для поиск и дисериализация дисциплины
     * @return          Содержание дисциплины
     * @throws IOException
     */
    Discipline readDiscipline(String idUser, AppCommands command) throws IOException;

    User readUserDate(String idUser) throws IOException;
}
