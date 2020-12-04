package commands;

import botAbility.Consol.RequestConsol;
import botAbility.Consol.botConsol;
import botAbility.FunctionsBot.ProviderBot.BotProvider;
import botAbility.FunctionsBot.ProviderBot.DateBaseBot;
import botAbility.FunctionsBot.ProviderBot.TelegramProvider;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Абстракция для
 */
public abstract class Command implements ICommand{
    protected TelegramProvider send = new TelegramProvider();
    protected botConsol request = new RequestConsol();
    protected BotProvider getGroupList = new DateBaseBot();

    public abstract String execute( Message message);
}
