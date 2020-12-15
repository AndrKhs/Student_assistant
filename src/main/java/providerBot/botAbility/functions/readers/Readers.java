package providerBot.botAbility.functions.readers;

import providerBot.botAbility.essences.*;
import providerBot.botAbility.constants.CommandsEnum;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.seekers.Seekers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс для дисериализации файлов
 */
public class Readers implements IReaders {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Seekers.class);

    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();

    @Override
    public String readDeadline(EndDate date, Group group, String message) throws IOException {
        sb.setLength(0);
        String [] checkDiscipline = message.split("\\?" );
        if(checkDiscipline.length != 1) return sb.append(ConstantError.INVALID_DISCIPLINE.gerError()).toString();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            Deadline deadline = deserializationDeadline(date, group, message, sb);
            StringBuilder builder = new StringBuilder();
            return builder.append(deadline.group.group)
                    .append(" ")
                    .append(deadline.date.Date)
                    .append(" ")
                    .append(deadline.discipline.discipline)
                    .append(":\n")
                    .append(deadline.homeWork.homeWork).toString();
        }
        return "Такого дедлайна не существует";
    }

    /**
     * Метод для дисериализации дедлайна
     * @param sb        адрес до директории
     * @return          объект после дисериализации дедлайна
     * @throws IOException
     */
    private Deadline deserializationDeadline(EndDate date, Group group, String message, StringBuilder sb) throws IOException {
        sb.append(group.group)
                .append("_")
                .append(date.Date)
                .append("_")
                .append(message);
        java.io.File file = new java.io.File(sb.toString());
        Deadline serialize = null;
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                serialize = (Deadline) objectInputStream.readObject();
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
    public Group readGroup(String idUser, CommandsEnum command) throws IOException {
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
    public EndDate readDate(String idUser, CommandsEnum command) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        java.io.File file = new java.io.File(sb.toString());
        EndDate serialize = null;
        if(file.exists()){
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                serialize = (EndDate) objectInputStream.readObject();
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
    public Discipline readDiscipline(String idUser, CommandsEnum command) throws IOException {
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
