package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.repositories.SubscriptionsRepository;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.miromori.project_sympla_entrega_2.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EventView extends Application {
    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    UserController userController;
    @Autowired
    SubscriptionsRepository subscriptionsRepository;

    Event event;
    private Label nameLabel, descriptionLabel, dateLabel, priceLabel, capacityLabel;
    private Button feedbackButton, subscribeButton, backButton;
    private HBox feedbackSubscribeBox, backButtonBox;
    private VBox eventDetailsBox, mainLayout;
    private Region spacer;

    private void initComponents() {
        nameLabel = new Label(event.getName());
        descriptionLabel = new Label(event.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(350);
        dateLabel = new Label("Date: " + event.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        priceLabel = new Label("Price: R$" + event.getPrice());
        capacityLabel = new Label("Capacity: " + event.getCapacity() + " people");
        feedbackButton = new Button("Feedback");
        subscribeButton = new Button("Subscribe");
        backButton = new Button("Back");
        feedbackSubscribeBox = new HBox(10, feedbackButton, subscribeButton);
        feedbackSubscribeBox.setAlignment(Pos.CENTER);
        backButtonBox = new HBox(backButton);
        backButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        spacer = new Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        eventDetailsBox = new VBox(10, nameLabel, descriptionLabel, dateLabel, priceLabel, capacityLabel);
        eventDetailsBox.setAlignment(Pos.CENTER);
        mainLayout = new VBox(10, eventDetailsBox, feedbackSubscribeBox, spacer, backButtonBox);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.TOP_CENTER);
    }

    @Override
    public void start(Stage stage) {
        initComponents();

        Scene scene = new Scene(mainLayout, 400, 300);
        stage.setTitle("Event view");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}



