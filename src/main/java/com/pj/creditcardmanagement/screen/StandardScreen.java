package com.pj.creditcardmanagement.screen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

/**
 * 
 * @author PJ Miranda
 *
 */
public abstract class StandardScreen extends AbstractScreen {

	private ToolBar toolBar = new ToolBar();
	private GridPane mainGrid;
	private GridPane contentGrid;
	private Button backButton = new Button("Back");

	public StandardScreen() {
		backButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				doOnBack();
			}
		});
	}
	
	@Override
	public Scene getScene(double width, double height) {
		mainGrid = new GridPane();
		mainGrid.setAlignment(Pos.CENTER);
		
		contentGrid = new GridPane();
		contentGrid.setAlignment(Pos.CENTER);
		
		initializeComponents();
		createStandardLayout();
		addToolBarButtons(toolBar);
		layoutComponents(contentGrid);
		
		return new Scene(mainGrid, width, height);
	}
	
	protected abstract void addToolBarButtons(ToolBar toolBar);

	protected abstract void doOnBack();

	private void createStandardLayout() {
		toolBar.getItems().clear();
		toolBar.getItems().add(backButton);
		
		mainGrid.add(toolBar, 0, 0);
		mainGrid.add(contentGrid, 0, 1);
		
		GridPane.setHgrow(toolBar, Priority.SOMETIMES);
		GridPane.setHgrow(contentGrid, Priority.SOMETIMES);
		GridPane.setVgrow(contentGrid, Priority.SOMETIMES);
	}
	
}