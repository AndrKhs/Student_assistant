package bot.writers;

import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.Discipline;
import bot.model.Group;
import bot.constants.Commands;

import java.io.IOException;

/**
 * Интерфейс сериализации и записи файлов
 */
public interface IWriter {
    /**
     * Метод для сериализации начала функции
     * @param message       Сообщение пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeUserState(Message message, Commands command, String input) throws IOException;

    /**
     * Метод для сериализации группы
     * @param message       Сообщение пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeGroup(Message message, Commands command, String input) throws IOException ;

    /**
     * Метод для сериализации даты
     * @param message       Сообщение пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeDate(Message message, Commands command, String input) throws IOException;

    /**
     * Метод для сериализации дисциплины
     * @param message       Сообщение пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void writeDiscipline(Message message, Commands command, String input) throws IOException;

    /**
     * Метод для сериализации дедлайна
     * @param homeWork      Сущность домашнего задания
     * @param group         Сущность группы
     * @param discipline    Сущьность учебной дисциплины
     * @param date          Сущьность даты сдачи
     * @return              Дату
     * @throws IOException
     */
    String writeDeadline(String homeWork, Group group, Discipline discipline, String date) throws IOException ;

    void writeUser(Message message, String input) throws IOException;

    /**
     * Метод для записи музыки в файл
     * @param nameMusic     Код музыки
     * @return              Уведомление что музыка добавлена
     */
    String writeMusic(String nameMusic) throws IOException;
}
