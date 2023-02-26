package ui;

import model.Function;
import model.basicfns.*;
import java.util.Arrays;

public class Main1 {
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
            System.out.println("let's go, the value is " + val);
        } else {
            System.out.println("rip");
        }
        test1.setPhase(0);
        test2.setPhase(0);
        Function fn6 = fn3.compose(fn1);
        System.out.println(fn6.eval(Math.PI / 4.0));
        System.out.println(fn5.integrate(0, Math.PI / 2));
        System.out.println(fn5.name("x"));
        Function fn7 = fn2.compose(fn1);
        System.out.println(fn7.eval(Math.PI / 2));
        System.out.println(fn7.name("x"));
        System.out.println(fn2.fourierSine(Math.PI,3).name("x"));
        Tan test5 = new Tan(1, 1, 0);
        Function fn9 = new Function(test5);
        System.out.println(fn9.integrate(-1 * Math.PI/2, Math.PI/2));
        Polynomial test8 = new Polynomial(Arrays.asList(0.0, 1.0, 0.0));
        Function fn10 = new Function(test8);
        Function fn11 = fn2.div(fn10);
        System.out.println(fn3.fourierFull(Math.PI, 15).name("x"));
    }
}
