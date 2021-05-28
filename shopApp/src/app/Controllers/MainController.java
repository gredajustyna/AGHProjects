package sample;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private Button buyDeviceButton;
    @FXML
    private Button userButton;
    @FXML
    private Label currentMoneyLabel;
    @FXML
    private Label tempLabel;
    private List<Button> listOfApps = new ArrayList<>();
    @FXML
    private Pane vbox;
    //nizej sa zmienne do appListForm
    @FXML
    private Pane myAppList;
    @FXML
    private Label APPS;
    //nizej sa zmienne do buy app form
    @FXML
    private ImageView appPic;
    @FXML
    private Label appName;
    @FXML
    private Label appPrice;
    @FXML
    private Label appDescr;
    @FXML
    private Button buyButton;
    @FXML
    private Label ramAmount;
    @FXML
    private Label screenSize;
    @FXML
    private Label yearOfP;

    static String NAME = "sss";

    static int myAppsCounter = 0;



    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void buyDeviceButtonOnAction(ActionEvent event) throws IOException {
        buyDevice();
    }
    public void userButtonOnAction(ActionEvent event) throws IOException {
        tempLabel.setText(Controller.login.getText());
        String status = UserController.getStatus(tempLabel);
        if(status.equalsIgnoreCase("dev")) openUserStatsDev();
        else openUserStats();
    }

    public void openUserStats() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/userMenu.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("User Stats");
        userStage.setScene(new Scene(root, 600, 400));
        userStage.show();
    }
    public void openUserStatsDev() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/userMenuDev.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("User Stats");
        userStage.setScene(new Scene(root, 600, 400));
        userStage.show();
    }
    public void openPhoneWeakAlert() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/phoneWeakAlert.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("WARNING!");
        userStage.setScene(new Scene(root, 400, 400));
        userStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( event -> userStage.close() );
        delay.play();
    }
    public void openSuccessfulPurchase() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/appPurchasedAlert.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("WARNING!");
        userStage.setScene(new Scene(root, 400, 400));
        userStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( event -> userStage.close() );
        delay.play();
    }
    public void openNoMoneyAlert() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/noMoneyAlert.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("WARNING!");
        userStage.setScene(new Scene(root, 400, 400));
        userStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( event -> userStage.close() );
        delay.play();
    }
    public void openAppInAccountAlert() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/appInAccountAlert.fxml"));
        Stage userStage = new Stage();
        userStage.setTitle("WARNING!");
        userStage.setScene(new Scene(root, 400, 400));
        userStage.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished( event -> userStage.close() );
        delay.play();
    }

    public void buyDevice() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/deviceBuyList.fxml"));
        Stage buyDevice = new Stage();
        buyDevice.setTitle("Buy Device");
        buyDevice.setScene(new Scene(root, 600, 400));
        buyDevice.show();

    }
    public void buyApp() throws IOException, SQLException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLForms/buyAppForm.fxml"));
        Stage buyDevice = new Stage();
        buyDevice.setTitle("Buy App");
        buyDevice.setScene(new Scene(root, 600, 400));
        buyDevice.show();


    }

    private static String getWallet(Label userLabel) {
        // lets read specific row on the database
        String wallet = "";
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

        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connections
            try {
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


    public void show() throws SQLException, IOException {
        List<Button> buttonList = new ArrayList<>();
        List<Image> imageList = new ArrayList<>();
        int i = 0;
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT Name, Picture FROM apps";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                String name = rs.getString("Name");
                InputStream is = rs.getBinaryStream("Picture");
                OutputStream os = new FileOutputStream(new File("photo.png"));
                byte[]content = new byte[1024];
                int size = 0;
                while((size=is.read(content))!= -1)
                {
                    os.write(content,0,size);
                }
                os.close();
                is.close();
                Image image1 = new Image("file:photo.png",300,300,true,true);
                imageList.add(image1);
                buttonList.add(new Button(name));
                i++;
            }
        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch(SQLException e) {
                System.out.println(e.toString());
            }
        }
        vbox.getChildren().clear(); //remove all Buttons that are currently in the container
        for(int j = 0; j < i; j++){
            buttonList.get(j).setPrefSize(300,300);
            ImageView buttonImage = new ImageView(imageList.get(j));
            buttonList.get(j).setGraphic(buttonImage);
            buttonList.get(j).setStyle("-fx-font-size:0");
            buttonList.get(j).setStyle("-fx-text-overrun:clip");

            // int blobLength = (int) getPicture(buttonList.get(j).getText()).length();
            //  byte[] blobAsBytes = getPicture(buttonList.get(j).getText()).getBytes(1, blobLength);
        }
        vbox.getChildren().addAll(buttonList); //then add all your Buttons that you just created

        listOfApps = buttonList;
        currentMoneyLabel.setText(Controller.login.getText());
        String status = UserController.getStatus(currentMoneyLabel);
        if(status.equals("dev")){
            currentMoneyLabel.setText("infinity");
        } else {
            currentMoneyLabel.setText(getWallet(currentMoneyLabel));// XDD tu zeby nie tworzyc zmiennej dodatkowo, najpierw wpisuje to label login zeby potem za pomoca tego loginu znalezc
        }
    }
    public void showMyApps() throws SQLException, IOException {
        List<Image> imageList = new ArrayList<>();
        List<Label> labelList = new ArrayList<>();
        int i = 0;
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT Name, Picture FROM apps";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                    String name = rs.getString("Name");
                    InputStream is = rs.getBinaryStream("Picture");
                    OutputStream os = new FileOutputStream(new File("photo.png"));
                    byte[] content = new byte[1024];
                    int size = 0;
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }
                    os.close();
                    is.close();
                    Image image1 = new Image("file:photo.png", 300, 300, true, true);
                    imageList.add(image1);
                    labelList.add(new Label (name));
                    System.out.println(labelList);
                    i++;

            }
        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch(SQLException e) {
                System.out.println(e.toString());
            }
        }
        myAppList.getChildren().clear(); //remove all Buttons that are currently in the container
        for(int j = 0; j < i; j++){
            APPS.setText(Controller.login.getText());
            String app = "";
            con = DatabaseHelper.connect();
            try {
                String sql = "Select " + labelList.get(j).getText() + " from users where username = ? ";
                ps = con.prepareStatement(sql);
                ps.setString(1, APPS.getText());
                rs = ps.executeQuery();

                // we are reading one row, so no need to loop
                app = rs.getString(1);


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
            if(app != null) {
                System.out.println(app);
                ImageView view = new ImageView(imageList.get(j));
                view.setFitHeight(50);
                view.setFitWidth(50);
                labelList.get(j).setGraphic(view);
                myAppList.getChildren().add(labelList.get(j));
                APPS.setText("APPS");
                myAppsCounter++;
            }

        }
        //then add all your Buttons that you just created
        APPS.setText("APPS");
    }
    public void buttonSetting() throws SQLException, IOException {
        int i = listOfApps.size();
        for(int j = 0; j < i; j++) {
            String name = listOfApps.get(j).getText();
            listOfApps.get(j).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        buyApp();
                        tempLabel.setText(name);
                        setNAME(tempLabel.getText());

                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void setLab() throws SQLException, IOException {

        appName.setText(NAME);
        appDescr.setText(getDesc(NAME));
        appPrice.setText(getPrice(NAME));
        yearOfP.setText(getYop(NAME));
        ramAmount.setText(getRam(NAME));
        screenSize.setText(getScreen(NAME));


        Connection con = DatabaseHelper.connect();
        String sql = "SELECT Picture FROM apps WHERE Name = '" + NAME + "'";
        Statement statement = con.createStatement();
        ResultSet queryResult = statement.executeQuery(sql);
        InputStream is = queryResult.getBinaryStream("Picture");
        OutputStream os = new FileOutputStream(new File("photo.png"));
        byte[]content = new byte[1024];
        int size = 0;
        while((size=is.read(content))!= -1)
        {
            os.write(content,0,size);
        }
        os.close();
        is.close();
        con.close();
        Image imagex = new Image("file:photo.png",250,250,true,true);
        appPic.setImage(imagex);
    }

    public void buyButton() throws SQLException, IOException {

        // dodaje do tabeli miejsce na appke
        Connection con = DatabaseHelper.connect();
        Statement st = con.createStatement();
        try {

            String sql = "ALTER TABLE users add " + NAME + " TEXT ";
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

        //pobiera minimalne wymagania od appki
        String minRam = "", minScreen = "", minYop = "", Price ="";
        con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select minRam, minScreen, minYop, Price from apps where Name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, NAME);
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            minRam = rs.getString(1);
            minScreen = rs.getString(2);
            minYop = rs.getString(3);
            Price = rs.getString(4);
            System.out.println(Price);
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connections
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }

        }

            //pobiera podzespoły urzadzenia
            String Ram = "", Screen = "", Yop = "", wallet = "", appOwn = "";
            con = DatabaseHelper.connect();

            try {
                String sql = "Select ram, screen, yop from devices where id = ?";
                ps = con.prepareStatement(sql);
                appName.setText(Controller.login.getText());//ustawiam tymczasowy label
                wallet = UserController.getWallet(appName);//wpisuje wallet
                appOwn = getOwnApp(appName);
                ps.setString(1, UserController.getId(appName));
                appName.setText(NAME);
                rs = ps.executeQuery();

                // we are reading one row, so no need to loop
                Ram = rs.getString(1);
                Screen = rs.getString(2);
                Yop = rs.getString(3);
                System.out.println(Ram);
                System.out.println(Screen);
                System.out.println(Yop);
            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                // close connections
                try {
                    rs.close();
                    ps.close();
                    con.close();
                } catch (SQLException e) {
                    // TODO: handle exception
                    System.out.println(e.toString());
                }

            }
            appName.setText(Controller.login.getText());
            if(appOwn == null && Double.valueOf(Ram) >= Double.valueOf(minRam) && Double.valueOf(Screen) >= Double.valueOf(minScreen) && Double.valueOf(Yop) >= Double.valueOf(minYop)
            && (Integer.valueOf(wallet) >= Integer.valueOf(Price) || (UserController.getStatus(appName).equals("dev"))))
            {
                String moneySpent = UserController.getMoneySpent(appName);
                //dodaje appke do uzytkownika
                con = DatabaseHelper.connect();
                ps = null;
                try {
                    String sql = "UPDATE users set " + NAME + " = ?, wallet = ?, moneySpent = ? WHERE username = ? ";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, "1");
                    if(UserController.getStatus(appName).equals("dev")) {
                        ps.setString(2, "0");
                        ps.setString(3, "0");
                    }
                    else {
                        ps.setString(2, String.valueOf(Integer.valueOf(wallet) - Integer.valueOf(Price)));
                        ps.setString(3, String.valueOf(Integer.valueOf(moneySpent) + Integer.valueOf(Price)));

                    }
                    ps.setString(4, Controller.login.getText());
                    ps.execute();
                    System.out.println("Data has been updated");
                } catch (SQLException e) {
                    System.out.println(e.toString());
                    rs.close();
                    ps.close();
                    con.close();
                }
                appName.setText(NAME);
                openSuccessfulPurchase();
            }
            else if (appOwn != null) {
                System.out.println("masz juz appke ");
                appName.setText(NAME);
                openAppInAccountAlert();
            }
            else if(Integer.valueOf(wallet) < Integer.valueOf(Price) && (UserController.getStatus(appName).equals("dev"))==false){
                appName.setText(NAME);
                openNoMoneyAlert();
            }
            else {
                appName.setText(NAME);
                openPhoneWeakAlert();
            }
    }




     private static String getDesc(String name) {
        String Description = "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select Description from apps where Name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            Description = rs.getString(1);


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
        return Description;
    }

    private static String getRam(String name) {
        String minRam = "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select minRam from apps where Name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            minRam = rs.getString(1);


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
        return minRam;
    }

    private static String getYop(String name) {
        String minYop = "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select minYop from apps where Name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            minYop = rs.getString(1);


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
        return minYop;
    }

    private static String getScreen(String name) {
        String minScreen= "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select minScreen from apps where Name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            minScreen = rs.getString(1);


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
        return minScreen;
    }

    //zwraca czy user ma appke czy nie
     private static String getOwnApp(Label username) {
        String app = "";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select " + NAME + " from users where username = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, username.getText());
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            app = rs.getString(1);


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
        return app;
    }

    private static String getPrice(String name) {
        String Price ="";
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select Price from apps where Name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            // we are reading one row, so no need to loop
            Price = rs.getString(1);


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
        return Price;
    }

    private static InputStream getPicture(String name) {
        Connection con = DatabaseHelper.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream binaryStream = null;
        try {
            String sql = "Select Picture from apps where Name = ? ";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            rs = ps.executeQuery();

            Blob picture = rs.getBlob(1);
            binaryStream = picture.getBinaryStream(0, picture.length());



        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            // close connections
            try {
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }

        }
        return binaryStream;
    }









}
