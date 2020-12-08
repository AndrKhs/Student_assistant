package botAbility.FunctionsBot.ProviderBot;

import botAbility.Console.FileConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FilesDeadlineActions implements BotProvider, java.io.Serializable{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(FilesDeadlineActions.class);

    /**
     * Набор сообщений об ошибках
     */
    private final String errorInput = "Неккоректный ввод: ";

    /**
     * Метод для создания группы с последующим созданием дедлайна
     * Создание дедлайна с последующей записью данных
     * @param file      Дата дедлайна, группа, дисциплина
     * @param wrote    Задание или ссылка
     * @return          Дата дедлайна
     */
    public String writeFile(FileConsole file, Object wrote) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(file.getGroupAdd())
                .append("_")
                .append(file.getDataAdd())
                .append("_")
                .append(file.getDisciplineAdd());
        Serialize serialize = new Serialize();
        serialize.HomeWork = wrote;
        serialize.Date = file.getDataAdd();
        serialize.Group = file.getGroupAdd();
        serialize.Discipline = file.getDisciplineAdd();
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(serialize);
        objectOutputStream.close();
        return file.getDataAdd().toString();
    }

    /**
     * Метод для удаления файл с дедлайном
     * @param file      Дата дедлайна, группа, дисциплина
     * @return          Результат запроса
     */
    public String deleteDeadline(FileConsole file) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbDel = new StringBuilder();
        sbDel.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(file.getGroupDelete())
                .append("_")
                .append(file.getDateDelete())
                .append("_")
                .append(file.getDisciplineDelete());
        final File fi = new File(sbDel.toString());
        if (fi.delete()) {
            sb.append("Дедлайн удален");
            return sb.toString();
        } else {
            sb.append(errorInput).append("Дедлайна не существует");
            return sb.toString();
        }
    }

    /**
     * Метод для поиск и чтение дедлайна
     * @param file      Дата дедлайна, группа, дисциплина
     * @return          Содержание дедлайна
     * @throws IOException
     */
    public Object readDeadline(FileConsole file) throws IOException {
        StringBuilder sb = new StringBuilder();
        String [] checkDiscipline = file.getDisciplineDeadline().toString().split("\\?" );
        if(checkDiscipline.length != 1) return sb.append(errorInput).append("Дисциплина").toString();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            Serialize deadline = Deserialization(file, sb);
            StringBuilder builder = new StringBuilder();
            return builder.append(deadline.Group)
                    .append(" ")
                    .append(deadline.Date)
                    .append(" ")
                    .append(deadline.Discipline)
                    .append(":\n")
                    .append(deadline.HomeWork);
        }
        return "Такой дедлайна не существует";
    }

    /**
     * Метод нужен для дисериализации дедлайна
     * @param file      Объект запросов
     * @param sb        адрес до директории
     * @return          объект после дисериализации дедлайна
     * @throws IOException
     */
    private Serialize Deserialization(FileConsole file, StringBuilder sb) throws IOException {
        sb.append(file.getGroupDeadline())
                .append("_")
                .append(file.getDateDeadline())
                .append("_")
                .append(file.getDisciplineDeadline());
        File fi = new File(sb.toString());
        if(fi.exists()){
            Serialize serialize = null;
            FileInputStream fileInputStream = new FileInputStream(sb.toString());
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            try {
                serialize = (Serialize) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                log.error("Ошибка дисериализации",e);
            }
            objectInputStream.close();
            fileInputStream.close();
            return serialize;
        }
        return null;
    }


    /**
     * Метод для записи музыки в файл
     * @param nameMusic     код музыки
     * @return              уведомление что музыка добавлена
     */
    public String writeMusic(String nameMusic) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Music\\").append("music");
        FileInputStream fI = new FileInputStream(sb.toString());
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
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
            return "Музыка добавлена";
        }
        catch (IOException e) {
            log.error("Ошибка прочитывания файла с музыкой",e);
        }
        return "Музыка не добавлена";
    }

    /**
     * Метод для вывода списока групп
     * @return      список групп
     */
    public String searchGroup(){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File groups = new File(sb.toString());
            StringBuilder builder = new StringBuilder();
            String l = null;
            for (String s : groups.list()) {
                String [] group = s.split("_");
                if(group[1].equals(date)) {
                    File fil = new File(sb.append(s).toString());
                    fil.delete();
                }
                else if(!group[0].equals(l)){
                    l = group[0];
                    builder.append(group[0]).append("\n");
                }
            }
            sb.delete(0, sb.length());
            if (builder.toString().length() < 10) {
                sb.append("\n").append("Список акакдемических групп пуст");
                return sb.toString();
            }
            sb.append("\n").append("Cписок академических групп:").append("\n").append(builder.toString());
            return sb.toString();
        }
        return "";
    }

    /**
     * Метод для поиска учебного предмета
     * @param group     Учебная группа
     * @param date      Дедлайн
     * @return          Учебный предмет
     */
    public String searchDiscipline(Object group, String date){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File disciplines = new File(sb.toString());
            String l = null;
            StringBuilder builder = new StringBuilder();
            for (String s : disciplines.list()) {
                String [] discipline = s.split("_");
                if(discipline[0].equals(group))
                    if(discipline[1].equals(date))
                        if(!discipline[2].equals(l)) {
                            l = discipline[2];
                            builder.append(discipline[2]).append("\n");
                        }
            }
            sb.delete(0, sb.length());
            if (builder.toString().length() < 1) {
                sb.append("\n").append("Список дисциплин пуст");
                return sb.toString();
            }
            sb.append("\n").append("Список дисциплин").append("\n").append(builder.toString());
            return sb.toString();
        }
        return "";
    }

    /**
     * Метод для поиска даты дедлайна
     * @param group     Учебная группа
     * @return          Дедлайн
     */
    public String searchDate(Object group){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File files = new File(sb.toString());
            String l = null;
            StringBuilder builder = new StringBuilder();
            for (String s : files.list()) {
                String [] deadline = s.split("_");
                if(deadline[0].equals(group)){
                    if(!deadline[1].equals(l)) {
                        l = deadline[1];
                        builder.append(deadline[1]).append("\n");
                    }
                }
            }
            sb.delete(0, sb.length());
            if (builder.toString().length() < 1) {
                sb.append("\n").append("Список дедлайнов пуст");
                return sb.toString();
            }
            sb.append("\n").append("Список дедлайнов").append("\n").append(builder.toString());
            return sb.toString();
        }
        return "";
    }

    @Override
    public void sendMsg(Message message, String text) {

    }

    @Override
    public void sendAudio(Message message) {

    }
}
