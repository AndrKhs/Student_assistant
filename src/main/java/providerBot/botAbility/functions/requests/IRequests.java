package providerBot.botAbility.functions.requests;

import providerBot.botAbility.constants.CommandsEnum;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс запросов
 */
public interface IRequests {

    /**
     * Метод для сохранения учебной группы введенным пользователем
     * @param command       Команда запроса
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void requestGroup(CommandsEnum command, Message message);

    /**
     * Метод для сохранения дедлайна введенным пользователем
     */
    void requestDeadline(CommandsEnum dateEnum, CommandsEnum groupEnum , CommandsEnum requestUser, Message message);
}