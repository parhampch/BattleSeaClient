package gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.ClientInfo;
import models.NetworkData;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoGuiController implements Initializable {

    @FXML
    private Label usernameLabel;
    @FXML
    private Label winsLabel;
    @FXML
    private Label lossesLabel;
    @FXML
    private Label scoreLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            NetworkData.dataOutputStream.writeUTF(ClientInfo.getToken() + " getInfo");
            NetworkData.dataOutputStream.flush();
            String result = NetworkData.dataInputStream.readUTF();
            String[] info = result.split(" ");
            usernameLabel.setText(info[0]);
            winsLabel.setText(info[1]);
            lossesLabel.setText(info[2]);
            scoreLabel.setText(info[3]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void backButtonClicked(ActionEvent actionEvent) {
        Toolbar.getInstance().mainMenu(actionEvent);
    }

}
