package gui.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import models.ClientInfo;
import models.NetworkData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WatchGameGuiController implements Initializable {

    private PauseTransition timer;

    @FXML
    private GridPane playerOneSea;
    @FXML
    private GridPane playerTwoSea;
    @FXML
    private Label playerOneName;
    @FXML
    private Label playerTwoName;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = new PauseTransition(Duration.seconds(25));
        timer.setOnFinished(
                e -> {
                    timer.stop();
                    updateScene();
                });

    }

    private void updateScene() {
        try {
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " ");
            NetworkData.dataOutputStream.flush();
            String result = NetworkData.dataInputStream.readUTF();




            timer.playFromStart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
