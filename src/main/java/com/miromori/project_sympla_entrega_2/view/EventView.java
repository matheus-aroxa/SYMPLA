package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.models.Subscription;
import com.miromori.project_sympla_entrega_2.models.User;
import com.miromori.project_sympla_entrega_2.repositories.SubscriptionsRepository;
import com.miromori.project_sympla_entrega_2.services.ServicoEmail;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    User loggedUser;
    private Label nameLabel, descriptionLabel, dateLabel, priceLabel, capacityLabel;
    private Button feedbackButton, subscribeButton, backButton;
    private HBox feedbackSubscribeBox, backButtonBox;
    private VBox eventDetailsBox, mainLayout;
    private Region spacer;
    private Stage stage;


    private void initComponents() {
        nameLabel = new Label(event.getName());
        descriptionLabel = new Label(event.getDescription());
        descriptionLabel.setWrapText(true);
        descriptionLabel.setMaxWidth(350);
        descriptionLabel.setAlignment(Pos.CENTER);
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

    private void initListeners(){
        backButton.setOnAction(e -> {
            UserMenu userMenu = new UserMenu();
            userMenu.eventController = eventController;
            userMenu.feedbackController = feedbackController;
            userMenu.userController = userController;
            userMenu.subscriptionsRepository = subscriptionsRepository;
            userMenu.loggedUser = loggedUser;
            userMenu.start(stage);
        });

        subscribeButton.setOnAction(e -> subscribeToEvent());
        feedbackButton.setOnAction(e -> {
                FeedbacksView view = new FeedbacksView();
                view.eventController = eventController;
                view.feedbackController = feedbackController;
                view.userController = userController;
                view.subscriptionsRepository = subscriptionsRepository;
                view.loggedUser = loggedUser;
                view.selectedEvent = event;
                view.start(stage);
        });
    }

    private void subscribeToEvent() {
        if (loggedUser != null && event != null) {
            Subscription subscription = new Subscription();
            subscription.setUserId(loggedUser.getId());
            subscription.setEventId(event.getId());
            subscription.setDate(LocalDateTime.now());
            subscription.setEventName(event.getName());
            subscriptionsRepository.save(subscription);
            ServicoEmail.enviaConfirmacaoDoEmail(loggedUser.getEmail());
            showAlert("Inscrição realizada com sucesso, um email foi enviado para voce");
        } else {
            showAlert("Error: User or Event not found.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage stage) {
        initComponents();
        initListeners();

        Scene scene = new Scene(mainLayout, 400, 300);
        this.stage = stage;
        stage.setTitle("Event view");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
