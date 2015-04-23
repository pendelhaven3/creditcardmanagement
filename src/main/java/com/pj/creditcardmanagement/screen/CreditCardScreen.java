package com.pj.creditcardmanagement.screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.dialog.ErrorDialog;
import com.pj.creditcardmanagement.dialog.MessageDialog;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.util.LayoutUtil;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class CreditCardScreen extends StandardScreen {

	@Autowired private CreditCardService creditCardService;
	
	private TextField nameField;
	private TextField bankField;
	private TextField cardNumberField;
	private Button saveButton = new Button("Save");
	private CreditCard creditCard;
	
	public CreditCardScreen() {
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				saveCreditCard();
			}
		});
	}
	
	@Override
	public void initializeComponents() {
		nameField = new TextField();
		bankField = new TextField();
		cardNumberField = new TextField();
	}

	private void saveCreditCard() {
		if (!validateFields()) {
			return;
		}
		
		creditCard.setName(nameField.getText());
		creditCard.setBank(bankField.getText());
		creditCard.setCardNumber(cardNumberField.getText());
		try {
			creditCardService.save(creditCard);
		} catch (Exception e) {
			ErrorDialog.show("Unexpected error occurred");
			return;
		}
		
		MessageDialog.show("Credit Card saved");
		getScreenController().showUpdateCreditCardScreen(creditCard);
	}

	private boolean validateFields() {
		if (nameField.getText().isEmpty()) {
			ErrorDialog.show("Name must be specified");
			return false;
		}
		if (bankField.getText().isEmpty()) {
			ErrorDialog.show("Bank must be specified");
			return false;
		}
		if (cardNumberField.getText().isEmpty()) {
			ErrorDialog.show("Card Number must be specified");
			return false;
		}
		return true;
	}

	@Override
	public void layoutComponents(GridPane grid) {
		grid.setPadding(new Insets(100, 100, 100, 100));
		grid.setHgap(10);
		grid.setVgap(10);
		
		int currentRow = 0;
		
		grid.add(new Label("Name"), 0, currentRow);
		grid.add(nameField, 1, currentRow);
		
		currentRow++;
		
		grid.add(new Label("Bank"), 0, currentRow);
		grid.add(bankField, 1, currentRow);
		
		currentRow++;
		
		grid.add(new Label("Card Number"), 0, currentRow);
		grid.add(cardNumberField, 1, currentRow);
		
		currentRow++;
		grid.add(saveButton, 0, currentRow, 2, 1);
		GridPane.setHalignment(saveButton, HPos.CENTER);
		
		LayoutUtil.setColumnPercentWidths(grid, 40, 60);
	}

	@Override
	protected void addToolBarButtons(ToolBar toolBar) {
	}

	@Override
	protected void doOnBack() {
		getScreenController().showCreditCardsListScreen();
	}

	public void updateDisplay(CreditCard creditCard) {
		this.creditCard = creditCard;
		
		if (creditCard.isNew()) {
			return;
		}
		
		this.creditCard = creditCard = creditCardService.getCreditCard(creditCard.getId());
		
		nameField.setText(creditCard.getName());
		bankField.setText(creditCard.getBank());
		cardNumberField.setText(creditCard.getCardNumber());
	}

}