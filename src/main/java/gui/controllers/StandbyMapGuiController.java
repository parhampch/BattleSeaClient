package gui.controllers;

import gui.controllers.popups.AlertBox;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import java.util.Timer;
import java.util.TimerTask;

public class StandbyMapGuiController implements Initializable {
    @FXML
    private volatile Label timerLabel;
    @FXML
    private GridPane sea;
    @FXML
    private Button changeMapBtn;
    @FXML
    private ProgressBar timerProgress;
    @FXML
    private Button startGameBtn;

    private static Stage stage;
    private GameTimer gameTimer;
    private CountDown countDown;
    private int allowedTimes = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawMap();
        PauseTransition timer = new PauseTransition(Duration.seconds(30));
        timer.setOnFinished(
                e -> {
                    startGame();
                });
        startGameBtn.setOnAction(event -> {
            event.consume();
            timer.stop();
            startGame();

        });
        changeMapBtn.setOnAction(
                e -> {e.consume();
                    if(allowedTimes > 0){
                        timer.pause();
                        Duration currentTime = timer.getCurrentTime();
                        Duration duration = timer.getDuration();
                        timer.setDuration(duration.subtract(currentTime).add(Duration.seconds(10)));
                        timer.playFromStart();
                        drawMap();
                    }
                    else {
                        AlertBox.display("no more","you can't change map anymore");
                    }
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
        timer.playFromStart();

    }

    public void updateTimer(int newTime){
        timerLabel.setText(String.valueOf(newTime));
    }

    public void drawMap(){
        allowedTimes--;
        try {
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " nextMap");
            NetworkData.dataOutputStream.flush();
            String map = NetworkData.dataInputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 10 ; j++){
                MapButton btn = new MapButton(i,j);
                btn.setManner(MapButton.COLOR.VIOLET);
                sea.add(btn,j,i);
            }
        }
    }

    public void startGame() {
        //todo: tell server the gamer is ready
        try {
            Parent root;
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLs/GameBoard.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        StandbyMapGuiController.stage = stage;
    }

}
