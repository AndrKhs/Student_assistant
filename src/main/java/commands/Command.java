package commands;

import botAbility.Console.FilesConsoleActions;
import botAbility.Console.botConsole;
import botAbility.FunctionsBot.ProviderBot.BotProvider;
import botAbility.FunctionsBot.ProviderBot.FilesDeadlineActions;
import botAbility.FunctionsBot.ProviderBot.TelegramProvider;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Абстракция нужна для выполнении команд
 */
public abstract class Command implements ICommand{
    /**
     * Нужна для отпавки сообщения
     */
    public static TelegramProvider send = new TelegramProvider();
    /**
     * Нужена для сохранении запроса
     */
    public static botConsole request = new FilesConsoleActions();
    /**
     * Нужна для получении листа групп
     */
    public static BotProvider getGroupList = new FilesDeadlineActions();
    /**
     * нужен для абстракции команд
     * @param message
     * @return
     */
    public abstract String execute(Message message);
}
