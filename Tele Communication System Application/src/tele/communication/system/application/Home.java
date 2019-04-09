package tele.communication.system.application;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author HAROLD FINCH
 */
public class Home extends Application {

    public static Stage stage;
    public static Group root;
    public static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        HomeScene();
    }

    public void HomeScene() {
        BackSceneOnly();
        ImageView homeimg = new ImageView("file:homepage.jpg");
        root.getChildren().add(homeimg);
        Text us = new Text("USER PART");
        Text em = new Text("EMPLOYEE PART");
        us.setX(900);
        us.setY(435);
        us.setFill(Color.rgb(216, 231, 232));
        us.setFont(Font.font(80));
        em.setX(810);
        em.setY(635);
        em.setFill(Color.rgb(216, 231, 232));
        em.setFont(Font.font(80));
        Rectangle userboxdown = new Rectangle(700, 120);
        userboxdown.setFill(Color.rgb(18, 55, 97, 0.8));
        userboxdown.relocate(750, 350);
        Rectangle userboxup = new Rectangle(700, 120);
        userboxup.setFill(Color.rgb(0, 0, 0, 0));
        userboxup.relocate(750, 350);
        userboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            User_UI user = new User_UI();
            try {
                user.go();
            } catch (Exception ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        userboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            userboxdown.setFill(Color.rgb(18, 55, 97, 1));
        });
        userboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            userboxdown.setFill(Color.rgb(18, 55, 97, 0.8));
        });

        Rectangle empboxdown = new Rectangle(700, 120);
        empboxdown.setFill(Color.rgb(18, 55, 97, 0.8));
        empboxdown.relocate(750, 550);
        Rectangle empboxup = new Rectangle(700, 120);
        empboxup.setFill(Color.rgb(0, 0, 0, 0));
        empboxup.relocate(750, 550);
        empboxup.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Employee_UI emp = new Employee_UI();
            try {
                emp.go();
            } catch (Exception ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        empboxup.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            empboxdown.setFill(Color.rgb(18, 55, 97, 1));
        });
        empboxup.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            empboxdown.setFill(Color.rgb(18, 55, 97, 0.8));
        });

        Text ver = new Text("Version 1.0");
        ver.setFill(Color.BLUE);
        ver.setX(1700);
        ver.setY(870);
        ver.setFont(Font.font(15));
        Rectangle verbox = new Rectangle(75, 15);
        verbox.setFill(Color.rgb(0, 0, 0, 0));
        verbox.relocate(1700, 858);

        verbox.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            ver.setFill(Color.BLUEVIOLET);
        });
        verbox.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            ver.setFill(Color.BLUE);
        });
        verbox.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            CreditScene();
        });

        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case E:
                    Employee_UI emp = new Employee_UI();
                    try {
                        emp.go();
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case U:
                    User_UI user = new User_UI();
                    try {
                        user.go();
                    } catch (Exception ex) {
                        Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
            }
        });

        root.getChildren().add(ver);
        root.getChildren().add(verbox);
        root.getChildren().add(userboxdown);
        root.getChildren().add(us);
        root.getChildren().add(userboxup);
        root.getChildren().add(empboxdown);
        root.getChildren().add(em);
        root.getChildren().add(empboxup);
        StageShow();
    }

    public static void BackSceneOnly() {
        root = new Group();
        //Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds(); 
        scene = new Scene(root, 1800, 900);
        scene.setFill(Color.rgb(255, 255, 255));//rgb(red, green, blue)
    }

    public static void StageShow() {
        stage.setTitle("HOME");
        stage.setScene(scene);
        Image logo = new Image("file:comlogo.png");
        stage.getIcons().add(logo);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void CreditScene() {
        BackSceneOnly();
        ImageView homeimg = new ImageView("file:credit.jpg");
        root.getChildren().add(homeimg);
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ESCAPE:
                    HomeScene();
                    break;
                case BACK_SPACE:
                    HomeScene();
                    break;
            }
        });
        StageShow();
    }
}
