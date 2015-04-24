package com.pj.creditcardmanagement.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.creditcardmanagement.dao.CreditCardTransactionDao;
import com.pj.creditcardmanagement.model.CreditCard;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.service.CreditCardTransactionService;

/**
 * 
 * @author PJ Miranda
 *
 */
@Service
public class CreditCardTransactionServiceImpl implements CreditCardTransactionService {

	@Autowired private CreditCardTransactionDao creditCardTransactionDao;
	
	@Override
	public List<CreditCardTransaction> getAllCreditCardTransactions() {
		List<CreditCardTransaction> transactions = new ArrayList<CreditCardTransaction>();
		
		CreditCardTransaction transaction = new CreditCardTransaction();
		transaction.setCreditCard(new CreditCard());
		transaction.setAmount(new BigDecimal("12345.15"));
		transaction.setDate(new Date());
		transactions.add(transaction);
		
//		return creditCardTransactionDao.getAll();
		return transactions;
	}

	@Transactional
	@Override
	public void save(CreditCardTransaction transaction) {
		creditCardTransactionDao.save(transaction);
	}

	@Override
	public CreditCardTransaction getCreditCardTransaction(long id) {
		return creditCardTransactionDao.get(id);
	}
	
}