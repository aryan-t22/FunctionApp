package model;

import model.basicfns.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the Function Class
public class FunctionTest {
    static final double DEVIATION = 1E-10;

    String expected;
    BasicFunction bfn1;
    BasicFunction bfn2;
    BasicFunction bfn3;
    BasicFunction bfn4;
    Function fn1;
    Function fn2;
    Function fn3;
    Function fn4;
    Function fn5;
    Function fn6;
    Function fn7;
    Function fn8;

    @BeforeEach
    void setup() {
        expected = "[[1.1752011936438007 + -0.21623624012038 * cos(3.141592653589793 * (x - 0.0π))] "
                + "+ 0.6793261834021016 * sin(3.141592653589793 * (x - 0.0π))]";
        bfn1 = new Exp(1, 1, 0);
        bfn2 = new Cosine(1, 1, 0);
        bfn3 = new Sine(1, 1, 0);
        bfn4 = new Polynomial(Arrays.asList(0.0, 1.0));
        fn1 = new Function(new Exp(1, 1, 0));
        fn2 = new Function(new Cosine(1, 1, 0));
    }

    @Test
    void testConstructor() {
        assertEquals("1.0 * e^(1.0 * (x - 0.0))", fn1.getFnn().getFn().getName("x"));
        assertNull(fn1.getLeft());
        assertNull(fn1.getRight());
    }

    // This method is used in the UI, but not in the model. Hence, it was not covered by the model, and testing
    // is done for thoroughness
    @Test
    void testGetSubIntervalsForThoroughness() {
        assertEquals(Function.DEFAULT_SUBINTERVALS, Function.getSubintervals());
        Function.setSubintervals(2);
        assertEquals(2, Function.getSubintervals());
        Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
    }

    // This method is used in the UI, but not in the model. Hence, it was not covered by the model, and testing
    // is done for thoroughness
    @Test
    void testGetPrecisionForThoroughness() {
        assertEquals(Function.DEFAULT_PRECISION, Function.getPrecision());
        Function.setPrecision(1E-6);
        assertEquals(1E-6, Function.getPrecision());
        Function.setPrecision(Function.DEFAULT_PRECISION);
    }

    @Test
    void testAdd() {
        // Test fn1 + fn2
        fn3 = fn1.add(fn2);
        assertEquals("+", fn3.getFnn().getOperation());
        assertFalse(fn3.getFnn().getIsBasicFunc());
        assertEquals(fn1, fn3.getLeft());
        assertEquals(fn2, fn3.getRight());
        // Test fn3 + fn4
        fn4 = new Function(bfn4);
        fn5 = fn3.add(fn4);
        assertEquals("+", fn5.getFnn().getOperation());
        assertFalse(fn5.getFnn().getIsBasicFunc());
        assertEquals(fn3, fn5.getLeft());
        assertEquals(fn4, fn5.getRight());
    }

    @Test
    void testSub() {
        // Test fn1 - fn2
        fn3 = fn1.sub(fn2);
        assertEquals("-", fn3.getFnn().getOperation());
        assertFalse(fn3.getFnn().getIsBasicFunc());
        assertEquals(fn1, fn3.getLeft());
        assertEquals(fn2, fn3.getRight());
        // Test fn3 - fn4
        fn4 = new Function(bfn4);
        fn5 = fn3.sub(fn4);
        assertEquals("-", fn5.getFnn().getOperation());
        assertFalse(fn5.getFnn().getIsBasicFunc());
        assertEquals(fn3, fn5.getLeft());
        assertEquals(fn4, fn5.getRight());
    }

    @Test
    void testProd() {
        // Test fn1 * fn2
        fn3 = fn1.prod(fn2);
        assertEquals("*", fn3.getFnn().getOperation());
        assertFalse(fn3.getFnn().getIsBasicFunc());
        assertEquals(fn1, fn3.getLeft());
        assertEquals(fn2, fn3.getRight());
        // Test fn3 + fn4
        fn4 = new Function(bfn4);
        fn5 = fn3.prod(fn4);
        assertEquals("*", fn5.getFnn().getOperation());
        assertFalse(fn5.getFnn().getIsBasicFunc());
        assertEquals(fn3, fn5.getLeft());
        assertEquals(fn4, fn5.getRight());
    }

