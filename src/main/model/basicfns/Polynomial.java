package model.basicfns;

import model.BasicFunction;

import java.util.*;

// A polynomial object of arbitrary degree. The degree of the polynomial is specified by the number of parameters chosen
// to initialize the polynomial with. The zero polynomial is the only polynomial which cannot be initialized with a
// degree other than 0.
public class Polynomial implements BasicFunction {
    private List<Double> params;

    // EFFECTS: Constructs a polynomial object from the given list of parameters. Using params as [1.0, 0.5, 1.0] for
    // example yields the polynomial 1 + x/2 + x^2. If params is empty, constructs the 0 polynomial by default. If all
    // the parameters are 0.0, constructs the zero polynomial (i.e. with 0 degree). If params has any trailing zeros
    // towards the end of the list, these are removed ([0, 1, 2, 0] returns 0.0 + 1.0 * x + 2.0 * x^2]
    public Polynomial(List<Double> params) {
        if (params.size() == 0) {
            this.params = new ArrayList<>(Arrays.asList(0.0));
        } else {
            this.params = params;
            checkForZero();
            initDegree();
        }
    }

    // EFFECTS: Checks if all params of a polynomial are 0.0, and if they are, changes the polynomial to have
    private void checkForZero() {
        boolean check = true;
        for (double ci : params) {
            check = check && ci == 0;
        }
        if (check) {
            params = Arrays.asList(0.0);
        }
    }

    // REQUIRES: this.params not empty
    // EFFECTS: initializes a polynomial via its degree, zeroes after the leading coefficient are removed and params
    // is modified
    private void initDegree() {
        List<Double> newList = new LinkedList<>();
        List<Double> loopList = new LinkedList<>();
        newList.addAll(params);
        loopList.addAll(params);
        if (newList.size() != 1) {
            Collections.reverse(newList);
            Collections.reverse(loopList);
            for (int i = 0; i < loopList.size(); i++) {
                if (loopList.get(i) == 0.0) {
                    newList.remove(0);
                } else {
                    break;
                }
            }
            Collections.reverse(newList);
        }
        this.params = newList;
    }

    public List<Double> getParams () {
        return params;
    }

    // REQUIRES: params to not be empty - handled by initialization
    // EFFECTS: Produces the name of a polynomial
    @Override
    public String getName(String x) {
        if (x.length() != 1) {
            x = "(" + x + ")";
        }
        String result = Double.toString(params.get(0));
        for (int i = 1; i <= getDegree(); i++) {
            result += (" + " + params.get(i) + " * " + x + "^" + i);
        }
        return result;
    }

    // REQUIRES: params to not be empty - handled by initialization
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
