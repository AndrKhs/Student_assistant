package providerBot.botAbility.botLogic;

import providerBot.botAbility.constants.Commands;
import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.essences.User;
import providerBot.botAbility.functions.readers.IReader;
import providerBot.botAbility.functions.removing.IRemoverUserState;
import providerBot.botAbility.functions.removing.RemoverUserState;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.checks.validate.IValidator;
import providerBot.botAbility.checks.validate.Validator;
import providerBot.botAbility.essences.Discipline;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.functions.removing.IFileRemover;
import providerBot.botAbility.functions.removing.FileRemover;
import providerBot.botAbility.functions.readers.Reader;
import providerBot.botAbility.functions.UserState.IUserState;
import providerBot.botAbility.functions.UserState.UserState;
import providerBot.botAbility.functions.writers.IWriter;
import providerBot.botAbility.functions.writers.Writer;
import providerBot.botAbility.functions.commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для работы с вводом пользователя, так же добавляет возможность использовать кнопики вместо написания комманд
 */
public class BotLogic implements IBotLogic {

    /**
     * Константа для отпавки сообщения
     */
    private final SendMsg sendMsg = new SendMsg();

    /**
     * Константа для проверки
     */
    private final IValidator validate = new Validator();

    /**
     * Набор работы с файлами
     */
    private final IFileRemover fileRemover = new FileRemover();
    private final IRemoverUserState deleteUserState = new RemoverUserState();
    private final IWriter write = new Writer();
    private final IReader read = new Reader();
    private final IUserState request = new UserState();

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(BotLogic.class);

    /**
     * Конструктор HashMap команд
     */
    private static final Map<String, Command> commandMap = new HashMap<>();
    public BotLogic() {
        setCommands();
    }
    public void setCommands() {
        commandMap.put(Constant.COMMAND_START.getConstant(), new StartCommand());
        commandMap.put(Constant.COMMAND_HELP.getConstant(), new HelpCommand());
        commandMap.put(Constant.COMMAND_ADD.getConstant(), new AddCommand());
        commandMap.put(Constant.COMMAND_REMOVE_DEADLINE.getConstant(), new DeleteDeadlineCommand());
        commandMap.put(Constant.COMMAND_RANDOM_MUSIC.getConstant(), new RandomMusicCommand());
        commandMap.put(Constant.COMMAND_DEADLINE.getConstant(), new DeadlineCommand());
        commandMap.put(Constant.COMMAND_BACK.getConstant(), new BackCommand());
        commandMap.put(Constant.COMMAND_REMOVE_GROUP.getConstant(), new DeleteGroupCommand());
        commandMap.put(Constant.COMMAND_REMOVE.getConstant(), new DeleteCommand());
        commandMap.put(Constant.COMMAND_REMOVE_DATE.getConstant(), new DeleteWholeDateCommand());
    }


    @Override
    public void consoleRecordingDeadline(Message message) {
        String words = message.getText().toLowerCase();
        StringBuilder sb = new StringBuilder();
        User user = new User(message);
        if (commandMap.containsKey(words))
            deleteUserState.allUserState(message);
        try {
            if (validate.isExist(user.id, Commands.DateDelete)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_CREATE.getConstant());
                String date = read.readDate(user.id, Commands.DateDelete);
                Group group = read.group(user.id, Commands.GroupDelete);
                sendMsg.execute(message, fileRemover.deadline(date, group, message.getText()));

                deleteUserState.allUserState(message);
                return;
            }
            if (validate.isExist(user.id, Commands.DateDeadline)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_CREATE.getConstant());
                String date = read.readDate(user.id, Commands.DateDeadline);
                Group group = read.group(user.id, Commands.GroupDeadline);
                sendMsg.execute(message, read.deadline(date, group, message.getText()));

                deleteUserState.allUserState(message);
                return;

            }
            if (validate.isExist(user.id, Commands.DisciplineAdd)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_CREATE.getConstant());

                String homeWork = message.getText();
                Group group = read.group(user.id, Commands.GroupAdd);
                String date = read.readDate(user.id, Commands.DateAdd);
                Discipline discipline = read.discipline(user.id, Commands.DisciplineAdd);

