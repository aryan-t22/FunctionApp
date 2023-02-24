package model.basicfns;

import model.BasicFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polynomial implements BasicFunction {
    private List<Double> params;
    private String name;

    // EFFECTS: Constructs a polynomial object from the given list of parameters. Using params as [1.0, 0.5, 1.0] for
    // example yields the polynomial 1 + x/2 + x^2. If params is empty, constructs the 0 polynomial by default.
    public Polynomial(List<Double> params) {
        this.name = "Polynomial";
        if (params.size() == 0) {
            this.params = new ArrayList<>(Arrays.asList(0.0));
        } else {
            this.params = params;
        }
    }

    public List<Double> getParams() {
        return params;
    }

    @Override
    public String getName() {
        double czero = params.get(0);
        String result = Double.toString(czero);
        for (int i = 1; i <= getDegree(); i++) {
            double ci = params.get(i);
            result += (" + " + Double.toString(ci) + " * x^" + Integer.toString(i));
        }
        return result;
    }

    // EFFECTS: returns the degree of the polynomial
    public int getDegree() {
        return params.size() - 1;
    }

    // EFFECTS: evaluates a given polynomial at the provided double x
    @Override
    public double eval(double x) {
        double result = 0.0;
        for (int i = 0; i <= this.getDegree(); i++) {
            result += params.get(i) * Math.pow(x, i);
        }
        return result;
    }
}
