package com.pj.creditcardmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.creditcardmanagement.Parameter;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.screen.ShowDialog;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.util.DateUtil;
import com.pj.creditcardmanagement.util.FormatterUtil;
import com.pj.creditcardmanagement.util.NumberUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

@Controller
@Scope("prototype")
public class PaymentController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired private CreditCardService creditCardService;
	
	@FXML private ComboBox<CreditCard> creditCardComboBox;
	@FXML private DatePicker paymentDatePicker;
	@FXML private TextField amountField;
	
	@Parameter private CreditCardPayment payment;
	
	@FXML 
	public void doOnBack() {
		screenController.showPaymentsListScreen();
	}

	@FXML
	public void savePayment() {
		if (!validateFields()) {
			return;
		}
		
		if (payment == null) {
			payment = new CreditCardPayment();
		}
		payment.setCreditCard(creditCardComboBox.getValue());
		payment.setPaymentDate(DateUtil.toDate(paymentDatePicker.getValue()));
		payment.setAmount(NumberUtil.toBigDecimal(amountField.getText()));
		
		try {
			creditCardService.save(payment);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Payment saved");
		screenController.showPaymentsListScreen();
	}

	private boolean validateFields() {
		if (creditCardComboBox.getValue() == null) {
			ShowDialog.error("Credit Card must be specified");
			creditCardComboBox.requestFocus();
			return false;
		}
		
		if (paymentDatePicker.getValue() == null) {
			ShowDialog.error("Payment Date must be specified");
			return false;
		}
		
		if (amountField.getText().isEmpty()) {
			ShowDialog.error("Amount must be specified");
			amountField.requestFocus();
			return false;
		}
		
		if (!NumberUtil.isAmount(amountField.getText())) {
			ShowDialog.error("Amount must be valid");
			amountField.requestFocus();
			return false;
		}
		
		return true;
	}

	@Override
	public void updateDisplay() {
		creditCardComboBox.setItems(FXCollections.observableList(creditCardService.getAllCreditCards()));
		
		if (payment != null) {
			creditCardComboBox.setValue(payment.getCreditCard());
			paymentDatePicker.setValue(DateUtil.toLocalDate(payment.getPaymentDate()));
			amountField.setText(FormatterUtil.formatAmount(payment.getAmount()));
		}
	}
	
}
