package providerBot.botAbility.botLogic;

import providerBot.botAbility.constants.CommandsEnum;
import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.readers.IReaders;
import providerBot.botAbility.functions.send.SendMsg;
import providerBot.botAbility.essences.HomeWork;
import providerBot.botAbility.checks.validate.IValidate;
import providerBot.botAbility.checks.validate.Validate;
import providerBot.botAbility.essences.EndDate;
import providerBot.botAbility.essences.Discipline;
import providerBot.botAbility.essences.Group;
import providerBot.botAbility.functions.removing.IRemoving;
import providerBot.botAbility.functions.removing.Removing;
import providerBot.botAbility.functions.readers.Readers;
import providerBot.botAbility.functions.requests.IRequests;
import providerBot.botAbility.functions.requests.Requests;
import providerBot.botAbility.functions.writers.IWriters;
import providerBot.botAbility.functions.writers.Writers;
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
    private final IValidate validate = new Validate();

    /**
     * Набор работы с файлами
     */
    private final IRemoving remove = new Removing();
    private final IWriters write = new Writers();
    private final IReaders read = new Readers();
    private final IRequests request = new Requests();

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
        String idUser = message.getFrom().getId().toString();
        String words = message.getText().toLowerCase();
        StringBuilder sb = new StringBuilder();
        if (commandMap.containsKey(words))
            remove.deleteAllRequest(message);
        try {
            if (validate.isExist(idUser, CommandsEnum.DateDelete)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_CREATE.getConstant());
                EndDate date = read.readDate(idUser, CommandsEnum.DateDelete);
                Group group = read.readGroup(idUser, CommandsEnum.GroupDelete);
                sendMsg.execute(message, remove.deleteDeadline(date, group, message.getText()));

                remove.deleteAllRequest(message);
                return;
            }
            if (validate.isExist(idUser, CommandsEnum.DateDeadline)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_CREATE.getConstant());
                EndDate date = read.readDate(idUser, CommandsEnum.DateDeadline);
                Group group = read.readGroup(idUser, CommandsEnum.GroupDeadline);
                sendMsg.execute(message, read.readDeadline(date, group, message.getText()));

                remove.deleteAllRequest(message);
                return;

            }
            if (validate.isExist(idUser, CommandsEnum.DisciplineAdd)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_CREATE.getConstant());

                HomeWork homeWork = new HomeWork();
                homeWork.homeWork = message.getText();
                Group group = read.readGroup(idUser, CommandsEnum.GroupAdd);
                EndDate date = read.readDate(idUser, CommandsEnum.DateAdd);
                Discipline discipline = read.readDiscipline(idUser, CommandsEnum.DisciplineAdd);

                sb.append("Дедлайн создан ")
                        .append(write.writeDeadline(homeWork,
                                group, discipline, date));
                sendMsg.execute(message, sb.toString());
                sb.delete(0, sb.length());
                remove.deleteAllRequest(message);
                return;
            }
        } catch (IOException e) {
            log.error(ConstantError.READ_WRITE_DELETE_DEADLINE.gerError(),e);
        }
        try {
            if (validate.isExist(idUser, CommandsEnum.DateAdd)) {
                log.info(message.getFrom().getUserName() + Constant.PERFORMS_DISCIPLINE.getConstant());
                write.writeDiscipline(idUser, CommandsEnum.DisciplineAdd, message.getText());
                sb.append(Constant.WRITE_HOME_WORK).append(message.getText());
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
        String idUser = message.getFrom().getId().toString();
        if (validate.isExist(idUser, CommandsEnum.GroupAdd)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_DATE.getConstant());
            analyzeDateCheck(idUser, CommandsEnum.GroupAdd, CommandsEnum.DateAdd, CommandsEnum.Add, message);
            return;
        }
        if (validate.isExist(idUser, CommandsEnum.GroupDelete)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_DATE.getConstant());
            analyzeDateCheck(idUser, CommandsEnum.GroupDelete, CommandsEnum.DateDelete, CommandsEnum.Delete, message);
            return;
        }
        if (validate.isExist(idUser, CommandsEnum.GroupDeadline)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_DATE.getConstant());
            analyzeDateCheck(idUser, CommandsEnum.GroupDeadline, CommandsEnum.DateDeadline, CommandsEnum.Deadline, message);
            return;
        }
        if (validate.isExist(idUser, CommandsEnum.DeleteGroupWholeDate)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_REMOVE.getConstant());
            try {
                Group group = read.readGroup(idUser, CommandsEnum.DeleteGroupWholeDate);
                remove.groupDelete(message, "date", group.group);
            } catch (IOException e) {
                log.error(ConstantError.REMOVE_FILE.gerError(),e);
            }
            remove.deleteAllRequest(message);
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
    private void analyzeDateCheck(String idUser, CommandsEnum group, CommandsEnum date, CommandsEnum command, Message message){
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
        request.requestDeadline(date, group, command, message);
        remove.deleteRequest(idUser, command);
    }

    /**
     * Метод завершающий проверку состояния пользователя
     * @param message
     */
    private void manipulations(Message message) {
        String idUser = message.getFrom().getId().toString();
        if (validate.isExist(idUser, CommandsEnum.Deadline)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.requestGroup(CommandsEnum.GroupDeadline, message);
            return;
        }
        if (validate.isExist(idUser, CommandsEnum.Delete)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.requestGroup(CommandsEnum.GroupDelete, message);
            return;
        }
        if (validate.isExist(idUser, CommandsEnum.Add)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.requestGroup(CommandsEnum.GroupAdd, message);
            return;
        }
        if (validate.isExist(idUser, CommandsEnum.DeleteWholeDate)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_GROUP.getConstant());
            request.requestGroup(CommandsEnum.DeleteGroupWholeDate, message);
            return;
        }
        if(validate.isExist(idUser, CommandsEnum.DeleteGroup)) {
            log.info(message.getFrom().getUserName() + Constant.PERFORMS_REMOVE_GROUP.getConstant());
            remove.groupDelete(message, "", "");
            return;
        }
        ICommand command = commandMap.get(message.getText().toLowerCase());
        log.info(message.getFrom().getUserName() + Constant.REQUEST.getConstant());
        if (command == null) sendMsg.execute(message, ConstantError.UNKNOWN_COMMAND.gerError());
        else command.execute(message);
    }
}