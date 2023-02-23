package model;

import basicfns.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Function {
    static final int DEFAULT_PRECISION = 5000;

    private int precision;

    private FunctionNode fnn;
    private Function left;
    private Function right;

    public Function(BasicFunction fn) {
        this.fnn = new FunctionNode(fn);
        precision = DEFAULT_PRECISION;
    }

    private Function(FunctionNode fnn, Function left, Function right) {
        this.fnn = fnn;
        this.left = left;
        this.right = right;
        precision = DEFAULT_PRECISION;
    }

    private Function operate(Function fn, String operator) {
        FunctionNode fnn = new FunctionNode(operator);
        return new Function(fnn, this, fn);
    }

    public Function plus(Function fn) {
        return operate(fn, "+");
    }

    public Function minus(Function fn) {
        return operate(fn, "-");
    }

    public Function times(Function fn) {
        return operate(fn, "*");
    }

    public Function quot(Function fn) {
        return operate(fn, "/");
    }

    public Function compose(Function fn) {
        return operate(fn, "o");
    }

    public double eval(double x) {
        if (this.fnn.getIsBasicFunc()) {
            return this.fnn.getFn().eval(x);
        } else {
            if (!Objects.equals(this.fnn.getOperation(), "o")) {
                return this.fnn.operateOn(this.left.eval(x), this.right.eval(x));
            } else {
                return this.left.eval(this.right.eval(x));
            }
        }
    }

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
        return deltaX / 3 * sum;
    }

    public List<Double> fourierSine(double l, int n) {
        List<Double> result = new ArrayList<>();
        if (n < 1 || l == 0) {
            return fourierSine(1, 10);
        } else if (l < 0) {
            return fourierSine(-1 * l, n);
        } else {
            for (int i = 1; i <= n; i++) {
                Function ithTerm = new Function(new Sine(2 / l, i * Math.PI / l, 0));
                Function fn = this.times(ithTerm);
                double ci = fn.integrate(-1 * l, l);
                result.add(ci);
            }
        }
        return result;
    }

    public List<Double> fourierCosine(double l, int n) {
        List<Double> result = new ArrayList<>();
        if (n < 0 || l == 0) {
            return fourierCosine(1, 10);
        } else if (l < 0) {
            return fourierCosine(-1 * l, n);
        } else {
            for (int i = 0; i <= n; i++) {
                Function ithTerm;
                if (i == 0) {
                    ithTerm = new Function(new Cosine(1 / l, i * Math.PI / l, 0));
                } else {
                    ithTerm = new Function(new Cosine(2 / l, i * Math.PI / l, 0));
                }
                Function fn = this.times(ithTerm);
                double ci = fn.integrate(-1 * l, l);
                result.add(ci);
            }
        }
        return result;
    }
}