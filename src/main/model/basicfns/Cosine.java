package model.basicfns;

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
}
