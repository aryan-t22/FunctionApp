package ui.menusforgui;

import model.Function;
import model.Worklist;
import model.basicfns.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMenu extends MenuTemplate {

    public AddMenu(Worklist wl) {
        super(wl, "Please select a basic function to add to your worklist: ", "Add a function:",
                false);
    }

    @Override
    protected ArrayList<JButton> buttons() {
        JButton buttonPolynomial = new JButton("Add a polynomial");
        JButton buttonExp = new JButton("Add an exponential function");
        JButton buttonSine = new JButton("Add a sine function");
        JButton buttonCosine = new JButton("Add a cosine function");
        JButton buttonTan = new JButton("Add a tangent function");
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(buttonPolynomial, buttonExp, buttonSine,
                buttonCosine, buttonTan));
        modify(buttons);
        return buttons;
    }

    @Override
    protected void modify(ArrayList<JButton> buttons) {
        buttons.get(0).addActionListener(new AddMenu.ButtonHandler());
        buttons.get(1).addActionListener(new AddMenu.ButtonHandler());
        buttons.get(2).addActionListener(new AddMenu.ButtonHandler());
        buttons.get(3).addActionListener(new AddMenu.ButtonHandler());
        buttons.get(4).addActionListener(new AddMenu.ButtonHandler());
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<JButton> buttons = getButtons();
            Worklist wl = getWl();
            if (e.getSource().equals(buttons.get(0))) {
                new PolynomialMenu(wl);
            } else if (e.getSource().equals(buttons.get(1))) {
                new StandardMenu(wl, "e^");
            } else if (e.getSource().equals(buttons.get(2))) {
                new StandardMenu(wl, "sin");
            } else if (e.getSource().equals(buttons.get(3))) {
                new StandardMenu(wl, "cos");
            } else if (e.getSource().equals(buttons.get(4))) {
                new StandardMenu(wl, "tan");
            }
        }
    }

    private class PolynomialMenu {
        private JFrame frame1;
        private JPanel panel1;
        private Worklist wl;
        private JButton next;
        private JTextField degreeField;
        private ArrayList<JTextField> fields = new ArrayList<>();
        private List<Double> params = new ArrayList<>();
        private JButton submit;
        private int degree;
        JFrame frame2 = new JFrame();
        JPanel panel2 = new JPanel();

        public PolynomialMenu(Worklist wl) {
            this.wl = wl;
            frame1 = new JFrame();
            panel1 = new JPanel();
            JLabel degreeQuestion = new JLabel("What is the degree of the polynomial: ");
            degreeField = new JTextField();
            next = new JButton("Next");
            next.addActionListener(new PolynomialMenu.ButtonHandler());
            panel1.add(degreeQuestion);
            panel1.add(degreeField);
            panel1.add(next);
            panel1.setLayout(new GridLayout(1, 0));
            frame1.add(panel1, BorderLayout.CENTER);
            frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame1.pack();
            frame1.setVisible(true);
        }

        private void polynomialSubMenu() {
            try {
                degree = Integer.parseInt(degreeField.getText());
            } catch (NumberFormatException nfe) {
                degree = 0;
            }
            for (int i = 0; i <= degree; i++) {
                JLabel coeffQuestion = new JLabel("What is the x^" + i + " coefficient: ");
                JTextField field = new JTextField();
                fields.add(field);
                panel2.add(coeffQuestion);
                panel2.add(field);
            }
            submit = new JButton("Submit");
            submit.addActionListener(new PolynomialMenu.ButtonHandler());
            panel2.add(submit);
            panel2.setLayout(new GridLayout(0, 1));
            frame2.add(panel2, BorderLayout.CENTER);
            frame2.pack();
            frame2.setVisible(true);
        }

        private class ButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(next)) {
                    frame1.dispose();
                    polynomialSubMenu();
                } else if (e.getSource().equals(submit)) {
                    adjust();
                    wl.insertFunc(new Function(new Polynomial(params)));
                    frame2.dispose();
                }
            }

            private void adjust() {
                for (JTextField f : fields) {
                    double param;
                    try {
                        param = Double.parseDouble(f.getText());
                    } catch (NumberFormatException nfe) {
                        param = 0.0;
                    }
                    params.add(param);
                }
            }
        }
    }

    private class StandardMenu {
        private Worklist wl;
        private JFrame frame1;
        private JPanel panel1;
        private ArrayList<JTextField> fields = new ArrayList<>();
        private List<Double> params = new ArrayList<>();
        private JButton submit;
        private String funcString;

        public StandardMenu(Worklist wl, String funcString) {
            this.wl = wl;
            this.funcString = funcString;
            frame1 = new JFrame();
            panel1 = new JPanel();
            standardPanelSetup();
            for (int i = 0; i <= 2; i++) {
                List<String> vars = new ArrayList<>(Arrays.asList("A", "b", "c"));
                JLabel coeffQuestion1 = new JLabel("Enter " + vars.get(i) + ": ");
                JTextField field = new JTextField();
                fields.add(field);
                panel1.add(coeffQuestion1);
                panel1.add(field);
            }
            submit = new JButton("Submit");
            submit.addActionListener(new StandardMenu.ButtonHandler());
            panel1.add(submit);
            panel1.setLayout(new GridLayout(0, 1));
            frame1.add(panel1, BorderLayout.CENTER);
            frame1.pack();
            frame1.setVisible(true);
        }

        private void standardPanelSetup() {
            JLabel coeffQuestion;
            if (funcString.equals("e^")) {
                coeffQuestion = new JLabel("For A" + funcString + "(B(x-c)):");
            } else {
                coeffQuestion = new JLabel("For A" + funcString + "(B(x-cπ)):");
            }
            panel1.add(coeffQuestion);
        }


        private class ButtonHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(submit)) {
                    adjust();
                    switch (funcString) {
                        case "sin":
                            wl.insertFunc(new Function(new Sine(params.get(0), params.get(1), params.get(2))));
                            break;
                        case "cos":
                            wl.insertFunc(new Function(new Cosine(params.get(0), params.get(1), params.get(2))));
                            break;
                        case "tan":
                            wl.insertFunc(new Function(new Tan(params.get(0), params.get(1), params.get(2))));
                            break;
                        case "e^":
                            wl.insertFunc(new Function(new Exp(params.get(0), params.get(1), params.get(2))));
                            break;
                    }
                    frame1.dispose();
                }
            }

            private void adjust() {
                for (JTextField f : fields) {
                    double param;
                    try {
                        param = Double.parseDouble(f.getText());
                    } catch (NumberFormatException nfe) {
                        param = 0.0;
                    }
                    params.add(param);
                }
            }
        }
    }
}