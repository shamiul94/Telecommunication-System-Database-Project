/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tele.communication.system.application;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author HEISENBERG
 */
public class User_UI extends Application {

    public static Stage stage;
    public static Stage stage2;
    public static Group root;
    public static Scene scene;
    public static String name;// user name
    public static String passcode;//nid
    public static String phoneno;//sim no
    public static String pass;
    public static String givenpass;
    String SIM_Type;
    String SIMtypeID;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.initModality(Modality.APPLICATION_MODAL);
        LoginScene();
    }

    public static void BackSceneOnly() {
        root = new Group();
        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); 
        scene = new Scene(root, 1800, 900);
        scene.setFill(Color.rgb(255, 255, 255));//rgb(red, green, blue)
        ImageView back = new ImageView("file:userback.jpg");
        root.getChildren().add(back);
    }

    public void go() throws Exception {
        stage2 = new Stage();
        start(stage2);
    }

    public static void StageShow() {
        stage.setTitle("USER UI");
        stage.setScene(scene);
        Image logo = new Image("file:comlogo.png");
        stage.getIcons().add(logo);
        stage.show();
    }

    private void SignUPScene() {

        int posX = 300, posY = 250;
        int textFieldPosX = posX + 320, textFieldPosY = posY - 20;
        int buttonPosX = textFieldPosX + 300, buttonPosY = 250, fonts = 20;

        BackSceneOnly();
        name = new String();
        passcode = new String();

        Text Name = new Text("USER NAME : ");
        Name.setX(posX);
        Name.setY(posY);
        Name.setFill(Color.rgb(0, 0, 0));
        Name.setFont(Font.font(fonts));

        Text nid = new Text("NID NO : ");
        nid.setX(posX);
        nid.setY(posY + 50);
        nid.setFill(Color.rgb(0, 0, 0));
        nid.setFont(Font.font(fonts));

        Text db = new Text("DATE OF BIRTH (MMM DD, YYYY) : ");
        db.setX(posX);
        db.setY(posY + 100);
        db.setFill(Color.rgb(0, 0, 0));
        db.setFont(Font.font(fonts));

        Text fn = new Text("FATHER'S NAME : ");
        fn.setX(posX);
        fn.setY(posY + 150);
        fn.setFill(Color.rgb(0, 0, 0));
        fn.setFont(Font.font(fonts));

        Text mn = new Text("MOTHER'S NAME : ");
        mn.setX(posX);
        mn.setY(posY + 200);
        mn.setFill(Color.rgb(0, 0, 0));
        mn.setFont(Font.font(fonts));

        Text add = new Text("ADDRESS : ");
        add.setX(posX);
        add.setY(posY + 250);
        add.setFill(Color.rgb(0, 0, 0));
        add.setFont(Font.font(fonts));

        Text ct = new Text("CITY : ");
        ct.setX(posX);
        ct.setY(posY + 300);
        ct.setFill(Color.rgb(0, 0, 0));
        ct.setFont(Font.font(fonts));

        Text mail = new Text("E-MAIL ID : ");
        mail.setX(posX);
        mail.setY(posY + 350);
        mail.setFill(Color.rgb(0, 0, 0));
        mail.setFont(Font.font(fonts));

        Text tn = new Text("SIM TYPE : ");
        tn.setX(posX);
        tn.setY(posY + 400);
        tn.setFill(Color.rgb(0, 0, 0));
        tn.setFont(Font.font(fonts));

        TextField userName = new TextField();
        userName.setLayoutX(textFieldPosX);
        userName.setLayoutY(textFieldPosY);
        root.getChildren().add(userName);

        TextField NID = new TextField();
        NID.setLayoutX(textFieldPosX);
        NID.setLayoutY(textFieldPosY + 50);
        root.getChildren().add(NID);

        TextField bDay = new TextField();
        bDay.setLayoutX(textFieldPosX);
        bDay.setLayoutY(textFieldPosY + 100);
        root.getChildren().add(bDay);

        TextField Father = new TextField();
        Father.setLayoutX(textFieldPosX);
        Father.setLayoutY(textFieldPosY + 150);
        root.getChildren().add(Father);

        TextField Mother = new TextField();
        Mother.setLayoutX(textFieldPosX);
        Mother.setLayoutY(textFieldPosY + 200);
        root.getChildren().add(Mother);

        TextField Address = new TextField();
        Address.setLayoutX(textFieldPosX);
        Address.setLayoutY(textFieldPosY + 250);
        root.getChildren().add(Address);

        TextField City = new TextField();
        City.setLayoutX(textFieldPosX);
        City.setLayoutY(textFieldPosY + 300);
        root.getChildren().add(City);

        TextField Mailf = new TextField();
        Mailf.setLayoutX(textFieldPosX);
        Mailf.setLayoutY(textFieldPosY + 350);
        root.getChildren().add(Mailf);

        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Pre-Paid SIM");
        rb1.setToggleGroup(group);
        rb1.setUserData("1");
        rb1.setLayoutX(textFieldPosX);
        rb1.setLayoutY(textFieldPosY + 400);

        RadioButton rb2 = new RadioButton("Post-Paid SIM");
        rb2.setToggleGroup(group);
        rb2.setUserData("2");
        rb2.setLayoutX(textFieldPosX);
        rb2.setLayoutY(textFieldPosY + 450);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    SIMtypeID = group.getSelectedToggle().getUserData().toString();
                    System.out.println(SIMtypeID);
                }
            }
        });

        root.getChildren().add(rb1);
        root.getChildren().add(rb2);

        Button signUpBtn = new Button("Apply For New SIM");
        signUpBtn.setLayoutX(buttonPosX);
        signUpBtn.setLayoutY(buttonPosY);
        root.getChildren().add(signUpBtn);

        Button backToHomeBtn = new Button("Back");
        backToHomeBtn.setLayoutX(buttonPosX);
        backToHomeBtn.setLayoutY(buttonPosY + 100);
        root.getChildren().add(backToHomeBtn);

        backToHomeBtn.setOnAction(e -> LoginScene());

        signUpBtn.setOnAction((ActionEvent e) -> {
            String s1 = userName.getText();
            String s2 = NID.getText();
            String s3 = bDay.getText();
            String s4 = Father.getText();
            String s5 = Mother.getText();
            String s6 = Address.getText();
            String s7 = City.getText();
            String s8 = Mailf.getText();

            boolean success = true;

            try {

                Connection con;
                con = new DataBase("TELECOM", "hr").getConnection();
                String sql = "INSERT INTO PENDING_SIM_REQ (USER_NAME,NID_NO,DATE_OF_BIRTH,NAME_OF_FATHER,NAME_OF_MOTHER,EMAIL,STREET_ADDRESS,CITY, IS_EMPLOYEE, SIM_TYPE_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, s1);
                pst.setString(2, s2);
                pst.setString(3, s3);
                pst.setString(4, s4);
                pst.setString(5, s5);
                pst.setString(6, s8);
                pst.setString(7, s6);
                pst.setString(8, s7);
                pst.setString(9, "NO");
                pst.setString(10, SIMtypeID);

                pst.executeQuery();

                SuccessMsg("Success", "Your Application Has Been Successfully Received.\nYou Will Receive A Confirmation Email.\nThank You.");

                pst.close();
                con.close();

            } catch (Exception ex) {
                System.out.println("Data insert doesn't work");
            }

        });

        root.getChildren().add(Name);
        root.getChildren().add(nid);
        root.getChildren().add(db);
        root.getChildren().add(fn);
        root.getChildren().add(mn);
        root.getChildren().add(add);
        root.getChildren().add(ct);
        root.getChildren().add(tn);
        root.getChildren().add(mail);

        StageShow();
    }

    private void LoginScene() {
        int posX = 350, posY = 300;
        int textFieldPosX = posX + 455, textFieldPosY = posY - 20;
        BackSceneOnly();
        ImageView toppart = new ImageView("file:tbdgreen.png");
        ImageView tbdlogin = new ImageView("file:userlogin.jpg");
        name = new String();
        passcode = new String();
        TextField username = new TextField();
        username.setLayoutX(textFieldPosX);
        username.setLayoutY(textFieldPosY);
        username.setScaleX(2.65);
        username.setScaleY(2);
        PasswordField password = new PasswordField();
        password.setLayoutX(textFieldPosX);
        password.setLayoutY(textFieldPosY + 100);
        password.setScaleX(2.65);
        password.setScaleY(2);
        Rectangle loginBox = new Rectangle(480, 45);
        loginBox.setFill(Color.rgb(32, 155, 146));
        loginBox.setX(660);
        loginBox.setY(482);
        Rectangle applyBox = new Rectangle(680, 75);
        applyBox.setX(560);
        applyBox.setY(582);
        applyBox.setFill(Color.rgb(32, 155, 146));
        Text applytext = new Text(750, 635, "APPLY FOR A SIM");
        applytext.setFont(Font.font(40));
        applytext.setFill(Color.WHITE);
        applyBox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            applyBox.setFill(Color.rgb(17, 119, 112));
        });
        applytext.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            applyBox.setFill(Color.rgb(17, 119, 112));
        });
        applytext.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            applyBox.setFill(Color.rgb(32, 155, 146));
        });
        applyBox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            applyBox.setFill(Color.rgb(32, 155, 146));
        });
        applyBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            SignUPScene();
        });
        Text login = new Text(850, 515, "LOG IN");
        login.setFont(Font.font(30));
        login.setFill(Color.WHITE);
        loginBox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            loginBox.setFill(Color.rgb(17, 119, 112));
        });
        login.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            loginBox.setFill(Color.rgb(17, 119, 112));
        });
        loginBox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            loginBox.setFill(Color.rgb(32, 155, 146));
        });
        loginBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            continueLogIn(username, password);
        });
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ENTER:
                    continueLogIn(username, password);
                    break;
            }
        });
        root.getChildren().add(tbdlogin);
        root.getChildren().add(toppart);
        root.getChildren().add(loginBox);
        root.getChildren().add(username);
        root.getChildren().add(password);
        root.getChildren().add(login);
        root.getChildren().add(applyBox);
        root.getChildren().add(applytext);
        StageShow();
    }

    public void LogIn(String User_Name, String NID_No) {
        BackSceneOnly();

        int posX = 300, posY = 200;
        int textFieldPosX = posX + 200, textFieldPosY = posY - 15;
        int buttonPosX = textFieldPosX + 400, buttonPosY = 130;

        Profile(User_Name, NID_No); // HomePage of User = User Profile

        Button profileBtn = new Button("Profile");
        SetThisButton(profileBtn, buttonPosX, buttonPosY + 50);
        profileBtn.setOnAction(e -> LogIn(User_Name, NID_No));

        Button dialListBtn = new Button("Dialled Call List");
        SetThisButton(dialListBtn, buttonPosX, buttonPosY + 100);

        dialListBtn.setOnAction(e -> {
            ShowDialledCallList(NID_No);
        });

        Button receiveListBtn = new Button("Received Call List");
        SetThisButton(receiveListBtn, buttonPosX, buttonPosY + 150);

        receiveListBtn.setOnAction(e -> {
            ShowReceivedCallList(NID_No);
        });

        Button SMSListBtn = new Button("SMS List");
        SetThisButton(SMSListBtn, buttonPosX, buttonPosY + 200);

        SMSListBtn.setOnAction(e -> {
            ShowSMSList(NID_No);
        });

        Button netDataBtn = new Button("Net Usage");
        SetThisButton(netDataBtn, buttonPosX, buttonPosY + 250);

        Button makeCallBtn = new Button("Make A Call");
        SetThisButton(makeCallBtn, buttonPosX, buttonPosY + 300);

        makeCallBtn.setOnAction(e -> {
            makeACall();
        });

        Button sendSMSBtn = new Button("Send An SMS");
        SetThisButton(sendSMSBtn, buttonPosX, buttonPosY + 350);

        sendSMSBtn.setOnAction(e -> {
            sendSMS();
        });

        Button useNetBtn = new Button("Use Internet");
        SetThisButton(useNetBtn, buttonPosX, buttonPosY + 400);

        useNetBtn.setOnAction(e -> {
            useNet();
        });

        Button BalanceBtn = new Button("Check Balance");
        BalanceBtn.setLayoutX(buttonPosX);
        BalanceBtn.setLayoutY(buttonPosY + 450);
        BalanceBtn.setPrefSize(150, 30);

        Button billBtn = new Button("Show Bill");
        billBtn.setLayoutX(buttonPosX);
        billBtn.setLayoutY(buttonPosY + 450);
        billBtn.setPrefSize(150, 30);

        BalanceBtn.setOnAction(e -> checkBalance());
        billBtn.setOnAction(e -> showBill());

        Button rechargeBtn = new Button("Recharge");
        rechargeBtn.setLayoutX(buttonPosX);
        rechargeBtn.setLayoutY(buttonPosY + 500);
        rechargeBtn.setPrefSize(150, 30);
        if (SIM_Type.equals("1")) {
            root.getChildren().add(rechargeBtn);
            root.getChildren().add(BalanceBtn);
        } else {
            root.getChildren().add(billBtn);
        }

        rechargeBtn.setOnAction(e -> recharge());

        Text ib = new Text("New Password : ");
        ib.setX(posX + 50);
        ib.setY(posY + 470);
        ib.setFont(Font.font(20));
        ib.setFill(Color.rgb(0, 0, 0));

        TextField tf = new TextField();
        setThisTextField(tf, posX + 200, posY + 450);
        root.getChildren().remove(tf);

        Button okbtn = new Button("OK");
        okbtn.relocate(posX + 50, posY + 500);

        Button changePassBtn = new Button("Change Password");
        SetThisButton(changePassBtn, posX + 50, posY + 450);

        changePassBtn.setOnAction(e
                -> {
            root.getChildren().remove(changePassBtn);
            root.getChildren().addAll(tf, ib, okbtn);

        });

        okbtn.setOnAction(ex -> {
            if (!tf.getText().equals("")) {
                String sql = "UPDATE USER_DATA \n"
                        + "SET \n"
                        + "USER_PASSWORD = ? \n"
                        + "WHERE NID_NO = ? ";
                try {
                    Connection con = new DataBase("TELECOM", "hr").getConnection();
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, tf.getText());
                    pst.setString(2, passcode);

                    pst.executeQuery();

                    pst.close();
                    con.close();
                    SuccessMsg("Success", "Password Changed");
                    LoginScene();
                } catch (Exception exc) {
                    System.out.println("doesn't work");
                }
            }
            else{
                InvalidLogInMsg("Failed", "New password hasn't been entered");
            }
        });

        Button backToHomeBtn = new Button("Log Out");
        SetThisButton(backToHomeBtn, posX + 50, buttonPosY + 450);
        backToHomeBtn.setOnAction(e -> LogOut());

        StageShow();
    }

    String billId, simNo, callBill, smsBill, netBill, totalBill, issuedBy;

    public void showBill() {

        double posX = 300, posY = 200;
        BackSceneOnly();
        try {
            String sql = "SELECT * FROM POSTPAID_BILL \n"
                    + "WHERE SIM_NO = ? AND ISSUED_BY IS NOT NULL AND IS_PAID = 'NO'";

            Connection con = new DataBase("TELECOM", "hr").getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, phoneno);

            ResultSet rs = pst.executeQuery();

            System.out.println("muri kha");

            if (rs.next()) {
                billId = rs.getString(1);
                simNo = rs.getString(2);
                callBill = rs.getString(3);
                smsBill = rs.getString(4);
                netBill = rs.getString(5);
                totalBill = rs.getString(6);
                issuedBy = rs.getString(7);

                Text bid = new Text("Bill ID : " + billId);
                bid.setX(posX);
                bid.setY(posY + 100);
                bid.setFont(Font.font(20));
                bid.setFill(Color.rgb(0, 0, 0));

                Text simno = new Text("User SIM No : " + simNo);
                simno.setX(posX);
                simno.setY(posY + 150);
                simno.setFont(Font.font(20));
                simno.setFill(Color.rgb(0, 0, 0));

                Text cb = new Text("Bill For Call : " + callBill);
                cb.setX(posX);
                cb.setY(posY + 200);
                cb.setFont(Font.font(20));
                cb.setFill(Color.rgb(0, 0, 0));

                Text sb = new Text("Bill For SMS : " + smsBill);
                sb.setX(posX);
                sb.setY(posY + 250);
                sb.setFont(Font.font(20));
                sb.setFill(Color.rgb(0, 0, 0));

                Text nb = new Text("Bill For Net : " + netBill);
                nb.setX(posX);
                nb.setY(posY + 300);
                nb.setFont(Font.font(20));
                nb.setFill(Color.rgb(0, 0, 0));

                Text totbill = new Text("Total Bill : " + totalBill);
                totbill.setX(posX);
                totbill.setY(posY + 350);
                totbill.setFont(Font.font(20));
                totbill.setFill(Color.rgb(0, 0, 0));

                Text ib = new Text("Issued By : " + issuedBy);
                ib.setX(posX);
                ib.setY(posY + 400);
                ib.setFont(Font.font(20));
                ib.setFill(Color.rgb(0, 0, 0));

                root.getChildren().add(ib);
                root.getChildren().add(totbill);
                root.getChildren().add(nb);
                root.getChildren().add(sb);
                root.getChildren().add(cb);
                root.getChildren().add(simno);
                root.getChildren().add(bid);

                StageShow();

                SuccessMsg("", "You have a due of " + totalBill + " Taka");
            } else {
                SuccessMsg("Congratulations!", "You don't have any due right now");
            }

            pst.close();
            con.close();
            SuccessMsg("Success", "Your Balance Was retrieved");
        } catch (Exception e) {

            System.out.println(balance);
            InvalidLogInMsg("failed", "Can't check Balance");
        }

        goToProfileButton(300, 630);

        StageShow();
    }

    double balance;

    public void checkBalance() {
        BackSceneOnly();

        try {
            String sql = "SELECT BALANCE FROM PREPAID_SIM "
                    + "WHERE SIM_NO = ?";

            Connection con = new DataBase("TELECOM", "hr").getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, phoneno);

            ResultSet rs = pst.executeQuery();

            System.out.println("muri kha");
            rs.next();

            balance = Double.parseDouble(rs.getString(1));

            pst.close();
            con.close();
            SuccessMsg("Success", "Your Balance Was retrieved");
        } catch (Exception e) {

            System.out.println(balance);
            InvalidLogInMsg("failed", "Can't check Balance");
        }

        double posX = 300, posY = 200;

        Text strtTime = new Text("USER NO : " + phoneno);
        strtTime.setX(posX);
        strtTime.setY(posY);
        strtTime.setFill(Color.rgb(0, 0, 0));

        Text duration = new Text("Balance : " + balance + "  Taka");
        duration.setX(posX);
        duration.setY(posY + 100);
        duration.setFill(Color.rgb(0, 0, 0));

        goToProfileButton(300 + 200, 600);

        StageShow();

