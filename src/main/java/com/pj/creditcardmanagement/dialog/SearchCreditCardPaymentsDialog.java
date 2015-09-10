package com.pj.creditcardmanagement.dialog;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardPaymentSearchCriteria;
import com.pj.creditcardmanagement.service.CreditCardService;
import com.pj.creditcardmanagement.util.DateUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Component
public class SearchCreditCardPaymentsDialog extends Stage {

	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;
	
	@Autowired private CreditCardService creditCardService;
	
	@FXML private ComboBox<CreditCard> creditCardComboBox;
	@FXML private DatePicker paymentDateFromPicker;
	@FXML private DatePicker paymentDateToPicker;
	@FXML private Button searchButton;
	
	private CreditCardPaymentSearchCriteria criteria;
	
	public SearchCreditCardPaymentsDialog() {
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Search Credit Card Payments");
	}
	
	@Override
	public void showAndWait() {
		setScene(null);
		setScene(loadSceneFromFXML());
		
		super.showAndWait();
	}

	private Scene loadSceneFromFXML() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setController(this);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/searchPayment.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return new Scene(root, WIDTH, HEIGHT);
	}

	@FXML
	public void initialize() {
		criteria = null;
		creditCardComboBox.setItems(FXCollections.observableList(creditCardService.getAllCreditCards()));
	}
	
	public CreditCardPaymentSearchCriteria getCreditCardPaymentSearchCriteria() {
		return criteria;
	}
	
	@FXML
	public void returnCriteria() {
		criteria = new CreditCardPaymentSearchCriteria();
		if (creditCardComboBox.getValue() != null) {
			criteria.setCreditCard(creditCardComboBox.getSelectionModel().getSelectedItem());
		}
		criteria.setPaymentDateFrom(DateUtil.toDate(paymentDateFromPicker.getValue()));
		criteria.setPaymentDateTo(DateUtil.toDate(paymentDateToPicker.getValue()));
		
		hide();
	}
	
}