package model.basicfnstests;

import model.basicfns.Polynomial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolynomialTest {
    private Polynomial p1;
    private Polynomial p2;
    private Polynomial p3;

    @BeforeEach
    void setup() {
        p1 = new Polynomial(List.of(0.0));
        p2 = new Polynomial(Arrays.asList(0.0, 1.0));
        p3 = new Polynomial(Arrays.asList(0.25, 0.3, 0.4));
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
    }

    @Test
    void testGetName() {
        assertEquals("0.0", p1.getName());
        assertEquals("0.0 + 1.0 * x^1", p2.getName());
        assertEquals("0.25 + 0.3 * x^1 + 0.4 * x^2", p3.getName());

    }

    @Test
    void testEval() {
        assertEquals(0.0, p1.eval(3.0));
        assertEquals(7.56, p2.eval(7.56));
        assertEquals(2.45, p3.eval(2.0));
    }
}
