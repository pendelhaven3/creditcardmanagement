package com.pj.creditcardmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.service.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Override
	public List<CreditCard> getAllCreditCards() {
		List<CreditCard> creditCards = new ArrayList<CreditCard>();
		
		CreditCard creditCard1 = new CreditCard();
		creditCard1.setName("PERSONAL");
		creditCard1.setBank("BDO");
		creditCard1.setCardNumber("1234567890123456");
		creditCards.add(creditCard1);
		
		return creditCards;
	}
	
}