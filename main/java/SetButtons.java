import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class SetButtons implements BotAI{
    @Override
    public String consoleRecordingDeadline(Message message) {
        return null;
    }

    /**
     * Устанавливает кнопки в телеграмм чат
     * @param sendMessage сообщение бота для пользователя
     */
    public synchronized void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Помощь"));
        keyboardFirstRow.add(new KeyboardButton("Удалить"));
        keyboardFirstRow.add(new KeyboardButton("Дедлайн"));
        keyboardFirstRow.add(new KeyboardButton("Добавить"));
        keyboardSecondRow.add(new KeyboardButton("Случайная музыка"));
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    @Override
    public String deadLine(Message message) {
        return null;
    }
}
