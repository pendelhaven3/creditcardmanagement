package com.pj.creditcardmanagement.screen;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.sun.javafx.collections.ObservableListWrapper;

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class CreditCardsListScreen extends StandardScreen {

	@Autowired private CreditCardService creditCardService;
	
	private TableView<CreditCard> table;
	private Button addButton = new Button("Add");

	@Override
	public void layoutComponents(GridPane grid) {
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<CreditCard, String> cardNameColumn = new TableColumn<>("Card Name");
		cardNameColumn.setCellValueFactory(new PropertyValueFactory<CreditCard, String>("name"));
		
		TableColumn<CreditCard, String> bankColumn = new TableColumn<>("Bank");
		bankColumn.setCellValueFactory(new PropertyValueFactory<CreditCard, String>("bank"));
		
		TableColumn<CreditCard, String> cardNumberColumn = new TableColumn<>("Card Number");
		cardNumberColumn.setCellValueFactory(new PropertyValueFactory<CreditCard, String>("cardNumber"));

		table.getColumns().add(cardNameColumn);
		table.getColumns().add(bankColumn);
		table.getColumns().add(cardNumberColumn);

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
				getScreenController().showUpdateCreditCardScreen(table.getSelectionModel().getSelectedItem());
			}
		});
	}

	public void updateDisplay() {
		ObservableList<CreditCard> creditCards = 
				new ObservableListWrapper<CreditCard>(creditCardService.getAllCreditCards());
		table.setItems(creditCards);
	}

	@Override
	protected void addToolBarButtons(ToolBar toolBar) {
		toolBar.getItems().add(addButton);
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				getScreenController().showAddCreditCardScreen();
			}
		});
	}

	@Override
	protected void doOnBack() {
		getScreenController().showMainScreen();
	}
	
}