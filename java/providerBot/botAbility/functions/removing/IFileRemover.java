package bot.removing;

import bot.model.Group;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс удаления
 */
public interface IFileRemover {

    /**
     *Метод нужен для удалении дедлайнов конкретной группы
     * @param message       Сообщение пользователся обратившийся к боту
     * @param action        Тип функции которую необходимо сделать
     * @param group  Академическая группа
     *
     */
    void group(Message message, String action, Group group);

    /**
     * Метод для удаления дедлайна
     * @param date
     * @param group
     * @param message
     * @return
     */
    String homeWork(String date, Group group, String message);
}
