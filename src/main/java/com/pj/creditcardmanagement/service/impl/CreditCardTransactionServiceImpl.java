package com.pj.creditcardmanagement.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.creditcardmanagement.dao.CreditCardTransactionDao;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.CreditCardTransactionSearchCriteria;
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
		return creditCardTransactionDao.getAll();
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

	@Transactional
	@Override
	public void delete(CreditCardTransaction transaction) {
		creditCardTransactionDao.delete(transaction);
	}

	@Override
	public List<CreditCardTransaction> searchCreditCardTransactions(CreditCardTransactionSearchCriteria criteria) {
		return creditCardTransactionDao.search(criteria);
	}
	
}