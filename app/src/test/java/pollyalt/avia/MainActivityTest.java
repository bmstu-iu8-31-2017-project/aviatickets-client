package pollyalt.avia;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Altana on 02-Dec-17.
 */
public class MainActivityTest {
    MainActivity mainActivity;
    @Before
    public void setUp() throws Exception {
        mainActivity = new MainActivity();
    }

    @Test
    public void onCreate() throws Exception {
        assertNotNull(mainActivity.findViewById(R.id.FromText));
        assertNotNull(mainActivity.findViewById(R.id.WhenText));
        assertNotNull(mainActivity.findViewById(R.id.LookBut));
        assertNotNull(mainActivity.findViewById(R.id.WhereText));
    }

}