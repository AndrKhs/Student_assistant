package botAbility.Consol;

import java.io.File;
import java.io.IOException;

public class SearchRequestConsol implements botConsol {
    /**
     * поиск временного файла
     * @param userId id пользователя телеграмм
     * @param commandNumber номер запроса
     * @return
     * @throws IOException
     */
    @Override
    public String searchRequest(String userId, String commandNumber) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(commandNumber);
        File fi = new  File(sb.toString());;
        if (fi.exists()) {
            return userId + commandNumber;
        }
        else fi.delete();
        return "";
    }

    @Override
    public String deleteRequest(String userId, String commandNumber) throws IOException {
        return null;
    }

    @Override
    public String readRequest(String user, String commandNumber) throws IOException {
        return null;
    }

    @Override
    public String writeRequest(String user, String commandNumber, String input) throws IOException {
        return null;
    }
}
