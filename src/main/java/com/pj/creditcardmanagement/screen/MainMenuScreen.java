package com.pj.creditcardmanagement.screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import org.springframework.stereotype.Component;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class MainMenuScreen extends AbstractScreen {

	private Button creditCardsButton;
	private Button transactionsButton;
	
	@Override
	public void layoutComponents(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		
		creditCardsButton.setText("Credit Cards");
		HBox creditCardButtonBox = new HBox(10);
		creditCardButtonBox.getChildren().add(creditCardsButton);
		grid.add(creditCardButtonBox, 0, 0);
		
		transactionsButton.setText("Transactions");
		HBox transactionsButtonBox = new HBox(10);
		transactionsButtonBox.getChildren().add(transactionsButton);
		grid.add(transactionsButtonBox, 0, 1);
	}

	@Override
	public void initializeComponents() {
		creditCardsButton = new Button();
		creditCardsButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getScreenController().showCreditCardsListScreen();
			}
		});
		
		transactionsButton = new Button();
	}

}