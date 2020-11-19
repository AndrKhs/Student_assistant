package botAbility.Consol;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class WriteRequestConsol implements botConsol {
    @Override
    public String searchRequest(String userId, String commandNumber) throws IOException {
        return null;
    }

    @Override
    public String deleteRequest(String userId, String commandNumber) throws IOException {
        return null;
    }

    @Override
    public String readRequest(String user, String commandNumber) throws IOException {
        return null;
    }

    /**
     * создание временного файла с последуещей записью на него данных
     * @param user id пользователя телеграмм
     * @param commandNumber номер запроса
     * @param input ввод который сохранится в временном файле
     * @return возрощает id пользователя
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
