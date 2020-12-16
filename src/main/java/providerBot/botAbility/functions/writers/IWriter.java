package providerBot.botAbility.functions.writers;

import org.telegram.telegrambots.meta.api.objects.Message;
import providerBot.botAbility.essences.Discipline;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.constants.Commands;

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
    void request(Message message, Commands command, String input) throws IOException;

    /**
     * Метод для сериализации группы
     * @param message       Сообщение пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void group(Message message, Commands command, String input) throws IOException ;

    /**
     * Метод для сериализации даты
     * @param message       Сообщение пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void date(Message message, Commands command, String input) throws IOException;

    /**
     * Метод для сериализации дисциплины
     * @param message       Сообщение пользователя
     * @param command       Команда
     * @param input         Ввод пользователя
     * @throws IOException
     */
    void discipline(Message message, Commands command, String input) throws IOException;

    /**
     * Метод для сериализации дедлайна
     * @param homeWork      Сущность домашнего задания
     * @param group         Сущность группы
     * @param discipline    Сущьность учебной дисциплины
     * @param date          Сущьность даты сдачи
     * @return              Дату
     * @throws IOException
     */
    String deadline(String homeWork, Group group, Discipline discipline, String date) throws IOException ;

    /**
     * Метод для записи музыки в файл
     * @param nameMusic     Код музыки
     * @return              Уведомление что музыка добавлена
     */
    String music(String nameMusic) throws IOException;
}
