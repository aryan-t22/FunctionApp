package model.basicfnstests;

import model.basicfns.Cosine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CosineTest {
    private Cosine c1;
    private Cosine c2;
    private Cosine c3;

    @BeforeEach
    void setup() {
        c1 = new Cosine(1.0, 1.0, 0.0);
        c2 = new Cosine(3.0, 2.0, 1 / 4.0);
        c3 = new Cosine(1.0, 1.0, 1 / 2.0);
    }

    @Test
    void testConstructor() {
        // Test c1:
        assertEquals(1.0, c1.getA());
        assertEquals(1.0, c1.getStretch());
        assertEquals(0.0, c1.getPhase());
        // Test c2:
        assertEquals(3.0, c2.getA());
        assertEquals(2.0, c2.getStretch());
        assertEquals(1 / 4.0, c2.getPhase());
    }

    @Test
    void testGetName() {
        assertEquals("1.0 * cos(1.0 * (t - 0.0π))", c1.getName("t"));
        assertEquals("3.0 * cos(2.0 * (xy - 0.25π))", c2.getName("xy"));

    }

    @Test
    void testEval() {
        assertEquals(1.0, c1.eval(0.0));
        assertEquals(Math.cos(Math.PI / 4), c1.eval(Math.PI / 4));
        assertEquals(Math.sin(1.0), c3.eval(1.0));
    }
}
