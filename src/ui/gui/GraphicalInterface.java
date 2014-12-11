package ui.gui;

import main.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicalInterface{

    Application app;
    private JPanel panel;

    private JButton reservationsButton;
    private JButton usersButton;
    private JButton roomsButton;
    private JButton hotelButton;
    private JComboBox comboBox1;


    public GraphicalInterface(Application app){

        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        this.app = app;

        createListeners();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public void createListeners(){
        usersButton.addActionListener(e -> {
            UsersGui users = new UsersGui(app);
        });
        reservationsButton.addActionListener(e -> {
            ReservationsGui reservations = new ReservationsGui();
        });
        roomsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomsGui rooms = new RoomsGui();
            }
        });
        hotelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HotelGui hotel = new HotelGui();
            }
        });
    }

    public void switchVisibility(boolean b){
        panel.setVisible(b ? true : false);
    }

    public Application getApp() {
        return app;
    }


}
