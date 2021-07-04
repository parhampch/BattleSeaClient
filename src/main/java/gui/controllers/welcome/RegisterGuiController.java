package gui.controllers.welcome;

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
    private Label fullNameError;
    @FXML
    private Label registerSuccessful;
    @FXML
    private Label usernameError;
    @FXML
    private Label passwordError;
    @FXML
    private Label rePasswordError;
    @FXML
    private Label emailError;
    @FXML
    private Label phoneNumberError;
    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private PasswordField rePasswordTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField bioTextField;
    @FXML
    private DatePicker birthdayTextField;
    @FXML
    private Button backButton;
    @FXML
    private Button registerButton;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void registerButtonClicked(ActionEvent actionEvent) {
        String fullName = fullNameTextField.getText();
        if (fullName.isEmpty()) {
            fullNameError.setText("you must enter your fullName");
            return;
        }
        fullNameError.setText("");
        String username = usernameTextField.getText();
        if(username.isEmpty()){
            usernameError.setText("you must enter your username");
            return;
        }
        else{
            if(true) {
                usernameError.setText("username already exists");
                return;
            }
            usernameError.setText("");
        }
        String password = passwordTextField.getText();
        if(password.isEmpty()){
            passwordError.setText("you must enter your password");
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
        String email = emailTextField.getText();
        if(email.isEmpty()){
            System.out.println("you must enter your email");
            return;
        }
        else if(!email.contains("@") || !email.contains(".")){
            emailError.setText("invalid email address");
            return;
        }
        else{
            if(false){
                emailError.setText("email already exists");
                return;
            }
            emailError.setText("");
        }
        String phoneNumber = phoneNumberTextField.getText();
        if(!phoneNumber.isEmpty()) {
            try {
                Integer.parseInt(phoneNumber);
                if (false){
                    phoneNumberError.setText("phone number already exists");
                    return;
                }
                phoneNumberError.setText("");
            } catch (NumberFormatException e) {
                phoneNumberError.setText("phone number should be only numbers");
                return;
            }
        }
        String bio = bioTextField.getText();
        String birthday = birthdayTextField.getValue() == null ? "" : birthdayTextField.getValue().toString();
        registerSuccessful.setText("register successful! go back to login");
    }


    public void backButtonClicked(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("Welcome/Login.fxml"));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
