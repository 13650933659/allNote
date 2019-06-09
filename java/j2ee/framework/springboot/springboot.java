


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

	3�����õ�springboot���
		1���Ȳ�����  // ��ο�https://www.cnblogs.com/jiangbei/p/8439394.html
			1����pom��ֱ����������
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-devtools</artifactId>
					<optional>true</optional>
				</dependency>
			2����File�� -> ��Settings�� -> ��Build,Execution,Deplyment�� -> ��Compiler����ѡ�д� ��Build project automatically�� ��
	����	3����ϼ�����Shift+Ctrl+Alt+/�� ��ѡ�� ��Registry�� ��ѡ�д� ��compiler.automake.allow.when.app.running�� ��


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
	2��springboot�������
		1��Ӧ�þ���˵starter�ˣ�-web(��������ͼ������������ʱ�䣬jsonת����),-thymeleaf,-data-jpa,-jdbc
	3��springboot���Զ�������ԭ��
		1��springboot������ȥ����������@SpringBootApplication��@EnableAutoConfiguration��
		����ʹ��classloader������classpath:meta-inf/spring.factories�����涨�ƺ��˵��Զ���������
		���͵���WebmvcAutoConfiguration

2����������
	1��pom.xml�̳�spring�\boot�\starter�\parent�̳�spring�\boot�\dependencies����������ֱ��ʹ��springboot��������������ʹ��spring�\boot�\starter�\web(springmvc)��������������� spring�\boot�\starter
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
	1����־����+ʵ��
		1����־�����У�JCL(Jakarta Commons Logging),SLF4j,Jboss-logging	
		2����־ʵ���У�JUL(java.util.Logging),Log4j->Log4j2->Logbak
		3���ܽ᣺����ѡslf4j��ʵ��ѡlog4j2����logbak����Ϊjcl�����ˣ�����slf4j��log4j2��logbak��ͬһ����д��
	2����־��ͳһ�� slf4j+logbak ���� slf4j+log4j2 ����
		1���ų�������־������棬����spring��jcl���棬���springboot�Ѿ��������Ƴ��ˣ������������Ҫ�Լ�ȥ�ų���
			<dependency>
				<groupId>org.springframework</groupId>
				<artifactId>spring�\core</artifactId>
				<exclusions>
					<exclusion>
						<groupId>commons�\logging</groupId>
						<artifactId>commons�\logging</artifactId>
					</exclusion>
				</exclusions>
			</dependency>
		2��springboot�Զ������������� jul-to-slf4j ��log4j-over-slf4j��jcl-over-slf4j�ȵ����������͵�컻�հ�(���ȫ������slf4j)
		3������������ʵ����
			1�������ֱ��ʹ��logbak��Ͳ��������ˣ�springbootĬ�Ͼ���ʹ��logbak
			2��ѡ��log4j2��Ҫʹ����������
				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-web</artifactId>
					<exclusions>
						<exclusion>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-starter-logging</artifactId>
						</exclusion>
					</exclusions>
				</dependency>

				<dependency>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-starter-log4j2</artifactId>
				</dependency>
			3��ѡ��log4j�����û���壬log4j���ã�������Ҫ��������������Բ���
	3��ʹ��
		1������ʹ��
			public void contextLoads() {
				Logger logger = LoggerFactory.getLogger(getClass());
				// �ɵ͵��� trace<debug<info<warn<error��Ĭ��info����ֻ�����>=info�������־
				logger.trace("����trace��־...");		// �����߿�
				logger.debug("����debug��־...");		// �����߿�
				logger.info("����info��־...");			// ���ڿ������û������һ�㿪���ߺ��û��ķֽ���
				logger.warn("����warn��־...");			// ���ڿ������û�
				logger.error("����error��־...");		// ���ڿ������û�
			}
		2������˵��
			1�������ļ�����Ĭ����logback-spring.xml or logback.xml ���Ƽ�ʹ�ô�spring�ģ���Ϊ����ʹ��spring�ĸ߼����ܣ�����ʹ��springProfile��ǩ�������Ǹ�����������
				�����ʹ��log4j2ʵ�֣�Ĭ�������ļ�Ϊ��log4j2-spring.xml����log4j2.xmlҲ�����Լ�ָ��logging.config=classpath:log4j2-dev.xml��Ҳ��log4j2��logback�������Ǽ��ݵģ�
			2����־����
				1��logging.level=trace��			��־����
				2��logging.file=C:/springboot.log�� ʹ��ȫ·�����Ͳ���Ҫlogging.path������
				3��logging.pattern.console��        �ڿ���̨�������־�ĸ�ʽ
					1����־�����ʽ��%d{yyyy�\MM�\dd HH:mm:ss.SSS} [%thread] %�\5level %logger{50} �\ %msg%n
						1��%d��         ��ʾ����ʱ�䣬
						2��%thread��    ��ʾ�߳�����
						3��%�\5level��   ���������ʾ5���ַ����
						4��%logger{50}����ʾlogger�����50���ַ��������վ��ָ
						5��%msg��       ��־��Ϣ��
						6��%n��         �ǻ��з�
				4��logging.pattern.file��           ָ���ļ�����־����ĸ�ʽ

