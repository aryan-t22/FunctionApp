package ui.menusforgui;

import model.Function;
import model.Worklist;
import ui.FunctionAppGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;


// Class for the menu to adjust the settings of the FunctionAppGUI. Submenu of MenuTemplate
public class SettingsMenu extends MenuTemplate {
    private JButton submitPrecision;
    private JButton submitSubinterval;
    private JTextField precision;
    private JTextField subintervals;


    public SettingsMenu(Worklist wl) {
        super(wl, "Please select an option below: (Note that proper functionality is only guaranteed at"
                + " default settings).", "Modify settings:", false);
    }

    // EFFECTS: constructs buttons for viewing the current settings, modifying precision/subintervals, or restoring
    // settings to their defaults.
    @Override
    protected ArrayList<JButton> buttons() {
        JButton buttonView = new JButton("View the current settings");
        JButton buttonPrecision = new JButton("Modify the precision used in the program");
        JButton buttonSubIntervals = new JButton("Modify the number of subintervals used for numeric integration "
                + "and Fourier Series");
        JButton buttonDefault = new JButton("Reset settings to default");
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(buttonView, buttonPrecision, buttonSubIntervals,
                buttonDefault));
        buttons.get(0).addActionListener(new SettingsMenu.ButtonHandler());
        buttons.get(1).addActionListener(new SettingsMenu.ButtonHandler());
        buttons.get(2).addActionListener(new SettingsMenu.ButtonHandler());
        buttons.get(3).addActionListener(new SettingsMenu.ButtonHandler());
        return buttons;
    }

    // This subclass does not require an implementation of this method
    @Override
    protected void menuFnTemplate(Function fn) {

    }

    // Helper class to handle selection of buttons from this.GetButtons()
    private class ButtonHandler implements ActionListener {
        // REQUIRES: getButtons() to have 4 or more entries
        // MODIFIES: this (SettingsMenu), Function (static precision and subintervals fields)
        // EFFECTS: Creates a new menu based on which option the user selects
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<JButton> buttons = getButtons();
            if (e.getSource().equals(buttons.get(0))) {
                viewSettings();
            } else if (e.getSource().equals(buttons.get(1))) {
                setPrecision();
            } else if (e.getSource().equals(buttons.get(2))) {
                setSubintervals();
            } else if (e.getSource().equals(buttons.get(3))) {
                setDefault();
            } else if (e.getSource().equals(submitPrecision)) {
                handlePrecision();
            } else if (e.getSource().equals(submitSubinterval)) {
                handleSubintervals();
            }
        }
    }

    // handles a selected precision - if the user inputted precision is negative or if the input is invalid, the
    // precision is set to default
    private void handlePrecision() {
        try {
            Function.setPrecision(Double.parseDouble(precision.getText()));
            if (Double.parseDouble(precision.getText()) < 0) {
                throw new NumberFormatException();
            }
            successWindow();
        } catch (NumberFormatException nfe) {
            Function.setPrecision(Function.DEFAULT_PRECISION);
            failureWindow();
        }
    }

    // EFFECTS: handles a selected number of subintervals - if the user inputted number of subintervals is negative or
    // if the input is invalid, the precision is set to default
    private void handleSubintervals() {
        try {
            Function.setSubintervals(Integer.parseInt(subintervals.getText()));
            if (Integer.parseInt(subintervals.getText()) < 0) {
                throw new NumberFormatException();
            }
            successWindow();
        } catch (NumberFormatException nfe) {
            Function.setPrecision(Function.DEFAULT_PRECISION);
            failureWindow();
        }
    }

    // EFFECTS: Helper method letting the user know the process was successful
    private void successWindow() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel title = new JLabel("Success! Please close the window to return to the settings menu.");
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: Helper method letting the user know the process has failed and that their parameter choice was invalid.
    private void failureWindow() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel title = new JLabel("The provided parameter was invalid. The setting has been set to default. "
                + "Please close the window to return to the settings menu.");
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // EFFECTS: Creates a frame to allow the user to view the current settings
    private void viewSettings() {
        JFrame frame1 = new JFrame();
        JPanel panel1 = new JPanel();
        JLabel title1 = new JLabel("Here are the current settings:");
        JLabel precision = new JLabel("Precision: " + Function.getPrecision());
        JLabel subintervals = new JLabel("Subintervals: " + Function.getSubintervals());
        panel1.add(title1);
        panel1.add(precision);
        panel1.add(subintervals);
        panel1.setLayout(new GridLayout(0, 1));
        frame1.add(panel1, BorderLayout.CENTER);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }

    // MODIFIES: this, Function (static precision field)
    // EFFECTS: Creates a frame to allow the user to set the current precision.
    private void setPrecision() {
        JFrame frame1 = new JFrame();
        JPanel panel1 = new JPanel();
        JLabel title1 = new JLabel("Enter the precision value here:");
        precision = new JTextField(FunctionAppGUI.SIZE);
        submitPrecision = new JButton("Submit");
        submitPrecision.addActionListener(new SettingsMenu.ButtonHandler());
        panel1.add(title1);
        panel1.add(precision);
        panel1.add(submitPrecision);
        panel1.setLayout(new GridLayout(0, 1));
        frame1.add(panel1, BorderLayout.CENTER);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }

    // MODIFIES: this, Function (static subintervals field)
    // EFFECTS: Creates a frame to allow the user to set the current number of subintervals
    private void setSubintervals() {
        JFrame frame1 = new JFrame();
        JPanel panel1 = new JPanel();
        JLabel title1 = new JLabel("Enter the number of subintervals here:");
        subintervals = new JTextField(FunctionAppGUI.SIZE);
        submitSubinterval = new JButton("Submit");
        submitSubinterval.addActionListener(new SettingsMenu.ButtonHandler());
        panel1.add(title1);
        panel1.add(subintervals);
        panel1.add(submitSubinterval);
        panel1.setLayout(new GridLayout(0, 1));
        frame1.add(panel1, BorderLayout.CENTER);
        frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame1.pack();
        frame1.setVisible(true);
    }

    // MODIFIES: this, Function (static precision and subintervals fields)
    // EFFECTS: Creates a frame letting the user know that settings have been reset to default, resets settings to
    // default.
    private void setDefault() {
        Function.setPrecision(Function.DEFAULT_PRECISION);
        Function.setSubintervals(Function.DEFAULT_SUBINTERVALS);
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel title = new JLabel("Success! Please close the window to return to the settings menu.");
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
