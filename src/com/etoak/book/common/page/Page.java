package com.etoak.book.common.page;

import java.util.List;
//分页的组件
//{pageNumber:xxx,total:xxx,rows:[{id:xx,name:xx,price:xx},{}]}
public class Page<T> {//
	private int pageNumber;//pageNumber currentPage当前页 
	private int total;//总记录数 
	private int pageSize;//每页显示多少条
	private int pageCount;//总页数
	
	private int pre; //上一页
	
	private int next;//下一页
	
	private int start;//每页的起始位置
	
	private List<T> rows;//每页数据
	
	

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return (total+pageSize-1)/pageSize;
	}
	public int getPre() {
		if(pageNumber>1){
			return pageNumber-1;
		}return 1;
	}

	public int getNext() {
		if(pageNumber<getPageCount()){
			return pageNumber+1;
		}return getPageCount();
	}

	//每页的起始位置
	public int getStart() {
		return (pageNumber-1)*pageSize;
	}
	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
	
 	
	
}
