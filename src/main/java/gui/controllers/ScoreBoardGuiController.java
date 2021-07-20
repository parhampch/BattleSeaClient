package gui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import models.ClientInfo;
import models.NetworkData;
import util.ConfigLoader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ScoreBoardGuiController implements Initializable {

    @FXML
    private ScrollPane listPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " getScoreBoard");
            NetworkData.dataOutputStream.flush();
            String result = NetworkData.dataInputStream.readUTF();
            ArrayList<String> scoreList = process(result);
            VBox layout = new VBox(10);
            layout.setPrefSize(350, 550);
            for (String s : scoreList) {
                //s: username score on/off
                Label label = new Label();
                label.setTextFill(Color.BLUEVIOLET);
                label.setWrapText(true);
                label.setText("Name: " + s.split(" ")[0] + "\nScore: " + s.split(" ")[1] + "\nStatus: " + s.split(" ")[2]);
                Image icon = new Image(String.valueOf(getClass().getClassLoader().getResource(ConfigLoader.readProperty("scoreIconAdd"))));
                label.setFont(new Font("Arial", 15));
                label.setGraphic(new ImageView(icon));
                layout.getChildren().add(label);
            }
            listPane.setContent(layout);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private ArrayList<String> process(String result) {
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        ArrayList<String> list = new Gson().fromJson(result, type);
        return list;
    }
}
