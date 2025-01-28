package com.miromori.project_sympla_entrega_2;

import com.miromori.project_sympla_entrega_2.config.JavaFxApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProjectSymplaEntrega2Application {

	private static ConfigurableApplicationContext springContext;

	public static void main(String[] args) {
		JavaFxApplication.main(args);
	}

	public static ConfigurableApplicationContext getSpringContext() {
		return springContext;
	}
}
