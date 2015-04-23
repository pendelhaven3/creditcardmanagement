package com.pj.creditcardmanagement.screen;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author PJ Miranda
 *
 */
public abstract class AbstractScreen {

	@Autowired private ScreenController screenController;
	
	private GridPane gridPane = new GridPane();
	
	@PostConstruct
	public final void initialize() {
		initializeComponents();
		layoutComponents(gridPane);
	}
	
	public abstract void layoutComponents(GridPane grid);
	
	public GridPane getGridPane() {
		gridPane.setAlignment(Pos.CENTER);
		return gridPane;
	}
	
	@PostConstruct
	public abstract void initializeComponents();
	
	public ScreenController getScreenController() {
		return screenController;
	}
	
}