    @Test
    void testCompose() {
        // Test fn1 o fn2
        fn3 = fn1.compose(fn2);
        assertEquals("o", fn3.getFnn().getOperation());
        assertFalse(fn3.getFnn().getIsBasicFunc());
        assertEquals(fn1, fn3.getLeft());
        assertEquals(fn2, fn3.getRight());
        // Test fn3 o fn4
        fn4 = new Function(bfn4);
        fn5 = fn3.compose(fn4);
        assertEquals("o", fn5.getFnn().getOperation());
        assertFalse(fn5.getFnn().getIsBasicFunc());
        assertEquals(fn3, fn5.getLeft());
        assertEquals(fn4, fn5.getRight());
    }

    @Test
    void testName() {
        assertEquals("1.0 * e^(1.0 * (x - 0.0))", fn1.name("x"));
        fn3 = fn1.add(fn2);
        fn4 = fn1.compose(fn2);
        fn5 = new Function(new Polynomial(new ArrayList<>())); // zero polynomial
        assertEquals("[1.0 * e^(1.0 * (t - 0.0)) + 1.0 * cos(1.0 * (t - 0.0π))]", fn3.name("t"));
        assertEquals("1.0 * e^(1.0 * (1.0 * cos(1.0 * (x - 0.0π)) - 0.0))", fn4.name("x"));
        assertEquals("0.0", fn5.name("x"));
        assertEquals("0.0", fn5.name("t"));
    }

    @Test
    void testModify() {
        String name1 = fn1.name("x");
        // Neither bfn2 nor bfn3 are part of fn1
        fn1.modify(bfn2, bfn3);
        assertEquals(name1, fn1.name("x"));

        // bfn1 is part of fn1 - fn1 and fn2 should have the same name (and hence are the same function)
        fn1.modify(bfn1, bfn2);
        assertEquals(fn2.name("x"), fn1.name("x"));
        // Swapping back should make fn1 the same function as before
        fn1.modify(bfn2, bfn1);
        assertEquals(name1, fn1.name("x"));

        // Testing a more complex example
        fn3 = new Function(bfn3);
        fn4 = new Function(bfn4);
        fn5 = fn4.add(fn3).prod(fn4.add(fn2)); // x + (sin(x) * (x + cos(x)))
        fn5.modify(bfn4, bfn1); // swap x with e^x
        String name2 = "[[1.0 * e^(1.0 * (x - 0.0)) + 1.0 * sin(1.0 * (x - 0.0π))] "
                + "* [1.0 * e^(1.0 * (x - 0.0)) + 1.0 * cos(1.0 * (x - 0.0π))]]";
        assertEquals(name2, fn5.name("x"));
    }

    @Test
    void testDivide() {
        // Test fn1 / fn2
        fn3 = fn1.div(fn2);
        assertEquals("/", fn3.getFnn().getOperation());
        assertFalse(fn3.getFnn().getIsBasicFunc());
        assertEquals(fn1, fn3.getLeft());
        assertEquals(fn2, fn3.getRight());
        // Test fn3 / fn4
        fn4 = new Function(bfn4);
        fn5 = fn3.div(fn4);
        assertEquals("/", fn5.getFnn().getOperation());
        assertFalse(fn5.getFnn().getIsBasicFunc());
        assertEquals(fn3, fn5.getLeft());
        assertEquals(fn4, fn5.getRight());
        // Test fn5 / 0.0 (should give same object given by fn5 by design)
        fn6 = new Function(new Polynomial(new ArrayList<>()));
        fn7 = fn5.div(fn6);
        assertEquals(fn5.getFnn(), fn7.getFnn());
        assertEquals(fn5.getLeft(), fn7.getLeft());
        assertEquals(fn5.getRight(), fn7.getRight());
    }

