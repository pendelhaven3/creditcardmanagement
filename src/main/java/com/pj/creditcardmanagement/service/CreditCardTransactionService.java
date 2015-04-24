package com.pj.creditcardmanagement.service;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.CreditCardTransactionSearchCriteria;

/**
 * 
 * @author PJ Miranda
 *
 */
public interface CreditCardTransactionService {

	List<CreditCardTransaction> getAllCreditCardTransactions();

	void save(CreditCardTransaction transaction);

	CreditCardTransaction getCreditCardTransaction(long id);

	void delete(CreditCardTransaction transaction);

	List<CreditCardTransaction> searchCreditCardTransactions(CreditCardTransactionSearchCriteria criteria);
	
}