5��web��Ŀ�Ŀ���
	1����̬��Դ����
		1��/webjars/XX��ȥpath=classpath:/META-INF/resources/webjars/�ң����磺localhost/webjars/a/a.js=path/a/a.js
		2��"/**"����ȥ��path=�������֣��ң����磺localhost/abc/a.js =path/abc/a.js
			"classpath:/META�\INF/resources/",
			"classpath:/static/",
			"classpath:/resources/",
			"classpath:/public/"
			"/"����ǰ��Ŀ�ĸ�·��
		3����ӭҳ=path/index.htmlҳ�棬���磺localhost=path(2)/index.html
		4����վͼ��=path(2)/favicon.ico
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
		6��Ƕ��ʽ��Servlet����
			1��Ƕ��ʽ��web����(��tomcatΪ����)
					1��ֱ�����������ļ�����(server��Ӧ��ServerProperties)
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
					4��SpringBoot�������Զ�SpringMVC��ʱ���Զ���ע��SpringMVC��ǰ�˿�������DispatcherServlet��DispatcherServletAutoConfiguration�У�
						@Bean(name = DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME)
						@ConditionalOnBean(value = DispatcherServlet.class, name =
						DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
						public ServletRegistrationBean dispatcherServletRegistration(DispatcherServlet dispatcherServlet) {
							ServletRegistrationBean registration = new ServletRegistrationBean(
							dispatcherServlet, this.serverProperties.getServletMapping());
							// Ĭ�����أ� / �������󣻰���̬��Դ�����ǲ�����jsp���� /*������jsp
							// ����ͨ��server.servletPath���޸�SpringMVCǰ�˿�����Ĭ�����ص�����·��
							registration.setName(DEFAULT_DISPATCHER_SERVLET_BEAN_NAME);
							registration.setLoadOnStartup(this.webMvcProperties.getServlet().getLoadOnStartup());
							if (this.multipartConfig != null) {
								registration.setMultipartConfig(this.multipartConfig);
							}
							return registration;
						}
			2��������Ƕ��ʽweb����
				1��jetty(�Ƚ��ʺϳ����ӵ�Ӧ�ã���������ϵͳ)
					1��pom�ļ�������
						<dependency>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-starter-web</artifactId>
							<exclusions>
								<exclusion>
									<groupId>org.springframework.boot</groupId>
									<artifactId>spring-boot-starter-tomcat</artifactId>
								</exclusion>
							</exclusions>
						</dependency>
						<dependency>
							<groupId>org.springframework.boot</groupId>
							<artifactId>spring-boot-starter-jetty</artifactId>
						</dependency>
					2������
						1����server.tomcat.xxx��Ϊserver.jetty.xxx���ã�������tomcatһ������
				2��Undertow(���ǲ�֧��jsp���Ǹ����ܣ���������)
			3��Ƕ��ʽ�������Զ�����ԭ��
				1��springboot���ݵ���������������������ӦEmbeddedServletContainerFactory��TomcatEmbeddedServletContainerFactory��
				2��������ĳ�����Ҫ�����ͻᾪ�����ô�����EmbeddedServletContainerCustomizerBeanPostProcessor��ֻҪ��Ƕ��ʽ�������������ͻṤ��
				3�����ô��������������л�ȡ���е�EmbeddedServletContainerCustomizer�����ö������Ķ��Ʒ���
			4��Ƕ��ʽ����������ԭ��
				1��springbootӦ����������run()����
				2��refreshContext(centext)	// ˢ��ioc����������ioc����������ÿһ��������������webӦ�ô���AnnotationConfigEmbeddedWebApplicationContext������AnnotationConfigApplicationContext
				3��refresh(centext)	// ˢ�¸ղŴ����õ�ioc��������ʱ��ȥ��springע����Դ�����
				4��onRefresh()	// ���Ǹ���Ҫ�ķ�������webioc������д��
				5��createEmbeddedServletContainer();	// webioc��������Ƕ��ʽ��servlet����
				6��EmbeddedServletContainerFactory containerFactory = getEmbeddedServletContainerFactory();
					��ioc�����л�ȡEmbeddedServletContainerFactory �����TomcatEmbeddedServletContainerFactory����
					���󣬺��ô�����һ����������󣬾ͻ�ȡ���еĶ��������ȶ���Servlet������������ã�// ��ȡǶ��ʽ��Servlet��������
				7��this.embeddedServletContainer = containerFactory.getEmbeddedServletContainer(getSelfInitializer());
				8��Ƕ��ʽ��Servlet������������������servlet����
				9��������Ƕ��ʽ��servlet��������ʼ��iocʣ�µ�����
			5��Ƕ��ʽ��servlet��������ȱ��
				1���ŵ�
					1��Ӧ�ô�ɿ�ִ�е�jar�����ɣ��򵥱�Я������Ҫ���ⰲװservlet����
				2��ȱ��
					1��Ĭ�ϲ�֧��jsp
					2���Ż����ƱȽϸ���(ʹ�ö�����ServletProperties���Զ���EmbeddedServletContainerCustomizer���Լ���дǶ��ʽServlet�����Ĺ���EmbeddedServletContainetFactory)
		7�����õ�Servlet���������氲װtomcat��Ӧ�ô��war��
			1������
				1�����봴��һ��war��Ŀ
				2����Ƕ��ʽ��tomcatָ��Ϊprovided
					<dependency>
						<groupId>org.springframework.boot</groupId>
						<artifactId>spring�\boot�\starter�\tomcat</artifactId>
						<scope>provided</scope>
					</dependency>
				3����дһ��SpringootServletInitializer�����࣬���ҵ���configure����
					public class ServletInitializer extends SpringBootServletInitializer {
						@Override
						protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
							//����SpringBootӦ�õ�������
							return application.sources(SpringBoot04WebJspApplication.class);
						}
					}
				4�������������ȿ�(ideaǶ�����õ�tomcat���òο�idea�ıʼ�)
			2��ԭ��
				1��jar����ִ��springboot�����main����������ioc����������Ƕ��ʽservlet����
				2��war��������������������������springbootӦ�á�SpringBootServletInitializer��������ioc����
					1������servlet3.0��8.2.4Shared libraries / runtimes pluggability����
						1������������(webӦ������)�ᴴ����ǰwebӦ�������ÿһ��jar�������ServletContainerInitializerʵ��
						2��ServletContainerInitializer��ʵ�ַ���jar����META-INF/services�ļ����µ�javax.servlet.ServletContainerInitializer�ļ���������ServletContainerInitializer��ʵ�ֵ�ȫ����
						3��������ʹ��@HandlesTypes����Ӧ��������ʱ��������Ǹ���Ȥ����
					2������
						1������Tomcat
						2��\METAINF\services\javax.servlet.ServletContainerInitializer�ļ���org.springframework.web.SpringServletContainerInitializer
						3�� SpringServletContainerInitializer �� @HandlesTypes(WebApplicationInitializer.class)��ע������������͵��඼���뵽onStartup������Set>��Ϊ��Щ WebApplicationInitializer ���͵��ഴ��ʵ����
						4��ÿһ�� WebApplicationInitializer �������Լ��� onStartup �������൱�����ǵ� SpringBootServletInitializer ����ᱻ�������󣬲�ִ�� onStartup ����
						6�� SpringBootServletInitializer ʵ��ִ�� onStartup ��ʱ��� createRootApplicationContext ��������Spring��Ӧ�þ��������Ҵ���IOC����
						7������Servlet������������SpringBootӦ��
			���ῴ�����õ�tomcatǶ��idea���Ǹ�tomcatApi�Ƿ���ͻ
7����������ԭ��(�п���ȥ��)
8���Զ���starter(�п���ȥ��)







































