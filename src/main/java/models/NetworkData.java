package models;

import util.ConfigLoader;

import java.io.*;
import java.net.Socket;

public class NetworkData {

    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    public NetworkData() throws IOException {
        try {
            socket = new Socket(ConfigLoader.readProperty("host"), Integer.parseInt(ConfigLoader.readProperty("port")));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }
        catch (IOException e){
            socket = new Socket("localhost", Integer.parseInt(ConfigLoader.readProperty("defaultPort")));
            dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }
    }

    public static String requestServer(String request) throws IOException {
        dataOutputStream.writeUTF(request);
        dataOutputStream.flush();
        return NetworkData.dataInputStream.readUTF();
    }

}
