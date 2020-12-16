package providerBot.botAbility.functions.removing;

import org.telegram.telegrambots.meta.api.objects.Message;
import providerBot.botAbility.constants.Commands;

public interface IRemoverUserState {
    /**
     * Метод для удаления запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     */
    void userState(Message message, Commands command);

    /**
     * Метод для удалении всех запросов пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void allUserState(Message message);
}
