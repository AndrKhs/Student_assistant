import botAbility.FunctionsBot.ProviderBot.TelegramProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

    /**
     * Константа для логирования
     */
    private static final Logger log = LogManager.getLogger();

    /**
     * Метод для запуска бота
     * @param args
     */
    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new TelegramProvider());
        } catch (TelegramApiException e) {
            log.error(e);
        }
        log.info("Bot_Assistant_Students started.");
    }
}