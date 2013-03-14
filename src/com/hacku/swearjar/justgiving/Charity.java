package com.hacku.swearjar.justgiving;

import java.io.Serializable;

/**
 * Stores name/id pair of a just giving charity 
 * @author Neil
 */
public class Charity implements Serializable{

	private static final long serialVersionUID = 3916738244449689706L;
	private String name;
	private String id;
	
	public Charity(){
		name = "";
		id = "";
	}
	
	public Charity(String name, String id){
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}	
}
