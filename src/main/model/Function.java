package model;

import model.basicfns.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

// A binary syntax tree representation of a function. Each function consists of a FunctionNode fnn, a left function,
// and a right function. A BasicFunction is represented as a Function with null left and null right functions, with the
// FunctionNode storing the BasicFunction, hence forming the leafs of the tree. A more complicated function stores the
// operation in its node, representing the function left(operation)right. The class itself has a subintervals field,
// the number of subintervals used in numeric integration, and a precision field, indicating how much tolerance is there
// from a value being close to an integer to be approximated by it. The DEFAULT_SUBINTERVALS and DEFAULT_PRECISION
// constants provide an example of well tested values
public class Function {
    public static final int DEFAULT_SUBINTERVALS = 5000;
    public static final double DEFAULT_PRECISION = 1E-10;

    private FunctionNode fnn;
    private Function left;
    private Function right;
    private static int subintervals = DEFAULT_SUBINTERVALS;
    private static double precision = DEFAULT_PRECISION;

    // EFFECTS: Constructs a function with a leaf FunctionNode made from fn.
    public Function(BasicFunction fn) {
        this.fnn = new FunctionNode(fn);
    }

    // EFFECTS: Constructs a function with a parent FunctionNode made from operator, with the this.left and this.right
    // branches assigned to left and right respectively. If either the left and right nodes are mull, return the
    // zero function.
    public Function(String operator, Function left, Function right) {
        boolean leftValid = left != null;
        boolean rightValid = right != null;
        if (leftValid && rightValid) {
            this.fnn = new FunctionNode(operator);
            this.left = left;
            this.right = right;
        } else {
            this.fnn = zero().fnn;
        }
    }

    // EFFECTS: Constructs a constant function f(x) = c.
    private Function constant(double c) {
        return new Function(new Polynomial(Arrays.asList(c)));
    }

