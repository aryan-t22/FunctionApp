package model;

import java.util.Objects;

public class FunctionNode {
    static final double DEFAULT_DIV_WINDOW = Math.pow(10.0, -50.0);

    private Boolean isBasicFunc;
    private String operation;
    private BasicFunction fn;
    private static double divWindow;

    // EFFECTS: Constructs a leaf FunctionNode - sets isBasicFunc to true (this verifies it is a leaf), sets this.fn to
    // fn, and sets the divWindow (allowance for a small denominator) to DEFAULT_DIV_WINDOW
    public FunctionNode(BasicFunction fn) {
        isBasicFunc = true;
        this.fn = fn;
        divWindow = DEFAULT_DIV_WINDOW;

    }

    // EFFECTS: Constructs a parent FunctionNode - sets isBasicFunc to false (this verifies it is not a leaf),
    // sets this.operation operation (an example operation is "+"), and sets the divWindow (allowance for a small
    // denominator) to DEFAULT_DIV_WINDOW
    public FunctionNode(String operation) {
        isBasicFunc = false;
        this.operation = operation;
    }

    public Boolean getIsBasicFunc() {
        return isBasicFunc;
    }

    public BasicFunction getFn() {
        return fn;
    }

    public String getOperation() {
        return operation;
    }

    public static double getDivWindow() {
        return divWindow;
    }

    // EFFECTS: returns x operator y, for the 4 standard operations "+", "-", "*" and "/". For the division (/) operator
    // , if 0.0 < abs(y) < divWindow, computes x / +-divWindow with sign inherited from y. For division by 0.0 exactly,
    // or any other operation apart from the 4 listed, returns 0.0.
    public double operateOn(double x, double y) {
        if (Objects.equals(operation, "+")) {
            return x + y;
        } else if (Objects.equals(operation, "-")) {
            return x - y;
        } else if (Objects.equals(operation, "*")) {
            return x * y;
        } else if (Objects.equals(operation, "/") && Math.abs(y) > divWindow) {
            return x / y;
        } else if (Math.abs(y) < divWindow && y > 0) {
            return x / divWindow;
        } else if (Math.abs(y) < divWindow && y < 0) {
            return x / (-1 * divWindow);
        } else {
            return 0.0;
        }
    }
}
