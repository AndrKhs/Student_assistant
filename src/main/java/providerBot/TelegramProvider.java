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
public class TelegramProvider extends TelegramLongPollingBot{

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(TelegramProvider.class);

    /**
     * Метод для работы бота с чатом
     * @param update     Обновленные переменные после обращение пользователся к боту
     */
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        ICheckerTypeMessage checkTypeMessage = new CheckerTypeMessage();
        checkTypeMessage.CheckTypeMessage(message);
    }

    /**
     * Метод для отправки имени бота
     * @return          Возращает имя
     */
    @Override
    public String getBotUsername() {
        return Constant.BOT_NAME.getConstant();
    }

    /**
     * Метод для чтения файла с токеном
     * @return          Возращает token бота
     * @throws IOException
     */
    private String readToken() throws IOException {
        java.io.File fi = new java.io.File(System.getProperty("user.dir") + "\\Token.txt");
        if (fi.exists()) {
            FileInputStream fI = new FileInputStream(fi);
            StringBuilder resultStringBuilder = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
                String line;
                while ((line = br.readLine()) != null) {
                    resultStringBuilder.append(line);
                }
                return resultStringBuilder.toString();
            } catch (IOException e) {
                log.error(ConstantError.READ_TOKEN.gerError(),e);
            }
        }
        return "";
    }

    /**
     * Токен бота для связки кода с ботом в телеграмме
     * @return        Возращает токен бота
     */
    @Override
    public String getBotToken() {
        try {
            return readToken();
        } catch (IOException e) {
            log.error(ConstantError.API_ERROR.gerError(), e);
        }
        return "Error";
    }
}
