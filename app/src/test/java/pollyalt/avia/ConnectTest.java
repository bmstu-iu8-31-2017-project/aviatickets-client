package pollyalt.avia;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Altana on 17-Dec-17.
 */

public class ConnectTest {
    @Test
    public void testConnectClass() throws Exception {
        Connect testConnect = new Connect();
        String request = "";
        String answer = "\"Wrong request\"";
        assertEquals(testConnect.ConnectToServer(request), answer) ;
        request = "/?from=Paris&where=Rome&when=2018-01-01";
        assertNotEquals(testConnect.ConnectToServer(request), answer);
    }
}
