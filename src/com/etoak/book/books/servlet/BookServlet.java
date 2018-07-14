package com.etoak.book.books.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.etoak.book.books.po.Book;
import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.po.Category;
import com.etoak.book.books.service.BookService;
import com.etoak.book.common.page.Page;
import com.jspsmart.upload.File;
import com.jspsmart.upload.Files;
import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;

public class BookServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String method = request.getParameter("method");
		if(null==method){addbook(request,response);return ;}
	/*	if("addca".equals(method)){
			addca(request,response);
		}else if("queryAllCas".equals(method)){
			queryAllCas(request,response);
		}else if("querySome".equals(method)){
			querySome(request,response);
		}*/
		//获得当前对象所在类的Class文件
		Class cls = this.getClass();
		try{
		//从class文件中寻找指定名字和参数的方法
		Method m = cls.getDeclaredMethod(method,
				HttpServletRequest.class,
				HttpServletResponse.class);
		//执行 this.addCa(request,response)
		//this.queryAllCas(reqyuest,response);
		m.invoke(this,request,response);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void export2Excel(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String caid = request.getParameter("caid");
		BookService bs = new BookService();
		List<Book> data = bs.querySome(name, caid,0,100000000);
		//workbook
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("书籍列表");
		Row row = sheet.createRow(0);
		int i=1;
		row.createCell(0).setCellValue("编号");
		row.createCell(1).setCellValue("名字");
		row.createCell(2).setCellValue("作者");
		row.createCell(3).setCellValue("价格");
		row.createCell(4).setCellValue("出版日期");
		
		for(Book b:data){
			Row row1 = sheet.createRow(i);
			row1.createCell(0).setCellValue(i++);
			row1.createCell(1).setCellValue(b.getName());
			row1.createCell(2).setCellValue(b.getAuthor());
			row1.createCell(3).setCellValue(b.getPrice());
			row1.createCell(4).setCellValue(b.getPublishdate());
		}
		//下载
		response.setHeader("Content-Disposition",
				"attachment;filename="+URLEncoder.
				encode("学生列表","UTF-8")+".xlsx");
		OutputStream os = response.getOutputStream();
		wb.write(os);
		wb.close();
		
	}
	public void setfm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		BookService bs = new BookService();
		// select * from bookpic where id=?
		Bookpic bp = bs.queryBpById(id);
		//bp
		String bookid = bp.getBookid();
		bs.updateBpByBookId(bookid);
		//
		bp.setFm("1");
		bs.updateBp(bp);
		
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("{\"flag\":\"success\"}");
		out.flush();
		out.close();
	}
	public void delpic(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String id = request.getParameter("id");
		BookService bs = new BookService();
		// select * from bookpic where id=?
		Bookpic bp = bs.queryBpById(id);
		//删除物理图片
		String path = 
		this.getServletContext().getRealPath(bp.getSavepath());
		java.io.File f = new java.io.File(path);
		if(f.exists()){f.delete();}
		//删除数据库中的图片信息
		//delete from bookpic where id=?
		bs.deletePicById(id);
		//返回客户端成功标志
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("{\"flag\":\"success\"}");
		out.flush();
		out.close();
	}
	//根据id查询书籍信息
	public void queryBookById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookService bs = new BookService();
		String id = request.getParameter("id");
		Book book = bs.queryBookById(id);
		//
		book.setBps(bs.queryBpsByBookid(book.getId()));
		
		JSONObject jo = JSONObject.fromObject(book);
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jo);
		out.flush();
		out.close();
	}
	public void querySome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int pageSize =Integer.parseInt(request.getParameter("pageSize"));
		int pageNumber =Integer.parseInt(request.getParameter("currentPage"));
		String name = request.getParameter("name");
		String caid = request.getParameter("caid");
		
		
		Page<Book> page = new Page<Book>();
		//pageSize
		page.setPageSize(pageSize);
		//pageNumer pre next  start
		page.setPageNumber(pageNumber);
		BookService bs = new BookService();
		// select count(*) from book
		int total = bs.countBook(name,caid);
		//total  pageCount 
		page.setTotal(total);
		//rows select * from (select rownum rn, b.* from book b  where rownum<pageSize+start) where rn>=start;
		List<Book> rows = bs.querySome(name,caid,page.getStart(),page.getPageSize());
		//给每一本书添加类别对象
		for(Book b:rows){
			//select * from category where id=?
			b.setCa(bs.queryCaById(b.getCategoryid()));
		}
		page.setRows(rows);
		
		///~~~~~~~~~~~~~~~~~~~~
		JSONObject jo = JSONObject.fromObject(page);
		
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jo);
		out.flush();
		out.close();
	}
	public void queryAllCas(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookService bs = new BookService();
		List<Category> cas  = bs.queryAllCas();
		//[{},{}]
		JSONArray ja = JSONArray.fromObject(cas);
		/*String str="[";
		for(Category ca:cas){
			str+="{id:"+ca.getId()+",name:"+ca.getName()+"},"
		}
		str+="]";*/
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(ja);
		out.flush();
		out.close();
	}
	public void addbook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//构造 初始化 并使用第三方组件解析请求
		SmartUpload  su = new SmartUpload();
		su.initialize(this, request, response);
		try{
		//上传---->传入到smartupload插件中了
		su.upload();
		//从第三方插件中获得request +文件
		Request req = su.getRequest();
		String name = req.getParameter("name");
		String author = req.getParameter("author");
		Double price = Double.parseDouble(req.getParameter("price"));
		String status = req.getParameter("status");
		String pdate = req.getParameter("publishdate");
		//Date date = string2Date(pdate);
		String caid = req.getParameter("categoryid");
		Book book = new Book(name,author,price,status,pdate,caid);
		BookService bs = new BookService();
		//添加书籍信息 返回id
		String id = bs.registerBook(book);
		
		//获得文件信息
		Files fs = su.getFiles();
		//获得文件个数
		int count = fs.getCount();
		//封面 0 1 3 2  
		int fm = Integer.parseInt(req.getParameter("fm"));
		for(int i=0;i<count;i++){
			File f  = fs.getFile(i);
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
			bp.setBookid(id);
			bp.setSavepath("/myfiles/"+newName);
			bp.setShowname(showname);
			if(fm==i){
				bp.setFm("1");
			}else{
				bp.setFm("0");
			}
			
			bs.registerBp(bp);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		response.sendRedirect("addbook.jsp");
	}

	//DOM4J DOM4j
	private Date string2Date(String pdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try{
		return sdf.parse(pdate);
		}catch(Exception e){
			e.printStackTrace();return null;
		}
	}
	public void addca(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//
		String name = request.getParameter("name");
		
		BookService bs = new BookService();
		Category ca = new Category();
		ca.setName(name);
		bs.registerCa(ca);
		response.sendRedirect("addca.jsp");
	}

}
