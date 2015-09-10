package com.pj.creditcardmanagement.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.creditcardmanagement.component.DoubleClickEventHandler;
import com.pj.creditcardmanagement.dialog.SearchCreditCardPaymentsDialog;
import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.model.CreditCardPaymentSearchCriteria;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.util.FormatterUtil;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Callback;

@Controller
@Scope("prototype")
public class PaymentListController extends AbstractController {

	@Autowired private CreditCardService creditCardService;
	@Autowired private SearchCreditCardPaymentsDialog filterCreditCardPaymentsDialog;
	
	@FXML private TableView<CreditCardPayment> paymentsTable;
	@FXML private TableColumn<CreditCardPayment, String> paymentDateTableColumn;
	@FXML private TableColumn<CreditCardPayment, String> amountTableColumn;
	@FXML private Text totalItemsText;
	@FXML private Text totalAmountText;
	
	@FXML 
	public void doOnBack() {
		screenController.showMainScreen();
	}

	@FXML 
	public void addPayment() {
		screenController.showAddPaymentScreen();
	}

	@Override
	public void updateDisplay() {
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
		
		paymentsTable.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!paymentsTable.getSelectionModel().isEmpty()) {
					screenController.showUpdatePaymentScreen(paymentsTable.getSelectionModel().getSelectedItem());
				}
			}
		} );
		
		updateTotalFields(payments);
	}

	private void updateTotalFields(List<CreditCardPayment> payments) {
		totalItemsText.setText(String.valueOf(payments.size()));
		totalAmountText.setText(FormatterUtil.formatAmount(getTotalAmount(payments)));
	}

	private static BigDecimal getTotalAmount(List<CreditCardPayment> payments) {
		BigDecimal total = BigDecimal.ZERO;
		for (CreditCardPayment payment : payments) {
			total = total.add(payment.getAmount());
		}
		return total;
	}

	@FXML 
	public void openSearchPaymentsDialog() {
		filterCreditCardPaymentsDialog.showAndWait();
	
		CreditCardPaymentSearchCriteria criteria = 
				filterCreditCardPaymentsDialog.getCreditCardPaymentSearchCriteria();
		if (criteria != null) {
			List<CreditCardPayment> payments = creditCardService.searchCreditCardPayments(criteria);
			paymentsTable.setItems(FXCollections.observableList(payments));
			updateTotalFields(payments);
		}
	}

}
