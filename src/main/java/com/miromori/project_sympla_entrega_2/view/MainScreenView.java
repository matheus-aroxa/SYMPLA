package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.UserController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainScreenView extends Application{
    @Autowired
    UserController userController;
    private Label goToLabel;
    private Button eventsButton, usersButton;
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
            goToLabel = new Label("Go to");
            eventsButton = new Button("Events");
            usersButton = new Button("Users");
            pane.getChildren().addAll(eventsButton, usersButton, goToLabel);
            stage = new Stage();

    }

    public void initLayout(){
            goToLabel.setLayoutX((pane.getWidth() - goToLabel.getWidth())/2 - 20);
            goToLabel.setLayoutY(50);
            goToLabel.setStyle("-fx-font-size: 30px;" +
                    " -fx-font-family: sans-serif;" +
                    " -fx-font-weight: bold;");

            eventsButton.setLayoutX((pane.getWidth() - eventsButton.getWidth())/2 - 35);
            eventsButton.setLayoutY(150);
            usersButton.setLayoutX((pane.getWidth() - usersButton.getWidth())/2 + 40);
            usersButton.setLayoutY(150);

    }

    public void initListeners(){
        eventsButton.setOnAction((event) -> {
            goToEvents();
        });

        usersButton.setOnAction((event) -> {
            goToUsers();
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


    public void goToUsers(){
        UsersView usersView = new UsersView();
        usersView.userController = userController;
        try{
            usersView.start(stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void goToEvents(){
        EventsView eventsView = new EventsView();
        eventsView.userController = userController;
        try{
            eventsView.start(stage);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

