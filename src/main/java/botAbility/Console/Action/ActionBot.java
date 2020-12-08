package botAbility.Console.Action;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфес для обращения к методам из BotAction
 */
public interface ActionBot {
    /**
     * Метод для проверки правильности ввода дедлайна
     * @param mes           Текст сообщения пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     * @return              null
     */
    String checkDate(String mes, Message message);

    /**
     * Метод нужен для удалении дедлайнов конкретной группы
     * @param message       Сообщение пользователся обратившийся к боту
     */
    void gropeDelete(Message message, Object action, Object academyGroup);
}
