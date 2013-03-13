package com.hacku.swearjar;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


//Entity bean class to represent a single blacklisted word triple (word, charge, occurrences)
public class BlackListItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private String word;
	private BigDecimal charge;
	private int occurrences;

	private static final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK); 
	
	public BlackListItem(String word, BigDecimal charge){
		this.word = word;
		this.charge = charge;
		this.occurrences = 0;
	}
	
	public BlackListItem(String word, BigDecimal charge, int occurrences) {
		this.word = word;
		this.charge = charge;
		this.occurrences = occurrences;
	}

	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public BigDecimal getCharge() {
		return charge;
	}
	public void setCharge(BigDecimal charge) {
		this.charge = charge;
	}
	public int getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}
	
	/**
	 * @return charge * occurrences
	 */
	public BigDecimal getTotalCharge() { 
		return charge.multiply(new BigDecimal(occurrences));
	}

	/**
	 * @return charge as a String formatted as local currency
	 */
	public String formatCharge(){
		return formatter.format(charge);
	}

	/**
	 * @return charge * occurrences as a String formatted as local currency
	 */
	public String formatTotalCharge(){
		return formatter.format(getTotalCharge());
	}

	/**
	 * Regex validation for currency
	 * 
	 * Check before setting the charge of a BlackListItem
	 * 
	 * @param string
	 * @return true if string of the form DD*.DD  
	 */
	public static boolean validateCurrency(String string) {
		return string.matches("^[ ]*[\\d]+[.][\\d][\\d]$");
	}

}
