package providerBot.botAbility.functions.readers;

import providerBot.botAbility.essences.Discipline;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.constants.Commands;

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
    String deadline(String date, Group group, String message) throws IOException;

    /**
     * Метод для поиск и дисериализация группы
     * @return          Содержание группы
     * @throws IOException
     */
    Group group(String idUser, Commands command) throws IOException;

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
    Discipline discipline(String idUser, Commands command) throws IOException;
}
