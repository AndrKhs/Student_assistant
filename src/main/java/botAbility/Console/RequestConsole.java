package botAbility.Console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class RequestConsole implements botConsole {
    private static final Logger log = LoggerFactory.getLogger(RequestConsole.class);

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
            log.error(e.toString());
        }
        objectInputStream.close();
        return date;
    }

    /**
     * Метод для сохранения запроса пользователя
     * @param userId        Уникальный индификатор пользователя
     * @param command       Команда
     * @param input         Запрос
     * @return id пользователя
     * @throws IOException
     */
    @Override
    public String writeRequest(String userId, Object command, String input) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        return userId;
    }
}
