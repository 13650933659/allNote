


讲师-周丰阳

杂项
	1、注解类型
		1、 @Configuration				// springboot的配置类同时也是@Component，可以使用配置文件来注入值
		2、 @SpringBootApplication		// springboot的主程序
		3、 @SpringBootConfiguration	// springboot的配置类型也是@configuration
		4、 @EnableAutoConfiguration	// 启用自动配置，他会自动注入controller和springmvc等等组件


	2、类和接口
		1、ResourceProperties			// (spring.resource)静态资源的配置类
		2、ResourceHandlerRegistry		// 用来管理ResourceHandlerRegistration
		3、ResourceHandlerRegistration	// 里边有(pathPatterns[]和locationValues[]怎么来的)
		4、WebMvcProperties				// (spring.mvc)配置mvc的属性，比如staticPathPattern就是配置静态资源的访问路径
		5、WebMvcAutoConfiguration.ResourceHandlerRegistrationCustomizer	// 这个可能是定制我们自己的静态资源访问注册器
		6、WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter			// 他也是继承了WebMvcConfigurerAdapter对mvc进行扩展
		7、FaviconConfiguration			// (spring.mvc.favicon)喜欢的图标配置
		8、WebMvcConfigurer				// 就相当于一个@Configuration并且继承WebMvcConfigurerAdapter的类，用WebMvcConfigurerComposite管理


	1、问题
		1、Interceptor他拦截所有的请求，那么他是怎么排除静态资源的呢？如果是webapp目录下的他是不用过滤的，如果是springboot他自动帮我们映射好了
		2、thymeleaf公共页面元素抽取和引入，传参数等
		3、resful风格的请求，改变请求的方法，
			1、可以使用SpringMVC中配置HiddenHttpMethodFilter;（SpringBoot自动配置好的）
			2、页面创建一个post表单
			3、创建一个input项，name="_method";值就是我们指定的请求方式
		4、错误处理机制
		5、springboot默认使用的是嵌入式的tomcat配置嵌入式其他Servlet容器
		6、使用外置的Servlet容器


	1、经验
		1、最好把静态资源和网站首页放在classpath:static\等等




1、springboot是什么
	1、整合了spring技术栈，大量的使用自动化的配置，使得基于spring的应用开发起来更加简化
	2、自动化配置原理，有空去看看

2、开发流程
	1、pom.xml继承spring‐boot‐starter‐parent继承spring‐boot‐dependencies，加入依赖直接使用springboot的启动器，例如使用spring‐boot‐starter‐web，可以使用springmvc
	2、编写src/main/resources/application.yml(bootstrap.yml优先级最高)等文件
	3、主程序的开发
		@SpringBootApplication
		public class SpringBoot04WebRestfulcrudApplication {
			public static void main(String[] args) {
				SpringApplication.run(SpringBoot04WebRestfulcrudApplication.class, args);
			}
		}

