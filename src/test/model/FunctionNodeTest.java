package model;

import model.basicfns.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FunctionNodeTest {
    private FunctionNode fnn1;
    private FunctionNode fnn2;
    private FunctionNode fnn3;
    private FunctionNode fnn4;
    private FunctionNode fnn5;

    @BeforeEach
    void setup() {
        fnn1 = new FunctionNode(new Exp(1,1,0)); // e^x function node
        fnn2 = new FunctionNode("+");
        fnn3 = new FunctionNode("-");
        fnn4 = new FunctionNode("*");
        fnn5 = new FunctionNode("/");
    }

    @Test
    void testChildNodeConstructor() {
        assertEquals("1.0 * e^(1.0 * (x - 0.0))", fnn1.getFn().getName("x"));
        assertTrue(fnn1.getIsBasicFunc());
        assertNull(fnn1.getOperation());
    }

    @Test
    void testParentNodeConstructor() {
        assertEquals("+", fnn2.getOperation());
        assertFalse(fnn2.getIsBasicFunc());
        assertNull(fnn2.getFn());
    }

    @Test
    void testOperateOn() {
        assertEquals(3.0, fnn2.operateOn(1.0, 2.0), Function.DEFAULT_PRECISION) ;
        assertEquals(2.5, fnn3.operateOn(4.7, 2.2), Function.DEFAULT_PRECISION);
        assertEquals(15.3, fnn4.operateOn(5.1, 3.0), Function.DEFAULT_PRECISION);
        assertEquals(0.5, fnn5.operateOn(1.0, 2.0), Function.DEFAULT_PRECISION);
        assertEquals(2.0, fnn5.operateOn(2.0, 1.0), Function.DEFAULT_PRECISION);
        boolean trial = true;
        // Testing division by 0
        try {
            fnn5.operateOn(1.0, 0.0);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
        }
        // Testing a child node/other operations from "+", "-", "*", "/"
        try {
            fnn1.operateOn(1.0, 3.0);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
        }
    }
}
