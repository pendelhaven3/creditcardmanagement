package com.pj.creditcardmanagement.dialog;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorDialog extends Stage {

	private static ErrorDialog instance = null;
	
	private Text messageField = new Text();
	
	public ErrorDialog() {
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		
		GridPane grid = new GridPane();
		grid.add(messageField, 0, 0);
		grid.setAlignment(Pos.CENTER);
		
		setScene(new Scene(grid, 300, 100));
		setTitle("Error");
	}
	
	public void setMessage(String message) {
		messageField.setText(message);
	}
	
	public static void show(String message) {
		if (instance == null) {
			instance = new ErrorDialog();
		}
		instance.setMessage(message);
		instance.showAndWait();
	}
	
}