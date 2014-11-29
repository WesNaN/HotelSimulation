package ui.gui;


import javax.swing.*;

public class HotelGui {
    private JButton button1;
    private JButton button2;
    private JPanel panel;

    public HotelGui(){
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }
}
