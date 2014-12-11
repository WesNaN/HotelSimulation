package ui.gui;

import main.Application;
import main.User;
import service.ConnectionError;
import service.DataService;

import javax.swing.*;
import java.util.ArrayList;

public class UsersGui {
    Application app;
    DataService service;
    private JButton addButton;
    private JButton removeButton;
    private JPanel panel;
    private JButton showButton;
    private JFrame frame = new JFrame();

    public UsersGui(Application app) {
        this.app = app;
        service = app.getDataService();


        createListeners();

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }


    public void createListeners(){

        addButton.addActionListener(e -> {
            User user = null;

            String name = JOptionPane.showInputDialog("Enter name");
            //long uniqueID = Long.parseLong(JOptionPane.showInputDialog("Enter unique ID"));

            boolean success = false;

            try {
                user = new User(name, service);
            } catch (ConnectionError connectionError) {
                connectionError.printStackTrace();
            }

            try {
                if(!service.userExists(user)){
                        try {

                            service.addUser(user);
                            success = true;
                        } catch (ConnectionError connectionError) {
                            JOptionPane.showMessageDialog(frame, "Error occurred while adding user!");
                            connectionError.printStackTrace();
                        }
                    } else {
                        success = false;
                        JOptionPane.showMessageDialog(frame, "Users already exists!");
                    }
            } catch (ConnectionError connectionError) {
                connectionError.printStackTrace();
            }

            if(success){
                JOptionPane.showMessageDialog(frame, "Adding user successful");
            }
        });

        showButton.addActionListener(e -> {
            ArrayList<User> users = null;
            try {
                users = (ArrayList<User>) service.getUsers();
            } catch (ConnectionError connectionError) {
                connectionError.printStackTrace();
            }



            StringBuilder sb = new StringBuilder();
            for(User u : users){
                sb.append(u.toString() + "\n");
            }

            JOptionPane.showMessageDialog(frame, sb);



            JFrame show = new JFrame();
            show.setContentPane(panel);
            show.setResizable(false);
            show.pack();
            show.setVisible(true);

        });
    }
}