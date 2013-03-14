package com.hacku.swearjar.speechapi;
import java.io.Serializable;

/**
 * Container for JSON returned from Google Speech API
 * @author Neil
 */
public class SpeechResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	int status;
	String id;
	Hypothesis[] hypotheses;
	
	public SpeechResponse(){
		status = 6;
		id = "";
		hypotheses = new Hypothesis[1];
		hypotheses[0] = new Hypothesis();
	}
	
	public String getBestUtterance() {
		
		if (hypotheses == null || hypotheses[0].getUtterance() == null)
			return "";
		return hypotheses[0].getUtterance();
		
	}
	
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
