package model;

import org.json.JSONObject;

public interface BasicFunction {
    // EFFECTS: evaluates a basic function at the given x
    double eval(double x);

    // EFFECTS: returns the name of the basic function, with x as the argument
    String getName(String x);

    // EFFECTS: creates a .JSON object for a BasicFunction
    JSONObject toJson();
}
