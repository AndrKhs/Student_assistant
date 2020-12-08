package botAbility.FunctionsBot.BotLogic;

import botAbility.Console.FileConsole;
import botAbility.Console.Action.ActionBot;
import botAbility.Console.Action.BotAction;
import commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.util.HashMap;

/**
 * Класс для работы с вводом пользователя, так же добавляет возможность использовать кнопики вместо написания комманд
 */
public class BotLogic implements BotCommunication {

    /**
     * набор действий бота в диалоге
     */
    private ActionBot action = new BotAction();

    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(BotLogic.class);

    /**
     * Набор сообщений об ошибках
     */
    final String errorUnknowCommand = "Неизвестная команда \uD83D\uDC68\u200D\uD83D\uDCBB\uD83E\uDDD1\u200D\uD83D\uDCBB";

    /**
     * Конструктор HashMap команд
     */
    private static final HashMap<String, Command> map = new HashMap<>();
    public BotLogic() {
        setCommands();
    }
    public void setCommands() {
        map.put("/start", new Start());
        map.put("помощь", new Help());
        map.put("добавить", new Add());
        map.put("удалить дедлайн", new DeleteDeadline());
        map.put("случайная музыка", new RandomMusic());
        map.put("дедлайн", new Deadline());
        map.put("назад", new Back());
        map.put("удалить группу с дедлайнами", new DeleteGroup());
        map.put("удалить дедлайн по дате", new DeleteWholeDate());
        map.put("удалить", new Delete());
    }

    /**
     * Метод для обращения к методам работы бота
     * @param message       сообщение пользователя обратившийся к боту
     */
    public void consoleRecordingDeadline(Message message) {
        String idUser = message.getFrom().getId().toString();
        String words = message.getText().toLowerCase();
        StringBuilder sb = new StringBuilder();
        if (map.containsKey(words))
            Command.request.deleteAllRequest(message);
        try {
            if (Command.request.searchRequest(idUser, Commands.DateDelete).equals(idUser + Commands.DateDelete)) {
                FileConsole file = new FileConsole();
                log.info(message.getFrom().getUserName() + " Выполняет удаление дедлайна");
                file.setDateDelete(Command.request.readRequest(idUser, Commands.DateDelete));
                file.setDisciplineDelete(message.getText());
                file.setGroupDelete(Command.request.readRequest(idUser, Commands.GroupDelete));

                Command.send.sendMsg(message, Command.getGroupList.deleteDeadline(file));

                Command.request.deleteAllRequest(message);
                return;
            }
            if (Command.request.searchRequest(idUser, Commands.DateDeadline).equals(idUser + Commands.DateDeadline)) {
                FileConsole file = new FileConsole();
                log.info(message.getFrom().getUserName() + " Выполняет прочтением дедлайна");
                file.setDateDeadline(Command.request.readRequest(idUser, Commands.DateDeadline));
                file.setDisciplineDeadline(message.getText());
                file.setGroupDeadline(Command.request.readRequest(idUser, Commands.GroupDeadline));

                Command.send.sendMsg(message, Command.getGroupList.readDeadline(file).toString());

                Command.request.deleteAllRequest(message);
                return;

            }
            if (Command.request.searchRequest(idUser, Commands.DisciplineAdd).equals(idUser + Commands.DisciplineAdd)) {
                FileConsole file = new FileConsole();
                log.info(message.getFrom().getUserName() + " Выполняет создание дедлайна");
                file.setDataAdd(Command.request.readRequest(idUser, Commands.DateAdd));
                file.setDisciplineAdd(Command.request.readRequest(idUser, Commands.DisciplineAdd));
                file.setGroupAdd(Command.request.readRequest(idUser, Commands.GroupAdd));

                sb.append("Дедлайн создан ").append(Command.getGroupList.writeFile(file, message.getText()));
                Command.send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                Command.request.deleteAllRequest(message);
                return;
            }
        } catch (IOException e) {
            log.error("Ошибка с (Созданием, удалением, прочтением) дедлайна",e);
        }
        try {
            if (Command.request.searchRequest(idUser, Commands.DateAdd).equals(idUser + Commands.DateAdd)) {
                log.info(message.getFrom().getUserName() + " Пишет дисциплину");
                Command.request.writeRequest(idUser, Commands.DisciplineAdd.toString(), message.getText().toLowerCase());
                sb.append("Напишите мне что вам задали по ").append(Command.request.readRequest(idUser, Commands.DisciplineAdd));
                Command.send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return;
            }
            if (Command.request.searchRequest(idUser, Commands.DateDelete).equals(idUser + Commands.DateDelete)) {
                log.info(message.getFrom().getUserName() + " Пишет дисциплину");
                Command.request.writeRequest(idUser, Commands.DisciplineDelete, message.getText().toLowerCase());
                return;
            }
            if (Command.request.searchRequest(idUser, Commands.DateDeadline).equals(idUser + Commands.DateDeadline)) {
                log.info(message.getFrom().getUserName() + " Пишет дисциплину");
                Command.request.writeRequest(idUser, Commands.DisciplineDeadline, message.getText().toLowerCase());
                return;
            }

        } catch (IOException e) {
            log.error("Ошибка с созданием запроса дисциплины",e);
        }
        analyzeDate(message);
    }

