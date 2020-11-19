package botAbility.FunctionsBot;

import java.io.*;

public class RecordMusic implements FunctionsBot {
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

    /**
     * Записывает в файл музыку
     * @param nameMusic код музыки
     * @return уведомление что музыка добавлена
     * @throws IOException
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
        sb.delete(0,sb.length());
        sb.append(resultStringBuilder.toString()).append(nameMusic).append("@");
        writer.write(sb.toString());
        writer.flush();
        writer.close();
        return "Музыка добавлена";
    }
    }

    @Override
    public String searchGroup() throws IOException {
        return null;
    }
}
