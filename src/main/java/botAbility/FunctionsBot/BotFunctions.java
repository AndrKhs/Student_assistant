package botAbility.FunctionsBot;

import botAbility.FileRequest;

import java.io.IOException;

/**
 * Нужен для обрашению у методам из дериктории FunctionsBot
 */
public interface BotFunctions {
    String writeFile(FileRequest file, String writed) throws IOException;

    String searchDeadline(FileRequest file) throws IOException;

    String writeMusic(String nameMusic) throws IOException;

    String deleteFile(FileRequest file);

    String searchGroup() ;

    String searchDiscipline(String group, String date);

    String searchDate(String group);
}

