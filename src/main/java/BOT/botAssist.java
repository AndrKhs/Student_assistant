package BOT;

import java.io.IOException;


/**
 * Нужен для обращение к методу bot в классе botAssistent
 */
public interface botAssist {
    String botAI(String Call) throws IOException;
}

