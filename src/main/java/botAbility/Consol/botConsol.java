package botAbility.Consol;

import java.io.IOException;

/**
 * Нужен для ображении к методам из директории Consol
 */
public interface botConsol {

    String searchRequest(String userId, String commandNumber) throws IOException;

    String deleteRequest(String userId, String commandNumber) throws IOException;

    String readRequest(String user, String commandNumber) throws IOException;

    String writeRequest(String user, String commandNumber, String input) throws IOException;

}