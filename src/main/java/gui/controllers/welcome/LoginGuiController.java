package gui.controllers.welcome;

import controllers.AuthController;
import gui.controllers.Toolbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import util.ConfigLoader;

public class LoginGuiController {
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorMessage;
    private Stage stage;
    private Scene scene;
    private Parent root;
    



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
                System.out.println(username + " logged in");
                Toolbar.getInstance().mainMenu(actionEvent);
            }
            else{
                errorMessage.setText("username or password invalid");
            }
        }
    }

    public void registerButtonClicked(ActionEvent actionEvent) {
        Toolbar.getInstance().changeScene(ConfigLoader.readProperty("registerMenuAdd"),actionEvent);
    }


}
