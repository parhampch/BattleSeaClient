package controllers;

import models.NetworkData;

import java.io.*;

public class RegisterController {

    public static boolean requestToMakeNewUser(String username, String password) {
        try {
            String result = NetworkData.requestServer("null register " + username + " " + password); //result: 0 (username exists) , 1 (register successful)
            if (result.equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
