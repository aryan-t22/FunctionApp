package model.basicfns;

import org.json.JSONObject;

// A tangent object with a Vertical Stretch, Horizontal Stretch, a Phase Shift (in units of π, so a phase shift of 5
// would be 5π units) to the right, and named "tan"
public class Tan extends Trig {
    // EFFECTS: creates a tan object, with Vertical Stretch am, Horizontal Stretch governed by stretch (2(tan(3x)
    // for example has a v.stretch of 2 and h.stretch of 1/3), and a Phase phase, which corresponds to a translation of
    // phase*pi units to the right. If amp is 0.0, then constructs the sine object with amp = 1.0, as amp being 0.0
    // corresponds to the zero polynomial
    public Tan(double am, double stretch, double phase) {
        super(am, stretch, phase, "tan");
    }

    // EFFECTS: evaluates a * tan(stretch * (x - phase)) for a given double x
    @Override
    public double eval(double x) {
        return this.getAm() * Math.tan(this.getStretch() * (x - Math.PI * this.getPhase()));
    }

    // EFFECTS: creates a .JSON object for a tangent function
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amp", this.getAm());
        json.put("stretch", this.getStretch());
        json.put("phase", this.getPhase());
        json.put("name", "cos");
        return json;
    }
}
