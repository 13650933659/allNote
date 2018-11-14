/**
 * 功能：主要是java调用oracle的函数和存储过程。
 */
public class SqlHelper {

	String driver,url,un,pw;//properties文件读取用类加载器吧
	Connection ct;
	PreparedStatement ps;
	CallableStatement cs;
	ResultSet rs;
	
	/**
	 * 构造初始化
	 */
	public SqlHelper(){
		this.driver="oracle.jdbc.driver.OracleDriver";
		this.url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		this.un="scott";
		this.pw="m123";
		
		try {
			//1.加载驱动 2.得到连接 3.创建火箭车《并给问号赋值再执行》4.得到结果集返回调用者自己使用
			Class.forName(driver);
			ct=DriverManager.getConnection(url,un,pw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    	《oracle的函数：1、ps调用2、输入输出都赋值  3、控制台调用（select functionName(参数1,参数2,...)）》
	《oracle的过程：1、cs调用2、输入赋值输出注册3、控制台调用（call/exec procejureName(输入输出参数...)）》

	/**
	 * 调用《oracle的函数》的方法
	 */
	public void callFunction1(){
		try {
			ps = ct.prepareStatement("select scott.pageckName.functionName(参数1,参数2,...) from dual");
			for(int i = 0; i < arrStr.length; i++){
				ps.setString(i + 1, arrStr[i]);
			}
			ps.execute();
		} catch (Exception e) {
			e.printStackTrace();
			b=false;
		}finally{
			closeDB();
		}
	}
	
	
	/**
	 * 调用《oracle的有存储过程》的方法
	 */
	public void callProcedure2(){
		try {
			cs = ct.prepareCall("{call fenye(?,?,?,?,?,?)}");
			
			//给问号赋值/注册
			cs.setString(1, "emp");
			cs.setString(2, "3");
			cs.setString(3, "3");
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.INTEGER);
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.INTEGER);
			
			//执行
			cs.execute();
			rs = (ResultSet) cs.getObject(4);
			while(rs.next()) {
				System.out.println("部门："+rs.getString(1)+"//名字："+rs.getString(2));
			}
			int rowCount=cs.getInt(5);
			int pageCount=cs.getInt(6);
			System.out.println("全部员工数量："+rowCount+"//多少页："+pageCount);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeDB();
		}
	}
	
	
	/**
	 * 关闭数据库连接，资源的方法自己写吧
	 */
	public void closeDB(){}
}
