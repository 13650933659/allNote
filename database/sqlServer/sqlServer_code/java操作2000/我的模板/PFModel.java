//����ļ��� ��PersonnelFiles�����ģ��

package com.mhl.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import com.mhl.db.SqlHelper;

public class PFModel extends AbstractTableModel{
	SqlHelper sh=null;
	//dql��
	Vector <String>coumns=null;
	Vector <Vector>rows=null;
	Vector <String>row=null;
	ResultSet res=null;
	ResultSetMetaData rsmd=null;
	
	//��PersonnelFiles������Ӽ�¼
	public boolean pfAdd(String[] arrStr){
		//
		sh=new SqlHelper();
		return sh.execUpdate("insert into PersonnelFiles values (?,?,?,?,?,?,?,?,?,?)", arrStr);
	}
	//��PersonnelFiles����ɾ����¼
	public boolean pfDel(String[] arrStr){
		//
		sh=new SqlHelper();
		return sh.execUpdate("delete from PersonnelFiles where empNo=?", arrStr);
	}
	//��PersonnelFiles�����޸ļ�¼
	public boolean pfUpd(String[] arrStr){
		//
		sh=new SqlHelper();
		return sh.execUpdate("update PersonnelFiles set p_name=?, pictureUrl=?, sex=?, address=?,hireDate=?,idCard=?, backgroud=?, post=?, married=? where empNo=?",arrStr);
	}
	
	//����������ѯ��PersonnelFiles����
	public void pfQuery(String sql,String[] arrStr){
		sh=new SqlHelper();
		res=sh.execQuery(sql, arrStr);
		try{
			rsmd=res.getMetaData();
			//���������ݣ�������
			rows=new Vector<Vector>();
			coumns=new Vector<String>();
			//ͨ�ð�Ļ�ȡ��ͷ���֡���ע�����Ǵ�1��ʼ��ŵġ�
			for(int i=0;i<rsmd.getColumnCount();i++){
				coumns.add(rsmd.getColumnName(i+1));
			}
			//ͨ�ð�Ļ�ȡ�����е����ݡ���ע�����Ǵ�0��ʼ��ŵġ�
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
		//���������˷��;Ϳ��Բ��û��ˣ��������Ǵ�1��ʼ�����Ǵ�0��ʼ.toString()
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





