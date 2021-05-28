package sample;

import basic_classes.Wallet;
import javafx.animation.PauseTransition;
import  javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class UserController {
    @FXML
    private Button addFundsButton;
    @FXML
    private Button myAppsButton;
    @FXML
    private Button statsButton;
    @FXML
    private Label statusLabel;
    @FXML
    private Label deviceLabel;
    @FXML
    private Label currentMoneyLabel;
    @FXML
    private Label userLabel;
    @FXML
    private TextField moneyToAdd;
    @FXML
    private Button addFundsButton2;
    @FXML
    private Hyperlink hyperDevice;
    @FXML
    private Label noOfPurchApps;
    @FXML
    private Label moneySpent;
    @FXML
    private Label devUploadApps;
    @FXML
    private Label moneyWarning;


    Wallet wallet = new Wallet(0);

    public void setMoney(){
        currentMoneyLabel.setText(Controller.login.getText()); //uzywam currentMoneyLabel zeby tymczasowo przypisac tam login a nastepnie za pomoca loginu pieniadze
        currentMoneyLabel.setText(getWallet(currentMoneyLabel));

    }
    public void setLabels() {
        userLabel.setText(Controller.login.getText());
        statusLabel.setText(getStatus(userLabel));
        deviceLabel.setText(getDevice(Integer.parseInt(getId(userLabel))));
        hyperDevice.setText(getDevice(Integer.parseInt(getId(userLabel)))); // zrobilem z labelem na poczatku i potem podmienilem na hyperlink, dlatego taki kod
        deviceLabel.setText("");
        setMoney();

    }
    public void setLabelsDev() {
        userLabel.setText(Controller.login.getText());
        statusLabel.setText(getStatus(userLabel));
        deviceLabel.setText(getDevice(Integer.parseInt(getId(userLabel))));
        hyperDevice.setText(getDevice(Integer.parseInt(getId(userLabel)))); // zrobilem z labelem na poczatku i potem podmienilem na hyperlink, dlatego taki kod
        deviceLabel.setText("");
        currentMoneyLabel.setText("infinity");

    }
    public void openFundsForm() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/addFundsForm.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("User menu");
        userStage.setScene(new Scene(root, 600, 400));
        userStage.show();
    }

    public void setAddFundsButton(ActionEvent event) throws IOException {
        openFundsForm();
    }
    public void setAddFundsButton2(ActionEvent event) throws IOException {
        currentMoneyLabel.setText(Controller.login.getText()); // dzieje sie typowa dla mnie magia, jako ze mam tylko JEDEN label, to uzywam go do wszystkiego xdd wkladam login, potem za jego pomoca
        addFunds(getWallet(currentMoneyLabel),currentMoneyLabel, moneyToAdd);//robie tam swoje i na koniec wkladam tam pieniazki
        currentMoneyLabel.setText(getWallet(currentMoneyLabel));
        Stage stage = (Stage) addFundsButton2.getScene().getWindow();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( ev -> stage.close() );
        delay.play();
    }

    //zwraca obecna ilosc pieniedzy
    public static String getWallet(Label userLabel) {
        // lets read specific row on the database
        String wallet ="";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select wallet from users where username = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, userLabel.getText());
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            wallet = rs.getString(1);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connections
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }
        }
        return wallet;

    }
    //zwraca status
    public static String getStatus(Label userLabel) {
        // lets read specific row on the database
        String status ="";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select status from users where username = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, userLabel.getText());
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            status = rs.getString(1);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connections
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }
        }
        return status;

    }
    //zwraca id urzadzenia 1-6
    public static String getId(Label userLabel) {
        String id = " ";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select device from users where username = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, userLabel.getText());
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            id = rs.getString(1);
        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connections
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }
        }
        return id;

    }
    //zwraca urzadzenie na podstawie id
    public static String getDevice(int id) {
        // lets read specific row on the database
        String device = "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select name from devices where id = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(id));
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            device = rs.getString(1);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connections
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }
        }
        return device;

    }
    //zwraca wydane pieniadze
    public static String getMoneySpent(Label userLabel) {
        // lets read specific row on the database
        String moneySpent = "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select moneySpent from users where username = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, userLabel.getText());
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            moneySpent = rs.getString(1);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }
        }
        return moneySpent;

    }
    public static String getNumberOfApps(Label userLabel) {
        // lets read specific row on the database
        String numberOfApps = "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select numberOfApps from users where username = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, userLabel.getText());
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            numberOfApps = rs.getString(1);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }
        }
        return numberOfApps;

    }

    // dodaje pieniadze do portfela
    public void addFunds(String wallet, Label userLabel, TextField moneyToAdd) throws NumberFormatException{
        try{
            wallet = Integer.toString(Integer.parseInt(wallet) + Integer.parseInt(moneyToAdd.getText()));
            Connection con = DatabaseHelper.connect();
            PreparedStatement ps = null;
            if(Integer.parseInt(moneyToAdd.getText())>0){
                try {
                    String sql = "UPDATE users set wallet = ? WHERE username = ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, wallet);
                    ps.setString(2, userLabel.getText());
                    ps.execute();
                    System.out.println("Data has been updated");
                    con.close();
                } catch (SQLException e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }
            }else{
                moneyWarning.setVisible(true);
                moneyWarning.setText("Enter correct amount of money!");
            }
        }catch (NumberFormatException notANumber){
            moneyWarning.setText("Enter correct amount of money!");
        }
    }

    public void openDeviceInfo() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/deviceInfoForm.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("");
        userStage.setScene(new Scene(root, 600, 400));
        userStage.show();
    }

    public void addApp() throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/addAppForm.fxml"));
        Stage buyDevice = new Stage();
        buyDevice.setTitle("User menu");
        buyDevice.setScene(new Scene(root, 600, 400));
        buyDevice.show();

    }
    public void openMyApps() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/appListForm.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("");
        userStage.setScene(new Scene(root, 600, 400));
        userStage.show();
    }
    public void openMyStats() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/statsForm.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("");
        userStage.setScene(new Scene(root, 600, 400));
        userStage.show();

    }


    public void myAppsButtonOnAction() throws IOException {
        openMyApps();
    }

    public void statsButtonOnAction() throws IOException {
        openMyStats();
    }
    public void setStats (){
        devUploadApps.setText(Controller.login.getText());
        moneySpent.setText(getMoneySpent(devUploadApps));
        noOfPurchApps.setText(String.valueOf(MainController.myAppsCounter));

    }





}
