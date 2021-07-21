package gui.controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.ConfigLoader;

public class SceneRunner extends Application {

    private SceneUpdater sceneUpdater;
    private String address;
    private String title;

    public SceneRunner(SceneUpdater sceneUpdater, String address, String title) {
        this.sceneUpdater = sceneUpdater;
        this.address = address;
        this.title = title;
        if (title.equals("Watching Game")){
            WatchGameGuiController.setSceneRunner(this);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {

                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(address));
                    primaryStage.setOnHiding(e -> {
                        e.consume();
                        sceneUpdater.setClosed(true);
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

    public SceneUpdater getUpdater() {
        return this.sceneUpdater;
    }

    public void setUpdater(SceneUpdater sceneUpdater) {
        this.sceneUpdater = sceneUpdater;
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
