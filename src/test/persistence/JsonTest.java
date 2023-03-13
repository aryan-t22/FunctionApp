package persistence;

import model.BasicFunction;
import model.Function;
import model.basicfns.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Test class for Testing .JSON Objects of Functions. This class tests the .toJson methods in each class except the
// Worklist class, which is tested via JsonReaderTest and JSonWriter test.
public class JsonTest {
    protected void checkFunction(JSONObject json, Function fn) {
        if (fn.getFnn().getIsBasicFunc()) {
            checkBasicFunction(json.getJSONObject("Basic Function"), fn.getFnn().getFn());
        } else {
            assertEquals(json.getString("operation"), fn.getFnn().getOperation());
            checkFunction(json.getJSONObject("left"), fn.getLeft());
            checkFunction(json.getJSONObject("right"), fn.getRight());
        }
    }

    private void checkBasicFunction(JSONObject json, BasicFunction fn) {
        String name = json.getString("name");
        switch (name) {
            case "cos":
                checkCos(json, (Cosine) fn);
                break;
            case "sin":
                checkSin(json, (Sine) fn);
                break;
            case "tan":
                checkTan(json, (Tan) fn);
                break;
            case "exp":
                checkExp(json, (Exp) fn);
                break;
            default:
                checkPolynomial(json, (Polynomial) fn);
                break;
        }
    }

    private void checkCos(JSONObject json, Cosine fn) {
        assertEquals(fn.getAm(), json.getDouble("amp"));
        assertEquals(fn.getStretch(), json.getDouble("stretch"));
        assertEquals(fn.getPhase(), json.get("phase"));
    }

    private void checkSin(JSONObject json, Sine fn) {
        assertEquals(fn.getAm(), json.getDouble("amp"));
        assertEquals(fn.getStretch(), json.getDouble("stretch"));
        assertEquals(fn.getPhase(), json.get("phase"));
    }

    private void checkTan(JSONObject json, Tan fn) {
        assertEquals(fn.getAm(), json.getDouble("am"));
        assertEquals(fn.getStretch(), json.getDouble("stretch"));
        assertEquals(fn.getPhase(), json.get("phase"));
    }

    private void checkExp(JSONObject json, Exp fn) {
        assertEquals(fn.getVscale(), json.getDouble("V scale"));
        assertEquals(fn.getHscale(), json.getDouble("H scale"));
        assertEquals(fn.getHshift(), json.get("H shift"));
    }

    private void checkPolynomial(JSONObject json, Polynomial fn) {
        assertEquals(fn.getDegree(), json.getInt("degree"));
        for (int i = 0; i <= fn.getDegree(); i++) {
            assertEquals(fn.getParams().get(i), json.getDouble(Integer.toString(i)));
        }
    }

    @Test
    void testFunctionToJson() {
        Cosine fn1 = new Cosine(1,1,0);
        Sine fn2 = new Sine(2,5,1);
        Exp fn3 = new Exp(15.689540,96.584646,0.1);
        Function f1 = new Function(fn1);
        Function f2 = new Function(fn2);
        Function f3 = new Function("o", f1, f2);
        JSONObject json = f3.toJson();
        checkFunction(json, f3);
    }

    @Test
    void testCosToJson() {
        Cosine fn1 = new Cosine(1,1,0);
        JSONObject json = fn1.toJson();
        checkCos(json, fn1);
    }

    @Test
    void testSinToJson() {
        Sine fn1 = new Sine(2,5,1);
        JSONObject json = fn1.toJson();
        checkSin(json, fn1);
    }

    @Test
    void testTanToJson() {
        Tan fn1 = new Tan(5,7.99,8.44);
        JSONObject json = fn1.toJson();
        checkTan(json, fn1);
    }

    @Test
    void testExpToJson() {
        Exp fn1 = new Exp(15.689540,96.584646,0.1);
        JSONObject json = fn1.toJson();
        checkExp(json, fn1);
    }

    @Test
    void testPolynomialToJson() {
        Polynomial fn1 = new Polynomial(Arrays.asList(0.0, 0.15, 0.83, 0.84, 0.0, 0.0));
        JSONObject json = fn1.toJson();
        checkPolynomial(json, fn1);
    }
}
