package gui.controllers;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameListGuiController implements Initializable {

    private PauseTransition timer;

    @FXML
    private ScrollPane listPane;
    private VBox layout = new VBox(10);
    private ArrayList<Label> labels = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timer = new PauseTransition(Duration.seconds(3));
        timer.setOnFinished(
                e -> {
                    timer.stop();
                    updateScene();
                });
        layout.setAlignment(Pos.CENTER);
        for (int i = 0; i < 10; i++) {
            Label label = new Label();
            label.setText("game number " + String.valueOf(i));
            label.setPrefSize(200,200);
            layout.getChildren().add(label);
            labels.add(label);
        }
        listPane.setContent(layout);
    }

    private void updateScene() {
        for (int i = 0; i < 10; i++) {
            Label label = new Label();
            label.setText("FUCK YOU BITCH" + String.valueOf(i));
            label.setPrefSize(200,200);
            layout.getChildren().add(label);
            labels.add(label);
        }
        listPane.setContent(layout);
    }
}
