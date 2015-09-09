package com.pj.creditcardmanagement.controller;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;

@Controller
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

}
