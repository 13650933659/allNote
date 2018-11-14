//这个文件是 《PersonnelFiles》表的模型

package com.mhl.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mhl.db.SqlHelper;

public class PFModel extends AbstractTableModel{
	SqlHelper sh=null;
	//dql的
	Vector <String>coumns=null;
	Vector <Vector>rows=null;
	Vector <String>row=null;
	ResultSet res=null;
	ResultSetMetaData rsmd=null;
	
	//《PersonnelFiles》表添加记录
	public boolean pfAdd(String[] arrStr){
		//
		sh=new SqlHelper();
		return sh.execUpdate("insert into PersonnelFiles values (?,?,?,?,?,?,?,?,?,?)", arrStr);
	}
	//《PersonnelFiles》表删除记录
	public boolean pfDel(String[] arrStr){
		//
		sh=new SqlHelper();
		return sh.execUpdate("delete from PersonnelFiles where empNo=?", arrStr);
	}
	//《PersonnelFiles》表修改记录
	public boolean pfUpd(String[] arrStr){
		//
		sh=new SqlHelper();
		return sh.execUpdate("update PersonnelFiles set p_name=?, pictureUrl=?, sex=?, address=?,hireDate=?,idCard=?, backgroud=?, post=?, married=? where empNo=?",arrStr);
	}
	
	//根据条件查询《PersonnelFiles》表
	public void pfQuery(String sql,String[] arrStr){
		sh=new SqlHelper();
		res=sh.execQuery(sql, arrStr);
		try{
			rsmd=res.getMetaData();
			//创建行数据，列数据
			rows=new Vector<Vector>();
			coumns=new Vector<String>();
			//通用版的获取表头名字《请注意他是从1开始编号的》
			for(int i=0;i<rsmd.getColumnCount();i++){
				coumns.add(rsmd.getColumnName(i+1));
			}
			//通用版的获取所有行的数据《请注意他是从0开始编号的》
			while(res.next()){
				row=new Vector<String>();
				for(int i=0;i<rsmd.getColumnCount();i++){
					row.add(res.getString(i+1));
				}
				rows.add(row);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			sh.closeDB();
		}
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		//这里我用了泛型就可以不用还了，而且列是从1开始，行是从0开始.toString()
		return coumns.get(column);
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return coumns.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return rows.get(rowIndex).get(columnIndex);
	}
}





