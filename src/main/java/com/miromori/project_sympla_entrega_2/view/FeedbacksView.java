package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.repositories.SubscriptionsRepository;
import com.miromori.project_sympla_entrega_2.models.Feedback;
import com.miromori.project_sympla_entrega_2.models.Event;
import com.miromori.project_sympla_entrega_2.models.User;
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
public class FeedbacksView extends Application {

    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    UserController userController;
    @Autowired
    SubscriptionsRepository subscriptionsRepository;

    Event selectedEvent;
    User loggedUser;
    private TableView<Feedback> table = new TableView<>();
    private TableColumn<Feedback, String> commentCol;
    private Button previousButton, nextButton, backButton, submitFeedbackButton;
    private TextField feedbackInput;
    private HBox buttonBox, feedbackBox;
    private VBox vBox;
    private ObservableList<Feedback> data = FXCollections.observableArrayList();
    private int currentPage = 0;
    private final int pageSize = 5;
    private ObservableList<Feedback> allData = FXCollections.observableArrayList();
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

        Scene scene = new Scene(vBox, 600, 450);
        stage.setScene(scene);
        stage.setTitle("Feedbacks");
        stage.show();
    }

    private void initComponents() {
        commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));

        table.getColumns().addAll(commentCol);
        table.setPlaceholder(new Label("No feedbacks found"));

        previousButton = new Button("Previous");
        nextButton = new Button("Next");
        backButton = new Button("Back");
        submitFeedbackButton = new Button("Submit Feedback");
        feedbackInput = new TextField();
        feedbackInput.setPromptText("Write your feedback here...");

        buttonBox = new HBox(10, previousButton, nextButton, backButton);
        feedbackBox = new HBox(10, feedbackInput, submitFeedbackButton);
        vBox = new VBox(10, table, feedbackBox, buttonBox);
        vBox.setPadding(new Insets(10));
    }

    private void initListeners() {
        previousButton.setOnAction(e -> previous());
        nextButton.setOnAction(e -> next());
        backButton.setOnAction(e -> back());
        submitFeedbackButton.setOnAction(e -> submitFeedback());
    }

    private void loadDataFromDatabase() {
        if (selectedEvent != null) {
            allData.clear();
            List<Feedback> feedbacks = feedbackController.findByEventId(selectedEvent.getId());
            allData.addAll(feedbacks);
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

    private void submitFeedback() {
        String feedbackText = feedbackInput.getText().trim();
        if (!feedbackText.isEmpty() && loggedUser != null && selectedEvent != null) {
            Feedback feedback = new Feedback();
            feedback.setComment(feedbackText);
            feedback.setEvent(selectedEvent);
            feedbackController.save(feedback);
            feedbackInput.clear();
            loadDataFromDatabase();
        } else {
            showAlert("Please enter a valid feedback.");
        }
    }

    private void back() {
        EventView view = new EventView();
        view.loggedUser = loggedUser;
        view.event = selectedEvent;
        view.userController = userController;
        view.eventController = eventController;
        view.subscriptionsRepository = subscriptionsRepository;
        view.feedbackController = feedbackController;
        view.start(stage);
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
