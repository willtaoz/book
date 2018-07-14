package com.etoak.book.books.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.service.BookService;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;

public class PicServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		SmartUpload su = new SmartUpload();
		su.initialize(this, request, response);
		try{
		su.upload();
		File f = su.getFiles().getFile(0);
		//获得文件名字		f.getFiledName()
		String showname = f.getFileName();
		//获得文件后缀
		String fext = f.getFileExt();
		//改名 adfadfadvafd112123asdfadfad.jpg
		String newName = UUID.randomUUID().toString()
		.replaceAll("-","")+"."+fext;
		//另存 到服务器指定目录中
		f.saveAs("/myfiles/"+newName);
		//将文件路径写到数据库中
		Bookpic bp = new Bookpic();
		String id = su.getRequest().getParameter("bookid");
		bp.setBookid(id);
		bp.setSavepath("/myfiles/"+newName);
		bp.setShowname(showname);
		bp.setFm("0");
		BookService bs = new BookService();
		bs.registerBp(bp);
		
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("{\"flag\":\"success\"}");
		out.flush();
		out.close();
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
