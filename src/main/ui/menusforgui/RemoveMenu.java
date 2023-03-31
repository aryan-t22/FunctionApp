package ui.menusforgui;

import model.Function;
import model.Worklist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Class for the menu to remove functions from the worklist in the FunctionAppGUI. Submenu of MenuTemplate
public class RemoveMenu extends MenuTemplate {

    public RemoveMenu(Worklist wl) {
        super(wl, "Please select a function in your worklist to edit: ", "Edit Worklist:",
                true);
    }

    // EFFECTS: sets up buttons to select a function
    @Override
    protected ArrayList<JButton> buttons() {
        return buttonsForSelectFn();
    }

    // MODIFIES: this
    // EFFECTS: Once the user has selected a function, removes the function from the worklist
    @Override
    protected void menuFnTemplate(Function fn) {
        getFrame().dispose();
        getWl().removeFunc(fn);
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel title = new JLabel(fn.name("x") + " has been removed from the worklist. Please close the window"
                + " to return to the main menu.");
        panel.add(title);
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Function removed:");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
