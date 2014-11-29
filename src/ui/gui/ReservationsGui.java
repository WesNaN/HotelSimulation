package ui.gui;

import javax.swing.*;


public class ReservationsGui {

    private JButton checkButton;
    private JButton createButton;
    private JButton removeButton;
    private JButton changeButton;
    private JPanel panel;

    public ReservationsGui(){

        JFrame frame = new JFrame();

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);


    }
}
