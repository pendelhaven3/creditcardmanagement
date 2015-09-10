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

import com.pj.creditcardmanagement.dao.CreditCardPaymentDao;
import com.pj.creditcardmanagement.model.CreditCardPayment;
import com.pj.creditcardmanagement.model.CreditCardPaymentSearchCriteria;

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

	@Override
	public List<CreditCardPayment> search(CreditCardPaymentSearchCriteria criteria) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CreditCardPayment> queryCriteria = builder.createQuery(CreditCardPayment.class);
		Root<CreditCardPayment> payment = queryCriteria.from(CreditCardPayment.class);
		
		List<Predicate> predicates = new ArrayList<>();
		if (criteria.getCreditCard() != null) {
			predicates.add(builder.equal(payment.get("creditCard"), criteria.getCreditCard()));
		}
		if (criteria.getPaymentDateFrom() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(payment.<Date>get("paymentDate"), criteria.getPaymentDateFrom()));
		}
		if (criteria.getPaymentDateTo() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(payment.<Date>get("paymentDate"), criteria.getPaymentDateTo()));
		}
		queryCriteria.where(predicates.toArray(new Predicate[]{}));
		
		try {
			return entityManager.createQuery(queryCriteria).getResultList();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}

}