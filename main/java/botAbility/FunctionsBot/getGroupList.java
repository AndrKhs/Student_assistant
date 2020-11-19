package botAbility.FunctionsBot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class getGroupList implements FunctionsBot {
    @Override
    public String writeFl(String file, String nameHoWor, String writed, String files) throws IOException {
        return null;
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

    /**
     * Вывод список групп
     * @return список групп
     * @throws IOException
     */
    public String searchGroup() throws IOException {
        ReadHomeWork read = new ReadHomeWork();
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
