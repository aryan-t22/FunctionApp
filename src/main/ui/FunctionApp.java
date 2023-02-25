package ui;

import model.BasicFunction;
import model.Function;
import model.Worklist;
import model.basicfns.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

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
            // doAnalyze();
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
        System.out.println("\tz -> analyze the functions in your worklist");
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
            System.out.println("It is recommended to have more than 1 function in the worklist to take full advantage" +
                    " of the modification features the program offers.");
            System.out.print("If you would like to go back to the main menu to add functions -> y. To continue " +
                    "-> any other key: ");
            String answer = input.next();
            answer = answer.toLowerCase();
            if (!answer.equals("y")) {
                selectOperation(selectFn());
            }
        }
    }

    // EFFECTS: Lets the user either remove a function from the worklist, or for a given function, swap one of the basic
    // functions it consists of with a basic function in the worklist. If the worklist has no basic functions, then
    // choosing the swap option does nothing.
    private void doEdit() {
        if (wl.isEmpty()) {
            System.out.println("Your worklist is empty. Please add a function, or functions to begin modifying");
        } else {
            System.out.println("This feature lets you modify the structure of a selected function.");
            Function fn = selectFn();String selection = "";  // force entry into loop
            while (!(selection.equals("r") || selection.equals("c"))) {
                System.out.println("\nHow would you like to modify the selected function? ");
                System.out.println("\tr -> Remove it from the worklist");
                System.out.println("\tc -> Swap a basic function term in the function with another basic function. " +
                        "An example would be changing (sin(x) + x)/(sin(x) + x^2) into (cos(x) + x)/(cos(x) + x^2)");
                selection = input.next();
                selection = selection.toLowerCase();
            }
            if (selection.equals("r")) {
            System.out.println(fn.name("x") + " removed from the worklist");
            wl.removeFunc(fn);
            } else if (!wl.hasBasicFn()){
            System.out.println("Your worklist has no basic functions. Please add one, and then try using this" +
                    " feature");
            } else {
                swap(fn);
            }
        }
    }

    //// HELPER METHODS ////

    // For doAdd(): //

    // EFFECTS: prompts user to create a new Function.
    private Function createFn() {
        String selection = "";  // force entry into loop
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
        List<Double> params = new ArrayList<>();
        int i = 0;
        while (i < degree + 1) {
            System.out.print("Please enter coefficient of x^" + Integer.toString(i) + ": ");
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
        System.out.println("For a function of the form Ae^(b(x-c)): ");
        System.out.print("Enter A: ");
        double A = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Exp(A, b, c));
        System.out.println("Success - Exponential: " + fn.name("x") + " made.");
        return fn;
    }

    // EFFECTS: prompts user to create a new Sine Function.
    private Function makeSin() {
        System.out.println("For a function of the form Asin(b(x-c)): ");
        System.out.print("Enter A: ");
        double A = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Sine(A, b, c));
        System.out.println("Success - Sine: " + fn.name("x") + " made.");
        return fn;
    }

    // EFFECTS: prompts user to create a new Cosine Function.
    private Function makeCos() {
        System.out.println("For a function of the form Acos(b(x-c)): ");
        System.out.print("Enter A: ");
        double A = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Cosine(A, b, c));
        System.out.println("Success - Cosine: " + fn.name("x") + " made.");
        return fn;
    }

    // EFFECTS: prompts user to create a new Tan Function.
    private Function makeTan() {
        System.out.println("For a function of the form Atan(b(x-c)): ");
        System.out.print("Enter A: ");
        double A = input.nextDouble();
        System.out.print("Enter b: ");
        double b = input.nextDouble();
        System.out.print("Enter c: ");
        double c = input.nextDouble();
        Function fn = new Function(new Tan(A, b, c));
        System.out.println("Success - Tan: " + fn.name("x") + " made.");
        return fn;
    }

    // For doMake() (may also be used in doEdit(): //

    // REQUIRES: wl.isEmpty() to be false (a non-empty worklist)
    // EFFECTS: Selects a function for modification of wl
    private Function selectFn() {
        System.out.print("What position in the worklist is the function you are interested in? ");
        int index = input.nextInt();
        while (index > wl.length() || index < 1) {
            System.out.print("Invalid index. Re-enter the index. Your index should satisfy the inequality " +
                    "1 <= index <= " + Integer.toString(wl.length()) + " to be valid: ");
            index = input.nextInt();
        }
        Function fn = wl.getListFn().get(index - 1);
        System.out.println("The function " + fn.name("x") + " is selected.");
        return fn;
    }

    private void selectOperation(Function fn) {
        String selection = "";  // force entry into loop
        while (!(selection.equals("a") || selection.equals("s") || selection.equals("p")
                || selection.equals("d") || selection.equals("c"))) {
            System.out.println("\nWhat would you like to do with this function:");
            System.out.println("\ta -> add it to another function");
            System.out.println("\ts -> subtract another function from this function");
            System.out.println("\tp -> take the product of this function with another function");
            System.out.println("\td -> take the quotient of this function over another function");
            System.out.println("\tc -> form the composition of this function and another function");
            selection = input.next();
            selection = selection.toLowerCase();
        }
        System.out.println("Now select the second function:");
        Function fn1 = selectFn();
        wl.insertFunc(make(fn, fn1, selection));
        System.out.print("Added the newly made function to the worklist. Would you also like to remove the two " +
                "functions used? yes -> y, no -> any other key: ");
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
            return fn.div(fn1);
        } else {
            return fn.compose(fn1);
        }
    }

    // for doEdit() //

    // MODIFIES: this, fn.
    // REQUIRES: The worklist to have at least one basic function in it.
    // EFFECTS: Swaps a user chosen basic function, bfn1, from fn, with another user chosen basic
    // function, bfn2 in the worklist. If bfn1 is not part of fn, fn remains unmodified
    private void swap(Function fn) {
        System.out.println("Please choose the basic function from the worklist that may be part of your function.");
        BasicFunction bfn1 = selectBasicFn().getFnn().getFn();
        System.out.println("Please choose the basic function from the worklist that you want to substitute into " +
                "the function.");
        BasicFunction bfn2 = selectBasicFn().getFnn().getFn();
        fn.modify(bfn1, bfn2);
        System.out.println("Swapped the basic functions as requested. The function is now: " + fn.name("x"));
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
}
