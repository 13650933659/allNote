


��ʦ-�ܷ���

����
	1��ע������
		1�� @Configuration				// springboot��������ͬʱҲ��@Component������ʹ�������ļ���ע��ֵ
		2�� @SpringBootApplication		// springboot��������
		3�� @SpringBootConfiguration	// springboot����������Ҳ��@configuration
		4�� @EnableAutoConfiguration	// �����Զ����ã������Զ�ע��controller��springmvc�ȵ����


	2����ͽӿ�
		1��ResourceProperties			// (spring.resource)��̬��Դ��������
		2��ResourceHandlerRegistry		// ��������ResourceHandlerRegistration
		3��ResourceHandlerRegistration	// �����(pathPatterns[]��locationValues[]��ô����)
		4��WebMvcProperties				// (spring.mvc)����mvc�����ԣ�����staticPathPattern�������þ�̬��Դ�ķ���·��
		5��WebMvcAutoConfiguration.ResourceHandlerRegistrationCustomizer	// ��������Ƕ��������Լ��ľ�̬��Դ����ע����
		6��WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter			// ��Ҳ�Ǽ̳���WebMvcConfigurerAdapter��mvc������չ
		7��FaviconConfiguration			// (spring.mvc.favicon)ϲ����ͼ������
		8��WebMvcConfigurer				// ���൱��һ��@Configuration���Ҽ̳�WebMvcConfigurerAdapter���࣬��WebMvcConfigurerComposite����


	1������
		1��Interceptor���������е�������ô������ô�ų���̬��Դ���أ������webappĿ¼�µ����ǲ��ù��˵ģ������springboot���Զ�������ӳ�����
		2��thymeleaf����ҳ��Ԫ�س�ȡ�����룬��������
		3��resful�������󣬸ı�����ķ�����
			1������ʹ��SpringMVC������HiddenHttpMethodFilter;��SpringBoot�Զ����úõģ�
			2��ҳ�洴��һ��post��
			3������һ��input�name="_method";ֵ��������ָ��������ʽ
		4�����������
		5��springbootĬ��ʹ�õ���Ƕ��ʽ��tomcat����Ƕ��ʽ����Servlet����
		6��ʹ�����õ�Servlet����


	1������
		1����ðѾ�̬��Դ����վ��ҳ����classpath:static\�ȵ�




1��springboot��ʲô
	1��������spring����ջ��������ʹ���Զ��������ã�ʹ�û���spring��Ӧ�ÿ����������Ӽ�
	2���Զ�������ԭ���п�ȥ����

2����������
	1��pom.xml�̳�spring�\boot�\starter�\parent�̳�spring�\boot�\dependencies����������ֱ��ʹ��springboot��������������ʹ��spring�\boot�\starter�\web������ʹ��springmvc
	2����дsrc/main/resources/application.yml(bootstrap.yml���ȼ����)���ļ�
	3��������Ŀ���
		@SpringBootApplication
		public class SpringBoot04WebRestfulcrudApplication {
			public static void main(String[] args) {
				SpringApplication.run(SpringBoot04WebRestfulcrudApplication.class, args);
			}
		}

