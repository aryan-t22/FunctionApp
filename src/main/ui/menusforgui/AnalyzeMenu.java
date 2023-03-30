package ui.menusforgui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

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

    public AnalyzeMenu(Worklist wl) {
        super(wl, "Please select a function in your worklist to analyze: ", "Analyze Functions:",
                true);
        toCompare = false;
    }

    private AnalyzeMenu(Worklist wl, Function fn, boolean toCompare) {
        super(wl, "Please select a function in your worklist to analyze: ", "Analyze Functions:",
                true);
        this.fn = fn;
        this.toCompare = toCompare;
    }

    @Override
    protected ArrayList<JButton> buttons() {
        return buttonsForSelectFn();
    }

    @Override
    protected void menuFnTemplate(Function fn) {
        getFrame().dispose();
        if (!toCompare) {
            this.fn = fn;
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel title = new JLabel("Select what you would like to do with the function f(xi) = "
                    + fn.name("xi"));
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

    private void panelSetup(JPanel panel, JLabel title) {
        panel.add(title);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        JButton button1 = new JButton("Evaluate f(xi) at some real xi value");
        button1.addActionListener(new AnalyzeMenu.ButtonHandler());
        JButton button2 = new JButton("Numerically integrate f(xi) on an interval [a.b], with a <= b");
        button2.addActionListener(new AnalyzeMenu.ButtonHandler());
        JButton button3 = new JButton("Compute the first n terms of the full Fourier Series of f(xi) on an "
                + "interval [-l,l]");
        button3.addActionListener(new AnalyzeMenu.ButtonHandler());
        JButton button4 = new JButton("Compare two functions at a certain real xi value");
        button4.addActionListener(new AnalyzeMenu.ButtonHandler());
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4));
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        setButtons(buttons);
        panel.setLayout(new GridLayout(0, 1));
    }

    private class ButtonHandler implements ActionListener {
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

    private void evaluate() {
        JFrame evalFrame = new JFrame();
        evalPanel = new JPanel();
        JLabel evalTitle = new JLabel("What xi value would you like the evaluate " + fn.name("xi") + " at? ");
        evalInput = new JTextField();
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

    private class ButtonHandlerEval implements ActionListener {
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

    private void integrate() {
        JFrame integrateFrame = new JFrame();
        integratePanel = new JPanel();
        JLabel integrateTitle = new JLabel("What interval [a,b] would you like to integrate " + fn.name("xi")
                + " over? ");
        JLabel integrateA = new JLabel("enter a: ");
        integrateAInput = new JTextField();
        JLabel integrateB = new JLabel("enter b: ");
        integrateBInput = new JTextField();
        integrateSubmit = new JButton("Submit");
        integrateSubmit.addActionListener(new AnalyzeMenu.ButtonHandlerIntegrate());
        integrateAns = new JLabel();
        integratePanel.setLayout(new GridLayout(0, 1));
        integratePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        integratePanel.add(integrateTitle);
        integratePanel.add(integrateA);
        integratePanel.add(integrateAInput);
        integratePanel.add(integrateB);
        integratePanel.add(integrateBInput);
        integratePanel.add(integrateSubmit);
        integratePanel.add(integrateAns);
        integrateFrame.add(integratePanel, BorderLayout.CENTER);
        integrateFrame.pack();
        integrateFrame.setVisible(true);
    }

    private class ButtonHandlerIntegrate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(integrateSubmit)) {
                double a;
                double b;
                try {
                    a = Double.parseDouble(integrateAInput.getText());
                    b = Double.parseDouble(integrateBInput.getText());
                    if (a > b) {
                        double b1 = b;
                        b = a;
                        a = b1;
                    }
                } catch (NumberFormatException nfe) {
                    a = -1;
                    b = 1;
                }
                integrateAns.setText(Double.toString(fn.integrate(a, b)));
            }
        }
    }

    private void fourier() {
        JFrame fourierFrame = new JFrame();
        fourierPanel = new JPanel();
        JLabel fourierTitle = new JLabel("What interval [-l,l] would you like to compute the full fourier series "
                + "for " + fn.name("xi") + " over? ");
        JLabel fourierL = new JLabel("enter l: ");
        fourierLInput = new JTextField();
        JLabel fourierTitle1 = new JLabel("How many terms of the full fourier series would you like?");
        JLabel fourierN = new JLabel("enter n: ");
        fourierNInput = new JTextField();
        fourierSubmit = new JButton("Submit");
        fourierSubmit.addActionListener(new AnalyzeMenu.ButtonHandlerFourier());
        fourierAns = new JLabel();
        fourierPanelSetup(fourierTitle, fourierL, fourierTitle1, fourierN);
        fourierFrame.add(fourierPanel, BorderLayout.CENTER);
        fourierFrame.pack();
        fourierFrame.setVisible(true);
    }

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

    private class ButtonHandlerFourier implements ActionListener {
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
                String ans = "The full fourier series is: \n" + fn2.name("xi") + "\n This has been added to the"
                        + " worklist.";
                fourierAns.setText("<html>" + ans.replaceAll("<", "&lt;")
                        .replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
            }
        }
    }

    private void compare(Function fn1) {
        this.fn1 = fn1;
        JFrame evalFrame = new JFrame();
        evalPanel = new JPanel();
        JLabel evalTitle = new JLabel("What xi value would you like the evaluate the difference between the"
                + " functions at");
        evalInput = new JTextField();
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

    private class ButtonHandlerComp implements ActionListener {
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
