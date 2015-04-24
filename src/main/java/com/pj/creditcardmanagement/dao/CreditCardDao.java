package com.pj.creditcardmanagement.dao;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCard;

public interface CreditCardDao {

	void save(CreditCard creditCard);

	List<CreditCard> getAll();

	CreditCard get(long id);

	void delete(CreditCard creditCard);

	CreditCard findByName(String name);
	
}