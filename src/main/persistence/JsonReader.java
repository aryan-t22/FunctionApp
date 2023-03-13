package persistence;

import model.Worklist;
import model.Function;
import model.BasicFunction;
import model.basicfns.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: Creates a .JSON reader, with target directory source
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads a file from source, throws an IO exception if the file is not found at the source, or if there
    // is an error during reading
    public Worklist read() throws IOException {
        String jsonData = this.readFile(this.source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return this.parseWorklist(jsonObject);
    }

    // EFFECTS: helper method for reading the file from a given source
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8);
        stream.forEach(contentBuilder::append);
        stream.close();
        return contentBuilder.toString();
    }

    // MODIFIES: Function Class - Function.subintervals, Function.precision
    // EFFECTS: parses the .JSON file at source to create the saved worklist
    private Worklist parseWorklist(JSONObject jsonObject) {
        Worklist wl = new Worklist();
        Function.setSubintervals(jsonObject.getInt("subintervals"));
        Function.setPrecision(jsonObject.getDouble("precision"));
        this.addFunctions(wl, jsonObject);
        return wl;
    }

    // EFFECTS: adds functions to the worklist during parsing
    private void addFunctions(Worklist wl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("functions");

        for (Object json : jsonArray) {
            JSONObject nextFunction = (JSONObject) json;
            wl.insertFunc(makeFunction(nextFunction));
        }

    }

    // REQUIRES: that the Function Class .JSON only has either a Basic Function .JSON object, or an operation string,
    // and two .JSON objects for the left and right functions. This is essentially guaranteed via the implementation
    // of .toJson in the Function class, but has been included for completeness
    // EFFECTS: makes a function from a given .JSON Object
    private Function makeFunction(JSONObject json) {
        try {
            return new Function(makeBasicFunction(json.getJSONObject("Basic Function")));
        } catch (JSONException e) {
            String operation = json.getString("operation");
            Function left = makeFunction(json.getJSONObject("left"));
            Function right = makeFunction(json.getJSONObject("right"));
            return new Function(operation, left, right);
        }
    }

    // REQUIRES: that a Basic Function consists of Cosine, Sine, Tan, Exp and Trig. The method would need to be modified
    // if functionality for more basic functions (such as the Logarithm) was included.
    // EFFECTS: makes a Basic Function from a given .JSON Object
    private BasicFunction makeBasicFunction(JSONObject json) {
        String name = json.getString("name");
        switch (name) {
            case "sin":
                return new Sine(json.getDouble("amp"), json.getDouble("stretch"), json.getDouble("phase"));
            case "cos":
                return new Cosine(json.getDouble("amp"), json.getDouble("stretch"), json.getDouble("phase"));
            case "tan":
                return new Tan(json.getDouble("am"), json.getDouble("stretch"), json.getDouble("phase"));
            case "exp":
                return new Exp(json.getDouble("V scale"), json.getDouble("H scale"), json.getDouble("H shift"));
            default:
                int degree = json.getInt("degree");
                int i = 0;
                List<Double> params = new ArrayList<>();
                while (i <= degree) {
                    params.add(json.getDouble(Integer.toString(i)));
                    i++;
                }
                return new Polynomial(params);
        }
    }
}

