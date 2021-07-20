package gui.controllers;

import javafx.stage.Stage;

public class Updater extends Thread{
    private Stage stage;
    private volatile boolean isClosed;
    private Runner runner;

    public Updater(Stage stage, String address , String title) {
        this.stage = stage;
        isClosed = false;
        runner = new Runner(this,address,title);
    }

    @Override
    public void run() {
        try {
            while (!this.isClosed){
                runner.start(stage);
                sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }
}
