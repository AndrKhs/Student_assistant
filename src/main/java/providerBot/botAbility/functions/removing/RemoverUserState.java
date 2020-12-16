package providerBot.botAbility.functions.removing;

import org.telegram.telegrambots.meta.api.objects.Message;
import providerBot.botAbility.constants.Commands;
import providerBot.botAbility.essences.User;

import java.io.File;

public class RemoverUserState implements IRemoverUserState {
    /**
     * Константа для StringBuilder
     */
    private final StringBuilder sb = new StringBuilder();
    @Override
    public void userState(Message message, Commands command) {
        User user = new User(message);
        sb.setLength(0);
        sb.append(System.getProperty("user.dir")).append("\\TimeDataBase\\").append(user.id).append(command);
        File file = new File(sb.toString());
        if (file.exists() && !file.isDirectory())
            file.delete();
    }

    @Override
    public void allUserState(Message message) {
        for (Commands s : Commands.values()) {
            userState(message, s);
        }
    }
}
