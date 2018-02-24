package com.billing.demo.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {

	private String url;
	private Page<T> page;
	private int totalPages;
	private int elementsPerPage;
	private int currentPage;
	private List<PageItem> lstPage;
	
	public PageRender(String url, Page<T> page) {
		this.url = url; 
		this.page = page;
		this.elementsPerPage = page.getSize(); 
		this.totalPages = page.getTotalPages(); 
		this.currentPage = page.getNumber() + 1; 
		this.lstPage = new ArrayList<PageItem>();
		int from, to;
		if(this.totalPages <= this.elementsPerPage) {
			from = 1;
			to = this.totalPages;
		} else {
			if(this.currentPage <= this.elementsPerPage / 2) {
				from = 1;
				to = this.elementsPerPage;
			} else if(this.currentPage >= this.totalPages - this.elementsPerPage / 2) {
				from = this.totalPages - this.elementsPerPage + 1;
				to = this.elementsPerPage;
			} else {
				from = this.currentPage - this.elementsPerPage / 2;
				to = this.elementsPerPage;
			}
		}
		for(int i = 0; i < to; i ++) {
			this.lstPage.add(new PageItem(from + i, this.currentPage == from + i));
		}
	}

	public boolean isFirst() {
		return this.page.isFirst();
	}

	public boolean isLast() {
		return this.page.isLast();
	}
	
	public boolean isHasNext() {
		return this.page.hasNext();
	}
	
	public boolean isHasPrevious() {
		return this.page.hasPrevious();
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getElementsPerPage() {
		return elementsPerPage;
	}

	public void setElementsPerPage(int elementsPerPage) {
		this.elementsPerPage = elementsPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<PageItem> getLstPage() {
		return lstPage;
	}

	public void setLstPage(List<PageItem> lstPage) {
		this.lstPage = lstPage;
	}
	
	
}
