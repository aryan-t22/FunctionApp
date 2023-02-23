package model.basicfns;

import model.BasicFunction;

public class Exp implements BasicFunction {
    private double vscale;
    private double hscale;
    private double hshift;
    private String name;

    public Exp(double vscale, double hscale, double hshift) {
        this.vscale = vscale;
        this.hscale = hscale;
        this.hshift = hshift;
        this.name = "exp";
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

    public String getName() {
        return name;
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

    public double eval(double x) {
        return vscale * Math.exp(hscale * (x - hshift));
    }

}