3���������ļ�
	1��Ĭ�ϵ�springboot�������ļ�
		1��λ�÷��ڣ�src/main/resources
		2���ļ����ƣ�application.yml(bootstrap.yml���ȼ����)��application.properties
	2��yml(yaml)�﷨
		1��k: v��ע��Ҫ�пո�
		2��һ�㲻�����ţ�˫���Ų�ת�壬�����Ż�ת�塣���ӣ�'z\ns'=z\ns
		3��map
			1������һ
				friends:
				  name: zs
				  age: 25
			2��������
				friends: {name: zs,age: 25}
		4��list,set
			1������һ
				pets:
				 - cat
				 - dog
			2��������
				pets: [cat,dog]
	3�������ļ���ע��@ConfigurationProperties��@Value��һ��ʹ��ǰ�ߣ����ע��ıȽϼ���ʹ�ú��ߣ�ע���ֵ����ʹ��@Email��У��
		1����yml�ļ���д
			 person:
			   name: zs
			   age: 25
		2��javaBean������������������ṩ@ConfigurationProperties(prefix = "person")���Զ�ע�빦�ܣ�
			 @Component
			 @ConfigurationProperties(prefix = "person")
			 public class Person {
			   private String name;
			   private int age;
			 }
		3������ڱ�дyml�ļ���Ҫ��ʾ���Ե�������
			 <dependency>
			   <groupId>org.springframework.boot</groupId>
			   <artifactId>spring�\boot�\configuration�\processor</artifactId>
			   <optional>true</optional>
			 </dependency>
	4��@PropertySource @ImportResource @Bean
		1����javabeanע��@PropertySource(value = {"classpath:person.yml"})��ͨ��ָ���������ļ���ע��ֵ
		2����javabeanע��@ImportResource(locations = {"classpath:beans.xml"})������ָ����spring�����ļ�
		3��spingboot�Ƽ�ʹ����ĳ����ע��@Configurationʹ��@Bean��ע��bean��������ǰ��xml��
	5�������ļ���ռλ��
		1���������${random.value}��${random.int}��${random.long}
		2��Ĭ��ֵ��${person.hello:hello}
	6��profile
		1���������ļ�
			 �������ļ��������� application-{profile}.ymlĬ��ʹ��application.yml��ָ�������ĸ�{profile}(ָ�������ˣ���ôĬ�ϵ������ļ���Ч����Ч)
		2�����ĵ���
			 server:
			   port: 8081
			 spring:
			   profiles:
				 active: prod #ָ�������ĸ�����
			 �\�\�\
			 spring:
			   profiles: dev
			 ...
			 �\�\�\
			 spring:
			   profiles: prod #ָ�������ĸ�����
			 ...
	7������ָ��profile
		1�����������ļ���ָ�� spring.profiles.active=dev
		2�������У�java -jar pro-v001.jar --spring.profiles.active=dev��
		3�������������-Dspring.profiles.active=dev
	8���������ļ���λ�ã�ͨ��spring.config.location���ı�Ĭ�ϵ������ļ�λ�ã����ȼ��ɸߵ��ף��߸��ͣ����໥�����ã�
		�Cfile:./config/
		�Cfile:./
		�Cclasspath:/config/
		�Cclasspath:/

4����־����
	1�����е���־��ͳһ��slf4j��
		1���ų�������־������棻
		2�����м�����滻ԭ�е���־���(�������slf4)��
		3�����ǵ���slf4j����ʵ��(ѡ��logback����log4j��������)
	2��slf4j��ʹ��
		public void contextLoads() {
			Logger logger = LoggerFactory.getLogger(getClass());
			// �ɵ͵��� trace<debug<info<warn<error��Ĭ��info����ֻ�����>=info�������־
			logger.trace("����trace��־...");
			logger.debug("����debug��־...");
			logger.info("����info��־...");
			logger.warn("����warn��־...");
			logger.error("����error��־...");
		}

	3����־�����ʽ��%d{yyyy�\MM�\dd HH:mm:ss.SSS} [%thread] %�\5level %logger{50} �\ %msg%n
		1��%d��         ��ʾ����ʱ�䣬
		2��%thread��    ��ʾ�߳�����
		3��%�\5level��   ���������ʾ5���ַ����
		4��%logger{50}����ʾlogger�����50���ַ��������վ��ָ
		5��%msg��       ��־��Ϣ��
		6��%n��         �ǻ��з�
	4����־����
		1��logging.level=trace��			��־����
		2��logging.file=C:/springboot.log�� ʹ��ȫ·�����Ͳ���Ҫlogging.path������
		3��logging.pattern.console��        �ڿ���̨�������־�ĸ�ʽ
		4��logging.pattern.file��           ָ���ļ�����־����ĸ�ʽ

