package sample;
import basic_classes.Shop;
import basic_classes.User;
import basic_classes.Wallet;
import  javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class RegisterController {
    @FXML
    private Button closeButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField usernameRegTx;
    @FXML
    private TextField emailRegTx;
    @FXML
    private PasswordField passwordRegTx;
    @FXML
    private PasswordField passwordConfTx;
    @FXML
    private Label regMessage;
    @FXML
    private TextField phoneName;
    @FXML
    private CheckBox devCheckBox;

    Wallet wallet = new Wallet(0);


    public void closeButtonOnAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    public void registerButtonOnAction(ActionEvent event) throws SQLException {
        registerUser();
    }

    public void registerUser() throws SQLException {
        User user = new User(usernameRegTx, passwordRegTx, emailRegTx, wallet, devCheckBox);
        Shop.registerUser(user.getLogin(), user.getPassword(), user.getEmail(), user.isDev(), passwordConfTx, regMessage, phoneName);
    }



}
