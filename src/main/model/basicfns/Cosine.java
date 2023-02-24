package model.basicfns;

public class Cosine extends Trig {

    // EFFECTS: creates a cosine object, with Amplitude Amp. Horizontal Stretch governed by stretch (cos(2x)
    // for example is a compression by 2), and a Phase phase, which translates the graph phase units to the right.
    public Cosine(double amp, double stretch, double phase) {
        super(amp, stretch, phase, "cos");
    }

    // EFFECTS: evaluates amp * cos(stretch * (x - phase)) for a given double x
    public double eval(double x) {
        return this.getA() * Math.cos(this.getStretch() * (x - Math.PI * this.getPhase()));
    }
}
