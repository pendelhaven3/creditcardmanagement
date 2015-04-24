package com.pj.creditcardmanagement.screen;

import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author PJ Miranda
 *
 */
public abstract class AbstractScreen {

	@Autowired private ScreenController screenController;
	
	protected abstract void initializeComponents();
	
	protected abstract void layoutComponents(GridPane grid);
	
	public Scene getScene(double width, double height) {
		GridPane mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.CENTER);
		
		initializeComponents();
		layoutComponents(mainGrid);
		
		return new Scene(mainGrid, width, height);
	}
	
	public ScreenController getScreenController() {
		return screenController;
	}
	
	public boolean confirm(String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText(message);
		
		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}
	
}