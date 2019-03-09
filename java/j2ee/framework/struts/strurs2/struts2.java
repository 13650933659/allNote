
	
	
	
1、前提知识
	1、 Action  的开发流程
		1、要使用struts的前提是在web.xml下加过滤器(这是2.1以上的,2.0的不同)
			<filter>
				<filter-name>struts2</filter-name>
				<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
			</filter>
			<filter-mapping>
				<filter-name>struts2</filter-name>
				<url-pattern>/星号</url-pattern>
			</filter-mapping>

		2、写一个 Action 需要继承 ActionSupport
			public class TagAction extends ActionSupport{
				public String excute() throws Exception{
					return SUCCESS; //直接返回 "success"
				}
			}
		3、 Action 方法的配置
			1、每个方法对应一个 Action  调用：http://localhost/webappName/命名空间/add.action
				<action name="add" class="com.action.LoginAction" method="add">
					<result name="success">/success.jsp</result>  // 1、当add方法返回success字符串时，就会跳转到/success.jsp页面  2、这个也可以不用，add就直接返回json字符串给前台也是可以的
				</action>
			2、动态方法调用：感叹号,要启用静态方法（一个Action搞定）案例：调用LoginAction的add方法：http://localhost/webappName/命名空间/LoginAction!add
				<action name="loginAction" class="com.action.LoginAction">
				</action>
			3、通配符方法：禁用静态方法：<constant name="struts.enable.DynamicMethodInrocation" value="true"/>推荐使用
				<action name="*" method="{1}" class="com.imooc.web.struts.action.HelloWorldAction">
					<result name="succuss">/WEB-INF/{1}.jsp</result>
				</action>


	3、得到两参的方法：HttpServlet/HttpSerlet等web元素（map类型常用/真实类型）
		1、Actioncontext（自己初始化再用）
		2、实现***Aware接口（IOC控制反转=DI依赖注入再用记得那个构造方法要是参数为空的要不你就不写）
		3、ServletActionContext



	4、接收参数（注意类型转换）
		1、在Action的属性直接接收（要set/get）
		2、<input type="text" name="user.un">,写opjo类User的属性((要set/get)),再去Action注入一个User对象属性（也要set/get）
		3、控件不用user.,让Action实现接口ModelDriven<User>,实现getModel(){return user}方法，《并且要new User》
		

	5、处理用户输入空件的参数，和返回的结果集字串
		1、五个常量结果集串（SUCCESS/NONE/ERROR/LOGIN/INPUT）
		2、自动返回登录视图案例：
		第一步先去该action内添加一个<resoult name="input">登录视图
		第一种：this.addFilterError("key","value");在login方法内就要return INPUT;如果重写父类的validate方法就不用INPUT就可以直接返回登录视图
		第二种：自动匹配输入的数据属性，有误自动直接返回登录视图，有点像服务器的正则表达式

	6、指定多个配置文件
		1、<include file="*.xml"/>被引入文件要齐全,这个有经验的经理就可以用

	7、默认ation：用于找不到的Action
		//好像和*有冲突
		在package内部配置如下
		<default-action-ref name="index"/>
		<action name="index">
			//直接调用不用写实在的对应Action，这是bug吗?去web.xml配置欢迎页面去配置Action的名字可以用web.xml的：用于搜不到Action直接给回的页面
			<result>/WEB-INF/error.jsp</result>
		</action>

	8、struts2的后缀名
		1、三个地方可以配置，一般我们在struts.xml中配置：<constant name="struts.action.extension" value="html"/>

	9、action的搜索顺序（跟namespace有关）
		1、跟<package name="default" namespace="/" extends="struts-default">有关系









2、转发和跳转
	1、转发/跳转用好四种就可以了
	1、dispatch  //只转发到jsp（默认的）
	2、chain  //只转发到下一个Action
	3、redirct  //只重定向到下jsp
	4、redirctAction  //只重定向到下一个Action

	2、默认包全局转发/跳转：用于出错页面


3、struts2的标签
	1、 s 标签的使用 
		1、先引入 <%@ taglib uri="/struts-tags" prefix="s" %>
		2、 <s:action name="myaction" executeResult="true"></s:action>  // <!-- 这句会显示请求 名字为 myaction 的action -->
    
		


4、拦截器的工作原理：他的本质是过滤器，工作原理一样
	1、自定义拦截器和拦截器栈（很少自定义拦截器）
	2、token拦截器的防止刷新（还有其他三防刷新的方法：两个A/重定向到jsp或者A/post提交让浏览器提示）
		1、token拦截器的原理：
			当用户第一次访问添加jsp页时拦截器就会在session加入随机token，并加入到用了<s:token/>的表单加入一个隐藏域，
			提交时又经过此拦截器则判断此隐藏域的值是否等于session的值，匹配到则给提交并且马上清掉此session当用户按刷新时，
			这时已经没有session和隐藏域的值匹配了，则放回invalid.token,所以我们还要在此A加一个:
			<result name="invalid.token">error.jsp</result>为了友好
	3、DefaultTypeConverter数据类型转化
		1、但是有时他转化出错，我们可以自己继承他然后再用写转化方法，再加配置文件（XXAction-conversion.propertyes/xwork-conversion.propertyes）
		2、还可以用request/session等可以得到jsp传过来的域对象
	4、文件上下传听说也是用拦截器实现的。可能要用到servletConfigInterptor第三个拦截器来得到web元素，实现文件上传下载

