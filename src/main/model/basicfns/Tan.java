package model.basicfns;

public class Tan extends Trig {

    public Tan(double amp, double stretch, double phase) {
        super(amp, stretch, phase, "tan");
    }

    public double eval(double x) {
        return this.getA() * Math.tan(this.getStretch() * (x - this.getPhase()));
    }
}
