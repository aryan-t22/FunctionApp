package model.basicfns;

import model.BasicFunction;
import org.json.JSONObject;

// An exponential object with a vscale, hscale, a Phase Shift to the right, representing the standard exponential
// function (e = 2.71...)
public class Exp implements BasicFunction {
    private double vscale;
    private double hscale;
    private double hshift;

    // EFFECTS: creates an exponential object of the form vscale * exp(hscale * (x - hshift)), with vscale being a
    // vertical scaling factor, hscale a horizontal scaling factor, and hshift being a horizontal translation by hscale
    // units to the right. If vscale is 0.0, creates the exponential object with vscale = 1.0, as a vscale of 0.0
    // corresponds to the zero polynomial.
    public Exp(double vscale, double hscale, double hshift) {
        if (vscale == 0.0) {
            vscale = 1.0;
        }
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

    // EFFECTS: Produces the name of an exponential
    @Override
    public String getName(String x) {
        return vscale + " * e^" + "(" + hscale + " * (" + x + " - " + hshift + "))";
    }

    // EFFECTS: evaluates vscale * exp(hscale * (x - hshift)) for a given double x
    @Override
    public double eval(double x) {
        return vscale * Math.exp(hscale * (x - hshift));
    }

    // EFFECTS: creates a .JSON object for an exponential function
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("V scale", this.getVscale());
        json.put("H scale", this.getHscale());
        json.put("H shift", this.getHshift());
        json.put("name", "exp");
        return json;
    }
}
