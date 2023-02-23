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
        Function fn5 = fn1.plus(fn2.quot(fn3.times(fn4)));
        double val = fn5.eval(0);
        test1.setPhase(Math.PI / 3);
        test2.setPhase(Math.PI / 3);
        if (val == fn5.eval(Math.PI / 3)) {
            System.out.println("lesgo, the value is " + val);
        } else {
            System.out.println("rip bozo");
        }
        test1.setPhase(0);
        test2.setPhase(0);
        Function fn6 = fn3.compose(fn1);
        System.out.println(fn6.eval(Math.PI / 4));
        System.out.println(fn5.integrate(0, Math.PI / 2));
    }
}
