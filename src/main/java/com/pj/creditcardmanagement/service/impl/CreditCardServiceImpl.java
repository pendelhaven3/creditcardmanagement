package com.pj.creditcardmanagement.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.creditcardmanagement.dao.CreditCardDao;
import com.pj.creditcardmanagement.dao.CreditCardPaymentDao;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.service.CreditCardService;

@Service
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired private CreditCardDao creditCardDao;
	@Autowired private CreditCardPaymentDao creditCardPaymentDao;
	
	@Override
	public List<CreditCard> getAllCreditCards() {
		return creditCardDao.getAll();
	}

	@Transactional
	@Override
	public void save(CreditCard creditCard) {
		creditCardDao.save(creditCard);
	}

	@Override
	public CreditCard getCreditCard(long id) {
		return creditCardDao.get(id);
	}

	@Transactional
	@Override
	public void delete(CreditCard creditCard) {
		creditCardDao.delete(creditCard);
	}

	@Override
	public CreditCard getCreditCardByName(String name) {
		return creditCardDao.findByName(name);
	}

	@Override
	public List<CreditCardPayment> getAllCreditCardPayments() {
		return creditCardPaymentDao.getAll();
	}

	@Transactional
	@Override
	public void save(CreditCardPayment payment) {
		creditCardPaymentDao.save(payment);
	}
	
}