    @Test
    void TestEval() {
        // test with fn2
        assertEquals(1.0, fn2.eval(0), DEVIATION);
        assertEquals(1 / Math.sqrt(2), fn2.eval(Math.PI / 4), DEVIATION);
        // test with fn1 + fn2
        fn3 = fn1.add(fn2);
        assertEquals(2, fn3.eval(0), DEVIATION);
        assertEquals(Math.exp(Math.PI / 2), fn3.eval(Math.PI / 2), DEVIATION);
        // test with f1 o f2
        fn4 = fn1.compose(fn2);
        assertEquals(Math.exp(1.0), fn4.eval(0), DEVIATION);
        assertEquals(1.0, fn4.eval(Math.PI / 2), DEVIATION);
        // test when result is not finite
        fn5 = new Function(bfn3); // sin(x)
        fn6 = new Function(bfn4); // x
        fn7 = fn5.div(fn6); // sin(x) / x
        boolean trial = true;
        try {
            fn7.eval(0);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
        }
    }

    @Test
    void testApproxBy() {
        // approx is tested for the default precision
        Function.setPrecision(Function.DEFAULT_PRECISION);
        // DEVIATION is not used in the below test because both functions are within default precision of one another
        // at x = 0
        assertEquals(0, fn1.approxBy(fn2, 0));
        assertEquals(0, fn1.approxBy(fn2, 0));
        // Testing when the functions do not approximate each other
        assertEquals(Math.exp(Math.PI) + 1, fn1.approxBy(fn2, Math.PI), DEVIATION);
        assertEquals(-1 * Math.exp(Math.PI) - 1, fn2.approxBy(fn1, Math.PI), DEVIATION);
        // test when result is not finite
        fn3 = new Function(bfn4); // x
        fn4 = fn1.div(fn3); // e^x / x
        boolean trial = true;
        try {
            fn4.approxBy(fn1, 0);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
        }
    }

    @Test
    void testIntegrate() {
        Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
        Function.setPrecision(Function.DEFAULT_PRECISION);
        // DEVIATION is not used in the below test because the expected result is within the default precision, for
        // the default subintervals
        assertEquals(2.0, fn2.integrate(-1 * Math.PI / 2, Math.PI / 2));
        // For a more useful case, consider the dirichlet function
        fn3 = new Function(bfn3);
        fn4 = new Function(bfn4);
        fn5 = fn3.div(fn4); // sin(x) / x
        // verified via https://www.wolframalpha.com/
        assertEquals(0.6038481745, fn5.integrate(1, 5), DEVIATION);
        // test when result is not finite
        fn6 = fn1.div(fn4); // e^x / x
        Function.setSubintervals(2); // Forces evaluation at x = 0 on interval [-1,1]
        boolean trial = true;
        try {
            fn6.integrate(-1, 1);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
            Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
        }
    }

    @Test
    void testFourierSineWrongArg() {
        fn3 = new Function(bfn3);
        fn4 = fn3.fourierSine(0, 10);
        assertEquals("0.596094018298453 * sin(3.141592653589793 * (x - 0.0π))", fn4.name("x"));
        fn5 = fn3.fourierSine(1, -2);
        assertEquals("0.596094018298453 * sin(3.141592653589793 * (x - 0.0π))", fn5.name("x"));
        fn6 = fn3.fourierSine(-1, 1);
        assertEquals("0.596094018298453 * sin(3.141592653589793 * (x - 0.0π))", fn6.name("x"));
    }

