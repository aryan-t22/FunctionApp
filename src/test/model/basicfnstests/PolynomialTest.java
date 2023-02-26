package model.basicfnstests;

import model.basicfns.Polynomial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests for the Polynomial Class
public class PolynomialTest {
    private Polynomial p1;
    private Polynomial p2;
    private Polynomial p3;
    private Polynomial p4;

    @BeforeEach
    void setup() {
        p1 = new Polynomial(Arrays.asList(0.0));
        p2 = new Polynomial(Arrays.asList(0.0, 1.0));
        p3 = new Polynomial(Arrays.asList(0.25, 0.3, 0.4));
        p4 = new Polynomial(Arrays.asList(0.0, 0.0, 0.0, 0.0));
    }

    @Test
    void testConstructor() {
        // Test p1:
        assertEquals(1.0, p1.getParams().size());
        assertEquals(0.0, p1.getParams().get(0));
        // Test p2:
        assertEquals(2.0, p2.getParams().size());
        assertEquals(0.0, p2.getParams().get(0));
        assertEquals(1.0, p2.getParams().get(1));
        // Test p3:
        assertEquals(3.0, p3.getParams().size());
        assertEquals(0.25, p3.getParams().get(0));
        assertEquals(0.3, p3.getParams().get(1));
        assertEquals(0.4, p3.getParams().get(2));
        // Test p4:
        assertEquals(1.0, p4.getParams().size());
        assertEquals(0.0, p4.getParams().get(0));
    }

    @Test
    void testGetDegree() {
        assertEquals(0, p1.getDegree());
        assertEquals(1, p2.getDegree());
        assertEquals(2, p3.getDegree());
        assertEquals(0, p4.getDegree());
    }

    @Test
    void testGetName() {
        assertEquals("0.0", p1.getName("x"));
        assertEquals("0.0", p1.getName("cos(x)"));
        assertEquals("0.0 + 1.0 * r^1", p2.getName("r"));
        assertEquals("0.25 + 0.3 * (sin(x))^1 + 0.4 * (sin(x))^2", p3.getName("sin(x)"));
        assertEquals("0.25 + 0.3 * (xy)^1 + 0.4 * (xy)^2", p3.getName("xy"));

    }

    @Test
    void testEval() {
        assertEquals(0.0, p1.eval(3.0));
        assertEquals(7.56, p2.eval(7.56));
        assertEquals(2.45, p3.eval(2.0));
    }
}

