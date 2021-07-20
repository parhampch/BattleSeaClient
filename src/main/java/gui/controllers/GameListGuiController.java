package gui.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.ClientInfo;
import models.NetworkData;
import util.ConfigLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameListGuiController implements Initializable {

    private PauseTransition timer;

    @FXML
    private ScrollPane listPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<String> scoreList = new ArrayList<>();
        try {
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " getScoreBoard");
            NetworkData.dataOutputStream.flush();
            String result = NetworkData.dataInputStream.readUTF();
            scoreList = process(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        VBox layout = new VBox(10);
        layout.setPrefSize(350, 550);
        for (int i = 0; i < 20; i++) {
            //s: username score on/off
            String gameID = "12345";
            Label label = new Label();
            label.setTextFill(Color.BLUEVIOLET);
            label.setWrapText(true);
            label.setText("Game: ");
            Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource(ConfigLoader.readProperty("scoreIconAdd"))));
            label.setFont(new Font("Arial", 15));
            label.setGraphic(new ImageView(icon));
            Button button = new Button("Watch");
            button.setId(gameID);
            button.setOnAction(event -> {
                System.out.println(button.getId());
                watchGame(button.getId());});
            label.setGraphic(button);
            layout.getChildren().add(label);
        }


        listPane.setContent(layout);


    }

    private void watchGame(String id) {
        WatchGameGuiController.setGameId(id);
        System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
        new Updater(new Stage(),"FXMLs/WatchGame.fxml","Watching Game").start();
    }

    private ArrayList<String> process(String result) {
        return null;
    }


}
