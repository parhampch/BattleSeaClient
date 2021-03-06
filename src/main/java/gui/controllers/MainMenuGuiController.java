package gui.controllers;

import controllers.AuthController;
import gui.controllers.popups.AlertBox;
import gui.controllers.popups.ConfirmBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.ClientInfo;
import models.NetworkData;
import util.ConfigLoader;

import java.io.IOException;

public class MainMenuGuiController {
    @FXML
    private Button logoutButton;
    @FXML
    private Button newGameButton;
    @FXML
    private Button watchGameButton;
    @FXML
    private Button scoreBoardButton;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void logoutButtonClicked(ActionEvent actionEvent) {
        boolean answer = ConfirmBox.display("Log out confirmation", "Are you sure you want to Log out??");
        if (answer) {
            AuthController.logOut();
            SceneChanger.getInstance().changeScene(ConfigLoader.readProperty("loginMenuAdd"), actionEvent);
        }
    }


    public void newGameButtonClicked(ActionEvent actionEvent) {
        try {
            String result = NetworkData.requestServer(ClientInfo.getToken() + " newGame");
            if (result.equals("0")) {
                AlertBox.display("wait", "no online player available yet.\nwait until another player is found\n you'll automatically go to game\n do not do anything");
                result = NetworkData.dataInputStream.readUTF();
            }
            ClientInfo.setOnGoingGameId(Integer.parseInt(result));
            StandbyMapGuiController.setStage(stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
            SceneChanger.getInstance().changeScene(ConfigLoader.readProperty("standbyMapMenuAdd"), actionEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void watchGameButtonClicked(ActionEvent actionEvent) {
        new SceneUpdater(new Stage(), "FXMLs/GameList.fxml" , "List Of OnGoing Games").start();
    }

    public void scoreBoardButtonClicked(ActionEvent actionEvent) {
        new SceneUpdater(new Stage(), "FXMLs/ScoreBoard.fxml" , "Score Board").start();
    }

    public void myInfoButtonClicked(ActionEvent actionEvent) {
        SceneChanger.getInstance().changeScene("FXMLs/Info.fxml",actionEvent);
    }
}
