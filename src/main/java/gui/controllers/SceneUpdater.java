package gui.controllers;

import javafx.stage.Stage;

public class SceneUpdater extends Thread {
    private Stage stage;
    private volatile boolean isClosed;
    private SceneRunner sceneRunner;

    public SceneUpdater(Stage stage, String address, String title) {
        this.stage = stage;
        isClosed = false;
        sceneRunner = new SceneRunner(this, address, title);
    }

    @Override
    public void run() {
        try {
            while (!this.isClosed) {
                sceneRunner.start(stage);
                sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;

    }
}
