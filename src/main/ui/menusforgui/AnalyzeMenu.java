package ui.menusforgui;

import model.Worklist;

import javax.swing.*;
import java.util.ArrayList;

public class AnalyzeMenu extends MenuTemplate {

    public AnalyzeMenu(Worklist wl) {
        super(wl, "Please select a function in your worklist to analyze: ", "Analyze Functions:",
                true);
    }

    @Override
    protected ArrayList<JButton> buttons() {
        return null;
    }

    @Override
    protected void modify(ArrayList<JButton> buttons) {

    }
}
