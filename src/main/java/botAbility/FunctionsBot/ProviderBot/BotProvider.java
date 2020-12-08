package botAbility.FunctionsBot.ProviderBot;

import botAbility.Console.FileConsole;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Интерфейс для обращения к методам функции бота
 */
public interface BotProvider {
    /**
     * Метод для создания сериализованного файл с вводом пользователя
     * @param file      Дата дедлайна
     * @param wrote    Задание или ссылка
     * @return          Задание или ссылка
     * @throws IOException
     */
    String writeFile(FileConsole file, Object wrote) throws IOException;

    /**
     * Метод для поиск и чтение дедлайна
     * @param file      Дата дедлайна, группа, дисциплина
     * @return          Содержание дедлайна
     * @throws IOException
     */
    Object readDeadline(FileConsole file) throws IOException;

    /**
     * Метод для записи музыки
     * @param nameMusic     код музыки
     * @return              уведомление что музыка добавлена
     * @throws IOException
     */
    String writeMusic(String nameMusic) throws IOException;

    /**
     * Метод для удаления файл с дедлайном
     * @param file      Дата дедлайна, группа, дисциплина
     * @return          Содержание дедлайна
     */
    String deleteDeadline(FileConsole file);

    /**
     * Метод для поиска группы
     * @return          Учебная группа
     */
    String searchGroup();

    /**
     * Метод для поиска учебного предмета
     * @param group     Учебная группа
     * @param date      Дедлайн
     * @return          Учебный предмет
     */
    String searchDiscipline(Object group, String date);

    /**
     * Метод для поиска даты дедлайна
     * @param group     Учебная группа
     * @return          Дедлайн
     */
    String searchDate(Object group);

    /**
     * Метод для отправки сообщения
     * @param message   Сообщение пользователся обратившийся к боту
     * @param text      Текст для ответа на сообщение пользователя
     */
    void sendMsg(Message message, String text);

    /**
     * Метод для отправки аудио
     * @param message    Сообщение пользователся обратившийся к боту
     * @throws FileNotFoundException
     */
    void sendAudio(Message message) throws FileNotFoundException;
}

