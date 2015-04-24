package com.pj.creditcardmanagement.dao;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCardTransaction;

public interface CreditCardTransactionDao {

	void save(CreditCardTransaction transaction);

	List<CreditCardTransaction> getAll();

	CreditCardTransaction get(long id);

	void delete(CreditCardTransaction transaction);
	
}