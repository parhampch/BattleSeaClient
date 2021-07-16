package controllers;

import models.NetworkData;

import java.io.*;

public class RegisterController {

    public static boolean requestToMakeNewUser(String username, String password) {
        try {
            NetworkData.dataOutputStream.writeUTF("null register " + username + " " + password);
            NetworkData.dataOutputStream.flush();
            String result = NetworkData.dataInputStream.readUTF(); //result: 0 (username exists) , 1 (register successful)
            if (result.equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
