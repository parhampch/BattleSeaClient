package gui.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import models.ClientInfo;
import models.NetworkData;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MapDrawer {
    public static void drawMyMap(GridPane gridPane, int[][] mapMatrix){
        gridPane.getChildren().clear();
        for (int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 10 ; j++){
                MapButton btn = new MapButton(i,j);
                btn.setDisable(true);
                int cellColor = mapMatrix[i][j];
                if (cellColor == 1)
                    btn.setManner(MapButton.COLOR.GREEN);
                else if (cellColor == 2)
                    btn.setManner(MapButton.COLOR.YELLOW);
                else if (cellColor == 3)
                    btn.setManner(MapButton.COLOR.PINK);
                else if (cellColor == 4)
                    btn.setManner(MapButton.COLOR.VIOLET);
                else
                    btn.setManner(MapButton.COLOR.BLUE);
                gridPane.add(btn,j,i);
            }
        }
    }

    public static void initializeCompetitorMap(GridPane gridPane , GameBoardGuiController controller) {
        for (int i = 0 ; i < 10 ; i++){
            for (int j = 0 ; j < 10 ; j++){
                MapButton btn = new MapButton(i,j);
                btn.setManner(MapButton.COLOR.BLUE);
                btn.setOnAction(e -> handleAttack(btn ,btn.getX(),btn.getY() , controller , gridPane) );
                gridPane.add(btn,j,i);
            }
        }
    }

    private static void handleAttack(MapButton btn, int x, int y, GameBoardGuiController controller, GridPane gridPane) {
        try {
            controller.getTimer().stop();
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " attack " + x + " " + y );
            NetworkData.dataOutputStream.flush();
            String result = NetworkData.dataInputStream.readUTF();
            //result[0]: 0: water  1: ship  2: ship complete  3: game finished
            // number initialX initialY myTurn (if 2 or 3) arraylist
            String[] tempResult = result.split(" ");
            int resultNum = Integer.parseInt(tempResult[0]);
            int targetX = Integer.parseInt(tempResult[1]);
            int targetY = Integer.parseInt(tempResult[2]);
            boolean isMyTurn = tempResult[3].equals("T");

            btn.setDestroyed();
            if (resultNum == 0){
                btn.setManner(MapButton.COLOR.BLACK);
            }else if(resultNum == 1){
                btn.setManner(MapButton.COLOR.RED);
            }else if (resultNum ==  2){
                btn.setManner(MapButton.COLOR.RED);
                Type type = new TypeToken<ArrayList<Integer>>() {
                }.getType();
                ArrayList<Integer> waterCells = new Gson().fromJson(tempResult[4],type);
                for (int i = 0; i < waterCells.size(); i+=2) {
                    int waterX = waterCells.get(i);
                    int waterY  = waterCells.get(i+1);
                    for (Node child : gridPane.getChildren()) {
                        MapButton newBtn = (MapButton)child;
                        int X = newBtn.getX();
                        int Y = newBtn.getY();
                        if (X == waterX && Y == waterY){
                            newBtn.setManner(MapButton.COLOR.BLACK);
                            newBtn.setDestroyed();
                        }
                    }
                }
            }
            //todo: turn
            if (!isMyTurn){
                controller.changeTurnToCompetitor();
            }
            else{
                controller.getTimer().playFromStart();
            }
            //todo: resultNum == 3


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
