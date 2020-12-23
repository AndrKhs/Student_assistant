package bot.writers;

import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.*;
import bot.constants.Commands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Класс для сериализации и записи файла
 */
public class Writer implements IWriter {
    /**
     * Константа StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Writer.class);
    /**
     * Набор сущностей
     */
    private final HomeWork deadline = new HomeWork();
    private final Group group = new Group();
    private final Discipline discipline = new Discipline();

    @Override
    public void writeUserState(Message message, Commands command, String input) throws IOException {
        IdUser user = new IdUser(message);
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user.id).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeGroup(Message message, Commands command, String input) throws IOException {
        IdUser user = new IdUser(message);
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user.id).append(command);
        group.group = input;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(group);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeDate(Message message, Commands command, String input) throws IOException {
        IdUser user = new IdUser(message);
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user.id).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeDiscipline(Message message, Commands command, String input) throws IOException {
        IdUser user = new IdUser(message);
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user.id).append(command);
        discipline.discipline = input;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(discipline);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeUser(Message message, String input) throws IOException {
        IdUser userId = new IdUser(message);
        User user = new User();
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\user\\").append(userId.id);
        user.userGroup = input;
        user.userId = userId.id;
        user.userName = message.getFrom().getUserName();
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public String writeDeadline(String homeWork, Group group, Discipline discipline, String date) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(group.group)
                .append("_")
                .append(date)
                .append("_")
                .append(discipline.discipline);
        deadline.setHomeWork(homeWork);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        deadline.setDate(cal);
        deadline.setGroup(group);
        deadline.setDiscipline(discipline);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(deadline);
        objectOutputStream.close();
        return date;
    }

    @Override
    public String writeMusic(String nameMusic) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\Music\\").append("music");
        FileInputStream inputStream = new FileInputStream(sb.toString());
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line);
            }
            FileWriter writer = new FileWriter(sb.toString());
            sb.delete(0, sb.length());
            sb.append(resultStringBuilder.toString()).append(nameMusic).append("@");
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            return AppConstants.MUSIC_ADDED.toStringValue();
        } catch (IOException e) {
            log.error(AppErrorConstants.READ_FILE_MUSIC.toStringValue(), e);
        }
        return AppErrorConstants.MUSIC_NO_ADDED.toStringValue();
    }
}
