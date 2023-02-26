package model.basicfnstests;

import model.basicfns.Tan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TanTest {
    private Tan t1;
    private Tan t2;
    private Tan t3;

    @BeforeEach
    void setup() {
        t1 = new Tan(1.0, 1.0, 0.0);
        t2 = new Tan(6.0, 4.0, 1 / 5.0);
        t3 = new Tan(0.0, 2.0, -1.0);
    }

    @Test
    void testConstructor() {
        // Test t1:
        assertEquals(1.0, t1.getAm());
        assertEquals(1.0, t1.getStretch());
        assertEquals(0.0, t1.getPhase());
        // Test t3:
        assertEquals(1.0, t3.getAm());
        assertEquals(2.0, t3.getStretch());
        assertEquals(-1.0, t3.getPhase());
    }

    @Test
    void testGetName() {
        assertEquals("1.0 * tan(1.0 * (t - 0.0π))", t1.getName("t"));
        assertEquals("6.0 * tan(4.0 * (x - 0.2π))", t2.getName("x"));
    }

    @Test
    void testEval() {
        assertEquals(0.0, t1.eval(0.0));
        assertEquals(6 * Math.tan(Math.PI / 5), t2.eval(Math.PI / 4));
    }
}
