package botAbility.FunctionsBot;

import java.io.IOException;

/**
 * Нужен для обрашению у методам из дериктории FunctionsBot
 */
public interface FunctionsBot {
    String writeFl(String file, String nameHoWor, String writed, String files) throws IOException;
    String searchDeadline(String files) throws IOException;
    String readFl(String file, String files) throws IOException;
    String writeMusic(String nameMusic)throws IOException;
    String searchGroup() throws IOException;
}

