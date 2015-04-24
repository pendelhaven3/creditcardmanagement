package com.pj.creditcardmanagement.screen;

import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransaction;

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
	@Autowired private CreditCardScreen creditCardScreen;
	@Autowired private CreditCardTransactionsListScreen creditCardTransactionsListScreen;
	@Autowired private CreditCardTransactionScreen creditCardTransactionScreen;
	
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void showMainScreen() {
		stage.setTitle("Credit Card Management");
		stage.setScene(mainMenuScreen.getScene(WIDTH, HEIGHT));
		if (!stage.isShowing()) {
			stage.show();
		}
	}

	public void showCreditCardsListScreen() {
		stage.setTitle("Credit Cards List");
		showScreen(creditCardsListScreen);
		creditCardsListScreen.updateDisplay();
	}

	private void showScreen(AbstractScreen screen) {
		stage.setScene(screen.getScene(WIDTH, HEIGHT));
	}
	
	public void showAddCreditCardScreen() {
		stage.setTitle("Add Credit Card");
		showScreen(creditCardScreen);
		creditCardScreen.updateDisplay(new CreditCard());
	}

	public void showUpdateCreditCardScreen(CreditCard creditCard) {
		stage.setTitle("Update Credit Card");
		showScreen(creditCardScreen);
		creditCardScreen.updateDisplay(creditCard);
	}

	public void showCreditCardTransactionsListScreen() {
		stage.setTitle("Credit Card Transactions List");
		showScreen(creditCardTransactionsListScreen);
		creditCardTransactionsListScreen.updateDisplay();
	}

	public void showUpdateCreditCardTransactionScreen(CreditCardTransaction transaction) {
		stage.setTitle("Update Credit Card Transaction");
		showScreen(creditCardTransactionScreen);
		creditCardTransactionScreen.updateDisplay(transaction);
	}

	public void showAddCreditCardTransactionScreen() {
		stage.setTitle("Add Credit Card Transaction");
		showScreen(creditCardTransactionScreen);
		creditCardTransactionScreen.updateDisplay(new CreditCardTransaction());
	}

}