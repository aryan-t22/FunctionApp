package model.basicfnstests;

import model.basicfns.Sine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SineTest {
    private Sine s1;
    private Sine s2;
    private Sine s3;
    private Sine s4;

    @BeforeEach
    void setup() {
        s1 = new Sine(1.0, 1.0, 0.0);
        s2 = new Sine(3.0, 2.0, 1 / 4.0);
        s3 = new Sine(1.0, 1.0, -1 / 2.0);
        s4 = new Sine(0.0, 6.0, 1.0);
    }

    @Test
    void testConstructor() {
        // Test s1:
        assertEquals(1.0, s1.getAm());
        assertEquals(1.0, s1.getStretch());
        assertEquals(0.0, s1.getPhase());
        // Test s4:
        assertEquals(1.0, s4.getAm());
        assertEquals(6.0, s4.getStretch());
        assertEquals(1.0, s4.getPhase());
    }

    @Test
    void testGetName() {
        assertEquals("1.0 * sin(1.0 * (t - 0.0π))", s1.getName("t"));
        assertEquals("3.0 * sin(2.0 * (x - 0.25π))", s2.getName("x"));
    }

    @Test
    void testEval() {
        assertEquals(0.0, s1.eval(0.0));
        assertEquals(Math.sin(Math.PI / 4), s1.eval(Math.PI / 4));
        assertEquals(Math.cos(1.0), s3.eval(1.0));
    }
}
