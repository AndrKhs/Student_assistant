package providerBot.botAbility.functions.send;

import providerBot.botAbility.checks.validate.IValidate;
import providerBot.botAbility.checks.validate.Validate;
import providerBot.botAbility.constants.CommandsEnum;
import providerBot.botAbility.constants.Constant;
import providerBot.botAbility.constants.ConstantError;
import providerBot.botAbility.functions.requests.IRequests;
import providerBot.botAbility.functions.requests.Requests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import providerBot.botAbility.functions.seekers.ISeekers;
import providerBot.botAbility.functions.seekers.Seekers;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для отправки сообщений
 */
public class SendMsg extends Send{
    /**
     * Константа для логирования
     */
    private static final Logger log = LoggerFactory.getLogger(SendMsg.class);

    /**
     * Константа для проверки
     */
    private final IValidate validate = new Validate();

    /**
     * Константа для поиска файла
     */
    private final ISeekers seekers = new Seekers();

    /**
     * Метод для отправки сообщения
     * @param message   Сообщение пользователся обратившийся к боту
     * @param text      Текст для ответа на сообщение пользователя
     */
    @Override
    public void execute(Message message, String text) {
        String idUser = message.getFrom().getId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setText(text);
        try {
            setButtons(sendMessage, idUser);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(ConstantError.SEND_MESSAGE.gerError(),e);
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
        if (validate.isExist(idUser, CommandsEnum.Add) || validate.isExist(idUser, CommandsEnum.Deadline)
                || validate.isExist(idUser, CommandsEnum.Delete) || validate.isExist(idUser, CommandsEnum.DeleteGroup)
                || validate.isExist(idUser, CommandsEnum.DeleteWholeDate)) {
            keyboardTriadRow.add(new KeyboardButton("Назад"));
        }
        if (validate.isExist(idUser, CommandsEnum.CommandDelete)) {
            keyboardFirstRow.clear();
            keyboardSecondRow.clear();
            keyboardTriadRow.clear();
            keyboardFirstRow.add(new KeyboardButton(Constant.BUTTON_REMOVE_GROUP.getConstant()));
            keyboardFirstRow.add(new KeyboardButton(Constant.BUTTON_REMOVE_DATE.getConstant()));
            keyboardFirstRow.add(new KeyboardButton(Constant.BUTTON_REMOVE_DEADLINE.getConstant()));
            keyboardSecondRow.add(new KeyboardButton(Constant.BUTTON_BACK.getConstant()));
        }
        if(!seekers.searchRequest(idUser, CommandsEnum.Back).equals(idUser + CommandsEnum.Back)) {
            keyboardFirstRow.add(new KeyboardButton(Constant.BUTTON_HELP.getConstant()));
            keyboardFirstRow.add(new KeyboardButton(Constant.BUTTON_DEADLINE.getConstant()));
            keyboardFirstRow.add(new KeyboardButton(Constant.BUTTON_ADD.getConstant()));
            keyboardSecondRow.add(new KeyboardButton(Constant.BUTTON_RANDOM_MUSIC.getConstant()));
            keyboardTriadRow.add(new KeyboardButton(Constant.BUTTON_REMOVE.getConstant()));
        }
        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        keyboardRowList.add(keyboardTriadRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }
}
