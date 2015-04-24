package com.pj.creditcardmanagement.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.pj.creditcardmanagement.dao.CreditCardTransactionDao;
import com.pj.creditcardmanagement.model.CreditCardTransaction;

/**
 * 
 * @author PJ Miranda
 *
 */
@Repository
public class CreditCardTransactionDaoImpl implements CreditCardTransactionDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(CreditCardTransaction transaction) {
		if (transaction.getId() == null) {
			entityManager.persist(transaction);
		} else {
			entityManager.merge(transaction);
		}
	}

	@Override
	public List<CreditCardTransaction> getAll() {
        return entityManager.createQuery("SELECT t FROM CreditCardTransaction t order by t.creditCard.name", 
        		CreditCardTransaction.class).getResultList();
	}

	@Override
	public CreditCardTransaction get(long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CreditCardTransaction> criteria = builder.createQuery(CreditCardTransaction.class);
		Root<CreditCardTransaction> transaction = criteria.from(CreditCardTransaction.class);
		criteria.where(transaction.get("id").in(id));
		
		try {
			return entityManager.createQuery(criteria).getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}

}