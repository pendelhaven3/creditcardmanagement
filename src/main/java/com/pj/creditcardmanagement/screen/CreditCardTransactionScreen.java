package com.pj.creditcardmanagement.screen;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.service.CreditCardTransactionService;
import com.pj.creditcardmanagement.util.DateUtil;
import com.pj.creditcardmanagement.util.FormatterUtil;
import com.pj.creditcardmanagement.util.LayoutUtil;
import com.pj.creditcardmanagement.util.NumberUtil;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class CreditCardTransactionScreen extends StandardScreen {

	@Autowired private CreditCardService creditCardService;
	@Autowired private CreditCardTransactionService creditCardTransactionService;
	
	private ComboBox<CreditCard> creditCardComboBox;
	private TextField amountField;
	private DatePicker datePicker;
	private Button saveButton = new Button("Save");
	private Button deleteButton = new Button("Delete");
	private CreditCardTransaction transaction;
	
	public CreditCardTransactionScreen() {
		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				saveCreditCardTransaction();
			}
		});
	}
	
	@Override
	public void initializeComponents() {
		creditCardComboBox = new ComboBox<>();
		amountField = new TextField();
		datePicker = new DatePicker();
	}

	private void saveCreditCardTransaction() {
		if (!validateFields()) {
			return;
		}
		
		transaction.setCreditCard(creditCardComboBox.getSelectionModel().getSelectedItem());
		transaction.setAmount(NumberUtil.toBigDecimal(amountField.getText()));
		transaction.setTransactionDate(DateUtil.toDate(datePicker.getValue()));
		try {
			creditCardTransactionService.save(transaction);
		} catch (Exception e) {
			ShowDialog.error("Unexpected error occurred");
			return;
		}
		
		ShowDialog.info("Credit Card Transaction saved");
		getScreenController().showUpdateCreditCardTransactionScreen(transaction);
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
		if (datePicker.getValue() == null) {
			ShowDialog.error("Transaction Date must be specified");
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
		
		grid.add(new Label("Credit Card"), 0, currentRow);
		grid.add(creditCardComboBox, 1, currentRow);
		
		currentRow++;
		
		grid.add(new Label("Amount"), 0, currentRow);
		grid.add(amountField, 1, currentRow);
		
		currentRow++;
		
		grid.add(new Label("Transaction Date"), 0, currentRow);
		grid.add(datePicker, 1, currentRow);
		
		currentRow++;
		grid.add(saveButton, 0, currentRow, 2, 1);
		GridPane.setHalignment(saveButton, HPos.CENTER);
		
		LayoutUtil.setColumnPercentWidths(grid, 40, 60);
	}

	@Override
	protected void addToolBarButtons(ToolBar toolBar) {
		toolBar.getItems().add(deleteButton);
		deleteButton.setOnAction(e -> deleteCreditCardTransaction());
	}

	private void deleteCreditCardTransaction() {
		if (ShowDialog.confirm("Delete Credit Card Transaction?")) {
			try {
				creditCardTransactionService.delete(transaction);
			} catch (Exception e) {
				e.printStackTrace();
				ShowDialog.error("Unexpected error occurred");
				return;
			}
			
			getScreenController().showCreditCardTransactionsListScreen();
		}
	}

	@Override
	protected void doOnBack() {
		getScreenController().showCreditCardTransactionsListScreen();
	}

	public void updateDisplay(CreditCardTransaction transaction) {
		creditCardComboBox.setItems(FXCollections.observableList(creditCardService.getAllCreditCards()));
		
		this.transaction = transaction;
		deleteButton.setDisable(transaction.isNew());
		
		if (transaction.isNew()) {
			return;
		}
		
		this.transaction = transaction = creditCardTransactionService.getCreditCardTransaction(transaction.getId());
		
		creditCardComboBox.getSelectionModel().select(transaction.getCreditCard());
		amountField.setText(FormatterUtil.formatAmount(transaction.getAmount()));
		datePicker.setValue(DateUtil.toLocalDate(transaction.getTransactionDate()));
	}

}