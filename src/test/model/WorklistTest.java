package model;

import model.basicfns.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the Worklist class
class WorklistTest {
    BasicFunction bfn1;
    BasicFunction bfn2;
    BasicFunction bfn3;
    Function fn1;
    Function fn2;
    Function fn3;
    Function fn4;
    Function fn5;
    Worklist wl1;

    @BeforeEach
    void setup() {
        bfn1 = new Polynomial(Arrays.asList(2.0, 3.0, 4.0));
        bfn2 = new Exp(1, 1, 0);
        bfn3 = new Sine(2, 3, 1);
        fn1 = new Function(bfn1);
        fn2 = new Function(bfn2);
        fn3 = new Function(bfn3);
        fn4 = fn1.add(fn2);
        fn5 = fn2.add(fn3);
        wl1 = new Worklist();
    }

    @Test
    void testConstructor() {
        assertEquals(0, wl1.getListFn().size());
    }

    @Test
    void testInsertFn() {
        assertEquals(0, wl1.getListFn().size());
        wl1.insertFunc(fn1);
        assertEquals(1, wl1.getListFn().size());
        assertEquals(fn1, wl1.getListFn().get(0));
        wl1.insertFunc(fn2);
        assertEquals(2, wl1.getListFn().size());
        assertEquals(fn1, wl1.getListFn().get(0));
        assertEquals(fn2, wl1.getListFn().get(1));
    }

    @Test
    void testLength() {
        assertEquals(0, wl1.length());
        wl1.insertFunc(fn1);
        assertEquals(1, wl1.length());
        wl1.insertFunc(fn2);
        wl1.insertFunc(fn2);
        assertEquals(3, wl1.length());
    }

    @Test
    void testIsEmpty() {
        assertTrue(wl1.isEmpty());
        wl1.insertFunc(fn1);
        assertFalse(wl1.isEmpty());
    }


    @Test
    void testRemoveFn() {
        assertEquals(0, wl1.length());
        wl1.removeFunc(fn1);
        assertEquals(0, wl1.length());
        wl1.insertFunc(fn3);
        wl1.insertFunc(fn1);
        wl1.insertFunc(fn3);
        // Worklist should have 3 functions - f3, f1 and f3
        wl1.removeFunc(fn2);
        assertEquals(3, wl1.length()); // unchanged
        wl1.removeFunc(fn3);
        assertEquals(2, wl1.length());
        assertEquals(fn1, wl1.getListFn().get(0));
        assertEquals(fn3, wl1.getListFn().get(1));
    }

    @Test
    void testNames() {
        assertEquals("The worklist is empty.", wl1.names());
        wl1.insertFunc(fn1);
        String output = "Here are the functions in your worklist: \n" + "[2.0 + 3.0 * x^1 + 4.0 * x^2].";
        assertEquals(output, wl1.names());
        wl1.insertFunc(fn4);
        output = output.substring(0, output.length() - 1)
                + ", [[2.0 + 3.0 * x^1 + 4.0 * x^2 + 1.0 * e^(1.0 * (x - 0.0))]].";
        assertEquals(output, wl1.names());
    }

    @Test
    void testHasBasicFn() {
        // We check the empty list, then add a non-basic function, a basic function twice, a non-basic function, and
        // remove the basic functions. At each step we check the output.
        assertFalse(wl1.hasBasicFn());
        wl1.insertFunc(fn4);
        assertFalse(wl1.hasBasicFn());
        wl1.insertFunc(fn1);
        assertTrue(wl1.hasBasicFn());
        wl1.insertFunc(fn2);
        assertTrue(wl1.hasBasicFn());
        wl1.removeFunc(fn1);
        assertTrue(wl1.hasBasicFn());
        wl1.removeFunc(fn2);
        assertFalse(wl1.hasBasicFn());
    }
}