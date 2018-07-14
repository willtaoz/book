package com.etoak.book.books.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.etoak.book.books.po.Book;
import com.etoak.book.books.service.BookService;
import com.etoak.book.common.page.Page;

public class Book1Servlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
	/*	int start = Integer.parseInt(request.getParameter("offset"));
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		*/
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String name = request.getParameter("name");
		String caid = request.getParameter("caid");
		
		if("".equals(name)){name=null;}
		if("".equals(caid) || "-1".equals(caid)){caid=null;}
		
		
		Page<Book> page = new Page<Book>();
		page.setPageSize(pageSize);
		// pageNumber=(start+pageSize)/pageSize
		//int pageNumber = (start+pageSize)/pageSize;
		page.setPageNumber(pageNumber);
		
		
		BookService bs = new BookService();
		int total = bs.countBook(name,caid);
		page.setTotal(total);
		
		List<Book> data = bs.querySome(name,caid,page.getStart()
				,pageSize);
		
		for(Book b:data){
			//select * from category where id=?
			b.setCa(bs.queryCaById(b.getCategoryid()));
		}
		
		page.setRows(data);
		
		JSONObject ja = JSONObject.fromObject(page);
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(ja);
		out.flush();
		out.close();
		
	}

}
