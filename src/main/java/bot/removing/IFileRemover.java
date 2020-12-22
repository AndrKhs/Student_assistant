package bot.removing;

import bot.constants.AppCommands;
import bot.model.Group;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.User;

/**
 * Интерфейс удаления
 */
public interface IFileRemover {

    /**
     *Метод нужен для удалении дедлайнов конкретной группы
     */
    void group(Message message, User userDate);

    /**
     * Метод для удаления дедлайна
     * @param date
     * @param group
     * @param message
     * @return
     */
    String homeWork(String date, Group group, String message);

    /**
     * Метод для удаления запроса пользователя
     * @param command       Команда
     */
    void userState(Message message, AppCommands command, User userdate);

    /**
     * Метод для удалении всех запросов пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void allUserState(Message message, User userDate) ;
}
