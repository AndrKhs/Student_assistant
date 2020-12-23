package bot.logic;

import bot.constants.AppCommands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.model.User;
import bot.readers.IReader;
import bot.seekers.ISearch;
import bot.seekers.Search;
import bot.sender.MessageSender;
import bot.checks.validate.IVerifier;
import bot.checks.validate.Verifier;
import bot.model.Discipline;
import bot.model.Group;
import bot.removing.IFileRemover;
import bot.removing.FileRemover;
import bot.readers.Reader;
import bot.writers.IWriter;
import bot.writers.Writer;
import bot.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.weather.APIOpenWeather;
import bot.weather.WeatherParser;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для работы с вводом пользователя, так же добавляет возможность использовать кнопики вместо написания комманд
 */
public class BotLogic implements IBotLogic {

    private final StringBuilder sb = new StringBuilder();

    private final WeatherParser weatherParser = new APIOpenWeather();

    /**
     * Вспомогательная сущность для отправки сообщений
     */
    private final MessageSender messageSender = new MessageSender();

    /**
     * Вспомогательная сущность для проверки введенных значений
     */
    private final IVerifier validator = new Verifier();

    /**
     * Набор вспомогательных сущностей для работы с файлами
     */
    private final IFileRemover fileRemover = new FileRemover();
    private final IWriter write = new Writer();
    private final IReader read = new Reader();

    /**
     * Нужна для получении листа групп
     */
    private final ISearch search = new Search();

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(BotLogic.class);

    private static final Map<String, Command> commandMap = new HashMap<>();

    public BotLogic() {
        setCommands();
    }

    /**
     * Соответствие между строковым представлением и сущностью команды
     */
    public void setCommands() {
        commandMap.put(AppConstants.COMMAND_HELP.toStringValue(), new HelpCommand());
        commandMap.put(AppConstants.COMMAND_ADD.toStringValue(), new AddCommand());
        commandMap.put(AppConstants.COMMAND_REMOVE_HOMEWORK.toStringValue(), new DeleteHomeworkCommand());
        commandMap.put(AppConstants.COMMAND_RANDOM_MUSIC.toStringValue(), new RandomMusicCommand());
        commandMap.put(AppConstants.COMMAND_DEADLINE.toStringValue(), new HomeworkCommand());
        commandMap.put(AppConstants.COMMAND_BACK.toStringValue(), new BackCommand());
        commandMap.put(AppConstants.COMMAND_REMOVE.toStringValue(), new DeleteMenuCommand());
        commandMap.put(AppConstants.COMMAND_REMOVE_DATE.toStringValue(), new DeleteDateCommand());
        commandMap.put(AppConstants.COMMAND_DELETE_GROUP_DATE.toStringValue(), new DeleteGroup());
        commandMap.put(AppConstants.COMMAND_WRITER.toStringValue(), new WeatherCommand());
        commandMap.put(AppConstants.COMMAND_HEADS_TAILS.toStringValue(), new HeadsTails());
        commandMap.put(AppConstants.EMOJI_ADD.toStringValue(), new AddCommand());
        commandMap.put(AppConstants.EMOJI_QUESTION_FIRST.toStringValue(), new HelpCommand());
        commandMap.put(AppConstants.EMOJI_QUESTION_SECOND.toStringValue(), new HelpCommand());
        commandMap.put(AppConstants.EMOJI_SPEAKER.toStringValue(), new RandomMusicCommand());
        commandMap.put(AppConstants.EMOJI_CALENDAR.toStringValue(), new HomeworkCommand());
        commandMap.put(AppConstants.EMOJI_BACK.toStringValue(), new BackCommand());
        commandMap.put(AppConstants.EMOJI_TRASH_CAN.toStringValue(), new DeleteMenuCommand());
        commandMap.put(AppConstants.EMOJI_GAME.toStringValue(), new HeadsTails());
        commandMap.put(AppConstants.EMOJI_WEATHER.toStringValue(), new WeatherCommand());
    }

