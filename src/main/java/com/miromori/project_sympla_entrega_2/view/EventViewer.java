package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.EventController;
import com.miromori.project_sympla_entrega_2.controllers.FeedbackController;
import com.miromori.project_sympla_entrega_2.controllers.UserController;
import com.miromori.project_sympla_entrega_2.models.Event;
import com.miromori.project_sympla_entrega_2.repositories.SubscriptionsRepository;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventViewer extends Application {

    @Autowired
    EventController eventController;
    @Autowired
    FeedbackController feedbackController;
    @Autowired
    UserController userController;
    @Autowired
    SubscriptionsRepository subscriptionsRepository;

    private TableView<Event> table = new TableView<>();
    private TableColumn<Event, String> nameCol, dateCol, priceCol;
    private Button previousButton, nextButton, subscriptionsButton, logoutButton;
    private HBox buttonBox;
    private VBox vBox;
    private ObservableList<Event> data = FXCollections.observableArrayList();
    private int currentPage = 0;
    private final int pageSize = 5;
    private Stage stage;
    private ObservableList<Event> allData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        initComponents();
        initListeners();
        loadDataFromDatabase();

        Scene scene = new Scene(vBox, 300, 250);
        this.stage = stage;
        stage.setScene(scene);
        stage.setTitle("User menu");
        stage.show();
        initLayout();
    }

    public void initComponents(){
        dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.getColumns().addAll(nameCol, dateCol, priceCol);
        table.setPlaceholder(new Label("No events found"));
        previousButton = new Button("Previous");
        nextButton = new Button("Next");
        subscriptionsButton = new Button("Subscriptions");
        logoutButton = new Button("Logout");
        buttonBox = new HBox(10, previousButton, nextButton, subscriptionsButton, logoutButton);
        vBox = new VBox(10, table, buttonBox);
    }

    public void initLayout(){
        vBox.setPadding(new Insets(10));
    }

    public void initListeners(){
        previousButton.setOnAction(e -> {
            previous();
        });
        nextButton.setOnAction(e -> {
            next();
        });
        logoutButton.setOnAction(e -> {
            logout();
        });

    }

    public Stage getStage() {
        return stage;
    }

    private void loadDataFromDatabase() {
        allData.clear();
        List<Event> events = eventController.findAll();
        allData.addAll(events);
        loadData();
    }

    private void loadData() {
        data.clear(); // Limpa os dados atuais da tabela

        // Calcula o índice inicial e final para a página atual
        int fromIndex = currentPage * pageSize;
        int toIndex = Math.min(fromIndex + pageSize, allData.size());

        // Adiciona os eventos da página atual à tabela
        data.addAll(allData.subList(fromIndex, toIndex));
        table.setItems(data); // Atualiza a tabela com os dados da página atual

        // Atualiza o estado dos botões de navegação
        previousButton.setDisable(currentPage == 0); // Desabilita "Previous" na primeira página
        nextButton.setDisable((currentPage + 1) * pageSize >= allData.size()); // Desabilita "Next" na última página
    }

    private void next(){
        if ((currentPage + 1) * pageSize < allData.size()) {
            currentPage++; // Vai para a próxima página
            loadData(); // Atualiza a tabela com os dados da nova página
        }
        // Desabilita o botão "Next" se estiver na última página
        nextButton.setDisable((currentPage + 1) * pageSize >= allData.size());
        // Habilita o botão "Previous" ao avançar para uma próxima página
        previousButton.setDisable(false);
    }

    private void previous(){
        if (currentPage > 0) {
            currentPage--; // Vai para a página anterior
            loadData(); // Atualiza a tabela com os dados da nova página
        }
        // Desabilita o botão "Previous" se estiver na primeira página
        previousButton.setDisable(currentPage == 0);
        // Habilita o botão "Next" ao voltar para uma página anterior
        nextButton.setDisable(false);
    }

    private void logout(){
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
}



