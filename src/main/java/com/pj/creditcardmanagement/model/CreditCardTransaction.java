package com.pj.creditcardmanagement.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * 
 * @author PJ Miranda
 *
 */
@Entity
public class CreditCardTransaction {

	@Id
	@GeneratedValue
	private Long id;

	@OneToOne
	private CreditCard creditCard;

	private BigDecimal amount;
	private Date transactionDate;
	
	@OneToOne
	private PurchaseType purchaseType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public boolean isNew() {
		return id == null;
	}

	public PurchaseType getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(PurchaseType purchaseType) {
		this.purchaseType = purchaseType;
	}

}