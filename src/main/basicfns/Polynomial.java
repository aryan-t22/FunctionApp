package basicfns;

import model.BasicFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polynomial implements BasicFunction {
    private List<Double> params;
    private String name;

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

    public String getName() {
        return name;
    }

    public int getDegree() {
        return params.size() - 1;
    }

    public double eval(double x) {
        double result = 0.0;
        for (int i = 0; i <= this.getDegree(); i++) {
            result += params.get(i) * Math.pow(x, i);
        }
        return result;
    }
}
