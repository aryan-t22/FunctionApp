package ui;

import model.Worklist;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class StartMenu {
    private JFrame frame;
    private JLabel title;
    private Worklist wl = new Worklist();
    private JPanel panel;
    private ArrayList<JButton> buttons;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public StartMenu() {
        this.jsonWriter = new JsonWriter("./data/worklist.json");
        this.jsonReader = new JsonReader("./data/worklist.json");
        frame = new JFrame();
        panel = new JPanel();
        buttons = buttons();
        title = new JLabel("Welcome to the FunctionApp! Please select what you would like to do?");
        // title.setAlignmentX(Component.CENTER_ALIGNMENT);
        // title.setAlignmentY(Component.CENTER_ALIGNMENT);
        handlePanel(panel, buttons);
        handleFrame(frame, panel);
    }

    private ArrayList<JButton> buttons() {
        JButton buttonView = new JButton("View your worklist");
        JButton buttonAdd = new JButton("Add a basic function to your worklist");
        JButton buttonMake = new JButton("Make more complicated functions");
        JButton buttonEdit = new JButton("Edit and/or remove functions in your worklist");
        JButton buttonAnalyze = new JButton("analyze (evaluate, integrate etc.) the functions in your worklist");
        JButton buttonSave = new JButton("save your current worklist and settings");
        JButton buttonLoad = new JButton("load the previously saved worklist and settings");
        JButton buttonSettings = new JButton("view or change settings");
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(buttonView, buttonAdd, buttonMake, buttonEdit,
                buttonAnalyze, buttonSave, buttonLoad, buttonSettings));
        modify(buttons);
        return buttons;
    }

    private void modify(ArrayList<JButton> buttons) {
        buttons.get(0).setActionCommand("view");
        buttons.get(0).addActionListener(new StartMenu.ButtonHandler());

        buttons.get(1).setActionCommand("add");
        buttons.get(1).addActionListener(new StartMenu.ButtonHandler());

        buttons.get(2).setActionCommand("make");
        buttons.get(2).addActionListener(new StartMenu.ButtonHandler());

        buttons.get(3).setActionCommand("edit");
        buttons.get(3).addActionListener(new StartMenu.ButtonHandler());

        buttons.get(4).setActionCommand("analyze");
        buttons.get(4).addActionListener(new StartMenu.ButtonHandler());

        buttons.get(5).setActionCommand("save");
        buttons.get(5).addActionListener(new StartMenu.ButtonHandler());

        buttons.get(6).setActionCommand("load");
        buttons.get(6).addActionListener(new StartMenu.ButtonHandler());

        buttons.get(7).setActionCommand("settings");
        buttons.get(7).addActionListener(new StartMenu.ButtonHandler());
    }

    private void handlePanel(JPanel p, ArrayList<JButton> buttons) {
        p.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        p.setLayout(new GridLayout(0, 1));
        p.add(title);
        for (JButton b : buttons) {
            p.add(b);
        }
    }

    private void handleFrame(JFrame f, JPanel p) {
        f.add(p, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("FunctionApp:");
        f.pack();
        f.setVisible(true);
    }

    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(wl);
            jsonWriter.close();
        } catch (FileNotFoundException var2) {
            // tell user there was an error
        }
    }

    private void load() {
        try {
            wl = jsonReader.read();
        } catch (IOException var2) {
            // tell user there was an error
        }
    }

    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(buttons.get(0))) {
                new ViewMenu(wl);
            } else if (e.getSource().equals(buttons.get(1))) {
                new AddMenu(wl);
            } else if (e.getSource().equals(buttons.get(2))) {
                new MakeMenu(wl);
            } else if (e.getSource().equals(buttons.get(3))) {
                new EditMenu(wl);
            } else if (e.getSource().equals(buttons.get(4))) {
                new AnalyzeMenu(wl);
            } else if (e.getSource().equals(buttons.get(5))) {
                save();
            } else if (e.getSource().equals(buttons.get(6))) {
                load();
            } else if (e.getSource().equals(buttons.get(7))) {
                new SettingsMenu(wl);
            }
        }
    }
}
