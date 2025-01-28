package com.miromori.project_sympla_entrega_2.view;

import com.miromori.project_sympla_entrega_2.controllers.UserController;
import javafx.application.Application;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class EventsView extends Application {

    UserController userController;
    private AnchorPane pane;
    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {

    }
}
