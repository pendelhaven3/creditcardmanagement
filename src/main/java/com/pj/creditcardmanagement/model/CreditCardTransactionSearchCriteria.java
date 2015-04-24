package com.pj.creditcardmanagement.model;

import java.util.Date;

/**
 * 
 * @author PJ Miranda
 *
 */
public class CreditCardTransactionSearchCriteria {

	private CreditCard creditCard;
	private Date transactionDateFrom;
	private Date transactionDateTo;

	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public Date getTransactionDateFrom() {
		return transactionDateFrom;
	}

	public void setTransactionDateFrom(Date transactionDateFrom) {
		this.transactionDateFrom = transactionDateFrom;
	}

	public Date getTransactionDateTo() {
		return transactionDateTo;
	}

	public void setTransactionDateTo(Date transactionDateTo) {
		this.transactionDateTo = transactionDateTo;
	}

}