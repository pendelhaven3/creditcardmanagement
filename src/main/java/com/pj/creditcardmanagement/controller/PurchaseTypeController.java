	package com.pj.creditcardmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.pj.creditcardmanagement.Parameter;
import com.pj.creditcardmanagement.model.PurchaseType;
import com.pj.creditcardmanagement.screen.ShowDialog;
import com.pj.creditcardmanagement.service.CreditCardService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

@Controller
@Scope("prototype")
public class PurchaseTypeController extends AbstractController {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseTypeController.class);
	
	@Autowired private CreditCardService creditCardService;
	
	@FXML private TextField descriptionField;
	@FXML private Button deleteButton;
	
	@Parameter private PurchaseType purchaseType;
	
	@FXML 
	public void doOnBack() {
		screenController.showPurchaseTypesListScreen();
	}

	@FXML
	public void savePurchaseType() {
		if (!validateFields()) {
			return;
		}
		
		if (purchaseType == null) {
			purchaseType = new PurchaseType();
		}
		purchaseType.setDescription(descriptionField.getText());
		
		try {
			creditCardService.save(purchaseType);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ShowDialog.unexpectedError();
			return;
		}
		
		ShowDialog.info("Purchase Type saved");
		screenController.showPurchaseTypesListScreen();
	}

	private boolean validateFields() {
		if (descriptionField.getText().isEmpty()) {
			ShowDialog.error("Description must be specified");
			descriptionField.requestFocus();
			return false;
		}
		
		if (isDescriptionAlreadyUsed(descriptionField.getText())) {
			ShowDialog.error("Description is already used by an existing record");
			descriptionField.requestFocus();
			return false;
		}
		
		return true;
	}

	private boolean isDescriptionAlreadyUsed(String description) {
		PurchaseType purchaseType = creditCardService.findPurchaseTypeByDescription(description);
		if (purchaseType != null) {
			if (this.purchaseType != null) {
				return !purchaseType.getId().equals(this.purchaseType.getId());
			} else {
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateDisplay() {
		if (purchaseType != null) {
			screenController.setTitle("Update Purchase Type");
			purchaseType = creditCardService.getPurchaseType(purchaseType.getId());
			
			descriptionField.setText(purchaseType.getDescription());
			deleteButton.setVisible(true);
		} else {
			screenController.setTitle("Add Purchase Type");
		}
		descriptionField.requestFocus();
	}

	@FXML 
	public void deletePurchaseType() {
		if (isPurchaseTypeUsed()) {
			ShowDialog.error("Purchase Type is used by an existing transaction");
			return;
		}
		
		if (ShowDialog.confirm("Do you want to delete this purchase type?")) {
			try {
				creditCardService.delete(purchaseType);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				ShowDialog.unexpectedError();
				return;
			}
			
			ShowDialog.info("Purchase Type deleted");
			screenController.showPurchaseTypesListScreen();
		}
	}

	private boolean isPurchaseTypeUsed() {
		return !creditCardService.findAllTransactionsByPurchaseType(purchaseType).isEmpty();
	}

}
