package bot.seekers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import bot.constants.AppConstants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringJoiner;

/**
 * Класс для поиска файлов
 */
public class Search implements ISearch {
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(Search.class);
    /**
     * Константа для StringBuilder
     */
    private final StringBuilder pathBuilder = new StringBuilder();
    /**
     * Константа даты для проверки
     */
    private final String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    @Override
    public String findGroup(){
        pathBuilder.setLength(0);
        pathBuilder.append(System.getProperty("user.dir")).append("\\Files\\");
        if (Files.exists(Paths.get(pathBuilder.toString()))) {
            File filePaths = new File(pathBuilder.toString());
            StringJoiner joiner = new StringJoiner("\n");
            String replay = null;
            try {
                for (String path : filePaths.list()) {
                    String[] group = path.split("_");
                    if (group[1].equals(date)) {
                        File file = new File(pathBuilder.append(path).toString());
                        if (file.exists() && !file.isDirectory() && file.delete()) break;
                    } else if (!group[0].equals(replay)) {
                        replay = group[0];
                        joiner.add(group[0]);
                    }
                }
                pathBuilder.delete(0, pathBuilder.length());
                if (joiner.toString().length() < 10) {
                    pathBuilder.append("\n").append(AppConstants.LIST_GROUP_NULL.toStringValue());
                    return pathBuilder.toString();
                }
            }catch (NullPointerException e){
                log.error("Error read file", e);
            }
            pathBuilder.append("\n").append(AppConstants.LIST_GROUP.toStringValue()).append("\n").append(joiner.toString());
            return pathBuilder.toString();
        }
        pathBuilder.setLength(0);
        return pathBuilder.append(AppConstants.LIST_GROUP_NULL.toStringValue()).toString();
    }

    @Override
    public String findDiscipline(String group, String date){
        pathBuilder.setLength(0);
        pathBuilder.append(System.getProperty("user.dir")).append("\\Files\\");
        if (Files.exists(Paths.get(pathBuilder.toString()))) {
            File filePaths = new File(pathBuilder.toString());
            String replay = null;
            StringJoiner joiner = new StringJoiner("\n");
            try {
                for (String path : filePaths.list()) {
                    String [] discipline = path.split("_");
                    if(discipline[0].equals(group) && discipline[1].equals(date) && !discipline[2].equals(replay)) {
                        replay = discipline[2];
                        joiner.add(discipline[2]);
                    }
                }
            }catch (NullPointerException e){
                log.error("Error read file", e);
            }
            pathBuilder.delete(0, pathBuilder.length());
            if (joiner.toString().length() < 1) {
                pathBuilder.append("\n").append(AppConstants.LIST_DISCIPLINE_NULL.toStringValue());
                return pathBuilder.toString();
            }
            pathBuilder.append("\n").append(AppConstants.LIST_DISCIPLINE.toStringValue()).append("\n").append(joiner.toString());
            return pathBuilder.toString();
        }
        return "";
    }

    @Override
    public String findDate(String group){
        pathBuilder.setLength(0);
        pathBuilder.append(System.getProperty("user.dir")).append("\\Files\\");
        if (Files.exists(Paths.get(pathBuilder.toString()))) {
            File filePaths = new File(pathBuilder.toString());
            String replay = null;
            StringJoiner joiner = new StringJoiner("\n");
            try {
                for (String path : filePaths.list()) {
                    String[] deadline = path.split("_");
                    if (deadline[0].equals(group.toUpperCase())) {
                        if (!deadline[1].equals(replay)) {
                            replay = deadline[1];
                            joiner.add(deadline[1]);
                        }
                    }
                }
            }catch (NullPointerException e){
                log.error("Error read file", e);
            }
            pathBuilder.delete(0, pathBuilder.length());
            if (joiner.toString().length() < 1) {
                pathBuilder.append("\n").append(AppConstants.LIST_DEADLINE_NULL.toStringValue());
                return pathBuilder.toString();
            }
            pathBuilder.append("\n").append(AppConstants.LIST_DEADLINE.toStringValue()).append("\n").append(joiner.toString());
            return pathBuilder.toString();
        }
        return "";
    }
}
