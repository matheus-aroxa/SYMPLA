package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.models.Event;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Component
public class CreateEventView extends Application {

    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    UserController userController;

    private Label eventLabel;
    private TextField eventName, eventDescription, eventLocation, eventCapacity, eventPrice;
    private DatePicker eventDate;
    private ComboBox<String> hora;
    private Button createEventButton, backButton;
    private AnchorPane pane;
    private Stage stage;


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
        eventLabel = new Label("Create Event");
        eventName = new TextField();
        eventName.setPromptText("Name");
        eventDescription = new TextField();
        eventDescription.setPromptText("Description");
        eventLocation = new TextField();
        eventLocation.setPromptText("Location");
        eventCapacity = new TextField();
        eventCapacity.setPromptText("Capacity");
        eventPrice = new TextField();
        eventPrice.setPromptText("Price");
        eventDate = new DatePicker();
        hora = new ComboBox<>();
        for (int i = 0; i < 24; i++) {
            hora.getItems().add(String.format("%02d", i));
        }
        createEventButton = new Button("Create Event");
        backButton = new Button("Back");

        pane.getChildren().addAll(eventLabel, eventName, eventDescription, eventLocation, eventCapacity, eventPrice, eventDate,
                hora, createEventButton, backButton);

    }

    public void initLayout(){
        eventLabel.setLayoutX((pane.getWidth() - eventLabel.getWidth()) / 2);
        eventLabel.setLayoutY(20);
        eventName.setLayoutX((pane.getWidth() - eventName.getWidth()) / 2);
        eventName.setLayoutY(50);
        eventDescription.setLayoutX((pane.getWidth() - eventDescription.getWidth()) / 2);
        eventDescription.setLayoutY(80);
        eventLocation.setLayoutX((pane.getWidth() - eventLocation.getWidth()) / 2);
        eventLocation.setLayoutY(110);
        eventCapacity.setLayoutX((pane.getWidth() - eventCapacity.getWidth()) / 2);
        eventCapacity.setLayoutY(140);
        eventPrice.setLayoutX((pane.getWidth() - eventPrice.getWidth()) / 2);
        eventPrice.setLayoutY(170);
        eventDate.setLayoutX((pane.getWidth() - eventDate.getWidth()) / 2);
        eventDate.setLayoutY(210);
        hora.setLayoutX((pane.getWidth() - hora.getWidth()) / 2);
        hora.setLayoutY(240);
        createEventButton.setLayoutX((pane.getWidth() - createEventButton.getPrefWidth()) / 2 - 85);
        createEventButton.setLayoutY(270);
        backButton.setLayoutX((pane.getWidth() - backButton.getWidth()) / 2 + 40);
        backButton.setLayoutY(270);

    }

    public void initListeners(){
        createEventButton.setOnAction(e -> {
            if(anyFieldIsBlank()) {
                showAlert("Fields cannot be empty");
                return;
            }
            createEvent();

        });

        backButton.setOnAction(e -> {
            EventsView eventsView = new EventsView();
            eventsView.userController = userController;
            eventsView.eventController = eventController;
            eventsView.feedbackController = feedbackController;
            try {
                eventsView.start(stage);
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

    public boolean anyFieldIsBlank(){
        for(Node node: pane.getChildren()){
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                if (textField.getText().isEmpty()) {
                    return true; // Campo vazio
                }
            } else if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                if (datePicker.getValue() == null) {
                    return true; // Data não selecionada
                }
            } else if (node instanceof ComboBox) {
                ComboBox<?> comboBox = (ComboBox<?>) node;
                if (comboBox.getValue() == null) {
                    return true; // Valor não selecionado
                }
            }
        }
        return false;
    }

    public void createEvent(){
        String name, description, location;
        int capacity;
        double price;
        LocalDate tempDate = eventDate.getValue();
        String horaSelecionada = hora.getValue();
        LocalTime tempHora = LocalTime.parse(horaSelecionada + ":00");
        LocalDateTime dateTime = LocalDateTime.of(tempDate, tempHora);

        name = eventName.getText();
        description = eventDescription.getText();
        location = eventLocation.getText();
        capacity = Integer.parseInt(eventCapacity.getText());
        price = Double.parseDouble(eventPrice.getText());

        Optional<Event> temp = eventController.findByName(name);
        if(temp.isPresent()){
            showAlert("Event already exists");
            return;
        }

        Event event = new Event();
        event.setDate(dateTime);
        event.setCapacity(capacity);
        event.setPrice(price);
        event.setName(name);
        event.setDescription(description);
        event.setLocation(location);

        eventController.save(event);
        showAlert("Event created with success");
    }

}



