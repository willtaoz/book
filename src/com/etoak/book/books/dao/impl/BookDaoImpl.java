package com.etoak.book.books.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;

import com.etoak.book.books.dao.IBookDao;
import com.etoak.book.books.po.Book;
import com.etoak.book.books.po.Bookpic;
import com.etoak.book.books.po.Category;
import com.etoak.book.common.factory.CF;

public class BookDaoImpl implements IBookDao{

	QueryRunner qr = new QueryRunner(CF.getDs());
	@Override
	public void addCa(Category ca) {
		try {
			String sql="insert into category values(sys_guid(),?)";
			qr.update(sql,ca.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//如何返回数据库中的主键 JDBC---》Hibernate--->MyBATIS
	@Override
	public String addBook(Book book) {
		try {
			/*String sql="select sys_guid()  from dual";
			Map map  = qr.query(sql, new MapHandler());
			String id = map.get("sys_guid()")+"";
			System.out.println(id);*/
			String id = UUID.randomUUID().toString()
					.replaceAll("-","");
			book.setId(id);
			
			String insert="insert into book(id,name,price,author,status,categoryid,publishdate)values(?,?,?,?,?,?,to_date(?,'yyyy-MM-dd'))";
			qr.update(insert,book.getId(),book.getName(),book.getPrice(),
			book.getAuthor(),
			book.getStatus(),book.getCategoryid(),
			book.getPublishdate() );
			
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void addBp(Bookpic bp) {
		try {
			String sql="insert into bookpic(id,savepath,showname,"
			+ "fm,bookid) values(sys_guid(),?,?,?,?)";
			qr.update(sql,bp.getSavepath(),
			bp.getShowname(),bp.getFm(),bp.getBookid());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<Category> queryAllCas() {
		try{
			String sql="select * from category";
			List<Category> cas = 
			qr.query(sql,
			new BeanListHandler<Category>(Category.class));
			return cas;
		}catch(Exception e){e.printStackTrace();return null;}
	}
	@Override
	public int countBook(String name,String caid) {
		try {
			String sql="select count(*) from book where 1=1 ";
			if(name!=null){
				sql+=" and name like '%"+name+"%'";
			}
			if(caid!=null){
				sql+=" and categoryid ='"+caid+"'";
			}
			Map data = qr.query(sql,new MapHandler());
			return Integer.parseInt(data.get("count(*)")+"");
		} catch (Exception e) {
			e.printStackTrace();return 0;
		}
	}
	@Override
	public List<Book> querySome(String name, String caid,int start, int pageSize) {
		try {
			String sql="select *  from (select rownum rn, "
			+ " b.* from book b  where   rownum<=? ";
			if(name!=null){
				sql+=" and name like '%"+name+"%'";
			}
			if(caid!=null){
				sql+=" and categoryid ='"+caid+"'";
			}
			sql+= ") where rn>?";
			System.out.println(sql);
			//name   name id  id  publishdate pdate
			List<Book> data = qr.query(sql,
			new BeanListHandler<Book>(Book.class),
			start+pageSize,
			start);
		
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Category queryCaById(String categoryid) {
		try {
			String sql="select * from category where id=?";
			Category ca = qr.query(sql,new 
			BeanHandler<Category>(Category.class),categoryid);
			return ca;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Book queryBookById(String id) {
		try {
			String sql="select * from book where id=?";
			Book book = 
			qr.query(sql,new BeanHandler<Book>(Book.class),id);
			return book;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<Bookpic> queryBpsByBookId(String id) {
		try {
			String sql="select * from bookpic where bookid=?";
			List<Bookpic> data = 
			qr.query(sql,new BeanListHandler<Bookpic>(Bookpic.class),id);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Bookpic queryBpById(String id) {
		try {
			String sql="select * from bookpic where id=?";;
			Bookpic bp = 
			qr.query(sql,new 
			BeanHandler<Bookpic>(Bookpic.class),id);
			return bp;
		} catch (Exception e) {
			e.printStackTrace();return null;
		}
	}
	@Override
	public void deletePicById(String id) {
		try {
			String sql="delete from bookpic where id=?";
			qr.update(sql,id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateBpByBookId(String bookid) {
		try {
			String sql="update bookpic set fm='0' where bookid=?";
			qr.update(sql,bookid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updatebp(Bookpic bp) {
		try {
			String sql="update bookpic set savepath=?,showname=?,bookid=?, fm=? where id=?";
			qr.update(sql,bp.getSavepath(),
			bp.getShowname(),bp.getBookid(),bp.getFm(),bp.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
