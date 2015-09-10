package com.pj.creditcardmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.pj.creditcardmanagement.screen.ScreenController;

public abstract class AbstractController {

	@Autowired protected ScreenController screenController;
	
	public abstract void updateDisplay();
	
}
