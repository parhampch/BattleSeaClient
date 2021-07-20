package models;

import util.ConfigLoader;

import java.io.*;
import java.net.Socket;

public class NetworkData {

    public static Socket socket;
    public static DataInputStream dataInputStream;
    public static DataOutputStream dataOutputStream;

    public NetworkData() throws IOException {
        socket = new Socket(ConfigLoader.readProperty("host"), Integer.parseInt(ConfigLoader.readProperty("port")));
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
    }

    public static String requestServer(String request) throws IOException {
        dataOutputStream.writeUTF(request);
        dataOutputStream.flush();
        String result = NetworkData.dataInputStream.readUTF();
        return result;
    }

    public static void informServer (String info) throws IOException {
        NetworkData.dataOutputStream.writeUTF(info);
        NetworkData.dataOutputStream.flush();
    }
}
