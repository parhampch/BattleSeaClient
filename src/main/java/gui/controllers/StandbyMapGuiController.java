package gui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.MapHandler;
import gui.controllers.popups.AlertBox;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

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
    private int allowedTimes = 4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawMap();
        PauseTransition timer = new PauseTransition(Duration.seconds(30));
        timer.setOnFinished(
                e -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(stage);
                    alert.setHeaderText("You took too long!");
                    alert.setHeight(200);
                    alert.setContentText("press OK and wait until you get to the game \nDO NOT DO ANYTHING ELSE");
                    alert.setOnHidden(we -> startGame());
                    alert.show();
                    changeMapBtn.setDisable(true);

                });
        startGameBtn.setOnAction(event -> {
            event.consume();
            timer.stop();
            startGame();
        });
        changeMapBtn.setOnAction(
                e -> {
                    e.consume();
                    if (allowedTimes > 0) {
//                        timer.jumpTo(timer.getCurrentTime().subtract(Duration.seconds(10)));   timer will never go above duration.
                        timer.pause();
                        Duration currentTime = timer.getCurrentTime();
                        Duration duration = timer.getDuration();
                        timer.setDuration(duration.subtract(currentTime).add(Duration.seconds(10)));
                        timer.playFromStart();
                        drawMap();
                    } else {
                        AlertBox.display("no more", "you can't change map anymore");
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


    public void drawMap() {
        allowedTimes--;
        try {
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " nextMap");
            NetworkData.dataOutputStream.flush();
            String map = NetworkData.dataInputStream.readUTF();
            Type type = new TypeToken<int[][]>() {
            }.getType();
            int[][] mapMatrix = new Gson().fromJson(map, type);
            MapHandler.drawMyMap(sea, mapMatrix);
            GameBoardGuiController.setMyMap(mapMatrix);
            sea.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        try {
            changeMapBtn.setDisable(true);
            String result = NetworkData.requestServer(ClientInfo.getToken() + " startGame");
            if (result.equals("0")) {
                AlertBox.display("Wait", "you competitor is not ready yet\n you'll automatically go to game\n DO NOT DO ANYTHING ELSE");
                result = NetworkData.dataInputStream.readUTF();
            }
            ClientInfo.setCompetitorUsername(result.split(" ")[1]);
            ClientInfo.setTurn(result.split(" ")[2].equals("T"));
            GameBoardGuiController.setStage(stage);
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLs/GameBoard.fxml"));
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
        StandbyMapGuiController.stage = stage;
    }

}
