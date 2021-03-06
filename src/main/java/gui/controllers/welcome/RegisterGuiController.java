package gui.controllers.welcome;

import controllers.RegisterController;
import gui.controllers.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.ConfigLoader;

public class RegisterGuiController {

    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label rePasswordError;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField rePasswordTextField;
    @FXML
    private Label registerSuccessful;


    public void registerButtonClicked(ActionEvent actionEvent) {

        String username = usernameTextField.getText();
        if(username.isEmpty()){
            usernameError.setText("you must enter your username");
            return;
        }
        if(username.contains(" ")){
            usernameError.setText("username can't contain space");
            return;
        }
        else{
            usernameError.setText("");
        }
        String password = passwordTextField.getText();
        if(password.isEmpty()){
            passwordError.setText("you must enter your password");
            return;
        }
        if(password.contains(" ")){
            passwordError.setText("password can't contain space");
            return;
        }
        passwordError.setText("");
        String rePassword = rePasswordTextField.getText();
        if(rePassword.isEmpty()){
            rePasswordError.setText("you must re-enter your password");
            return;
        }
        else{
            if(!password.equals(rePassword)){
                rePasswordError.setText("passwords don't match");
                return;
            }
            rePasswordError.setText("");
        }

        if (!RegisterController.requestToMakeNewUser(username,password)){
            usernameError.setText("username already exists");
            return;
        }
        else {
            registerSuccessful.setText("register successful! go back to login");
        }
    }


    public void backButtonClicked(ActionEvent actionEvent) {
        SceneChanger.getInstance().changeScene(ConfigLoader.readProperty("loginMenuAdd"),actionEvent);
    }


}