    // EFFECTS: Constructs the additive identity, the zero function f(x) = 0.
    private Function zero() {
        return constant(0.0);
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

    public static int getSubintervals() {
        EventLog.getInstance().logEvent(new Event("Viewed number of subintervals."));
        return subintervals;
    }

    public static double getPrecision() {
        EventLog.getInstance().logEvent(new Event("Viewed precision."));
        return precision;
    }

    // REQUIRES: subintervals >= 1
    public static void setSubintervals(int subintervals) {
        EventLog.getInstance().logEvent(new Event("Set number of subintervals to " + subintervals + "."));
        Function.subintervals = subintervals;
    }

    public static void setPrecision(double precision) {
        EventLog.getInstance().logEvent(new Event("Set precision to " + precision + "."));
        Function.precision = precision;
    }

    // Non-numerical function methods //

    // EFFECTS: Constructs a new Function (tree) for this operator fn, for the provided operator.
    private Function operate(Function fn, String operator) {
        return new Function(operator, this, fn);
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

    // EFFECTS: Constructs a new Function (tree) for this / fn. If fn is 0.0, this is returned.
    public Function div(Function fn) {
        if (!fn.name("x").equals("0.0")) {
            return operate(fn, "/");
        } else {
            return this;
        }
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
                return "[" + left.name(x) + "] " + fnn.getOperation() + " [" + right.name(x) + "]";
            } else {
                return left.name("[" + right.name(x) + "]");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: finds if fn1 is part of the function, and swaps all appearances of fn1 with fn2. Otherwise,
    // does nothing.
    public void modify(BasicFunction fn1, BasicFunction fn2) {
        if (fnn.getIsBasicFunc() && fn1.getName("x").equals(fnn.getFn().getName("x"))) {
            fnn.setFn(fn2);
        } else if (!fnn.getIsBasicFunc()) {
            left.modify(fn1, fn2);
            right.modify(fn1, fn2);
        }
    }

    // Numerical function methods //

    // EFFECTS: For a given x, with its rounded value being x0, if |x - x0| < precision, returns x0. Otherwise, returns
    // x
    private double adjust(double x) {
        double x0 = Math.round(x);
        if (Math.abs(x - x0) < precision) {
            return x0;
        } else {
            return x;
        }
    }

    // EFFECTS: Evaluates a given Function at the double x, up to precision. Throws ArithmeticException if the result
    // is not finite
    public double eval(double x) throws ArithmeticException {
        EventLog.getInstance().logEvent(new Event("Evaluated " + name("x") + " at x = " + x));
        if (fnn.getIsBasicFunc()) {
            double output = fnn.getFn().eval(x);
            return adjust(output);
        } else {
            if (!Objects.equals(fnn.getOperation(), "o")) {
                return fnn.operateOn(left.eval(x), right.eval(x));
            } else {
                return left.eval(right.eval(x));
            }
        }
    }

    // EFFECTS: Compares how well fn approximates this at x. If the output is positive, fn underestimates this, negative
    // with overestimate, and 0.0 indicates the functions are equivalent at x up to precision. Throws
    // ArithmeticException if the result is not finite
    public double approxBy(Function fn, double x) throws ArithmeticException {
        return adjust(this.eval(x) - fn.eval(x));
    }

    // EFFECTS: integrates a given Function on an interval [a,b] via Simpson's rule, with the number of subintervals n
    // being given by Function.subintervals, up to precision. Throws ArithmeticException if the result is not finite
    public double integrate(double a, double b) throws ArithmeticException {
        double deltaX = (b - a) / subintervals;
        double sum = this.eval(a) + this.eval(b);
        for (int i = 1; i < subintervals; i++) {
            if (i % 2 == 1) {
                sum += 4 * this.eval(a + deltaX * i);
            } else {
                sum += 2 * this.eval(a + deltaX * i);
            }
        }
        return adjust(deltaX / 3.0 * sum);
    }

    // REQUIRES: l > 0, n >= 1
    // EFFECTS: Returns the first n coefficients of the Fourier sine series of a function on [-l, l] up to precision.
    // Throws ArithmeticException if any of the coefficients is not finite
    private List<Double> fourierSineCoeff(double l, int n) throws ArithmeticException {
        List<Double> result = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            Function ithTerm = new Function(new Sine(1 / l, i * Math.PI / l, 0));
            Function fn = this.prod(ithTerm);
            double ci = fn.integrate(-1 * l, l);
            result.add(ci);
        }
        return result;
    }

    // EFFECTS: Returns the first n terms of the Fourier sine series of a function on [-l, l]. If n < 1 or l is 0,
    // produces the first term of the Fourier sine Series on [-1, 1]. If l < 0, returns fourierSine(-l, n) to
    // properly align the interval. If all n terms are zero, returns the zero polynomial. Throws ArithmeticException
    // if any of the terms is not finite
    public Function fourierSine(double l, int n) throws ArithmeticException {
        if (n < 1 || l == 0) {
            return fourierSine(1, 1);
        } else if (l < 0) {
            return fourierSine(-1 * l, n);
        } else {
            Function result;
            List<Double> coefficients = fourierSineCoeff(l, n);
            double c1 = coefficients.get(0);
            if (c1 == 0) {
                result = zero();
            } else {
                result = new Function(new Sine(c1, Math.PI / l, 0));
            }
            for (int i = 2; i <= n; i++) {
                double ci = coefficients.get(i - 1);
                if (ci != 0) {
                    Function ithTerm = new Function(new Sine(ci, i * Math.PI / l, 0));
                    result = result.add(ithTerm);
                }
            }
            return result;
        }
    }

    // REQUIRES: l > 0, n >= 0
    // EFFECTS: Produces the first n + 1 coefficients of the Fourier cosine series of a function on [-l, l] up to
    // precision. Throws ArithmeticException if any of the coefficients is not finite
    private List<Double> fourierCosineCoeff(double l, int n) throws ArithmeticException {
        List<Double> result = new ArrayList<>();
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
        return result;
    }

    // EFFECTS: Returns the first n+1 terms of the Fourier cosine series of a function on [-l, l] up to precision, with
    // coefficients calculated via fourierCosineCoeff. If n < 1 or l is 0, produces the first two terms of the Fourier
    // cosine Series on [-1, 1]. If l < 0, returns fourierCosine(-l, n) to properly align the interval. If all n terms
    // are zero, returns the zero polynomial. Throws ArithmeticException if any of the terms is not finite
    public Function fourierCosine(double l, int n) throws ArithmeticException {
        if (n < 0 || l == 0) {
            return fourierCosine(1, 1);
        } else if (l < 0) {
            return fourierCosine(-1 * l, n);
        } else {
            List<Double> coefficients = fourierCosineCoeff(l, n);
            Function result = zero();
            for (int i = 0; i <= n; i++) {
                double ci = coefficients.get(i);
                if (i == 0) {
                    result = constant(ci);
                } else if (ci != 0) {
                    Function ithTerm = new Function(new Cosine(ci, i * Math.PI / l, 0));
                    result = result.add(ithTerm);
                }
            }
            return result;
        }
    }

    // EFFECTS: Produces the first n+1 terms of the full Fourier series of a function on [-l, l] up to precision, using
    // fourierCosine and fourierSine. Throws ArithmeticException if any of the terms is not finite
    public Function fourierFull(double l, int n) throws ArithmeticException {
        if (fourierCosine(l, n).name("x").equals("0.0")) {
            return fourierSine(l, n);
        } else if (fourierSine(l, n).name("x").equals("0.0")) {
            return fourierCosine(l, n);
        } else {
            return fourierCosine(l, n).add(fourierSine(l, n));
        }
    }

    // REQUIRES: for left and right to either both be null (leaf node), or both not be null (parent node). This is
    // essentially guaranteed by design, but has been included for completeness
    // EFFECTS: creates a .JSON object for a Function
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        if (left == null) {
            json.put("Basic Function", fnn.getFn().toJson());
            return json;
        }
        json.put("operation", fnn.getOperation());
        json.put("left", left.toJson());
        json.put("right", right.toJson());
        return json;
    }
}