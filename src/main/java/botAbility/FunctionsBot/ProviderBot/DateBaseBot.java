package botAbility.FunctionsBot.ProviderBot;

import botAbility.Consol.FileRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateBaseBot implements BotProvider{
    /**
     * Константа для логирования
     */
    private static final Logger log = LogManager.getLogger(DateBaseBot.class);

    /**
     * Набор сообщений об ошибках
     */
    private final String errorInput = "Неккоректный ввод: ";

    /**
     * Метод для создания группы с последующим созданием дедлайна
     * Создание дедлайна с последующей записью данных
     * @param file      Дата дедлайна, группа, дисциплина
     * @param writed    Задание или ссылка
     * @return          Дата дедлайна
     */
    public String writeFile(FileRequest file, String writed) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(file.getGroupAdd())
                .append("_")
                .append(file.getDataAdd())
                .append("_")
                .append(file.getDisciplineAdd().toLowerCase());
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject("\n"+writed);
        objectOutputStream.close();
        return file.getDataAdd();
    }

    /**
     * Метод для удаления файл с дедлайном
     * @param file      Дата дедлайна, группа, дисциплина
     * @return          Результат запроса
     */
    public String deleteDeadline(FileRequest file) {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbDel = new StringBuilder();
        sbDel.append(System.getProperty("user.dir"))
                .append("\\Files\\")
                .append(file.getGroupDelete())
                .append("_")
                .append(file.getDateDelete())
                .append("_")
                .append(file.getDisciplineDelete().toLowerCase());
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
    public String readDeadline(FileRequest file) throws IOException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbDate = new StringBuilder();
        String [] checkDiscipline = file.getDisciplineDeadline().split("\\?" );
        if(checkDiscipline.length != 1) return sb.append(errorInput).append("Дисциплина").toString();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
            sb.append(file.getGroupDeadline())
                    .append("_")
                    .append(file.getDateDeadline())
                    .append("_")
                    .append(file.getDisciplineDeadline().toLowerCase());
            sbDate.append(System.getProperty("user.dir"))
                    .append("\\Files\\")
                    .append(file.getGroupDeadline())
                    .append("_")
                    .append(date)
                    .append("_")
                    .append(file.getDisciplineDeadline().toLowerCase());
            File fi = new File(sb.toString());
            if(fi.exists()){
                if(fi.equals(sbDate.toString())) fi.delete();
                StringBuilder builder = new StringBuilder();
                builder.append(file.getGroupDeadline())
                        .append(" ")
                        .append(file.getDateDeadline())
                        .append(" ")
                        .append(file.getDisciplineDeadline().toLowerCase())
                        .append(":")
                        .append(readFile(sb.toString()));
                return builder.toString();
            }
        }
        return "Такой дедлайна не существует";
    }

    /**
     * Метод для считывания содержимого дедлайна
     * @param file      путь до дедлайна
     * @return          Содержимое дедалайна
     */
    private String readFile(String file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String date = "";
        try {
            date = objectInputStream.readObject().toString();
        } catch (ClassNotFoundException e) {
            log.error(e);
        }
        objectInputStream.close();
        return date;
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
            log.error(e);
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
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File groups = new File(sb.toString());
            StringBuilder builder = new StringBuilder();
            String l = null;
            for (String s : groups.list()) {
                String [] group = s.split("_");
                if(!group[0].equals(l)){
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
    public String searchDiscipline(String group, String date){
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
    public String searchDate(String group){
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
    public void sendAudio(Message message) throws FileNotFoundException {

    }
}
