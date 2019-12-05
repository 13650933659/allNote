

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
			1、 public String editItems(Model model,@RequestParam(value="id",required=true,defaultValue=1) Integer items_id);
		3、pojo类型(使用OGNL)
			1、前台传的name="张三"会被绑定到pojo的name属性
			2、前台传的item.name="手机"会被绑定到pojo的item属性的name属性
			3、如果前台传入有时间类型，那么需要我们写一个时间格式的转换器，前后台分离的是，前后台使用 时间戳来交互，因为平台多了，格式不好写死
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
		2、在controller中使用@RequestBody(把前台的json字符串转成我们的java类或者集合)和@ResponseBody(把我们的java类型和集合转成json字符串返回给前台)
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
		public String editItemsSubmit(Model model, @Validated\@Valid(value = { ValidGroup1.class }) ItemsCustom itemsCustom,BindingResult bindingResult) {
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
	1、文件上传
		@PostMapping
		public FileInfo upload(MultipartFile file) throws Exception {

			System.out.println(file.getName());				// 文件对象的参数名称 这里是 file
			System.out.println(file.getOriginalFilename());	// 上传时的文件名称
			System.out.println(file.getSize());				// 文件的大小

			File localFile = new File("c:/file", new Date().getTime() + ".txt");
			file.transferTo(localFile);     // 把上传的文件写到 localFile
			return new FileInfo(localFile.getAbsolutePath());
		}
	2、文件下载
		@GetMapping("/{id}")
		public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
			// jdk7的新语法 把流写在 try()中就可以不用关闭流了
			try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
					OutputStream outputStream = response.getOutputStream();) {
				response.setContentType("application/x-download");
				response.addHeader("Content-Disposition", "attachment;filename=test.txt");
				IOUtils.copy(inputStream, outputStream);    // 把 inputStream 写到 outputStream
				outputStream.flush();
			} 
		}

9、RESTful支持(有空再去看)
	1、 异步请求
		1、 使用 Callable<String> 的方法实现异步请求，最后返回 Callable<String> 即可
			@RequestMapping("/order1")
			public Callable<String> order1() throws Exception {
				logger.info("主线程开始");
				Callable<String> result = new Callable<String>() {
					@Override
					public String call() throws Exception {
						logger.info("副线程开始");
						Thread.sleep(1000);
						logger.info("副线程返回");
						return "{success:true}";
					}
				};
				logger.info("主线程结束");
				return result;
			}
		2、 使用 DeferredResult<String> 的方法实现异步请求 最好返回 DeferredResult<String> 即可
			@RequestMapping("/order2")
			public DeferredResult<String> order2() throws Exception {
				logger.info("主线程开始");

				String orderNumber = RandomStringUtils.randomNumeric(8);
				mockQueue.setPlaceOrder(orderNumber);	// 把这个请求往消息队列存放，然后有另外的服务器坚挺到消息并且处理这个请求

				// 创建 DeferredResult<String> result 对象，
				// 一旦监听消息队列(即：取刚刚那条请求的响应消息)的方法，立即调用 result.setResult("{name:'zs'}");则前台就可以得到响应了
				DeferredResult<String> result = new DeferredResult<>();
				deferredResultHolder.getMap().put(orderNumber, result);

				logger.info("主线程结束");
				return result;
			}
		3、 如果是使用 Callable<String> 或者 DeferredResult<String> 做异步请求的服务，需要使用以下四个设置对应的拦截器和对应的任务处理超时和异常处理
			@Override
			public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
				configurer.registerCallableInterceptors();
				configurer.registerDeferredResultInterceptors();
				configurer.setDefaultTimeout(1);
				configurer.setTaskExecutor();
			}
		4、 总结： 这样异步之后我们的tomcat的程序池空闲的线程就多了，但是前台还是要等到子线程处理完成才可以得到相应，所以前台应该也是异步的调用
	2、 @PathVariable 注解可以取得 @GetMapping("/{id}") id的值
10、 @ControllerAdvice 全部的controllerr的增强器 有空去看
	1、  处理所有控制器抛出的异常统一处理
		@ControllerAdvice
		public class ControllerExceptionHandler {
			/**
			 * 捕捉{@link UserNotExistException}异常，并且处理（如果是浏览器请求是直接返回 resources/error/状态码.html  如果有了这个增强类，就不会找 状态码.html 了）
			 * 其实这个方法更好的做法是 如果是浏览器请求有text/html的，直接返回ModelAndView ， 如果其他的移动端请求则直接 返回下面的结果即可 
			 * @param ex
			 * @return
			 */
			@ExceptionHandler(UserNotExistException.class)
			@ResponseBody
			@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)	// 这是返回的状态码
			public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
				Map<String, Object> result = new HashMap<>();
				result.put("id", ex.getId());
				result.put("message", ex.getMessage());
				return result;
			}
		}

11、 springmvc 的 controller 的拦截机制
	用户请求 -> tomcat容器依赖 -> Filter -> Interceptor -> ControllerAdvice -> Aspect(不需要 @EnableAspectJAutoProxy 注解，可能其他的切面需要) -> Controller
	用户请求 <- tomcat容器依赖 <- Filter <- Interceptor <- ControllerAdvice <- Aspect(不需要 @EnableAspectJAutoProxy 注解，可能其他的切面需要) <- Controller






