package ui.menusforgui;

import model.Function;
import model.Worklist;

import javax.swing.*;
import java.util.ArrayList;

// Class for the menu to view functions in the worklist in the FunctionAppGUI. Submenu of MenuTemplate
public class ViewMenu extends MenuTemplate  {

    // EFFECTS have been included for clarity.
    // EFFECTS: creates a frame to view the names of functions in the worklist
    public ViewMenu(Worklist wl) {
        super(wl, "Your Worklist: ", "Viewing your Worklist:", false);
        String ans = wl.names();
        JLabel names = new JLabel();
        names.setText("<html>" + ans.replaceAll("<","&lt;")
                .replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
        getPanel().add(names);
        getFrame().pack();
    }

    // This class does not need implementations of this method
    @Override
    protected ArrayList<JButton> buttons() {
        return new ArrayList<>();
    }

    // This class does not need implementations of this method
    @Override
    protected void menuFnTemplate(Function fn) {

    }
}