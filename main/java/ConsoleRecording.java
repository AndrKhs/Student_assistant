import botAbility.Consol.*;
import botAbility.FunctionsBot.WriteHomeWork;
import botAbility.FunctionsBot.FunctionsBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;

public class ConsoleRecording implements BotAI{
    /**
     *  Метод сохраняет ввод пользоватеся для дальнейшей обработки
     *  А так же обрабатывает их что позволяет боту более правильно работать с дедлайнами
     * @param message сообщение пользователся обратившийся к боту
     * @return
     */
    public String consoleRecordingDeadline(Message message){
        FunctionsBot write = new WriteHomeWork();
        Bot send = new Bot();
        BotAI dl = new setDeadline();
        botConsol readRequest = new ReadRequestConsol();
        botConsol writeRequest = new WriteRequestConsol();
        botConsol searchRequest = new SearchRequestConsol();
        botConsol deleteRequset = new DeleteRequestConsol();
        StringBuilder sb = new StringBuilder();
        String idUser = message.getFrom().getId().toString();
        String words = message.getText().toLowerCase();
        if (words.equals("помощь") || words.equals("удалить") || words.equals("добавить") || words.equals("дедлайн") || words.equals("/start") || words.equals("случайная музыка"))
        {
            try {
                for (Integer k = 0; k != 6; k++) {
                    deleteRequset.deleteRequest(idUser, k.toString());
                    deleteRequset.deleteRequest(idUser, "9");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            if (searchRequest.searchRequest(idUser, "5").equals(idUser + "5")) {
                sb.append("Дедлайн создан ").append(write.writeFl(readRequest.readRequest(idUser ,"4"),
                        readRequest.readRequest(idUser,"5"), message.getText(),
                        readRequest.readRequest(idUser ,"3")));
                send.sendMsg(message, sb.toString());
                sb.delete(0,sb.length());
                try {
                    for (Integer k = 3; k != 6; k++) {
                        deleteRequset.deleteRequest(idUser, k.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "break";
            }
            if (searchRequest.searchRequest(idUser, "4").equals(idUser + "4"))
            {
                try {
                    writeRequest.writeRequest(idUser, "5", message.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append("Напишите мне что вам задали по ").append(readRequest.readRequest(idUser,"5"));
                send.sendMsg(message, sb.toString());
                sb.delete(0,sb.length());
                return "break";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (searchRequest.searchRequest(idUser, "2").equals(idUser + "2") || searchRequest.searchRequest(idUser, "3").equals(idUser + "3")) {
                if (dl.deadLine(message).equals("break"))
                    return "break";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
      public void setButtons(SendMessage sendMessage) {

    }

    @Override
    public String deadLine(Message message) {
        return null;
    }

}
