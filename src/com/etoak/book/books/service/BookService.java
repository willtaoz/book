package com.etoak.book.books.service;

import java.util.List;

import com.etoak.book.books.dao.IBookDao;
import com.etoak.book.books.dao.impl.BookDaoImpl;
import com.etoak.book.books.po.Book;
import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.po.Category;

public class BookService {
	IBookDao dao = new BookDaoImpl();
	public void registerCa(Category ca) {
		dao.addCa(ca);
	}
	public String registerBook(Book book) {
		// TODO Auto-generated method stub
		return dao.addBook(book);
	}
	public void registerBp(Bookpic bp) {
		// TODO Auto-generated method stub
		dao.addBp(bp);
	}
	public List<Category> queryAllCas() {
		// TODO Auto-generated method stub
		return dao.queryAllCas();
	}
	public int countBook(String name,String caid) {
		// TODO Auto-generated method stub
		return dao.countBook(name,caid);
	}
	public List<Book> querySome(String name,String caid,int start, int pageSize) {
		// TODO Auto-generated method stub
		return dao.querySome(name,caid,start,pageSize);
	}
	public Category queryCaById(String categoryid) {
		// TODO Auto-generated method stub
		return dao.queryCaById(categoryid);
	}
	public Book queryBookById(String id) {
		// TODO Auto-generated method stub
		return dao.queryBookById(id);
	}
	public List<Bookpic> queryBpsByBookid(String id) {
		// TODO Auto-generated method stub
		return dao.queryBpsByBookId(id);
	}
	public Bookpic queryBpById(String id) {
		// TODO Auto-generated method stub
		return dao.queryBpById(id);
	}
	public void deletePicById(String id) {
		// TODO Auto-generated method stub
		dao.deletePicById(id);
	}
	public void updateBpByBookId(String bookid) {
		// TODO Auto-generated method stub
		dao.updateBpByBookId(bookid);
	}
	public void updateBp(Bookpic bp) {
		// TODO Auto-generated method stub
		dao.updatebp(bp);
	}
	

}
