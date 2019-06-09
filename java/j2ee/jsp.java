













1、jsp原理：
	当jsp文件第一次被访问，tomcat就把jsp->servlet->字节文件常驻内存，修改了该jsp访问时就会重新翻译
	被翻译的文件和字节文件，放在tomcat/work目录下，如果你不用这个jsp页面了，那两个自动生成的文件是不会自动删除的，
	如果你把你的web应用卸载了，则才会删除。jsp他是可以说是一个适应MVC模式的框架：index.jsp入口只能转发->login.jsp->只能转发到Servlet控制器->只能转发到main.jsp

2、九大内置对象
	1、out、session、cookie、request、response、application、pageContext、page、config

3、jsp的语法
	1、指令元素（directiv）
		1、<%@ page %>一些属性如下：
			1、language/errorPage/jsErroePage  //这个不重要
			2、seeion  //默认true:要用seeion内置对象
			3、buffer  //给out对象指定缓存大小默认8k如果等到结束还没8k则也会发过去
			4、autoFlash  //默认true:buffer满后自动发送
			5、isThreadSafe  //默认true，msb说默认是false：该jsp线程安全由程序员自己管理，这个默认值可能我记错了，但是保持默认值就对了
			6、contenType/pageEncoding  //指定网页显示编码/指定网页和servlet引擎翻译的编码
			7、import  //引入

		2、<%@ incluce file="xx.jsp"%>  ：静态引入另一个jsp页面，不可传参数，他只会生成一个servlet文件，编译之前合并
		3、<%@ taglib %>：引入标签
	2、脚本元素（scriptlet）
		1、<% %>：这里可以用!来修饰你的变量和方法，用!修饰的就是此servlet的成员属性了，记得线程安全
		2、<%= %>：表达式，相当于out.print()
	3、动作元素（Action）
		1、<jsp:forward page=”/WEB-INF/login.jsp”></jsp:forward>《转发》
		2、<jsp:incluce file=”b.jsp”></jsp:incluce>《会翻译成两个servlet文件，并且可以传参数，但是两个request不同的一个对象b.jsp的request大于a.jsp的，区别静态引入》
		3、还有一些jsp带头的标签就不用管了，比如还有<jsp:usebean/>
	4、注释元素：
		1、<!-- -->
		2、<%-- -->：建议用这一种减少网络传输的开销

4、jsp三大标签：标签使用原则：jstl+el首先，如果有需求就用（jstl+el+struts混用可能会对文件上传支持更好一点因为有一个文件域控件）
	1、自定标签不讲了（即：老程序员喜欢用的）
	2、jstl标签：在jstl炸包/META-INF/这里就有jstl的一些标签库uri就是用这里库：<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
		1、这里只讲c标签，不讲sql好xml标签
			1、c:out案例：(他还有两个参数：default如果在全部域对象中找不到的默认值，escapexml=false可以让html标签形式显示)
				<c:out vaule="${user.name}"></c:out>=${user.name}	
			2、if单分支：test=boolean用el表达式搞定一个特殊的${empty name}如果name属性=null/""返回true：<c:if test="${a=='hi'}">${'他是hi'}</c:if>
			3、迭代标签：但是在取map里面的元素时，会不会是无序的呢
				<c:forEach items="${result_users_map}" var="user">
					${"key="+user.name}
					${"vaule="+user.value}
					${"如果值是对象这还可以取出此对象的属性="+user.value.age}
				</c:forEach>
				<c:forEach var="i" begin=1 end=10 step=3>
					${"相当于：i+=3的for循环"}
				</c:forEach>
			
			4、<c:set var="age" value=10 scope="request"></c:set>和<c:remove var="age" scope="request"></c:set>这两个就不讲了
			5、try/catch案例：<c:ctach var="e">${8/0}</c:ctach>${e.message}
			6、分隔标签：<c:forTokens items="12;l;19" delims=";" var="temp">${temp}</c:forTokens>	
			7、<c:redirect url="a.jsp">和<c:import url="a.jsp">：这个很少用他好像可以实现两个jsp在WEB-INF内重定向的效果
			8、swith/case
				<c:choose>
					<c:when test="${user.age<10}">${太小了}</c:when>
					<c:when test="${user.age>10}">${太大了}</c:when>
				</c:choose>

			经验：
				1、${_name}=（在全部域对象找属性，优先级：request>Session>ServletContext）{可以得到get/post提交过来的参数吗，可以得到actionform的属性的}
				2、${param._name}（是用用来的到get/post提交过来的参数）
				3、el表达式里面可以放运算符什么的都可以
				4、${pageContext.request.contextPath}  //得到项目的名称结果：/strutsLogin
				5、request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"  //得到项目的url结果：http://localhost:8080/strutsLogin/
				6、<base href="<%=basePath%>">  //每个href前加：http://localhost:8080/strutsLogin/
				7、最好用el表达式来代替c:out的表达式
	


















