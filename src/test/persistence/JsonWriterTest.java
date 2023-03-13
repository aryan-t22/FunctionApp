package persistence;

import model.Function;
import model.Worklist;
import model.basicfns.Cosine;
import model.basicfns.Exp;
import model.basicfns.Sine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Tests for the JsonWriter Class which writes the worklist to a .JSON file
public class JsonWriterTest extends JsonTest {
    Cosine fn1;
    Sine fn2;
    Exp fn3;
    Function f1;
    Function f2;
    Function f3;
    Worklist wl1;

    @BeforeEach
    void setUp() {
        fn1 = new Cosine(1,1,0);
        fn2 = new Sine(2,5,1);
        fn3 = new Exp(15.689540,96.584646,0.1);
        f1 = new Function(fn1);
        f2 = new Function(fn2);
        f3 = new Function("o", f1, f2);
        wl1 = new Worklist();
        wl1.insertFunc(f1);
        wl1.insertFunc(f3);
    }

    @Test
    void testWriterInvalidFile() {
        try {
            Worklist wl = new Worklist();
            JsonWriter writer = new JsonWriter("./data/worklist\17.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorklist() {
        try {
            Worklist wl = new Worklist();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorklist.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorklist.json");
            wl = reader.read();
            assertEquals(0, wl.length());
            assertEquals(Function.DEFAULT_PRECISION, Function.getPrecision());
            assertEquals(Function.DEFAULT_SUBINTERVALS, Function.getSubintervals());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorklist() {
        try {
            Function.setPrecision(1E-14);
            Function.setSubintervals(9000);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorklist.json");
            writer.open();
            writer.write(wl1);
            writer.close();

            Function.setPrecision(Function.DEFAULT_PRECISION);
            Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
            JsonReader reader = new JsonReader("./data/testWriterGeneralWorklist.json");
            wl1 = reader.read();
            assertEquals(1E-14, Function.getPrecision());
            assertEquals(9000, Function.getSubintervals());
            List<Function> functions = wl1.getListFn();
            assertEquals(2, functions.size());
            checkFunction(f1.toJson(), functions.get(0));
            checkFunction(f3.toJson(), functions.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
