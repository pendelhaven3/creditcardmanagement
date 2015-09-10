package com.pj.creditcardmanagement.dao;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.model.CreditCardPaymentSearchCriteria;

public interface CreditCardPaymentDao {

	List<CreditCardPayment> getAll();

	void save(CreditCardPayment payment);

	CreditCardPayment get(long id);

	void delete(CreditCardPayment payment);

	List<CreditCardPayment> search(CreditCardPaymentSearchCriteria criteria);
	
}
