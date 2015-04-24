package com.pj.creditcardmanagement.screen;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.dialog.FilterCreditCardTransactionsDialog;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.CreditCardTransactionSearchCriteria;
import com.pj.creditcardmanagement.service.CreditCardTransactionService;
import com.pj.creditcardmanagement.util.FormatterUtil;
import com.sun.javafx.collections.ObservableListWrapper;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class CreditCardTransactionsListScreen extends StandardScreen {

	@Autowired private CreditCardTransactionService creditCardTransactionService;
	@Autowired private FilterCreditCardTransactionsDialog filterCreditCardTransactionsDialog;
	
	private TableView<CreditCardTransaction> table;
	private Button addButton = new Button("Add");
	private Button filterButton = new Button("Filter");

	@Override
	public void layoutComponents(GridPane grid) {
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<CreditCardTransaction, CreditCard> creditCardColumn = new TableColumn<>("Credit Card");
		creditCardColumn.setCellValueFactory(new PropertyValueFactory<CreditCardTransaction, CreditCard>("creditCard"));
		
		TableColumn<CreditCardTransaction, String> amountColumn = new TableColumn<>("Amount");
		amountColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CreditCardTransaction,String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<CreditCardTransaction, String> param) {
						return new ReadOnlyStringWrapper(FormatterUtil.formatAmount(param.getValue().getAmount()));
					}
					
		});
		
		TableColumn<CreditCardTransaction, String> dateColumn = new TableColumn<>("Transaction Date");
		dateColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CreditCardTransaction,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<CreditCardTransaction, String> param) {
				return new ReadOnlyStringWrapper(FormatterUtil.formatDate(param.getValue().getTransactionDate()));
			}
			
		});

		table.getColumns().add(creditCardColumn);
		table.getColumns().add(amountColumn);
		table.getColumns().add(dateColumn);

		grid.add(table, 0, 0);
		
		GridPane.setHgrow(table, Priority.SOMETIMES);
		GridPane.setVgrow(table, Priority.SOMETIMES);
	}

	@Override
	public void initializeComponents() {
		table = new TableView<>();
		table.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				getScreenController().showUpdateCreditCardTransactionScreen(table.getSelectionModel().getSelectedItem());
			}
		});
	}

	public void updateDisplay() {
		ObservableList<CreditCardTransaction> transactions = 
				new ObservableListWrapper<CreditCardTransaction>(creditCardTransactionService.getAllCreditCardTransactions());
		table.setItems(transactions);
	}

	@Override
	protected void addToolBarButtons(ToolBar toolBar) {
		toolBar.getItems().add(addButton);
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getScreenController().showAddCreditCardTransactionScreen();
			}
		});
		
		toolBar.getItems().add(filterButton);
		filterButton.setOnAction(event -> showFilterCreditCardTransactionsDialog());
	}

	private void showFilterCreditCardTransactionsDialog() {
		filterCreditCardTransactionsDialog.showAndWait();
		
		CreditCardTransactionSearchCriteria criteria = 
				filterCreditCardTransactionsDialog.getCreditCardTransactionSearchCriteria();
		if (criteria != null) {
			table.setItems(FXCollections.observableList(
					creditCardTransactionService.searchCreditCardTransactions(criteria)));
		}
	}

	@Override
	protected void doOnBack() {
		getScreenController().showMainScreen();
	}
	
}