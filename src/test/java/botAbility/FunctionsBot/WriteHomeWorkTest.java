package botAbility.FunctionsBot;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.apache.http.client.methods.RequestBuilder.delete;
import static org.junit.Assert.*;

public class WriteHomeWorkTest {

    @Test
    public void writeFl() {
        WriteHomeWork write = new WriteHomeWork();
        StringBuilder builder = new StringBuilder();
        builder.append(System.getProperty("user.dir")).append("\\Files\\МЕН-292203\\Test");
        File fi = new File(builder.toString());
        try {
            String test = write.writeFl("Test", "test", "test", "МЕН-292203");
            if (fi.delete())
            assertEquals("Test", test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}