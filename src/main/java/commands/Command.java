package commands;

import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class Command {
    public abstract String execute( Message message);
}
