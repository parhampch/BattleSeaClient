package gui.controllers.welcome;

import controllers.AuthController;
import gui.controllers.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.ClientInfo;
import util.ConfigLoader;

public class LoginGuiController {
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label errorMessage;



    public void loginButtonClicked(ActionEvent actionEvent) {
        System.out.println("logging in....");
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        if(username.isEmpty())
            errorMessage.setText("Enter Your Username");
        else if(password.isEmpty())
            errorMessage.setText("Enter Your Password");
        else {
            boolean isLoginValid = AuthController.loginChecker(username,password);
            if (isLoginValid) {
                SceneChanger.getInstance().mainMenu(actionEvent);
            }
            else{
                errorMessage.setText("username or password invalid");
            }
        }
    }

    public void registerButtonClicked(ActionEvent actionEvent) {
        SceneChanger.getInstance().changeScene(ConfigLoader.readProperty("registerMenuAdd"),actionEvent);
    }


}
