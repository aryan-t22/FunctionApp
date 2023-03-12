package model.basicfns;

import org.json.JSONObject;

// A cosine object with an Amplitude, Horizontal Stretch, a Phase Shift (in units of π, so a phase shift of 5 would be
// 5π units) to the right, and named "cos"
public class Cosine extends Trig {
    // EFFECTS: creates a cosine object, with Amplitude Amp, Horizontal Stretch governed by stretch (cos(2x)
    // for example is a compression by 2), and a Phase phase, corresponding to a translation phase*pi units to the
    // right. If amp is 0.0, then constructs the cosine object with amp = 1.0, as amp being 0.0 corresponds to the zero
    // polynomial
    public Cosine(double amp, double stretch, double phase) {
        super(amp, stretch, phase, "cos");
    }

    // EFFECTS: evaluates amp * cos(stretch * (x - phase)) for a given double x
    @Override
    public double eval(double x) {
        return this.getAm() * Math.cos(this.getStretch() * (x - Math.PI * this.getPhase()));
    }

    // EFFECTS: creates a .JSON object for a cosine function
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
