package ui;

import model.Worklist;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.menusforgui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// Class for the main menu of the GUI - has fields for the frame, panel, title of the panel, button in the GUI,
// the worklist, JSON reading and writing, and for an icon with an image,
public class FunctionAppGUI {
    public static final int SIZE = 10;

    private JFrame frame;
    private JLabel title;
    private Worklist wl = new Worklist();
    private JPanel panel;
    private ArrayList<JButton> buttons;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private ImageIcon image;
    private JLabel icon;

    public FunctionAppGUI() {
        jsonWriter = new JsonWriter("./data/worklist.json");
        jsonReader = new JsonReader("./data/worklist.json");
        frame = new JFrame();
        panel = new JPanel();
        buttons = buttons();
        title = new JLabel();
        String titleString = "Welcome to the FunctionApp! \n" + "For information on how to use the program, please "
                + "check the README file. \n" + "Please select what you would like to do:";
        title.setText("<html>" + titleString.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>");
        handlePanel(panel, buttons);
        handleFrame(frame, panel);
    }

    // MODIFIES: this
    // EFFECTS: creates and modifies buttons for use in the GUI
    private ArrayList<JButton> buttons() {
        JButton buttonView = new JButton("View your worklist");
        JButton buttonAdd = new JButton("Add a basic function to your worklist");
        JButton buttonMake = new JButton("Make more complicated functions");
        JButton buttonRemove = new JButton("Remove functions in your worklist");
        JButton buttonAnalyze = new JButton("Analyze (evaluate, integrate etc.) the functions in your worklist");
        JButton buttonSave = new JButton("Save your current worklist and settings");
        JButton buttonLoad = new JButton("Load the previously saved worklist and settings");
        JButton buttonSettings = new JButton("View or change settings");
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(buttonView, buttonAdd, buttonMake, buttonRemove,
                buttonAnalyze, buttonSave, buttonLoad, buttonSettings));
        modify(buttons);
        return buttons;
    }

    // REQUIRES: buttons to have 7 (or more entries)
    // MODIFIES: this
    // EFFECTS: Adds an action to each button
    private void modify(ArrayList<JButton> buttons) {
        buttons.get(0).addActionListener(new FunctionAppGUI.ButtonHandler());
        buttons.get(1).addActionListener(new FunctionAppGUI.ButtonHandler());
        buttons.get(2).addActionListener(new FunctionAppGUI.ButtonHandler());
        buttons.get(3).addActionListener(new FunctionAppGUI.ButtonHandler());
        buttons.get(4).addActionListener(new FunctionAppGUI.ButtonHandler());
        buttons.get(5).addActionListener(new FunctionAppGUI.ButtonHandler());
        buttons.get(6).addActionListener(new FunctionAppGUI.ButtonHandler());
        buttons.get(7).addActionListener(new FunctionAppGUI.ButtonHandler());
    }

    // MODIFIES: this
    // EFFECTS: Sets up the panel for the GUI
    private void handlePanel(JPanel p, ArrayList<JButton> buttons) {
        p.setBorder(BorderFactory.createEmptyBorder(-10, 5, 40, 5));
        p.setLayout(new GridLayout(0, 1));
        p.add(title, BorderLayout.CENTER);
        image = new ImageIcon("./data/Icon.png");
        image.setImage(image.getImage().getScaledInstance(430, 250, Image.SCALE_AREA_AVERAGING));
        icon = new JLabel(image);
        p.add(icon);
        for (JButton b : buttons) {
            p.add(b);
        }
    }

    // MODIFIES: this:
    // EFFECTS: sets up the frame for the GUI
    private void handleFrame(JFrame f, JPanel p) {
        f.add(p, BorderLayout.CENTER);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("FunctionApp:");
        f.pack();
        f.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: saves the current worklist and settings
    private void save() {
        JFrame frame1 = new JFrame();
        JPanel panel1 = new JPanel();
        JLabel title1;
        try {
            jsonWriter.open();
            jsonWriter.write(wl);
            jsonWriter.close();
            title1 = new JLabel("Success! You can close the window now.");
        } catch (FileNotFoundException var2) {
            title1 = new JLabel("There was an error loading the saved worklist.");
        }
        panel1.add(title1);
        panel1.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
        panel1.setLayout(new GridLayout(0,1));
        frame1.add(panel1, BorderLayout.CENTER);
        frame1.pack();
        frame1.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads the worklist stored in the JSONReader directory
    private void load() {
        JFrame frame1 = new JFrame();
        JPanel panel1 = new JPanel();
        JLabel title1;
        try {
            wl = jsonReader.read();
            title1 = new JLabel("Success! You can close the window now.");
        } catch (IOException var2) {
            title1 = new JLabel("There was an error saving the current worklist.");
        }
        panel1.add(title1);
        panel1.setBorder(BorderFactory.createEmptyBorder(15, 5, 15, 5));
        panel1.setLayout(new GridLayout(0,1));
        frame1.add(panel1, BorderLayout.CENTER);
        frame1.pack();
        frame1.setVisible(true);
    }

    // ButtonHandler Helper Class which assigns actions to each button
    private class ButtonHandler implements ActionListener {
        @Override
        // REQUIRES: this.buttons to have 7 (or more) JButtons
        // MODIFIES: this
        // EFFECTS: Assigns responses to each button in buttons
        public void actionPerformed(ActionEvent e) {
            if (e.getSource().equals(buttons.get(0))) {
                new ViewMenu(wl);
            } else if (e.getSource().equals(buttons.get(1))) {
                new AddMenu(wl);
            } else if (e.getSource().equals(buttons.get(2))) {
                new MakeMenu(wl);
            } else if (e.getSource().equals(buttons.get(3))) {
                new RemoveMenu(wl);
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
