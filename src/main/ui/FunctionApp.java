package ui;

import model.BasicFunction;
import model.Function;
import model.Worklist;
import model.basicfns.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FunctionApp {
    private Worklist wl;
    private Scanner input;

    // EFFECTS: runs the Function application
    public FunctionApp() {
        runFunction();
    }

    private void runFunction() {
        boolean keepGoing = true;
        String command;
        init();
        while (keepGoing) {
            displayMenu();
            command = input.next().toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nThank you for using the FunctionApp! Have a good one!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("v")) {
            doView();
        } else if (command.equals("a")) {
            doAdd();
        } else if (command.equals("m")) {
            doMake();
        } else if (command.equals("e")) {
            doEdit();
        } else if (command.equals("z")) {
            doAnalyze();
        } else if (command.equals(".")) {
            doSettings();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes the worklist
    private void init() {
        wl = new Worklist();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select from the following:");
        System.out.println("\tv -> view your worklist");
        System.out.println("\ta -> add a basic function to your worklist");
        System.out.println("\tm -> make more complicated functions");
        System.out.println("\te -> edit (and remove) functions in your worklist");
        System.out.println("\tz -> analyze (evaluate, integrate etc.) the functions in your worklist");
        System.out.println("\t. -> change default settings.");
        System.out.println("\tq -> quit");
    }

    //// MAIN METHODS ////

    // MODIFIES: this
    // EFFECTS: displays a view of the worklist.
    private void doView() {
        System.out.println(wl.names());
    }

    // MODIFIES: this
    // EFFECTS: adds a function to the worklist.
    private void doAdd() {
        wl.insertFunc(createFn());
    }

    // MODIFIES: this
    // EFFECTS: Selects function fn1 and fn2 from the worklist (need not be distinct), and an operation ., and
    // adds fn1.fn2 to the worklist. The user may also choose to remove fn1 and fn2 afterwards.
    private void doMake() {
        if (wl.isEmpty()) {
            System.out.println("Your worklist is empty. Please add a function, or functions to begin modifying");
        } else {
            System.out.println("It is recommended to have more than 1 function in the worklist to take full advantage"
                    + " of the modification features the program offers.");
            System.out.print("If you would like to go back to the main menu to add functions -> y. To continue "
                    + "-> any other key: ");
            String answer = input.next();
            answer = answer.toLowerCase();
            if (!answer.equals("y")) {
                selectOperation(selectFn());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Lets the user either remove a function from the worklist, or for a given function, swap one of the basic
    // functions it consists of with a basic function in the worklist. If the worklist has no basic functions, then
    // choosing the swap option does nothing.
    private void doEdit() {
        if (wl.isEmpty()) {
            System.out.println("Your worklist is empty. Please add a function, or functions to begin modifying");
        } else {
            System.out.println("This feature lets you modify the structure of a selected function");
            Function fn = selectFn();
            String selection = "";
            while (!(selection.equals("r") || selection.equals("c"))) {
                System.out.println("\nHow would you like to modify the selected function, f? ");
                System.out.println("\tr -> Remove it from the worklist");
                System.out.println("\tc -> Choose two basic functions, f1 and f2, from your worklist. If f1 is part "
                        + "of f, then all f1 terms get replaced with f2, and f1 itself is removed from your worklist");
                selection = input.next().toLowerCase();
            }
            if (selection.equals("r")) {
                System.out.println(fn.name("x") + " removed from the worklist");
                wl.removeFunc(fn);
            } else if (!wl.hasBasicFn()) {
                System.out.println("Your worklist has no basic functions. Please add one, and then try using this "
                        + "feature");
            } else {
                swap(fn);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Lets the user analyze a function from the worklist - which involves evaluating it, integrating it
    // numerically, computing its fourier series, or comparing the function to another function in the worklist for a
    // particular x value. The user may choose to add the computed Fourier Series as functions to their worklist
    private void doAnalyze() {
        if (wl.isEmpty()) {
            System.out.println("Your worklist is empty. Please add a function, or functions to begin modifying");
        } else {
            Function fn = selectFn();
            String opt = "";
            while (!(opt.equals("e") || opt.equals("i") || opt.equals("f") || opt.equals("a"))) {
                System.out.println("\nHow would you like to analyze the selected function?");
                System.out.println("\te -> evaluate the function at a particular point");
                System.out.println("\ti -> numerically integrate your function on an interval [a,b]");
                System.out.println("\tf -> compute the first n terms of a Fourier Series on an interval [-l,l]");
                System.out.println("\ta -> Check how closely two functions approximate each other for a given x value");
                opt = input.next().toLowerCase();
            }
            if (opt.equals("e")) {
                evaluate(fn);
            } else if (opt.equals("i")) {
                integrate(fn);
            } else if (opt.equals("f")) {
                fourierSeries(fn);
            } else {
                compare(fn);
            }
        }
    }

    // MODIFIES: Function (static state)
    // EFFECTS: Allows the user to change the settings of the FunctionApp, which involve changing the precision (a cut-
    // -off for when to round a value to its nearest integer, or changing the number of subintervals used in integration
    private void doSettings() {
        System.out.println("The program is guaranteed to run smoothly for the default settings, but promises cannot"
                + " be made if the settings below are made. Please keep this in mind when changing the settings.");
        String opt = "";
        while (!(opt.equals("v") || opt.equals("c") || opt.equals("r"))) {
            System.out.println("\nPlease select what you would like to do:");
            System.out.println("\tv -> view current settings");
            System.out.println("\tc -> change current settings");
            System.out.println("\tr -> reset settings to default");
            opt = input.next().toLowerCase();
        }
        if (opt.equals("v")) {
            System.out.println("The current precision is set to " + Function.getPrecision() + " and the current number"
                    + " of subintervals used in integration is " + Function.getSubintervals());
        } else if (opt.equals("c")) {
            changeSettings();
        } else {
            Function.setPrecision(Function.DEFAULT_PRECISION);
            Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
            System.out.println("Settings successfully restored to default");
        }
    }

    //// HELPER METHODS ////

    // For doAdd(): //

    // EFFECTS: prompts user to create a new Function.
    private Function createFn() {
        String selection = "";
        while (!(selection.equals("p") || selection.equals("e") || selection.equals("s")
                || selection.equals("c") || selection.equals("t"))) {
            System.out.println("\nConstruct one of the basic functions below:");
            System.out.println("\tp -> Polynomial");
            System.out.println("\te -> Exponential");
            System.out.println("\ts -> Sine");
            System.out.println("\tc -> Cosine");
            System.out.println("\tt -> Tangent");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        if (selection.equals("p")) {
            return makePolynomial();
        } else if (selection.equals("e")) {
            return makeExponential();
        } else if (selection.equals("s")) {
            return makeSin();
        } else if (selection.equals("c")) {
            return makeCos();
        } else {
            return makeTan();
        }
    }

    // EFFECTS: prompts user to create a new Polynomial Function.
    private Function makePolynomial() {
        System.out.print("Enter degree of polynomial: ");
        int degree = input.nextInt();
        while (degree < 0) {
            System.out.print("Invalid degree. Re-enter the degree (it should be a non negative integer): ");
            degree = input.nextInt();
        }
        System.out.println("Now you will be prompted to enter the coefficients for each term in the polynomial. If "
                + "all the coefficients you enter are 0.0, the zero polynomial (with degree 0) is constructed");
        List<Double> params = new ArrayList<>();
        int i = 0;
        while (i < degree + 1) {
            System.out.print("Please enter coefficient of x^" + i + ": ");
            double coeff = input.nextDouble();
            params.add(coeff);
            i++;
        }
        Function fn = new Function(new Polynomial(params));
        System.out.println("Success - Polynomial: " + fn.name("x") + " made.");
        return fn;
    }

    // EFFECTS: prompts user to create a new Exponential Function.
    private Function makeExponential() {
        System.out.println("For a function of the form ae^(b(x-c)): ");
        System.out.println("If a = 0.0 is entered, then this will be set by default to a = 1.0 instead. For a = 0.0, "
                + "please construct a zero polynomial");
        System.out.print("Enter a: ");
        double a = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Exp(a, b, c));
        System.out.println("Success - Exponential: " + fn.name("x") + " made.");
        return fn;
    }

    // EFFECTS: prompts user to create a new Sine Function.
    private Function makeSin() {
        System.out.println("For a function of the form asin(b(x-c)): ");
        System.out.println("If a = 0.0 is entered, then this will be set by default to a = 1.0 instead. For a = 0.0, "
                + "please construct a zero polynomial");
        System.out.print("Enter a: ");
        double a = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Sine(a, b, c));
        System.out.println("Success - Sine: " + fn.name("x") + " made.");
        return fn;
    }

    // EFFECTS: prompts user to create a new Cosine Function.
    private Function makeCos() {
        System.out.println("For a function of the form acos(b(x-c)): ");
        System.out.println("If a = 0.0 is entered, then this will be set by default to a = 1.0 instead. For a = 0.0, "
                + "please construct a zero polynomial");
        System.out.print("Enter a: ");
        double a = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Cosine(a, b, c));
        System.out.println("Success - Cosine: " + fn.name("x") + " made.");
        return fn;
    }

    // EFFECTS: prompts user to create a new Tan Function.
    private Function makeTan() {
        System.out.println("For a function of the form atan(b(x-c)): ");
        System.out.println("If a = 0.0 is entered, then this will be set by default to a = 1.0 instead. For a = 0.0, "
                + "please construct a zero polynomial");
        System.out.print("Enter a: ");
        double a = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Tan(a, b, c));
        System.out.println("Success - Tan: " + fn.name("x") + " made.");
        return fn;
    }

    // For doMake(): (selectFn() is used for doMake, doEdit, and doAnalyze()) //

    // REQUIRES: wl.isEmpty() to be false (a non-empty worklist)
    // EFFECTS: Selects a function for modification of wl
    private Function selectFn() {
        System.out.print("What position in the worklist is the function you are interested in? ");
        int index = input.nextInt();
        while (index > wl.length() || index < 1) {
            System.out.print("Invalid index. Re-enter the index. Your index should satisfy the inequality "
                    + "1 <= index <= " + wl.length() + " to be valid: ");
            index = input.nextInt();
        }
        Function fn = wl.getListFn().get(index - 1);
        System.out.println("The function " + fn.name("x") + " is selected.");
        return fn;
    }

    // MODIFIES: this
    // EFFECTS: Selects an operation (.) and a second function (fn1) from the worklist, performs fn.fn1 and adds it to
    // the worklist. The user may also choose to remove fn and fn1 from the worklist thereafter
    private void selectOperation(Function fn) {
        String selection = "";
        while (!(selection.equals("a") || selection.equals("s") || selection.equals("p")
                || selection.equals("d") || selection.equals("c"))) {
            System.out.println("\nWhat would you like to do with this function:");
            System.out.println("\ta -> add it to another function");
            System.out.println("\ts -> subtract another function from this function");
            System.out.println("\tp -> take the product of this function with another function");
            System.out.println("\td -> take the quotient of this function over another function");
            System.out.println("\tc -> form the composition of this function and another function");
            selection = input.next().toLowerCase();
        }
        System.out.println("Now select the second function:");
        Function fn1 = selectFn();
        wl.insertFunc(make(fn, fn1, selection));
        System.out.print("Added the newly made function to the worklist. Would you also like to remove the two "
                + "functions used? yes -> y, no -> any other key: ");
        String answer = input.next();
        if (answer.equals("y")) {
            wl.removeFunc(fn);
            wl.removeFunc(fn1);
        }
    }

    // REQUIRES: That selection is one of "a", "s", "p", "d", or "c".
    // EFFECTS: Modifies fn with functions selected by the user according to the selection string
    private Function make(Function fn, Function fn1, String selection) {
        if (selection.equals("a")) {
            return fn.add(fn1);
        } else if (selection.equals("s")) {
            return fn.sub(fn1);
        } else if (selection.equals("p")) {
            return fn.prod(fn1);
        } else if (selection.equals("d")) {
            if (fn1.name("x").equals("0.0")) {
                System.out.println("You've divided by the zero polynomial, so a new copy of the chosen function will "
                        + "be made. If you choose to modify the original or its copy, both will be modified");
            }
            return fn.div(fn1);
        } else {
            return fn.compose(fn1);
        }
    }

    // for doEdit() //

    // REQUIRES: The worklist to have at least one basic function in it.
    // MODIFIES: this, fn.
    // EFFECTS: Swaps a user chosen basic function, f1, from the worklist with another user chosen basis function,
    // f2 in the worklist, and removes bfn1 from the worklist. If bfn1 is not part of fn, fn remains unmodified
    // and the method does nothing.
    private void swap(Function fn) {
        System.out.println("Please choose the basic function from the worklist that may be part of your function.");
        Function f1 = selectBasicFn();
        BasicFunction bfn1 = f1.getFnn().getFn();
        System.out.println("Please choose the basic function from the worklist that you want to substitute into "
                + "the function.");
        Function f2 = selectBasicFn();
        BasicFunction bfn2 = f2.getFnn().getFn();
        String oldName = fn.name("x");
        fn.modify(bfn1, bfn2);
        if (!fn.name("x").equals(oldName)) {
            wl.removeFunc(f1);
            System.out.println("Swapped the basic functions as requested. The function is now: " + fn.name("x")
                    + " and " + f1.name("x") + " is removed from your worklist.");
        } else {
            System.out.println("The function is left unchanged: " + oldName);
        }
    }

    // REQUIRES: the worklist to have at least one basic function in it.
    // EFFECTS: Selects a basic function from the worklist.
    private Function selectBasicFn() {
        Function bfn = selectFn();
        while (!bfn.getFnn().getIsBasicFunc()) {
            System.out.println("Not a valid basic function. Please select again");
            bfn = selectFn();
        }
        return bfn;
    }

    // for doAnalyze() //

    // EFFECTS: evaluates the function at a given x values. Allows for repeated evaluation. Quits if division by
    // 0.0 is encountered.
    private void evaluate(Function fn) {
        try {
            System.out.print("Given f(x) = " + fn.name("x") + ", what x value would you like to evaluate f at? ");
            double x = input.nextDouble();
            double y = fn.eval(x);
            System.out.println("f(" + x + ") = " + y);
            System.out.print("Would you like to evaluate the function again? yes -> y, no -> any other key: ");
            String answer = input.next().toLowerCase();
            if (answer.equals("y")) {
                evaluate(fn);
            }
        } catch (ArithmeticException e) {
            System.out.println("An arithmetic error occurred during evaluation. This can perhaps occur due to division "
                    + "by zero when evaluating the function. Please try again with a different function and/or x"
                    + "value");
        }
    }

    // EFFECTS: integrates the function on an interval [a,b] chosen by the user. Allows for repetition with different
    // subintervals. Quits if division by 0.0 is encountered
    private void integrate(Function fn) {
        try {
            System.out.println("Given f(x) = " + fn.name("x") + ", on what interval [a,b] would you like to "
                    + "integrate f on?");
            System.out.print("a = ");
            double a = input.nextDouble();
            System.out.print("b = ");
            double b = input.nextDouble();
            double y = fn.integrate(a, b);
            System.out.println("The integral of f on [" + a + "," + b + "] = " + y);
            System.out.print("Would you like to integrate the function again (on either the same or another interval)? "
                    + "yes -> y, no -> any other key: ");
            String answer = input.next().toLowerCase();
            if (answer.equals("y")) {
                integrate(fn);
            }
        } catch (ArithmeticException e) {
            System.out.println("An arithmetic error occurred during integration. This can perhaps occur due to division"
                    + " by zero during numerical integration. Please try again with a different function and/or "
                    + "interval");
        }
    }

    // MODIFIES: this
    // EFFECTS: computes the first n terms of the fourier series of fn on [-l,l], with which fourier series
    // (sine, cosine, full) being chosen by the user. The user may also choose to add the fourier series computed as
    // a function in their worklist.
    private void fourierSeries(Function fn) {
        int n = getN();
        double l = getL();
        String selection = "";
        while (!(selection.equals("s") || selection.equals("c") || selection.equals("f"))) {
            System.out.println("Which fourier series in particular are you interested in?");
            System.out.println("\ts -> Fourier Sine Series");
            System.out.println("\tc -> Fourier Cosine Series");
            System.out.println("\tf -> Full Fourier Series");
            selection = input.next().toLowerCase();
        }
        if (selection.equals("s")) {
            getFourierSine(fn, n, l);
        } else if (selection.equals("c")) {
            getFourierCosine(fn, n, l);
        } else {
            getFullFourier(fn, n, l);
        }
    }

    // EFFECTS: gets an n value from the user (number of terms desired) for Fourier Series computation
    private int getN() {
        System.out.print("How many terms (n) of a Fourier Series would you like to compute? n = ");
        int n = input.nextInt();
        while (n <= 0) {
            System.out.println("The choice for n is invalid. Please choose a positive integer for n.");
            System.out.print("n = ");
            n = input.nextInt();
        }
        return n;
    }

    // EFFECTS: gets an l value from the user (half width of interval) for Fourier Series computation
    private double getL() {
        System.out.print("On what interval [-l,l] would you like to compute a Fourier Series? l = ");
        double l = input.nextDouble();
        while (l == 0) {
            System.out.println("The choice for l is invalid. Please choose a non zero value for l.");
            System.out.print("l = ");
            l = input.nextInt();
        }
        if (l < 0) {
            l *= -1;
        }
        return l;
    }

    // MODIFIES: this
    // EFFECTS: computes the first n terms of the fourier sine series of fn on [-l,l]. The user may also choose to add
    // the fourier series computed as a function in their worklist.
    private void getFourierSine(Function fn, int n, double l) {
        try {
            Function result = fn.fourierSine(l, n);
            System.out.println("The first " + n + " terms of the Fourier Sine Series on [-" + l + "," + l + "] are:");
            System.out.println(result.name("x"));
            System.out.print("Would you like to add this function to your worklist? yes -> y, no -> any other key: ");
            String selection = input.next().toLowerCase();
            if (selection.equals("y")) {
                wl.insertFunc(result);
                System.out.println("The computed Fourier Sine Series has been added to your worklist");
            }
            System.out.print("Would you like to continue with computing Fourier Series for this function, or return to "
                    + "the main menu? yes -> y, no -> any other key : ");
            String goBack = input.next().toLowerCase();
            if (goBack.equals("y")) {
                fourierSeries(fn);
            }
        } catch (ArithmeticException e) {
            System.out.println("An arithmetic error occurred when computing the Fourier Sine Series. This can perhaps "
                    + "occur due to division by zero during numerical integration when finding the coefficients. Please"
                    + "try again with a different function or choice of interval");
        }
    }

    // MODIFIES: this
    // EFFECTS: computes the first n terms of the fourier cosine series of fn on [-l,l]. The user may also choose to add
    // the fourier series computed as a function in their worklist.
    private void getFourierCosine(Function fn, int n, double l) {
        try {
            Function result = fn.fourierCosine(l, n);
            System.out.println("The first " + n + " terms of the Fourier Cosine Series on [-" + l + "," + l + "] are:");
            System.out.println(result.name("x"));
            System.out.print("Would you like to add this function to your worklist? yes -> y, no -> any other key: ");
            String selection = input.next().toLowerCase();
            if (selection.equals("y")) {
                wl.insertFunc(result);
                System.out.println("The computed Fourier Cosine Series has been added to your worklist");
            }
            System.out.print("Would you like to continue with computing Fourier Series for this function, or return to "
                    + "the main menu? yes -> y, no -> any other key : ");
            String goBack = input.next().toLowerCase();
            if (goBack.equals("y")) {
                fourierSeries(fn);
            }
        } catch (ArithmeticException e) {
            System.out.println("An arithmetic error occurred when computing the Fourier Cosine Series. This can perhaps"
                    + " occur due to division by zero during numerical integration when finding the coefficients. "
                    + "Please try again with a different function or choice of interval");
        }
    }

    // MODIFIES: this
    // EFFECTS: computes the first n terms of the full fourier series of fn on [-l,l]. The user may also choose to add
    // the fourier series computed as a function in their worklist.
    private void getFullFourier(Function fn, int n, double l) {
        try {
            Function result = fn.fourierFull(l, n);
            System.out.println("The first " + n + " terms of the Full Fourier Series on [-" + l + "," + l + "] are:");
            System.out.println(result.name("x"));
            System.out.print("Would you like to add this function to your worklist? yes -> y, no -> any other key: ");
            String selection = input.next().toLowerCase();
            if (selection.equals("y")) {
                wl.insertFunc(result);
                System.out.println("The computed Full Fourier Series has been added to your worklist");
            }
            System.out.print("Would you like to continue with computing Fourier Series for this function, or return to "
                    + "the main menu? yes -> y, no -> any other key : ");
            String goBack = input.next().toLowerCase();
            if (goBack.equals("y")) {
                fourierSeries(fn);
            }
        } catch (ArithmeticException e) {
            System.out.println("An arithmetic error occurred when computing the Full Fourier Series. This can perhaps "
                    + "occur due to division by zero during numerical integration when finding the coefficients. Please"
                    + "try again with a different function or choice of interval");
        }
    }

    // EFFECTS: Compares fn to a function chosen by the user, fn1, at a value chosen by the user "x"
    private void compare(Function fn) {
        System.out.println("Please select a second function to compare your original function to from the worklist.");
        Function fn1 = selectFn();
        System.out.print("Now please select an x value at which you would like to compare the functions. x = ");
        double x = input.nextDouble();
        try {
            double y = fn1.approxBy(fn1, x);
            if (y == 0) {
                System.out.println("Within the precision set, " + fn1.name("x") + " approximates " + fn.name("x")
                        + " well at x = " + x);
            } else if (y > 0) {
                System.out.println(fn1.name("x") + " overestimates " + fn.name("x")
                        + "at x = " + x + " by " + y + " within precision");
            } else {
                System.out.println(fn1.name("x") + " underestimates " + fn.name("x")
                        + "at x = " + x + " by " + -1 * y + " within precision");
            }
        } catch (ArithmeticException e) {
            System.out.println("Evaluating the functions for comparison led to an arithmetic error. This can perhaps "
                    + "occur due to division by zero during evaluation. Please try again at a different x value, or "
                    + "with different functions");
        }
    }

    // for doSettings() //

    // MODIFIES: Function (static state)
    // EFFECTS: Changes the precision and number of subintervals settings in the Function class
    private void changeSettings() {
        String selection = "";
        while (!(selection.equals("p") || selection.equals("s"))) {
            System.out.println("\nWhich setting would you like to modify?");
            System.out.println("\tp -> precision");
            System.out.println("\ts -> number of subintervals used in integration");
            selection = input.next();
        }
        if (selection.equals("p")) {
            System.out.print("Please enter what you would like the new precision value to be: ");
            double precision = input.nextDouble();
            Function.setPrecision(precision);
            System.out.println("The precision has been changed to " + precision);
        } else {
            System.out.print("Please enter how many subintervals you would like the program to use for integration: ");
            int subintervals = input.nextInt();
            while (subintervals < 1) {
                System.out.println("This is an invalid number of subintervals. Please choose a value >= 1: ");
                subintervals = input.nextInt();
            }
            Function.setSubintervals(subintervals);
            System.out.println("The number of subintervals used for integration has been changed to " + subintervals);
        }
    }
}
