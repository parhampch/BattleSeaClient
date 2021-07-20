package controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.controllers.GameBoardGuiController;
import javafx.scene.layout.GridPane;
import models.ClientInfo;
import models.MapButton;
import models.NetworkData;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MapHandler {

    private static ArrayList<MapButton> myButtons = new ArrayList<>();
    private static ArrayList<MapButton> competitorButtons = new ArrayList<>();
    private static ArrayList<MapButton> playerOneButtons = new ArrayList<>();
    private static ArrayList<MapButton> playerTwoButtons = new ArrayList<>();

    public static void drawMyMap(GridPane gridPane, int[][] mapMatrix) {
        gridPane.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                MapButton btn = new MapButton(i, j);
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
            controller.getTimer().stop();
            String result = NetworkData.requestServer(ClientInfo.getToken() + " attack " + x + " " + y);
            //result[0]: 0: water  1: ship  2: ship complete  3: game finished
            // number initialX initialY myTurn (if 2 or 3) arraylist
            String[] attackRespond = result.split(" ");
            int resultNum = Integer.parseInt(attackRespond[0]);
            boolean isMyTurn = attackRespond[3].equals("T");
            if (resultNum == 0) {
                btn.setManner(MapButton.COLOR.BLACK);
            } else {
                btn.setManner(MapButton.COLOR.RED);
                if (resultNum == 2) {
                    destroySeveral(attackRespond, competitorButtons);
                } else if (resultNum == 3) {
                    destroySeveral(attackRespond, competitorButtons);
                    controller.finishGame(true);
                }
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
        String[] responds = result.split(" ");
        int resultNum = Integer.parseInt(responds[1]);
        if (resultNum == 1) {
            destroySingle(responds, MapButton.COLOR.BLACK , myButtons);
        } else if (resultNum == 2) {
            destroySingle(responds, MapButton.COLOR.RED , myButtons);
        } else if (resultNum == 3) {
            destroySingle(responds, MapButton.COLOR.RED , myButtons);
            destroySeveral(responds, myButtons);
        } else if (resultNum == 4) {
            destroySingle(responds, MapButton.COLOR.RED , myButtons);
            destroySeveral(responds, myButtons);
            controller.finishGame(false);
            return;
        }
        if (responds[0].equals("T")) {
            controller.beginMyTurn();
        } else {
            controller.stopMyTurn();
        }
    }

    public static void destroySingle(String[] responds, MapButton.COLOR color , ArrayList<MapButton> buttonArrayList) {
        int x = Integer.parseInt(responds[2]);
        int y = Integer.parseInt(responds[3]);
        for (MapButton button : buttonArrayList) {
            if (button.getX() == x && button.getY() == y) {
                button.setManner(color);
                button.setDestroyed();
            }
        }
    }

    public static void destroySeveral(String[] responds, ArrayList<MapButton> buttonArrayList) {
        Type type = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> waterCells = new Gson().fromJson(responds[4], type);
        for (int i = 0; i < waterCells.size(); i += 2) {
            int waterX = waterCells.get(i);
            int waterY = waterCells.get(i + 1);
            for (MapButton button : buttonArrayList) {
                if (button.getX() == waterX && button.getY() == waterY) {
                    button.setManner(MapButton.COLOR.BLACK);
                    button.setDestroyed();
                }
            }
        }
    }

    public static void drawUnseenMap(GridPane gridPane, int[][] matrix) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                MapButton btn = new MapButton(i, j);
                switch (matrix[i][j]){
                    case 0 -> btn.setManner(MapButton.COLOR.BLACK);
                    case -1 -> btn.setManner(MapButton.COLOR.RED);
                    default -> btn.setManner(MapButton.COLOR.BLUE);
                }
                btn.setOnAction(e -> {});
                gridPane.add(btn, j, i);
            }
        }
    }
}
