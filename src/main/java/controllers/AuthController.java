package controllers;

import models.ClientInfo;
import models.NetworkData;

import java.io.IOException;

public class AuthController {


    public static boolean loginChecker(String username, String password) {
        String result = "0";
        try {
            result = NetworkData.requestServer("null login "+ username + " " + password);
            if (!result.equals("0")){
                String token = result.split(" ")[1];
                result = "1";
                ClientInfo.setToken(token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.equals("1");
    }

    public static void logOut() {
        if (ClientInfo.getToken() != null) {
            try {
                NetworkData.informServer(ClientInfo.getToken() + " logout");
                ClientInfo.setToken(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
