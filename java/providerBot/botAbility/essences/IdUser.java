package bot.model;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Класс сущности пользователя
 */
public class IdUser {
    /**
     * Уникальный индентификатор пользователя
     */
    public String id;

    /**
     * Конструктор пользователя
     * @param message       Сообщение пользователя обративши
     */
    public IdUser(Message message){
        id = message.getFrom().getId().toString();
    }
}
