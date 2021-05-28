package sample;

import  javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Controller {
    @FXML
    private Label loginMessage;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameTx;
    @FXML
    private PasswordField passwordTx;
    @FXML
    private Hyperlink registerLink;

    public static Stage getRegStage() {
        return regStage;
    }

    @FXML
    private static Stage regStage;

    static TextField login; // getting username from login


    //adding action to login button so that it performs the method validateLogin()
    public void loginButtonOnAction(ActionEvent event){
        if(usernameTx.getText().isEmpty()==false && passwordTx.getText().isEmpty()==false){
            validateLogin();
        }else{
            loginMessage.setText("You left an empty field");
        }
    }

    //adding action to the register link so that it opens a register form
    public void registerLinkOnAction(ActionEvent event) throws IOException {
        createRegisterForm();
    }

    //a method to check whether the username and password are valid
    public void validateLogin(){
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        try{
            String sql = "SELECT count(1) FROM users WHERE username = '" + usernameTx.getText() +"' AND password = '"+passwordTx.getText() +"'";
            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    loginMessage.setText("Successfully logged in!");
                    login = usernameTx;
                    Main.getLoginStage().close();
                    openMain();
                }else{
                    loginMessage.setText("Try again!!");
                }
            }
        }catch (SQLException | IOException e){
            System.out.println(e.toString());
        }
    }

    //creating a stage to open register form on click
    public void createRegisterForm() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("registerForm.fxml"));
        Stage registerStage = new Stage();
        regStage = registerStage;
        registerStage.setTitle("Register");
        registerStage.setScene(new Scene(root, 600, 400));
        registerStage.show();
    }

    public void openMain() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainApp.fxml"));
        Stage mainStage = new Stage();
        mainStage.setTitle("ShopApp");
        mainStage.setScene(new Scene(root, 1083, 651));
        mainStage.show();
    }

}
