package gui.controllers.popups;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title , String message){

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Image icon = new Image(classloader.getResourceAsStream("Images/warning.png"));


        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setResizable(false);
        window.setMinWidth(300);
        window.getIcons().add(icon);
        Label label = new Label();
        label.setText(message);

        //create one buttons
        Button yesButton  = new Button("OK");

        yesButton.setOnAction(e -> {
            window.close();
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yesButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }

}
