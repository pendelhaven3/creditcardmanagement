package com.pj.creditcardmanagement.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;

@Controller
@Scope("prototype")
public class MainMenuController extends AbstractController {
	
	@FXML
	public void showCreditCardsListScreen() {
		screenController.showCreditCardsListScreen();
	}

	@FXML
	public void showTransactionsListScreen() {
		screenController.showCreditCardTransactionsListScreen();
	}

	@FXML
	public void showPaymentsListScreen() {
		screenController.showPaymentsListScreen();
	}

	@Override
	public void updateDisplay() {
	}

	@FXML 
	public void showPurchaseTypesListScreen() {
		screenController.showPurchaseTypesListScreen();
	}

}
