package com.pj.creditcardmanagement.dialog;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransactionSearchCriteria;
import com.pj.creditcardmanagement.model.PurchaseType;
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

/**
 * 
 * @author PJ Miranda
 *
 */
@Component
public class SearchCreditCardTransactionsDialog extends Stage {

	private static final int WIDTH = 400;
	private static final int HEIGHT = 200;
	
	@Autowired private CreditCardService creditCardService;
	
	@FXML private ComboBox<CreditCard> creditCardComboBox;
	@FXML private DatePicker transactionDateFromPicker;
	@FXML private DatePicker transactionDateToPicker;
	@FXML private ComboBox<PurchaseType> purchaseTypeComboBox;
	@FXML private Button searchButton;
	
	private CreditCardTransactionSearchCriteria criteria;
	
	public SearchCreditCardTransactionsDialog() {
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		setTitle("Search Credit Card Transactions");
	}
	
	public void updateDisplay() {
		setScene(null);
		setScene(loadSceneFromFXML());
	}
	
	private Scene loadSceneFromFXML() {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setController(this);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/searchTransaction.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return new Scene(root, WIDTH, HEIGHT);
	}

	@FXML
	public void initialize() {
		criteria = null;
		creditCardComboBox.setItems(FXCollections.observableList(creditCardService.getAllCreditCards()));
		purchaseTypeComboBox.setItems(FXCollections.observableList(creditCardService.getAllPurchaseTypes()));
	}
	
	public CreditCardTransactionSearchCriteria getCreditCardTransactionSearchCriteria() {
		return criteria;
	}
	
	@FXML
	public void returnCriteria() {
		criteria = new CreditCardTransactionSearchCriteria();
		criteria.setCreditCard(creditCardComboBox.getValue());
		if (transactionDateFromPicker.getValue() != null) {
			criteria.setTransactionDateFrom(DateUtil.toDate(transactionDateFromPicker.getValue()));
		}
		if (transactionDateToPicker.getValue() != null) {
			criteria.setTransactionDateTo(DateUtil.toDate(transactionDateToPicker.getValue()));
		}
		criteria.setPurchaseType(purchaseTypeComboBox.getValue());
		
		hide();
	}
	
}