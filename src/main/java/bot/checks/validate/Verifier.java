package bot.checks.validate;

import bot.constants.AppCommands;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс проверок введенных значений
 */
public class Verifier implements IVerifier {
    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();

    @Override
    public boolean isExist(String idUser, AppCommands command) {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(idUser).append(command);
        File file = new File(sb.toString());
        if (file.exists() && !file.isDirectory()) return true;
        return false;
    }

    @Override
    public boolean isExistUser(String idUser) {
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\user\\").append(idUser);
        File file = new File(sb.toString());
        if (file.exists() && !file.isDirectory()) return true;
        return false;
    }

    @Override
    public boolean checkDay(int day) {
        if ((day > 31) || (day < 1)) return false;
        return true;
    }

    @Override
    public boolean checkMonth(int month) {
        if ((month > 12) || (month < 1)) return false;
        return true;
    }

    @Override
    public boolean checkYear(int year) {
        String currentYear = new SimpleDateFormat("yyyy").format(new Date());
        if (year < Integer.parseInt(currentYear)) return false;
        return true;
    }

    @Override
    public boolean checkFormatDate(String[] dateLength) {
        if (dateLength.length != 3) return false;
        return true;

    }

    @Override
    public boolean checkLengthDate(String[] dateLength) {
        for (String date : dateLength)
            if (date.length() > 5) return false;
        return true;
    }

    @Override
    public boolean checkGroup(String group) {
        String[] number = group.split("-");
        if (number.length == 2) {
            String lenOne = number[0];
            String lenTwo = number[1];
            if (lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) return true;
        }
        return false;
    }

}
