package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage loginStage;

    public static Stage getloginStage() {
        return loginStage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        loginStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/loginForm.fxml"));
        primaryStage.setTitle("Login to buy apps");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
