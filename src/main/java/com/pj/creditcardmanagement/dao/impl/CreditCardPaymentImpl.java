package com.pj.creditcardmanagement.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.pj.creditcardmanagement.dao.CreditCardPaymentDao;
import com.pj.creditcardmanagement.model.CreditCardPayment;

/**
 * 
 * @author PJ Miranda
 *
 */
@Repository
public class CreditCardPaymentImpl implements CreditCardPaymentDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<CreditCardPayment> getAll() {
        return entityManager.createQuery("SELECT p FROM CreditCardPayment p order by p.paymentDate desc", 
        		CreditCardPayment.class)
        		.getResultList();
	}

	@Override
	public void save(CreditCardPayment payment) {
		if (payment.getId() == null) {
			entityManager.persist(payment);
		} else {
			entityManager.merge(payment);
		}
	}

	@Override
	public CreditCardPayment get(long id) {
		return entityManager.find(CreditCardPayment.class, id);
	}

	@Override
	public void delete(CreditCardPayment payment) {
		entityManager.remove(get(payment.getId()));
	}

}