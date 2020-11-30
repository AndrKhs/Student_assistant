package botAbility.FunctionsBot;

import botAbility.FileRequest;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateBaseBot implements BotFunctions {
    private final static Logger log = Logger.getLogger(DateBaseBot.class);
    final String errorInput = "Неккоректный ввод: ";
    /**
     * Создание группы с последуюзей создании дедлайна
     * Создание дедлайна с последующей записью данных
     *
     * @param file      Дата дедлайна
     * @param writed    Задание или ссылка
     * @return Дата дедлайна
     * @throws IOException
     */
    @Override
    public String writeFile(FileRequest file, String writed) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\").append(file.getGroupAdd()).append("_").append(file.getDataAdd()).append("_").append(file.getDisciplineAdd());
        FileOutputStream outputStream = new FileOutputStream(sb.toString());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject("\n"+writed);
        objectOutputStream.close();
        return file.getDataAdd();
    }

    @Override
    public String deleteFile(FileRequest file) {
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

    @Override
    public String searchDeadline(FileRequest file) throws IOException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbDate = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
            File fil = new File(sb.toString());
            sb.append(file.getGroupDeadline())
                    .append("_")
                    .append(file.getDateDeadline())
                    .append("_")
                    .append(file.getDisciplineDeadline());
            sbDate.append(System.getProperty("user.dir"))
                    .append("\\Files\\")
                    .append(file.getGroupDeadline())
                    .append("_")
                    .append(date)
                    .append("_")
                    .append(file.getDisciplineDeadline());
            File fi = new File(sb.toString());
            if(fi.equals(sbDate.toString())) fi.delete();
            StringBuilder builder = new StringBuilder();
            builder.append(file.getGroupDeadline()).append(" ").append(file.getDateDeadline()).append(" ").append(file.getDisciplineDeadline()).append(":").append(readFile(sb.toString()));
            return builder.toString();
        }
        return "Такой группы не существует";
    }

    /**
     * Считывание содержимого дедлайна
     *
     * @param file  путь до дедлайна
     * @return Содержимое дедалайна
     * @throws IOException
     */
    private String readFile(String file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        String date = "";
        try {
            date = objectInputStream.readObject().toString();
        } catch (ClassNotFoundException e) {
            log.error(e);
            e.printStackTrace();
        }
        objectInputStream.close();
        return date;
    }

    /**
     * Записывает в файл музыку
     *
     * @param nameMusic код музыки
     * @return уведомление что музыка добавлена
     * @throws IOException
     */
    @Override
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
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Вывод список групп
     *
     * @return список групп
     * @throws IOException
     */
    @Override
    public String searchGroup(){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File fil = new File(sb.toString());
            StringBuilder builder = new StringBuilder();
            String l = null;
            for (String s : fil.list()) {
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

    @Override
    public String searchDiscipline(String group, String date){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File fil = new File(sb.toString());
            StringBuilder builder = new StringBuilder();
            for (String s : fil.list()) {
                String [] deadline = s.split("_");
                if(deadline[0].equals(group))
                    if(deadline[1].equals(date))
                        builder.append(deadline[2]).append("\n");

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

    @Override
    public String searchDate(String group){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File fil = new File(sb.toString());
            String l = null;
            StringBuilder builder = new StringBuilder();
            for (String s : fil.list()) {
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
}
