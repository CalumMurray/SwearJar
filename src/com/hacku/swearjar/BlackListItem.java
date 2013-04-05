package com.hacku.swearjar;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

//Entity bean class to represent a single blacklisted word triple (word, charge, occurrences)
public class BlackListItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String word;
	private BigDecimal charge;
	private int occurrences;

	private boolean matchSimilar;
	
	private static final NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.UK); 
	
	public BlackListItem(String word, BigDecimal charge){
		this.word = word;
		this.charge = charge;
		this.occurrences = 0;
		matchSimilar = true;
	}
	
	public BlackListItem(String word, BigDecimal charge, int occurrences, boolean matchSimilar) {
		this.word = word;
		this.charge = charge;
		this.occurrences = occurrences;
		this.matchSimilar = matchSimilar;
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
	
	public boolean isMatchSimilar() {
		return matchSimilar;
	}

	public void setMatchSimilar(boolean matchSimilar) {
		this.matchSimilar = matchSimilar;
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
		return string.matches("^\\s*-?(\\d+(\\.\\d{1,2})?|\\.\\d{1,2})\\s*$");
	}
	
	/**
	 * Adds to occurrences the number of times that the word it appears 
	 * in the utterance.  Not case sensitive.
	 * 
	 * @param utterance to search for word
	 * @return number of times this BlackListItem's word appears in utterance
	 */
	public int addOccurrences(String utterance) {
		int occurrences = 0;
		if (matchSimilar)
			occurrences = StringUtils.countMatches(utterance.toUpperCase(), word.toUpperCase());  //Find the number of SIMILAR matches
		else
		{
			String[] words = utterance.split(" ");
			//Find the number of EXACT matches
			for (String splitWord : words)  
				if (splitWord.toUpperCase().equals(word.toUpperCase()))
					occurrences++;
		}
		this.occurrences += occurrences; //Add the matches to the total matches
			
		return occurrences;
	}
}
