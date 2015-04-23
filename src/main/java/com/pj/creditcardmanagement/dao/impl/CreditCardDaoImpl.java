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

import com.pj.creditcardmanagement.dao.CreditCardDao;
import com.pj.creditcardmanagement.model.CreditCard;

/**
 * 
 * @author PJ Miranda
 *
 */
@Repository
public class CreditCardDaoImpl implements CreditCardDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(CreditCard creditCard) {
		if (creditCard.getId() == null) {
			entityManager.persist(creditCard);
		} else {
			entityManager.merge(creditCard);
		}
	}

	@Override
	public List<CreditCard> getAll() {
        return entityManager.createQuery("SELECT c FROM CreditCard c order by c.name", CreditCard.class)
        		.getResultList();
	}

	@Override
	public CreditCard get(long id) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CreditCard> criteria = builder.createQuery(CreditCard.class);
		Root<CreditCard> creditCard = criteria.from(CreditCard.class);
		criteria.where(creditCard.get("id").in(id));
		
		try {
			return entityManager.createQuery(criteria).getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}

}