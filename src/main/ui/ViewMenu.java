package ui;

import model.Worklist;

import javax.swing.*;
import java.awt.*;

public class ViewMenu {
    private JFrame frame;
    private JPanel panel;
    private JLabel title;
    private Worklist wl;

    public ViewMenu(Worklist wl) {
        this.wl = wl;
        frame = new JFrame();
        panel = new JPanel();
        title = new JLabel("Viewing your worklist");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel names = new JLabel(wl.names());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
        panel.setLayout(new GridLayout(0, 1));
        panel.add(title);
        panel.add(names);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("FunctionApp:");
        frame.pack();
        frame.setVisible(true);
    }
}
