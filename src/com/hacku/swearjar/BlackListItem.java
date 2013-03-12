package com.hacku.swearjar;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class BlackListItem implements Serializable {

	private String word;
	private BigDecimal charge;
	private int occurrences;

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
