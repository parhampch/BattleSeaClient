package gui.controllers;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class MapButton extends Button {
    public enum COLOR {
        BLUE , BLACK , VIOLET , RED
    }
    private int x;
    private int y;
    private boolean isDestroyed;
    private COLOR color;

    public MapButton(int x, int y) {
        this.x = x;
        this.y = y;
        this.isDestroyed = false;
        this.setPrefSize(31,31);

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed() {
        isDestroyed = true;
        this.setDisable(true);
    }

    public COLOR getManner() {
        return color;
    }

    public void setManner(COLOR newColor) {
        this.color = newColor;
        changeColor();
    }

    public void changeColor(){
        switch (this.color){
            case RED -> this.setStyle("-fx-background-color: #ff1e00;");
            case BLUE -> this.setStyle("-fx-background-color: #00ccff;");
            case BLACK -> this.setStyle("-fx-background-color: #000000;");
            case VIOLET -> this.setStyle("-fx-background-color: #7400b1;");
        }
    }
}
