package ui.gui;

import javax.swing.*;

public class UsersGui {
    private JButton addButton;
    private JButton removeButton;
    private JPanel panel;

    public UsersGui(){
        JFrame frame = new JFrame();

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
