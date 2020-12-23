package bot.readers;

import bot.model.Discipline;
import bot.model.Group;
import bot.constants.Commands;
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
    String readDeadline(String date, Group group, String message) throws IOException;

    /**
     * Метод для поиск и дисериализация группы
     * @return          Содержание группы
     * @throws IOException
     */
    Group readGroup(String idUser, Commands command) throws IOException;

    /**
     * Метод для поиск и дисериализация даты
     * @return          Содержание даты
     * @throws IOException
     */
    String readDate(String idUser, Commands command) throws IOException;

    /**
     * Метод для поиск и дисериализация дисциплины
     * @return          Содержание дисциплины
     * @throws IOException
     */
    Discipline readDiscipline(String idUser, Commands command) throws IOException;

    User readUser(String idUser) throws IOException;
}
