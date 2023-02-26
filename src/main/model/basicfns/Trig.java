package model.basicfns;

import model.BasicFunction;

// A class for trigonometric functions, which have a vertical stretch (amplitude in some cases), horizontal stretch,
// a horizontal phase shift (in units of π, so a phase shift of 5 would be 5π units) to the right, and a name field
// which is determined by the trigonometric function inheriting the abstract class
public abstract class Trig implements BasicFunction {
    private double am;
    private double stretch;
    private double phase;
    private String name;

    // EFFECTS: constructs am Trig object of the form am * fn(stretch * (x - phase * pi)), where the trig function fn
    // corresponds to name. If am = 0 is entered, chooses am = 1 instead, as am = 0 corresponds to the zero polynomial.
    public Trig(double am, double stretch, double phase, String name) {
        if (am == 0.0) {
            am = 1.0;
        }
        this.am = am;
        this.stretch = stretch;
        this.phase = phase;
        this.name = name;
    }

    public double getAm() {
        return am;
    }

    public double getPhase() {
        return phase;
    }

    public double getStretch() {
        return stretch;
    }

    // EFFECTS: generates the name of am given trigonometric function
    @Override
    public String getName(String x) {
        return am + " * " + name + "(" + stretch + " * (" + x + " - " + phase + "π))";
    }
}
