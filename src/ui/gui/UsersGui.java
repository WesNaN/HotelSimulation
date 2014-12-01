package ui.gui;

import main.Application;
import main.User;
import service.ConnectionError;
import service.DataService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UsersGui {
    Application app;
    private JButton addButton;
    private JButton removeButton;
    private JPanel panel;
    private JButton showButton;

    public UsersGui(Application app) {
        JFrame frame = new JFrame();
        this.app = app;

        createListeners();

        frame.setContentPane(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public void createListeners(){

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataService service = app.getDataService();
                User user = null;

                String name = JOptionPane.showInputDialog("Enter name");
                long uniqueID = Long.parseLong(JOptionPane.showInputDialog("Enter unique ID"));

                try {
                    user = new User(name, uniqueID, service);
                    service.addUser(user);
                } catch (ConnectionError connectionError) {
                    connectionError.printStackTrace();
                }
            }
        });
    }
}