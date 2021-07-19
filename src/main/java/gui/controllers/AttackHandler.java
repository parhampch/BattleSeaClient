package gui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.controllers.popups.AlertBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import models.ClientInfo;
import models.NetworkData;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class AttackHandler {

    private static ArrayList<MapButton> myButtons = new ArrayList<>();
    private static ArrayList<MapButton> competitorButtons = new ArrayList<>();

    public static void drawMyMap(GridPane gridPane, int[][] mapMatrix) {
        gridPane.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                MapButton btn = new MapButton(i, j);
                //btn.setDisable(true);
                btn.setOnAction(e -> {
                });
                switch (mapMatrix[i][j]) {
                    case 1 -> btn.setManner(MapButton.COLOR.GREEN);
                    case 2 -> btn.setManner(MapButton.COLOR.YELLOW);
                    case 3 -> btn.setManner(MapButton.COLOR.PINK);
                    case 4 -> btn.setManner(MapButton.COLOR.VIOLET);
                    default -> btn.setManner(MapButton.COLOR.BLUE);
                }
                gridPane.add(btn, j, i);
                myButtons.add(btn);
            }
        }
    }

    public static void initializeCompetitorMap(GridPane gridPane, GameBoardGuiController controller) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                MapButton btn = new MapButton(i, j);
                btn.setManner(MapButton.COLOR.BLUE);
                btn.setOnAction(e -> {
                    if (!btn.isDestroyed()) {
                        btn.setDestroyed();
                        handleAttack(btn, btn.getX(), btn.getY(), controller);
                    }
                });
                gridPane.add(btn, j, i);
                competitorButtons.add(btn);
            }
        }
    }

    private static void handleAttack(MapButton btn, int x, int y, GameBoardGuiController controller) {
        try {
            System.out.println("detected the attack");
            controller.getTimer().stop();
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " attack " + x + " " + y);
            NetworkData.dataOutputStream.flush();
            String result = NetworkData.dataInputStream.readUTF();
            System.out.println(result);
            //result[0]: 0: water  1: ship  2: ship complete  3: game finished
            // number initialX initialY myTurn (if 2 or 3) arraylist
            String[] tempResult = result.split(" ");
            int resultNum = Integer.parseInt(tempResult[0]);
            boolean isMyTurn = tempResult[3].equals("T");
            if (resultNum == 0) {
                btn.setManner(MapButton.COLOR.BLACK);
            } else if (resultNum == 1) {
                btn.setManner(MapButton.COLOR.RED);
            } else if (resultNum == 2) {
                btn.setManner(MapButton.COLOR.RED);
                Type type = new TypeToken<ArrayList<Integer>>() {
                }.getType();
                ArrayList<Integer> waterCells = new Gson().fromJson(tempResult[4], type);
                for (int i = 0; i < waterCells.size(); i += 2) {
                    int waterX = waterCells.get(i);
                    int waterY = waterCells.get(i + 1);
                    for (MapButton competitorButton : competitorButtons) {
                        if (competitorButton.getX() == waterX && competitorButton.getY() == waterY) {
                            competitorButton.setManner(MapButton.COLOR.BLACK);
                            competitorButton.setDestroyed();
                        }
                    }
                }
            }
            else if (resultNum == 3){
                btn.setManner(MapButton.COLOR.RED);
                Type type = new TypeToken<ArrayList<Integer>>() {
                }.getType();
                ArrayList<Integer> waterCells = new Gson().fromJson(tempResult[4], type);
                for (int i = 0; i < waterCells.size(); i += 2) {
                    int waterX = waterCells.get(i);
                    int waterY = waterCells.get(i + 1);
                    for (MapButton competitorButton : competitorButtons) {
                        if (competitorButton.getX() == waterX && competitorButton.getY() == waterY) {
                            competitorButton.setManner(MapButton.COLOR.BLACK);
                            competitorButton.setDestroyed();
                        }
                    }
                }
                controller.finishGame(true);
            }
            if (isMyTurn) {
                controller.beginMyTurn();
            } else {
                controller.stopMyTurn();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void enableAllButtons() {
        for (MapButton competitorButton : competitorButtons) {
            if (!competitorButton.isDestroyed()) {
                competitorButton.setDisable(false);
            }
        }
    }

    public static void disableAllButtons() {
        for (MapButton competitorButton : competitorButtons) {
            competitorButton.setDisable(true);
        }
    }

    public static void updateMyMap(String result, GameBoardGuiController controller) {
        // T/F 0: competitor timeout 1 : water  2: ship  3: complete ship 4: lose  x y  (case 3 or 4) arraylist
        System.out.println(result);
        String[] responds = result.split(" ");
        int resultnum = Integer.parseInt(responds[1]);
        if (resultnum == 1){
            int x = Integer.parseInt(responds[2]);
            int y = Integer.parseInt(responds[3]);
            for (MapButton myButton : myButtons) {
                if (myButton.getX() == x && myButton.getY() == y)
                    myButton.setManner(MapButton.COLOR.BLACK);
            }
        }
        else if( resultnum ==  2){
            int x = Integer.parseInt(responds[2]);
            int y = Integer.parseInt(responds[3]);
            for (MapButton myButton : myButtons) {
                if (myButton.getX() == x && myButton.getY() == y){
                    myButton.setManner(MapButton.COLOR.RED);
                    myButton.setDestroyed();
                }
            }
        }
        else if (resultnum == 3){
            int x = Integer.parseInt(responds[2]);
            int y = Integer.parseInt(responds[3]);
            for (MapButton myButton : myButtons) {
                if (myButton.getX() == x && myButton.getY() == y) {
                    myButton.setManner(MapButton.COLOR.RED);
                    myButton.setDestroyed();
                }
            }
            Type type = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            ArrayList<Integer> waterCells = new Gson().fromJson(responds[4], type);
            for (int i = 0; i < waterCells.size(); i += 2) {
                int waterX = waterCells.get(i);
                int waterY = waterCells.get(i + 1);
                for (MapButton myButton : myButtons) {
                    if (myButton.getX() == waterX && myButton.getY() == waterY) {
                        myButton.setManner(MapButton.COLOR.BLACK);
                        myButton.setDestroyed();
                    }
                }
            }
        }
        else if (resultnum == 4){
            int x = Integer.parseInt(responds[2]);
            int y = Integer.parseInt(responds[3]);
            for (MapButton myButton : myButtons) {
                if (myButton.getX() == x && myButton.getY() == y) {
                    myButton.setManner(MapButton.COLOR.RED);
                    myButton.setDestroyed();
                }
            }
            Type type = new TypeToken<ArrayList<Integer>>() {
            }.getType();
            ArrayList<Integer> waterCells = new Gson().fromJson(responds[4], type);
            for (int i = 0; i < waterCells.size(); i += 2) {
                int waterX = waterCells.get(i);
                int waterY = waterCells.get(i + 1);
                for (MapButton myButton : myButtons) {
                    if (myButton.getX() == waterX && myButton.getY() == waterY) {
                        myButton.setManner(MapButton.COLOR.BLACK);
                        myButton.setDestroyed();
                    }
                }
            }
            controller.finishGame(false);
            return;
        }
        if (responds[0].equals("T")){
            controller.beginMyTurn();
        }
        else {
            controller.stopMyTurn();
        }
    }
}
