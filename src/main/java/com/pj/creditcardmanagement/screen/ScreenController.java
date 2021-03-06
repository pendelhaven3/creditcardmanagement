package com.pj.creditcardmanagement.screen;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pj.creditcardmanagement.ControllerFactory;
import com.pj.creditcardmanagement.Parameter;
import com.pj.creditcardmanagement.controller.AbstractController;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.PurchaseType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@Component
public class ScreenController {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;
	
	@Autowired private ControllerFactory controllerFactory;
	@Autowired private CreditCardsListScreen creditCardsListScreen;
	@Autowired private CreditCardScreen creditCardScreen;
	@Autowired private CreditCardTransactionsListScreen creditCardTransactionsListScreen;
	
	private Stage stage;
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void showMainScreen() {
		stage.setTitle("Credit Card Management");
		loadSceneFromFXML("main");
		if (!stage.isShowing()) {
			stage.show();
		}
	}

	public void showCreditCardsListScreen() {
		stage.setTitle("Credit Cards List");
		showScreen(creditCardsListScreen);
		creditCardsListScreen.updateDisplay();
	}

	private void loadSceneFromFXML(String file) {
		loadSceneFromFXML(file, null);
	}
	
	private void loadSceneFromFXML(String file, Map<String, Object> model) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setControllerFactory(controllerFactory);
		
		Parent root = null;
		try {
			root = fxmlLoader.load(getClass().getResourceAsStream("/fxml/" + file + ".fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		stage.setScene(new Scene(root, WIDTH, HEIGHT));
		
		if (fxmlLoader.getController() instanceof AbstractController) {
			AbstractController controller = (AbstractController)fxmlLoader.getController();
			if (model != null && !model.isEmpty()) {
				mapParameters(controller, model);
			}
			controller.updateDisplay();
		}
	}

	private void mapParameters(AbstractController controller, Map<String, Object> model) {
		Class<? extends AbstractController> clazz = controller.getClass();
		for (String key : model.keySet()) {
			try {
				Field field = clazz.getDeclaredField(key);
				if (field != null && field.getAnnotation(Parameter.class) != null) {
					field.setAccessible(true);
					field.set(controller, model.get(key));
				}
			} catch (Exception e) {
				System.out.println("Error setting parameter " + key);
			}
		}
	}

	private void showScreen(AbstractScreen screen) {
		stage.setScene(screen.getScene(WIDTH, HEIGHT));
	}
	
	public void showAddCreditCardScreen() {
		stage.setTitle("Add Credit Card");
		showScreen(creditCardScreen);
		creditCardScreen.updateDisplay(new CreditCard());
	}

	public void showUpdateCreditCardScreen(CreditCard creditCard) {
		stage.setTitle("Update Credit Card");
		showScreen(creditCardScreen);
		creditCardScreen.updateDisplay(creditCard);
	}

	public void showCreditCardTransactionsListScreen() {
		stage.setTitle("Credit Card Transactions List");
		showScreen(creditCardTransactionsListScreen);
		creditCardTransactionsListScreen.updateDisplay();
	}

	public void showUpdateCreditCardTransactionScreen(CreditCardTransaction transaction) {
		stage.setTitle("Update Credit Card Transaction");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("transaction", transaction);
		
		loadSceneFromFXML("transaction", paramMap);
	}

	public void showAddCreditCardTransactionScreen() {
		stage.setTitle("Add Credit Card Transaction");
		loadSceneFromFXML("transaction");
	}

	public void showPaymentsListScreen() {
		stage.setTitle("Credit Card Payments List");
		loadSceneFromFXML("paymentList");
	}

	public void showAddPaymentScreen() {
		stage.setTitle("Add Credit Card Payment");
		loadSceneFromFXML("payment");
	}

	public void showUpdatePaymentScreen(CreditCardPayment payment) {
		stage.setTitle("Update Credit Card Payment");
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("payment", payment);
		
		loadSceneFromFXML("payment", paramMap);
	}

	public void showPurchaseTypesListScreen() {
		loadSceneFromFXML("purchaseTypeList");
	}

	public void showAddPurchaseTypeScreen() {
		loadSceneFromFXML("purchaseType");
	}

	public void setTitle(String title) {
		stage.setTitle(title);
	}

	public void showUpdatePurchaseTypeScreen(PurchaseType purchaseType) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("purchaseType", purchaseType);
		
		loadSceneFromFXML("purchaseType", paramMap);
	}
	
}