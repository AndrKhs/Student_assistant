import providerBot.TelegramProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Класс для инциализации
 */
public class Main {

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * Метод для запуска бота
     * @param args
     */
    public static void main(String[] args) {
        log.info("BOT start");
        ApiContextInitializer.init();
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new TelegramProvider());
        } catch (TelegramApiException e) {
            log.error("Error initialized", e);
        }
        log.info("Bot_Assistant_Students started.");
    }
}