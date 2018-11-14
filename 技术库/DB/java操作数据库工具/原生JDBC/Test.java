package com.hsp.dao;

public class Test {

	public static void main(String[] args) {
		
		// PagingEntity类自己设计
		getPagingEntity();

	}
	
	
	public static void getPagingEntity(PagingEntity pe) {
		/**
		 * 1、计算出rowCount和pageCount
		 */
		int rowCount = DAO.getCount("select count(*) from user");
		pe.calculate(rowCount);
		
		/**
		 * 2、获取当前页要显示的数据
		 */
		String sql = null;
		int startRow = pe.getStartRow();
		int pageSize = pe.getPageSize();
		if (startRow >= 0 && pageSize > 0) {
			sql = String.format("select * from user limit %s,%s", startRow, pageSize);
		} else {
			sql = "select * from user";
		}
		
		List<Object> data = DAO.execqueQuery(User.class, sql, null);
		pe.setData(data);
	}

}
