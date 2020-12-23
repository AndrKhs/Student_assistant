package bot.readers;

import bot.model.*;
import bot.constants.Commands;
import bot.constants.AppErrorConstants;
import bot.seekers.Search;
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
    public String readDeadline(String date, Group group, String message) throws IOException {
        sb.setLength(0);
        String[] checkDiscipline = message.split("\\?");
        if (checkDiscipline.length != 1) return sb.append(AppErrorConstants.INVALID_DISCIPLINE.toStringValue()).toString();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            HomeWork deadline = deserializationDeadline(date, group, message, sb);
            StringBuilder builder = new StringBuilder();
            Date date1 = deadline.getDate().getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            return builder.append(deadline.getGroup().group)
                    .append(" ")
                    .append(dateFormat.format(date1))
                    .append(" ")
                    .append(deadline.getDiscipline().discipline)
                    .append(":\n")
                    .append(deadline.getHomeWork()).toString();
        }
        return "Такого дедлайна не существует";
    }

    /**
     * Метод для дисериализации дедлайна
     * @param sb адрес до директории
     * @return объект после дисериализации дедлайна
     * @throws IOException
     */
    private HomeWork deserializationDeadline(String date, Group group, String message, StringBuilder sb) throws IOException {
        sb.append(group.group)
                .append("_")
                .append(date)
                .append("_")
                .append(message);
        java.io.File file = new java.io.File(sb.toString());
        HomeWork homeWork = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                homeWork = (HomeWork) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(AppErrorConstants.SERIALIZE.toStringValue(), e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return homeWork;
        }
        return null;
    }

    @Override
    public Group readGroup(String idUser, Commands command) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        java.io.File file = new java.io.File(sb.toString());
        Group group = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                group = (Group) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(AppErrorConstants.SERIALIZE.toStringValue(), e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return group;
        }
        return null;
    }

    @Override
    public String readDate(String idUser, Commands command) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        java.io.File file = new java.io.File(sb.toString());
        String date = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                date = (String) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(AppErrorConstants.SERIALIZE.toStringValue(), e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return date;
        }
        return null;
    }

    @Override
    public User readUser(String idUser) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\user\\").append(idUser);
        java.io.File file = new java.io.File(sb.toString());
        User user = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                user = (User) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(AppErrorConstants.SERIALIZE.toStringValue(), e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return user;
        }
        return null;
    }

    @Override
    public Discipline readDiscipline(String idUser, Commands command) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        java.io.File file = new java.io.File(sb.toString());
        Discipline discipline = null;
        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                discipline = (Discipline) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error(AppErrorConstants.SERIALIZE.toStringValue(), e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return discipline;
        }
        return null;
    }
}
