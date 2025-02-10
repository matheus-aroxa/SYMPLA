package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class EasterEggView extends Application{

    @Autowired
    UserController userController;
    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    private AnchorPane pane;
    private Button backButton;
    Image image;
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListeners();

        Scene scene = new Scene(pane);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("Easter Egg");
        stage.show();
        initLayout();
    }

    public void initComponents(){
        pane = new AnchorPane();
        pane.setPrefSize(600, 600);
        backButton = new Button("Back");
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/easter-egg.jpeg")));
        BackgroundImage background = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));
        pane.setBackground(new Background(background));
        pane.getChildren().addAll(backButton);
        stage = new Stage();

    }

    public void initLayout(){
        backButton.setLayoutX(540);
        backButton.setLayoutY(540);
    }

    public void initListeners(){
        backButton.setOnAction(event -> {
           LoginForm loginForm = new LoginForm();
           loginForm.userController = userController;
           loginForm.eventController = eventController;
           loginForm.feedbackController = feedbackController;
            try {
                loginForm.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    public void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }
}
