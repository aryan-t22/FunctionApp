package persistence;

import model.Function;
import model.Worklist;
import model.basicfns.*;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// // Tests for the JsonReader Class which reads the worklist from a .JSON file
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/wrong file name.json");
        try {
            Worklist wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorklist() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorklist.json");
        try {
            Worklist wl = reader.read();
            assertEquals(0, wl.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorklist() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorklist.json");
        try {
            assertEquals(Function.DEFAULT_SUBINTERVALS, Function.getSubintervals());
            assertEquals(Function.DEFAULT_PRECISION, Function.getPrecision());
            Function f1 = new Function(new Polynomial(Arrays.asList(1.0, 0.0, 1.0)));
            Function f2 = new Function(new Tan(1, 1, 0));
            Function f3 = f1.div(f2);
            Worklist wl = reader.read();
            assertEquals(1E-13, Function.getPrecision());
            assertEquals(8000, Function.getSubintervals());
            List<Function> functions = wl.getListFn();
            assertEquals(3, functions.size());
            checkFunction(f1.toJson(), functions.get(0));
            checkFunction(f2.toJson(), functions.get(1));
            checkFunction(f3.toJson(), functions.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
