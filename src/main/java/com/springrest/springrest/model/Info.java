package com.springrest.springrest.model;


public class Info {

	private int pageSize;
	private int limit;
	private  String shortType;
	private String shortField;
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getShortType() {
		return shortType;
	}
	public void setShortType(String shortType) {
		this.shortType = shortType;
	}
	public String getShortField() {
		return shortField;
	}
	public void setShortField(String shortField) {
		this.shortField = shortField;
	}
	public Object getSortType() {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
