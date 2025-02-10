package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersView extends Application {

    @Autowired
    UserController userController;
    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    private Button createUserButton, deleteUserButton, findUserButton, backButton;
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

    public void initComponents(){
        pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        findUserButton = new Button("Find User");
        createUserButton = new Button("Create User");
        deleteUserButton = new Button("Delete User");
        backButton = new Button("Back");

        pane.getChildren().addAll(findUserButton, createUserButton, deleteUserButton, backButton);
        stage = new Stage();

    }

    public void initLayout(){
        createUserButton.setLayoutX((pane.getWidth() - createUserButton.getWidth())/2) ;
        createUserButton.setLayoutY(40);
        deleteUserButton.setLayoutX((pane.getWidth() - deleteUserButton.getWidth()) / 2);
        deleteUserButton.setLayoutY(70);
        findUserButton.setLayoutX((pane.getWidth() - findUserButton.getWidth()) / 2);
        findUserButton.setLayoutY(100);
        backButton.setLayoutX((pane.getWidth() - backButton.getWidth()) / 2);
        backButton.setLayoutY(130);

    }

    public void initListeners(){
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
