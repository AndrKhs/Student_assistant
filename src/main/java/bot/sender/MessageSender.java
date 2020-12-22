package bot.sender;

import bot.checks.validate.IVerification;
import bot.checks.validate.Verification;
import bot.constants.AppCommands;
import bot.constants.AppConstants;
import bot.constants.AppErrorConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import bot.model.User;
import bot.readers.IReader;
import bot.readers.Reader;
import bot.seekers.ISearch;
import bot.seekers.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для отправки сообщений
 */
public class MessageSender extends Send{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);

    /**
     * Константа для проверки
     */
    private final IVerification validate = new Verification();
    private final IReader read = new Reader();

    private final ISearch searcher = new Search();

    /**
     * Метод для отправки сообщения
     * @param message   Сообщение пользователся обратившийся к боту
     * @param text      Текст для ответа на сообщение пользователя
     */
    @Override
    public void execute(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage, message);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(AppErrorConstants.SEND_MESSAGE.toStringValue(),e);
        }
    }


    /**
     * Метод для обновление клавиатуры бота
     * @param sendMessage
     */
    private synchronized void setButtons(SendMessage sendMessage, Message message) {
        String userId = message.getFrom().getId().toString();
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardTriadRow = new KeyboardRow();
        User user = null;
        try {
            user = read.readUserDate(userId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(validate.isExistUser(userId)) {
            if (validate.isExist(userId, AppCommands.DateDeadline)
                    || validate.isExist(userId, AppCommands.DateAdd)
                    || validate.isExist(userId, AppCommands.DateDelete)) {
                keyboardFirstRow.clear();
                keyboardSecondRow.clear();
                keyboardTriadRow.clear();
                String[] discipline = searcher.findDiscipline(user.getGroup().group.toUpperCase(), message.getText())
                        .split("\n");
                String replay = "";
                for (int i = 2; i < discipline.length; i++) {
                    if (!replay.equals(discipline[i])) {
                        keyboardFirstRow.add(new KeyboardButton(discipline[i]));
                        replay = discipline[i];
                    }
                }
                keyboardTriadRow.add(new KeyboardButton("Назад"));
            }
            if (validate.isExist(userId, AppCommands.Add)
                    || validate.isExist(userId, AppCommands.Deadline)
                    ||validate.isExist(userId, AppCommands.Delete)
                    || validate.isExist(userId, AppCommands.GroupDelete)
                    || validate.isExist(userId, AppCommands.DeleteWholeDate)) {
                keyboardFirstRow.clear();
                keyboardSecondRow.clear();
                keyboardTriadRow.clear();
                String[] date = searcher.findDate(user.getGroup().group).split("\n");
                String replay = "";
                for (int i = 2; i < date.length; i++) {
                    if (!replay.equals(date[i])) {
                        keyboardFirstRow.add(new KeyboardButton(date[i]));
                        replay = date[i];
                    }
                }
                keyboardSecondRow.add(new KeyboardButton(AppConstants.BUTTON_BACK.toStringValue()));
            }
            if (validate.isExist(userId, AppCommands.CommandDelete)) {
                keyboardFirstRow.clear();
                keyboardSecondRow.clear();
                keyboardTriadRow.clear();
                keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_REMOVE_DATE.toStringValue()));
                keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_REMOVE_DEADLINE.toStringValue()));
                keyboardSecondRow.add(new KeyboardButton("Удалить данные о группе"));
                keyboardTriadRow.add(new KeyboardButton(AppConstants.BUTTON_BACK.toStringValue()));
            }
            if (validate.isExist(userId, AppCommands.Weather)) {
                keyboardFirstRow.clear();
                keyboardSecondRow.clear();
                keyboardTriadRow.clear();
                keyboardSecondRow.add(new KeyboardButton(AppConstants.BUTTON_BACK.toStringValue()));
            }
            if (!validate.isExist(userId, AppCommands.Back)) {
                keyboardFirstRow.clear();
                keyboardSecondRow.clear();
                keyboardTriadRow.clear();
                keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_HELP.toStringValue()));
                keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_HOMEWORK.toStringValue()));
                keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_ADD.toStringValue()));
                keyboardSecondRow.add(new KeyboardButton(AppConstants.BUTTON_RANDOM_MUSIC.toStringValue()));
                keyboardTriadRow.add(new KeyboardButton(AppConstants.BUTTON_REMOVE.toStringValue()));
                keyboardSecondRow.add(new KeyboardButton("Орел и Решка"));
                keyboardSecondRow.add(new KeyboardButton("Погода"));
            }
        }else {
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardTriadRow.clear();
            keyboardSecondRow.add(new KeyboardButton(" "));
        }
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardTriadRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
