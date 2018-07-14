package com.etoak.book.common.factory;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class CF {
	private static BasicDataSource ds = new BasicDataSource();
	static{
		InputStream is = 
		CF.class.getResourceAsStream("../../../../../db.properties");
		Properties pro = new Properties();
		try{
		pro.load(is);
		is.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		ds.setDriverClassName(pro.getProperty("driver"));
		ds.setUrl(pro.getProperty("url"));
		ds.setUsername(pro.getProperty("username"));
		ds.setPassword(pro.getProperty("password"));
		ds.setMaxActive(Integer.
		parseInt(pro.getProperty("maxactive")));
	}
	public static Connection getConnection(){try{
			return ds.getConnection();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public static DataSource getDs(){return ds;}
	public static void main(String[] args) {
		System.out.println(CF.getConnection());
	}
	
}
