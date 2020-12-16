package providerBot.botAbility.functions.UserState;

import providerBot.botAbility.constants.Commands;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс запросов
 */
public interface IUserState {

    /**
     * Метод для сохранения учебной группы введенным пользователем
     * @param command       Команда запроса
     * @param message       Сообщение пользователся обратившегося к боту
     */
    void getGroup(Commands command, Message message);

    /**
     * Метод для сохранения введенным пользователем дедлайна
     */
    void getDeadline(Commands dateEnum, Commands groupEnum , Commands requestUser, Message message);
}