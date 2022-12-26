package com.springrest.springrest.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Course {  
	@Id
	private Long sno;
	private String title;
	private String technology;
	private float  price;
	@OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
	private Company provider;
	
	@OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Website> weblist=new ArrayList<>();

	Course(){
		
	}

	public Course(Long sno, String title, String technology, float price, Company provider) {
		super();
		this.sno = sno;
		this.title = title;
		this.technology = technology;
		this.price = price;
		this.provider = provider;
	}

	public Long getSno() {
		return sno;
	}

	public void setSno(Long sno) {
		this.sno = sno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Company getProvider() {
		return provider;
	}

	public void setProvider(Company provider) {
		this.provider = provider;
	}

	public List<Website> getWeblist() {
		return weblist;
	}

	public void setWeblist(List<Website> weblist) {
		this.weblist = weblist;
	}

	
	
}
