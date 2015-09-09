package com.pj.creditcardmanagement.screen;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.ControllerFactory;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransaction;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class ScreenController {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	
	@Autowired private ControllerFactory controllerFactory;
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
		stage.setScene(loadSceneFromFXML("main"));
//		stage.setScene(mainMenuScreen.getScene(WIDTH, HEIGHT));
		if (!stage.isShowing()) {
			stage.show();
		}
	}

	public void showCreditCardsListScreen() {
		stage.setTitle("Credit Cards List");
		showScreen(creditCardsListScreen);
		creditCardsListScreen.updateDisplay();
	}

	private Scene loadSceneFromFXML(String file) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(controllerFactory);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/" + file + ".fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new Scene(root, WIDTH, HEIGHT);
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

	public void showPaymentsListScreen() {
		stage.setTitle("Credit Card Payments List");
		stage.setScene(loadSceneFromFXML("paymentList"));
	}

	public void showAddPaymentScreen() {
		stage.setTitle("Add Credit Card Payment");
		stage.setScene(loadSceneFromFXML("payment"));
	}

}