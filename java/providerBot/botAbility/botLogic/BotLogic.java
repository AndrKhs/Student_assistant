package bot.botLogic;

import bot.constants.Commands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import bot.model.IdUser;
import bot.model.User;
import bot.readers.IReader;
import bot.sender.MessageSender;
import bot.checks.validate.IValidator;
import bot.checks.validate.Validator;
import bot.model.Discipline;
import bot.model.Group;
import bot.removing.IFileRemover;
import bot.removing.FileRemover;
import bot.readers.Reader;
import providerBot.botAbility.functions.UserState.IUserState;
import providerBot.botAbility.functions.UserState.UserState;
import bot.writers.IWriter;
import bot.writers.Writer;
import bot.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.weather.OpenWeatherMapJsonParser;
import bot.weather.Weather;
import bot.weather.WeatherParser;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для работы с вводом пользователя, так же добавляет возможность использовать кнопики вместо написания комманд
 */
public class BotLogic implements IBotLogic {

    private WeatherParser weatherParser = new OpenWeatherMapJsonParser();

    /**
     * Вспомогательная сущность для отправки сообщений
     */
    private final MessageSender sendMsg = new MessageSender();

    /**
     * Вспомогательная сущность для проверки введенных значений
     */
    private final IValidator validator = new Validator();

    /**
     * Набор вспомогательных сущностей для работы с файлами
     */
    private final IFileRemover fileRemover = new FileRemover();
    private final IWriter write = new Writer();
    private final IReader read = new Reader();
    private final IUserState userState = new UserState();