    @Override
    public void consoleRecordingDeadline(Message message, User userDate) {
        if (validator.isExistUser(message.getFrom().getId().toString())) {
            String words = message.getText().toLowerCase();
            ICommand command = commandMap.get(message.getText().toLowerCase());
            log.info(message.getFrom().getUserName() + AppConstants.REQUEST.toStringValue());
            if (commandMap.containsKey(words))
                fileRemover.allUserState(message, userDate);
            if (command == null) {
                sb.setLength(0);
                if (validator.isExist(userDate.getId(), AppCommands.DateDelete)) {
                    deleteHomeWork(message, userDate);
                    return;
                }
                if (validator.isExist(userDate.getId(), AppCommands.DateHomeWork)) {
                    outputDeadline(message, userDate);
                    return;
                }
                if (validator.isExist(userDate.getId(), AppCommands.DisciplineAdd)) {
                    createDeadline(message, userDate);
                    return;
                }
                try {
                    if (validator.isExist(userDate.getId(), AppCommands.DateAdd)) {
                        log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DISCIPLINE.toStringValue());
                        write.writeDiscipline(message, AppCommands.DisciplineAdd, message.getText(), userDate);
                        sb.append(AppConstants.WRITE_HOME_WORK.toStringValue()).append(message.getText());
                        messageSender.execute(message, sb.toString());
                        sb.delete(0, sb.length());
                        return;
                    }

                } catch (IOException e) {
                    log.error(AppErrorConstants.CREATED_REQUEST_DISCIPLINE.toStringValue(), e);
                }
                analyzeDate(message, userDate);
            } else command.execute(message, userDate);
        }
    }

    /**
     * Метод для временного хранения введенной даты для создания дедлайна
     * @param message Сообщение пользователя обратившегося к боту
     */
    private void analyzeDate(Message message, User userDate) {
        if (validator.isExist(userDate.getId(), AppCommands.Add)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DATE.toStringValue());
            if (checkDate(AppCommands.DateAdd, AppCommands.Add, message, userDate)) {
                messageSender.execute(message, search.findDiscipline(userDate.getGroup().group.toUpperCase(), message.getText()));
                messageSender.execute(message, AppConstants.WRITE_DISCIPLINE.toStringValue());
                messageSender.execute(message, AppConstants.CHOICE.toStringValue());
            }
            return;
        }
        if (validator.isExist(userDate.getId(), AppCommands.GroupDelete)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DATE.toStringValue());
            if (checkDate(AppCommands.DateDelete, AppCommands.Delete, message, userDate)) {
                fileRemover.userState(message, AppCommands.GroupDelete, userDate);
                messageSender.execute(message, search.findDiscipline(userDate.getGroup().group.toUpperCase().toUpperCase(), message.getText()));
                messageSender.execute(message, AppConstants.WRITE_DISCIPLINE.toStringValue());
            }
            return;
        }
        if (validator.isExist(userDate.getId(), AppCommands.Deadline)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DATE.toStringValue());
            if (checkDate(AppCommands.DateHomeWork, AppCommands.Deadline, message, userDate)) {
                messageSender.execute(message, search.findDiscipline(userDate.getGroup().group.toUpperCase(), message.getText()));
                messageSender.execute(message, AppConstants.WRITE_DISCIPLINE.toStringValue());
            }
            return;
        }
        if (validator.isExist(userDate.getId(), AppCommands.DeleteWholeDate)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_REMOVE.toStringValue());
            fileRemover.allUserState(message, userDate);
            fileRemover.group(message, userDate);
            return;
        }

        if (validator.isExist(userDate.getId(), AppCommands.Weather)) {
            log.info(message.getFrom().getUserName() + " Запрашивает погоду");
            messageSender.execute(message, weatherParser.getReadyForecast(message.getText()));
            fileRemover.allUserState(message, userDate);
            return;
        }
        messageSender.execute(message, AppErrorConstants.UNKNOWN_COMMAND.toStringValue());
    }

    /**
     * Метод для проверка даты
     * @param date      Команда даты
     * @param command   Команда
     * @param message   Сообщение пользователя обратившегося к боту
     */
    private boolean checkDate(AppCommands date, AppCommands command, Message message, User userDate) {
        sb.setLength(0);
        final String[] dateLength = message.getText().split("\\.");
        if (dateLength.length == 3) {
            final int day = Integer.parseInt(dateLength[0]);
            final int month = Integer.parseInt(dateLength[1]);
            final int year = Integer.parseInt(dateLength[2]);
            if (!validator.checkDay(day)) {
                outputErrorDay(message);
                return false;
            }
            if (!validator.checkMonth(month)) {
                outputErrorMonth(message);
                return false;
            }
            if (!validator.checkYear(year)) {
                outputErrorYear(message);
                return false;
            }
            if (!validator.checkFormatDate(dateLength)) {
                sb.append(AppErrorConstants.INVALID_DEADLINE.toStringValue());
                messageSender.execute(message, sb.toString());
                sb.setLength(0);
                return false;
            }
            if (!validator.checkLengthDate(dateLength)) {
                sb.append(AppErrorConstants.INVALID_DATE.toStringValue());
                messageSender.execute(message, sb.toString());
                sb.setLength(0);
                return false;
            }
            try {
                write.writeDate(message, date, message.getText(), userDate);
            } catch (IOException e) {
                log.error(e.toString());
            }
            fileRemover.userState(message, command, userDate);
        } else {
            messageSender.execute(message, AppErrorConstants.DATE_ERROR.toStringValue());
            return false;
        }
        return true;
    }

    /**
     * Метод для удаления домашнего задания
     * @param message   Сообщение пользователя обратившегося к боту
     * @param userDate  Сущность пользователя
     */
    private void deleteHomeWork(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_CREATE.toStringValue());
        try {
            String date = read.readDate(userDate.getId(), AppCommands.DateDelete);
            Group group = userDate.getGroup();
            messageSender.execute(message, fileRemover.homeWork(date, group, message.getText()));
            fileRemover.allUserState(message, userDate);
        } catch (IOException e) {
            log.error("Ошибка удаления домашнего задания", e);
        }
        return;
    }

    /**
     * Метод вывода домашнего задания
     * @param message   Сообщение пользователя обратившегося к боту
     * @param userDate  Сущность пользователя
     */
    private void outputDeadline(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_CREATE.toStringValue());
        try {
            String date = read.readDate(userDate.getId(), AppCommands.DateHomeWork);
            messageSender.execute(message, read.readDeadline(date, message.getText(), userDate));
            fileRemover.allUserState(message, userDate);
        } catch (IOException e) {
            log.error("Ошибка вывода домашнего задания", e);
        }
        return;
    }

    /**
     * Метод для создания крайнего срока сдачи домашнего задания
     * @param message   Сообщение пользователя обратившегося к боту
     * @param userDate  Сущность пользователя
     */
    private void createDeadline(Message message, User userDate) {
        log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_CREATE.toStringValue());
        try {
            sb.setLength(0);
            String homeWork = message.getText();
            String date = read.readDate(userDate.getId(), AppCommands.DateAdd);
            Discipline discipline = read.readDiscipline(userDate.getId(), AppCommands.DisciplineAdd);

            sb.append("Дедлайн создан ")
                    .append(write.writeDeadline(homeWork,
                            userDate, discipline, date));
            messageSender.execute(message, sb.toString());
            sb.delete(0, sb.length());
            fileRemover.allUserState(message, userDate);
        } catch (IOException e) {
            log.error("Ошибка создания домашнего задания", e);
        }
        return;
    }

    /**
     * Метод вывода ошибки дня
     * @param message   Сообщение пользователя обратившегося к боту
     */
    private void outputErrorDay(Message message) {
        sb.setLength(0);
        sb.append(AppErrorConstants.INVALID_DATE_DAY.toStringValue());
        messageSender.execute(message, sb.toString());
        sb.setLength(0);
    }

    /**
     * Метод вывода ошибки месяца
     * @param message   Сообщение пользователя обратившегося к боту
     */
    private void outputErrorMonth(Message message) {
        sb.setLength(0);
        sb.append(AppErrorConstants.INVALID_DATE_MONTH.toStringValue());
        messageSender.execute(message, sb.toString());
        sb.setLength(0);
    }

    /**
     * Метод вывод ошибки года
     * @param message   Сообщение пользователя обратившегося к боту
     */
    private void outputErrorYear(Message message) {
        sb.setLength(0);
        sb.append(AppErrorConstants.INVALID_DATE_YEAR.toStringValue());
        messageSender.execute(message, sb.toString());
        sb.setLength(0);
    }
}