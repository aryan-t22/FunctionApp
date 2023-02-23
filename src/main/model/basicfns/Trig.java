package model.basicfns;

import model.BasicFunction;

public abstract class Trig implements BasicFunction {
    private double a;
    private double stretch;
    private double phase;
    private String name;

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

    public String getName() {
        return name;
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

    public abstract double eval(double x);
}
