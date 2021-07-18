package view;

import gui.controllers.StandbyMapGuiController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameBoard extends Application {

    public GridPane getSea() {
        return sea;
    }

    public void setSea(GridPane sea) {
        this.sea = sea;
    }

    private GridPane sea;

    @Override
    public void start(Stage primaryStage) {
        PauseTransition timer = new PauseTransition(Duration.seconds(30));
        timer.setOnFinished(
                e -> {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.initOwner(primaryStage);
                    alert.setHeaderText(null);
                    alert.setContentText("You took too long! Will now exit application.");
                    alert.setOnHidden(we -> Platform.exit());
                    alert.show();
                });

        Button button = new Button("Add ten seconds");
        button.setOnAction(
                e -> {
                    e.consume();
                    timer.jumpTo(timer.getCurrentTime().subtract(Duration.seconds(10)));
                });

        Label timerLabel = new Label();
        timerLabel
                .textProperty()
                .bind(
                        Bindings.createStringBinding(
                                () -> {
                                    Duration currentTime = timer.getCurrentTime();
                                    Duration duration = timer.getDuration();
                                    double timeRemaining = duration.subtract(currentTime).toSeconds();
                                    return String.format("%04.1f seconds remaining", timeRemaining);
                                },
                                timer.currentTimeProperty(),
                                timer.durationProperty()));

        ProgressBar timerProgress = new ProgressBar();
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

        StackPane root = new StackPane(button, timerLabel, timerProgress , sea);
        root.setPadding(new Insets(10));
        StackPane.setAlignment(timerLabel, Pos.TOP_LEFT);
        StackPane.setAlignment(timerProgress, Pos.BOTTOM_CENTER);
        StackPane.setAlignment(sea , Pos.CENTER_LEFT);
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        timer.playFromStart();
    }
}
