package com.pj.creditcardmanagement.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.pj.creditcardmanagement.dao.CreditCardTransactionDao;
import com.pj.creditcardmanagement.model.CreditCardTransaction;
import com.pj.creditcardmanagement.model.CreditCardTransactionSearchCriteria;

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

	@Override
	public void delete(CreditCardTransaction transaction) {
		entityManager.remove(get(transaction.getId()));
	}

	@Override
	public List<CreditCardTransaction> search(CreditCardTransactionSearchCriteria criteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CreditCardTransaction> queryCriteria = builder.createQuery(CreditCardTransaction.class);
		Root<CreditCardTransaction> transaction = queryCriteria.from(CreditCardTransaction.class);
		
		List<Predicate> predicates = new ArrayList<>();
		if (criteria.getCreditCard() != null) {
			predicates.add(builder.equal(transaction.get("creditCard"), criteria.getCreditCard()));
		}
		if (criteria.getTransactionDateFrom() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(transaction.<Date>get("transactionDate"), criteria.getTransactionDateFrom()));
		}
		if (criteria.getTransactionDateTo() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(transaction.<Date>get("transactionDate"), criteria.getTransactionDateTo()));
		}
		queryCriteria.where(predicates.toArray(new Predicate[]{}));
		
		try {
			return entityManager.createQuery(queryCriteria).getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}

}