package model.basicfns;

public class Tan extends Trig {

    // EFFECTS: creates a sine object, with Vertical Stretch a, Horizontal Stretch governed by stretch (2(tan(2x)
    // for example is a vertical stretch by 2 and horizontal compression by 3), and a Phase phase, which translates
    // the function phase units to the right.
    public Tan(double a, double stretch, double phase) {
        super(a, stretch, phase, "tan");
    }

    // EFFECTS: evaluates a * tan(stretch * (x - phase)) for a given double x
    public double eval(double x) {
        return this.getA() * Math.tan(this.getStretch() * (x - Math.PI * this.getPhase()));
    }
}
