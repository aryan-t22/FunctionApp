package model.basicfnstests;

import model.basicfns.Exp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpTest {
    private Exp e1;
    private Exp e2;

    @BeforeEach
    void setup() {
        e1 = new Exp(1.0, 1.0, 0.0);
        e2 = new Exp(3.0, 4.0, 1 / 4.0);
    }

    @Test
    void testConstructor() {
        // Test e1:
        assertEquals(1.0, e1.getVscale());
        assertEquals(1.0, e1.getHscale());
        assertEquals(0.0, e1.getHshift());
        // Test e2:
        assertEquals(3.0, e2.getVscale());
        assertEquals(4.0, e2.getHscale());
        assertEquals(1 / 4.0, e2.getHshift());
    }

    @Test
    void testGetName() {
        assertEquals("1.0 * e^(1.0 * (x - 0.0))", e1.getName("x"));
        assertEquals("3.0 * e^(4.0 * (cos(x) - 0.25))", e2.getName("cos(x)"));

    }

    @Test
    void testEval() {
        assertEquals(1.0, e1.eval(0.0));
        assertEquals(3.0 * Math.exp(3.0), e2.eval(1.0));
    }
}
