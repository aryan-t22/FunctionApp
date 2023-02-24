package model;

import model.basicfns.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Function {
    static final int DEFAULT_PRECISION = 5000;

    private FunctionNode fnn;
    private Function left;
    private Function right;
    private int precision;

    // EFFECTS: Constructs a function with a leaf FunctionNode made from fn, and sets precision to DEFAULT_PRECISION.
    public Function(BasicFunction fn) {
        this.fnn = new FunctionNode(fn);
        precision = DEFAULT_PRECISION;
    }

    // EFFECTS: Constructs a function with a parent FunctionNode made from operator, with the this.left and this.right
    // branches assigned to left and right respectively. Sets precision to DEFAULT_PRECISION.
    public Function(String operator, Function left, Function right) {
        this.fnn = new FunctionNode(operator);
        this.left = left;
        this.right = right;
        precision = DEFAULT_PRECISION;
    }

    public FunctionNode getFnn() {
        return fnn;
    }

    public Function getLeft() {
        return left;
    }

    public Function getRight() {
        return right;
    }

    public int getPrecision() {
        return precision;
    }

    public void setFnn(FunctionNode fnn) {
        this.fnn = fnn;
    }

    public void setLeft(Function left) {
        this.left = left;
    }

    public void setRight(Function right) {
        this.right = right;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    // EFFECTS: Constructs a new Function (tree) for this operator fn, for the provided operator.
    private Function operate(Function fn, String operator) {
        return new Function(operator, this, fn);
    }

    // EFFECTS: Constructs a constant function f(x) = c.
    private Function constant(double c) {
        return new Function(new Polynomial(List.of(c)));
    }

    // EFFECTS: Constructs the additive identity, the zero function f(x) = 0.
    private Function zero() {
        return constant(0.0);
    }

    // EFFECTS: Constructs a new Function (tree) for this + fn.
    public Function add(Function fn) {
        return operate(fn, "+");
    }

    // EFFECTS: Constructs a new Function (tree) for this - fn.
    public Function sub(Function fn) {
        return operate(fn, "-");
    }

    // EFFECTS: Constructs a new Function (tree) for this * fn.
    public Function prod(Function fn) {
        return operate(fn, "*");
    }

    // EFFECTS: Constructs a new Function (tree) for this / fn.
    public Function div(Function fn) {
        return operate(fn, "/");
    }

    // EFFECTS: Constructs a new Function (tree) for this o fn (i.e. for a given double x, the returned function would
    // produce this(fn(x)), the composition of this and fn.)
    public Function compose(Function fn) {
        return operate(fn, "o");
    }

    // EFFECTS: produces the name of the function with x being the parameter of choice.
    public String name(String x) {
        if (fnn.getIsBasicFunc()) {
            return fnn.getFn().getName(x);
        } else {
            if (!Objects.equals(fnn.getOperation(), "o")) {
                return left.name(x) + " " + fnn.getOperation() + " [" + right.name(x) + "]";
            } else {
                return left.name(right.name(x));
            }
        }
    }

    // EFFECTS: Evaluates a given Function at the double x.
    public double eval(double x) {
        if (fnn.getIsBasicFunc()) {
            return fnn.getFn().eval(x);
        } else {
            if (!Objects.equals(fnn.getOperation(), "o")) {
                return fnn.operateOn(left.eval(x), right.eval(x));
            } else {
                return left.eval(right.eval(x));
            }
        }
    }

    // EFFECTS: integrates a given Function on an interval [a,b], with this.precision many sub-intervals.
    public double integrate(double a, double b) {
        double deltaX = (b - a) / precision;
        double sum = this.eval(a) + this.eval(b);
        for (int i = 1; i < precision; i++) {
            if (i % 2 == 1) {
                sum += 4 * this.eval(a + deltaX * i);
            } else {
                sum += 2 * this.eval(a + deltaX * i);
            }
        }
        return deltaX / 3.0 * sum;
    }

    // EFFECTS: Returns the first n coefficients of the Fourier sine series of a function on [-l, l]. If n < 1 or l is
    // 0, produces the first 10 terms of the Fourier sine Series on [-1, 1]. If l < 0, returns fourierSine(-l, n) to
    // properly align the interval.
    public List<Double> fourierSineCoeff(double l, int n) {
        List<Double> result = new ArrayList<>();
        if (n < 1 || l == 0) {
            return fourierSineCoeff(1, 10);
        } else if (l < 0) {
            return fourierSineCoeff(-1 * l, n);
        } else {
            for (int i = 1; i <= n; i++) {
                Function ithTerm = new Function(new Sine(1 / l, i * Math.PI / l, 0));
                Function fn = this.prod(ithTerm);
                double ci = fn.integrate(-1 * l, l);
                result.add(ci);
            }
        }
        return result;
    }

    // EFFECTS: Returns the first n terms of the Fourier sine series of a function on [-l, l], with coefficients
    // calculated via fourierSineCoeff.
    public Function fourierSine(double l, int n) {
        List<Double> coefficients = fourierSineCoeff(l, n);
        Function result = zero();
        for (int i = 1; i <= n; i++) {
            double ci = coefficients.get(i - 1);
            Function ithTerm = new Function(new Sine(ci, i * Math.PI / l, 0));
            result = result.add(ithTerm);
        }
        return result;
    }

    // EFFECTS: Produces the first n coefficients of the Fourier cosine series of a function on [-l, l]. If n < 1 or l
    // is 0, produces the first 10 terms of the Fourier cosine Series on [-1, 1]. If l < 0, returns fourierSine(-l, n)
    // to properly align the interval.
    public List<Double> fourierCosineCoeff(double l, int n) {
        List<Double> result = new ArrayList<>();
        if (n < 0 || l == 0) {
            return fourierCosineCoeff(1, 10);
        } else if (l < 0) {
            return fourierCosineCoeff(-1 * l, n);
        } else {
            for (int i = 0; i <= n; i++) {
                Function ithTerm;
                if (i == 0) {
                    ithTerm = constant(1 / (2 * l));
                } else {
                    ithTerm = new Function(new Cosine(1 / l, i * Math.PI / l, 0));
                }
                Function fn = this.prod(ithTerm);
                double ci = fn.integrate(-1 * l, l);
                result.add(ci);
            }
        }
        return result;
    }

    // EFFECTS: Returns the first n terms of the Fourier cosine series of a function on [-l, l], with coefficients
    // calculated via fourierCosineCoeff.
    public Function fourierCosine(double l, int n) {
        List<Double> coefficients = fourierCosineCoeff(l, n);
        Function result = zero();
        for (int i = 0; i <= n; i++) {
            double ci = coefficients.get(i);
            if (i == 0) {
                result = result.add(constant(ci));
            } else {
                Function ithTerm = new Function(new Cosine(1 / l, i * Math.PI / l, 0));
                result = result.add(ithTerm);
            }
        }
        return result;
    }

    // EFFECTS: Produces the first n coefficients of the full Fourier series of a function on [-l, l], as a list of list
    // of doubles. The first entry in the list are the list of cosine coefficients, with the second being the sine
    // coefficients.
    public List<List<Double>> fourierFullCoeff(double l, int n) {
        return Arrays.asList(fourierCosineCoeff(l, n), fourierSineCoeff(l , n));
    }

    // EFFECTS: Produces the first n terms of the full Fourier series of a function on [-l, l], using fourierCosine
    // and fourierSine.
    public Function fourierFull(double l, int n) {
        return fourierCosine(l, n).add(fourierSine(l, n));
    }

    // EFFECTS: Compares how well fn approximates this at x. If the output is positive, fn underestimates this, negative
    // with overestimate, and 0.0 indicates the functions are equivalent at x.
    public double approxBy(Function fn, double x) {
        return this.eval(x) - fn.eval(x);
    }
}