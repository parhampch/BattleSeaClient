package controllers;

import models.ClientInfo;
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
                result = "1";
                ClientInfo.setToken(token);
                System.out.println(username+"'s token is: "+token);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.equals("1");
    }

    public static void logOut() {
        if (ClientInfo.getToken() != null) {
            try {
                NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " logout");
                NetworkData.dataOutputStream.flush();
                ClientInfo.setToken(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
