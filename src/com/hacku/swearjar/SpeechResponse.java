package com.hacku.swearjar;
import java.io.Serializable;

public class SpeechResponse implements Serializable{
	int status;
	String id;
	Hypothesis[] hypotheses;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Hypothesis[] getHypotheisis() {
		return hypotheses;
	}
	public void setHypotheisis(Hypothesis[] hypotheisis) {
		this.hypotheses = hypotheisis;
	}
}
