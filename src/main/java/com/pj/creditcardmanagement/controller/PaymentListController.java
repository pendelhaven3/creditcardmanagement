package com.pj.creditcardmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.pj.creditcardmanagement.model.CreditCardPayment;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

@Controller
public class PaymentListController extends AbstractController {

	@FXML private TableView<CreditCardPayment> paymentsTable;
	
	@FXML 
	public void doOnBack() {
		screenController.showMainScreen();
	}

	@FXML
	private void initialize() {
		List<CreditCardPayment> payments = new ArrayList<>();
		paymentsTable.setItems(FXCollections.observableList(payments));
	}

}
