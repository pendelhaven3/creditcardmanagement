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

import com.pj.creditcardmanagement.dao.PurchaseTypeDao;
import com.pj.creditcardmanagement.model.PurchaseType;

@Repository
public class PurchaseTypeDaoImpl implements PurchaseTypeDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<PurchaseType> getAll() {
        return entityManager.createQuery("SELECT pt FROM PurchaseType pt order by pt.description", PurchaseType.class)
        		.getResultList();
	}

	@Override
	public void save(PurchaseType purchaseType) {
		if (purchaseType.getId() == null) {
			entityManager.persist(purchaseType);
		} else {
			entityManager.merge(purchaseType);
		}
	}

	@Override
	public PurchaseType get(long id) {
		return entityManager.find(PurchaseType.class, id);
	}

	@Override
	public void delete(PurchaseType purchaseType) {
		entityManager.remove(get(purchaseType.getId()));
	}

	@Override
	public PurchaseType findByDescription(String description) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PurchaseType> criteria = builder.createQuery(PurchaseType.class);
		Root<PurchaseType> transaction = criteria.from(PurchaseType.class);
		criteria.where(transaction.get("description").in(description));
		
		try {
			return entityManager.createQuery(criteria).getSingleResult();
		} catch (NoResultException | NonUniqueResultException e) {
			return null;
		}
	}

}
