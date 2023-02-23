package model;

import java.util.Objects;

public class FunctionNode {
    static final double DIV_WINDOW = Math.pow(10.0, -8.0);

    private Boolean isBasicFunc;
    private String operation;
    private BasicFunction fn;
    private static double divWindow;

    public FunctionNode(BasicFunction fn) {
        isBasicFunc = true;
        this.fn = fn;
        divWindow = DIV_WINDOW;

    }

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

    public double operateOn(double x, double y) {
        if (Objects.equals(operation, "+")) {
            return x + y;
        } else if (Objects.equals(operation, "-")) {
            return x - y;
        } else if (Objects.equals(operation, "*")) {
            return x * y;
        } else if (Objects.equals(operation, "/") && Math.abs(y) <= divWindow) {
            return x / y;
        } else {
            return 0.0;
        }
    }
}
