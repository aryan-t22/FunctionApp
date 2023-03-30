package ui.menusforgui;

import model.Function;
import model.Worklist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class MakeMenu extends MenuTemplate {
    Boolean toCompare;
    Function fn;
    Function fn1;
    String operation;

    public MakeMenu(Worklist wl) {
        super(wl, "Please select a function in your worklist to start making more complicated functions: ",
                "Make more complicated Functions:", true);
        toCompare = false;
    }

    private MakeMenu(Worklist wl, Function fn, boolean toCompare, String operation) {
        super(wl, "Please select a function in your worklist to start making more complicated functions: ",
                "Make more complicated Functions:", true);
        this.fn = fn;
        this.toCompare = toCompare;
        this.operation = operation;
    }

    @Override
    protected ArrayList<JButton> buttons() {
        return buttonsForSelectFn();
    }

    @Override
    protected void menuFnTemplate(Function fn) {
        getFrame().dispose();
        if (!toCompare) {
            JFrame frame = new JFrame();
            JPanel panel = new JPanel();
            JLabel title = new JLabel();
            this.fn = fn;
            title.setText("Select what you would like to do with the function f(x) = " + fn.name("x"));
            panelSetup(panel, title);
            frame.setTitle("Select Operation:");
            setup(frame, panel, title);
        } else {
            JFrame frame1 = new JFrame();
            JPanel panel1 = new JPanel();
            JLabel title1 = new JLabel();
            fn1 = fn;
            panel1.setLayout(new GridLayout(0, 1));
            panel1.add(title1);
            panel1.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
            setup(frame1, panel1, title1);
            getWl().insertFunc(new Function(operation, this.fn, fn1));
            title1.setText("Created the function: " + this.fn.name("x") + " " + operation + " " + fn1.name("x"));
            frame1.setTitle("Function Made!");
        }
    }

    private void setup(JFrame frame, JPanel panel, JLabel title) {
        setFrame(frame);
        setPanel(panel);
        setTitle(title);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void panelSetup(JPanel panel, JLabel title) {
        panel.add(title);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        JButton button1 = new JButton("Add this to another function in the worklist");
        button1.addActionListener(new MakeMenu.ButtonHandler());
        JButton button2 = new JButton("Subtract another function in the worklist from this function");
        button2.addActionListener(new MakeMenu.ButtonHandler());
        JButton button3 = new JButton("Take the product of this function with another in the worklist");
        button3.addActionListener(new MakeMenu.ButtonHandler());
        JButton button4 = new JButton("Take the quotient of this function with another from the worklist");
        button4.addActionListener(new MakeMenu.ButtonHandler());
        JButton button5 = new JButton("Take the composition of this function with another from the worklist");
        button5.addActionListener(new MakeMenu.ButtonHandler());
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5));
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);
        panel.add(button5);
        setButtons(buttons);
        panel.setLayout(new GridLayout(0, 1));
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            getFrame().dispose();
            if (e.getSource().equals(getButtons().get(0))) {
                new MakeMenu(getWl(), fn, true, "+");
            } else if (e.getSource().equals(getButtons().get(1))) {
                new MakeMenu(getWl(), fn, true, "-");
            } else if (e.getSource().equals(getButtons().get(2))) {
                new MakeMenu(getWl(), fn, true, "*");
            } else if (e.getSource().equals(getButtons().get(3))) {
                new MakeMenu(getWl(), fn, true, "/");
            } else if (e.getSource().equals(getButtons().get(4))) {
                new MakeMenu(getWl(), fn, true, "o");
            }
        }
    }
}
