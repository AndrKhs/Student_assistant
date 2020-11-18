import botAbility.Consol.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class setDeadline implements BotAI{
    @Override
    public String consoleRecordingDeadline(Message message) {
        return null;
    }

    @Override
    public void setButtons(SendMessage sendMessage) {
    }

    /**
     * Нужен для временного хранение введенной даты для создание дедлайна
     * @param message Сообщение пользователся обратившийся к боту
     * @return возрощает "break" для последущего игнорирование switch case
     */
    public String deadLine(Message message){
        String idUser = message.getFrom().getId().toString();
        botConsol readRequest = new ReadRequestConsol();
        botConsol writeRequest = new WriteRequestConsol();
        botConsol searchRequest = new SearchRequestConsol();
        botConsol deleteRequset = new DeleteRequestConsol();
        Bot send = new Bot();
        String mes = message.getText();
        StringBuilder sb = new StringBuilder();
        String year = new SimpleDateFormat("yyyy").format(new Date());
        final String[] word = mes.split("\\.");
        try {
            sb.append(idUser).append("2");
            if (searchRequest.searchRequest(idUser, "2").equals(sb.toString())){
                String files = readRequest.readRequest(idUser, "2");
                sb.delete(0,sb.length());
                StringBuilder sbDel = new StringBuilder();
                sbDel.append(System.getProperty("user.dir")).append("\\Files\\").append(files).append("\\").append(mes);
                final File fi = new File(sbDel.toString());
                if (fi.delete()) {
                    sb.append("Дедлайн удален");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0,sb.length());
                } else {
                    sb.append(send.errorOne).append("Дедлайна не существует");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0,sb.length());
                }
                deleteRequset.deleteRequest(idUser,"0");
                return "break";
            }
            sb.delete(0,sb.length());
            sb.append(idUser).append("3");
            if (searchRequest.searchRequest(idUser, "3").equals(sb.toString())) {
                sb.delete(0,sb.length());
                if (word.length != 3) {
                    sb.append(send.errorOne).append("дедлайн");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0,sb.length());
                    return "break";
                }
                final int IntWords1 = Integer.parseInt(word[0]);
                if ((IntWords1 > 31) || (IntWords1 < 1)) {
                    sb.append(send.errorOne).append("день");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0,sb.length());
                    return "break";
                }
                final int IntWords2 = Integer.parseInt(word[1]);
                if ((IntWords2 > 12) || (IntWords2 < 1)) {
                    sb.append(send.errorOne).append("месяц");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0,sb.length());
                    return "break";
                }
                final int IntWords3 = Integer.parseInt(word[2]);
                if (IntWords3 < Integer.parseInt(year)) {
                    sb.append(send.errorOne).append("год");
                    send.sendMsg(message, sb.toString());
                    sb.delete(0,sb.length());
                    return "break";
                }
                for (String wor : word)
                    if (wor.length() > 5) {
                        sb.append(send.errorOne).append("дедлайн");
                        send.sendMsg(message, sb.toString());
                        sb.delete(0,sb.length());
                        return "break";
                    }
                try {
                    writeRequest.writeRequest(idUser, "4", mes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                send.sendMsg(message, "Напишите мне название дисциплины, по которому вам что то задали");
                deleteRequset.deleteRequest(idUser,"1");
                return "break";
            }   sb.delete(0,sb.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
