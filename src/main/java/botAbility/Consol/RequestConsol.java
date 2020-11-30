package botAbility.Consol;

import bot.Bot;
import org.apache.log4j.Logger;

import java.io.*;

public class RequestConsol implements botConsol {
    private final static Logger log = Logger.getLogger(RequestConsol.class);
    /**
     * Поиск временного файла
     *
     * @param userId        Id пользователя телеграмм
     * @param commandNumber Номер запроса
     * @return
     * @throws IOException
     */
    @Override
    public String searchRequest(String userId, String commandNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(commandNumber);
        File fi = new File(sb.toString());
        if (fi.exists()) {
            return userId + commandNumber;
        } else fi.delete();
        return "";
    }

    /**
     * Удаление временного файла
     *
     * @param userId        Id пользователся в телеграмме
     * @param commandNumber Номер запроса
     * @return
     * @throws IOException
     */
    @Override
    public String deleteRequest(String userId, String commandNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(commandNumber);
        File fi = new File(sb.toString());
        if (fi.delete()) return "";
        return "";
    }

    /**
     * @param user          Id пользователя телеграмм
     * @param commandNumber Номер запроса
     * @return Данные содержащиеся в временном файле
     * @throws IOException
     */
    @Override
    public String readRequest(String user, String commandNumber) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user).append(commandNumber);
        FileInputStream fileInputStream = new FileInputStream(sb.toString());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String date = "";
        try {
            date = objectInputStream.readObject().toString();
        } catch (ClassNotFoundException e) {
            log.error(e);
            e.printStackTrace();
        }
        objectInputStream.close();
        return date;
    }

    /**
     * Создание временного файла с последуещей записью на него данных
     *
     * @param user          Id пользователя телеграмм
     * @param commandNumber Номер запроса
     * @param input         Ввод который сохранится в временном файле
     * @return Возврашает id пользователя
     * @throws IOException
     */
    @Override
    public String writeRequest(String user, String commandNumber, String input) throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user).append(commandNumber);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        return user;
    }
}
