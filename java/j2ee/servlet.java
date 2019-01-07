








1、Servlet是什么
	1、Servlet是Java的一个接口，和实现实现了这个Servlet接口的类，Servlet运行于支持Java的容器中(tomcat)。
2、Servlet的开发3种方法
	1、实现Servlet接口：实现5个方法：
		public void init(ServletConfig p){try}
		public ServletConfig getServletConfig(){}
		public void service(ServletRequst req,ServletRespose res){try}
		public String getServletInfo(){}
		public void destroy(){}
	2、实现五个方法有点麻烦所以就有继承GenericServlet抽象类，其实他也实现了servlet接口
		1、public void service(ServletRequst request,ServletRespose respose){try}《一个就可以：业务》
	3、继承HttpServlet：其实他继承了GenericServlet，这种用的最多
		1、第一种：组合doGet和doPost两个方法
		2、第二种：写service一个方法即可不用去管是什么请求了
3、开发完servlet需要去web.xml注册(映射url优先级别：正常>\*此网站全部>*.do)
	<servlet>
		<servlet-name>随意</servlet-name>
		<servlet-class>包名+类名</servlet-class>
		<load-on-startup>
			给他一个值=1，这个Servlet会随着你的web应用启动而调用
			如果给了1不要给这个servlet做映射了，不要给人家调用的机会。
		</load-on-startup >
	</servlet>
	<servlet-mapping>
		<servlet-name>随意</servlet-name>
 		<url-pattern>/你访问的url</url-pattern>
	</servlet-mapping>
3、过滤器也是servlet，过滤器的顺序是按代码的顺序
	1、过滤器类
		public class EncodingFilter extends HttpServlet implements Filter{
			private String encoding = "utf-8";
			 @Override
			public void doFilter(ServletRequest req, ServletResponse resp,
 				FilterChain arg2) throws IOException, ServletException {
  				req.setCharacterEncoding(encoding);
				arg2.doFilter(req, resp);
			}
	 
			@Override
	 		public void init(FilterConfig fc) throws ServletException {
				encoding = fc.getInitParameter("encoding");
				System.out.println("处理中文乱码的方法（只对post请求有效），其编码为：" + encoding);
			}
		}
	2、web.xml配置
		<filter>
			<filter-name>EncodingFilter</filter-name>
			<filter-class>com.filter.EncodingFilter</filter-class>
			<init-param>
 				<param-name>encoding</param-name>
				<param-value>utf-8</param-value>
			</init-param>
		</filter>
		<filter-mapping>
			<filter-name>EncodingFilter</filter-name>
			<url-pattern>\*</url-pattern>
		</filter-mapping>
4、Cookie和Session技术
	1、cookie技术：服务器回写在客户端的信息(小纸条)
		1、特点：
			1、值不可以是对象，不能写中文 
			2、大小最多4k 
			3、客户端可以阻止服务端写 
			4、只能拿自己webapp写入的内容 
			5、针对javaweb可能子目录设的cookie父亲不能读，反过来可以
		2、使用
			1、创建：Cookie cookie = new Cookie(String n,String val);
			2、回写：response.addCookie(cookie);
			3、获得有点麻烦看如下代码：《用for增强遍历reqest.getCookies()数组》
			4、删除有点麻烦看如下代码：《用for增强遍历reqest.getCookies()数组》
			5、生命周期
				1、cookie.setMaxAge(0)设置cookie的生命周期（秒），如果没设则关闭当前浏览器就没有了。
	2、session技术《特点：值是对象》
		1、介绍:
			1、ie访问我们，我们就会给用它分配一个空间（有sessionID和值（键值对）），利用cookie叫ie把JSESSIONID写到客户机上，
			只要不关闭此这个ie并且这个session的生命周期不结束，那么此客户端以后的每一次访问都会带这个JSESSIONID，那么我们就可以通过JSESSIONID拿到属于他的session。
		2、使用   
			1、添加：httpReqest.getSession(true).setAttribute(String name,Object val)
			2、获得：httpReqest.getSession(true).getAttribute(String name)
			3、删除：httpReqest.getSession(true).removeAttribute(name)/invalidate()《结束某个session属性/清空session》
			4、生命周期
				1、setMaxInactiveInterval(int)设置不活跃生命周期（秒，0和负数无限期），默认30min，关闭ie不管时间到没到都会结束，因为浏览器不带那个JSESSIONID，关tomcat或者重新加载你的web应用也会结束。
 				2、不活跃生命周期还可以去D:/tomcat/conf/web.xml配置，如果你想只改变自己的web应用的session就把那个配置复制到你的web.xml文件去<session-config><session-timeout>30</session-timeout></session-config>
				3、还有是同一台机器打开两个ie，是两个不同的session。
 				4、Cookie和session是关联的，如果用户禁用cookie我们的session技术也无法实现，解决用户禁用cookie的方法：
 					1、第一种：检查用户是否禁用cookie如果禁用就弹窗提示一句叫他打开cookie，比较霸道。
 					2、第二种：URL重写，需要用到session的url都要重写一下response.encodeURL(request.getRequestURL().toString())，如果客户没有禁用cookie则url不变，否则会加入JSESSIONID，但是参数他分隔符好像是;而不是平常的?
 					3、总结：如果是局域网的web应用，我们不用重写url，可以要求用户别禁用cookie，但是是公网的web最后要重写一下，比较稳定
 	3、比较session/cookie
		1、保存地方/安全/宽带/生命周期
 		2、核心的业务不要用cookie，要用session，锦上添花的功能可以考虑使用cookie
5、ServletContext应用(相当于web.xmls)
	1、得到全部servlet在web.xml中的配置共享参数：this.getServletContext().getInitParameter(String)
	2、得到属性：this.getServletContext().getAttribute(String)
	3、读取文件：
 		1、this.getServletContext().getResourceAsStream(path)  
  		2、如果path在src就不可以用这种方法读要用：DownloadServlet.class.getClassLoader().getResourceAsStream(path)
		3、还可以再用new java.util.Properties().load(inStream)加载该流进行读取
	4、通过文件相对路径得到此文件在服务器的全路径：
		String path=this.getServletContext().getRealPath("images/aa.jpg")  //在普通servlet文件获取文件的绝对路径
