package com.hacku.swearjar.speechapi;
import java.io.Serializable;

/**
 * Container for JSON returned from Google Speech API
 * @author Neil
 */
public class Hypothesis implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String utterance;
	private float confidence;
	
	public String getUtterance() {
		return utterance;
	}
	public void setUtterance(String utterance) {
		this.utterance = utterance;
	}
	public float getConfidence() {
		return confidence;
	}
	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}
}
