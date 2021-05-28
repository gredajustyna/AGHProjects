package sample;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.sql.*;


public class buyDeviceController {

    @FXML
    private Label phoneName;
    @FXML
    private Label phonePrice;
    @FXML
    private Label phoneDesc;
    @FXML
    private Label ramAmount;
    @FXML
    private Label screenSize;
    @FXML
    private Label yearOfP;
    @FXML
    private Label tempLabel;
    private int deviceId = deviceController.getDeviceId();
    @FXML
    private ImageView phoneImageView;
    @FXML
    private Button buyButton;

    String wallet;


    // dodaje urzadzenie do usera i odejmuje pieniazki
    public void addDevice()  throws IOException {
        tempLabel.setText(Controller.login.getText());
        System.out.println(UserController.getStatus(tempLabel));
        if (UserController.getStatus(tempLabel).equals("dev")) {
            Connection con = DatabaseHelper.connect();
            PreparedStatement ps = null;
            try {
                String sql = "UPDATE users set device = ? WHERE username = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, String.valueOf(deviceId));
                ps.setString(2, tempLabel.getText());
                ps.execute();
                System.out.println("Data has been updated");
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            tempLabel.setText("");
            Stage stage = (Stage) buyButton.getScene().getWindow();
            stage.close();
            openSucces();
        }
        else {
            wallet = Integer.toString(Integer.parseInt(UserController.getWallet(tempLabel)) - Integer.parseInt(phonePrice.getText()));
            String moneySpent = Integer.toString(Integer.parseInt(UserController.getMoneySpent(tempLabel)) + Integer.parseInt(phonePrice.getText()));

            if (Integer.parseInt(wallet) < 0) {
                Stage stage = (Stage) buyButton.getScene().getWindow();
                stage.close();
                openAlert();
            } else {
                Connection con = DatabaseHelper.connect();
                PreparedStatement ps = null;
                try {
                    String sql = "UPDATE users set wallet = ?, moneySpent = ? WHERE username = ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, wallet);
                    System.out.println(moneySpent);
                    ps.setString(2, moneySpent);
                    ps.setString(3, tempLabel.getText());
                    ps.execute();
                    System.out.println("Data has been updated");
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
                try {
                    String sql = "UPDATE users set device = ? WHERE username = ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, String.valueOf(deviceId));
                    ps.setString(2, tempLabel.getText());
                    ps.execute();
                    System.out.println("Data has been updated");
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }
                tempLabel.setText("");
                Stage stage = (Stage) buyButton.getScene().getWindow();
                stage.close();
                openSucces();

            }
        }

    }


    public void openAlert() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/noMoneyAlert.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("");
        userStage.setScene(new Scene(root, 400, 400));
        userStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( event -> userStage.close() );
        delay.play();
    }
    public void openSucces() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/appPurchasedAlert.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("");
        userStage.setScene(new Scene(root, 400, 400));
        userStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( event -> userStage.close() );
        delay.play();
    }

    public void buyDevice() throws SQLException, IOException {
        phoneDesc.setText("");
        Connection con = DatabaseHelper.connect();
        String sql = "SELECT id, name, ram, screen, yop, price, description, picture FROM devices WHERE id = '" + deviceId + "'";
        Statement statement = con.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);
            phoneName.setText(queryResult.getString("name"));
            phoneDesc.setText(queryResult.getString("description"));
            phonePrice.setText(String.valueOf(queryResult.getInt("price")));
            ramAmount.setText(String.valueOf(queryResult.getInt("ram")));
            screenSize.setText(String.valueOf(queryResult.getFloat("screen")));
            yearOfP.setText(String.valueOf(queryResult.getInt("yop")));

        InputStream is = queryResult.getBinaryStream("picture");
        OutputStream os = new FileOutputStream(new File("photo.png"));
        byte[]content = new byte[1024];
        int size = 0;
        while((size=is.read(content))!= -1)
        {
            os.write(content,0,size);
        }
        os.close();
        is.close();
        Image imagex = new Image("file:photo.png",250,250,true,true);
        phoneImageView.setImage(imagex);
        con.close();
    }

    public void showInfo() throws SQLException, IOException {
        Connection con = DatabaseHelper.connect();
        phoneName.setText(Controller.login.getText()); //tymczasowo przypisuje login
        String sql = "SELECT id, name, ram, screen, yop FROM devices WHERE id = '" + UserController.getId(phoneName) + "'";
        Statement statement = con.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);
        phoneName.setText(queryResult.getString("name"));
        ramAmount.setText(String.valueOf(queryResult.getInt("ram")));
        screenSize.setText(String.valueOf(queryResult.getFloat("screen")));
        yearOfP.setText(String.valueOf(queryResult.getInt("yop")));
        con.close();
    }

    public void openHyper() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/deviceBuyList.fxml"));
        Stage buyDevice = new Stage();
        buyDevice.setTitle("User menu");
        buyDevice.setScene(new Scene(root, 600, 400));
        buyDevice.show();

    }


}
