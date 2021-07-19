
import controllers.AuthController;
import gui.controllers.popups.ConfirmBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import models.NetworkData;
import util.ConfigLoader;

import java.io.*;


public class Client extends Application {

    public static void main(String[] args) {
        try {
            new NetworkData();
            launch(args);
        } catch (IOException e) {
            System.out.println("connection to server failed");
            System.exit(0);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(ConfigLoader.readProperty("loginFXMLAddress")));
        primaryStage.setTitle("Burial Fields");
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            boolean answer = ConfirmBox.display("Exit confirmation" , "Are you sure to Exit?");
            if (answer){
                primaryStage.close();
                AuthController.logOut();
                try {
                    NetworkData.dataOutputStream.writeUTF("null close");
                    NetworkData.dataOutputStream.flush();
                    NetworkData.dataOutputStream.close();
                    NetworkData.dataInputStream.close();
                    NetworkData.socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
        primaryStage.setResizable(Boolean.parseBoolean(ConfigLoader.readProperty("appWindowResizable")));
        Image icon = new Image(String.valueOf(getClass().getResource(ConfigLoader.readProperty("appIconAddress"))));
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}