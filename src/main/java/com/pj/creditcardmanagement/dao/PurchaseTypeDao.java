package com.pj.creditcardmanagement.dao;

import java.util.List;

import com.pj.creditcardmanagement.model.PurchaseType;

public interface PurchaseTypeDao {

	List<PurchaseType> getAll();

	void save(PurchaseType purchaseType);

	PurchaseType get(long id);

	void delete(PurchaseType purchaseType);

	PurchaseType findByDescription(String description);
	
}
