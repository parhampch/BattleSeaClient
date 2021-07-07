package gui.controllers;

import controllers.AuthController;
import gui.controllers.popups.AlertBox;
import gui.controllers.popups.ConfirmBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
            try {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLs/Welcome/Login.fxml"));
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }

        }
    }


    public void newGameButtonClicked(ActionEvent actionEvent) {
    }

    public void watchGameButtonClicked(ActionEvent actionEvent) {
    }

    public void scoreBoardButtonClicked(ActionEvent actionEvent) {
    }
}
