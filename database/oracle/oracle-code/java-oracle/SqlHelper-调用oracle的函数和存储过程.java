/**
 * ���ܣ���Ҫ��java����oracle�ĺ����ʹ洢���̡�
 */
public class SqlHelper {

	String driver,url,un,pw;//properties�ļ���ȡ�����������
	Connection ct;
	PreparedStatement ps;
	CallableStatement cs;
	ResultSet rs;
	
	/**
	 * �����ʼ��
	 */
	public SqlHelper(){
		this.driver="oracle.jdbc.driver.OracleDriver";
		this.url="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
		this.un="scott";
		this.pw="m123";
		
		try {
			//1.�������� 2.�õ����� 3.����������������ʺŸ�ֵ��ִ�С�4.�õ���������ص������Լ�ʹ��
			Class.forName(driver);
			ct=DriverManager.getConnection(url,un,pw);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    	��oracle�ĺ�����1��ps����2�������������ֵ  3������̨���ã�select functionName(����1,����2,...)����
	��oracle�Ĺ��̣�1��cs����2�����븳ֵ���ע��3������̨���ã�call/exec procejureName(�����������...)����

	/**
	 * ���á�oracle�ĺ������ķ���
	 */
	public void callFunction1(){
		try {
			ps = ct.prepareStatement("select scott.pageckName.functionName(����1,����2,...) from dual");
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
	 * ���á�oracle���д洢���̡��ķ���
	 */
	public void callProcedure2(){
		try {
			cs = ct.prepareCall("{call fenye(?,?,?,?,?,?)}");
			
			//���ʺŸ�ֵ/ע��
			cs.setString(1, "emp");
			cs.setString(2, "3");
			cs.setString(3, "3");
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.CURSOR);
			cs.registerOutParameter(5, oracle.jdbc.OracleTypes.INTEGER);
			cs.registerOutParameter(6, oracle.jdbc.OracleTypes.INTEGER);
			
			//ִ��
			cs.execute();
			rs = (ResultSet) cs.getObject(4);
			while(rs.next()) {
				System.out.println("���ţ�"+rs.getString(1)+"//���֣�"+rs.getString(2));
			}
			int rowCount=cs.getInt(5);
			int pageCount=cs.getInt(6);
			System.out.println("ȫ��Ա��������"+rowCount+"//����ҳ��"+pageCount);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeDB();
		}
	}
	
	
	/**
	 * �ر����ݿ����ӣ���Դ�ķ����Լ�д��
	 */
	public void closeDB(){}
}
