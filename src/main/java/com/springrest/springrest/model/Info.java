package com.springrest.springrest.model;


public class Info {

	private int pageSize=1;
	private int limit=3;
	private String shortType;
	private String shortField;
	private String searchitem;
	
	public String getSearchitem() {
		return searchitem;
	}
	public void setSearchitem(String searchitem) {
		this.searchitem = searchitem;
	}
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
	
}
