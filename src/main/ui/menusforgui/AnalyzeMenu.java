package ui.menusforgui;

import model.*;
import ui.FunctionAppGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

// Class for the menu to analyze functions to the worklist in the FunctionAppGUI. Submenu of MenuTemplate. Has fields
// for two functions that the user may select at a time for analysis, a Boolean fnSelected
public class AnalyzeMenu extends MenuTemplate {
    private Function fn;
    private Function fn1;
    private Boolean toCompare;
    private double xi;
    private JPanel evalPanel;
    private JTextField evalInput;
    private JButton evalSubmit;
    private JLabel evalAns;
    private JPanel integratePanel;
    private JTextField integrateAInput;
    private JTextField integrateBInput;
    private JLabel integrateAns;
    private JButton integrateSubmit;
    private JPanel fourierPanel;
    private JTextField fourierLInput;
    private JTextField fourierNInput;
    private JLabel fourierAns;
    private JButton fourierSubmit;

    // EFFECTS: Creates a menu to analyze a function, by selecting the function the user want to analyze.
    public AnalyzeMenu(Worklist wl) {
        super(wl, "Please select a function in your worklist to analyze: ", "Analyze Functions:",
                true);
        toCompare = false;
    }

    // EFFECTS: If the user wants to compare functions, creates a second menu to prompt the user for the second function
    // to use in comparison
    private AnalyzeMenu(Worklist wl, Function fn, boolean toCompare) {
        super(wl, "Please select a second function to compare your first function to: ",
                "Pick Second Function for Comparison:", true);
        this.fn = fn;
        this.toCompare = toCompare;
    }

    // MODIFIES: this
    // EFFECTS: sets up buttons to select a function
    @Override
    protected ArrayList<JButton> buttons() {
        return buttonsForSelectFn();
    }

    // MODIFIES: this
    // EFFECTS: sets up a menu to select the analysis technique once a function has been chosen. If fnSelected is true,
    // this means that the user wants to compare two functions and has selected the second function - hence compare is
    // called
    @Override
    protected void menuFnTemplate(Function fn) {
        getFrame().dispose();
        if (!toCompare) {
            this.fn = fn;
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel title = new JLabel("Select what you would like to do with the function f(x) = "
                    + fn.name("x"));
            panelSetup(panel, title);
            frame.add(panel, BorderLayout.CENTER);
            frame.setTitle("Select Analysis Method:");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            setFrame(frame);
            setPanel(panel);
            setTitle(title);
        } else {
            compare(fn);
        }
    }

