package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.models.Subscription;
import com.miromori.project_sympla_entrega_2.models.User;
import com.miromori.project_sympla_entrega_2.repositories.SubscriptionsRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubscriptionsView extends Application {

    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    UserController userController;
    @Autowired
    SubscriptionsRepository subscriptionsRepository;

    User loggedUser;
    private TableView<Subscription> table = new TableView<>();
    private TableColumn<Subscription, String> eventCol;
    private Button previousButton, nextButton, logoutButton, backButton;
    private HBox buttonBox;
    private VBox vBox;
    private ObservableList<Subscription> data = FXCollections.observableArrayList();
    private int currentPage = 0;
    private final int pageSize = 5;
    private ObservableList<Subscription> allData = FXCollections.observableArrayList();
    private Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        initComponents();
        initListeners();
        loadDataFromDatabase();

        Scene scene = new Scene(vBox, 500, 350);
        stage.setScene(scene);
        stage.setTitle("User Subscriptions");
        stage.show();
    }

    public void initComponents() {
        TableColumn<Subscription, Long> userIdCol = new TableColumn<>("User ID");
        userIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));

        TableColumn<Subscription, String> eventCol = new TableColumn<>("Event");
        eventCol.setCellValueFactory(new PropertyValueFactory<>("eventName"));

        TableColumn<Subscription, String> dateCol = new TableColumn<>("Subscription Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(userIdCol, eventCol, dateCol);
        table.setPlaceholder(new Label("No subscriptions found"));

        previousButton = new Button("Previous");
        nextButton = new Button("Next");
        logoutButton = new Button("Logout");
        backButton = new Button("Back");
        buttonBox = new HBox(10, previousButton, nextButton, backButton, logoutButton);
        vBox = new VBox(10, table, buttonBox);
        vBox.setPadding(new Insets(10));
    }

    public void initListeners() {
        previousButton.setOnAction(e -> previous());
        nextButton.setOnAction(e -> next());
        logoutButton.setOnAction(e -> logout());
        backButton.setOnAction(e -> back());
    }

    private void loadDataFromDatabase() {
        if (loggedUser != null) {
            allData.clear();
            Long userId = userController.findByEmail(loggedUser.getEmail()).getId();
            List<Subscription> subscriptions = subscriptionsRepository.findByUserId(userId);
            for (Subscription subscription : subscriptions) {
                subscription.setEventName(eventController.findById(subscription.getEventId()).get().getName());
            }
            allData.addAll(subscriptions);
            loadData();
        }
    }

    private void loadData() {
        data.clear();
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allData.size());
        data.addAll(allData.subList(fromIndex, toIndex));
        table.setItems(data);

        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable((currentPage + 1) * pageSize >= allData.size());
    }

    private void next() {
        if ((currentPage + 1) * pageSize < allData.size()) {
            currentPage++;
            loadData();
        }
    }

    private void previous() {
        if (currentPage > 0) {
            currentPage--;
            loadData();
        }
    }

    private void logout() {
        LoginForm loginForm = new LoginForm();
        loginForm.userController = userController;
        loginForm.eventController = eventController;
        loginForm.feedbackController = feedbackController;
        loginForm.subscriptionsRepository = subscriptionsRepository;
        try {
            loginForm.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void back(){
        UserMenu userMenu = new UserMenu();
        userMenu.userController = userController;
        userMenu.eventController = eventController;
        userMenu.feedbackController = feedbackController;
        userMenu.subscriptionsRepository = subscriptionsRepository;
        userMenu.loggedUser = loggedUser;
        try {
            userMenu.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
