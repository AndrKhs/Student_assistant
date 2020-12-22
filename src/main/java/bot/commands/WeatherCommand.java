package bot.commands;

import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.constants.AppCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.User;

import java.io.IOException;

public class WeatherCommand extends Command {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(WeatherCommand.class);

    /**
     * Начало работы команды "Погода"
     * @param message       Сообщение обратившегося к боту пользователя
     * @param userDate
     */
    @Override
    public void execute(Message message, User userDate) {
        try {
            write.writeUserState(message, AppCommands.Back, "", userDate);
            write.writeUserState(message, AppCommands.Weather, "", userDate);
            sendMsg.execute(message, AppConstants.ENTER_CITY.toStringValue());
        } catch (IOException e) {
            log.error(AppErrorConstants.COMMAND_ADD.toStringValue(), e);
        }
    }
}