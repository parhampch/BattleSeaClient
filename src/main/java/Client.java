
import Client.gui.controllers.popups.ConfirmBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        //Parent root = FXMLLoader.load(getClass().getResource("FXMLs.Login.fxml"));
        primaryStage.setTitle("Burial Fields");
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            boolean answer = ConfirmBox.display("Exit confirmation" , "Are you sure to Exit?");
            if (answer){
                primaryStage.close();
            }
        });
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}