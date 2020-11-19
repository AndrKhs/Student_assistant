package botAbility.Consol;

import java.io.*;


public class ReadRequestConsol implements botConsol {

    @Override
    public String searchRequest(String userId, String commandNumber) throws IOException {
        return null;
    }

    @Override
    public String deleteRequest(String userId, String commandNumber) throws IOException {
        return null;
    }

    /**
     * 
     * @param user id пользователя телеграмм
     * @param commandNumber номер запроса
     * @return данные содержащиеся в временном файле
     * @throws IOException
     */
    @Override
        public String readRequest(String user, String commandNumber) throws IOException{
            StringBuilder sb = new StringBuilder();
            sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user).append(commandNumber);
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            String date = "";
            try {
                date = objectInputStream.readObject().toString();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            objectInputStream.close();
            return date;
        }

    @Override
    public String writeRequest(String user, String commandNumber, String input) throws IOException {
        return null;
    }
}