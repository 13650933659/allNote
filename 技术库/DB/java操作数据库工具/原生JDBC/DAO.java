package com.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


/**
 * 此文件适应操作任何数据库
 * 
 */

public class DAO {
	
	/**
	 * 执行更新的sql语句
	 * 
	 * @param sql			预编译的sql语句
	 * @param parameters	预编译的参数数组
	 * @return
	 */
	public static void execqueUpdate(String sql, Object[] parameters) {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			//2、得到连接  3.创建火箭车，并且给问号赋值并执行sql语句  4.对执行的结果进行处理《这里就返回给调用者处理》
			c = DBUtil.getConnection();
			ps = c.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.length; i++) {
					ps.setObject(i + 1, parameters[i]);
				}
			}
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			DBUtil.closeDB(null, ps, c);
		}
	}
	
	
	
	/**
	 * 执行sql语句，把结果集用反射技术封装成对应的实体类的集合，这时就可以关闭资源了
	 * 
	 * @param sql		预编译的sql语句
	 * @param params	预编译的参数数组
	 * @return
	 */
	public static List<Object> execqueQuery(Class<?> klass, String sql, Object[] params){
		if (klass == null || sql == null) {
			return null;
		}
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			c = DBUtil.getConnection();
			ps = c.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();

			
			Field[] fields = klass.getDeclaredFields();
			int len = fields.length;
			
			List<Object> result = new ArrayList<Object>();
			while(rs.next()) {
				Object obj = klass.newInstance();
				for (int i = 0; i < len; i++) {
					Field field = fields[i];
					
					Object value = getCorrespondingValue(rs, field);
					Method m = getCorrespondingMethod(klass, field);
					if (value != null && m != null) {
						m.invoke(obj, value);
					}
				}
				result.add(obj);
			}
			return result;
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(rs,ps,c);
		}
		return null;
	}


	/**
	 * 取出Field对应的set方法对象
	 * 
	 * @param klass	所属的类
	 * @param field	实体类的字段
	 * @return
	 * @throws NoSuchMethodException
	 */
	private static Method getCorrespondingMethod(Class<?> klass, Field field)
			throws NoSuchMethodException {
		String fieldName = field.getName();
		fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);	// 获取标准的get方法名称
		Class<?> type = field.getType();
		Method m = klass.getDeclaredMethod("set" + fieldName, type);
		return m;
	}


	/**
	 * 取出Field对应的值（此方法的数据类型的映射有待改善，目前基本类型和其的引用类型也能支持）
	 * 
	 * @param rs	结果集
	 * @param field	实体类的字段
	 * @return
	 * @throws SQLException
	 */
	private static Object getCorrespondingValue(ResultSet rs, Field field)
			throws SQLException {
		Object value = null;
		if (rs != null && field != null) {
			Class<?> type = field.getType();
			String fieldName = field.getName();
			
			
			if (type.equals(BigDecimal.class)) {
				value = rs.getBigDecimal(fieldName);
			} else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
				value = rs.getBoolean(fieldName);
			} else if (type.equals(Byte.class) || type.equals(byte.class)) {
				value = rs.getByte(fieldName);
			} else if (type.equals(Date.class)) { // 只支持java.util.Date
				value = rs.getDate(fieldName);
			} else if (type.equals(Double.class) || type.equals(double.class)) {
				value = rs.getDouble(fieldName);
			} else if (type.equals(Float.class) || type.equals(float.class)) {
				value = rs.getFloat(fieldName);
			} else if (type.equals(Integer.class) || type.equals(int.class)) {
				value = rs.getInt(fieldName);
			} else if (type.equals(Long.class) || type.equals(long.class)) {
				value = rs.getLong(fieldName);
			} else if (type.equals(Short.class) || type.equals(short.class)) {
				value = rs.getShort(fieldName);
			} else if (type.equals(String.class)) {
				value = rs.getString(fieldName);
			}
		}
		return value;
	}
	
	
	/**
	 * 取列名
	 */
	public static void getColumnNames() {
//		ResultSetMetaData rsmd = null; //经常resultSet和resultSetmd一起使用，resultSetmd不用关闭的
//		rsmd = rs.getMetaData();
//		int column = rsmd.getColumnCount();
//		
//		List<String> columnNames = new ArrayList<String>();
//		for (int i = 1; i <= column; i++) {
//			columnNames.add(rsmd.getColumnName(i));	// 如果想用别名，请用rsmd.getColumnLabel(i)
//		}
	}


	/**
	 * 获取总数，注意sql语句必须为聚合类型的sql，否则抛运行时异常
	 * @param sql	聚合类型的sql
	 * @return
	 */
	public static int getCount(String sql) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			c = DBUtil.getConnection();
			ps = c.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while(rs.next()) {
				return rs.getInt(1);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("获取总数失败，原因：" + e.getMessage());
		} finally {
			DBUtil.closeDB(rs,ps,c);
		}
		return 0;
	}
	
	
}