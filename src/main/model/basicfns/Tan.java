package model.basicfns;

public class Tan extends Trig {

    // EFFECTS: creates a tan object, with Vertical Stretch a, Horizontal Stretch governed by stretch (2(tan(3x)
    // for example has a v.stretch of 2 and h.stretch of 1/3), and a Phase phase, which corresponds to a translation of
    // phase*pi units to the right. If amp is 0.0, then constructs the sine object with amp = 1.0, as amp being 0.0
    // corresponds to the zero polynomial
    public Tan(double a, double stretch, double phase) {
        super(a, stretch, phase, "tan");
    }

    // EFFECTS: evaluates a * tan(stretch * (x - phase)) for a given double x
    @Override
    public double eval(double x) {
        return this.getAm() * Math.tan(this.getStretch() * (x - Math.PI * this.getPhase()));
    }
}
