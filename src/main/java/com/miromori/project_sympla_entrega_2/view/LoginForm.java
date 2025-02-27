package com.miromori.project_sympla_entrega_2.view;


import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.models.User;
import com.miromori.project_sympla_entrega_2.repositories.SubscriptionsRepository;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginForm extends Application {
    @Autowired
    UserController userController;
    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    SubscriptionsRepository subscriptionsRepository;

    private Label loginLabel;
    private AnchorPane pane;
    private TextField emailField;
    private PasswordField passwordField;
    private Button signInButton, signUpButton, secretButton;
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

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

    public void initComponents(){
        pane = new AnchorPane();
        pane.setPrefSize(400, 300);
        secretButton = new Button("?");
        emailField = new TextField();
        emailField.setPromptText("email: ");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password: ");
        signInButton = new Button("Sign in");
        signUpButton = new Button("Sign up");
        loginLabel = new Label("Login");
        pane.getChildren().addAll(emailField, passwordField, signInButton, signUpButton, loginLabel, secretButton);

    }

    public void initLayout(){
        loginLabel.setLayoutX((pane.getWidth() - loginLabel.getWidth())/2 - 10);
        loginLabel.setLayoutY(20);
        emailField.setLayoutX((pane.getWidth() - emailField.getWidth()) / 2);
        emailField.setLayoutY(50);
        passwordField.setLayoutX((pane.getWidth() - passwordField.getWidth()) / 2);
        passwordField.setLayoutY(100);
        signInButton.setLayoutX((pane.getWidth() - signInButton.getWidth()) / 2 - 35);
        signInButton.setLayoutY(150);
        signUpButton.setLayoutX((pane.getWidth() - signUpButton.getWidth()) / 2 + 35);
        signUpButton.setLayoutY(150);
        secretButton.setLayoutX(360);
        secretButton.setLayoutY(260);
        secretButton.setPrefSize(10, 10);
    }

    public void initListeners(){
        signInButton.setOnAction(e -> {
            login();
        });

        signUpButton.setOnAction(e -> {
            signUp();
        });

        secretButton.setOnAction(e -> {
            EasterEggView easterEggView = new EasterEggView();
            easterEggView.userController = userController;
            easterEggView.eventController = eventController;
            easterEggView.feedbackController = feedbackController;
            easterEggView.subscriptionsRepository = subscriptionsRepository;
            try {
                easterEggView.start(stage);
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

    public void login(){
        String email = emailField.getText();
        String password = passwordField.getText();

        if(email.isBlank() || password.isBlank()){
            showAlert("fields cant be empty");
            return;
        }

        User usuarioEncontrado = userController.findByEmail(email);
        if(usuarioEncontrado == null){
            showAlert("email doesn't exist");
            return;
        }

        if(!password.equals(usuarioEncontrado.getPassword())) {
            showAlert("password doesn't match");
            return;
        }

        String role = usuarioEncontrado.getRole();

        if(role == null){
            User user = new User();
            user.setEmail(email);
            user.setId(userController.findByEmail(email).getId());
            UserMenu userMenu = new UserMenu();
            userMenu.loggedUser = user;
            userMenu.userController = userController;
            userMenu.eventController = eventController;
            userMenu.feedbackController = feedbackController;
            userMenu.subscriptionsRepository = subscriptionsRepository;
            try{
                userMenu.start(stage);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(role.equals("admin")){
            CreateEventView event = new CreateEventView();
            event.userController = userController;
            event.eventController = eventController;
            event.feedbackController = feedbackController;
            event.subscriptionsRepository = subscriptionsRepository;
            try{
                event.start(stage);
            } catch (Exception e){
                e.printStackTrace();
            }
            return;
        }

    }

    public void signUp(){
        SignUpForm createUserView = new SignUpForm();
        createUserView.userController = this.userController;
        createUserView.eventController = eventController;
        createUserView.feedbackController = feedbackController;
        createUserView.subscriptionsRepository = subscriptionsRepository;

        try {
            createUserView.start(this.stage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