3、主配置文件
	1、默认的springboot的配置文件
		1、位置放在：src/main/resources
		2、文件名称：application.yml(bootstrap.yml优先级最高)，application.properties
	2、yml(yaml)语法
		1、k: v，注意要有空格
		2、一般不加引号，双引号不转义，单引号会转义。例子：'z\ns'=z\ns
		3、map
			1、方法一
				friends:
				  name: zs
				  age: 25
			2、方法二
				friends: {name: zs,age: 25}
		4、list,set
			1、方法一
				pets:
				 - cat
				 - dog
			2、方法二
				pets: [cat,dog]
	3、配置文件的注入@ConfigurationProperties和@Value，一般使用前者，如果注入的比较简单则使用后者，注入的值可以使用@Email等校验
		1、在yml文件编写
			 person:
			   name: zs
			   age: 25
		2、javaBean：必须是组件，才能提供@ConfigurationProperties(prefix = "person")的自动注入功能；
			 @Component
			 @ConfigurationProperties(prefix = "person")
			 public class Person {
			   private String name;
			   private int age;
			 }
		3、如果在编写yml文件需要提示可以导入依赖
			 <dependency>
			   <groupId>org.springframework.boot</groupId>
			   <artifactId>spring‐boot‐configuration‐processor</artifactId>
			   <optional>true</optional>
			 </dependency>
	4、@PropertySource @ImportResource @Bean
		1、在javabean注解@PropertySource(value = {"classpath:person.yml"})：通过指定的配置文件来注入值
		2、在javabean注解@ImportResource(locations = {"classpath:beans.xml"})：加载指定的spring配置文件
		3、spingboot推荐使用在某个类注解@Configuration使用@Bean来注入bean，不用以前的xml了
	5、配置文件的占位符
		1、随机数：${random.value}、${random.int}、${random.long}
		2、默认值：${person.hello:hello}
	6、profile
		1、多配置文件
			 主配置文件名可以是 application-{profile}.yml默认使用application.yml，指定激活哪个{profile}(指定激活了，那么默认的配置文件生效吗？生效)
		2、多文档块
			 server:
			   port: 8081
			 spring:
			   profiles:
				 active: prod #指定激活哪个环境
			 ‐‐‐
			 spring:
			   profiles: dev
			 ...
			 ‐‐‐
			 spring:
			   profiles: prod #指定属于哪个环境
			 ...
	7、激活指定profile
		1、在主配置文件中指定 spring.profiles.active=dev
		2、命令行：java -jar pro-v001.jar --spring.profiles.active=dev；
		3、虚拟机参数；-Dspring.profiles.active=dev
	8、主配置文件的位置：通过spring.config.location来改变默认的配置文件位置，优先级由高到底，高覆低，其余互补配置；
		–file:./config/
		–file:./
		–classpath:/config/
		–classpath:/

4、日志管理
	1、所有的日志都统一到slf4j；
		1、排除其他日志框架门面；
		2、用中间包来替换原有的日志框架(他会调用slf4)；
		3、我们导入slf4j，和实现(选用logback，是log4j的升级版)
	2、slf4j的使用
		public void contextLoads() {
			Logger logger = LoggerFactory.getLogger(getClass());
			// 由低到高 trace<debug<info<warn<error，默认info级别，只会输出>=info级别的日志
			logger.trace("这是trace日志...");
			logger.debug("这是debug日志...");
			logger.info("这是info日志...");
			logger.warn("这是warn日志...");
			logger.error("这是error日志...");
		}

	3、日志输出格式：%d{yyyy‐MM‐dd HH:mm:ss.SSS} [%thread] %‐5level %logger{50} ‐ %msg%n
		1、%d：         表示日期时间，
		2、%thread：    表示线程名，
		3、%‐5level：   级别从左显示5个字符宽度
		4、%logger{50}：表示logger名字最长50个字符，否则按照句点分割。
		5、%msg：       日志消息，
		6、%n：         是换行符
	4、日志配置
		1、logging.level=trace：			日志级别
		2、logging.file=C:/springboot.log： 使用全路径，就不需要logging.path配置了
		3、logging.pattern.console：        在控制台输出的日志的格式
		4、logging.pattern.file：           指定文件中日志输出的格式

