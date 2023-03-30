package ui.menusforgui;

import model.Function;
import model.Worklist;

import javax.swing.*;
import java.util.ArrayList;

public class ViewMenu extends MenuTemplate  {

    public ViewMenu(Worklist wl) {
        super(wl, "Your Worklist: ", "Viewing your Worklist:", false);
        String ans = wl.names();
        JLabel names = new JLabel();
        names.setText("<html>" + ans.replaceAll("<","&lt;")
                .replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
        getPanel().add(names);
        getFrame().pack();
    }

    @Override
    protected ArrayList<JButton> buttons() {
        return new ArrayList<>();
    }

    @Override
    protected void menuFnTemplate(Function fn) {

    }
}