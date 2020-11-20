package botAbility.FunctionsBot;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SearchDeadlineTest {

    @Test
    public void searchDeadline() {
        StringBuilder sb = new StringBuilder();
        FunctionsBot search = new GetFunctionsBot();
        sb.append("\n").append("Дедлайнов нет");
        try {
            assertEquals(sb.toString(), search.searchDeadline("МЕН-292203"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}