package com.pj.creditcardmanagement.screen;

import javafx.scene.Scene;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class ScreenController {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	
	@Autowired private MainMenuScreen mainMenuScreen;
	@Autowired private CreditCardsListScreen creditCardsListScreen;
	
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void showMainScreen() {
		stage.setTitle("Credit Card Management");
		stage.setScene(new Scene(mainMenuScreen.getGridPane(), WIDTH, HEIGHT));
		stage.show();
	}

	public void showCreditCardsListScreen() {
		stage.setTitle("Credit Cards List");
		creditCardsListScreen.updateDisplay();
		showScreen(creditCardsListScreen);
	}

	private void showScreen(AbstractScreen screen) {
		stage.setScene(new Scene(screen.getGridPane(), WIDTH, HEIGHT));
	}
	
}