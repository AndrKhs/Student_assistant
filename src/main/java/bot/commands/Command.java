package bot.commands;

import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.User;
import bot.seekers.ISearch;
import bot.seekers.Search;
import bot.sender.MessageSender;
import bot.writers.Writer;

/**
 * Абстракция для выполнении команд
 */
public abstract class Command implements ICommand{
    /**
     * Константа для отпавки сообщения
     */
    protected final MessageSender messageSender = new MessageSender();
    /**
     * Константа для получении листа групп
     */
    protected final ISearch searcher = new Search();
    /**
     * Константа для сохранения состояния пользователя
     */
    protected final Writer write = new Writer();
    /**
     * Абстракция команд
     * @param message       Сообщение пользователся обратившийся к боту
     */
    public abstract void execute(Message message, User userDate);
}
