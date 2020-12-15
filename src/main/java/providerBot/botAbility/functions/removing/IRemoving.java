package providerBot.botAbility.functions.removing;

import providerBot.botAbility.essences.EndDate;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.constants.CommandsEnum;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс удаления
 */
public interface IRemoving {
    /**
     * Метод для удаления запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     */
    void deleteRequest(String userId, CommandsEnum command);

    /**
     * Метод для удалении всех запросов пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void deleteAllRequest(Message message);


    /**
     *Метод нужен для удалении дедлайнов конкретной группы
     * @param message       Сообщение пользователся обратившийся к боту
     * @param action        Тип функции которую необходимо сделать
     * @param academyGroup  Академическая группа
     *
     */
    void groupDelete(Message message, String action, String academyGroup);

    /**
     * Метод для удаления дедлайна
     * @param date
     * @param group
     * @param message
     * @return
     */
    String deleteDeadline(EndDate date, Group group, String message);
}