//            VBox vb = new VBox();
        root.getChildren().addAll(strtTime);
        root.getChildren().addAll(duration);

//            Scene sc = new Scene(vb);
//            Stage window = new Stage();
//            window.setTitle("Balance");
//            window.setScene(sc);
//            window.show();
        StageShow();
    }

    int rechargeCost;

    public void recharge() {
        BackSceneOnly();

        int textFieldPosX = 600, textFieldPosY = 350;
        int buttonPosX = textFieldPosX - 55, buttonPosY = textFieldPosY + 50;

        TextField cardNo = new TextField();
        setThisTextField(cardNo, textFieldPosX, textFieldPosY);

        Button applyButton = new Button("Recharge");
        SetThisButton(applyButton, buttonPosX, buttonPosY);

        Button backToHomeBtn = new Button("Back To Profile");
        SetThisButton(backToHomeBtn, buttonPosX + 200, buttonPosY);

        backToHomeBtn.setOnAction(e -> LogIn(name, passcode));

        applyButton.setOnAction(e -> {
            try {

                String sql = "SELECT COST FROM AVAILABLE_RECHARGE_CARDS \n"
                        + "WHERE CARD_NUMBER = ?";

                Connection con = new DataBase("TELECOM", "hr").getConnection();

                PreparedStatement pst = con.prepareStatement(sql);

                pst.setInt(1, Integer.parseInt(cardNo.getText()));

                ResultSet rs = pst.executeQuery();

                rs.next();

                rechargeCost = Integer.parseInt(rs.getString(1));

                System.out.println(rechargeCost);

                pst.close();
                con.close();

                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con2 = new DataBase("TELECOM", "hr").getConnection();

                CallableStatement stmt = con2.prepareCall("{call RECHARGE_PROCEDURE(?,?)}");

                stmt.setString(1, cardNo.getText());
                stmt.setString(2, phoneno);

                stmt.executeUpdate();

                stmt.close();
                con2.close();

                SuccessMsg("Success", "Your SIM Was Successfully Recharged With Tk. " + rechargeCost);

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(User_UI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(User_UI.class.getName()).log(Level.SEVERE, null, ex);
            }

            double posX = 300, posY = 200;
            Text strtTime = new Text("USER NO : " + phoneno);
            strtTime.setX(posX);
            strtTime.setY(posY);
            strtTime.setFill(Color.rgb(0, 0, 0));
            Text duration = new Text("Amount : " + rechargeCost + "  Taka");
            duration.setX(posX);
            duration.setY(posY + 100);
            duration.setFill(Color.rgb(0, 0, 0));
            BackSceneOnly();

            root.getChildren().add(strtTime);
            root.getChildren().add(duration);

            goToProfileButton(buttonPosX + 200, buttonPosY);

            StageShow();
        });
        StageShow();
    }

    String startTime, endTime, recno;
    String STRT2, END2;

    public void sendSMS() {
        BackSceneOnly();

        SimpleDateFormat sdfTime = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        int textFieldPosX = 600, textFieldPosY = 350;
        int buttonPosX = textFieldPosX - 55, buttonPosY = textFieldPosY + 50;

        TextField receiverNo = new TextField();
        setThisTextField(receiverNo, textFieldPosX, textFieldPosY - 50);
        TextArea sms = new TextArea("Write your sms here.");
        sms.setLayoutX(600);
        sms.setLayoutY(350);
        sms.setPrefHeight(100);
        sms.setPrefWidth(500);
        root.getChildren().add(sms);

        Text to = new Text("To :");
        Text content = new Text("Content :");
        to.setFont(Font.font(20));
        content.setFont(Font.font(20));
        to.setX(548);
        to.setY(320);
        content.setX(500);
        content.setY(370);
        root.getChildren().add(to);
        root.getChildren().add(content);

        Button sendButton = new Button("Send An SMS");
        SetThisButton(sendButton, buttonPosX + 55, buttonPosY + 75);

        Button backToHomeBtn = new Button("Back To Profile");
        SetThisButton(backToHomeBtn, buttonPosX + 230, buttonPosY + 75);

        backToHomeBtn.setOnAction(e -> LogIn(name, passcode));

        sendButton.setOnAction(e -> {
            Date now = new Date();
            startTime = sdf2.format(now);

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con2 = new DataBase("TELECOM", "hr").getConnection();

                CallableStatement stmt = con2.prepareCall("{call SEND_AN_SMS(?,?,?)}");

                recno = receiverNo.getText();
                stmt.setString(1, phoneno);
                stmt.setString(2, recno);
                stmt.setString(3, startTime);

                stmt.executeUpdate();

                stmt.close();
                con2.close();

                SuccessMsg("Success", "The SMS Was Successfully Sent." + passcode);
            } catch (SQLException ex2) {
                System.out.println("doesn't work");
                InvalidLogInMsg("Approve Failed", "Didn't Work");
            } catch (ClassNotFoundException ex) {
                System.out.println("calling e problem hocche");
            }

            double posX = 300, posY = 200;
            Text strtTime = new Text("SENDER NO : " + phoneno);
            strtTime.setX(posX);
            strtTime.setY(posY);
            strtTime.setFill(Color.rgb(0, 0, 0));

            Text recNotxt = new Text("RECEIVER NO : " + recno);
            recNotxt.setX(posX);
            recNotxt.setY(posY + 40);
            recNotxt.setFill(Color.rgb(0, 0, 0));

            Text sendTime = new Text("Sending Time : " + startTime);
            sendTime.setX(posX);
            sendTime.setY(posY + 80);
            sendTime.setFill(Color.rgb(0, 0, 0));

//                Text duration = new Text("COST : " + CallDurationSecond + "  second(s)");
//                duration.setX(posX);
//                duration.setY(posY + 100);
//                duration.setFill(Color.rgb(0, 0, 0));
            BackSceneOnly();

            root.getChildren().add(strtTime);
            root.getChildren().add(sendTime);
            root.getChildren().add(recNotxt);
//                root.getChildren().add(cost);

            goToProfileButton(buttonPosX + 200, buttonPosY);

            StageShow();
        });

        StageShow();
    }

    public void useNet() {
        BackSceneOnly();

        SimpleDateFormat sdfTime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        int textFieldPosX = 600, textFieldPosY = 350;
        int buttonPosX = textFieldPosX - 55, buttonPosY = textFieldPosY + 50;

        TextField USEkb = new TextField();
        setThisTextField(USEkb, textFieldPosX, textFieldPosY);

        Button startButton = new Button("Use Net");
        SetThisButton(startButton, buttonPosX, buttonPosY);

        Button backToHomeBtn = new Button("Back To Profile");
        SetThisButton(backToHomeBtn, buttonPosX + 200, buttonPosY);

        backToHomeBtn.setOnAction(e -> LogIn(name, passcode));

        startButton.setOnAction(e -> {
            Date now = new Date();
            startTime = sdfTime.format(now);

            root.getChildren().remove(USEkb);
            Date now1 = new Date();

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con2 = new DataBase("TELECOM", "hr").getConnection();

                CallableStatement stmt = con2.prepareCall("{call USE_NET(?,?,?)}");

                stmt.setString(1, phoneno);
                stmt.setString(2, startTime);
                stmt.setString(3, USEkb.getText());

                stmt.executeUpdate();

                stmt.close();
                con2.close();

                SuccessMsg("Approved", USEkb.getText() + "KB Net Was Used By This Sim" + passcode);
            } catch (SQLException ex2) {
                System.out.println("doesn't work");
                InvalidLogInMsg("Approve Failed", "Didn't Work");
            } catch (ClassNotFoundException ex) {
                System.out.println("calling e problem hocche");
            }

            double posX = 300, posY = 200;

            Text userno = new Text("User SIM No: " + phoneno);
            userno.setX(posX);
            userno.setY(posY);
            userno.setFill(Color.rgb(0, 0, 0));

            Text strtTime = new Text("Session Starting Time : " + startTime);
            strtTime.setX(posX);
            strtTime.setY(posY + 50);
            strtTime.setFill(Color.rgb(0, 0, 0));

            Text duration = new Text("Net Used : " + USEkb.getText() + "  KB");
            duration.setX(posX);
            duration.setY(posY + 100);
            duration.setFill(Color.rgb(0, 0, 0));

            BackSceneOnly();

            root.getChildren().add(strtTime);
            root.getChildren().add(userno);
            root.getChildren().add(duration);

            goToProfileButton(buttonPosX + 200, buttonPosY);
            StageShow();
        });

        StageShow();
    }

    public void makeACall() {
        BackSceneOnly();

        SimpleDateFormat sdfTime = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        ImageView img = new ImageView("file:load2.gif");
        img.setFitWidth(100);
        img.setFitHeight(100);
        img.setX(570);
        img.setY(300);

        int textFieldPosX = 600, textFieldPosY = 350;
        int buttonPosX = textFieldPosX - 55, buttonPosY = textFieldPosY + 50;

        TextField receiverNo = new TextField();
        setThisTextField(receiverNo, textFieldPosX, textFieldPosY);

        Button startButton = new Button("Start Call");
        SetThisButton(startButton, buttonPosX + 55, buttonPosY);

        Button backToHomeBtn = new Button("Back To Profile");
        SetThisButton(backToHomeBtn, buttonPosX + 55, buttonPosY + 50);

        Text receiver = new Text("Receiver No :");
        receiver.setX(460);
        receiver.setY(370);
        receiver.setFont(Font.font(20));
        root.getChildren().add(receiver);

        backToHomeBtn.setOnAction(e -> LogIn(name, passcode));

        startButton.setOnAction(e -> {
            Date now = new Date();
            root.getChildren().add(img);
            root.getChildren().remove(receiverNo);
            root.getChildren().remove(receiver);
            startTime = sdfTime.format(now);
            STRT2 = sdf2.format(now);

            root.getChildren().remove(startButton);
            root.getChildren().remove(backToHomeBtn);

            Button endButton = new Button("End Call");
            SetThisButton(endButton, buttonPosX, buttonPosY);

            endButton.setOnAction(e1 -> {
                root.getChildren().remove(img);

                Date now1 = new Date();
                endTime = sdfTime.format(now1);
                END2 = sdf2.format(now1);

                Date strt = null, end = null;

                try {
                    strt = sdfTime.parse(startTime);
                    end = sdfTime.parse(endTime);
                } catch (ParseException exc) {
                    exc.printStackTrace();
                }

                long diff = end.getTime() - strt.getTime();
                double CallDurationSecond = diff / 1000.0;

                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con2 = new DataBase("TELECOM", "hr").getConnection();

                    CallableStatement stmt = con2.prepareCall("{call MAKE_A_CALL(?,?,?,?)}");

                    stmt.setString(1, phoneno);
                    stmt.setString(2, receiverNo.getText());
                    stmt.setString(3, STRT2);
                    stmt.setString(4, END2);

                    stmt.executeUpdate();

                    stmt.close();
                    con2.close();
                } catch (SQLException ex2) {

                } catch (ClassNotFoundException ex) {

                }

                System.out.println("hi:" + STRT2 + "       " + END2);

                double posX = 500, posY = 250;
                Text strtTime = new Text("Starting Time : " + startTime);
                strtTime.setX(posX);
                strtTime.setY(posY);
                strtTime.setFont(Font.font(20));
                strtTime.setFill(Color.rgb(0, 0, 0));

                Text EndingTime = new Text("Ending Time : " + endTime);
                EndingTime.setX(posX);
                EndingTime.setY(posY + 50);
                EndingTime.setFont(Font.font(20));
                EndingTime.setFill(Color.rgb(0, 0, 0));

                Text duration = new Text("Duration (Second) : " + CallDurationSecond + "  second(s)");
                duration.setX(posX);
                duration.setY(posY + 100);
                duration.setFont(Font.font(20));
                duration.setFill(Color.rgb(0, 0, 0));

                BackSceneOnly();

                root.getChildren().add(strtTime);
                root.getChildren().add(EndingTime);
                root.getChildren().add(duration);

                goToProfileButton(buttonPosX - 50, buttonPosY);
                StageShow();
            });

            StageShow();
        });

        StageShow();
    }

    public void goToProfileButton(int buttonPosX, int buttonPosY) {
        Button backToHomeBtn = new Button("Back To Profile");
        SetThisButton(backToHomeBtn, buttonPosX, buttonPosY);
        backToHomeBtn.setOnAction(e -> LogIn(name, passcode));
    }

    void LogOut() {
        LoginScene();
        SuccessMsg("Success", "Log Out Successful");
    }

    public void SetThisButton(Button b, double x, double y) {
        b.setLayoutX(x);
        b.setLayoutY(y);
        b.setPrefSize(150, 30);
        root.getChildren().add(b);
    }

    public void setThisTextField(TextField tf, double PosX, double PosY) {
        tf.setLayoutX(PosX);
        tf.setLayoutY(PosY);
        root.getChildren().add(tf);
    }

    public void ShowSMSList(String NID_No) {
        try {
            String sql = "SELECT C.SMS_ID , C.SENDER_NO , C.RECEIVER_NO , C.SENDING_TIME , T.TYPE_NAME\n"
                    + "FROM USER_DATA U \n"
                    + "JOIN SIM S\n"
                    + "ON(U.NID_NO = ? AND U.NID_NO = S.NID_NO)\n"
                    + "JOIN SMS_DATABASE C\n"
                    + "ON(S.PHONE_NO = C.SENDER_NO)\n"
                    + "JOIN SIM_TYPE T \n"
                    + "ON(S.TYPE_ID = T.TYPE_ID)";

            Connection con = new DataBase("TELECOM", "hr").getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, NID_No);
            ResultSet rs = pst.executeQuery();

            ObservableList<SMSData> dialList = FXCollections.observableArrayList();
            while (rs.next()) {
                dialList.add(new SMSData(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5)));

            }

            TableColumn<SMSData, String> CallerNoColm = new TableColumn<>("SMS ID");
            CallerNoColm.setMinWidth(200);
            CallerNoColm.setCellValueFactory(new PropertyValueFactory<>("SMS_ID"));

            TableColumn<SMSData, String> ReceiverNoColm = new TableColumn<>("Sender No");
            ReceiverNoColm.setMinWidth(200);
            ReceiverNoColm.setCellValueFactory(new PropertyValueFactory<>("SenderNo"));

            TableColumn<SMSData, String> StartingTimeColm = new TableColumn<>("Receiver No");
            StartingTimeColm.setMinWidth(200);
            StartingTimeColm.setCellValueFactory(new PropertyValueFactory<>("ReceiverNo"));

            TableColumn<SMSData, String> EndingTimeColm = new TableColumn<>("Sending Time");
            EndingTimeColm.setMinWidth(200);
            EndingTimeColm.setCellValueFactory(new PropertyValueFactory<>("SendingTime"));

            TableColumn<SMSData, String> typenameColm = new TableColumn<>("SIM Type");
            typenameColm.setMinWidth(200);
            typenameColm.setCellValueFactory(new PropertyValueFactory<>("typeName"));

            TableView<SMSData> DialTable;
            DialTable = new TableView<>();
            DialTable.getColumns().addAll(CallerNoColm, ReceiverNoColm, StartingTimeColm, EndingTimeColm, typenameColm);

            DialTable.setItems(dialList);

            VBox vb = new VBox();
            vb.getChildren().addAll(DialTable);

            Scene sc = new Scene(vb);
            Stage window = new Stage();
            window.setTitle("SMS List");
            window.setScene(sc);
            window.show();

            pst.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("SMS list show error!!!");
        }
    }

    public void ShowDialledCallList(String NID_No) {
        try {
            String sql = "SELECT C.CALLER_NO , C.RECEIVER_NO, C.STARTING_TIME , "
                    + "C.ENDING_TIME , TO_CHAR(C.ENDING_TIME-C.STARTING_TIME),"
                    + "T.TYPE_NAME FROM USER_DATA U "
                    + "JOIN SIM S ON(U.NID_NO = ? AND "
                    + "U.NID_NO = S.NID_NO) JOIN CALL_DATABASE C "
                    + "ON(S.PHONE_NO = C.CALLER_NO) "
                    + "JOIN SIM_TYPE T ON(S.TYPE_ID = T.TYPE_ID)";

            Connection con = new DataBase("TELECOM", "hr").getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, NID_No);
            ResultSet rs = pst.executeQuery();

            ObservableList<DialledListData> dialList = FXCollections.observableArrayList();
            while (rs.next()) {
                System.out.println(rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));

                dialList.add(new DialledListData(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6)));

            }

            TableColumn<DialledListData, String> CallerNoColm = new TableColumn<>("Caller SIM No");
            CallerNoColm.setMinWidth(200);
            CallerNoColm.setCellValueFactory(new PropertyValueFactory<>("callerNo"));

            TableColumn<DialledListData, String> ReceiverNoColm = new TableColumn<>("Receiver SIM No");
            ReceiverNoColm.setMinWidth(200);
            ReceiverNoColm.setCellValueFactory(new PropertyValueFactory<>("receiverNo"));

            TableColumn<DialledListData, String> StartingTimeColm = new TableColumn<>("Starting Time");
            StartingTimeColm.setMinWidth(200);
            StartingTimeColm.setCellValueFactory(new PropertyValueFactory<>("startingTime"));

            TableColumn<DialledListData, String> EndingTimeColm = new TableColumn<>("Ending Time");
            EndingTimeColm.setMinWidth(200);
            EndingTimeColm.setCellValueFactory(new PropertyValueFactory<>("endingTime"));

            TableColumn<DialledListData, String> DurationColm = new TableColumn<>("Call Duration");
            DurationColm.setMinWidth(200);
            DurationColm.setCellValueFactory(new PropertyValueFactory<>("callDuration"));

            TableColumn<DialledListData, String> typenameColm = new TableColumn<>("SIM Type");
            typenameColm.setMinWidth(200);
            typenameColm.setCellValueFactory(new PropertyValueFactory<>("typeName"));

            TableView<DialledListData> DialTable;
            DialTable = new TableView<>();
            DialTable.getColumns().addAll(CallerNoColm, ReceiverNoColm, StartingTimeColm, EndingTimeColm, DurationColm, typenameColm);

            DialTable.setItems(dialList);

            VBox vb = new VBox();
            vb.getChildren().addAll(DialTable);

            Scene sc = new Scene(vb);
            Stage window = new Stage();
            window.setTitle("Dialled Call List");
            window.setScene(sc);
            window.show();

            pst.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Diall Call list show error!!!");
        }
    }

    public void ShowReceivedCallList(String NID_No) {
        try {
            String sql = "SELECT C.RECEIVER_NO, C.CALLER_NO ,  C.STARTING_TIME , "
                    + "C.ENDING_TIME , TO_CHAR(C.ENDING_TIME-C.STARTING_TIME),T.TYPE_NAME\n"
                    + "FROM USER_DATA U \n"
                    + "JOIN SIM S\n"
                    + "ON(U.NID_NO = ? AND	U.NID_NO = S.NID_NO)\n"
                    + "JOIN CALL_DATABASE C\n"
                    + "ON(S.PHONE_NO = C.RECEIVER_NO)\n"
                    + "JOIN SIM_TYPE T\n"
                    + "ON(S.TYPE_ID = T.TYPE_ID)";

            Connection con = new DataBase("TELECOM", "hr").getConnection();

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, NID_No);
            ResultSet rs = pst.executeQuery();

            ObservableList<ReceivedListData> receiveList = FXCollections.observableArrayList();
            while (rs.next()) {
//                System.out.println(rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6));

                receiveList.add(new ReceivedListData(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6)));

            }

            TableColumn<ReceivedListData, String> ReceiverNoColm = new TableColumn<>("Receiver SIM No");
            ReceiverNoColm.setMinWidth(200);
            ReceiverNoColm.setCellValueFactory(new PropertyValueFactory<>("receiverNo"));

            TableColumn<ReceivedListData, String> CallerNoColm = new TableColumn<>("Caller SIM No");
            CallerNoColm.setMinWidth(200);
            CallerNoColm.setCellValueFactory(new PropertyValueFactory<>("callerNo"));

            TableColumn<ReceivedListData, String> StartingTimeColm = new TableColumn<>("Starting Time");
            StartingTimeColm.setMinWidth(200);
            StartingTimeColm.setCellValueFactory(new PropertyValueFactory<>("startingTime"));

            TableColumn<ReceivedListData, String> EndingTimeColm = new TableColumn<>("Ending Time");
            EndingTimeColm.setMinWidth(200);
            EndingTimeColm.setCellValueFactory(new PropertyValueFactory<>("endingTime"));

            TableColumn<ReceivedListData, String> DurationColm = new TableColumn<>("Call Duration");
            DurationColm.setMinWidth(200);
            DurationColm.setCellValueFactory(new PropertyValueFactory<>("callDuration"));

            TableColumn<ReceivedListData, String> typenameColm = new TableColumn<>("SIM Type");
            typenameColm.setMinWidth(200);
            typenameColm.setCellValueFactory(new PropertyValueFactory<>("typeName"));

            TableView<ReceivedListData> receiveTable;
            receiveTable = new TableView<>();
            receiveTable.getColumns().addAll(ReceiverNoColm, CallerNoColm, StartingTimeColm, EndingTimeColm, DurationColm, typenameColm);

            receiveTable.setItems(receiveList);

            VBox vb = new VBox();

            vb.getChildren().addAll(receiveTable);

            Scene sc = new Scene(vb);
            Stage window = new Stage();
            window.setTitle("Received Call List");
            window.setScene(sc);
            window.show();

            pst.close();
            con.close();

        } catch (Exception ex) {
            System.out.println("Received  Call list show error!!!");
        }
    }

    public void Profile(String User_Name, String NID_No) {
        try {
            String sql = "SELECT * FROM USER_DATA WHERE USER_NAME = ? AND NID_NO = ?";

            int posX = 350, posY = 200;
            int textFieldPosX = posX + 200, textFieldPosY = posY - 15;
            int buttonPosX = textFieldPosX + 300, buttonPosY = 50;

            Connection con = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, User_Name);
            pst.setString(2, NID_No);
            ResultSet rs = pst.executeQuery();

//            System.out.println("agdfkasbhkc");
            rs.next();

            Text Name = new Text("USER NAME : " + rs.getString(1));
            Name.setX(posX);
            Name.setY(posY);
            Name.setFont(Font.font(20));
            Name.setFill(Color.rgb(0, 0, 0));

            Text nid = new Text("NID NO : " + rs.getString(2));
            nid.setX(posX);
            nid.setY(posY + 50);
            nid.setFont(Font.font(20));
            nid.setFill(Color.rgb(0, 0, 0));

            Text db = new Text("DATE OF BIRTH (MMM DD, YYYY) : " + rs.getString(3));
            db.setFont(Font.font(20));
            db.setX(posX);
            db.setY(posY + 100);
            db.setFill(Color.rgb(0, 0, 0));

            Text fn = new Text("FATHER'S NAME : " + rs.getString(4));
            fn.setX(posX);
            fn.setFont(Font.font(20));
            fn.setY(posY + 150);
            fn.setFill(Color.rgb(0, 0, 0));

            Text mn = new Text("MOTHER'S NAME : " + rs.getString(5));
            mn.setX(posX);
            mn.setFont(Font.font(20));
            mn.setY(posY + 200);
            mn.setFill(Color.rgb(0, 0, 0));

            Text add = new Text("ADDRESS : " + rs.getString(6));
            add.setX(posX);
            add.setFont(Font.font(20));
            add.setY(posY + 250);
            add.setFill(Color.rgb(0, 0, 0));

            Text ct = new Text("CITY : " + rs.getString(7));
            ct.setX(posX);
            ct.setFont(Font.font(20));
            ct.setY(posY + 300);
            ct.setFill(Color.rgb(0, 0, 0));

            Text type = new Text("hi");
            type.setFont(Font.font(20));
            if (rs.getString(8).equals("NO")) {
                type = new Text("User Type: " + "Not Employee");
            } else {
                type = new Text("User Type: " + "Employee");
            }
            type.setFont(Font.font(20));
            type.setX(posX);
            type.setY(posY + 350);
            type.setFill(Color.rgb(0, 0, 0));

            root.getChildren().add(Name);
            root.getChildren().add(nid);
            root.getChildren().add(db);
            root.getChildren().add(fn);
            root.getChildren().add(mn);
            root.getChildren().add(add);
            root.getChildren().add(ct);
            root.getChildren().add(type);

            pst.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("Log In Error!!");
        }
    }

    public static void SuccessMsg(String title, String msg) {
        ImageView img = new ImageView("file:Success.png");
        Notifications not = Notifications.create()
                .title(title)
                .text(msg)
                .graphic(img)
                .hideAfter(Duration.seconds(3));
        not.darkStyle();
        not.show();
    }

    public static void InvalidLogInMsg(String title, String msg) {
        Notifications not = Notifications.create()
                .title(title)
                .text(msg)
                .hideAfter(Duration.seconds(3));
        not.darkStyle();
        not.showError();
    }

    private void continueLogIn(TextField username, PasswordField password) {
        phoneno = username.getText();
        pass = password.getText();
        String sql = "SELECT NID_NO , TYPE_ID FROM SIM WHERE PHONE_NO = ?";
        try {
            Connection con = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, phoneno);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("success");
                passcode = rs.getString(1);
                SIM_Type = rs.getString(2);
                //SuccessMsg("Success", "LogIn Successful");
                pst.close();
                con.close();
                //LogIn(name, passcode);
            } else {
                InvalidLogInMsg("Failed", "No Such Phone no.");
            }
        } catch (SQLException ex) {
            System.out.println("doesn,t work");
        }
        String sql2 = "SELECT USER_PASSWORD,USER_NAME FROM USER_DATA WHERE NID_NO = ?";
        try {
            Connection con = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst = con.prepareStatement(sql2);
            pst.setString(1, passcode);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("success");
                givenpass = rs.getString(1);
                name = rs.getString(2);
                pst.close();
                con.close();
            } else {
                InvalidLogInMsg("Failed", "Invalid Log In");
            }
        } catch (SQLException ex) {
            System.out.println("doesn,t work");
        }
        if (pass.equals(givenpass)) {
            LogIn(name, passcode);
            SuccessMsg("Success", "LogIn Successful");
        } else {
            InvalidLogInMsg("Failed", "Invalid Log In");
        }
    }

}
