package com.pj.creditcardmanagement;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.screen.ScreenController;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		String[] configLocations = new String[] {"applicationContext.xml", "datasource.xml"};
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(configLocations);
		context.registerShutdownHook();
		
		stage.setTitle("Credit Card Management");
		
		ScreenController screenController = context.getBean(ScreenController.class);
		screenController.setStage(stage);
		screenController.showMainScreen();
	}

}