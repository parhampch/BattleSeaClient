package gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.ClientInfo;
import models.NetworkData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class StandbyMapGuiController implements Initializable {
    @FXML
    private Label timerLabel;
    @FXML
    private GridPane sea;

    private static Stage stage;
    private GameTimer gameTimer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawMap();
        gameTimer = new GameTimer(10,timerLabel , this);
        gameTimer.countDown();
    }

    public void updateTimer(int newTime){
        timerLabel.setText(String.valueOf(newTime));
    }
    public void drawMap(){
//        try {
//            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " nextMap");
//            NetworkData.dataOutputStream.flush();
//            String map = NetworkData.dataInputStream.readUTF();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for (int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 10 ; j++){
                MapButton btn = new MapButton(i,j);
                btn.setManner(MapButton.COLOR.VIOLET);
                sea.add(btn,j,i);
            }
        }

    }

    public void changeMap(ActionEvent actionEvent) {
        gameTimer.getTask();
        //drawMap();
    }

    public void startGame() {
        //todo: tell server the gamer is ready
        timerLabel.setText("Time's up");
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
