package model.basicfns;

import model.BasicFunction;

public class Exp implements BasicFunction {
    private double vscale;
    private double hscale;
    private double hshift;

    // EFFECTS: creates an E
    public Exp(double vscale, double hscale, double hshift) {
        this.vscale = vscale;
        this.hscale = hscale;
        this.hshift = hshift;
    }

    public double getVscale() {
        return vscale;
    }

    public double getHscale() {
        return hscale;
    }

    public double getHshift() {
        return hshift;
    }

    @Override
    public String getName(String x) {
        String a = Double.toString(vscale);
        String b = Double.toString(hscale);
        String c = Double.toString(hshift);
        return a + " * e^" + "(" + b + " * (" + x + " - " + c + "))";
    }

    public void setVscale(double vscale) {
        this.vscale = vscale;
    }

    public void setHscale(double hscale) {
        this.hscale = hscale;
    }

    public void setHshift(double hshift) {
        this.hshift = hshift;
    }

    // EFFECTS: evaluates vscale * exp(hscale * (x - hshift)) for a given double x
    @Override
    public double eval(double x) {
        return vscale * Math.exp(hscale * (x - hshift));
    }

}
