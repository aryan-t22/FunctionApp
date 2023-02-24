package ui;

import model.basicfns.*;
import model.Function;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Trig test1 = new Cosine(1, 1, 0);
        Trig test2 = new Sine(1,1,0);
        Exp test3 = new Exp(1,1,0);
        Polynomial test4 = new Polynomial(Arrays.asList(1.0, 0.0, 1.0));
        Function fn1 = new Function(test1);
        Function fn2 = new Function(test2);
        Function fn3 = new Function(test3);
        Function fn4 = new Function(test4);
        Function fn5 = fn1.add(fn2.div(fn3.prod(fn4)));
        double val = fn5.eval(0);
        test1.setPhase(1 / 3.0);
        test2.setPhase(1 / 3.0);
        if (val == fn5.eval(Math.PI / 3.0)) {
            System.out.println("lesgo, the value is " + val);
        } else {
            System.out.println("rip bozo");
        }
        test1.setPhase(0);
        test2.setPhase(0);
        Function fn6 = fn3.compose(fn1);
        System.out.println(fn6.eval(Math.PI / 4.0));
        System.out.println(fn5.integrate(0, Math.PI / 2));
        System.out.println(fn2.fourierSineCoeff(Math.PI, 5));
        System.out.println(test1.getName());
    }
}
