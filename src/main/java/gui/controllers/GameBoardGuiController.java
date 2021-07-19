package gui.controllers;

import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
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

public class GameBoardGuiController implements Initializable {

    private static int[][] myMap;
    private PauseTransition timer;

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
        MapDrawer.initializeCompetitorMap(competitorSea, this);
        MapDrawer.drawMyMap(sea, myMap);

        timer = new PauseTransition(Duration.seconds(25));
        timer.setOnFinished(
                e -> {
                    changeTurnInTimeOut();
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
            ClientInfo.setTurn(false);
            turn.setText(ClientInfo.getCompetitorUsername());
            MapDrawer.disableAllButtons();
            timer.stop();
        }
    }

    public void beginMyTurn(){
        ClientInfo.setTurn(true);
        MapDrawer.enableAllButtons();
        turn.setText("you");
        timer.playFromStart();
    }

    public void stopMyTurn(){
        ClientInfo.setTurn(false);
        turn.setText(ClientInfo.getCompetitorUsername());
        MapDrawer.disableAllButtons();
        timer.stop();
        try {
            String result = NetworkData.dataInputStream.readUTF();
            // 0/1/2  0 : time out  1 : water  2 : ship  3 : complete ship [x,y] arrayList +turn
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTurnInTimeOut(){
        try {
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " nextTurn");
            NetworkData.dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.changeTurnToCompetitor();
    }

    public void changeTurnToCompetitor() {
        ClientInfo.setTurn(false);
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
}
