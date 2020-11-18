package botAbility.FunctionsBot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SearchDeadline implements FunctionsBot {

    @Override
    public String writeFl(String file, String nameHoWor, String writed, String files) throws IOException {
        return null;
    }

    /**
     * Поиск делайна
     * Вывод содержимого дедлайна
     * Удаление не актуального дедалайна
     * @param files Имя группы
     * @return Список дедлайнов с их содержимым или отсутсвие списка дедалйнов
     * @throws IOException
     */
    public String searchDeadline(String files) throws IOException {
        ReadHomeWork read = new ReadHomeWork();
        StringBuilder sbZero = new StringBuilder();
        StringBuilder sbOne = new StringBuilder();
        StringBuilder sbTwo= new StringBuilder();
        sbZero.append(System.getProperty("user.dir")).append("\\Files\\").append(files);
        Path path = Paths.get(sbZero.toString());
        if (Files.exists(path)) {
            String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date()); // вывод текущей даты
            sbOne.append(System.getProperty("user.dir")).append("\\Files\\").append(files);
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
                read.readFl(s, files);
                builder.append(s).append(":").append(read.readFl(s, files)).append("\n");
            }
            sbOne.delete(0, sbOne.length());
            if (builder.toString().length() < 10) {
                sbOne.append("\n").append("Дедлайнов нет");
                return sbOne.toString();
            }
            sbOne.append("\n").append("Cписок дедлайнов:").append("\n").append(builder.toString());
            return sbOne.toString();
        }
        return "";
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
