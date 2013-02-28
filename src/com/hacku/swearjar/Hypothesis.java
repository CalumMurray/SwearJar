package com.hacku.swearjar;
import java.io.Serializable;

public class Hypothesis implements Serializable{
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
