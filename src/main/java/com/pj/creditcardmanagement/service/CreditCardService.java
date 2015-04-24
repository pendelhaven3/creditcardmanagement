package com.pj.creditcardmanagement.service;

import java.util.List;

import com.pj.creditcardmanagement.model.CreditCard;

/**
 * 
 * @author PJ Miranda
 *
 */
public interface CreditCardService {

	List<CreditCard> getAllCreditCards();

	void save(CreditCard creditCard);

	CreditCard getCreditCard(long id);

	void delete(CreditCard creditCard);

	CreditCard getCreditCardByName(String name);
	
}