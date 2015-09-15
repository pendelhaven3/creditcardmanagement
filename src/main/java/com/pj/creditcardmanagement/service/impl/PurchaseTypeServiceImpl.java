package com.pj.creditcardmanagement.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pj.creditcardmanagement.dao.PurchaseTypeDao;
import com.pj.creditcardmanagement.model.PurchaseType;
import com.pj.creditcardmanagement.service.PurchaseTypeService;

@Service
public class PurchaseTypeServiceImpl implements PurchaseTypeService {

	@Autowired private PurchaseTypeDao purchaseTypeDao;
	
	@Override
	public List<PurchaseType> getAllPurchaseTypes() {
		return purchaseTypeDao.getAll();
	}

}
