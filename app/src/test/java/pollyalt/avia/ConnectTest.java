package pollyalt.avia;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Altana on 17-Dec-17.
 */

public class ConnectTest {
    private Connect testConnect = new Connect();

    @Test
    public void testWrongRequest() throws Exception {
        String request = "";
        String answer = "\"Wrong request\"";
        assertEquals(testConnect.ConnectToServer(request), answer) ;

    }

    @Test
    public void testRightRequest() throws Exception{
        String request = "/?from=Paris&where=Rome&when=2018-01-01";
        String answer = "\"Wrong request\"";
        assertNotEquals(testConnect.ConnectToServer(request), answer);
    }

}
