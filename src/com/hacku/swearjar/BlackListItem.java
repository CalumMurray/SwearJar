package com.hacku.swearjar;

import java.io.Serializable;

public class BlackListItem implements Serializable {

	private String word;
	private float charge;
	private int occurrences;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public float getCharge() {
		return charge;
	}
	public void setCharge(float charge) {
		this.charge = charge;
	}
	public int getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(int occurrences) {
		this.occurrences = occurrences;
	}
}
