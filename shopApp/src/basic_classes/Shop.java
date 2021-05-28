package basic_classes;

import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.Controller;
import sample.DatabaseHelper;
import sample.RegisterController;
import sample.UserController;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Shop {

    App app;
    Wallet wallet;
    TextField textField = new TextField();
    PasswordField passField = new PasswordField();
    CheckBox status;
    User user = new User(textField, passField, textField, wallet, status );
    Device device;
    public static RegisterController registerController;


   
    public App addApp(String description, double price, String name, Device device){
        app = new App(description, price, name, device);
        return app;
    }


    public static void registerUser(TextField usernamex, PasswordField passwordx, TextField emailx, CheckBox devCheckBox, PasswordField passwordconfx, Label mess, TextField phoneName) throws SQLException {
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> emailList = new ArrayList<>();
        int licznik = 0;

        try {
            String sql = "SELECT email FROM users";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {

                String email = rs.getString("email");
                emailList.add(email);
                licznik++;
            }
        } catch(SQLException e) {
            //System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch(SQLException e) {
                //System.out.println(e.toString());
            }
        }


        con = DatabaseHelper.connect();
        ps = null;
        Random generator = new Random();
        try{
            String sql = "INSERT INTO devices(name, ram, screen, yop) VALUES (?,?,?,?) ";
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneName.getText());
            ps.setString(2, String.valueOf((generator.nextInt(8) + 1)));
            ps.setString(3,String.valueOf((generator.nextInt(4) + 4 + Math.round(generator.nextDouble())/10 )));
            ps.setString(4,String.valueOf((generator.nextInt(10) + 2010)) );

            ps.execute();
            con.close();
            ps.close();
        }catch (SQLException e){
            //System.out.println(e.toString());
        }

        int id = 0;
        //fetching id from new device
        con = DatabaseHelper.connect();
        ps = null;
        rs = null;
        try {
            String sql = "Select id from devices where name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, phoneName.getText());
            rs = ps.executeQuery();

            id = rs.getInt(1);

        } catch(SQLException e) {
            //System.out.println(e.toString());
        } finally {
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
               // System.out.println(e.toString());
            }

        }


        if(passwordx.getText().equals(passwordconfx.getText())){
            if(passwordx.getText().isEmpty()==false && passwordconfx.getText().isEmpty()==false
                    && emailx.getText().isEmpty()==false && usernamex.getText().isEmpty()==false){
                if(emailx.getText().contains("@") && emailx.getText().contains(".")) {
                    int sem = 0;
                    for (int i = 0; i < licznik; i++) {
                        if (emailList.get(i).equals(emailx.getText())) {
                            sem = 1;
                        }
                    }
                    if (sem == 0) {
                        con = DatabaseHelper.connect();
                        ps = null;
                        String username = usernamex.getText();
                        String password = passwordx.getText();
                        String email = emailx.getText();
                        String regstatus = "client";  //client nie ma buttona, jesli nie jest zaznaczony dev to client sie wybierze
                        if (devCheckBox.isSelected()) {
                            regstatus = "dev";
                        }
                        try {
                            String sql = "INSERT INTO users(username, password, email, wallet, status, device, moneySpent, numberOfApps) VALUES (?,?,?,?,?,?,?,?) ";
                            ps = con.prepareStatement(sql);
                            ps.setString(1, username);
                            ps.setString(2, password);
                            ps.setString(3, email);
                            ps.setString(4, "0");
                            ps.setString(5, regstatus);
                            ps.setString(6, String.valueOf(id));
                            ps.setString(7, "0");
                            ps.setString(8, "0");
                            ps.execute();
                            mess.setText("basic_classes.User registered!!");
                            con.close();
                            ps.close();
                            Controller.getRegStage().close();
                        } catch (SQLException e) {
                            System.out.println(e.toString());
                        }

                    }
                    else mess.setText("Email already used");
                }else{
                    mess.setText("Incorrect email form!");
                }
            }else{
                mess.setText("You need to enter data to register!!!");
            }


        }else {
            mess.setText("Passwords don't match!!!");
        }


            }




}