    /**
     * Константа для удаления состояния пользователя
     */
    private final IRemoverUserState deleteUserState = new RemoverUserState();

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
        commandMap.put(AppConstants.COMMAND_START.toStringValue(), new StartCommand());
        commandMap.put(AppConstants.COMMAND_HELP.toStringValue(), new HelpCommand());
        commandMap.put(AppConstants.COMMAND_ADD.toStringValue(), new AddCommand());
        commandMap.put(AppConstants.COMMAND_REMOVE_HOMEWORK.toStringValue(), new DeleteDeadlineCommand());
        commandMap.put(AppConstants.COMMAND_RANDOM_MUSIC.toStringValue(), new RandomMusicCommand());
        commandMap.put(AppConstants.COMMAND_DEADLINE.toStringValue(), new DeadlineCommand());
        commandMap.put(AppConstants.COMMAND_BACK.toStringValue(), new BackCommand());
        commandMap.put(AppConstants.COMMAND_REMOVE_GROUP.toStringValue(), new DeleteGroupCommand());
        commandMap.put(AppConstants.COMMAND_REMOVE.toStringValue(), new DeleteAllCommand());
        commandMap.put(AppConstants.COMMAND_REMOVE_DATE.toStringValue(), new DeleteDateCommand());
        commandMap.put("орел и решка", new HeadsTails());
        commandMap.put("погода", new Weather());
    }

    @Override
    public void consoleRecordingDeadline(Message message) {
        String words = message.getText().toLowerCase();
        StringBuilder sb = new StringBuilder();
        IdUser idUser = new IdUser(message);
        User user = new User();
        if(!validator.isExistUser(idUser.id)) {
            try {
                write.writeUser(message,words);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                user = read.readUser(idUser.id);
                System.out.println(user.userGroup + "" + user.userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (commandMap.containsKey(words))
            deleteUserState.allUserState(message);
        try {
            if (validator.isExist(idUser.id, Commands.DateDelete)) {
                log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_CREATE.toStringValue());
                String date = read.readDate(idUser.id, Commands.DateDelete);
                Group group = read.readGroup(idUser.id, Commands.GroupDelete);
                sendMsg.execute(message, fileRemover.homeWork(date, group, message.getText()));

                deleteUserState.allUserState(message);
                return;
            }
            if (validator.isExist(idUser.id, Commands.DateDeadline)) {
                log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_CREATE.toStringValue());
                String date = read.readDate(idUser.id, Commands.DateDeadline);
                Group group = read.readGroup(idUser.id, Commands.GroupDeadline);
                sendMsg.execute(message, read.readDeadline(date, group, message.getText()));

                deleteUserState.allUserState(message);
                return;

            }
            if (validator.isExist(idUser.id, Commands.DisciplineAdd)) {
                log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_CREATE.toStringValue());

                String homeWork = message.getText();
                Group group = read.readGroup(idUser.id, Commands.GroupAdd);
                String date = read.readDate(idUser.id, Commands.DateAdd);
                Discipline discipline = read.readDiscipline(idUser.id, Commands.DisciplineAdd);

                sb.append("Дедлайн создан ")
                        .append(write.writeDeadline(homeWork,
                                group, discipline, date));
                sendMsg.execute(message, sb.toString());
                sb.delete(0, sb.length());
                deleteUserState.allUserState(message);
                return;
            }
        } catch (IOException e) {
            log.error(AppErrorConstants.READ_WRITE_DELETE_DEADLINE.toStringValue(),e);
        }
        try {
            if (validator.isExist(idUser.id, Commands.DateAdd)) {
                log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DISCIPLINE.toStringValue());
                write.writeDiscipline(message, Commands.DisciplineAdd, message.getText());
                sb.append(AppConstants.WRITE_HOME_WORK.toStringValue()).append(message.getText());
                sendMsg.execute(message, sb.toString());
                sb.delete(0, sb.length());
                return;
            }

        } catch (IOException e) {
            log.error(AppErrorConstants.CREATED_REQUEST_DISCIPLINE.toStringValue(),e);
        }
        analyzeDate(message);
    }

    /**
     * Метод для временного хранения введенной даты для создания дедлайна
     * @param message       Сообщение пользователся обратившийся к боту
     */
    @Override
    public void analyzeDate(Message message) {
        IdUser user = new IdUser(message);
        if (validator.isExist(user.id, Commands.GroupAdd)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DATE.toStringValue());
            checkDate(Commands.GroupAdd, Commands.DateAdd, Commands.Add, message);
            return;
        }
        if (validator.isExist(user.id, Commands.GroupDelete)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DATE.toStringValue());
            checkDate(Commands.GroupDelete, Commands.DateDelete, Commands.Delete, message);
            return;
        }
        if (validator.isExist(user.id, Commands.GroupDeadline)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_DATE.toStringValue());
            checkDate(Commands.GroupDeadline, Commands.DateDeadline, Commands.Deadline, message);
            return;
        }
        if (validator.isExist(user.id, Commands.DeleteGroupWholeDate)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_REMOVE.toStringValue());
            try {
                Group group = read.readGroup(user.id, Commands.DeleteGroupWholeDate);
                fileRemover.group(message, "date", group);
            } catch (IOException e) {
                log.error(AppErrorConstants.REMOVE_FILE.toStringValue(),e);
            }
            deleteUserState.allUserState(message);
            return;
        }
        if (validator.isExist(user.id, Commands.DeleteGroupSecond)) {
            log.info(message.getFrom().getUserName() + "Второй шаг удаления");
            try {
                Group group = read.readGroup(user.id, Commands.DeleteGroupSecond);
                fileRemover.group(message, "group", group);
            } catch (IOException e) {
                log.error(AppErrorConstants.REMOVE_FILE.toStringValue(),e);
            }
            deleteUserState.allUserState(message);
            return;
        }
        manipulations(message);
    }

    /**
     * Метод для проверка даты
     * @param group
     * @param date
     * @param command
     * @param message
     */
    @Override
    public void checkDate(Commands group, Commands date, Commands command, Message message){
        StringBuilder sb = new StringBuilder();
        String mes = message.getText();
        sb.setLength(0);
        final String[] dateLength = mes.split("\\.");
        final int day = Integer.parseInt(dateLength[0]);
        final int month = Integer.parseInt(dateLength[1]);
        final int year = Integer.parseInt(dateLength[2]);
        if (!validator.checkDay(day)) {
            sb.append(AppErrorConstants.INVALID_DATE_DAY.toStringValue());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validator.checkMonth(month)) {
            sb.append(AppErrorConstants.INVALID_DATE_MONTH.toStringValue());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validator.checkYear(year)) {
            sb.append(AppErrorConstants.INVALID_DATE_YEAR.toStringValue());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validator.checkFormatDate(dateLength)) {
            sb.append(AppErrorConstants.INVALID_DEADLINE.toStringValue());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validator.checkLengthDate(dateLength)) {
            sb.append(AppErrorConstants.INVALID_DATE.toStringValue());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        userState.getDeadline(date, group, command, message);
        deleteUserState.userState(message, command);
    }

    /**
     * Метод завершающий проверку состояния пользователя
     * @param message
     */
    @Override
    public void manipulations(Message message) {
        IdUser user = new IdUser(message);
        if (validator.isExist(user.id, Commands.Deadline)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_GROUP.toStringValue());
            userState.getGroup(Commands.GroupDeadline, message);
            return;
        }
        if (validator.isExist(user.id, Commands.Delete)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_GROUP.toStringValue());
            userState.getGroup(Commands.GroupDelete, message);
            return;
        }
        if (validator.isExist(user.id, Commands.Add)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_GROUP.toStringValue());
            userState.getGroup(Commands.GroupAdd, message);
            return;
        }
        if (validator.isExist(user.id, Commands.DeleteWholeDate)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_GROUP.toStringValue());
            userState.getGroup(Commands.DeleteGroupWholeDate, message);
            return;
        }
        if(validator.isExist(user.id, Commands.DeleteGroupFirst)) {
            log.info(message.getFrom().getUserName() + AppConstants.PERFORMS_REMOVE_GROUP.toStringValue());
            userState.getGroup(Commands.DeleteGroupSecond, message);
            return;
        }
        if(validator.isExist(user.id, Commands.Weather)) {
            log.info(message.getFrom().getUserName() + " Запрашивает погоду");
            sendMsg.execute(message, weatherParser.getReadyForecast(message.getText()));
            deleteUserState.allUserState(message);
            return;
        }
        ICommand command = commandMap.get(message.getText().toLowerCase());
        log.info(message.getFrom().getUserName() + AppConstants.REQUEST.toStringValue());
        if (command == null) sendMsg.execute(message, AppErrorConstants.UNKNOWN_COMMAND.toStringValue());
        else command.execute(message);
    }
}