package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.models.Event;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class MakeSubscriptionView extends Application {

    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    UserController userController;

    private Label subscriptionLabel;
    private TextField eventName, userIdentification;
    private PasswordField password;
    private Button makeSubscriptionButton, backButton;
    private AnchorPane pane;
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListeners();

        Scene scene = new Scene(pane);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.show();
        initLayout();
    }

    public void initComponents() {
        pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        subscriptionLabel = new Label("Make subscription");
        eventName = new TextField();
        eventName.setPromptText("Event name");
        userIdentification = new TextField();
        userIdentification.setPromptText("User identification");
        password = new PasswordField();
        password.setPromptText("Password");
        makeSubscriptionButton = new Button("Subscribe");
        backButton = new Button("Back");

        pane.getChildren().addAll(subscriptionLabel, eventName, userIdentification, password, makeSubscriptionButton, backButton);

    }

    public void initLayout() {
        subscriptionLabel.setLayoutX((pane.getWidth() - subscriptionLabel.getWidth()) / 2);
        subscriptionLabel.setLayoutY(20);
        eventName.setLayoutX((pane.getWidth() - eventName.getWidth()) / 2);
        eventName.setLayoutY(50);
        userIdentification.setLayoutX((pane.getWidth() - userIdentification.getWidth()) / 2);
        userIdentification.setLayoutY(80);
        password.setLayoutX((pane.getWidth() - password.getWidth()) / 2);
        password.setLayoutY(110);
        makeSubscriptionButton.setLayoutX((pane.getWidth() - makeSubscriptionButton.getPrefWidth()) / 2 - 70);
        makeSubscriptionButton.setLayoutY(145);
        backButton.setLayoutX((pane.getWidth() - backButton.getWidth()) / 2 + 40);
        backButton.setLayoutY(145);

    }

    public void initListeners() {

    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

}




