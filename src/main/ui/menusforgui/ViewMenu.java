package ui.menusforgui;

import model.Worklist;

import javax.swing.*;
import java.util.ArrayList;

public class ViewMenu extends MenuTemplate  {

    public ViewMenu(Worklist wl) {
        super(wl, "Your Worklist: ", "Viewing your Worklist:", false);
        JLabel names = new JLabel(wl.names());
        getPanel().add(names);
        getFrame().pack();
    }

    @Override
    protected ArrayList<JButton> buttons() {
        return new ArrayList<>();
    }

    @Override
    protected void modify(ArrayList<JButton> buttons) {

    }
}