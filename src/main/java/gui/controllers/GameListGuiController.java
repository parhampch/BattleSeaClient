package gui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import models.ClientInfo;
import models.NetworkData;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameListGuiController implements Initializable {

    @FXML
    private ScrollPane listPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String result = NetworkData.requestServer(ClientInfo.getToken() + " ongoingGames");
            ArrayList<String> ongoingGames = process(result);
            VBox layout = new VBox(10);
            layout.setPrefSize(350, 550);
            for (String ongoingGame : ongoingGames) {
                //ongoingGame: gameID 1moves 1healthy 1bombs 2moves 2healthy 2bombs
                String[] details = ongoingGame.split(" ");
                String gameID = details[0];
                Label label = new Label();
                label.setTextFill(Color.BLUEVIOLET);
                label.setWrapText(true);
                label.setFont(new Font("Arial", 15));
                label.setText("Player1 Moves: " + details[1] + " Healthy Ships: " + details[2] + " Bombs Hit: " + details[3]
                + "\nPlayer2 Moves: " + details[4] + " Healthy Ships: " + details[5] + " Bombs Hit: " + details[6]);
                Button button = new Button("Watch");
                button.setId(gameID);
                button.setOnAction(event -> {
                    ((Stage)listPane.getScene().getWindow()).close();
                    watchGame(button.getId());});
                label.setGraphic(button);
                layout.getChildren().add(label);
            }
            listPane.setContent(layout);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void watchGame(String id) {
        try {
            NetworkData.requestServer(ClientInfo.getToken() + " watchGames " + id);
            new SceneUpdater(new Stage(),"FXMLs/WatchGame.fxml","Watching Game").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> process(String result) {
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> games = new Gson().fromJson(result, type);
        return games;
    }


}