    @Test
    void testFourierSineMain() {
        // Simple Examples
        fn3 = new Function(bfn3); // sin(x) is its own Fourier Series on [-π, π]
        fn4 = fn3.fourierSine(Math.PI, 10);
        assertEquals("1.0 * sin(1.0 * (x - 0.0π))", fn4.name("x"));
        fn5 = fn2.fourierSine(Math.PI, 3); // sin(mx) and cos(x) are orthogonal for any m (for [-π, π])
        assertEquals("0.0", fn5.name("x"));
        // A more involved example - verified via https://www.wolframalpha.com/ :
        fn6 = fn1.fourierSine(1, 2); // e^x fourier sine series
        String expected1 = "[0.6793261834021016 * sin(3.141592653589793 * (x - 0.0π)) "
                + "+ -0.36483673571712544 * sin(6.283185307179586 * (x - 0.0π))]";
        assertEquals(expected1, fn6.name("x"));
        // test when result is not finite
        fn7 = new Function(new Polynomial(Arrays.asList(0.0, 0.0, 1.0)));
        fn8 = fn1.div(fn7); // e^x / x^2
        Function.setSubintervals(2); // guaranteed to evaluate at 0 on [-1,1] when integrating
        boolean trial = true;
        try {
            fn8.fourierSine(1, 1);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
            Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
        }
    }

    @Test
    void testFourierCosineWrongArg() {
        fn3 = fn1.fourierCosine(0, 10);
        String output = "[1.1752011936438007 + -0.21623624012038 * cos(3.141592653589793 * (x - 0.0π))]";
        assertEquals(output, fn3.name("x"));
        fn4 = fn1.fourierCosine(1, -2);
        assertEquals(output, fn4.name("x"));
        fn5 = fn1.fourierCosine(-1, 1);
        assertEquals(output, fn5.name("x"));
    }

    @Test
    void testFourierCosineMain() {
        // Simple Examples - Similar to the Fourier Sine Case (but swapped)
        fn3 = fn2.fourierCosine(Math.PI, 3); // cos(x) is its own Fourier Cosine Series on [-π, π]
        assertEquals("[0.0 + 1.0 * cos(1.0 * (x - 0.0π))]", fn3.name("x"));
        fn4 = new Function(bfn3); // sin(x) and cos(mx) are orthogonal for any m (for [-π, π])
        fn5 = fn4.fourierCosine(Math.PI, 10);
        assertEquals("0.0", fn5.name("x"));
        // A more involved example - verified via https://www.wolframalpha.com/:
        fn6 = fn1.fourierCosine(1, 2); // e^x fourier sine series
        String expected1 = "[[1.1752011936438007 + -0.21623624012038 * cos(3.141592653589793 * (x - 0.0π))] "
                + "+ 0.05806556991084091 * cos(6.283185307179586 * (x - 0.0π))]";
        assertEquals(expected1, fn6.name("x"));
        // test when result is not finite
        fn7 = fn1.div(new Function(new Polynomial(Arrays.asList(0.0, 0.0, 1.0)))); // e^x / x^2
        Function.setSubintervals(2); // guaranteed to evaluate at 0 on [-1,1] when integrating
        boolean trial = true;
        try {
            fn7.fourierCosine(1, 1);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
            Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
        }
    }

    @Test
    void testFourierFull() {
        // test with an odd function:
        fn3 = new Function(bfn4); // x
        fn4 = fn3.fourierFull(3, 3);
        assertEquals(fn3.fourierSine(3, 3).name("x"), fn4.name("x"));
        // test with an even function:
        fn5 = new Function(new Polynomial(Arrays.asList(0.0, 0.0, 1.0)));
        fn6 = fn5.fourierFull(4, 3);
        assertEquals(fn5.fourierCosine(4, 3).name("x"), fn6.name("x"));
        // more complex example
        fn7 = fn1.fourierFull(1, 1);
        assertEquals(expected, fn7.name("x"));
        // test when result is not finite
        fn8 = fn1.div(new Function(new Polynomial(Arrays.asList(0.0, 0.0, 1.0)))); // e^x / x^2
        Function.setSubintervals(2); // guaranteed to evaluate at 0 on [-1,1] when integrating
        boolean trial = true;
        try {
            fn8.fourierFull(1, 1);
            assertFalse(trial);
        } catch (ArithmeticException e) {
            assertTrue(trial);
            Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
        }
    }
}
