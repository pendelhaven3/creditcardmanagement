package com.pj.creditcardmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.util.FormatterUtil;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

@Controller
public class PaymentListController extends AbstractController {

	@Autowired private CreditCardService creditCardService;
	
	@FXML private TableView<CreditCardPayment> paymentsTable;
	@FXML private TableColumn<CreditCardPayment, String> paymentDateTableColumn;
	@FXML private TableColumn<CreditCardPayment, String> amountTableColumn;
	
	@FXML 
	public void doOnBack() {
		screenController.showMainScreen();
	}

	@FXML
	private void initialize() {
		List<CreditCardPayment> payments = creditCardService.getAllCreditCardPayments();
		paymentsTable.setItems(FXCollections.observableList(payments));
		
		paymentDateTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CreditCardPayment,String>, ObservableValue<String>>() {
					
			@Override
			public ObservableValue<String> call(CellDataFeatures<CreditCardPayment, String> param) {
				return new ReadOnlyStringWrapper(
						FormatterUtil.formatDate(param.getValue().getPaymentDate()));
			}
		});
		
		amountTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CreditCardPayment,String>, ObservableValue<String>>() {
					
			@Override
			public ObservableValue<String> call(CellDataFeatures<CreditCardPayment, String> param) {
				return new ReadOnlyStringWrapper(
						FormatterUtil.formatAmount(param.getValue().getAmount()));
			}
		});
	}

	@FXML 
	public void addPayment() {
		screenController.showAddPaymentScreen();
	}

}
