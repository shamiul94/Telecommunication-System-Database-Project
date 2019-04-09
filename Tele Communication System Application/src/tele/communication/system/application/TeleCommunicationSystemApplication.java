package tele.communication.system.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author HAROLD FINCH
 */

public class TeleCommunicationSystemApplication extends Application {

    public static Stage stage;
    public static Group root;
    public static Scene scene;
    public static String name;
    public static String passcode;

    
    
    @Override
    public void start(Stage primaryStage) {
        //primaryStage.setFullScreen(true);
        stage = primaryStage;
        InitialScene();
    }

    
//    public static void main(String[] args) {
//        launch(args);
//    }

    private void LoginScene() {
        BackSceneOnly();
        name = new String();
        passcode = new String();

        Text user = new Text("USERNAME : ");
        user.setX(0);
        user.setY(20);
        user.setFill(Color.rgb(0, 0, 0));

        Text pass = new Text("PASSWORD : ");
        pass.setX(0);
        pass.setY(70);
        pass.setFill(Color.rgb(0, 0, 0));

        TextField username = new TextField();
        username.setLayoutX(100);
        username.setLayoutY(0);
        PasswordField password = new PasswordField();
        password.setLayoutX(100);
        password.setLayoutY(50);
        Button loginbtn = new Button("Log In");
        loginbtn.setLayoutX(300);
        loginbtn.setLayoutY(0);
        loginbtn.setOnAction(e -> {
            name = username.getText();
            passcode = password.getText();
//            SuccessMsg("Success", "LogIn Successful");
            System.out.println(name);
            System.out.println(passcode);
            boolean success = false;
            String sql = "SELECT * FROM USER_DATA WHERE USER_NAME = ? AND NID_NO = ?";
            try {
                Connection con = new DataBase("TELECOM", "hr").getConnection();
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, name);
                pst.setString(2, passcode);
                System.out.println("aaisi");
                ResultSet rs = pst.executeQuery();
                System.out.println("success");

                if (rs.next()) {
                    success = true;
                    System.out.println("success");

                    SuccessMsg("Success", "LogIn Successful");
                    System.out.println(rs.getString(1) + " , " + rs.getString(2) + " , " + rs.getString(3));

                    while (rs.next()) {
                        System.out.println(rs.getString(1) + " , " + rs.getString(2) + " , " + rs.getString(3));
                    }
                } else {
                    InvalidLogInMsg("Failed", "Invalid Log In");
                }
                //System.out.println("failed");

                pst.close();
                con.close();
            } catch (Exception ex) {
                System.out.println("doesn't work");
            }
        });
        root.getChildren().add(username);
        root.getChildren().add(password);
        root.getChildren().add(pass);
        root.getChildren().add(user);
        root.getChildren().add(loginbtn);
        StageShow();
    }

    private void InitialScene() {
        BackSceneOnly();
        ImageView welcomebackscreen = new ImageView("file:Back2.jpg");
        ImageView Welcome = new ImageView("file:WelcomeMSG.png");
        Rectangle GetStartesBox = new Rectangle(500, 200);
        GetStartesBox.setFill(Color.rgb(0, 0, 0, 0));
        ImageView Get_Started = new ImageView("file:Get Started.png");
        Get_Started.setX(630);
        Get_Started.setY(400);
        Welcome.setX(100);
        Welcome.setY(100);
        welcomebackscreen.setFitHeight(900);
        welcomebackscreen.setFitWidth(1800);
        GetStartesBox.setX(630);
        GetStartesBox.setY(400);

        GetStartesBox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> LoginScene());

        root.getChildren().add(welcomebackscreen);
        root.getChildren().add(Welcome);
        root.getChildren().add(Get_Started);
        root.getChildren().add(GetStartesBox);
        StageShow();
    }

    public static void BackSceneOnly() {
        root = new Group();
        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); 
        scene = new Scene(root, 1800, 900);
        scene.setFill(Color.rgb(255, 255, 255));//rgb(red, green, blue)
    }

    public static void StageShow() {
        stage.setTitle("EMPLOYEE UI");
        stage.setScene(scene);
        stage.show();
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
}