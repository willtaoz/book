package com.etoak.book.books.dao;

import java.util.List;

import com.etoak.book.books.po.Book;
import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.po.Category;

public interface IBookDao {
	public void addCa(Category ca);
	
	//返回添加到数据库中的主键  
	public String addBook(Book book);
	
	public void addBp(Bookpic bp); 
			
	public List<Category> queryAllCas();

	public int countBook(String name,String caid);

	public List<Book> querySome(String name,String caid,int start, int pageSize);

	public Category queryCaById(String categoryid);

	public Book queryBookById(String id);

	public List<Bookpic> queryBpsByBookId(String id);

	public Bookpic queryBpById(String id);

	public void deletePicById(String id);

	public void updateBpByBookId(String bookid);

	public void updatebp(Bookpic bp);
}
