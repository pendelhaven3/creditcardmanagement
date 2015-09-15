package com.pj.creditcardmanagement.service;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.model.CreditCardPaymentSearchCriteria;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.PurchaseType;

public interface CreditCardService {

	List<CreditCard> getAllCreditCards();

	void save(CreditCard creditCard);

	CreditCard getCreditCard(long id);

	void delete(CreditCard creditCard);

	CreditCard getCreditCardByName(String name);
	
	List<CreditCardPayment> getAllCreditCardPayments();

	void save(CreditCardPayment payment);

	CreditCardPayment getCreditCardPayment(long id);

	void delete(CreditCardPayment payment);

	List<CreditCardPayment> searchCreditCardPayments(CreditCardPaymentSearchCriteria criteria);

	void save(PurchaseType purchaseType);

	PurchaseType getPurchaseType(long id);

	void delete(PurchaseType purchaseType);

	PurchaseType findPurchaseTypeByDescription(String description);

	List<CreditCardTransaction> findAllTransactionsByPurchaseType(PurchaseType purchaseType);

	CreditCardTransaction getCreditCardTransaction(long id);

	List<PurchaseType> getAllPurchaseTypes();

	void save(CreditCardTransaction transaction);

	void delete(CreditCardTransaction transaction);

}