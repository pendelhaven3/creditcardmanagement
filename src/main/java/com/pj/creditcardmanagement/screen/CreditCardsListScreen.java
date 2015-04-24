package com.pj.creditcardmanagement.screen;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.component.DoubleClickEventHandler;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.service.CreditCardService;

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
	private Label totalItemsField;

	@Override
	public void layoutComponents(GridPane grid) {
		grid.setVgap(10);
		
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
		GridPane.setHgrow(table, Priority.ALWAYS);
		GridPane.setVgrow(table, Priority.ALWAYS);
		
		grid.add(totalItemsField, 0, 1);
		GridPane.setHgrow(totalItemsField, Priority.ALWAYS);
		GridPane.setHalignment(totalItemsField, HPos.CENTER);
		GridPane.setMargin(totalItemsField, new Insets(0, 0, 5, 0));
	}

	@Override
	public void initializeComponents() {
		table = new TableView<>();
		table.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!table.getSelectionModel().isEmpty()) {
					getScreenController().showUpdateCreditCardScreen(table.getSelectionModel().getSelectedItem());
				}
			}
		});
		
		totalItemsField = new Label();
	}

	public void updateDisplay() {
		List<CreditCard> creditCards = creditCardService.getAllCreditCards();
		table.setItems(FXCollections.observableList(creditCards));
		totalItemsField.setText("Total Items:   " + String.valueOf(creditCards.size()));
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