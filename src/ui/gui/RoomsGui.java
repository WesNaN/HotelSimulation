package ui.gui;

import javax.swing.*;

public class RoomsGui {

    private JButton checkButton;
    private JButton addButton;
    private JButton removeButton;
    private JPanel panel;

    public RoomsGui(){

        JFrame frame = new JFrame();
        frame.setContentPane(panel);


        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}
