1、要使用struts的前提是在web.xml下加过滤器(这是2.1以上的,2.0的不同)
	<filter>
		<filter-name>struts2</filter-name>
		<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>struts2</filter-name>
		<url-pattern>/星</url-pattern>
	</filter-mapping>

2、Action方法的调用
	1、每个方法对应一个Action，调用：http://localhost/webappName/命名空间/add.action
		<action name="add" class="com.action.LoginAction" method="add">
		    <result name="success">/success.jsp</result>  // 这个也可以不用，add就直接返回json字符串给前台
		</action>
	2、动态方法调用：感叹号,要启用静态方法（一个Action搞定）案例：调用：http://localhost/webappName/命名空间/XXAction!add
		<action name="add" class="com.action.LoginAction" method="add">
		    <result name="success">/success.jsp</result>  // 这个也可以不用，add就直接返回json字符串给前台
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
