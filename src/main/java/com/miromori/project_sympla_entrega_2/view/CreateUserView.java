package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateUserView extends Application {


    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    UserController userController;

    private Label label;
    private TextField nameField, emailField;
    private ComboBox<String> role;
    private Button createButton, backButton;
    private PasswordField passwordField;
    private AnchorPane pane;
    private Stage stage;


    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListeners();

        Scene scene = new Scene(pane);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("create user");
        stage.show();
        initLayout();
    }

    public void initComponents(){
        pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        label = new Label("Create user");
        nameField = new TextField();
        nameField.setPromptText("Name");
        emailField = new TextField();
        emailField.setPromptText("Email");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        role = new ComboBox<>();
        role.getItems().addAll("admin", "user");
        createButton = new Button("Create user");
        backButton = new Button("Back");

        pane.getChildren().addAll(label, nameField, emailField, passwordField, createButton, backButton, role);

    }

    public void initLayout(){
        label.setLayoutX((pane.getWidth() - label.getWidth()) / 2);
        label.setLayoutY(20);
        nameField.setLayoutX((pane.getWidth() - nameField.getWidth()) / 2);
        nameField.setLayoutY(50);
        emailField.setLayoutX((pane.getWidth() - emailField.getWidth()) / 2);
        emailField.setLayoutY(80);
        passwordField.setLayoutX((pane.getWidth() - passwordField.getWidth()) / 2);
        passwordField.setLayoutY(110);
        role.setLayoutX((pane.getWidth() - role.getWidth()) / 2);
        role.setLayoutY(140);
        createButton.setLayoutX((pane.getWidth() - createButton.getPrefWidth()) / 2 - 75);
        createButton.setLayoutY(170);
        backButton.setLayoutX((pane.getWidth() - backButton.getWidth()) / 2 + 45);
        backButton.setLayoutY(170);

    }

    public void initListeners(){

    }

    public Stage getStage() {
        return stage;
    }
}
