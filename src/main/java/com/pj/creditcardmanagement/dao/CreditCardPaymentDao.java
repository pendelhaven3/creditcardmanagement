package com.pj.creditcardmanagement.dao;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCardPayment;

public interface CreditCardPaymentDao {

	List<CreditCardPayment> getAll();

	void save(CreditCardPayment payment);
	
}