                sb.append("Дедлайн создан ")
                        .append(write.deadline(homeWork,
                                group, discipline, date));
                sendMsg.execute(message, sb.toString());
                sb.delete(0, sb.length());
                deleteUserState.allUserState(message);
                return;
            }
        } catch (IOException e) {
            log.error(ConstantError.READ_WRITE_DELETE_DEADLINE.gerError(),e);
        }
        try {
            if (validate.isExist(user.id, Commands.DateAdd)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_DISCIPLINE.getConstant());
                write.discipline(message, Commands.DisciplineAdd, message.getText());
                sb.append(Constant.WRITE_HOME_WORK.getConstant()).append(message.getText());
                sendMsg.execute(message, sb.toString());
                sb.delete(0, sb.length());
                return;
            }

        } catch (IOException e) {
            log.error(ConstantError.CREATED_REQUEST_DISCIPLINE.gerError(),e);
        }
        analyzeDate(message);
    }

    /**
     * Метод для временного хранение введенной даты для создание дедлайна
     * @param message       Сообщение пользователся обратившийся к боту
     */
    private void analyzeDate(Message message) {
        User user = new User(message);
        if (validate.isExist(user.id, Commands.GroupAdd)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_DATE.getConstant());
            analyzeDateCheck(user.id, Commands.GroupAdd, Commands.DateAdd, Commands.Add, message);
            return;
        }
        if (validate.isExist(user.id, Commands.GroupDelete)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_DATE.getConstant());
            analyzeDateCheck(user.id, Commands.GroupDelete, Commands.DateDelete, Commands.Delete, message);
            return;
        }
        if (validate.isExist(user.id, Commands.GroupDeadline)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_DATE.getConstant());
            analyzeDateCheck(user.id, Commands.GroupDeadline, Commands.DateDeadline, Commands.Deadline, message);
            return;
        }
        if (validate.isExist(user.id, Commands.DeleteGroupWholeDate)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_REMOVE.getConstant());
            try {
                Group group = read.group(user.id, Commands.DeleteGroupWholeDate);
                fileRemover.group(message, "date", group);
            } catch (IOException e) {
                log.error(ConstantError.REMOVE_FILE.gerError(),e);
            }
            deleteUserState.allUserState(message);
            return;
        }
        manipulations(message);
    }

    /**
     * Метод для проверка даты
     * @param idUser
     * @param group
     * @param date
     * @param command
     * @param message
     */
    private void analyzeDateCheck(String idUser, Commands group, Commands date, Commands command, Message message){
        StringBuilder sb = new StringBuilder();
        String mes = message.getText();
        sb.setLength(0);
        final String[] dateLength = mes.split("\\.");
        final int day = Integer.parseInt(dateLength[0]);
        final int month = Integer.parseInt(dateLength[1]);
        final int year = Integer.parseInt(dateLength[2]);
        if (!validate.checkDay(day)) {
            sb.append(ConstantError.INVALID_DATE_DAY.gerError());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validate.checkMonth(month)) {
            sb.append(ConstantError.INVALID_DATE_MONTH.gerError());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validate.checkYear(year)) {
            sb.append(ConstantError.INVALID_DATE_YEAR.gerError());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validate.checkFormatDate(dateLength)) {
            sb.append(ConstantError.INVALID_DEADLINE.gerError());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        if (!validate.checkLengthDate(dateLength)) {
            sb.append(ConstantError.INVALID_DATE.gerError());
            sendMsg.execute(message, sb.toString());
            sb.setLength(0);
            return;
        }
        request.getDeadline(date, group, command, message);
        deleteUserState.userState(message, command);
    }

    /**
     * Метод завершающий проверку состояния пользователя
     * @param message
     */
    private void manipulations(Message message) {
        User user = new User(message);
        if (validate.isExist(user.id, Commands.Deadline)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.getGroup(Commands.GroupDeadline, message);
            return;
        }
        if (validate.isExist(user.id, Commands.Delete)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.getGroup(Commands.GroupDelete, message);
            return;
        }
        if (validate.isExist(user.id, Commands.Add)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.getGroup(Commands.GroupAdd, message);
            return;
        }
        if (validate.isExist(user.id, Commands.DeleteWholeDate)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.getGroup(Commands.DeleteGroupWholeDate, message);
            return;
        }
        if(validate.isExist(user.id, Commands.DeleteGroup)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_REMOVE_GROUP.getConstant());
            Group group = null;
            group.group = "";
            fileRemover.group(message, "", group);
            return;
        }
        ICommand command = commandMap.get(message.getText().toLowerCase());
        log.info(message.getFrom().getUserName() + Constant.REQUEST.getConstant());
        if (command == null) sendMsg.execute(message, ConstantError.UNKNOWN_COMMAND.gerError());
        else command.execute(message);
    }
}