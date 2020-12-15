package providerBot.botAbility.functions.writers;

import providerBot.botAbility.essences.*;
import providerBot.botAbility.constants.CommandsEnum;
import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Класс для сериализации и записи файла
 */
public class Writers implements IWriters {
    /**
     * Константа StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Writers.class);
    /**
     * Набор сущностей
     */
    private final Deadline deadline= new Deadline();
    private final EndDate date = new EndDate();
    private final Group group = new Group();
    private final Discipline discipline = new Discipline();

    @Override
    public void writeRequest(String userId, CommandsEnum command, String input) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(input);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeGroup(String userId, CommandsEnum command, String input) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        group.group = input;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(group);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeDate(String userId, CommandsEnum command, String input) throws IOException{
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        date.Date = input;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(date);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public void writeDiscipline(String userId, CommandsEnum command, String input) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        discipline.discipline = input;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(discipline);
        objectOutputStream.close();
        outputStream.close();
    }

    @Override
    public String writeDeadline(HomeWork homeWork, Group group, Discipline discipline, EndDate date) throws IOException {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(group.group)
                .append("_")
                .append(date.Date)
                .append("_")
                .append(discipline.discipline);
        deadline.homeWork.homeWork = homeWork.homeWork;
        deadline.date.Date = date.Date;
        deadline.group.group = group.group;
        deadline.discipline.discipline = discipline.discipline;
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(deadline);
        objectOutputStream.close();
        return date.Date;
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
            return Constant.MUSIC_ADDED.getConstant();
        }
        catch (IOException e) {
            log.error(ConstantError.READ_FILE_MUSIC.gerError(),e);
        }
        return ConstantError.MUSIC_NO_ADDED.gerError();
    }
}
