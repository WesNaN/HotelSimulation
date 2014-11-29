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


    public GraphicalInterface(Application app){

        JFrame frame = new JFrame();
        frame.setContentPane(panel);

        createListeners();

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public void createListeners(){
        usersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsersGui users = new UsersGui();
            }
        });
        reservationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationsGui reservations = new ReservationsGui();
            }
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
