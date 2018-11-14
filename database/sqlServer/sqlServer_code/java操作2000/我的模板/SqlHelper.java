package com.mhl.db;


//java����2000��oracleֻ�м���������ͬ�����ȫ��һ���Ĳ�����
//����oracle�����ˣ�һ���洢���̸�java����

import java.sql.*;

public class SqlHelper {
	
	//����������ݿ���Ҫ�Ķ���
	String dirver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=Restaurant";
	String user="sa";
	String password="666666";
	
	
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet res=null;
	
	//���캯��
	public SqlHelper(){
		try {
			Class.forName(this.dirver);
			this.ct=DriverManager.getConnection(this.url,this.user,this.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//�ر����ݿ���Դ
	public void closeDB(){
		try{
			if(this.res!=null) this.res.close();
			if(this.ps!=null) this.ps.close();
			if(this.ct!=null) this.ct.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//ִ�в�ѯ��������ѯ��
	public ResultSet execQuery(String sql,String[] arrStr){
		try{
			//���������,���Ҹ��ʺŸ�ֵȻ����sql���
			this.ps=this.ct.prepareStatement(sql);
			for(int i=0; i<arrStr.length;i++){
				this.ps.setString(i+1, arrStr[i]);
			}
			this.res=this.ps.executeQuery();
		}catch(Exception e){
			e.printStackTrace();
		}
		return this.res;
	}

	//ִ����ɾ�ġ�dml��䡷
	public boolean execUpdate(String sql,String[] arrStr){
		boolean b=true;
		try{
			//���������,���Ҹ��ʺŸ�ֵȻ����sql���
			this.ps=this.ct.prepareStatement(sql);
			for(int i=0; i<arrStr.length;i++){
				this.ps.setString(i+1, arrStr[i]);
			}
			this.ps.executeUpdate();
		}catch(Exception e){
			b=false;
			e.printStackTrace();
		}finally{
			this.closeDB();
		}
		return b;
	}
}
