package gui.controllers;

import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.ClientInfo;
import models.NetworkData;
import util.ConfigLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardGuiController implements Initializable {

    private static int[][] myMap;
    private PauseTransition timer;
    private static Stage stage;

    @FXML
    private GridPane sea;
    @FXML
    private GridPane competitorSea;
    @FXML
    private ProgressBar timerProgress;
    @FXML
    private Label competitorName;
    @FXML
    private Label timerLabel;
    @FXML
    private Label turn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        competitorName.setText(ClientInfo.getCompetitorUsername());
        AttackHandler.initializeCompetitorMap(competitorSea, this);
        AttackHandler.drawMyMap(sea, myMap);

        timer = new PauseTransition(Duration.seconds(25));
        timer.setOnFinished(
                e -> {
                    tellServerTimeOut();
                    stopMyTurn();
                });
        timerLabel
                .textProperty()
                .bind(
                        Bindings.createStringBinding(
                                () -> {
                                    Duration currentTime = timer.getCurrentTime();
                                    Duration duration = timer.getDuration();
                                    double timeRemaining = duration.subtract(currentTime).toSeconds();
                                    return String.format("%02.0f", timeRemaining);
                                },
                                timer.currentTimeProperty(),
                                timer.durationProperty()));
        timerProgress.setMaxWidth(Double.MAX_VALUE);
        timerProgress
                .progressProperty()
                .bind(
                        Bindings.createDoubleBinding(
                                () -> {
                                    double currentTime = timer.getCurrentTime().toMillis();
                                    double duration = timer.getDuration().toMillis();
                                    return 1.0 - (currentTime / duration);
                                },
                                timer.currentTimeProperty(),
                                timer.durationProperty()));

        if (ClientInfo.isTurn()) {
            beginMyTurn();
        } else {
            stopMyTurn();
        }
    }

    public void beginMyTurn() {
        ClientInfo.setTurn(true);
        AttackHandler.enableAllButtons();
        turn.setText("you");
        timer.playFromStart();
    }

    public void stopMyTurn() {
        ClientInfo.setTurn(false);
        turn.setText(ClientInfo.getCompetitorUsername());
        AttackHandler.disableAllButtons();
        timer.stop();
        PauseTransition socketTimer = new PauseTransition(Duration.seconds(1));
        socketTimer.setOnFinished(
                e -> {
                    try {
                        String result = NetworkData.dataInputStream.readUTF();
                        AttackHandler.updateMyMap(result , this);
                        System.out.println("my map updated " + result);
                    } catch (IOException error) {
                        error.printStackTrace();
                    }
                });
        socketTimer.playFromStart();

    }

    public void tellServerTimeOut() {
        try {
            NetworkData.requestServer(ClientInfo.getToken() + " nextTurn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] getMyMap() {
        return myMap;
    }

    public static void setMyMap(int[][] myMap) {
        GameBoardGuiController.myMap = myMap;
    }

    public PauseTransition getTimer() {
        return timer;
    }

    public void setTimer(PauseTransition timer) {
        this.timer = timer;
    }

    public void finishGame(boolean b) {
        timer.stop();
        String winner = b ? "you" : ClientInfo.getCompetitorUsername();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(stage);
        alert.setHeaderText(null);
        alert.setHeight(200);
        alert.setContentText("GAME FINISHED \n" + winner + " won");
        alert.setOnHidden(we -> {
            System.out.println("player game over");
        });
        alert.show();
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(ConfigLoader.readProperty("mainMenuAdd")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        GameBoardGuiController.stage = stage;
    }
}
