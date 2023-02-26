package model;

import java.util.Objects;

public class FunctionNode {
    private final Boolean isBasicFunc;
    private String operation;
    private BasicFunction fn;

    // EFFECTS: Constructs a leaf FunctionNode - sets isBasicFunc to true (this verifies it is a leaf), sets this.fn to
    // fn, and sets the divWindow (allowance for a small denominator) to DEFAULT_DIV_WINDOW
    public FunctionNode(BasicFunction fn) {
        isBasicFunc = true;
        this.fn = fn;
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

    public void setFn(BasicFunction fn) {
        this.fn = fn;
    }

    // EFFECTS: returns x operator y, for the 4 standard operations "+", "-", "*" and "/". For any other operation
    // apart from the 4 listed, returns 0.0.
    public double operateOn(double x, double y) throws ArithmeticException {
        if (Objects.equals(operation, "+")) {
            return x + y;
        } else if (Objects.equals(operation, "-")) {
            return x - y;
        } else if (Objects.equals(operation, "*")) {
            return x * y;
        } else if (Objects.equals(operation, "/") && Double.isFinite(x / y)) {
            return x / y;
        } else {
            throw new ArithmeticException();
        }
    }
}
