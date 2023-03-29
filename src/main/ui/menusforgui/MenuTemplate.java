package ui.menusforgui;

import model.Worklist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

abstract class MenuTemplate {
    private JFrame frame;
    private JPanel panel;
    private Worklist wl;
    private ArrayList<JButton> buttons;
    private JLabel title;

    public MenuTemplate(Worklist wl, String panelTitle, String frameTitle, Boolean checkEmpty) {
        if (checkEmpty && wl.isEmpty()) {
            handleEmpty();
        }
        this.wl = wl;
        this.title = new JLabel(panelTitle);
        frame = new JFrame();
        panel = new JPanel();
        panel.add(title);
        buttons = buttons();
        for (JButton b : buttons) {
            panel.add(b);
        }
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        panel.setLayout(new GridLayout(0, 1));
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle(frameTitle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void handleEmpty() {
        title = new JLabel("Your worklist is empty, please close the window and try adding functions" +
                "to use this feature");
        panel = new JPanel();
        panel.add(title);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        frame = new JFrame();
        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Worklist is Empty - Add functions:");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public Worklist getWl() {
        return wl;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getTitle() {
        return title;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    protected abstract ArrayList<JButton> buttons();

    protected abstract void modify(ArrayList<JButton> buttons);

}
