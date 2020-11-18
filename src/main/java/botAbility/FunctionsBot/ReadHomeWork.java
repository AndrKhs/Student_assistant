package botAbility.FunctionsBot;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadHomeWork implements FunctionsBot {
    @Override
    public String writeFl(String file, String nameHoWor, String writed, String files) throws IOException {
        return null;
    }

    @Override
    public String searchDeadline(String files) throws IOException {
        return null;
    }

    /**
     * Считывание содержимого дедлайна
     * @param file Дата дедлайна
     * @param files имя группы
     * @return содержимое дедалайна
     * @throws IOException
     */
    @Override
    public String readFl(String file, String files) throws IOException {
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

    @Override
    public String writeMusic(String nameMusic) throws IOException {
        return null;
    }

    @Override
    public String searchGroup() throws IOException {
        return null;
    }
}