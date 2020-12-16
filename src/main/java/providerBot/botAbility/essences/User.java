package providerBot.botAbility.essences;

import org.telegram.telegrambots.meta.api.objects.Message;

public class User {
    public String id;

    public User(Message message){
        id = message.getFrom().getId().toString();
    }
}
