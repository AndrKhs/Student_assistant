package commands;

import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Интерфейс нужен для абстракции
 */
public interface ICommand {

    String execute( Message message);

}
