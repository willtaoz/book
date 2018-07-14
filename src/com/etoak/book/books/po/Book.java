package com.etoak.book.books.po;

import java.util.List;

public class Book {
	private String id;
	private String name;
	private String author;
	private Double price;
	private String status;
	private String publishdate;
	private String categoryid;
	//一本书对应一个类别
	private Category ca;
	
	//图片 
	private List<Bookpic> bps;
	
	public List<Bookpic> getBps() {
		return bps;
	}
	public void setBps(List<Bookpic> bps) {
		this.bps = bps;
	}
	public Category getCa() {
		return ca;
	}
	public void setCa(Category ca) {
		this.ca = ca;
	}
	public Book(){}
	public Book(String name, String author, Double price, String status,
			String publishdate, String categoryid) {
		super();
		this.name = name;
		this.author = author;
		this.price = price;
		this.status = status;
		this.publishdate = publishdate;
		this.categoryid = categoryid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPublishdate() {
		return publishdate;
	}
	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}
	public String getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}
	
}
