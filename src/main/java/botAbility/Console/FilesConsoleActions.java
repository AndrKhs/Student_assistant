package botAbility.Console;

import botAbility.FunctionsBot.BotLogic.Commands;
import commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;

public class FilesConsoleActions implements botConsole {
    private static final Logger log = LoggerFactory.getLogger(FilesConsoleActions.class);

    /**
     * Набор сообщений об ошибках
     */
    private final String errorInput = "Неккоректный ввод: ";

    /**
     * Метод для поиска запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Если запрос был найден - возращает имя файла с запросом(userId + commandNumber)
     */
    @Override
    public String searchRequest(String userId, Object command) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        File file = new File(sb.toString());
        if (file.exists()) {
            return userId + command;
        } else file.delete();
        return "";
    }

    /**
     * Метод для удаления запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       команда
     */
    @Override
    public void deleteRequest(String userId, Object command) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        File file = new File(sb.toString());
        file.delete();
    }

    /**
     * Метод для прочитать содержание запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @return              Содержание запроса
     * @throws IOException
     */
    @Override
    public Object readRequest(String userId, Object command) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        FileInputStream fileInputStream = new FileInputStream(sb.toString());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object date = "";
        try {
            date = objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            log.error("Не получилось прочитать файл",e);
        }
        objectInputStream.close();
        return date;
    }

    /**
     * Метод для сохранения запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @param input         Запрос
     * @throws IOException
     */
    @Override
    public void writeRequest(String userId, Object command, String input) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
    }

    /**
     * Метод нужен для удалении всех запросов пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     */
    public void deleteAllRequest(Message message) {
        String idUser = message.getFrom().getId().toString();
        for (int i = 0; i < Commands.values().length; i++)
            Command.request.deleteRequest(idUser, Commands.valueOf(i));
    }

    /**
     * Метод для сохранения учебной группы введенным пользователем
     * @param command       Команда запроса
     * @param message       Сообщение пользователся обратившийся к боту
     */
    public void writeRequestGroup(Object command, Message message) {
        String words = message.getText();
        String idUser = message.getFrom().getId().toString();
        String[] number = words.split("-");
        if(number.length == 2){
            String lenOne = number[0];
            String lenTwo = number[1];
            try {
                if (lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                    Command.request.writeRequest(idUser, command, words);
                    Command.send.sendMsg(message, Command.getGroupList.searchDate(words));
                    Command.send.sendMsg(message, "Напишите мне ДД.ММ.ГГГГ дедлайна");
                }
            } catch (IOException e) {
                log.error("Ошибка создании группы",e);
            }
        }
        else {
            Command.send.sendMsg(message, errorInput + "Группа");
            deleteAllRequest(message);
        }
    }

    /**
     * Метод для сохранения дедлайна введенным пользователем
     * @param date              Дедлайн
     * @param group             Учебная группа
     * @param requestUser       Запрос пользователя
     * @param message           Сообщение пользователся обратившийся к боту
     */
    public void writeRequestDeadline(Object date, Object group, Object requestUser, Message message) {
        String mes = message.getText();
        String idUser = message.getFrom().getId().toString();
        try {
            try {
                Command.request.writeRequest(idUser, date, mes);
            } catch (IOException e) {
                log.error(e.toString());
            }
            Command.send.sendMsg(message, Command.getGroupList.searchDiscipline(Command.request.readRequest(idUser, group), mes));
            Command.send.sendMsg(message, "Напишите мне название учебной дисциплины");
            Command.request.deleteRequest(idUser, requestUser);
        } catch (IOException e) {
            log.error("Ошибка создании даты",e);
        }
    }
}
