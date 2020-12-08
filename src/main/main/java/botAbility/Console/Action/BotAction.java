package botAbility.Console.Action;

import commands.Command;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

public class BotAction implements ActionBot{
    /**
     * Набор сообщений об ошибках
     */
    final String errorInput = "Неккоректный ввод: ";

    /**
     * Метод для проверки правильности ввода дедлайна
     *
     * @param mes           Текст сообщения пользователя
     * @param message       Сообщение пользователся обратившийся к боту
     * @return              null
     */
    public String checkDate(String mes, Message message) {
        StringBuilder sb = new StringBuilder();
        String year = new SimpleDateFormat("yyyy").format(new java.util.Date());
        final String[] word = mes.split("\\.");
        if (word.length != 3) {
            sb.append(Command.send.errorInput).append("дедлайн");
            Command.send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        final int IntWords1 = Integer.parseInt(word[0]);
        if ((IntWords1 > 31) || (IntWords1 < 1)) {
            sb.append(Command.send.errorInput).append("день");
            Command.send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        final int IntWords2 = Integer.parseInt(word[1]);
        if ((IntWords2 > 12) || (IntWords2 < 1)) {
            sb.append(Command.send.errorInput).append("месяц");
            Command.send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        final int IntWords3 = Integer.parseInt(word[2]);
        if (IntWords3 < Integer.parseInt(year)) {
            sb.append(Command.send.errorInput).append("год");
            Command.send.sendMsg(message, sb.toString());
            sb.delete(0, sb.length());
            return "false";
        }
        for (String wor : word)
            if (wor.length() > 5) {
                sb.append(Command.send.errorInput).append("дедлайн");
                Command.send.sendMsg(message, sb.toString());
                sb.delete(0, sb.length());
                return "false";
            }
        return "True";
    }

    /**
     * Метод нужен для удалении дедлайнов конкретной группы
     * @param message       Сообщение пользователся обратившийся к боту
     */
    public void gropeDelete(Message message, Object action, Object academyGroup){
        StringBuilder sb = new StringBuilder();
        sb.append(System.getProperty("user.dir")).append("\\Files\\");
        String words = message.getText();
        if(action.equals("date")) words = academyGroup.toString();
        String[] number = words.split("-");
        if(number.length == 2){
            String lenOne = number[0];
            String lenTwo = number[1];
            if (lenOne.length() < 4 && lenOne.length() > 1 && lenTwo.length() == 6) {
                Path path = Paths.get(sb.toString());
                if (Files.exists(path)) {
                    File groups = new File(sb.toString());
                    for (String s : groups.list()) {
                        String[] group = s.split("_");
                        if(action.equals("date")){
                            if (group[0].equals(academyGroup)){
                                if (group[1].equals(message.getText())) {
                                    File file = new File(sb.append(s).toString());
                                    if (file.delete()) {
                                        Command.send.sendMsg(message,"Удалил");
                                        return;
                                    }
                                }
                            }
                        }
                        if (group[0].equals(message.getText())) {
                            File file = new File(sb.append(s).toString());
                            if (file.delete()) {
                                Command.send.sendMsg(message,"Удалил");
                                return;
                            }
                        }
                    }
                }
            }
            else Command.send.sendMsg(message, errorInput + "некоректный ввод группы");
        }
        else Command.send.sendMsg(message, errorInput + "некоректный ввод группы");
    }
}
