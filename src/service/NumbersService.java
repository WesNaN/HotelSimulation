package service;

import main.User;

import java.util.Random;

public class NumbersService {

    public static String createNumberForUser(User user) {

        String number = "";

        Random r = new Random();
        int next = Math.abs(r.nextInt(Integer.MAX_VALUE / 10000));
        number = String.format(user.getName() + "@%d", next);


        return number;
    }
}