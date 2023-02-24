package model.basicfns;

public class Sine extends Trig {

    // EFFECTS: creates a sine object, with Amplitude Amp. Horizontal Stretch governed by stretch (sin(2x)
    // for example is a compression by 2), and a Phase phase, which translates the graph phase units to the right.
    public Sine(double amp, double stretch, double phase) {
        super(amp, stretch, phase, "sin");
    }

    // EFFECTS: evaluates amp * sin(stretch * (x - phase)) for a given double x
    public double eval(double x) {
        return this.getA() * Math.sin(this.getStretch() * (x - Math.PI * this.getPhase()));
    }
}