5��web��Ŀ�Ŀ���
	1����̬��Դ����
		1��/webjars/XX��ȥpath=classpath:/META-INF/resources/webjars/�ң����磺localhost/webjars/a/a.js=path/a/a.js
		2��"/**"����ȥ��path=�������֣��ң����磺localhost/abc/a.js =path/abc/a.js
			"classpath:/META�\INF/resources/",
			"classpath:/resources/",
			"classpath:/static/",
			"classpath:/public/"
			"/"����ǰ��Ŀ�ĸ�·��
		3����ӭҳ=path/index.htmlҳ�棬���磺localhost=path/index.html
		4����վͼ��=path/favicon.ico
	2��thymeleaf��<html lang="en" xmlns:th="http://www.thymeleaf.org">
		1�����룺
			<!-- 4������thymeleaf�������൱��jsp�Ĺ���-->
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-thymeleaf</artifactId>
			</dependency>
			<properties>
				<!-- thymeleaf3����layout2 -->
				<!-- thymeleaf2����layout1 -->
				<thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
				<thymeleaf-layout-dialect.version>2.2.2</thymeleaf-layout-dialect.version>
			</properties>
		2��ʹ�ã�ֻҪ���ǰ�HTMLҳ�����classpath:/templates/��thymeleaf�����Զ���Ⱦ��
			@ConfigurationProperties(prefix = "spring.thymeleaf")
			public class ThymeleafProperties {
				private static final Charset DEFAULT_ENCODING = Charset.forName("UTF�\8");
				private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");
				public static final String DEFAULT_PREFIX = "classpath:/templates/";
				public static final String DEFAULT_SUFFIX = ".html";
			}
		3���﷨(�ο�ͼƬ)
			1�����ֱ��ʽ
				1��${}��ȡ����ֵOGNL�����磺${session.foo}\${person.name}
				2��*{}ѡ����ʽ��${}�ڹ�������һ�����������${}���÷�����
					<div th:object="${session.user}">
						<p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
						<p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
						<p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
					</div>
				3��#{}��ȡ���ʻ�����
				4��@{}����URL�����磺@{/order/process(execId=${execId},execType='FAST')}
				5��~{}Ƭ�����ñ��ʽ�����磺<div th:insert="~{commons :: main}"></div>
			2��spring.thymeleaf.cache=false���û���(�����������ɿ���)
			3��html����������
				1����ȡ����Ƭ��
					<div th:fragment="name">abc</div>
				2�����빫��Ƭ��
					1��~{templatename::fragmentname}:ģ����::Ƭ����
					2��~{templatename::selector}��ģ����::ѡ����
				3������
					<div th:insert="footer :: name"></div>  ==> <div><footer>abc</footer></div>
					<div th:replace="footer :: name"></div> ==> <footer>abc</footer>
					<div th:include="footer :: name"></div> ==> <div>abc</div>
				4�����뻹���Դ�������<div th:replace="commons/bar::#sidebar(activeUri='emps')"></div>��Ȼ���������ʹ��${}ȡ��
	3������
		1���Զ�ע��
			1����ͼ������
				1��ViewResolver(��ͼ������)
				2��ContentNegotiatingViewResolver(������е���ͼ��������)
				3����ζ��ƣ����ǿ����Լ������������һ����ͼ���������Զ��Ľ�����Ͻ�����
			2��webjars\��ӭҳ\��վͼ��\������̬��Դ
			3��Converter,GenericConverter,Formatter��ת�����͸�ʽ�����Լ���ӵĸ�ʽ����ת������ֻ��Ҫ���������м���
			4��HttpMessageConverters����ת��Http�������Ӧ�ģ�User---Json
			5��MessageCodesResolver �������������ɹ���
			6��ConfigurableWebBindingInitializer������ʼ����
		2����չMVC
			1��дһ��������(@Configuration)WebMvcConfigurerAdapter���ͣ�ͨ��setConfigurers(List<WebMvcConfigurer> configurers)����벢�����ã�
			ע�⣺���ܱ�ע@EnableWebMvc��ȫ����mvc;
		3�����ʻ�
			1����д��classpath:i18n/(login.pro/login_zh_CN.pro/login_en_US.pro)
			2��thymeleaf��ʹ�ã�#{login.btn}
			3�����������Ĭ��ʹ������ͷ�������򣬵��ҿ����Զ���LocaleResolver����ͨ������������
		4���Զ�����������HandlerInterceptor���������м���
		5���ݴ���(�п���ȥ����)
		6��Ƕ��ʽ��web����(��tomcatΪ����)
				1��ֱ�����������ļ�����
					server.port=8081					// Ӧ�ü����Ķ˿�
					server.context�\path=/crud			// Ӧ�õķ���ǰ׺
					server.tomcat.uri�\encoding=UTF�\8	// ���get��������
					server.xxx							// ͨ�õ�Servlet��������
					server.tomcat.xxx					// Tomcat������
				2��Ҳ���Ա�дһ��EmbeddedServletContainerCustomizer��Ƕ��ʽ��Servlet�����Ķ�����(ʹ���������þͲ���Ҫ���������)
					@Bean //һ��Ҫ��������������뵽������
					public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(){
						return new EmbeddedServletContainerCustomizer() {
							//����Ƕ��ʽ��Servlet������صĹ���
							@Override
							public void customize(ConfigurableEmbeddedServletContainer container) {
								container.setPort(8083);
							}
						};
					}
				3��û��web.xml�ˣ���ô���������Servlet��Filter��Listener��������
					1��Servlet
						@Bean
						public ServletRegistrationBean myServlet(){
							ServletRegistrationBean registrationBean = new ServletRegistrationBean(newMyServlet(),"/myServlet");
							return registrationBean;
						}
					2��Filter
						@Bean
						public FilterRegistrationBean myFilter(){
							FilterRegistrationBean registrationBean = new FilterRegistrationBean();
							registrationBean.setFilter(new MyFilter());
							registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
							return registrationBean;
						}
					3��Listener
						@Bean
						public ServletListenerRegistrationBean myListener(){
							ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
							return registrationBean;
						}





7����������ԭ��(�п���ȥ��)
8���Զ���starter(�п���ȥ��)

