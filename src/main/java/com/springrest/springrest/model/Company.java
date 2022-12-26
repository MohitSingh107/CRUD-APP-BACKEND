package com.springrest.springrest.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Company {
	@Id
	private Long compId;	
private String compName;

public Long getCompId() {
	return compId;
}
public void setCompId(Long compId) {
	this.compId = compId;
}
public String getCompName() {
	return compName;
}
public void setCompName(String compName) {
	this.compName = compName;
}


}
