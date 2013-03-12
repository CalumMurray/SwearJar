package com.hacku.swearjar;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

//Entity bean class to represent a single blacklisted word triple (word, charge, occurrences)
public class BlackListItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String word;
	private BigDecimal charge;
	private int occurrences;

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
	 * @return charge as a String formatted as local currency
	 */
	public String formatCharge(){
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		return nf.format(charge);
	}
}