    /**
     * Метод для временного хранение введенной даты для создание дедлайна
     * @param message       Сообщение пользователся обратившийся к боту
     */
    private void analyzeDate(Message message) {
        String idUser = message.getFrom().getId().toString();
        String mes = message.getText();
        if (Command.request.searchRequest(idUser, Commands.GroupAdd).equals(idUser + Commands.GroupAdd)) {
            log.info(message.getFrom().getUserName() + " Пишет дату");
            analyzeDateCheck(idUser, Commands.GroupAdd, mes, message, Commands.DateAdd, Commands.Add);
            return;
        }
        if (Command.request.searchRequest(idUser, Commands.GroupDelete).equals(idUser + Commands.GroupDelete)) {
            log.info(message.getFrom().getUserName() + " Пишет дату");
            analyzeDateCheck(idUser, Commands.GroupDelete, mes, message, Commands.DateDelete, Commands.Delete);
            return;
        }
        if (Command.request.searchRequest(idUser, Commands.GroupDeadline).equals(idUser + Commands.GroupDeadline)) {
            log.info(message.getFrom().getUserName() + " Пишет дату");
            analyzeDateCheck(idUser, Commands.GroupDeadline, mes, message, Commands.DateDeadline, Commands.Deadline);
            return;
        }
        if (Command.request.searchRequest(idUser, Commands.DeleteGroupWholeDate).equals(idUser + Commands.DeleteGroupWholeDate)) {
            log.info(message.getFrom().getUserName() + " удаляет дедлайн по дате ");
            try {
                action.gropeDelete(message, "date", Command.request.readRequest(idUser, Commands.DeleteGroupWholeDate));
            } catch (IOException e) {
                log.error("Ошибка в удалении файла",e);
            }
            Command.request.deleteAllRequest(message);
            return;
        }
        analyzeGroup(message);
    }

    private void analyzeDateCheck(String idUser, Commands a, String mes, Message message, Commands b, Commands c){
        if (action.checkDate(mes, message).equals("false")) return;
        Command.request.writeRequestDeadline(b, a, c, message);
        Command.request.deleteRequest(idUser, c.toString());
    }

    /**
     * Метод
     * @param message   Сообщение пользователся обратившийся к боту
     */
    private void analyzeGroup(Message message) {
        String idUser = message.getFrom().getId().toString();
        if (Command.request.searchRequest(idUser, Commands.Deadline).equals(idUser + Commands.Deadline)) {
            log.info(message.getFrom().getUserName() + " Пишет группу");
            Command.request.writeRequestGroup(Commands.GroupDeadline, message);
            return;
        }
        if (Command.request.searchRequest(idUser, Commands.Delete).equals(idUser + Commands.Delete)) {
            log.info(message.getFrom().getUserName() + " Пишет группу");
            Command.request.writeRequestGroup(Commands.GroupDelete, message);
            return;
        }
        if (Command.request.searchRequest(idUser, Commands.Add).equals(idUser + Commands.Add)) {
            log.info(message.getFrom().getUserName() + " Пишет группу");
            Command.request.writeRequestGroup(Commands.GroupAdd, message);
            return;
        }
        if (Command.request.searchRequest(idUser, Commands.DeleteWholeDate).equals(idUser + Commands.DeleteWholeDate)) {
            log.info(message.getFrom().getUserName() + " Пишет группу");
            Command.request.writeRequestGroup(Commands.DeleteGroupWholeDate, message);
            return;
        }
        if(Command.request.searchRequest(idUser, Commands.DeleteGroup).equals(idUser + Commands.DeleteGroup)) {
            log.info(message.getFrom().getUserName() + " Удаляет группу");
            action.gropeDelete(message, "", "");
            return;
        }
        checkCommand(message);
    }

    /**
     * Метод для проверки команды в HashMap
     * @param message       Сообщение пользователся обратившийся к боту
     */
    private void checkCommand(Message message) {
        Command command = map.get(message.getText().toLowerCase());
        log.info(message.getFrom().getUserName() + " Запрашивает одну из функций бота");
        if (command == null) Command.send.sendMsg(message, errorUnknowCommand);
        else command.execute(message);
    }

}