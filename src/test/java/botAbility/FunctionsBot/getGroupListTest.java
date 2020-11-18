package botAbility.FunctionsBot;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class getGroupListTest {

    @Test
    public void search() {
        StringBuilder sb = new StringBuilder();
        getGroupList groupList = new getGroupList();
        sb.append("\n").append("Cписок академических групп:").append("\n").append("МЕН-292203");
        try {
            assertEquals(sb.toString(), groupList.searchGroup());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}