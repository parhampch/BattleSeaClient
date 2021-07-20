package gui.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.ConfigLoader;

import static javafx.application.Application.launch;

public class Runner extends Application {

    private Updater updater;
    private String address;
    private String title;

    public Runner(Updater updater, String address, String title) {
        this.updater = updater;
        this.address = address;
        this.title = title;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(address));
                    primaryStage.setOnCloseRequest(e -> {
                        e.consume();
                        updater.setClosed(true);
                        primaryStage.close();
                    });
                    primaryStage.setResizable(Boolean.parseBoolean(ConfigLoader.readProperty("appWindowResizable")));
                    primaryStage.setTitle(title);
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Updater getUpdater() {
        return this.updater;
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
