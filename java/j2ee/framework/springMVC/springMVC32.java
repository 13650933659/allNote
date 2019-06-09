

1、springmvc是什么
	1、是spring的一个子模块和spring是无缝的，是一个web框架
	2、原理
		1、用户请求到前端控制器(DispatcherServlet)，然后调用处理器映射器(HandlerMapping)找到对应的处理器(Controller)，
		2、然后在调用处理器映射器(HandlerAdapter)，处理器映射器适配之后调用处理器
		3、处理器执行完之后返回ModeAndView给前端控制器
		4、前端控制器得到ModeAndView之后传给视图解析器放回具体的View
		5、前端控制器根据View进行视图渲染，然后相应客户
2、方法返回值
	1、ModelAndView：需要方法结束时，定义ModelAndView，将model和view分别进行设置。参考如下代码
	2、String，这种返回值用于1、2就好了
		1、return "redirect:queryItems.action";
		2、return "forward:queryItems.action";
		3、return "itemsList.jsp";			//是forward的方式直接的视图地址，相当于modelAndView.setViewName("items/itemsList");，如果真的要返回字符串给前台需要使用@ResponseBody
	3、void：像原始servlet的开发
	4、pojo类使用@ResponseBody标识以json格式返回给前台
3、方法形参绑定
	1、从客户端请求key/value数据
		1、默认支持的
			1、HttpServletRequest、HttpServletResponse、HttpSession
			2、Model、ModelMap，我们往这个参数放存放数据，他们将数据填充到request域。
		2、简单类型，通过@RequestParam对简单类型的参数进行映射required表示是否必须传入。如果前台传过来的和形参一样则不用映射
			1、 public String editItems(Model model,@RequestParam(value="id",required=true) Integer items_id);
		3、pojo类型(使用OGNL)
			1、前台传的name="张三"会被绑定到pojo的name属性
			2、前台传的item.name="手机"会被绑定到pojo的item属性的name属性
			3、如果前台传入有时间类型，那么需要我们写一个时间格式的转换器
		4、集合类型
			1、数据类型
				1、前台传过来id=1&id=2&id=3
				2、后台public String deleteItems(Integer[] items_id);即可接收到[1,2,3]
			2、List集合
				1、前台的list[0].name="张三"&list[0].age=20&list[1].name="李四"&list[0].age=25
				2、后台public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo);itemsQueryVo里边的list会得到值[{name="张三",age=20},{name="李四",age=25}]
			3、Map集合
				1、前台的map['name']="张三"&map['age']=20
				2、后台public String editItemsAllSubmit(ItemsQueryVo itemsQueryVo);itemsQueryVo里边的map会得到值{name="张三",age=20}
	2、json交互，springmvc中使用jackson的包进行json转换
		1、加入jackson的jar包，使用<mvc:annotation-driven />中的处理器适配器默认就有jsckson的转换器
		2、在controller中使用@RequestBody和@ResponseBody即可
4、参数校验器，springmvc使用的是hibernate的校验器
	1、hibernate的校验框架validation所需要3个jar包(请参考项目)
	2、在springmvc.xml中配置校验器如下
		<!-- 校验器 -->
		<bean id="validator" class="org.springframework.validation.beanvalidation.LocalValidatorFactoryBean">
			<!-- hibernate校验器-->
			<property name="providerClass" value="org.hibernate.validator.HibernateValidator" />
			<!-- 指定校验使用的资源文件，在文件中配置校验错误信息，如果不指定则默认使用classpath下的ValidationMessages.properties -->
			<property name="validationMessageSource" ref="messageSource" />
		</bean>
		<!-- 校验错误信息配置文件 -->
		<bean id="messageSource" class="org.springframework.context.support.ReloadableResourceBundleMessageSource">
			<!-- 资源文件名-->
			<property name="basenames">   
			 <list>    
				<value>classpath:CustomValidationMessages</value> 
			 </list>   
			</property>
			<!-- 资源文件编码格式 -->
			<property name="fileEncodings" value="utf-8" />
			<!-- 对资源文件内容缓存时间，单位秒 -->
			<property name="cacheSeconds" value="120" />
		</bean>
	3、在springmvc.xml中的处理器的适配器中配置校验器，如下
		<mvc:annotation-driven conversion-service="conversionService" validator="validator"></mvc:annotation-driven>
	4、在classpath下定义错误信息，文件：CustomValidationMessages.properties{items.name.length.error=长度过长,items.name.length.isNull=不能为空}
	5、定义两个没有方法的接口ValidGroup1、ValidGroup2只要作用是对不同的校验规则进行分组
	6、定义校验规则
		1、长度校验属于ValidGroup1分组
			@Size(min=1,max=30,message="{items.name.length.error}",groups={ValidGroup1.class})
			private String name;
		2、非空校验属于ValidGroup2分组
			@NotNull(message="{items.createtime.isNUll}")
			private Date createtime;
	7、捕获校验错误信息注意：@Validated和BindingResult bindingResult是配对出现，并且形参顺序是固定的（一前一后）	
		public String editItemsSubmit(Model model, @Validated(value = { ValidGroup1.class }) ItemsCustom itemsCustom,BindingResult bindingResult) {
				// 获取校验错误信息
				if (bindingResult.hasErrors()) {
					// 将错误信息传到页面
					model.addAttribute("allErrors", bindingResult.getAllErrors());
					return "items/editItems";
				}
		}
5、数据回显
	1、@ModelAttribute
		1、形参是pojo类型会根据pojo名小写开头给前台回显，如果形参名不符合规则，可以用@ModelAttribute，简单类型的形参也用@ModelAttribute
		2、用法@ModelAttribute("itemtypes")注解在方法的头上，每个请求的url都会回显此方法返回的数据
	2、只用使用Model和ModelAndView就行了
6、全局异常处理器
	1、springmvc提供一个HandlerExceptionResolver接口
	2、在springmvc.xml中配置：<bean class="com.exception.CustomExceptionResolver"></bean>
7、拦截器(Interception)
	1、定义拦截器，实现HandlerInterceptor接口，三个方法的应用场景请参考项目
	2、在springmvc.xml中配置如下
		<mvc:interceptors>
			<!--多个拦截器,顺序执行 -->
			<!-- 登陆认证拦截器，"/**"表示所有url包括子url路径 -->
			<mvc:interceptor>
				<mvc:mapping path="/**"/>
				<bean class="com.interceptor.LoginInterceptor"></bean>
			</mvc:interceptor>
		</mvc:interceptors>


8、上传图片，对form的多部件的支持(有空再去看)
9、RESTful支持(有空再去看)

10、 @ControllerAdvice 全部的controllerr的增强器 有空去看


localhost:8769/api/addUser?companyId=27&apicode=SERVICE-API-DETAIL&userId=a7&starttime=2019-01-02&endtime=2019-01-02