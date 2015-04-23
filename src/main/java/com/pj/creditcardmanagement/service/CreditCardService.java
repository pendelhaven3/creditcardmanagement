package com.pj.creditcardmanagement.service;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCard;

public interface CreditCardService {

	List<CreditCard> getAllCreditCards();

	void save(CreditCard creditCard);

	CreditCard getCreditCard(long id);
	
}