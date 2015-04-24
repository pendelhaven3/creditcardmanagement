package com.pj.creditcardmanagement.service;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCardTransaction;

public interface CreditCardTransactionService {

	List<CreditCardTransaction> getAllCreditCardTransactions();

	void save(CreditCardTransaction transaction);

	CreditCardTransaction getCreditCardTransaction(long id);

	void delete(CreditCardTransaction transaction);
	
}