package models;

import util.ConfigLoader;

import java.io.*;
import java.net.Socket;

public class NetworkData {

    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    public NetworkData() {
        try {
            socket = new Socket(ConfigLoader.readProperty("host"),Integer.parseInt(ConfigLoader.readProperty("port")));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
