package com.pj.creditcardmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pj.creditcardmanagement.component.DoubleClickEventHandler;
import com.pj.creditcardmanagement.model.PurchaseType;
import com.pj.creditcardmanagement.service.PurchaseTypeService;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

@Controller
public class PurchaseTypeListController extends AbstractController {

	@Autowired private PurchaseTypeService purchaseTypeService;
	
	@FXML private TableView<PurchaseType> purchaseTypesTable;
	
	@Override
	public void updateDisplay() {
		purchaseTypesTable.setItems(FXCollections.observableList(
				purchaseTypeService.getAllPurchaseTypes()));
		
		purchaseTypesTable.setOnMouseClicked(new DoubleClickEventHandler() {
			
			@Override
			protected void onDoubleClick(MouseEvent event) {
				if (!purchaseTypesTable.getSelectionModel().isEmpty()) {
					screenController.showUpdatePurchaseTypeScreen(
							purchaseTypesTable.getSelectionModel().getSelectedItem());
				}
			}
		} );
	}

	@FXML public void doOnBack() {
		screenController.showMainScreen();
	}

	@FXML public void addPurchaseType() {
		screenController.showAddPurchaseTypeScreen();
	}

}
