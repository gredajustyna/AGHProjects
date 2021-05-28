package sample;

import basic_classes.Shop;
import basic_classes.User;
import basic_classes.Wallet;
import javafx.embed.swing.SwingFXUtils;
import  javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.sql.*;

public class addAppFormController {

    @FXML
    private ImageView appPic;
    @FXML
    private Button addButton;
    @FXML
    private Button selectPhoto;
    @FXML
    private TextArea appDescriptionTx;
    @FXML
    private TextField appNameTx;
    @FXML
    private TextField appPriceTx;
    @FXML
    private TextField yopTx;
    @FXML
    private TextField screenSizeTx;
    @FXML
    private TextField ramTx;
    @FXML
    private Label warningLabel;
    @FXML
    private Button defaultPhotoButton;

    static FileInputStream fis;
    static int length;

    public void addApp() throws SQLException {
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        int k=0;
            if(appDescriptionTx.getText().isEmpty()==false && appNameTx.getText().isEmpty()==false && ramTx.getText().isEmpty()==false && appPic.getImage().isError()==false
                    && appPriceTx.getText().isEmpty()==false && yopTx.getText().isEmpty()==false && screenSizeTx.getText().isEmpty()==false ){
                if(isNumericDouble(appPriceTx.getText()) && isNumericDouble(ramTx.getText()) && isNumericDouble(screenSizeTx.getText()) &&isNumericDouble(yopTx.getText())){
                    k=1;
                    try{
                        String sql = "INSERT INTO apps (Description, Name, Price, minRam, minScreen, minYop, Picture) VALUES (?,?,?,?,?,?,?) ";
                        ps = con.prepareStatement(sql);
                        ps.setString(1,appDescriptionTx.getText());
                        ps.setString(2, appNameTx.getText());
                        ps.setString(3, appPriceTx.getText());
                        ps.setString(4, ramTx.getText());
                        ps.setString(5, screenSizeTx.getText());
                        ps.setString(6, yopTx.getText());
                        ps.setBinaryStream(7, fis, length );
                        ps.execute();
                        Stage stage = (Stage) addButton.getScene().getWindow();
                        stage.close();
                    }catch (SQLException e){
                        System.out.println(e.toString());
                    }
                    ps.close();
                    con.close();
                }else{
                    warningLabel.setText("Incorrect data form!");
                }
            }else{
                warningLabel.setText("Enter app data!");
            }
        if(k == 1 ) {

            con = DatabaseHelper.connect();
            Statement st = con.createStatement();
            try {

                String sql = "ALTER TABLE users add " + appNameTx.getText() + " TEXT ";
                st.executeUpdate(sql);

            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                // close connections
                try {
                    con.close();
                } catch (SQLException e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }
            }
        }
    }
    public void selectPhotoButton() throws FileNotFoundException, NullPointerException {
        try {
            final FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(appPic.getScene().getWindow());
            length = Math.toIntExact(file.length());
            fis = new FileInputStream ( file );
            if (file != null) {
                Image image1 = new Image(file.toURI().toString());
                ImageView ip = new ImageView(image1);
                appPic.setImage(ip.getImage());
            }
        }catch (NullPointerException noFileSelected){
            File file = new File("Default.png");
            length = Math.toIntExact(file.length());
            fis = new FileInputStream ( file );
            Image image1 = new Image(file.toURI().toString());
            ImageView ip = new ImageView(image1);
            appPic.setImage(ip.getImage());
        }
    }
    public void backToDefaultButonOnAction(){
        File file = new File("Default.png");
        length = Math.toIntExact(file.length());
        try {
            fis = new FileInputStream ( file );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image1 = new Image(file.toURI().toString());
        ImageView ip = new ImageView(image1);
        appPic.setImage(ip.getImage());
    }

    public static boolean isNumericDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
