package gui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controllers.MapHandler;
import gui.controllers.popups.AlertBox;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.ClientInfo;
import models.NetworkData;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ResourceBundle;

public class WatchGameGuiController implements Initializable {

    private static String gameId;
    private static SceneRunner sceneRunner;

    @FXML
    private GridPane playerOneSea;
    @FXML
    private GridPane playerTwoSea;
    @FXML
    private Label playerOneName;
    @FXML
    private Label playerTwoName;


    private int[][] playerOneMatrix;
    private int[][] playerTwoMatrix;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NetworkData.requestServer(ClientInfo.getToken() + " watchGames " + gameId);
            String result = NetworkData.requestServer(ClientInfo.getToken() + " gameInfo");
            if (result.equals("0")){
                sceneRunner.stop();
            }
            //playerName int[][] 0: water hit -1: ruined ship 1: unknown
            else {
                process(result);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void process (String result ){
        String[] details = result.split(" ");
        playerOneName.setText(details[0]);
        playerTwoName.setText(details[2]);

        Type type = new TypeToken<int[][]>() {
        }.getType();

        playerOneMatrix = new Gson().fromJson(details[1], type);
        playerTwoMatrix = new Gson().fromJson(details[3], type);

        MapHandler.drawUnseenMap(playerOneSea , playerOneMatrix);
        MapHandler.drawUnseenMap(playerTwoSea , playerTwoMatrix);

    }

    public static String getGameId() {
        return gameId;
    }

    public static void setGameId(String gameId) {
        WatchGameGuiController.gameId = gameId;
    }

    public static SceneRunner getSceneRunner() {
        return sceneRunner;
    }

    public static void setSceneRunner(SceneRunner sceneRunner) {
        WatchGameGuiController.sceneRunner = sceneRunner;
    }
}
