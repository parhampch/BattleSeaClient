package controllers;

import models.ClientToken;
import models.NetworkData;

import java.io.IOException;

public class AuthController {


    public static boolean loginChecker(String username, String password) {
        String result = "0";
        try {
            NetworkData.dataOutputStream.writeUTF("null login "+ username + " " + password);
            NetworkData.dataOutputStream.flush();
            result = NetworkData.dataInputStream.readUTF();
            //todo: remove the sout lines
            System.out.println("login result = "+ result);
            if (!result.equals("0")){
                String token = result.split(" ")[1];
                ClientToken.setToken(token);
                System.out.println(username+"'s token is: "+token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.equals("1");
    }

    public static void logOut() {
        if (ClientToken.getToken() != null) {
            try {
                NetworkData.dataOutputStream.writeUTF(ClientToken.getToken() + " logout");
                NetworkData.dataOutputStream.flush();
                ClientToken.setToken(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
