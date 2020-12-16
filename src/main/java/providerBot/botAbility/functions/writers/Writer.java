package providerBot.botAbility.functions.writers;

import org.telegram.telegrambots.meta.api.objects.Message;
import providerBot.botAbility.essences.*;
import providerBot.botAbility.constants.Commands;
import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
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
    private final HomeWork deadline= new HomeWork();
    private final Group group = new Group();
    private final Discipline discipline = new Discipline();

    @Override
    public void request(Message message, Commands command, String input) throws IOException {
        User user = new User(message);
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user.id).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void group(Message message, Commands command, String input) throws IOException {
        User user = new User(message);
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
    public void date(Message message, Commands command, String input) throws IOException{
        User user = new User(message);
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user.id).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void discipline(Message message, Commands command, String input) throws IOException {
        User user = new User(message);
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
    public String deadline(String homeWork, Group group, Discipline discipline, String date) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(group.group)
                .append("_")
                .append(date)
                .append("_")
                .append(discipline.discipline);
        deadline.homeWork = homeWork;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        try {
            cal.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        deadline.date = cal;
        deadline.group.group = group.group;
        deadline.discipline.discipline = discipline.discipline;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(deadline);
        objectOutputStream.close();
        return date;
    }

    @Override
    public String music(String nameMusic) throws IOException {
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
            return Constant.MUSIC_ADDED.getConstant();
        }
        catch (IOException e) {
            log.error(ConstantError.READ_FILE_MUSIC.gerError(),e);
        }
        return ConstantError.MUSIC_NO_ADDED.gerError();
    }
}