    // EFFECTS: sets up a given JPanel and JLabel
    private void panelSetup(JPanel panel, JLabel title) {
        panel.add(title);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        JButton button1 = new JButton("Evaluate f(x) at some real x value");
        button1.addActionListener(new AnalyzeMenu.ButtonHandler());
        JButton button2 = new JButton("Numerically integrate f(x) on an interval [a.b]");
        button2.addActionListener(new AnalyzeMenu.ButtonHandler());
        JButton button3 = new JButton("Compute the first n terms of the full Fourier Series of f(x) on an "
                + "interval [-l,l]");
        button3.addActionListener(new AnalyzeMenu.ButtonHandler());
        JButton button4 = new JButton("Compare two functions at a given real x value");
        button4.addActionListener(new AnalyzeMenu.ButtonHandler());
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4));
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        setButtons(buttons);
        panel.setLayout(new GridLayout(0, 1));
    }

    // Helper class to handle what analysis technique the user chooses
    private class ButtonHandler implements ActionListener {
        // MODIFIES: this (the AnalyzeMenu)
        // EFFECTS: performs an analysis technique based on which button the user selects.
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(getButtons().get(0))) {
                evaluate();
            } else if (e.getSource().equals(getButtons().get(1))) {
                integrate();
            } else if (e.getSource().equals(getButtons().get(2))) {
                fourier();
            } else if (e.getSource().equals(getButtons().get(3))) {
                getFrame().dispose();
                new AnalyzeMenu(getWl(), fn, true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up a frame for evaluating a function at a given x value
    private void evaluate() {
        JFrame evalFrame = new JFrame();
        evalPanel = new JPanel();
        JLabel evalTitle = new JLabel("What xi value would you like the evaluate " + fn.name("x") + " at? ");
        evalInput = new JTextField(FunctionAppGUI.SIZE);
        evalSubmit = new JButton("Submit");
        evalSubmit.addActionListener(new AnalyzeMenu.ButtonHandlerEval());
        evalAns = new JLabel();
        evalPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        evalPanel.add(evalTitle);
        evalPanel.add(evalInput);
        evalPanel.add(evalSubmit);
        evalPanel.add(evalAns);
        evalFrame.add(evalPanel, BorderLayout.CENTER);
        evalFrame.pack();
        evalFrame.setVisible(true);
    }

    // Helper class to handle buttons selected during evaluation
    private class ButtonHandlerEval implements ActionListener {
        // MODIFIES: this (AnalyzeMenu)
        // EFFECTS: evaluates the function whenever the user has selected evalSubmit
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(evalSubmit)) {
                try {
                    xi = Double.parseDouble(evalInput.getText());
                } catch (NumberFormatException nfe) {
                    xi = 0;
                }
                evalAns.setText(Double.toString(fn.eval(xi)));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up a frame for integrating a function on an interval [a,b]
    private void integrate() {
        JFrame integrateFrame = new JFrame();
        integratePanel = new JPanel();
        JLabel integrateTitle = new JLabel("What interval [a,b] would you like to integrate " + fn.name("x")
                + " over? ");
        JLabel integrateA = new JLabel("enter a: ");
        integrateAInput = new JTextField(FunctionAppGUI.SIZE);
        JLabel integrateB = new JLabel("enter b: ");
        integrateBInput = new JTextField(FunctionAppGUI.SIZE);
        integrateSubmit = new JButton("Submit");
        integrateSubmit.addActionListener(new AnalyzeMenu.ButtonHandlerIntegrate());
        integrateAns = new JLabel();
        integratePanel.setLayout(new GridLayout(0, 1));
        integratePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        integratePanel.add(integrateTitle);
        integrateSetup(integrateA, integrateB);
        integrateFrame.add(integratePanel, BorderLayout.CENTER);
        integrateFrame.pack();
        integrateFrame.setVisible(true);
    }

    private void integrateSetup(JLabel integrateA, JLabel integrateB) {
        integratePanel.add(integrateA);
        integratePanel.add(integrateAInput);
        integratePanel.add(integrateB);
        integratePanel.add(integrateBInput);
        integratePanel.add(integrateSubmit);
        integratePanel.add(integrateAns);
    }

    // Helper class to handle buttons selected during integration
    private class ButtonHandlerIntegrate implements ActionListener {
        // MODIFIES: this (AnalyzeMenu)
        // EFFECTS: integrates the function whenever the user has selected integrateSubmit
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(integrateSubmit)) {
                double a;
                double b;
                try {
                    a = Double.parseDouble(integrateAInput.getText());
                    b = Double.parseDouble(integrateBInput.getText());
                } catch (NumberFormatException nfe) {
                    a = -1;
                    b = 1;
                }
                integrateAns.setText(Double.toString(fn.integrate(a, b)));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up a frame for computing the first n terms of the full fourier series a function on an interval
    // [-l,l]
    private void fourier() {
        JFrame fourierFrame = new JFrame();
        fourierPanel = new JPanel();
        JLabel fourierTitle = new JLabel("What interval [-l,l] would you like to compute the full fourier series "
                + "for " + fn.name("x") + " over? ");
        JLabel fourierL = new JLabel("enter l: ");
        fourierLInput = new JTextField(FunctionAppGUI.SIZE);
        JLabel fourierTitle1 = new JLabel("How many terms of the full fourier series would you like?");
        JLabel fourierN = new JLabel("enter n: ");
        fourierNInput = new JTextField(FunctionAppGUI.SIZE);
        fourierSubmit = new JButton("Submit");
        fourierSubmit.addActionListener(new AnalyzeMenu.ButtonHandlerFourier());
        fourierAns = new JLabel();
        fourierPanelSetup(fourierTitle, fourierL, fourierTitle1, fourierN);
        fourierFrame.add(fourierPanel, BorderLayout.CENTER);
        fourierFrame.pack();
        fourierFrame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Helper method for fourier(), sets up fourierPanel
    private void fourierPanelSetup(JLabel fourierTitle, JLabel fourierL, JLabel fourierTitle1, JLabel fourierN) {
        fourierPanel.setLayout(new GridLayout(0, 1));
        fourierPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        fourierPanel.add(fourierTitle);
        fourierPanel.add(fourierL);
        fourierPanel.add(fourierLInput);
        fourierPanel.add(fourierTitle1);
        fourierPanel.add(fourierN);
        fourierPanel.add(fourierNInput);
        fourierPanel.add(fourierSubmit);
        fourierPanel.add(fourierAns);
    }

    // Helper class to handle buttons selected during fourier series computation
    private class ButtonHandlerFourier implements ActionListener {
        // MODIFIES: this (AnalyzeMenu)
        // EFFECTS: computes the fourier series of the function whenever the user has selected fourierSubmit
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(fourierSubmit)) {
                double l;
                int n;
                try {
                    l = Double.parseDouble(fourierLInput.getText());
                    n = Integer.parseInt(fourierNInput.getText());
                    if (l < 0) {
                        l = -l;
                    }
                    if (n < 0) {
                        n = -n;
                    }
                } catch (NumberFormatException nfe) {
                    l = 1;
                    n = 3;
                }
                Function fn2 = fn.fourierFull(l, n);
                getWl().insertFunc(fn2);
                String ans = "The first " + n + " terms of the full fourier series on [" + -l + "," + l + "] are: \n"
                        + fn2.name("x") + "\n This has been added to the" + " worklist.";
                fourierAns.setText("<html>" + ans.replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up a frame for comparing two functions, this.fn and fn1
    private void compare(Function fn1) {
        this.fn1 = fn1;
        JFrame evalFrame = new JFrame();
        evalPanel = new JPanel();
        JLabel evalTitle = new JLabel("What x value would you like the evaluate the difference between the"
                + " functions at? ");
        evalInput = new JTextField(FunctionAppGUI.SIZE);
        evalSubmit = new JButton("Submit");
        evalSubmit.addActionListener(new AnalyzeMenu.ButtonHandlerComp());
        evalAns = new JLabel();
        evalPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        evalPanel.add(evalTitle);
        evalPanel.add(evalInput);
        evalPanel.add(evalSubmit);
        evalPanel.add(evalAns);
        evalFrame.add(evalPanel, BorderLayout.CENTER);
        evalFrame.pack();
        evalFrame.setVisible(true);
    }

    // Helper class to handle buttons selected during function comparison
    private class ButtonHandlerComp implements ActionListener {
        // MODIFIES: this (AnalyzeMenu)
        // EFFECTS: compares fn and fn1 whenever the user selects evalSubmit
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(evalSubmit)) {
                try {
                    xi = Double.parseDouble(evalInput.getText());
                } catch (NumberFormatException nfe) {
                    xi = 0;
                }
                evalAns.setText(Double.toString(fn.eval(xi) - fn1.eval(xi)));
            }
        }
    }
}
