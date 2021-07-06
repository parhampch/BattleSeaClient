package controllers;

import models.ClientToken;
import util.ConfigLoader;

import java.io.*;
import java.net.Socket;

public class RegisterController {
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ClientToken clientToken;

    public RegisterController(){
        try {
            int port = Integer.parseInt(ConfigLoader.readProperty("port"));
            String host = ConfigLoader.readProperty("host");
            this.socket = new Socket(host,port);
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean requestToMakeNewUser(String username, String password) {
        try {
            dataOutputStream.writeUTF(clientToken.getToken()+ " register" + " " + username + " " + password);
            dataOutputStream.flush();
            String result = dataInputStream.readUTF(); //result: 0 (username exists) , 1 (register successful)
            if (result.equals("1")){
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
