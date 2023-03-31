package ui.menusforgui;

import model.Function;
import model.Worklist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Class to represent the template for the standard submenu in the FunctionAppGUI. Sets up a frame, panel, title, and
// buttons. Also has functionality to provide subclasses with the ability to select a function from the worklist wl
abstract class MenuTemplate {
    private JFrame frame;
    private JPanel panel;
    private Worklist wl;
    private ArrayList<JButton> buttons;
    private JLabel title;

    // Though this is a constructor, the effects have been explained as this constructor does more than just set up the
    // object
    // EFFECTS: Creates a sample menu, with the given worklist, panelTitle, and frameTitle. checkEmpty represents if the
    // submenu wants to check if the worklist is empty or not prior to proceeding. If checkEmpty is true and wl is empty
    // , then a special menu is created prompting the user to add functions before proceeding. Otherwise, constructs
    // the desired submenu
    public MenuTemplate(Worklist wl, String panelTitle, String frameTitle, Boolean checkEmpty) {
        if (checkEmpty && wl.isEmpty()) {
            handleEmpty();
        } else {
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
    }

    // EFFECTS: creates a menu telling the user that the worklist is empty and to try adding functions first
    private void handleEmpty() {
        title = new JLabel("Your worklist is empty, please close the window and try adding functions to use this "
                + "feature");
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

    public Worklist getWl() {
        return wl;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JLabel getTitle() {
        return title;
    }

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    public void setWl(Worklist wl) {
        this.wl = wl;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setPanel(JPanel panel) {
        this.panel = panel;
    }

    public void setTitle(JLabel title) {
        this.title = title;
    }

    public void setButtons(ArrayList<JButton> buttons) {
        this.buttons = buttons;
    }

    protected abstract ArrayList<JButton> buttons();

    // EFFECTS: a special implementation of buttons that creates a button for each function in the worklist. Often used
    // in several submenus.
    protected ArrayList<JButton> buttonsForSelectFn() {
        int n = wl.length() - 1;
        ArrayList<JButton> buttons = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            JButton button = new JButton(wl.getListFn().get(i).name("x"));
            button.addActionListener(new MenuTemplate.ButtonHandlerForSelectFn());
            buttons.add(button);
        }
        return buttons;
    }

    // Helper class to handle the buttons created by buttonsForSelectFn()
    private class ButtonHandlerForSelectFn implements ActionListener {
        @Override
        // Continues the submenu when the user selects a function button from buttonsForSelectFn()
        public void actionPerformed(ActionEvent e) {
            int n = buttons.size() - 1;
            for (int i = 0; i <= n; i++) {
                if (e.getSource().equals(buttons.get(i))) {
                    menuFnTemplate(wl.getListFn().get(i));
                }
            }
        }
    }

    protected abstract void menuFnTemplate(Function fn);
}