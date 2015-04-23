package com.pj.creditcardmanagement.util;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class LayoutUtil {

	public static void setColumnPercentWidths(GridPane grid, int... percentWidths) {
		List<ColumnConstraints> constraints = new ArrayList<>();
		for (int width : percentWidths) {
			ColumnConstraints constraint = new ColumnConstraints();
			constraint.setPercentWidth(width);
			constraints.add(constraint);
		}
		
		grid.getColumnConstraints().clear();
		grid.getColumnConstraints().addAll(constraints.toArray(new ColumnConstraints[] {}));
	}
	
}