5、web项目的开发
	1、静态资源管理
		1、/webjars/XX都去path=classpath:/META-INF/resources/webjars/找，例如：localhost/webjars/a/a.js=path/a/a.js
		2、"/**"，都去（path=下列四种）找，例如：localhost/abc/a.js =path/abc/a.js
			"classpath:/META‐INF/resources/",
			"classpath:/resources/",
			"classpath:/static/",
			"classpath:/public/"
			"/"：当前项目的根路径
		3、欢迎页=path/index.html页面，例如：localhost=path/index.html
		4、网站图标=path/favicon.ico
	2、thymeleaf：<html lang="en" xmlns:th="http://www.thymeleaf.org">
		1、引入：
			<!-- 4、引入thymeleaf依赖就相当于jsp的功能-->
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-thymeleaf</artifactId>
			</dependency>
			<properties>
				<!-- thymeleaf3适配layout2 -->
				<!-- thymeleaf2适配layout1 -->
				<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
				<thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
			</properties>
		2、使用：只要我们把HTML页面放在classpath:/templates/，thymeleaf就能自动渲染；
			@ConfigurationProperties(prefix = "spring.thymeleaf")
			public class ThymeleafProperties {
				private static final Charset DEFAULT_ENCODING = Charset.forName("UTF‐8");
				private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
				public static final String DEFAULT_PREFIX = "classpath:/templates/";
				public static final String DEFAULT_SUFFIX = ".html";
			}
		3、语法(参考图片)
			1、五种表达式
				1、${}获取变量值OGNL；例如：${session.foo}\${person.name}
				2、*{}选择表达式和${}在功能上是一样；补充配合${}，用法如下
					<div th:object="${session.user}">
						<p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
						<p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
						<p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
					</div>
				3、#{}获取国际化内容
				4、@{}定义URL，例如：@{/order/process(execId=${execId},execType='FAST')}
				5、~{}片段引用表达式，例如：<div th:insert="~{commons :: main}"></div>
			2、spring.thymeleaf.cache=false禁用缓存(开发禁用生成开启)
			3、html代码块的引入
				1、抽取公共片段
					<div th:fragment="name">abc</div>
				2、引入公共片段
					1、~{templatename::fragmentname}:模板名::片段名
					2、~{templatename::selector}：模板名::选择器
				3、例子
					<div th:insert="footer :: name"></div>  ==> <div><footer>abc</footer></div>
					<div th:replace="footer :: name"></div> ==> <footer>abc</footer>
					<div th:include="footer :: name"></div> ==> <div>abc</div>
				4、引入还可以传参数：<div th:replace="commons/bar::#sidebar(activeUri='emps')"></div>，然后代码块可以使用${}取到
	3、开发
		1、自动注入
			1、视图解析器
				1、ViewResolver(视图解析器)
				2、ContentNegotiatingViewResolver(组合所有的视图解析器的)
				3、如何定制：我们可以自己给容器中添加一个视图解析器；自动的将其组合进来；
			2、webjars\欢迎页\网站图标\其他静态资源
			3、Converter,GenericConverter,Formatter：转换器和格式器，自己添加的格式化器转换器，只需要放在容器中即可
			4、HttpMessageConverters用来转换Http请求和响应的；User---Json
			5、MessageCodesResolver 定义错误代码生成规则
			6、ConfigurableWebBindingInitializer：做初始化的
		2、扩展MVC
			1、写一个配置类(@Configuration)WebMvcConfigurerAdapter类型，通过setConfigurers(List<WebMvcConfigurer> configurers)会加入并起作用；
			注意：不能标注@EnableWebMvc会全面结果mvc;
		3、国际化
			1、编写：classpath:i18n/(login.pro/login_zh_CN.pro/login_en_US.pro)
			2、thymeleaf的使用：#{login.btn}
			3、区域解析器默认使用请求头解析区域，但我可以自定义LocaleResolver可以通过传参数解析
		4、自定义拦截器：HandlerInterceptor放入容器中即可
		5、容错处理(有空再去看看)
		6、嵌入式的web容器(以tomcat为例子)
				1、直接在主配置文件配置
					server.port=8081					// 应用监听的端口
					server.context‐path=/crud			// 应用的访问前缀
					server.tomcat.uri‐encoding=UTF‐8	// 解决get请求乱码
					server.xxx							// 通用的Servlet容器设置
					server.tomcat.xxx					// Tomcat的设置
				2、也可以编写一个EmbeddedServletContainerCustomizer：嵌入式的Servlet容器的定制器(使用上述配置就不需要这个定制了)
					@Bean //一定要将这个定制器加入到容器中
					public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
						return new EmbeddedServletContainerCustomizer() {
							//定制嵌入式的Servlet容器相关的规则
							@Override
							public void customize(ConfigurableEmbeddedServletContainer container) {
								container.setPort(8083);
							}
						};
					}
				3、没有web.xml了，那么三大组件【Servlet、Filter、Listener】的配置
					1、Servlet
						@Bean
						public ServletRegistrationBean myServlet(){
							ServletRegistrationBean registrationBean = new ServletRegistrationBean(newMyServlet(),"/myServlet");
							return registrationBean;
						}
					2、Filter
						@Bean
						public FilterRegistrationBean myFilter(){
							FilterRegistrationBean registrationBean = new FilterRegistrationBean();
							registrationBean.setFilter(new MyFilter());
							registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
							return registrationBean;
						}
					3、Listener
						@Bean
						public ServletListenerRegistrationBean myListener(){
							ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
							return registrationBean;
						}





7、启动配置原理(有空再去看)
8、自定义starter(有空再去看)

