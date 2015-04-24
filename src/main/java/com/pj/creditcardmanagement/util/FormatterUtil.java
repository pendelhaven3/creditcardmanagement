package com.pj.creditcardmanagement.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 
 * @author PJ Miranda
 *
 */
public class FormatterUtil {

	private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	private static final String AMOUNT_FORMAT = "#,##0.00";
	
	public static String formatDate(Date date) {
		return dateFormatter.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
	}

	public static String formatAmount(BigDecimal amount) {
		return new DecimalFormat(AMOUNT_FORMAT).format(amount);
	}
	
}