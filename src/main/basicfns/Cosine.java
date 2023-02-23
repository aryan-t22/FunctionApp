package basicfns;

public class Cosine extends Trig {

    public Cosine(double amp, double stretch, double phase) {
        super(amp, stretch, phase,"cos");
    }

    public double eval(double x) {
        return this.getA() * Math.cos(this.getStretch() * (x - this.getPhase()));
    }
}
