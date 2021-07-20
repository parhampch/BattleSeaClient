package gui.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.ClientInfo;
import models.NetworkData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WatchGameGuiController implements Initializable {

    private PauseTransition timer;
    private static String gameId;

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
        playerOneName.setText(getGameId());
    }


    public static String getGameId() {
        return gameId;
    }

    public static void setGameId(String gameId) {
        WatchGameGuiController.gameId = gameId;

    }
}
