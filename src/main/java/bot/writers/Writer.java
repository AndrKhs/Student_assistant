package bot.writers;

import bot.constants.AppCommands;
import org.telegram.telegrambots.meta.api.objects.Message;
import bot.model.*;
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
    private final HomeWork homeWork = new HomeWork();
    private final Group group = new Group();
    private final Discipline discipline = new Discipline();
    private final User user = new User();

    @Override
    public void writeUserDate(Message message, String input) throws IOException {
        String idUser = message.getFrom().getId().toString();
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\user\\").append(idUser);
        group.group = input.toUpperCase();

        user.setId(idUser);
        user.setUserName(message.getFrom().getUserName());
        user.setGroup(group);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(user);
        objectOutputStream.close();
    }

    @Override
    public void writeUserState(Message message, AppCommands command, String input, User userDate) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userDate.getId()).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeGroup(Message message, AppCommands command, User userDate) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userDate.getId()).append(command);
        group.group = userDate.getGroup().group;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(group);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeDate(Message message, AppCommands command, String input, User userDate) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userDate.getId()).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeDiscipline(Message message, AppCommands command, String input, User userDate) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userDate.getId()).append(command);
        discipline.discipline = input;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(discipline);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public String writeDeadline(String homeWork, User userDate, Discipline discipline, String date) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(userDate.getGroup().group)
                .append("_")
                .append(date)
                .append("_")
                .append(discipline.discipline);
        this.homeWork.setHomeWork(homeWork);
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.homeWork.setDate(cal);
        this.homeWork.setDiscipline(discipline);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(this.homeWork);
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
