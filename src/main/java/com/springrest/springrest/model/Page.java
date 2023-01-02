package com.springrest.springrest.model;

import java.util.ArrayList;
import java.util.List;

public class Page {


	private List<?> mylist=new ArrayList<>();
	private int count;
	private int lastPage;
	private int	pageSize;
	private int limit;
	
	public Page(List<?> mylist, int count, int lastPage, int pageSize, int limit) {
		super();
		this.mylist = mylist;
		this.count = count;
		this.lastPage = lastPage;
		this.pageSize = pageSize;
		this.limit = limit;
	}

	public List<?> getMylist() {
		return mylist;
	}

	public void setMylist(List<?> mylist) {
		this.mylist = mylist;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLastPage() {
		return lastPage;
	}

	public void setLastPage(int lastPage) {
		this.lastPage = lastPage;
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
	
	
}
