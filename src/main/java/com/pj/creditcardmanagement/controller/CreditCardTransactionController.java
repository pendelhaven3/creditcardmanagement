package com.pj.creditcardmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.creditcardmanagement.Parameter;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.PurchaseType;
import com.pj.creditcardmanagement.screen.ShowDialog;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.util.DateUtil;
import com.pj.creditcardmanagement.util.FormatterUtil;
import com.pj.creditcardmanagement.util.NumberUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

@Controller
@Scope("prototype")
public class CreditCardTransactionController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(CreditCardTransactionController.class);
	
	@Autowired private CreditCardService creditCardService;
	
	@FXML private ComboBox<CreditCard> creditCardComboBox;
	@FXML private TextField amountField;
	@FXML private DatePicker transactionDatePicker;
	@FXML private ComboBox<PurchaseType> purchaseTypeComboBox;
	@FXML private Button deleteButton;
	
	@Parameter private CreditCardTransaction transaction;
	
	@Override
	public void updateDisplay() {
		creditCardComboBox.setItems(FXCollections.observableList(creditCardService.getAllCreditCards()));
		purchaseTypeComboBox.setItems(FXCollections.observableList(creditCardService.getAllPurchaseTypes()));
		
		if (transaction != null) {
			transaction = creditCardService.getCreditCardTransaction(transaction.getId());
			
			creditCardComboBox.setValue(transaction.getCreditCard());
			transactionDatePicker.setValue(DateUtil.toLocalDate(transaction.getTransactionDate()));
			amountField.setText(FormatterUtil.formatAmount(transaction.getAmount()));
			purchaseTypeComboBox.setValue(transaction.getPurchaseType());
			deleteButton.setVisible(true);
		}
		
		creditCardComboBox.requestFocus();
	}

	@FXML
	public void saveTransaction() {
		if (!validateFields()) {
			return;
		}
		
		if (transaction == null) {
			transaction = new CreditCardTransaction();
		}
		transaction.setCreditCard(creditCardComboBox.getValue());
		transaction.setAmount(NumberUtil.toBigDecimal(amountField.getText()));
		transaction.setTransactionDate(DateUtil.toDate(transactionDatePicker.getValue()));
		transaction.setPurchaseType(purchaseTypeComboBox.getValue());
		try {
			creditCardService.save(transaction);
		} catch (Exception e) {
			ShowDialog.error("Unexpected error occurred");
			return;
		}
		
		ShowDialog.info("Credit Card Transaction saved");
		screenController.showCreditCardTransactionsListScreen();
	}
	
	private boolean validateFields() {
		if (creditCardComboBox.getSelectionModel().isEmpty()) {
			ShowDialog.error("Credit Card must be specified");
			return false;
		}
		if (amountField.getText().isEmpty()) {
			ShowDialog.error("Amount must be specified");
			return false;
		}
		if (!NumberUtil.isAmount(amountField.getText())) {
			ShowDialog.error("Amount must be a valid amount");
			return false;
		}
		if (transactionDatePicker.getValue() == null) {
			ShowDialog.error("Transaction Date must be specified");
			return false;
		}
		if (purchaseTypeComboBox.getSelectionModel().isEmpty()) {
			ShowDialog.error("Purchase Type must be specified");
			return false;
		}
		return true;
	}

	@FXML
	public void deleteTransaction() {
		if (ShowDialog.confirm("Delete Credit Card Transaction?")) {
			try {
				creditCardService.delete(transaction);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				ShowDialog.error("Unexpected error occurred");
				return;
			}
			ShowDialog.info("Transaction deleted");
			screenController.showCreditCardTransactionsListScreen();
		}
	}
	
	@FXML
	public void doOnBack() {
		screenController.showCreditCardTransactionsListScreen();
	}
	
}
