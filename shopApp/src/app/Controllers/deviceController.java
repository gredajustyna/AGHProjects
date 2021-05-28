package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.*;
import java.sql.SQLException;

public class deviceController {

    private static int deviceId;
    @FXML
    private Button phone1Button;
    @FXML
    private Button phone2Button;
    @FXML
    private Button phone3Button;
    @FXML
    private Button tab1Button;
    @FXML
    private Button tab2Button;
    @FXML
    private Button tab3Button;
    @FXML
    private ImageView phoneImageView = new ImageView();

/*
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
    private Label yearOfP;*/

    public static int getDeviceId() {
        return deviceId;
    }




    public void phone1ButtonOnAction(ActionEvent event) throws IOException, SQLException {
        deviceId=1;
        openDeviceForm();
    }
    public void phone2ButtonOnAction(ActionEvent event) throws IOException, SQLException {
        deviceId=2;
        openDeviceForm();
    }
    public void phone3ButtonOnAction(ActionEvent event) throws IOException, SQLException {
        deviceId=3;
        openDeviceForm();
    }
    public void tab1ButtonOnAction(ActionEvent event) throws IOException, SQLException {
        deviceId=4;
        openDeviceForm();
    }
    public void tab2ButtonOnAction(ActionEvent event) throws IOException, SQLException {
        deviceId=5;
        openDeviceForm();
    }
    public void tab3ButtonOnAction(ActionEvent event) throws IOException, SQLException {
        deviceId=6;
        openDeviceForm();
    }

    @FXML
    public void openDeviceForm() throws IOException, SQLException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLForms/buyDeviceForm.fxml"));
        Parent root = (Parent)loader.load();
        loader.setController(this);
        //loader.getController();

        Stage deviceStage = new Stage();
        deviceStage.setTitle("Device description");
        deviceStage.setScene(new Scene(root, 600, 400));
        deviceStage.show();
    }
}
