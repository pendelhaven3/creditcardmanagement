package com.pj.creditcardmanagement.dialog;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransactionSearchCriteria;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.util.DateUtil;
import com.pj.creditcardmanagement.util.LayoutUtil;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class FilterCreditCardTransactionsDialog extends Stage {

	@Autowired private CreditCardService creditCardService;
	
	private ComboBox<CreditCard> creditCardComboBox;
	private DatePicker transactionDateFromDatePicker;
	private DatePicker transactionDateToDatePicker;
	private Button filterButton;
	private CreditCardTransactionSearchCriteria criteria;
	
	public FilterCreditCardTransactionsDialog() {
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		initializeComponents();
		layoutComponents();
	}

	private void initializeComponents() {
		creditCardComboBox = new ComboBox<>();
		transactionDateFromDatePicker = new DatePicker();
		transactionDateToDatePicker = new DatePicker();
		
		LocalDate now = LocalDate.now();
		transactionDateFromDatePicker.setValue(now);
		transactionDateToDatePicker.setValue(now);
		
		filterButton = new Button("Filter");
		filterButton.setOnAction(event -> returnCreditCardTransactionSearchCriteria());
	}

	private void layoutComponents() {
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 20, 0, 20));
		LayoutUtil.setColumnPercentWidths(grid, 40, 60);
		
		int currentRow = 0;
		
		grid.add(new Label("Credit Card"), 0, currentRow);
		grid.add(creditCardComboBox, 1, currentRow);
		
		currentRow++;
		
		grid.add(new Label("Transaction Date From"), 0, currentRow);
		grid.add(transactionDateFromDatePicker, 1, currentRow);
		
		currentRow++;
		
		grid.add(new Label("Transaction Date To"), 0, currentRow);
		grid.add(transactionDateToDatePicker, 1, currentRow);
		
		currentRow++;
		
		grid.add(filterButton, 0, currentRow, 2, 1);
		GridPane.setHalignment(filterButton, HPos.CENTER);
		
		setScene(new Scene(grid, 400, 200));
	}

	private void returnCreditCardTransactionSearchCriteria() {
		criteria = new CreditCardTransactionSearchCriteria();
		if (!creditCardComboBox.getSelectionModel().isEmpty()) {
			criteria.setCreditCard(creditCardComboBox.getSelectionModel().getSelectedItem());
		}
		criteria.setTransactionDateFrom(DateUtil.toDate(transactionDateFromDatePicker.getValue()));
		criteria.setTransactionDateTo(DateUtil.toDate(transactionDateToDatePicker.getValue()));
		
		hide();
	}
	
	public void updateDisplay() {
		criteria = null;
		creditCardComboBox.setItems(FXCollections.observableList(creditCardService.getAllCreditCards()));
	}
	
	@Override
	public void showAndWait() {
		updateDisplay();
		super.showAndWait();
	}

	public CreditCardTransactionSearchCriteria getCreditCardTransactionSearchCriteria() {
		return criteria;
	}
	
}