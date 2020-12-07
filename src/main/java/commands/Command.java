package commands;

import botAbility.Console.RequestConsole;
import botAbility.Console.botConsole;
import botAbility.FunctionsBot.ProviderBot.BotProvider;
import botAbility.FunctionsBot.ProviderBot.DateBaseBot;
import botAbility.FunctionsBot.ProviderBot.TelegramProvider;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Абстракция для
 */
public abstract class Command implements ICommand{
    protected TelegramProvider send = new TelegramProvider();
    protected botConsole request = new RequestConsole();
    protected BotProvider getGroupList = new DateBaseBot();
    public abstract String execute(Message message);
}
