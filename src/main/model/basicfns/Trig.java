package model.basicfns;

import model.BasicFunction;

public abstract class Trig implements BasicFunction {
    private double a;
    private double stretch;
    private double phase;
    private String name;

    // EFFECTS: constructs a Trig object of the form a * fn(stretch * (x - phase * pi)), where the trig function fn
    // corresponds to name.
    public Trig(double a, double stretch, double phase, String name) {
        this.a = a;
        this.stretch = stretch;
        this.phase = phase;
        this.name = name;
    }

    public double getA() {
        return a;
    }

    public double getPhase() {
        return phase;
    }

    public double getStretch() {
        return stretch;
    }

    @Override
    public String getName(String x) {
        String a = Double.toString(this.a);
        String b = Double.toString(stretch);
        String c = Double.toString(phase) + "\u03C0";
        return a + " * " + name + "(" + b + " * (" + x + " - " + c + "))";
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setStretch(double stretch) {
        this.stretch = stretch;
    }

    public void setPhase(double phase) {
        this.phase = phase;
    }
}
