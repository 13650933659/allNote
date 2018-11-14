package com.dao;

import java.sql.*;
import java.util.Properties;
import java.io.*;

/**
 * 此文件读取数据库的配置信息
 * 可以得到：
 * 数据库的连接对象：connection
 * 关闭数据库资源的方法：closeDB()
 * 
 */

public class DBUtil {

	//连接数据库需要的从利用《properties和is》读配置文件需要的
	static private String driver = "";
	static private String url = "";
	static private String un = "";
	static private String pw = "";
	static private Properties properties = null;
	static private InputStream inputStream = null;
	
	
	/**
	 * 加载驱动，一次就好，如果系统还要用properties这个流不用关闭了
	 * 
	 */
	static {
		try  {
			//创建Properties文件对象，并且加载《类加载器的方法》
			properties = new Properties();
			inputStream = DBUtil.class.getClassLoader().getResourceAsStream("mysqlInfo.properties");
			properties.load(inputStream);
			
			//读入
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			un = properties.getProperty("un");
			pw = properties.getProperty("pw");
			//1.加载驱动
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 得到连接,要考虑线程安全,所以每一次请求给一个新的连接，用完马上关闭
	 * 
	 */
	static public Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, un, pw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	
	/**
	 * 关闭数据库资源的方法,用完马上关闭
	 * @param rs
	 * @param ps
	 * @param c
	 */
	static public void closeDB(ResultSet rs, PreparedStatement ps, Connection c){
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (c != null){
				c.close();
				c = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}