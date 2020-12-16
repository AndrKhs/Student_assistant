package providerBot;

import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.checks.checkerTypeMessage.CheckerTypeMessage;
import providerBot.botAbility.checks.checkerTypeMessage.ICheckerTypeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;

/**
 * Класс для работы бота
 */
public class TelegramProvider extends TelegramLongPollingBot {

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(TelegramProvider.class);

    /**
     * Метод для работы бота с чатом
     * @param update        Обновленные переменные после обращение пользователся к боту
     */
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        ICheckerTypeMessage checkTypeMessage = new CheckerTypeMessage();
        checkTypeMessage.CheckTypeMessage(message);
    }

    /**
     * Метод для отправки имени бота
     * @return              Возращает имя
     */
    @Override
    public String getBotUsername() {
        return Constant.BOT_NAME.getConstant();
    }

    /**
     * Метод для чтения файла с токеном
     * @return              Возращает token бота
     */
    private String readToken() {
        try {
            File file = new File(System.getProperty("user.dir") + "\\Token.txt");
            if (file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                StringBuilder result = new StringBuilder();
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                }
            }
        } catch (IOException e) {
            log.error(ConstantError.READ_TOKEN.gerError(), e);
        }
        return "";
    }

    /**
     * Токен бота для связки кода с ботом в телеграмме
     * @return              Возращает токен бота
     */
    @Override
    public String getBotToken() {
        return readToken();
    }
}
