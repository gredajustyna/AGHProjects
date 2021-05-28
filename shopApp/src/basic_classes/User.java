package basic_classes;

import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;


public class User {

    App app;
    Wallet wallet;
    private TextField login;
    private PasswordField password;
    private TextField email;
    private CheckBox status;

    public CheckBox isDev() {
        return status;
    }

    public User(TextField login, PasswordField password, TextField email, Wallet wallet, CheckBox status) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.wallet = wallet;
        this.status = status;
    }

    public TextField getLogin() {
        return login;
    }

    public  PasswordField  getPassword() {
        return password;
    }

    public TextField getEmail() {
        return email;
    }
}
