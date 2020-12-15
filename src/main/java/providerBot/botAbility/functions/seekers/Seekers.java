package providerBot.botAbility.functions.seekers;

import providerBot.botAbility.constants.CommandsEnum;
import providerBot.botAbility.constants.Constant;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс для поиска файлов
 */
public class Seekers implements ISeekers {

    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();

    /**
     * Константа даты для проверки
     */
    private final String date = new SimpleDateFormat("dd.MM.yyyy").format(new Date());

    @Override
    public String searchGroup(){
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File groups = new File(sb.toString());
            StringBuilder builder = new StringBuilder();
            String replay = null;
            for (String list : groups.list()) {
                String [] group = list.split("_");
                if(group[1].equals(date)) {
                    File file = new File(sb.append(list).toString());
                    if (file.exists() && !file.isDirectory() && file.delete()) break;
                }
                else if(!group[0].equals(replay)){
                    replay = group[0];
                    builder.append(group[0]).append("\n");
                }
            }
            sb.delete(0, sb.length());
            if (builder.toString().length() < 10) {
                sb.append("\n").append(Constant.LIST_GROUP_NULL.getConstant());
                return sb.toString();
            }
            sb.append("\n").append(Constant.LIST_GROUP.getConstant()).append("\n").append(builder.toString());
            return sb.toString();
        }
        sb.setLength(0);
        return sb.append(Constant.LIST_GROUP_NULL.getConstant()).toString();
    }

    @Override
    public String searchDiscipline(String group, String date){
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File disciplines = new File(sb.toString());
            String replay = null;
            StringBuilder builder = new StringBuilder();
            for (String s : disciplines.list()) {
                String [] discipline = s.split("_");
                if(discipline[0].equals(group) && discipline[1].equals(date) && !discipline[2].equals(replay)) {
                    replay = discipline[2];
                    builder.append(discipline[2]).append("\n");
                }
            }
            sb.delete(0, sb.length());
            if (builder.toString().length() < 1) {
                sb.append("\n").append(Constant.LIST_DISCIPLINE_NULL.getConstant());
                return sb.toString();
            }
            sb.append("\n").append(Constant.LIST_DISCIPLINE.getConstant()).append("\n").append(builder.toString());
            return sb.toString();
        }
        return "";
    }

    @Override
    public String searchDate(String group){
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        Path path = Paths.get(sb.toString());
        if (Files.exists(path)) {
            File files = new File(sb.toString());
            String replay = null;
            StringBuilder builder = new StringBuilder();
            for (String s : files.list()) {
                String [] deadline = s.split("_");
                if(deadline[0].equals(group)){
                    if(!deadline[1].equals(replay)) {
                        replay = deadline[1];
                        builder.append(deadline[1]).append("\n");
                    }
                }
            }
            sb.delete(0, sb.length());
            if (builder.toString().length() < 1) {
                sb.append("\n").append(Constant.LIST_DEADLINE_NULL.getConstant());
                return sb.toString();
            }
            sb.append("\n").append(Constant.LIST_DEADLINE.getConstant()).append("\n").append(builder.toString());
            return sb.toString();
        }
        return "";
    }

    @Override
    public String searchRequest(String userId, CommandsEnum command) {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(userId).append(command);
        File file = new File(sb.toString());
        if (file.exists()&& !file.isDirectory()) {
            return userId + command;
        }
        return "";
    }
}
