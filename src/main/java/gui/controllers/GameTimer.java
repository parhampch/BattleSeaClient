package gui.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private TimerTask task;
    private int time;
    private Label label;

    public GameTimer(int time, Label label, StandbyMapGuiController controller) {
        this.time = time;
        this.label = label;
        timer = new Timer();
        task = new TimerTask() {
            public volatile int counter = time;
            boolean timeOut = false;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (counter > 0) {
                        label.setText(String.valueOf(counter));
                        counter--;
                    } else {
                        timeOut = true;
                        controller.startGame();
                        timer.cancel();
                    }
                });
            }
        };
    }

    public void countDown() {
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public TimerTask getTask() {
        return task;
    }
}
