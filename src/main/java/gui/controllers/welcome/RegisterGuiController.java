package gui.controllers.welcome;

import controllers.RegisterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    @FXML
    private Button backButton;
    @FXML
    private Button registerButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

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
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("FXMLs/welcome/Login.fxml"));
            stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
