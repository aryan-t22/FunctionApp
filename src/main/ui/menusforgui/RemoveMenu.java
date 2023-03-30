package ui.menusforgui;

import model.Function;
import model.Worklist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RemoveMenu extends MenuTemplate {

    public RemoveMenu(Worklist wl) {
        super(wl, "Please select a function in your worklist to edit: ", "Edit Worklist:",
                true);
    }

    @Override
    protected ArrayList<JButton> buttons() {
        return buttonsForSelectFn();
    }

    @Override
    protected void menuFnTemplate(Function fn) {
        getFrame().dispose();
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
