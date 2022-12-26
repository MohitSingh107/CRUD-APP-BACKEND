package com.springrest.springrest.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Website {

	@Id
	private Long webId;
	private String webName;
	private Float webrating;
	
	public Website(Long webId, String webName, Float webrating) {
		this.webId = webId;
		this.webName = webName;
		this.webrating = webrating;
	}

	public Website() {
	
	}

	public Long getWebId() {
		return webId;
	}

	public void setWebId(Long webId) {
		this.webId = webId;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public Float getWebrating() {
		return webrating;
	}

	public void setWebrating(Float webrating) {
		this.webrating = webrating;
	}
	
	
}
