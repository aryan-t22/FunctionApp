package basicfns;

public class Sine extends Trig {

    public Sine(double amp, double stretch, double phase) {
        super(amp, stretch, phase, "sin");
    }

    public double eval(double x) {
        return this.getA() * Math.sin(this.getStretch() * (x - this.getPhase()));
    }
}
