package botAbility.FunctionsBot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetFunctionsBot implements FunctionsBot {
    /**
     * Создание группы с последуюзей создании дедлайна
     * Создание дедлайна с последующей записью данных
     *
     * @param file      Дата дедлайна
     * @param nameHoWor Название учебной дисцеплины
     * @param writed    Задание или ссылка
     * @param files     Имя группы
     * @return Дата дедлайна
     * @throws IOException
     */
    @Override
    public String writeFile(String file, String nameHoWor, String writed, String files) throws IOException {
        String rep = "";
        try {
            rep = readFile(file, files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\").append(files);
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            sb.append("\\").append(file);
            FileWriter writer = new FileWriter(sb.toString());
            sb.delete(0, sb.length());
            sb.append("\n")
                    .append("       ")
                    .append(nameHoWor)
                    .append(":")
                    .append(" ")
                    .append(writed)
                    .append(rep);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            return file;
        }
        File dir = new File(sb.toString());
        boolean created = dir.mkdir();
        if (created) {
            sb.append("\\").append(file);
            FileWriter writer = new FileWriter(sb.toString());
            sb.delete(0, sb.length());
            sb.append("\n")
                    .append("       ")
                    .append(nameHoWor)
                    .append(":")
                    .append(" ")
                    .append(writed)
                    .append(rep);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            return file;
        }
        return "";
    }

    /**
     * Поиск делайна
     * Вывод содержимого дедлайна
     * Удаление не актуального дедалайна
     *
     * @param files Имя группы
     * @return Список дедлайнов с их содержимым или отсутсвие списка дедалйнов
     * @throws IOException
     */
    @Override
    public String searchDeadline(String files) throws IOException {
        StringBuilder sbOne = new StringBuilder();
        StringBuilder sbTwo = new StringBuilder();
        sbOne.append(System.getProperty("user.dir")).append("\\Files\\").append(files);
        Path path = Paths.get(sbOne.toString());
        if (Files.exists(path)) {
            String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date()); // вывод текущей даты
            File fil = new File(sbOne.toString());
            sbTwo.append(System.getProperty("user.dir")).append("\\Files\\").append(files).append("\\").append(date);
            File fi = new File(sbTwo.toString());
            for (File f : fil.listFiles()) {
                if (fi.equals(f))
                    if (f.delete())
                        break;
            }
            StringBuilder builder = new StringBuilder();
            for (String s : fil.list()) {
                readFile(s, files);
                builder.append(s).append(":").append(readFile(s, files)).append("\n");
            }
            sbOne.delete(0, sbOne.length());
            if (builder.toString().length() < 10) {
                sbOne.append("\n").append("Дедлайнов нет");
                return sbOne.toString();
            }
            sbOne.append("\n").append("Cписок дедлайнов:").append("\n").append(builder.toString());
            return sbOne.toString();
        }
        return "Такой группы не существует";
    }

    /**
     * Считывание содержимого дедлайна
     *
     * @param file  Дата дедлайна
     * @param files Имя группы
     * @return Содержимое дедалайна
     * @throws IOException
     */
    @Override
    public String readFile(String file, String files) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\").append(files);
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            sb.append("\\").append(file);
            File fi = new File(sb.toString());
            if (fi.exists()) {
                FileInputStream fI = new FileInputStream(fi);
                StringBuilder resultStringBuilder = new StringBuilder();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(fI))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        resultStringBuilder.append(line).append(" ").append("\n");
                    }
                    return resultStringBuilder.toString();
                }
            }
        }
        return "";
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
    }

    /**
     * Вывод список групп
     *
     * @return список групп
     * @throws IOException
     */
    public String searchGroup() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File fil = new File(sb.toString());
            StringBuilder builder = new StringBuilder();
            for (String s : fil.list()) {
                builder.append(s).append("\n");
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

}
