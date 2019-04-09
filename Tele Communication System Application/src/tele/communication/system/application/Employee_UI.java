package tele.communication.system.application;

/**
 *
 * @author HAROLD FINCH
 */
//import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class Employee_UI extends Application {

    public static Stage stage;
    public static Stage stage2;
    public static Group root;
    public static Scene scene;
    public static String name;
    public static String Job_ID;
    public static String passcode;
    public String jobtype;
    public String nid1;
    public String nid2;
    public String street;
    public String mail;
    public String city;
    public String basicsal;
    public String empid;
    public String billid;
    public String billsim;
    public String billcall;
    public String billsms;
    public String billin;
    public String totcost;

    ResultSet rs;
    Connection con;
    PreparedStatement pst;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.initModality(Modality.APPLICATION_MODAL);
        LoginScene();
    }

    public void go() throws Exception {
        stage2 = new Stage();
        start(stage2);
    }

    public static void BackSceneOnly() {
        root = new Group();
        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); 
        scene = new Scene(root, 1800, 900);
        scene.setFill(Color.rgb(255, 255, 255));//rgb(red, green, blue) // 396490 - torrentbd blue // 00666a torrentbd green
    }

    public static void StageShow() {
        stage.setTitle("EMPLOYEE UI");
        stage.setScene(scene);
        Image logo = new Image("file:comlogo.png");
        stage.getIcons().add(logo);
        stage.show();
    }

    private void LoginScene() {
        int posX = 350, posY = 300;
        int textFieldPosX = posX + 455, textFieldPosY = posY - 20;
        BackSceneOnly();
        ImageView toppart = new ImageView("file:tbdgreen.png");
        ImageView tbdlogin = new ImageView("file:tbdlogin.jpg");
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
        Text applytext = new Text(750, 635, "APPLY FOR A JOB");
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
            signupscene();
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

    public void continueLogIn(TextField username, PasswordField password) {
        name = username.getText();
        passcode = password.getText();
        String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_NAME = ? AND NID_NO = ?";
        try {
            con = new DataBase("TELECOM", "hr").getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, passcode);
            rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("success");
                empid = rs.getString(1);
                basicsal = rs.getString(15);
                SuccessMsg("Success", "LogIn Successful");
                pst.close();
                con.close();
                LogIn(name, passcode);
            } else {
                InvalidLogInMsg("Failed", "Invalid Log In");
            }
        } catch (SQLException ex) {
            System.out.println("doesn,t work");
        }
    }

    private void signupscene() {
        BackSceneOnly();
        ImageView back = new ImageView("file:mainback.jpg");
        root.getChildren().add(back);
        int posX = 100, posY = 185, diffy = 42, i = 1;
        int tx = posX + 350, ty = posY - 20;
        Text fillform = new Text("FILL THE FOLLOWING FIELDS CORRECTLY");
        fillform.setX(50);
        fillform.setY(posY - 15);
        fillform.setFont(Font.font(30));
        fillform.setFill(Color.rgb(255, 255, 255));
        root.getChildren().add(fillform);
        Text backtext = new Text("BACK");
        backtext.setX(1670);
        backtext.setY(posY - 15);
        backtext.setFont(Font.font(30));
        backtext.setFill(Color.rgb(0, 0, 0));
        root.getChildren().add(backtext);
        Text Name = new Text("NAME : ");
        SetThisText(Name, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fName = new TextField();
        SetThisField(fName, tx, ty + (diffy * (i - 1)));
        Text fn = new Text("FATHER'S NAME : ");
        SetThisText(fn, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField ffn = new TextField();
        SetThisField(ffn, tx, ty + (diffy * (i - 1)));
        Text mn = new Text("MOTHER'S NAME : ");
        SetThisText(mn, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fmn = new TextField();
        SetThisField(fmn, tx, ty + (diffy * (i - 1)));
        Text bd = new Text("BIRTH DATE (MMM DD, YYYY): ");
        SetThisText(bd, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fbd = new TextField();
        SetThisField(fbd, tx, ty + (diffy * (i - 1)));
        Text st = new Text("STREET ADDRESS : ");
        SetThisText(st, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fst = new TextField();
        SetThisField(fst, tx, ty + (diffy * (i - 1)));
        Text ct = new Text("CITY : ");
        SetThisText(ct, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        ChoiceBox<String> chct = new ChoiceBox<String>();
        chct.getItems().add("Dhaka");
        chct.getItems().add("Chittagong");
        chct.getItems().add("Rajshahi");
        chct.getItems().add("Sylhet");
        chct.getItems().add("Rangpur");
        chct.getItems().add("Barisal");
        chct.getItems().add("Khulna");
        chct.setValue("Dhaka");
        chct.setScaleX(2.3);

        chct.relocate(tx + 33, ty + (diffy * (i - 1)));
        root.getChildren().add(chct);
        //TextField fct = new TextField();
        //SetThisField(fct, tx, ty + (diffy * (i - 1)));
        Text nid = new Text("NID : ");
        SetThisText(nid, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fnid = new TextField();
        SetThisField(fnid, tx, ty + (diffy * (i - 1)));
        Text mail = new Text("EMAIL : ");
        SetThisText(mail, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fmail = new TextField();
        SetThisField(fmail, tx, ty + (diffy * (i - 1)));
        Text acc = new Text("ACC NO : ");
        SetThisText(acc, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField facc = new TextField();
        SetThisField(facc, tx, ty + (diffy * (i - 1)));
        Text ssc = new Text("SSC PASSING YEAR : ");
        SetThisText(ssc, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fssc = new TextField();
        SetThisField(fssc, tx, ty + (diffy * (i - 1)));
        Text sscg = new Text("GPA OF SSC : ");
        SetThisText(sscg, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fsscg = new TextField();
        SetThisField(fsscg, tx, ty + (diffy * (i - 1)));
        Text hsc = new Text("HSC PASSING YEAR : ");
        SetThisText(hsc, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fhsc = new TextField();
        SetThisField(fhsc, tx, ty + (diffy * (i - 1)));
        Text hscg = new Text("GPA OF HSC : ");
        SetThisText(hscg, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fhscg = new TextField();
        SetThisField(fhscg, tx, ty + (diffy * (i - 1)));
        Text uni = new Text("UNIVERSITY : ");
        SetThisText(uni, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField funi = new TextField();
        SetThisField(funi, tx, ty + (diffy * (i - 1)));
        Text unicg = new Text("CGPA OF UNIVERSITY : ");
        SetThisText(unicg, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField funicg = new TextField();
        SetThisField(funicg, tx, ty + (diffy * (i - 1)));
        Text exp = new Text("EXPERIENCE : ");
        SetThisText(exp, posX, posY + (diffy * (i++)), 20, Color.BLACK);
        TextField fexp = new TextField();
        SetThisField(fexp, tx, ty + (diffy * (i - 1)));
        Text jt = new Text("DESIRED JOB TYPE : ");
        SetThisText(jt, posX + 800, posY + 42, 20, Color.BLACK);
        final ToggleGroup p = new ToggleGroup();
        RadioButton rb1 = new RadioButton("ADMINISTRATOR");
        rb1.setToggleGroup(p);
        rb1.setFont(Font.font(20));
        rb1.setUserData("101");
        rb1.setLayoutX(posX + 1000);
        rb1.setLayoutY(posY + 20);
        RadioButton rb2 = new RadioButton("ENGINEER");
        rb2.setToggleGroup(p);
        rb2.setFont(Font.font(20));
        rb2.setUserData("102");
        rb2.setLayoutX(posX + 1000);
        rb2.setLayoutY(posY + 60);
        RadioButton rb3 = new RadioButton("HUMAN RESOURCES REPRESENTATIVE");
        rb3.setToggleGroup(p);
        rb3.setFont(Font.font(20));
        rb3.setUserData("103");
        rb3.setLayoutX(posX + 1000);
        rb3.setLayoutY(posY + 100);
        RadioButton rb4 = new RadioButton("ACCOUNTING MANAGER");
        rb4.setToggleGroup(p);
        rb4.setFont(Font.font(20));
        rb4.setUserData("104");
        rb4.setLayoutX(posX + 1000);
        rb4.setLayoutY(posY + 140);
        RadioButton rb5 = new RadioButton("CUSTOMER CARE");
        rb5.setToggleGroup(p);
        rb5.setFont(Font.font(20));
        rb5.setUserData("105");
        rb5.setLayoutX(posX + 1000);
        rb5.setLayoutY(posY + 180);
        p.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) -> {
            if (p.getSelectedToggle() != null) {
                jobtype = new String();
                jobtype = p.getSelectedToggle().getUserData().toString();
            }
        });
        root.getChildren().add(rb1);
        root.getChildren().add(rb2);
        root.getChildren().add(rb3);
        root.getChildren().add(rb4);
        root.getChildren().add(rb5);
        ImageView backpage = new ImageView("file:previous.png");
        backpage.setX(1600);
        backpage.setY(130);
        backpage.setFitHeight(60);
        backpage.setFitWidth(60);
        root.getChildren().add(backpage);
        Rectangle backbox = new Rectangle(155, 50);
        backbox.setFill(Color.rgb(0, 0, 0, 0));
        backbox.setX(1600);
        backbox.setY(135);
        root.getChildren().add(backbox);
        backbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            backtext.setFont(Font.font(40));
            backtext.setX(1665);
            backtext.setY(posY - 12);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            backtext.setFont(Font.font(30));
            backtext.setX(1670);
            backtext.setY(posY - 15);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            LoginScene();
        });
        Text applytext = new Text("APPLY");
        applytext.setX(1130);
        applytext.setY(450);
        applytext.setFill(Color.WHITE);
        applytext.setFont(Font.font(35));
        Rectangle applyboxdown = new Rectangle(150, 50);
        applyboxdown.setFill(Color.rgb(32, 155, 146));
        applyboxdown.setX(1105);
        applyboxdown.setY(412);
        Rectangle applyboxup = new Rectangle(150, 50);
        applyboxup.setFill(Color.rgb(0, 0, 0, 0));
        applyboxup.setX(1105);
        applyboxup.setY(412);
        applyboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            applyboxdown.setFill(Color.rgb(17, 119, 112));
        });
        applyboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            applyboxdown.setFill(Color.rgb(32, 155, 146));
        });
        applyboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            String sql = "INSERT INTO PENDING_JOB_REQ (EMP_NAME,FATHER_NAME,"
                    + "MOTHER_NAME,BIRTH_DATE,STREET_ADDRESS,CITY,NID_NO,"
                    + "EMAIL,ACC_NO,SSC_YEAR,SSC_GPA,HSC_YEAR,HSC_GPA,UNIVERSITY,"
                    + "CGPA,EXPERIENCE,JOB_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            try {
                con = new DataBase("TELECOM", "hr").getConnection();
                pst = con.prepareStatement(sql);
                System.out.println(fName.getText());
                pst.setString(1, fName.getText());
                pst.setString(2, ffn.getText());
                pst.setString(3, fmn.getText());
                pst.setString(4, fbd.getText());
                pst.setString(5, fst.getText());
                pst.setString(6, chct.getValue());
                pst.setString(7, fnid.getText());
                pst.setString(8, fmail.getText());
                pst.setString(9, facc.getText());
                pst.setString(10, fssc.getText());
                pst.setString(11, fsscg.getText());
                pst.setString(12, fhsc.getText());
                pst.setString(13, fhscg.getText());
                pst.setString(14, funi.getText());
                pst.setString(15, funicg.getText());
                pst.setString(16, fexp.getText());
                pst.setString(17, jobtype);
                pst.executeQuery();
                SuccessMsg("Success", "Successfully Applied for the job");
                pst.close();
                con.close();
            } catch (SQLException ex) {
                InvalidLogInMsg("Failed", "Wrong Credential");
                //printStackTrace();
            }
        });
        ImageView toppart = new ImageView("file:tbdgreen.png");
        root.getChildren().add(toppart);
        root.getChildren().add(applyboxdown);
        root.getChildren().add(applytext);
        root.getChildren().add(applyboxup);
        StageShow();
    }

    public void LogIn(String User_Name, String NID_No) {
        BackSceneOnly();
        ImageView back = new ImageView("file:mainback.jpg");
        root.getChildren().add(back);
        int move = 175;
        double tabpaneX = 45, tabpaneY = 150;

        Profile(User_Name, NID_No); // HomePage of User = User Profile
        ImageView profileimg = new ImageView("file:profile.png");
        root.getChildren().add(profileimg);
        profileimg.relocate(tabpaneX, tabpaneY);
        profileimg.setFitHeight(40);
        profileimg.setFitWidth(160);
        Text profiletext = new Text("PROFILE");
        SetThisText(profiletext, 85, 175, 20);
        Rectangle profilebox = new Rectangle(150, 40);
        SetThisBox(profilebox, 50, 170);
        //profilebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {profilebox.setFill(Color.rgb(255, 255, 255, 0.1));});
        //profilebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {profilebox.setFill(Color.rgb(255, 255, 255, 0));});
        profilebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Profile(User_Name, NID_No);
            root.getChildren().add(profileimg);
            root.getChildren().remove(profiletext);
            root.getChildren().add(profiletext);
        });
        ImageView approveimg = new ImageView("file:profile.png");
        //root.getChildren().add(logoutimg);
        approveimg.relocate(tabpaneX + (move * 1), tabpaneY);
        approveimg.setFitHeight(40);
        approveimg.setFitWidth(160);
        Text approvetext = new Text("ISSUE SIM");
        SetThisText(approvetext, 80 + (move * 1), 175, 20);
        Rectangle approvebox = new Rectangle(150, 40);
        SetThisBox(approvebox, 50 + (move * 1), 170);
        approvebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            approvebox.setFill(Color.rgb(255, 255, 255, 0.1));
        });
        approvebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            approvebox.setFill(Color.rgb(255, 255, 255, 0));
        });
        approvebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                ApproveScene(User_Name, NID_No);
            } catch (SQLException ex) {
                Logger.getLogger(Employee_UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ImageView jobapproveimg = new ImageView("file:profile.png");
        //root.getChildren().add(logoutimg);
        jobapproveimg.relocate(tabpaneX + (move * 2), tabpaneY);
        jobapproveimg.setFitHeight(40);
        jobapproveimg.setFitWidth(160);
        Text jobapprovetext = new Text("JOB REQ");
        SetThisText(jobapprovetext, 80 + (move * 2), 175, 20);
        Rectangle jobapprovebox = new Rectangle(150, 40);
        SetThisBox(jobapprovebox, 50 + (move * 2), 170);
        jobapprovebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            jobapprovebox.setFill(Color.rgb(255, 255, 255, 0.1));
        });
        jobapprovebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            jobapprovebox.setFill(Color.rgb(255, 255, 255, 0));
        });
        jobapprovebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                jobApproveScene(User_Name, NID_No);
            } catch (SQLException ex) {
                Logger.getLogger(Employee_UI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        ImageView logoutimg = new ImageView("file:profile.png");
        //root.getChildren().add(logoutimg);
        logoutimg.relocate(tabpaneX + (move * 3), tabpaneY);
        logoutimg.setFitHeight(40);
        logoutimg.setFitWidth(160);
        Text logouttext = new Text("LOG OUT");
        SetThisText(logouttext, 85 + (move * 3), 175, 20);
        Rectangle logoutbox = new Rectangle(150, 40);
        SetThisBox(logoutbox, 50 + (move * 3), 170);
        logoutbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            logoutbox.setFill(Color.rgb(255, 255, 255, 0.1));
        });
        logoutbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            logoutbox.setFill(Color.rgb(255, 255, 255, 0));
        });
        logoutbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> LogOut());
        ImageView toppart = new ImageView("file:tbdgreen.png");
        root.getChildren().add(toppart);
        StageShow();
    }

    void LogOut() {
        LoginScene();
        SuccessMsg("Success", "Log Out Successful");
    }

    public void Profile(String User_Name, String NID_No) {
        userprofile(User_Name, NID_No);
        empprofile(User_Name, NID_No);
    }

    public void userprofile(String User_Name, String NID_No) {
        try {
            String sql = "SELECT * FROM USER_DATA WHERE USER_NAME = ? AND NID_NO = ?";
            int posX = 100, posY = 270, fonts = 20;
            con = new DataBase("TELECOM", "hr").getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, User_Name);
            pst.setString(2, NID_No);
            rs = pst.executeQuery();
            rs.next();
            Text Name = new Text("USER NAME : " + rs.getString(1));
            Name.setX(posX);
            Name.setY(posY);
            Name.setFont(Font.font(fonts));
            Name.setFill(Color.rgb(0, 0, 0));
            Text nid = new Text("NID NO : " + rs.getString(2));
            nid.setX(posX);
            nid.setY(posY + 50);
            nid.setFont(Font.font(fonts));
            nid.setFill(Color.rgb(0, 0, 0));
            Text db = new Text("DATE OF BIRTH (MMM DD, YYYY) : " + rs.getString(3));
            db.setX(posX);
            db.setY(posY + 100);
            db.setFont(Font.font(fonts));
            db.setFill(Color.rgb(0, 0, 0));
            Text fn = new Text("FATHER'S NAME : " + rs.getString(4));
            fn.setX(posX);
            fn.setY(posY + 150);
            fn.setFont(Font.font(fonts));
            fn.setFill(Color.rgb(0, 0, 0));
            Text mn = new Text("MOTHER'S NAME : " + rs.getString(5));
            mn.setX(posX);
            mn.setY(posY + 200);
            mn.setFont(Font.font(fonts));
            mn.setFill(Color.rgb(0, 0, 0));
            Text add = new Text("ADRESS : " + rs.getString(6));
            add.setX(posX);
            add.setY(posY + 250);
            add.setFont(Font.font(fonts));
            add.setFill(Color.rgb(0, 0, 0));
            Text ct = new Text("CITY : " + rs.getString(7));
            ct.setX(posX);
            ct.setY(posY + 300);
            ct.setFont(Font.font(fonts));
            ct.setFill(Color.rgb(0, 0, 0));
            Text type;
            if (rs.getString(8).equals("NO")) {
                type = new Text("User Type: " + "Not Employee");
            } else {
                type = new Text("User Type: " + "Employee");
            }
            type.setFont(Font.font(fonts));
            type.setX(posX);
            type.setY(posY + 350);
            type.setFill(Color.rgb(0, 0, 0));

            Text applytext = new Text("APPLY");
            applytext.setX(posX + 25);
            applytext.setY(posY + 450);
            applytext.setFill(Color.WHITE);
            applytext.setFont(Font.font(35));
            Rectangle applyboxdown = new Rectangle(150, 50);
            applyboxdown.setFill(Color.rgb(32, 155, 146));
            applyboxdown.setX(posX);
            applyboxdown.setY(posY + 412);
            Rectangle applyboxup = new Rectangle(150, 50);
            applyboxup.setFill(Color.rgb(0, 0, 0, 0));
            applyboxup.setX(posX);
            applyboxup.setY(posY + 412);
            applyboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                applyboxdown.setFill(Color.rgb(17, 119, 112));
            });
            applyboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                applyboxdown.setFill(Color.rgb(32, 155, 146));
            });
            TextField newstf = new TextField();
            TextField newctf = new TextField();

            applyboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                street = newstf.getText();
                city = newctf.getText();
                String sql2 = "UPDATE USER_DATA SET STREET_ADDRESS = ?, CITY = ? WHERE NID_NO = ?";
                try {
                    Connection con2 = new DataBase("TELECOM", "hr").getConnection();
                    PreparedStatement pst2 = con2.prepareStatement(sql2);
                    pst2.setString(1, street);
                    pst2.setString(2, city);
                    pst2.setString(3, NID_No);
                    pst2.executeQuery();
                    SuccessMsg("Success", "Please Log In Again to View Changes.");
                    LoginScene();
                    pst2.close();
                    con2.close();
                } catch (SQLException ex) {
                    InvalidLogInMsg("Failed", "Wrong Credential");
                    //printStackTrace();
                }
            });

            Text canceltext = new Text("CANCEL");
            canceltext.setX(posX + 10 + 180);
            canceltext.setY(posY + 450);
            canceltext.setFill(Color.WHITE);
            canceltext.setFont(Font.font(35));
            Rectangle cancelboxdown = new Rectangle(150, 50);
            cancelboxdown.setFill(Color.rgb(32, 155, 146));
            cancelboxdown.setX(posX + 180);
            cancelboxdown.setY(posY + 412);
            Rectangle cancelboxup = new Rectangle(150, 50);
            cancelboxup.setFill(Color.rgb(0, 0, 0, 0));
            cancelboxup.setX(posX + 180);
            cancelboxup.setY(posY + 412);
            cancelboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                cancelboxdown.setFill(Color.rgb(17, 119, 112));
            });
            cancelboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                cancelboxdown.setFill(Color.rgb(32, 155, 146));
            });
            Text newst = new Text("Street :");
            Text newct = new Text("City :");
            Text chadd = new Text("Change Address");
            Rectangle chaddbox = new Rectangle(110, 15);
            cancelboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                root.getChildren().add(chadd);
                root.getChildren().add(chaddbox);
                root.getChildren().add(add);
                root.getChildren().add(ct);
                root.getChildren().remove(newct);
                root.getChildren().remove(newst);
                root.getChildren().remove(newstf);
                root.getChildren().remove(newctf);
                root.getChildren().remove(applyboxdown);
                root.getChildren().remove(applytext);
                root.getChildren().remove(applyboxup);
                root.getChildren().remove(cancelboxdown);
                root.getChildren().remove(canceltext);
                root.getChildren().remove(cancelboxup);
            });

            newst.setFill(Color.BLACK);
            newst.setX(posX);
            newst.setY(posY + 250);
            newst.setFont(Font.font(20));
            newct.setFill(Color.BLACK);
            newct.setX(posX);
            newct.setY(posY + 300);
            newct.setFont(Font.font(20));
            SetThisFieldvir(newstf, posX + 200, posY + 230);
            SetThisFieldvir(newctf, posX + 200, posY + 280);

            chadd.setFill(Color.BLUE);
            chadd.setX(posX);
            chadd.setY(posY + 412);
            chadd.setFont(Font.font(15));

            chaddbox.setFill(Color.rgb(0, 0, 0, 0));
            chaddbox.relocate(posX, posY + 400);
            chaddbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                chadd.setFill(Color.BLUEVIOLET);
            });
            chaddbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                chadd.setFill(Color.BLUE);
            });
            chaddbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                root.getChildren().remove(chadd);
                root.getChildren().remove(chaddbox);
                root.getChildren().remove(add);
                root.getChildren().remove(ct);
                root.getChildren().add(newct);
                root.getChildren().add(newst);
                root.getChildren().add(newstf);
                root.getChildren().add(newctf);
                root.getChildren().add(applyboxdown);
                root.getChildren().add(applytext);
                root.getChildren().add(applyboxup);
                root.getChildren().add(cancelboxdown);
                root.getChildren().add(canceltext);
                root.getChildren().add(cancelboxup);
            });

            root.getChildren().add(chadd);
            root.getChildren().add(chaddbox);
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
        } catch (SQLException ex) {
            System.out.println("USER DATA ERROR!!");
        }
    }

    public void empprofile(String User_Name, String NID_No) {
        try {
            String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_NAME = ? AND NID_NO = ?";
            int posX = 800, posY = 270, fonts = 20;
            con = new DataBase("TELECOM", "hr").getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, User_Name);
            pst.setString(2, NID_No);
            rs = pst.executeQuery();
            rs.next();
            Job_ID = rs.getString(14);
            Text id = new Text("EMPLOYEE ID : " + rs.getString(1));
            id.setX(posX);
            id.setY(posY);
            id.setFont(Font.font(fonts));
            id.setFill(Color.rgb(0, 0, 0));
            Text email = new Text("EMAIL ID : " + rs.getString(4));
            email.setX(posX);
            email.setY(posY + 50);
            email.setFont(Font.font(fonts));
            email.setFill(Color.rgb(0, 0, 0));
            Text ac = new Text("BANK ACC NO : " + rs.getString(5));
            ac.setX(posX);
            ac.setY(posY + 100);
            ac.setFont(Font.font(fonts));
            ac.setFill(Color.rgb(0, 0, 0));
            Text sy = new Text("SSC YEAR : " + rs.getString(7));
            sy.setX(posX);
            sy.setY(posY + 150);
            sy.setFont(Font.font(fonts));
            sy.setFill(Color.rgb(0, 0, 0));
            Text sg = new Text("SSC GPA : " + rs.getString(8));
            sg.setX(posX);
            sg.setY(posY + 200);
            sg.setFont(Font.font(fonts));
            sg.setFill(Color.rgb(0, 0, 0));
            Text hy = new Text("HSC YEAR : " + rs.getString(9));
            hy.setX(posX);
            hy.setY(posY + 250);
            hy.setFont(Font.font(fonts));
            hy.setFill(Color.rgb(0, 0, 0));
            Text hg = new Text("HSC GPA : " + rs.getString(10));
            hg.setX(posX);
            hg.setY(posY + 300);
            hg.setFont(Font.font(fonts));
            hg.setFill(Color.rgb(0, 0, 0));
            Text uni = new Text("UNIVERSITY : " + rs.getString(11));
            uni.setX(posX);
            uni.setY(posY + 350);
            uni.setFont(Font.font(fonts));
            uni.setFill(Color.rgb(0, 0, 0));
            Text ug = new Text("CGPA : " + rs.getString(12));
            ug.setX(posX);
            ug.setY(posY + 400);
            ug.setFont(Font.font(fonts));
            ug.setFill(Color.rgb(0, 0, 0));
            Text sal = new Text("SALARY : " + rs.getString(15) + " tk/-");
            sal.setX(posX);
            sal.setY(posY + 450);
            sal.setFont(Font.font(fonts));
            sal.setFill(Color.rgb(0, 0, 0));

            Rectangle chmailbox = new Rectangle(110, 15);
            Text chmail = new Text("Change Mail.");
            Text newm = new Text("Mail :");

            Text mybill = new Text("Show My Payslip");
            mybill.setX(posX + 10 + 600);
            mybill.setY(posY + 570);
            mybill.setFill(Color.WHITE);
            mybill.setFont(Font.font(35));
            Rectangle mybillboxdown = new Rectangle(280, 50);
            mybillboxdown.setFill(Color.rgb(32, 155, 146));
            mybillboxdown.setX(posX + 600);
            mybillboxdown.setY(posY + 532);
            Rectangle mybillboxup = new Rectangle(280, 50);
            mybillboxup.setFill(Color.rgb(0, 0, 0, 0));
            mybillboxup.setX(posX + 600);
            mybillboxup.setY(posY + 532);
            root.getChildren().add(mybillboxdown);
            root.getChildren().add(mybill);
            root.getChildren().add(mybillboxup);
            mybillboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                mybillboxdown.setFill(Color.rgb(17, 119, 112));
            });
            mybillboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                mybillboxdown.setFill(Color.rgb(32, 155, 146));
            });

            mybillboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                MybillScene(User_Name, NID_No);
            });

            Text Admtext = new Text("ISSUE BILL");
            Admtext.setX(posX + 50 + 600);
            Admtext.setY(posY + 500);
            Admtext.setFill(Color.WHITE);
            Admtext.setFont(Font.font(35));
            Rectangle admboxdown = new Rectangle(280, 50);
            admboxdown.setFill(Color.rgb(32, 155, 146));
            admboxdown.setX(posX + 600);
            admboxdown.setY(posY + 462);
            Rectangle admboxup = new Rectangle(280, 50);
            admboxup.setFill(Color.rgb(0, 0, 0, 0));
            admboxup.setX(posX + 600);
            admboxup.setY(posY + 462);
            if ("101".equals(Job_ID)) {
                root.getChildren().add(admboxdown);
                root.getChildren().add(Admtext);
                root.getChildren().add(admboxup);
            }
            admboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                admboxdown.setFill(Color.rgb(17, 119, 112));
            });
            admboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                admboxdown.setFill(Color.rgb(32, 155, 146));
            });

            admboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                AdmScene(User_Name, NID_No);
            });

            Text Admtext2 = new Text("Create Report");
            Admtext2.setX(posX + 25 + 600);
            Admtext2.setY(posY + 430);
            Admtext2.setFill(Color.WHITE);
            Admtext2.setFont(Font.font(35));
            Rectangle admboxdown2 = new Rectangle(280, 50);
            admboxdown2.setFill(Color.rgb(32, 155, 146));
            admboxdown2.setX(posX + 600);
            admboxdown2.setY(posY + 392);
            Rectangle admboxup2 = new Rectangle(280, 50);
            admboxup2.setFill(Color.rgb(0, 0, 0, 0));
            admboxup2.setX(posX + 600);
            admboxup2.setY(posY + 392);
            if ("101".equals(Job_ID)) {
                root.getChildren().add(admboxdown2);
                root.getChildren().add(Admtext2);
                root.getChildren().add(admboxup2);
            }
            admboxup2.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                admboxdown2.setFill(Color.rgb(17, 119, 112));
            });
            admboxup2.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                admboxdown2.setFill(Color.rgb(32, 155, 146));
            });

            admboxup2.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                report(User_Name, NID_No);
            });

            Text applytext = new Text("APPLY");
            applytext.setX(posX + 25);
            applytext.setY(posY + 550);
            applytext.setFill(Color.WHITE);
            applytext.setFont(Font.font(35));
            Rectangle applyboxdown = new Rectangle(150, 50);
            applyboxdown.setFill(Color.rgb(32, 155, 146));
            applyboxdown.setX(posX);
            applyboxdown.setY(posY + 512);
            Rectangle applyboxup = new Rectangle(150, 50);
            applyboxup.setFill(Color.rgb(0, 0, 0, 0));
            applyboxup.setX(posX);
            applyboxup.setY(posY + 512);
            applyboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                applyboxdown.setFill(Color.rgb(17, 119, 112));
            });
            applyboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                applyboxdown.setFill(Color.rgb(32, 155, 146));
            });
            TextField newstf = new TextField();
            TextField newctf = new TextField();

            applyboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                mail = newstf.getText();
                String sql2 = "UPDATE EMPLOYEES SET EMAIL = ? WHERE NID_NO = ?";
                try {
                    Connection con2 = new DataBase("TELECOM", "hr").getConnection();
                    PreparedStatement pst2 = con2.prepareStatement(sql2);
                    pst2.setString(1, mail);
                    pst2.setString(2, NID_No);
                    pst2.executeQuery();
                    SuccessMsg("Success", "Please Log In Again to View Changes.");
                    LoginScene();
                    pst2.close();
                    con2.close();
                } catch (SQLException ex) {
                    InvalidLogInMsg("Failed", "Wrong Credential");
                    //printStackTrace();
                }
            });

            Text canceltext = new Text("CANCEL");
            canceltext.setX(posX + 10 + 180);
            canceltext.setY(posY + 550);
            canceltext.setFill(Color.WHITE);
            canceltext.setFont(Font.font(35));
            Rectangle cancelboxdown = new Rectangle(150, 50);
            cancelboxdown.setFill(Color.rgb(32, 155, 146));
            cancelboxdown.setX(posX + 180);
            cancelboxdown.setY(posY + 512);
            Rectangle cancelboxup = new Rectangle(150, 50);
            cancelboxup.setFill(Color.rgb(0, 0, 0, 0));
            cancelboxup.setX(posX + 180);
            cancelboxup.setY(posY + 512);
            cancelboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                cancelboxdown.setFill(Color.rgb(17, 119, 112));
            });
            cancelboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                cancelboxdown.setFill(Color.rgb(32, 155, 146));
            });
            TextField newmf = new TextField();
            cancelboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                root.getChildren().add(email);
                root.getChildren().remove(newm);
                root.getChildren().remove(newmf);
                root.getChildren().add(chmail);
                root.getChildren().add(chmailbox);
                root.getChildren().remove(applyboxdown);
                root.getChildren().remove(applytext);
                root.getChildren().remove(applyboxup);
                root.getChildren().remove(cancelboxdown);
                root.getChildren().remove(canceltext);
                root.getChildren().remove(cancelboxup);
            });
            SetThisFieldvir(newmf, posX + 200, posY + 30);
            newm.setFill(Color.BLACK);
            newm.setX(posX);
            newm.setY(posY + 50);
            newm.setFont(Font.font(20));
            chmail.setX(posX);
            chmail.setY(posY + 512);
            chmail.setFont(Font.font(15));
            chmail.setFill(Color.BLUE);

            chmailbox.setFill(Color.rgb(0, 0, 0, 0));
            chmailbox.relocate(posX, posY + 500);
            chmailbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                chmail.setFill(Color.BLUEVIOLET);
            });
            chmailbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                chmail.setFill(Color.BLUE);
            });
            chmailbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                root.getChildren().remove(email);
                root.getChildren().add(newm);
                root.getChildren().add(newmf);
                root.getChildren().remove(chmail);
                root.getChildren().remove(chmailbox);
                root.getChildren().add(applyboxdown);
                root.getChildren().add(applytext);
                root.getChildren().add(applyboxup);
                root.getChildren().add(cancelboxdown);
                root.getChildren().add(canceltext);
                root.getChildren().add(cancelboxup);
            });

            root.getChildren().add(chmail);
            root.getChildren().add(chmailbox);
            root.getChildren().add(id);
            root.getChildren().add(email);
            root.getChildren().add(ac);
            root.getChildren().add(sy);
            root.getChildren().add(sg);
            root.getChildren().add(hy);
            root.getChildren().add(hg);
            root.getChildren().add(uni);
            root.getChildren().add(ug);
            root.getChildren().add(sal);
            pst.close();
            con.close();
        } catch (SQLException ex) {
            System.out.println("EMPLOYYE DATA ERROR!!");
        }
    }

    private void ApproveScene(String User_Name, String NID_No) throws SQLException {
        BackSceneOnly();
        ImageView back = new ImageView("file:mainback.jpg");
        root.getChildren().add(back);
        int buttonPosX = 300, buttonPosY = 150, move = 175;
        double tabpaneX = 45, tabpaneY = 150;

        String sql = "SELECT * FROM PENDING_SIM_REQ WHERE ROWNUM = 1";
        try {
            con = new DataBase("TELECOM", "hr").getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            System.out.println("success");
            //rs.next();
            //System.out.println("Boro Mara");
            boolean x;
            x = false;
            if (rs.next()) {
                x = true;
                nid1 = rs.getString(2);
            }
            if (x) {
                pending_profile();
                System.out.println("success");
            } else {
                InvalidLogInMsg("Failed", "No More Sim Req");
            }
            //Profile(User_Name, NID_No); // HomePage of User = User Profile
            ImageView profileimg = new ImageView("file:profile.png");
            //root.getChildren().add(profileimg);
            profileimg.relocate(tabpaneX, tabpaneY);
            profileimg.setFitHeight(40);
            profileimg.setFitWidth(160);
            Text profiletext = new Text("PROFILE");
            SetThisText(profiletext, 85, 175, 20);
            Rectangle profilebox = new Rectangle(150, 40);
            SetThisBox(profilebox, 50, 170);
            profilebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                profilebox.setFill(Color.rgb(255, 255, 255, 0.1));
            });
            profilebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                profilebox.setFill(Color.rgb(255, 255, 255, 0));
            });
            profilebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                LogIn(User_Name, NID_No);
            });

            ImageView approveimg = new ImageView("file:profile.png");
            root.getChildren().add(approveimg);
            approveimg.relocate(tabpaneX + (move * 1), tabpaneY);
            approveimg.setFitHeight(40);
            approveimg.setFitWidth(160);
            Text approvetext = new Text("ISSUE SIM");
            SetThisText(approvetext, 80 + (move * 1), 175, 20);
            Rectangle approvebox = new Rectangle(150, 40);
            SetThisBox(approvebox, 50 + (move * 1), 170);
            //approvebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {approvebox.setFill(Color.rgb(255, 255, 255, 0.1));});
            //approvebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {approvebox.setFill(Color.rgb(255, 255, 255, 0));});
            //approvebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> ApproveScene(User_Name, NID_No));

            ImageView jobapproveimg = new ImageView("file:profile.png");
            //root.getChildren().add(logoutimg);
            jobapproveimg.relocate(tabpaneX + (move * 2), tabpaneY);
            jobapproveimg.setFitHeight(40);
            jobapproveimg.setFitWidth(160);
            Text jobapprovetext = new Text("JOB REQ");
            SetThisText(jobapprovetext, 80 + (move * 2), 175, 20);
            Rectangle jobapprovebox = new Rectangle(150, 40);
            SetThisBox(jobapprovebox, 50 + (move * 2), 170);
            jobapprovebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                jobapprovebox.setFill(Color.rgb(255, 255, 255, 0.1));
            });
            jobapprovebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                jobapprovebox.setFill(Color.rgb(255, 255, 255, 0));
            });
            jobapprovebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                try {
                    jobApproveScene(User_Name, NID_No);
                } catch (SQLException ex) {
                    Logger.getLogger(Employee_UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            ImageView logoutimg = new ImageView("file:profile.png");
            //root.getChildren().add(logoutimg);
            logoutimg.relocate(tabpaneX + (move * 3), tabpaneY);
            logoutimg.setFitHeight(40);
            logoutimg.setFitWidth(160);
            Text logouttext = new Text("LOG OUT");
            SetThisText(logouttext, 85 + (move * 3), 175, 20);
            Rectangle logoutbox = new Rectangle(150, 40);
            SetThisBox(logoutbox, 50 + (move * 3), 170);
            logoutbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                logoutbox.setFill(Color.rgb(255, 255, 255, 0.1));
            });
            logoutbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                logoutbox.setFill(Color.rgb(255, 255, 255, 0));
            });
            logoutbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> LogOut());

            Rectangle confirmboxup = new Rectangle(200, 60);
            Text confirmtext = new Text("APPROVE");
            confirmtext.setX(1015);
            confirmtext.setY(343);
            confirmtext.setFill(Color.WHITE);
            confirmtext.setFont(Font.font(40));
            Rectangle confirmboxdown = new Rectangle(200, 60);
            confirmboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            confirmboxdown.setX(1000);
            confirmboxdown.setY(300);
            confirmboxup.setFill(Color.rgb(0, 0, 0, 0));
            confirmboxup.setX(1000);
            confirmboxup.setY(300);
            confirmboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                confirmboxdown.setFill(Color.rgb(17, 119, 112, 1));
            });
            confirmboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                confirmboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            });
            //rs.next();
            //System.out.println(rs.getString(1));

            confirmboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                try {

                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con2 = new DataBase("TELECOM", "hr").getConnection();

                    CallableStatement stmt = con2.prepareCall("{call APPROVE_THIS_PENDING_SIM(?,?)}");

                    stmt.setInt(1, Integer.parseInt(Job_ID));
                    stmt.setInt(2, Integer.parseInt(nid1));

                    System.out.println(nid1 + " jsgdfkhaj " + passcode);
                    System.out.println("execute hoitese na");
                    stmt.executeUpdate();
                    System.out.println("execute hoise re!!!!");

                    stmt.close();
                    con2.close();

                    ApproveScene(User_Name, NID_No);

                    SuccessMsg("Approved", "This SIM Was Issued By Employee No." + passcode);
                } catch (SQLException ex2) {
                    System.out.println("doesn't work");
                    InvalidLogInMsg("Approve Failed", "Didn't Work");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Employee_UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            Rectangle disapproveboxup = new Rectangle(200, 60);
            Text disapprovetext = new Text("DELETE");
            disapprovetext.setX(1035);
            disapprovetext.setY(443);
            disapprovetext.setFill(Color.WHITE);
            disapprovetext.setFont(Font.font(40));
            Rectangle disapproveboxdown = new Rectangle(200, 60);
            disapproveboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            disapproveboxdown.setX(1000);
            disapproveboxdown.setY(400);
            disapproveboxup.setFill(Color.rgb(0, 0, 0, 0));
            disapproveboxup.setX(1000);
            disapproveboxup.setY(400);
            disapproveboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                disapproveboxdown.setFill(Color.rgb(17, 119, 112, 1));
            });
            disapproveboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                disapproveboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            });
            //rs.next();
            //System.out.println(rs.getString(1));

            disapproveboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                String sql2 = "DELETE FROM PENDING_SIM_REQ WHERE NID_NO = ?";
                try {
                    Connection con2 = new DataBase("TELECOM", "hr").getConnection();
                    PreparedStatement pst2 = con2.prepareStatement(sql2);
                    pst2.setString(1, nid1);
                    //ResultSet rs2 = pst2.executeQuery();
                    //if (rs2.next()) {

                    //} else {
                    //    InvalidLogInMsg("Failed", "Invalid Log In");
                    //}
                    pst2.executeQuery();
                    pst2.close();
                    con2.close();
                    ApproveScene(User_Name, NID_No);
                } catch (SQLException ex2) {
                    System.out.println("doesn't work");
                }
            });

            if (x) {
                root.getChildren().add(confirmboxdown);
                root.getChildren().add(confirmtext);
                root.getChildren().add(confirmboxup);
                root.getChildren().add(disapproveboxdown);
                root.getChildren().add(disapprovetext);
                root.getChildren().add(disapproveboxup);

            }
            ImageView toppart = new ImageView("file:tbdgreen.png");
            root.getChildren().add(toppart);
            pst.close();
            con.close();
            StageShow();
        } catch (SQLException ex) {
            System.out.println("doesn't work");
        }
    }

    private void pending_profile() throws SQLException {
        int posX = 100, posY = 270, fonts = 20;
        Text Name = new Text("USER NAME : " + rs.getString(1));
        System.out.println(rs.getString(1));
        Name.setX(posX);
        Name.setY(posY);
        Name.setFont(Font.font(fonts));
        Name.setFill(Color.rgb(0, 0, 0));
        Text nid = new Text("NID NO : " + rs.getString(2));
        nid.setX(posX);
        nid.setY(posY + 50);
        nid.setFont(Font.font(fonts));
        nid.setFill(Color.rgb(0, 0, 0));

        Text db = new Text("DATE OF BIRTH (MMM DD, YYYY) : " + rs.getString(3));

        db.setX(posX);
        db.setY(posY + 100);
        db.setFont(Font.font(fonts));
        db.setFill(Color.rgb(0, 0, 0));

        Text fn = new Text("FATHER'S NAME : " + rs.getString(4));
        fn.setX(posX);
        fn.setY(posY + 150);
        fn.setFont(Font.font(fonts));
        fn.setFill(Color.rgb(0, 0, 0));

        Text mn = new Text("MOTHER'S NAME : " + rs.getString(5));
        mn.setX(posX);
        mn.setY(posY + 200);
        mn.setFont(Font.font(fonts));
        mn.setFill(Color.rgb(0, 0, 0));

        Text add = new Text("ADRESS : " + rs.getString(6));
        add.setX(posX);
        add.setY(posY + 250);
        add.setFont(Font.font(fonts));
        add.setFill(Color.rgb(0, 0, 0));

        Text ct = new Text("CITY : " + rs.getString(7));
        ct.setX(posX);
        ct.setY(posY + 300);
        ct.setFont(Font.font(fonts));
        ct.setFill(Color.rgb(0, 0, 0));
        Text type;
        if (rs.getString(8).equals("NO")) {
            type = new Text("User Type: " + "Not Employee");
        } else {
            type = new Text("User Type: " + "Employee");
        }
        type.setFont(Font.font(fonts));
        type.setX(posX);
        type.setY(posY + 350);
        type.setFill(Color.rgb(0, 0, 0));
        Text simtype = new Text("Applied for : " + rs.getString(9) + " Sim");
        simtype.setX(posX);
        simtype.setY(posY + 400);
        simtype.setFont(Font.font(fonts));
        simtype.setFill(Color.rgb(0, 0, 0));
        root.getChildren().add(Name);
        root.getChildren().add(nid);
        root.getChildren().add(db);
        root.getChildren().add(fn);
        root.getChildren().add(mn);
        root.getChildren().add(add);
        root.getChildren().add(ct);
        root.getChildren().add(type);
    }

    private void pending_job() throws SQLException {
        int posX = 100, posY = 270, fonts = 20;
        Text Name = new Text("USER NAME : " + rs.getString(1));
        System.out.println(rs.getString(1));
        Name.setX(posX);
        Name.setY(posY);
        Name.setFont(Font.font(fonts));
        Name.setFill(Color.rgb(0, 0, 0));
        Text nid = new Text("NID NO : " + rs.getString(7));
        nid.setX(posX);
        nid.setY(posY + 40);
        nid.setFont(Font.font(fonts));
        nid.setFill(Color.rgb(0, 0, 0));

        Text db = new Text("DATE OF BIRTH (MMM DD, YYYY) : " + rs.getString(4));

        db.setX(posX);
        db.setY(posY + 80);
        db.setFont(Font.font(fonts));
        db.setFill(Color.rgb(0, 0, 0));

        Text fn = new Text("FATHER'S NAME : " + rs.getString(2));
        fn.setX(posX);
        fn.setY(posY + 120);
        fn.setFont(Font.font(fonts));
        fn.setFill(Color.rgb(0, 0, 0));

        Text mn = new Text("MOTHER'S NAME : " + rs.getString(3));
        mn.setX(posX);
        mn.setY(posY + 160);
        mn.setFont(Font.font(fonts));
        mn.setFill(Color.rgb(0, 0, 0));

        Text add = new Text("ADRESS : " + rs.getString(5));
        add.setX(posX);
        add.setY(posY + 200);
        add.setFont(Font.font(fonts));
        add.setFill(Color.rgb(0, 0, 0));

        Text ct = new Text("CITY : " + rs.getString(6));
        ct.setX(posX);
        ct.setY(posY + 240);
        ct.setFont(Font.font(fonts));
        ct.setFill(Color.rgb(0, 0, 0));

        Text em = new Text("EMAIL : " + rs.getString(8));
        em.setX(posX);
        em.setY(posY + 280);
        em.setFont(Font.font(fonts));
        em.setFill(Color.rgb(0, 0, 0));

        Text ac = new Text("ACC NO : " + rs.getString(9));
        ac.setX(posX);
        ac.setY(posY + 320);
        ac.setFont(Font.font(fonts));
        ac.setFill(Color.rgb(0, 0, 0));

        Text sy = new Text("SSC YEAR : " + rs.getString(10));
        sy.setX(posX);
        sy.setY(posY + 360);
        sy.setFont(Font.font(fonts));
        sy.setFill(Color.rgb(0, 0, 0));

        Text sg = new Text("SSC GPA : " + rs.getString(11));
        sg.setX(posX);
        sg.setY(posY + 400);
        sg.setFont(Font.font(fonts));
        sg.setFill(Color.rgb(0, 0, 0));

        Text hy = new Text("HSC YEAR : " + rs.getString(12));
        hy.setX(posX);
        hy.setY(posY + 440);
        hy.setFont(Font.font(fonts));
        hy.setFill(Color.rgb(0, 0, 0));

        Text hg = new Text("HSC GPA : " + rs.getString(13));
        hg.setX(posX);
        hg.setY(posY + 480);
        hg.setFont(Font.font(fonts));
        hg.setFill(Color.rgb(0, 0, 0));

        Text un = new Text("UNIVERSITY : " + rs.getString(14));
        un.setX(posX);
        un.setY(posY + 520);
        un.setFont(Font.font(fonts));
        un.setFill(Color.rgb(0, 0, 0));

        Text ug = new Text("CGPA : " + rs.getString(15));
        ug.setX(posX);
        ug.setY(posY + 560);
        ug.setFont(Font.font(fonts));
        ug.setFill(Color.rgb(0, 0, 0));

        Text exp = new Text("Experience : " + rs.getString(16));
        exp.setX(posX);
        exp.setY(posY + 600);
        exp.setFont(Font.font(fonts));
        exp.setFill(Color.rgb(0, 0, 0));

        root.getChildren().add(Name);
        root.getChildren().add(nid);
        root.getChildren().add(db);
        root.getChildren().add(fn);
        root.getChildren().add(mn);
        root.getChildren().add(add);
        root.getChildren().add(ct);
        root.getChildren().add(em);
        root.getChildren().add(ac);
        root.getChildren().add(sy);
        root.getChildren().add(sg);
        root.getChildren().add(hy);
        root.getChildren().add(hg);
        root.getChildren().add(un);
        root.getChildren().add(ug);
        root.getChildren().add(exp);
    }

    private void jobApproveScene(String User_Name, String NID_No) throws SQLException {
        BackSceneOnly();
        ImageView back = new ImageView("file:mainback.jpg");
        root.getChildren().add(back);
        int move = 175;
        double tabpaneX = 45, tabpaneY = 150;
        String sql = "SELECT * FROM PENDING_JOB_REQ WHERE ROWNUM = 1";
        try {
            con = new DataBase("TELECOM", "hr").getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            boolean x;
            x = false;
            if (rs.next()) {
                x = true;
                nid2 = rs.getString(7);
            }
            if (x) {
                pending_job();
            } else {
                InvalidLogInMsg("Failed", "No More Job Req");
            }
            //Profile(User_Name, NID_No); // HomePage of User = User Profile
            ImageView profileimg = new ImageView("file:profile.png");
            //root.getChildren().add(profileimg);
            profileimg.relocate(tabpaneX, tabpaneY);
            profileimg.setFitHeight(40);
            profileimg.setFitWidth(160);
            Text profiletext = new Text("PROFILE");
            SetThisText(profiletext, 85, 175, 20);
            Rectangle profilebox = new Rectangle(150, 40);
            SetThisBox(profilebox, 50, 170);
            profilebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                profilebox.setFill(Color.rgb(255, 255, 255, 0.1));
            });
            profilebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                profilebox.setFill(Color.rgb(255, 255, 255, 0));
            });
            profilebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                LogIn(User_Name, NID_No);
            });

            ImageView approveimg = new ImageView("file:profile.png");
            //root.getChildren().add(approveimg);
            approveimg.relocate(tabpaneX + (move * 1), tabpaneY);
            approveimg.setFitHeight(40);
            approveimg.setFitWidth(160);
            Text approvetext = new Text("ISSUE SIM");
            SetThisText(approvetext, 80 + (move * 1), 175, 20);
            Rectangle approvebox = new Rectangle(150, 40);
            SetThisBox(approvebox, 50 + (move * 1), 170);
            approvebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                approvebox.setFill(Color.rgb(255, 255, 255, 0.1));
            });
            approvebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                approvebox.setFill(Color.rgb(255, 255, 255, 0));
            });
            approvebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                try {
                    ApproveScene(User_Name, NID_No);
                } catch (SQLException ex) {
                    Logger.getLogger(Employee_UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            ImageView jobapproveimg = new ImageView("file:profile.png");
            root.getChildren().add(jobapproveimg);
            jobapproveimg.relocate(tabpaneX + (move * 2), tabpaneY);
            jobapproveimg.setFitHeight(40);
            jobapproveimg.setFitWidth(160);
            Text jobapprovetext = new Text("JOB REQ");
            SetThisText(jobapprovetext, 80 + (move * 2), 175, 20);
            Rectangle jobapprovebox = new Rectangle(150, 40);
            SetThisBox(jobapprovebox, 50 + (move * 2), 170);
//            jobapprovebox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
//                jobapprovebox.setFill(Color.rgb(255, 255, 255, 0.1));
//            });
//            jobapprovebox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
//                jobapprovebox.setFill(Color.rgb(255, 255, 255, 0));
//            });
//            jobapprovebox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//                try {
//                    jobApproveScene(User_Name, NID_No);
//                } catch (SQLException ex) {
//                    Logger.getLogger(Employee_UI.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            });

            ImageView logoutimg = new ImageView("file:profile.png");
            //root.getChildren().add(logoutimg);
            logoutimg.relocate(tabpaneX + (move * 3), tabpaneY);
            logoutimg.setFitHeight(40);
            logoutimg.setFitWidth(160);
            Text logouttext = new Text("LOG OUT");
            SetThisText(logouttext, 85 + (move * 3), 175, 20);
            Rectangle logoutbox = new Rectangle(150, 40);
            SetThisBox(logoutbox, 50 + (move * 3), 170);
            logoutbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                logoutbox.setFill(Color.rgb(255, 255, 255, 0.1));
            });
            logoutbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                logoutbox.setFill(Color.rgb(255, 255, 255, 0));
            });
            logoutbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> LogOut());

            Rectangle confirmboxup = new Rectangle(200, 60);
            Text confirmtext = new Text("APPROVE");
            confirmtext.setX(1015);
            confirmtext.setY(373);
            confirmtext.setFill(Color.WHITE);
            confirmtext.setFont(Font.font(40));
            Rectangle confirmboxdown = new Rectangle(200, 60);
            confirmboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            confirmboxdown.setX(1000);
            confirmboxdown.setY(330);
            confirmboxup.setFill(Color.rgb(0, 0, 0, 0));
            confirmboxup.setX(1000);
            confirmboxup.setY(330);
            confirmboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                confirmboxdown.setFill(Color.rgb(17, 119, 112, 1));
            });
            confirmboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                confirmboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            });
            //rs.next();
            //System.out.println(rs.getString(1));
            Text sal = new Text("Salary : ");
            sal.setX(965);
            sal.setY(270);
            sal.setFont(Font.font(20));
            TextField salaryf = new TextField();
            SetThisFieldvir(salaryf, 1040, 250);
            salaryf.setScaleX(1);
            confirmboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                try {

                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    Connection con2 = new DataBase("TELECOM", "hr").getConnection();

                    CallableStatement stmt = con2.prepareCall("{call APPROVE_THIS_PENDING_JOB_REQ(?,?,?)}");

                    stmt.setInt(1, Integer.parseInt(Job_ID));
//                    System.out.println(nid2 + " jsgdfkhaj " +empid);
                    stmt.setInt(2, Integer.parseInt(nid2));
//                    stmt.setString(2, nid2);
//                    System.out.println(salaryf.getText());
                    stmt.setInt(3, Integer.parseInt(salaryf.getText()));
                    basicsal = salaryf.getText();

//                    System.out.println(nid2 + " jsgdfkhaj " + passcode);
                    System.out.println("execute hoitese na");
                    stmt.executeUpdate();
                    System.out.println("execute hoise re!!!!");

                    stmt.close();
                    con2.close();

                    ApproveScene(User_Name, NID_No);

                    SuccessMsg("Approved", "This SIM Was Issued By Employee No." + passcode);
                } catch (SQLException ex2) {
                    System.out.println("doesn't work");
                    InvalidLogInMsg("Approve Failed", "Didn't Work");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Employee_UI.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            Rectangle disapproveboxup = new Rectangle(200, 60);
            Text disapprovetext = new Text("DELETE");
            disapprovetext.setX(1035);
            disapprovetext.setY(443);
            disapprovetext.setFill(Color.WHITE);
            disapprovetext.setFont(Font.font(40));
            Rectangle disapproveboxdown = new Rectangle(200, 60);
            disapproveboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            disapproveboxdown.setX(1000);
            disapproveboxdown.setY(400);
            disapproveboxup.setFill(Color.rgb(0, 0, 0, 0));
            disapproveboxup.setX(1000);
            disapproveboxup.setY(400);
            disapproveboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
                disapproveboxdown.setFill(Color.rgb(17, 119, 112, 1));
            });
            disapproveboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
                disapproveboxdown.setFill(Color.rgb(17, 119, 112, 0.7));
            });
            //rs.next();
            //System.out.println(rs.getString(1));

            disapproveboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                String sql2 = "DELETE FROM PENDING_JOB_REQ WHERE NID_NO = ?";
                try {
                    Connection con2 = new DataBase("TELECOM", "hr").getConnection();
                    PreparedStatement pst2 = con2.prepareStatement(sql2);
                    pst2.setString(1, nid2);
                    //ResultSet rs2 = pst2.executeQuery();
                    //if (rs2.next()) {

                    //} else {
                    //    InvalidLogInMsg("Failed", "Invalid Log In");
                    //}
                    pst2.executeQuery();
                    pst2.close();
                    con2.close();
                    jobApproveScene(User_Name, NID_No);
                } catch (SQLException ex2) {
                    System.out.println("doesn't work");
                }
            });

            if (x) {
                root.getChildren().add(confirmboxdown);
                root.getChildren().add(confirmtext);
                root.getChildren().add(confirmboxup);
                root.getChildren().add(disapproveboxdown);
                root.getChildren().add(disapprovetext);
                root.getChildren().add(disapproveboxup);
                root.getChildren().add(salaryf);
                root.getChildren().add(sal);
            }
            ImageView toppart = new ImageView("file:tbdgreen.png");
            root.getChildren().add(toppart);
            pst.close();
            con.close();
            StageShow();
        } catch (SQLException ex) {
            System.out.println("doesn't work");
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

    public void SetThisText(Text t, double x, double y, int s, Color c) {
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setFont(Font.font(s));
        t.setFill(c);
        root.getChildren().add(t);
    }

    public void SetThisField(TextField t, double x, double y) {
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setScaleX(1.5);
        //t.setScaleY(2);
        root.getChildren().add(t);
    }

    public void SetThisButton(Button b, double x, double y) {
        b.setLayoutX(x);
        b.setLayoutY(y);
        root.getChildren().add(b);
    }

    public void SetThisText(Text t, double x, double y, int s) {
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setFont(Font.font(s));
        t.setFill(Color.BLACK);
        root.getChildren().add(t);
    }

    public void SetThisBox(Rectangle t, double x, double y) {
        t.setFill(Color.rgb(255, 255, 255, 0));
        t.setX(x);
        t.setY(y - 20);
        root.getChildren().add(t);
    }

    public void setchText(Text t, double x, double y) {
        t.setX(x);
        t.setY(y);
        t.setFont(Font.font(15));
        t.setFill(Color.BLUE);
        root.getChildren().add(t);
    }

    private void SetThisFieldvir(TextField t, int x, int y) {
        t.setLayoutX(x);
        t.setLayoutY(y);
        t.setScaleX(1.5);
    }

    private void AdmScene(String User_Name, String NID_No) {
        BackSceneOnly();
        int posX = 100, posY = 185, diffy = 42, i = 1;
        int tx = posX + 350, ty = posY - 20;
        ImageView back = new ImageView("file:mainback.jpg");
        root.getChildren().add(back);
        int move = 175;
        double tabpaneX = 45, tabpaneY = 150;
        ImageView toppart = new ImageView("file:tbdgreen.png");
        root.getChildren().add(toppart);
        Text backtext = new Text("BACK");
        backtext.setX(1670);
        backtext.setY(posY - 15);
        backtext.setFont(Font.font(30));
        backtext.setFill(Color.rgb(0, 0, 0));
        root.getChildren().add(backtext);
        ImageView backpage = new ImageView("file:previous.png");
        backpage.setX(1600);
        backpage.setY(130);
        backpage.setFitHeight(60);
        backpage.setFitWidth(60);
        root.getChildren().add(backpage);
        Rectangle backbox = new Rectangle(155, 50);
        backbox.setFill(Color.rgb(0, 0, 0, 0));
        backbox.setX(1600);
        backbox.setY(135);
        root.getChildren().add(backbox);
        backbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            backtext.setFont(Font.font(40));
            backtext.setX(1665);
            backtext.setY(posY - 12);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            backtext.setFont(Font.font(30));
            backtext.setX(1670);
            backtext.setY(posY - 15);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            LogIn(User_Name, NID_No);
        });

        String sql = "SELECT * FROM POSTPAID_BILL WHERE ISSUED_BY IS NULL AND TOTAL_COST <> 0";
        try {
            con = new DataBase("TELECOM", "hr").getConnection();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                pendingBill(User_Name, NID_No);
            } else {
                InvalidLogInMsg("Failed", "No Bill To Be Issued.");
            }
            pst.close();
            con.close();
        } catch (Exception ex) {
            System.out.println("doesn't work");
        }

        StageShow();
    }

    private void MybillScene(String User_Name, String NID_No) {
        BackSceneOnly();
        int posX = 100, posY = 185;
        ImageView back = new ImageView("file:mainback.jpg");
        root.getChildren().add(back);
        ImageView toppart = new ImageView("file:tbdgreen.png");
        root.getChildren().add(toppart);
        Text backtext = new Text("BACK");
        backtext.setX(1670);
        backtext.setY(posY - 15);
        backtext.setFont(Font.font(30));
        backtext.setFill(Color.rgb(0, 0, 0));
        root.getChildren().add(backtext);
        ImageView backpage = new ImageView("file:previous.png");
        backpage.setX(1600);
        backpage.setY(130);
        backpage.setFitHeight(60);
        backpage.setFitWidth(60);
        root.getChildren().add(backpage);
        Rectangle backbox = new Rectangle(155, 50);
        backbox.setFill(Color.rgb(0, 0, 0, 0));
        backbox.setX(1600);
        backbox.setY(135);
        root.getChildren().add(backbox);
        backbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            backtext.setFont(Font.font(40));
            backtext.setX(1665);
            backtext.setY(posY - 12);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            backtext.setFont(Font.font(30));
            backtext.setX(1670);
            backtext.setY(posY - 15);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            LogIn(User_Name, NID_No);
        });

        Text warningtext1 = new Text("To see your call list in detail, please log out, then go to user part and");
        Text warningtext2 = new Text("log in back as a user. Thank you.");

        warningtext1.setX(600);
        warningtext1.setY(850);
        warningtext2.setX(770);
        warningtext2.setY(870);
        warningtext1.setFont(Font.font(20));
        warningtext2.setFont(Font.font(20));
        warningtext1.setFill(Color.BLUE);
        warningtext2.setFill(Color.BLUE);

        int saln = Integer.parseInt(basicsal);

        double b1 = saln * 0.1;
        double b2 = saln * 0.05;
        double b3 = saln * 1;
        double totsal;

        Text empname = new Text("Name : " + name);
        Text basesal = new Text("Basic Salary : " + basicsal);
        Text bonus = new Text("Bonus : ");
        Text bonus1 = new Text("Health Care Bonus (10% of basic) : " + b1);
        Text bonus2 = new Text("Performance Bonus (5% of basic) : " + b2);
        Text bonus3 = new Text("Festival Bonus (100% of basic) : " + b3);
        empname.relocate(500, 250);
        basesal.relocate(500, 300);
        bonus.relocate(500, 350);
        bonus1.relocate(580, 350);
        bonus2.relocate(580, 400);
        bonus3.relocate(580, 450);

        SimpleDateFormat sdftime = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        Date now = new Date();
        String nowdate;
        nowdate = sdftime.format(now);
        String nowmonth;
        nowmonth = nowdate.substring(6, 7);
        //System.out.println(nowmonth);
        empname.setFont(Font.font(20));
        basesal.setFont(Font.font(20));
        bonus.setFont(Font.font(20));
        bonus1.setFont(Font.font(20));
        bonus2.setFont(Font.font(20));
        bonus3.setFont(Font.font(20));

        root.getChildren().add(empname);
        root.getChildren().add(basesal);
        root.getChildren().add(bonus);
        root.getChildren().add(bonus1);
        root.getChildren().add(bonus2);
        if ("7".equals(nowmonth)) {
            root.getChildren().add(bonus3);
            totsal = saln + b1 + b2 + b3;
        } else {
            totsal = saln + b1 + b2;
        }

        int payam = (int) (totsal + 1);
        Text totsals = new Text("Total Payable Amount : " + payam);
        totsals.relocate(500, 500);
        totsals.setFont(Font.font(20));
        root.getChildren().add(totsals);

        root.getChildren().add(warningtext1);
        root.getChildren().add(warningtext2);
        StageShow();
    }

    private void report(String User_Name, String NID_No) {
        BackSceneOnly();
        int posX = 600, posY = 185, diffy = 42, i = 1;
        int tx = posX + 350, ty = posY - 20;
        ImageView back = new ImageView("file:mainback.jpg");
        root.getChildren().add(back);
        int move = 175;
        double tabpaneX = 45, tabpaneY = 150;
        ImageView toppart = new ImageView("file:tbdgreen.png");
        root.getChildren().add(toppart);
        Text backtext = new Text("BACK");
        backtext.setX(1670);
        backtext.setY(posY - 15);
        backtext.setFont(Font.font(30));
        backtext.setFill(Color.rgb(0, 0, 0));
        root.getChildren().add(backtext);
        ImageView backpage = new ImageView("file:previous.png");
        backpage.setX(1600);
        backpage.setY(130);
        backpage.setFitHeight(60);
        backpage.setFitWidth(60);
        root.getChildren().add(backpage);
        Rectangle backbox = new Rectangle(155, 50);
        backbox.setFill(Color.rgb(0, 0, 0, 0));
        backbox.setX(1600);
        backbox.setY(135);
        root.getChildren().add(backbox);
        backbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            backtext.setFont(Font.font(40));
            backtext.setX(1665);
            backtext.setY(posY - 12);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            backtext.setFont(Font.font(30));
            backtext.setX(1670);
            backtext.setY(posY - 15);
        });
        backbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            LogIn(User_Name, NID_No);
        });
        String totaluser = null;
        String totalpreuser = null;
        String totalpostuser = null;
        String totalcall = null;
        String totalsms = null;
        String totalearnpre = null;
        double totalearnpost = 0;
        String totalbasic = null;
        String sql4 = "SELECT COUNT(*) FROM SIM";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                totaluser = rs4.getString(1);
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
        }
        sql4 = "SELECT COUNT(*) FROM PREPAID_SIM";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                totalpreuser = rs4.getString(1);
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
        }
        sql4 = "SELECT COUNT(*) FROM POSTPAID_SIM";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                totalpostuser = rs4.getString(1);
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
        }
        sql4 = "SELECT COUNT(*) FROM CALL_DATABASE";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                totalcall = rs4.getString(1);
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
        }
        sql4 = "SELECT COUNT(*) FROM SMS_DATABASE";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                totalsms = rs4.getString(1);
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
        }
        sql4 = "SELECT COUNT(*) FROM EARNING_FROM_RECHARGE";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                totalearnpre = rs4.getString(1);
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
        }
        sql4 = "SELECT SUM(CALL_BILL),SUM(SMS_BILL),SUM(INTERNET_BILL) FROM POSTPAID_BILL";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                double earn;
                earn = Double.parseDouble(rs4.getString(1)) + Double.parseDouble(rs4.getString(2)) + Double.parseDouble(rs4.getString(3));
                totalearnpost = earn/100;
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
        }
        sql4 = "SELECT SUM(BASIC_SALARY) FROM EMPLOYEES";
        try {
            Connection con4 = new DataBase("TELECOM", "hr").getConnection();
            PreparedStatement pst4 = con4.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            if (rs4.next()) {
                totalbasic = rs4.getString(1);
            }
            pst4.close();
            con4.close();
        } catch (Exception ex) {
            System.out.println("doesn't work");
        }
        
        Text totuser = new Text("TOTAL USER : " + totaluser);
        Text totpre = new Text("TOTAL PREPAID SIM : " + totalpreuser);
        Text totpost = new Text("TOTAL POSTPAID SIM : " + totalpostuser);
        Text totcall = new Text("TOTAL CALL : " + totalcall);
        Text totsms = new Text("TOTAL SMS : " + totalsms);
        Text totearnpre = new Text("TOTAL EARNING FROM RECHARGE : " + totalearnpre);
        Text totearnpost = new Text("TOTAL POSTPAID EARNING : " + Double.toString(totalearnpost));
        Text totbase = new Text("TOTAL BASIC SALARY GIVEN : " + totalbasic);
        totuser.relocate(posX, 250);
        totpre.relocate(posX, 300);
        totpost.relocate(posX, 350);
        totcall.relocate(posX, 400);
        totsms.relocate(posX, 450);
        totearnpre.relocate(posX, 500);
        totearnpost.relocate(posX, 550);
        totbase.setFont(Font.font(20));
        totuser.setFont(Font.font(20));
        totpre.setFont(Font.font(20));
        totpost.setFont(Font.font(20));
        totcall.setFont(Font.font(20));
        totsms.setFont(Font.font(20));
        totearnpre.setFont(Font.font(20));
        totearnpost.setFont(Font.font(20));
        totbase.setFont(Font.font(20));
        root.getChildren().addAll(totuser,totpre,totpost,totcall,totsms,totearnpre,totearnpost,totbase);
        StageShow();
    }

    private void pendingBill(String User_Name, String NID_No) throws SQLException {
        int posX = 600, posY = 270, fonts = 20;
        billid = rs.getString(1);
        billsim = rs.getString(2);
        billcall = rs.getString(3);
        billsms = rs.getString(4);
        billin = rs.getString(5);
        totcost = rs.getString(6);
        Text id = new Text("BILL ID : " + rs.getString(1));
        //System.out.println(rs.getString(1));
        id.setX(posX);
        id.setY(posY);
        id.setFont(Font.font(fonts));
        id.setFill(Color.rgb(0, 0, 0));

        Text sim = new Text("SIM NO : " + rs.getString(2));
        sim.setX(posX);
        sim.setY(posY + 40);
        sim.setFont(Font.font(fonts));
        sim.setFill(Color.rgb(0, 0, 0));

        Text call = new Text("CALL COST : " + Double.parseDouble(rs.getString(3)) / 100);

        call.setX(posX);
        call.setY(posY + 80);
        call.setFont(Font.font(fonts));
        call.setFill(Color.rgb(0, 0, 0));

        Text sms = new Text("SMS COST : " + Double.parseDouble(rs.getString(4)) / 100);
        sms.setX(posX);
        sms.setY(posY + 120);
        sms.setFont(Font.font(fonts));
        sms.setFill(Color.rgb(0, 0, 0));

        Text in = new Text("INTERNET COST : " + Double.parseDouble(rs.getString(5)) / 100);
        in.setX(posX);
        in.setY(posY + 160);
        in.setFont(Font.font(fonts));
        in.setFill(Color.rgb(0, 0, 0));

        Text cost = new Text("TOTAL COST : " + Double.parseDouble(rs.getString(5)) / 100);
        cost.setX(posX);
        cost.setY(posY + 200);
        cost.setFont(Font.font(fonts));
        cost.setFill(Color.rgb(0, 0, 0));

        Text issuetext = new Text("ISSUE");
        issuetext.setX(posX + 25);
        issuetext.setY(posY + 500);
        issuetext.setFill(Color.WHITE);
        issuetext.setFont(Font.font(35));
        Rectangle issueboxdown = new Rectangle(140, 50);
        issueboxdown.setFill(Color.rgb(32, 155, 146));
        issueboxdown.setX(posX);
        issueboxdown.setY(posY + 462);
        Rectangle issueboxup = new Rectangle(140, 50);
        issueboxup.setFill(Color.rgb(0, 0, 0, 0));
        issueboxup.setX(posX);
        issueboxup.setY(posY + 462);
        root.getChildren().add(issueboxdown);
        root.getChildren().add(issuetext);
        root.getChildren().add(issueboxup);
        issueboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            issueboxdown.setFill(Color.rgb(17, 119, 112));
        });
        issueboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            issueboxdown.setFill(Color.rgb(32, 155, 146));
        });

        issueboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            AdmScene(User_Name, NID_No);
            String sql3 = "UPDATE POSTPAID_BILL SET ISSUED_BY = ? WHERE BILL_ID = ?";
            try {
                Connection con3 = new DataBase("TELECOM", "hr").getConnection();
                PreparedStatement pst3 = con3.prepareStatement(sql3);
                pst3.setString(1, empid);
                pst3.setString(2, billid);
                pst3.executeQuery();
                pst3.close();
                con3.close();
            } catch (Exception ex) {
                System.out.println("doesn't work");
            }
        });

        root.getChildren().add(id);
        root.getChildren().add(sim);
        root.getChildren().add(call);
        root.getChildren().add(sms);
        root.getChildren().add(in);
        root.getChildren().add(cost);

    }

}
