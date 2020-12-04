package commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface ICommand {

    String execute( Message message);

}
