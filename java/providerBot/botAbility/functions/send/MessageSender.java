package bot.sender;

import bot.checks.validate.IValidator;
import bot.checks.validate.Validator;
import bot.constants.Commands;
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
import bot.model.IdUser;

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
    private final IValidator validate = new Validator();

    /**
     * Метод для отправки сообщения
     * @param message   Сообщение пользователся обратившийся к боту
     * @param text      Текст для ответа на сообщение пользователя
     */
    @Override
    public void execute(Message message, String text) {
        IdUser user = new IdUser(message);
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage, user.id);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(AppErrorConstants.SEND_MESSAGE.toStringValue(),e);
        }
    }


    /**
     * Метод для обновление клавиатуры бота
     * @param sendMessage
     * @param idUser
     */
    private synchronized void setButtons(SendMessage sendMessage, String idUser) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        KeyboardRow keyboardTriadRow = new KeyboardRow();
        if (validate.isExist(idUser, Commands.Add) || validate.isExist(idUser, Commands.Deadline)
                || validate.isExist(idUser, Commands.Delete) || validate.isExist(idUser, Commands.DeleteGroupFirst)
                || validate.isExist(idUser, Commands.DeleteWholeDate)) {
            keyboardTriadRow.add(new KeyboardButton("Назад"));
        }
        if (validate.isExist(idUser, Commands.CommandDelete)) {
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardTriadRow.clear();
            keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_REMOVE_GROUP.toStringValue()));
            keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_REMOVE_DATE.toStringValue()));
            keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_REMOVE_DEADLINE.toStringValue()));
            keyboardSecondRow.add(new KeyboardButton(AppConstants.BUTTON_BACK.toStringValue()));
        }
        if (validate.isExist(idUser, Commands.Weather)) {
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardTriadRow.clear();
            keyboardSecondRow.add(new KeyboardButton(AppConstants.BUTTON_BACK.toStringValue()));
        }
        if(!validate.isExist(idUser, Commands.Back)) {
            keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_HELP.toStringValue()));
            keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_DEADLINE.toStringValue()));
            keyboardFirstRow.add(new KeyboardButton(AppConstants.BUTTON_ADD.toStringValue()));
            keyboardSecondRow.add(new KeyboardButton(AppConstants.BUTTON_RANDOM_MUSIC.toStringValue()));
            keyboardTriadRow.add(new KeyboardButton(AppConstants.BUTTON_REMOVE.toStringValue()));
            keyboardSecondRow.add(new KeyboardButton("Орел и Решка"));
            keyboardSecondRow.add(new KeyboardButton("Погода"));
        }
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardTriadRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
