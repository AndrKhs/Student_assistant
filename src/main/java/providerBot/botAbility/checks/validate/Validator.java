package providerBot.botAbility.checks.validate;

import providerBot.botAbility.constants.Commands;
import providerBot.botAbility.functions.seekers.ISearch;
import providerBot.botAbility.functions.seekers.Search;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс проверок
 */
public class Validator implements IValidator {
    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();
    /**
     * Константа для поиска файла
     */
    private final ISearch seekers = new Search();

    @Override
    public boolean isExist (String idUser, Commands command){
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        File file = new File(sb.toString());
        if (file.exists()&& !file.isDirectory()) return true;
        return false;
    }

    @Override
    public boolean checkDay(int day){
        if ((day > 31) || (day < 1)) return false;
        return true;
    }

    @Override
    public boolean checkMonth(int month){
        if ((month > 12) || (month < 1)) return false;
        return true;
    }

    @Override
    public boolean checkYear(int year){
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());
        if (year < Integer.parseInt(currentYear)) return false;
        return true;
    }

    @Override
    public boolean checkFormatDate(String[] dateLength){
        if (dateLength.length != 3) return false;
        return true;

    }

    @Override
    public boolean checkLengthDate(String[] dateLength){
        for (String date : dateLength)
            if (date.length() > 5) return false;
        return true;
    }

}
