package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventsView extends Application {

    @Autowired
    UserController userController;

    @Autowired
    EventController eventController;

    @Autowired
    FeedbackController feedbackController;

    private Button createEventButton, deleteEventButton, findEventButton, backButton;
    private AnchorPane pane;
    private Stage stage;



    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListeners();

        Scene scene = new Scene(pane);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("Go to");
        stage.show();
        initLayout();
    }

    public void initComponents() {
        pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        createEventButton = new Button("create event");
        findEventButton = new Button("find event");
        deleteEventButton = new Button("Delete event");
        backButton = new Button("Back");

        pane.getChildren().addAll(createEventButton, findEventButton, deleteEventButton, backButton);
        stage = new Stage();

    }

    public void initLayout() {
        createEventButton.setLayoutX((pane.getWidth() - createEventButton.getWidth()) / 2);
        createEventButton.setLayoutY(40);
        deleteEventButton.setLayoutX((pane.getWidth() - deleteEventButton.getWidth()) / 2);
        deleteEventButton.setLayoutY(70);
        findEventButton.setLayoutX((pane.getWidth() - findEventButton.getWidth()) / 2);
        findEventButton.setLayoutY(100);
        backButton.setLayoutX((pane.getWidth() - backButton.getWidth()) / 2);
        backButton.setLayoutY(130);


    }

    public void initListeners() {
        backButton.setOnAction(e -> {
            MainScreenView mainScreenView = new MainScreenView();
            mainScreenView.userController = userController;
            mainScreenView.eventController = eventController;
            mainScreenView.feedbackController = feedbackController;
            try {
                mainScreenView.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        createEventButton.setOnAction(e -> {
            CreateEventView createEventView = new CreateEventView();
            createEventView.eventController = eventController;
            createEventView.userController = userController;
            createEventView.feedbackController = feedbackController;
            try {
                createEventView.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

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

