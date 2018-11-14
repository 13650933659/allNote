package com.hsp.dao;

public class Test {

	public static void main(String[] args) {
		
		// PagingEntity���Լ����
		getPagingEntity();

	}
	
	
	public static void getPagingEntity(PagingEntity pe) {
		/**
		 * 1�������rowCount��pageCount
		 */
		int rowCount = DAO.getCount("select count(*) from user");
		pe.calculate(rowCount);
		
		/**
		 * 2����ȡ��ǰҳҪ��ʾ������
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
