package gui.controllers;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

public class CountDown extends Thread {
    private volatile int counter;
    private volatile Label label;
    private volatile boolean timeOut;
    private StandbyMapGuiController controller;

    public CountDown(int counter, Label label, StandbyMapGuiController controller) {
        this.counter = counter;
        this.label = label;
        this.timeOut = false;
        this.controller = controller;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            if (counter > 0) {
                try {
                    label.setText(String.valueOf(counter));
                    counter--;
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                timeOut = true;
                controller.startGame();
            }
        });
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public boolean isTimeOut() {
        return timeOut;
    }

    public void setTimeOut(boolean timeOut) {
        this.timeOut = timeOut;
    }

    public StandbyMapGuiController getController() {
        return controller;
    }

    public void setController(StandbyMapGuiController controller) {
        this.controller = controller;
    }

}
