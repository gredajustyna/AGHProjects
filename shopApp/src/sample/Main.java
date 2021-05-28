package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Main extends Application {

    private static Stage loginStage;

    public static Stage getLoginStage() {
        return loginStage;
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        loginStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("loginForm.fxml"));
        primaryStage.setTitle("Login to buy apps");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
