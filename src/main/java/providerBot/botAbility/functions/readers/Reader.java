package providerBot.botAbility.functions.readers;

import providerBot.botAbility.essences.*;
import providerBot.botAbility.constants.Commands;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.seekers.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс для дисериализации файлов
 */
public class Reader implements IReader {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Search.class);

    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();

    @Override
    public String deadline(String date, Group group, String message) throws IOException {
        sb.setLength(0);
        String [] checkDiscipline = message.split("\\?" );
        if(checkDiscipline.length != 1) return sb.append(ConstantError.INVALID_DISCIPLINE.gerError()).toString();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            HomeWork deadline = deserializationDeadline(date, group, message, sb);
            StringBuilder builder = new StringBuilder();
            Date date1 = deadline.date.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return builder.append(deadline.group.group)
                    .append(" ")
                    .append(dateFormat.format(date1))
                    .append(" ")
                    .append(deadline.discipline.discipline)
                    .append(":\n")
                    .append(deadline.homeWork).toString();
        }
        return "Такого дедлайна не существует";
    }

    /**
     * Метод для дисериализации дедлайна
     * @param sb        адрес до директории
     * @return          объект после дисериализации дедлайна
     * @throws IOException
     */
    private HomeWork deserializationDeadline(String date, Group group, String message, StringBuilder sb) throws IOException {
        sb.append(group.group)
                .append("_")
                .append(date)
                .append("_")
                .append(message);
        java.io.File file = new java.io.File(sb.toString());
        HomeWork serialize = null;
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                serialize = (HomeWork) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(ConstantError.SERIALIZE.gerError(),e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return serialize;
        }
        return null;
    }

    @Override
    public Group group(String idUser, Commands command) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        java.io.File file = new java.io.File(sb.toString());
        Group serialize = null;
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                serialize = (Group) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(ConstantError.SERIALIZE.gerError(),e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return serialize;
        }
        return null;
    }
    @Override
    public String readDate(String idUser, Commands command) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        java.io.File file = new java.io.File(sb.toString());
        String serialize = null;
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                serialize = (String) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(ConstantError.SERIALIZE.gerError(),e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return serialize;
        }
        return null;
    }
    @Override
    public Discipline discipline(String idUser, Commands command) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        java.io.File file = new java.io.File(sb.toString());
        Discipline serialize = null;
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                serialize = (Discipline) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(ConstantError.SERIALIZE.gerError(),e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return serialize;
        }
        return null;
    }
}
