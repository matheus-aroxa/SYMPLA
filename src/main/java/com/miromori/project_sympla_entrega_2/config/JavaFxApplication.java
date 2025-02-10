package com.miromori.project_sympla_entrega_2.config;

import com.miromori.project_sympla_entrega_2.ProjectSymplaEntrega2Application;
import com.miromori.project_sympla_entrega_2.view.LoginForm;
import com.miromori.project_sympla_entrega_2.view.MainScreenView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavaFxApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        // Inicializa o contexto do Spring
        springContext = new SpringApplicationBuilder(ProjectSymplaEntrega2Application.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Obtém o bean LoginForm do contexto do Spring
        LoginForm loginForm = springContext.getBean(LoginForm.class);
        loginForm.start(primaryStage);
//        MainScreenView loginForm = springContext.getBean(MainScreenView.class);
//        loginForm.start(primaryStage);
    }

    @Override
    public void stop() {
        // Fecha o contexto do Spring ao fechar a aplicação
        springContext.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}