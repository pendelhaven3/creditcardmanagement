package com.pj.creditcardmanagement.screen;

import java.math.BigDecimal;
import java.util.List;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.Constants;
import com.pj.creditcardmanagement.component.DoubleClickEventHandler;
import com.pj.creditcardmanagement.dialog.FilterCreditCardTransactionsDialog;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.CreditCardTransactionSearchCriteria;
import com.pj.creditcardmanagement.service.CreditCardTransactionService;
import com.pj.creditcardmanagement.util.FormatterUtil;

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
	private Label totalItemsField;
	private Label totalAmountField;

	@Override
	public void layoutComponents(GridPane grid) {
		grid.setVgap(10);
		
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
		
		GridPane.setHgrow(table, Priority.ALWAYS);
		GridPane.setVgrow(table, Priority.ALWAYS);
		
		GridPane totalsGrid = createTotalsGrid();
		grid.add(totalsGrid, 0, 1);
	}

	private GridPane createTotalsGrid() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(0, 5, 5, 0));
		grid.setAlignment(Pos.CENTER);
		
		grid.add(new Label("Total Items"), 0, 0);
		grid.add(totalItemsField, 1, 0);
		grid.add(new Label("Total Amount"), 0, 1);
		grid.add(totalAmountField, 1, 1);
		
		totalItemsField.setMinWidth(100);
		totalItemsField.setAlignment(Pos.CENTER_RIGHT);
		
		totalAmountField.setMinWidth(100);
		totalAmountField.setAlignment(Pos.CENTER_RIGHT);
		
		return grid;
	}

	@Override
	public void initializeComponents() {
		table = new TableView<>();
		table.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!table.getSelectionModel().isEmpty()) {
					getScreenController().showUpdateCreditCardTransactionScreen(table.getSelectionModel().getSelectedItem());
				}
			}
		});
		
		totalItemsField = new Label();
		totalAmountField = new Label();
	}

	public void updateDisplay() {
		List<CreditCardTransaction> transactions = creditCardTransactionService.getAllCreditCardTransactions();
		table.setItems(FXCollections.observableList(transactions));
		updateFields(transactions);
	}

	private void updateFields(List<CreditCardTransaction> transactions) {
		totalItemsField.setText(String.valueOf(transactions.size()));
		totalAmountField.setText(FormatterUtil.formatAmount(getTotalAmount(transactions)));
	}

	private static BigDecimal getTotalAmount(List<CreditCardTransaction> transactions) {
		BigDecimal total = Constants.ZERO;
		for (CreditCardTransaction transaction : transactions) {
			total = total.add(transaction.getAmount());
		}
		return total;
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
			List<CreditCardTransaction> transactions = creditCardTransactionService.searchCreditCardTransactions(criteria);
			table.setItems(FXCollections.observableList(transactions));
			updateFields(transactions);
		}
	}

	@Override
	protected void doOnBack() {
		getScreenController().showMainScreen();
	}
	
}