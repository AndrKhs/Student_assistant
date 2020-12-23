package bot.weather;

import bot.constants.AppErrorConstants;
import bot.constants.Commands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.commands.Command;

import java.io.IOException;

public class Weather extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Weather.class);

    @Override
    public void execute(Message message) {
        try {
            write.writeUserState(message, Commands.Back, "");
            write.writeUserState(message, Commands.Weather, "");
            sendMsg.execute(message,"Введите город в чат и получите прогноз на 5 дней!");
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_ADD.toStringValue(), e);
        }
    }
}
