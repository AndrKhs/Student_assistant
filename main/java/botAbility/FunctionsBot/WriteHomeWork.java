package botAbility.FunctionsBot;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriteHomeWork implements FunctionsBot {
    /**
     * Создание группы с последуюзей создании дедлайна
     * Создание дедлайна с последующей записью данных
     * @param file Дата дедлайна
     * @param nameHoWor Название учебной дисцеплины
     * @param writed Задание или ссылка
     * @param files Имя группы
     * @return Дата дедлайна
     * @throws IOException
     */
    public String writeFl(String file, String nameHoWor, String writed, String files) throws IOException {
        ReadHomeWork read = new ReadHomeWork();
        String rep = "";
        try {
            rep = read.readFl(file, files);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\").append(files);
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            sb.append("\\").append(file);
            FileWriter writer = new FileWriter(sb.toString());
            sb.delete(0,sb.length());
            sb.append("\n").append("       ").append(nameHoWor).append(":").append(" ").append(writed).append(rep);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            return file;
        }
        File dir = new File(sb.toString());
        boolean created = dir.mkdir();
        if(created){
            sb.append("\\").append(file);
            FileWriter writer = new FileWriter(sb.toString());
            sb.delete(0,sb.length());
            sb.append("\n").append("       ").append(nameHoWor).append(":").append(" ").append(writed).append(rep);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
            return file;
        }
        return "";
    }

    @Override
    public String searchDeadline(String files) throws IOException {
        return null;
    }

    @Override
    public String readFl(String file, String files) throws IOException {
        return null;
    }

    @Override
    public String writeMusic(String nameMusic) throws IOException {
        return null;
    }

    @Override
    public String searchGroup() throws IOException {
        return null;
    }


}
