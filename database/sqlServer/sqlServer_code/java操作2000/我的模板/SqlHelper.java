package com.mhl.db;


//java操作2000和oracle只有加载驱动不同其余的全部一样的操作。
//但是oracle还多了，一个存储过程给java调用

import java.sql.*;

public class SqlHelper {
	
	//定义操作数据库需要的对象
	String dirver="com.microsoft.jdbc.sqlserver.SQLServerDriver";
	String url="jdbc:microsoft:sqlserver://127.0.0.1:1433;databaseName=Restaurant";
	String user="sa";
	String password="666666";
	
	
	Connection ct=null;
	PreparedStatement ps=null;
	ResultSet res=null;
	
	//构造函数
	public SqlHelper(){
		try {
			Class.forName(this.dirver);
			this.ct=DriverManager.getConnection(this.url,this.user,this.password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//关闭数据库资源
	public void closeDB(){
		try{
			if(this.res!=null) this.res.close();
			if(this.ps!=null) this.ps.close();
			if(this.ct!=null) this.ct.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//执行查询《条件查询》
	public ResultSet execQuery(String sql,String[] arrStr){
		try{
			//创建火箭车,并且给问号赋值然后发送sql语句
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

	//执行增删改《dml语句》
	public boolean execUpdate(String sql,String[] arrStr){
		boolean b=true;
		try{
			//创建火箭车,并且给问号赋值然后发送sql语句
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
