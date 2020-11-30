package bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileNotFoundException;

public interface BotMain {

    void sendMsg(Message message, String text);

    void sendAudio(Message message) throws FileNotFoundException;

    void setButtons(SendMessage sendMessage);
}
