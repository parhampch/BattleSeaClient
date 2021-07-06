
import gui.controllers.popups.ConfirmBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.ConfigLoader;


public class Client extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLs/Welcome/Login.fxml"));
        primaryStage.setTitle("Burial Fields");
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            boolean answer = ConfirmBox.display("Exit confirmation" , "Are you sure to Exit?");
            if (answer){
                primaryStage.close();
            }
        });
//        primaryStage.setHeight(Integer.parseInt(ConfigLoader.readProperty("appWindowHeight")));
//        primaryStage.setWidth(Integer.parseInt(ConfigLoader.readProperty("appWindowWidth")));
        primaryStage.setResizable(Boolean.parseBoolean(ConfigLoader.readProperty("appWindowResizable")));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}