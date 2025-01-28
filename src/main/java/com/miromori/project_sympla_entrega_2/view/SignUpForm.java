package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.models.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SignUpForm extends Application {

    @Autowired
    UserController userController;
    private Label signUpLabel;
    private Button confirmButton, backButton;
    private TextField fullNameTextField, emailTextField;
    private PasswordField passwordTextField;
    private AnchorPane pane;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        initComponents();
        initListeners();

        Scene scene = new Scene(pane);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("Create User");
        stage.show();
        initLayout();
    }

    public void initComponents(){
        pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        signUpLabel = new Label("Sign Up");
        confirmButton = new Button("Confirm");
        backButton = new Button("Back");
        fullNameTextField = new TextField();
        fullNameTextField.setPromptText("Full Name");
        emailTextField = new TextField();
        emailTextField.setPromptText("Email");
        passwordTextField = new PasswordField();
        passwordTextField.setPromptText("Password");

        pane.getChildren().addAll(signUpLabel, fullNameTextField, emailTextField, passwordTextField, confirmButton, backButton);
        stage = new Stage();

    }

    public void initLayout(){
        signUpLabel.setLayoutX((pane.getWidth() - signUpLabel.getWidth())/2 - 10);
        signUpLabel.setLayoutY(20);
        fullNameTextField.setLayoutX((pane.getWidth() - signUpLabel.getWidth())/2 - 60);
        fullNameTextField.setLayoutY(100);
        emailTextField.setLayoutX((pane.getWidth() - signUpLabel.getWidth())/2 - 60);
        emailTextField.setLayoutY(130);
        passwordTextField.setLayoutX((pane.getWidth() - signUpLabel.getWidth())/2 - 60);
        passwordTextField.setLayoutY(160);

        confirmButton.setLayoutX((pane.getWidth() - signUpLabel.getWidth())/2 - 50);
        confirmButton.setLayoutY(200);
        backButton.setLayoutX((pane.getWidth() - signUpLabel.getWidth())/2 + 20);
        backButton.setLayoutY(200);

    }

    public void initListeners(){
        backButton.setOnAction(e -> {
            stage.close();
            LoginForm loginForm = new LoginForm();
            loginForm.userController = this.userController;
            try {
                loginForm.start(stage);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        confirmButton.setOnAction(e -> {
            String fullName = fullNameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordTextField.getText();

            if(email.isEmpty() || password.isEmpty() || fullName.isEmpty()){
                showAlert("Fields cannot be empty");
                return;
            }
            if(userController.findByEmail(email) != null){
                showAlert("Email Already Exist");
                return;
            }

            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setName(fullName);
            userController.save(user);
            showAlert("User created, return to login form");
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







