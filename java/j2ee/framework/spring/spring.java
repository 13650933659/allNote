










1、以前学的
	1、spring是什么
		  是Java轻量级的开源框架，他是分层的mvc，他使得我们的应用开发起来更加容易，使得我们有更多的时间专注于自己的业务

	2、获取spring容器上下文
		1、得到ApplicationContext/AbstractApplicationContext(可关闭)，请参考API还有一些构造方法，比如可以加载多个beans.xml
		   1、ac=new ClassPathXmlApplicationContext("beans.xml");
		   2、ac=FileSystemXmlApplicationContext("G:\MyEclipse_code\J2EE-spring\myssh\src\beans.xml");
		   3、ac=new XmlWebApplicationContext("beans.xml");
		   4、 AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
		2、得到BeanFactory，bf是ac的父接口《懒加载》
		   1、BeanFactory bf=new XmlBeanFactory("beans.xml");

	3、IOC(Inversion Of Control)或者说DI(Dependency Injection)
	4、AOP(Aspect Orient Programming)，Spring该我们提供了多切面比如声明式的事物管理
	   1、底层是动态代理，这里所说的切面是面向一大堆的对象的，当这些对象在执行指定的方法时，这个切面就会切入做一些自定义的业务（比如：日志管理、性能测试、事务管理）

1、spring注解驱动开发
	1、配置类(相当于beans.xml)
		@Configuration  // 告诉Spring这是一个配置类
		public class MainConfig {
			// 给容器中注册一个Bean;类型为返回值的类型，id默认是用方法名作为id，但是你也可以指定value="person"
			@Bean("person")
			public Person person01(){
				return new Person("lisi", 20);
			}
		}
	2、组件扫描
		1、使用 @ComponentScan
				@ComponentScan(value="com.atguigu",includeFilters = {
						@Filter(type=FilterType.ANNOTATION,classes={Controller.class}),
						@Filter(type=FilterType.ASSIGNABLE_TYPE,classes={BookService.class}),
						@Filter(type=FilterType.CUSTOM,classes={MyTypeFilter.class})
				}, useDefaultFilters = false)
				// value:指定要扫描的包
				// excludeFilters = Filter[] ：指定扫描的时候按照什么规则排除那些组件
				// includeFilters = Filter[] ：指定扫描的时候只需要包含哪些组件，要使用这个需要指定useDefaultFilters =false，取消使用全部的
				// FilterType.ANNOTATION：按照注解
				// FilterType.ASSIGNABLE_TYPE：按照给定的类型；
				// FilterType.ASPECTJ ：使用ASPECTJ表达式
				// FilterType.REGEX：使用正则指定
				// FilterType.CUSTOM：使用自定义规则，下面是自定义规则(即：classname有er的就会注入ioc容器)
					public class MyTypeFilter implements TypeFilter {
						/**
						 * metadataReader：读取到的当前正在扫描的类的信息
						 * metadataReaderFactory:可以获取到其他任何类信息的
						 */
						@Override
						public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
								throws IOException {
							// TODO Auto-generated method stub
							//获取当前类注解的信息
							AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
							//获取当前正在扫描的类的类信息
							ClassMetadata classMetadata = metadataReader.getClassMetadata();
							//获取当前类资源（类的路径）
							Resource resource = metadataReader.getResource();
							
							String className = classMetadata.getClassName();
							System.out.println("--->"+className);
							if(className.contains("er")){
								return true;
							}
							return false;
						}
					}
		2、使用 @ComponentScans 他的value可以指定多个@ComponentScan，但是java8也可以指定多个@ComponentScan
	3、bean的作用域， 使用 @Scope("prototype") 调整作用域
		1、prototype：多实例的，每次获取的时候才会调用方法创建对象；
		2、singleton：单实例的（默认值）：ioc容器启动会调用方法创建对象放到ioc容器中。以后每次获取就是直接从容器（map.get()）中拿，
		3、request：同一次请求创建一个实例(很少用)
		4、session：同一个session创建一个实例(很少用)
	4、bean的懒加载，这个是针对单实例来说的(使用 @Lazy)
	5、使用 Condition
		1、自定义Condition
			//判断是否linux系统
			public class LinuxCondition implements Condition {
				/**
				 * ConditionContext：判断条件能使用的上下文（环境）
				 * AnnotatedTypeMetadata：注释信息
				 */
				@Override
				public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
					// TODO是否linux系统
					//1、能获取到ioc使用的beanfactory
					ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
					//2、获取类加载器
					ClassLoader classLoader = context.getClassLoader();
					//3、获取当前环境信息
					Environment environment = context.getEnvironment();
					//4、获取到bean定义的注册类
					BeanDefinitionRegistry registry = context.getRegistry();
					
					String property = environment.getProperty("os.name");
					
					//可以判断容器中的bean注册情况，也可以给容器中注册bean
					boolean definition = registry.containsBeanDefinition("person");
					if(property.contains("linux")){
						return true;
					}
					return false;
				}
			}
		2、使用自定义的 Condition (只有符合LinuxCondition的条件此bean才会生效)
			@Conditional(LinuxCondition.class)
			@Bean("linus")
			public Person person02(){
				return new Person("linus", 48);
			}
	6、向ioc容器导入组件的方法
		1、包扫描+组件标注注解 @Controller/@Service/@Repository/@Component 	// 适合自己写的类
		2、 @Bean		// 适合导入的第三方包里面的类
		3、 @Import		// 快速给容器中导入组件，可以结合下面三种方法来使用
			1、 @Import({Color.class,Color.class})；容器中就会自动注册这个组件，id默认是全类名
			2、 @Import({MyImportSelector.class}) ImportSelector: 返回需要导入的组件的全类名数组；
				public class MyImportSelector implements ImportSelector {
					//返回值，就是到导入到容器中的组件全类名
					//AnnotationMetadata:当前标注@Import注解的类的所有注解信息
					@Override
					public String[] selectImports(AnnotationMetadata importingClassMetadata) {
						// TODO Auto-generated method stub
						//importingClassMetadata
						//方法不要返回null值
						return new String[]{"com.atguigu.bean.Blue","com.atguigu.bean.Yellow"};
					}
				}
			3、 @Import({MyImportBeanDefinitionRegistrar.class}) ImportBeanDefinitionRegistrar:手动注册bean到容器中
				public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
					/**
					 * AnnotationMetadata：当前类的注解信息
					 * BeanDefinitionRegistry:BeanDefinition注册类；把所有需要添加到容器中的bean；调用BeanDefinitionRegistry.registerBeanDefinition手工注册进来
					 */
					@Override
					public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
						boolean definition = registry.containsBeanDefinition("com.atguigu.bean.Red");
						boolean definition2 = registry.containsBeanDefinition("com.atguigu.bean.Blue");
						if(definition && definition2){
							//指定Bean定义信息；（Bean的类型，Bean。。。）
							RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
							//注册一个Bean，指定bean名
							registry.registerBeanDefinition("rainBow", beanDefinition);
						}
					}
				}
		4、使用Spring提供的 FactoryBean（工厂Bean）;
			1、代码
				public class ColorFactoryBean implements FactoryBean<Color> {
					//返回一个Color对象，这个对象会添加到容器中
					@Override
					public Color getObject() throws Exception {
						// TODO Auto-generated method stub
						System.out.println("ColorFactoryBean...getObject...");
						return new Color();
					}
					@Override
					public Class<?> getObjectType() {
						// TODO Auto-generated method stub
						return Color.class;
					}

					//是单例？
					//true：这个bean是单实例，在容器中保存一份
					//false：多实例，每次获取都会创建一个新的bean；
					@Override
					public boolean isSingleton() {
						return true;
					}
				}
			2、使用
				1、默认获取到的是工厂bean调用getObject创建的对象
				2、要获取工厂Bean本身，我们需要给id前面加一个& 比如 &colorFactoryBean

	7、bean的生命周期
		1、大致描述
			1、IOC容器启动之后，我们的bean实例化(非懒加载的单例bean)			// 构造方法执行
			2、设置	bean的属性													// 属性的set方法执行
			3、调用	BeanNameAware.setBeanName()方法
			4、调用	BeanFactoryAware.setBeanFactory()方法
			5、调用	ApplicationContextAware.setApplicationContext()方法			// 使用这些Aware我们就可以使用ApplicationContext、EmbeddedValueResolverAware(可以直接取出配置文件的属性)等等
			6、调用	BeanPostProcessor.postProcessBeforeInitialization()方法		// (bean初始化之前的)后置处理器，这是切面的方法(AOP)
			7、调用 InitializingBean.afterPropertiesSet()方法					// 这里我们也可以定制自己的init方法
			8、调用 BeanPostProcessor.postProcessAfterInitialization()方法		// (bean初始化之后的)后置处理器，这是切面的方法(AOP)
			9、Bean可以使用了
			10、当IOC容器关闭之后												// 要手动调用AnnotationConfigApplicationContext.close()才会触发下面的destory方法
			11、调用 DisposableBean.destory()方法								// 这里我们也定制自己的destory方法
		2、原理
			-> ioc启动，我们的ben执行构造方法
			-> populateBean(beanName, mbd, instanceWrapper);						// 调用set方法给bean进行属性赋值
			-> initializeBean：初始化bean；
				-> invokeAwareMethods()													// 处理Aware接口的方法回调
				-> applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);	// 遍历得到容器中所有的BeanPostProcessor.postProcessBeforeInitialization
				-> invokeInitMethods(beanName, wrappedBean, mbd);						// 完成初始化(这里会调用我们自定义初始化的方法)
				-> applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);	// 遍历得到容器中所有的BeanPostProcessor.postProcessAfterInitialization
			-> bean创建完成
		3、初始化和销毁方法
			1、配置：@Bean(initMethod="init",destroyMethod="detory")
			2、也可以实现InitializingBean.afterPropertiesSet()方法=init=@PostConstruct(JSR250)， DisposableBean.destory()=detory=@PreDestroy(JSR250)
			3、注意：要调用 AnnotationConfigApplicationContext.close(); 才会触发销毁方法，而且多实例的销毁方法无效，需要自己销毁bean
		4、后置处理器 BeanPostProcessor
			1、从上面可以看出后置处理器是一个大功臣
			2、Spring底层对 BeanPostProcessor 的使用还有如下；
				bean赋值，注入其他组件，@Autowired，生命周期注解功能，@Async,xxx BeanPostProcessor;

	8、 @Value 配合 @PropertySource("classpath:/a.properties")
		1、赋值方式
			1、基本数值
			2、可以写SpEL； #{}
			3、可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）
	9、自动注入(IOC 或者 DI)
		1、 @Autowired （原理是 AutowiredAnnotationBeanPostProcessor 后置处理器的自动注入，按照类型，没找到就报错，多个的话，就会根据属性的名称作为组件的id去找）	// 推荐使用
			1、可以使用 @Qualifier("bookDao")指定使用哪个
			2、可以使用 @Autowired(required=false);解决找不到报错的问题，但是注意使用时的null异常
			3、可以使用 @Primary 注解在优先级别较高的bean，找到个优先使用
			4、 @Autowired 可以注解在:构造器，参数，方法，属性；都是从容器中获取参数组件的值
				1、[表在set方法上]：spring容器创建当前对象，就会调用此set方法，传入bean
				2、[标在构造器上]：如果组件只有一个有参构造器，这个有参构造器的 @Autowired 可以省略，参数位置的组件还是可以自动从容器中获取
				3、[标注在定义bean的方法位置]：@Bean+方法参数；参数从容器中获取;默认不写 @Autowired 效果是一样的；都能自动装配
		2、Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]
			1、 @Resource 可以和 @Autowired 一样实现自动装配功能；默认是按照组件名称进行装配的，没有能支持 @Primary 功能没有支持 @Autowired（reqiured=false）;
			2、 @Inject 需要导入 javax.inject 的包，和 Autowired 的功能一样。没有 required=false 的功能；

	10、 @Profile(dev) 用次注解指定组件在dev环境的情况下生效，默认是default环境
	11、 AOP：指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的编程方式，底层是动态代理
		1、使用
			1、导入aop模块的依赖；Spring AOP：(spring-aspects)
			2、定义一个日志切面类（LogAspects）	// 以后有空去开发一个方法的性能勘测
				1、通知方法：
					前置通知(@Before)：			// logStart：在目标方法(div)运行之前运行
					后置通知(@After)：			// logEnd：在目标方法(div)运行结束之后运行（无论方法正常结束还是异常结束）
					返回通知(@AfterReturning)：	// logReturn：在目标方法(div)正常返回之后运行
					异常通知(@AfterThrowing)：	// logException：在目标方法(div)出现异常以后运行，后续异常还会继续抛出
					环绕通知(@Around)：			// 动态代理，手动推进目标方法运行（joinPoint.procced()）
				2、代码实现
					@Aspect		// 注解为切面类
					@Service
					public class LogAspects {

						//抽取公共的切入点表达式
						//1、本类引用            @Before("pointCut()")
						//2、其他的切面引用       @Before("全类名.pointCut()")
						@Pointcut("execution(public * com.atguigu.bean.MathCalculator.*(..))")
						public void pointCut(){};

						//@Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
						@Before("pointCut()")
						public void logStart(JoinPoint joinPoint){
							Object[] args = joinPoint.getArgs();
							System.out.println(""+joinPoint.getSignature().getName()+"运行。。。@Before:参数列表是：{"+Arrays.asList(args)+"}");
						}

						@After("com.atguigu.aop.LogAspects.pointCut()")
						public void logEnd(JoinPoint joinPoint){
							System.out.println(""+joinPoint.getSignature().getName()+"结束。。。@After");
						}

						//JoinPoint一定要出现在参数表的第一位
						@AfterReturning(value="pointCut()",returning="result")
						public void logReturn(JoinPoint joinPoint,Object result){
							System.out.println(""+joinPoint.getSignature().getName()+"正常返回。。。@AfterReturning:运行结果：{"+result+"}");
						}

						@AfterThrowing(value="pointCut()",throwing="exception")
						public void logException(JoinPoint joinPoint,Exception exception){
							System.out.println(""+joinPoint.getSignature().getName()+"异常。。。异常信息：{"+exception+"}");
						}

					}
			3、给配置类中加 @EnableAspectJAutoProxy 【开启基于注解的aop模式】
		2、原理【看给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么？】
			1、 @EnableAspectJAutoProxy是什么？
					@Import(AspectJAutoProxyRegistrar.class)：给容器中导入 AspectJAutoProxyRegistrar 组件
						他会向容器中注册 AnnotationAwareAspectJAutoProxyCreator 组件
			2、 AnnotationAwareAspectJAutoProxyCreator 的继承结构
				AnnotationAwareAspectJAutoProxyCreator
					->AspectJAwareAdvisorAutoProxyCreator
						->AbstractAdvisorAutoProxyCreator
							->AbstractAutoProxyCreator
								implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
									implements -> InstantiationAwareBeanPostProcessor
										implements -> BeanPostProcessor
  						关注后置处理器（在bean初始化完成前后做事情）、自动装配BeanFactory
							// 1、BeanFactoryAware 的的处理
							AbstractAutoProxyCreator.setBeanFactory()
								AbstractAdvisorAutoProxyCreator.setBeanFactory() -> initBeanFactory()
									AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
							// 2、后置处理的
							AbstractAutoProxyCreator.postProcessBeforeInitialization()	// 这个是 BeanPostProcessor 的
							AbstractAutoProxyCreator.postProcessBeforeInstantiation()	// 这个是 InstantiationAwareBeanPostProcessor 的
							AbstractAutoProxyCreator.postProcessAfterInstantiation()	// 这个是 InstantiationAwareBeanPostProcessor 的
			3、 AnnotationAwareAspectJAutoProxyCreator 的创建流程：
				1、传入配置类，创建ioc容器
				2、注册配置类，调用refresh() 刷新容器；
					1、 registerBeanPostProcessors(beanFactory);	// 注册bean的后置处理器来方便拦截bean的创建；
						1、先获取ioc容器已经定义了的需要创建对象的所有BeanPostProcessor
						2、给容器中加别的BeanPostProcessor
						3、优先注册实现了PriorityOrdered接口的BeanPostProcessor；
						4、再给容器中注册实现了Ordered接口的BeanPostProcessor；
						5、注册没实现优先级接口的BeanPostProcessor；
						6、注册BeanPostProcessor，实际上就是创建BeanPostProcessor对象，保存在容器中；
					2、创建 internalAutoProxyCreator = AnnotationAwareAspectJAutoProxyCreator 
						1、AbstractAutowireCapableBeanFactory.doCreateBean()	// 创建Bean的实例
						2、populateBean；给bean的各种属性赋值
						3、initializeBean：初始化bean；
							1、invokeAwareMethods()：							// 回调Aware.setXXX()
							2、applyBeanPostProcessorsBeforeInitialization()	// 回调用BeanPostProcessors.postProcessBeforeInitialization()
							3、invokeInitMethods()								// 执行自定义的初始化方法
							4、applyBeanPostProcessorsAfterInitialization()		// 回调用BeanPostProcessors.postProcessBeforeInitialization()
						4、AnnotationAwareAspectJAutoProxyCreator 创建成功；--> aspectJAdvisorsBuilder
						5、把 AnnotationAwareAspectJAutoProxyCreator 注册到 BeanFactory 中；beanFactory.addBeanPostProcessor(postProcessor);
					3、 finishBeanFactoryInitialization(beanFactory); 完成BeanFactory初始化工作；创建剩下的单实例bean
						1、遍历获取容器中所有的Bean，依次创建对象getBean(beanName);
							getBean()->doGetBean()->getSingleton()->
						2、doCreateBean() 创建bean
							AnnotationAwareAspectJAutoProxyCreator 在所有bean创建之前会有一个拦截， InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation()
							1、先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建；
								只要创建好的Bean都会被缓存起来
							2、createBean();创建bean；
								【 BeanPostProcessor 是在Bean对象创建完成，初始化前后调用的】
								【 InstantiationAwareBeanPostProcessor 是在创建Bean实例之前先尝试用后置处理器返回对象的】
								1、 resolveBeforeInstantiation(beanName, mbdToUse);解析BeforeInstantiation
									希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续
									1、后置处理器先尝试返回对象；
										bean = applyBeanPostProcessorsBeforeInstantiation()：
											拿到所有后置处理器，如果是 InstantiationAwareBeanPostProcessor;就执行postProcessBeforeInstantiation
										if (bean != null) {
											bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
										}
			  
								2、 doCreateBean(beanName, mbdToUse, args);	// 真正的去创建一个bean实例；和2.2流程一样；
			4、每一个bean创建之前(关心 MathCalculator 和 LogAspect 的创建)，调用 InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation()；
				1、判断当前bean是否在 advisedBeans 中（保存了所有需要增强bean）
				2、判断当前bean是否是基础类型的 Advice Pointcut Advisor AopInfrastructureBean 或者是否是切面 @Aspect
				3、是否需要跳过
					1、获取候选的增强器（切面里面的通知方法） List<Advisor> candidateAdvisors
						每一个封装的通知方法的增强器是 InstantiationModelAwarePointcutAdvisor
						判断每一个增强器是否是 AspectJPointcutAdvisor 类型的；返回true
					2、永远返回false
				4、创建bean的代理对象
					postProcessAfterInitialization
						return wrapIfNecessary(bean, beanName, cacheKey);	// 包装如果需要的情况下
							1、获取当前bean的所有增强器（通知方法）  Object[]  specificInterceptors
								1、找到候选的所有的增强器（找哪些通知方法是需要切入当前bean方法的）
								2、获取到能在bean使用的增强器。
								3、给增强器排序
							2、保存当前bean在advisedBeans中；
							3、如果当前bean需要增强，创建当前bean的代理对象；
								1、获取所有增强器（通知方法）
								2、保存到proxyFactory
								3、创建代理对象：Spring自动决定
									JdkDynamicAopProxy(config);			// 是接口或者是代理类，就创建jdk动态代理；
									ObjenesisCglibAopProxy(config);		// 是类就创建cglib的动态代理；
							4、给容器中返回当前组件使用cglib增强了的代理对象；
							5、以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程；
			5、目标方法执行	；cglib的代理对象，这个对象里面保存了详细信息（比如增强器，目标对象，xxx）；
				1、 CglibAopProxy.intercept();拦截目标方法的执行
				2、根据 ProxyFactory 对象获取将要执行的目标方法拦截器链；
					List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
						1、List<Object> interceptorList保存所有拦截器 5
							一个默认的ExposeInvocationInterceptor 和 4个增强器；
						2、遍历所有的增强器，将其转为 Interceptor；
							registry.getInterceptors(advisor);
						3、将增强器转为List<MethodInterceptor>；
							如果是MethodInterceptor，直接加入到集合中
							如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor；
							转换完成返回MethodInterceptor数组；
				3、如果没有拦截器链，直接执行目标方法;
					拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）
				4、如果有拦截器链，把需要执行的目标对象，目标方法，
					拦截器链等信息传入创建一个 CglibMethodInvocation 对象，并调用 Object retVal =  mi.proceed();
				5、拦截器链的触发过程;
					1、如果没有拦截器执行执行目标方法，或者拦截器的索引和拦截器数组-1大小一样（指定到了最后一个拦截器）执行目标方法；
					2、链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行；
						拦截器链的机制，保证通知方法与目标方法的执行顺序；

	12、声明式事务
		1、使用
			1、导入相关依赖：数据源(c3p0)、数据库驱动、Spring-jdbc模块(事务管理器)
			2、配置c3p0数据源、JdbcTemplate(Spring提供的简化数据库操作的工具)、配置事务管理器来控制事务
				// c3p0 数据源
				@Bean
				public DataSource dataSource() throws Exception{
					ComboPooledDataSource dataSource = new ComboPooledDataSource();
					dataSource.setUser("root");
					dataSource.setPassword("123456");
					dataSource.setDriverClass("com.mysql.jdbc.Driver");
					dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
					return dataSource;
				}
				
				// JdbcTemplate
				@Bean
				public JdbcTemplate jdbcTemplate() throws Exception{
					//Spring对@Configuration类会特殊处理；给容器中加组件的方法，多次调用都只是从容器中找组件
					JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
					return jdbcTemplate;
				}
				
				//注册事务管理器在容器中
				@Bean
				public PlatformTransactionManager transactionManager() throws Exception{
					return new DataSourceTransactionManager(dataSource());
				}
			3、 @EnableTransactionManagement 开启基于注解的事务管理功能
			4、给需要事务的方法上标注 @Transactional // 但是事务的传播属性的配置有空再去看，还有使用aop去做的事务声明，这个有空也去看一下
			5、 如果使用了springboot2.0.0开始， @EnableTransactionManagement 和 transactionManager 都不用指定了，但是为了规范这两个还是加上吧， 直接就可以使用 @Transactional 注解使用事务了
			6、如果是多数据源的事务管理需要使用到 https://blog.csdn.net/qq_36138324/article/details/81612890，由于使用jta+atomikos解决分布式事务，所以此处不必再指定事务
		2、原理
			1、 @EnableTransactionManagement 利用 TransactionManagementConfigurationSelector 给容器中会导入组件导入两个组件
				1、 AutoProxyRegistrar
					给容器中注册一个 InfrastructureAdvisorAutoProxyCreator 组件；
					利用后置处理器机制在对象创建以后，包装对象，返回一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用；
				2、 ProxyTransactionManagementConfiguration 做了什么？
					1、给容器中注册事务增强器 BeanFactoryTransactionAttributeSourceAdvisor=transactionAdvisor 需要下面两个组件
						1、事务增强器要用事务注解的信息 AnnotationTransactionAttributeSource 解析事务注解
						2、事务拦截器 TransactionInterceptor 保存了事务属性信息，事务管理器；他是一个 MethodInterceptor；在目标方法执行的时候；
  							1、执行拦截器链；
  							2、事务拦截器：
								1、先获取事务相关的属性
								2、再获取 PlatformTransactionManager 如果事先没有添加指定任何 transactionmanger 最终会从容器中按照类型获取一个 PlatformTransactionManager
								3、执行目标方法
									如果异常，获取到事务管理器，利用事务管理回滚操作；
									如果正常，利用事务管理器，提交事务

	13、扩展原理
		1、 BeanFactoryPostProcessor 所有的bean定义已经保存加载到 BeanFactory 但是bean的实例还未创建
			1、ioc容器创建对象
			2、refresh() -> invokeBeanFactoryPostProcessors(beanFactory);
				1、直接在BeanFactory中找到所有类型是 BeanFactoryPostProcessor 的组件，并执行他们的 postProcessBeanFactory() 方法
				2、在初始化创建其他组件前面执行
		2、 BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor 的 postProcessBeanDefinitionRegistry()方法
		在所有bean定义信息将要被加载，bean实例还未创建的；优先于BeanFactoryPostProcessor执行
			1、ioc创建对象
			2、refresh()->invokeBeanFactoryPostProcessors(beanFactory);
			3、从容器中获取到所有的 BeanDefinitionRegistryPostProcessor 组件。
	  			1、依次触发所有的 postProcessBeanDefinitionRegistry()方法
	  			2、再来触发 postProcessBeanFactory()方法BeanFactoryPostProcessor；
		3、 ApplicationListener 监听容器中发布的事件(ApplicationEvent)。事件驱动模型开发；
			1、写事件监听器
				1、写一个监听器（ ApplicationListener 实现类）来监听某个事件（ ApplicationEvent 及其子类）
					@Component
					public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {
						//当容器中发布此事件以后，方法触发
						@Override
						public void onApplicationEvent(ApplicationEvent event) {
							System.out.println("收到事件："+event);
						}
					} 			
	 
				2、 @EventListener;原理：
					1、使用 EventListenerMethodProcessor 处理器来解析 @EventListener 的方法
					2、把监听器加入到容器；
					3、只要容器中有相关事件的发布，我们就能监听到这个事件；
			2、事件发布
				1、 自己发布一个事件 ublishEvent(new ContextRefreshedEvent(this));
					1、获取事件的多播器（派发器） getApplicationEventMulticaster()
					2、 multicastEvent 派发事件：
					3、获取到所有的 ApplicationListener 
						for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
							1、如果有Executor，可以支持使用Executor进行异步派发；
								Executor executor = getTaskExecutor();
							2、否则，同步的方式直接执行listener方法 invokeListener(listener, event); 拿到listener回调onApplicationEvent方法；
						}
			   2、内置的 ContextRefreshedEvent 事件发布原理
					1、ioc容器创建
					2、refresh() -> finishRefresh(); 容器刷新完成会发布ContextRefreshedEvent事件
			3、事件多播器（派发器）的初始化
				1、容器创建对象：refresh();
				2、initApplicationEventMulticaster();初始化ApplicationEventMulticaster；
					1、先去容器中找有没有id=applicationEventMulticaster 的组件；
					2、如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
			4、监听器的初始化
				1、容器创建对象：refresh();
				2、registerListeners();
					1、从容器中拿到所有的监听器，把他们注册到 applicationEventMulticaster 中；
						String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
						getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
			5、 SmartInitializingSingleton 原理：-> afterSingletonsInstantiated(); 执行时机刚好等于 ContextRefreshedEvent 事件
				1、ioc容器创建对象并refresh()；
				2、 finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean；
					1、先创建所有的单实例bean；getBean();
					2、获取所有创建好的单实例bean，判断是否是 SmartInitializingSingleton 类型的；如果是就调用afterSingletonsInstantiated();
	14、Spring容器的 refresh()【创建刷新】;
		1、 prepareRefresh()刷新前的预处理;
			1）、 initPropertySources()初始化一些属性设置;子类自定义个性化的属性设置方法；
			2）、 getEnvironment().validateRequiredProperties();检验属性的合法等
			3）、 earlyApplicationEvents= new LinkedHashSet<ApplicationEvent>();保存容器中的一些早期的事件；
		2、 obtainFreshBeanFactory();获取BeanFactory；
			1）、 refreshBeanFactory();刷新【创建】 BeanFactory
					创建了一个this.beanFactory = new DefaultListableBeanFactory();
					设置id；
			2）、 getBeanFactory();返回刚才GenericApplicationContext创建的BeanFactory对象；
			3）、将创建的BeanFactory【DefaultListableBeanFactory】返回；
		3、 prepareBeanFactory(beanFactory);BeanFactory的预准备工作（BeanFactory进行一些设置）；
			1）、 设置BeanFactory的类加载器、支持表达式解析器...
			2）、 添加部分 BeanPostProcessor  ApplicationContextAwareProcessor 
			3）、 设置忽略的自动装配的接口 EnvironmentAware  EmbeddedValueResolverAware、 xxx 
			4）、 注册可以解析的自动装配；我们能直接在任何组件中自动注入：
					BeanFactory ResourceLoader ApplicationEventPublisher ApplicationContext
			5）、添加 BeanPostProcessor ApplicationListenerDetector 
			6）、添加编译时的 AspectJ 
			7）、给 BeanFactory 中注册一些能用的组件；
				environment 【 ConfigurableEnvironment 】、
				systemProperties 【 Map<String, Object> 】、
				systemEnvironment 【 Map<String, Object> 】
		4、 postProcessBeanFactory(beanFactory);BeanFactory准备工作完成后进行的后置处理工作；
			1）、子类通过重写这个方法来在BeanFactory创建并预准备完成以后做进一步的设置
		======================以上是BeanFactory的创建及预准备工作==================================
		5、 invokeBeanFactoryPostProcessors(beanFactory)
			1）、先执行 BeanDefinitionRegistryPostProcessor 的方法；
				1）、获取所有的 BeanDefinitionRegistryPostProcessor
				2）、看先执行实现了 PriorityOrdered 优先级接口的 BeanDefinitionRegistryPostProcessor 
					postProcessor.postProcessBeanDefinitionRegistry(registry)
				3）、在执行实现了 Ordered 顺序接口的 BeanDefinitionRegistryPostProcessor 
					postProcessor.postProcessBeanDefinitionRegistry(registry)
				4）、最后执行没有实现任何优先级或者是顺序接口的 BeanDefinitionRegistryPostProcessors 
					postProcessor.postProcessBeanDefinitionRegistry(registry)
			2）、再执行 BeanFactoryPostProcessor 的方法
				1）、获取所有的 BeanFactoryPostProcessor
				2）、看先执行实现了 PriorityOrdered 优先级接口的 BeanFactoryPostProcessor 、
					postProcessor.postProcessBeanFactory()
				3）、在执行实现了 Ordered 顺序接口的 BeanFactoryPostProcessor 
					postProcessor.postProcessBeanFactory()
				4）、最后执行没有实现任何优先级或者是顺序接口的 BeanFactoryPostProcessor 
					postProcessor.postProcessBeanFactory()
		6、 registerBeanPostProcessors(beanFactory); 注册 BeanPostProcessor （Bean的后置处理器）【  intercept bean creation 】
				不同接口类型的 BeanPostProcessor ；在Bean创建前后的执行时机是不一样的
				BeanPostProcessor 、
				DestructionAwareBeanPostProcessor 、
				InstantiationAwareBeanPostProcessor 、	// aop 和 声明式事务的功能
				SmartInstantiationAwareBeanPostProcessor 、	// aop 和 声明式事务的功能
				MergedBeanDefinitionPostProcessor 【 internalPostProcessors 】 、
				
				1）、获取所有的 BeanPostProcessor;后置处理器都默认可以通过 PriorityOrdered 、 Ordered 接口来执行优先级
				2）、先注册 PriorityOrdered 优先级接口的 BeanPostProcessor 
					把每一个 BeanPostProcessor ；添加到 BeanFactory 中
					beanFactory.addBeanPostProcessor(postProcessor);
				3）、再注册 Ordered 接口的
				4）、最后注册没有实现任何优先级接口的
				5）、最终注册 MergedBeanDefinitionPostProcessor ；
				6）、注册一个 ApplicationListenerDetector ；来在Bean创建完成后检查是否是 ApplicationListener ，如果是
					applicationContext.addApplicationListener((ApplicationListener<?>) bean);
		7、 initMessageSource();初始化 MessageSource 组件（做国际化功能；消息绑定，消息解析）；
				1）、获取 BeanFactory
				2）、看容器中是否有id为 messageSource 的，类型是 MessageSource 的组件
					如果有赋值给 messageSource ，如果没有自己创建一个 DelegatingMessageSource ；
						MessageSource ：取出国际化配置文件中的某个key的值；能按照区域信息获取；
				3）、把创建好的 MessageSource 注册在容器中，以后获取国际化配置文件的值的时候，可以自动注入 MessageSource；
					beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME, this.messageSource);	
					MessageSource.getMessage(String code, Object[] args, String defaultMessage, Locale locale);
		8、 initApplicationEventMulticaster(); 初始化事件派发器；
				1）、获取 BeanFactory
				2）、从 BeanFactory 中获取 applicationEventMulticaster 的 ApplicationEventMulticaster ；
				3）、如果上一步没有配置；创建一个 SimpleApplicationEventMulticaster
				4）、将创建的 ApplicationEventMulticaster 添加到 BeanFactory 中，以后其他组件直接自动注入
		9、 onRefresh();留给子容器（子类）
				1、子类重写这个方法，在容器刷新的时候可以自定义逻辑；
		10、 registerListeners(); 给容器中将所有项目里面的 ApplicationListener 注册进来；
				1、从容器中拿到所有的 ApplicationListener
				2、将每个监听器添加到事件派发器中；
					getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);
				3、派发之前步骤产生的事件；
		11、 finishBeanFactoryInitialization(beanFactory);初始化所有剩下的单实例bean；
			1、 beanFactory.preInstantiateSingletons();初始化后剩下的单实例bean
				1）、获取容器中的所有Bean，依次进行初始化和创建对象
				2）、获取Bean的定义信息； RootBeanDefinition
				3）、Bean不是抽象的，是单实例的，是懒加载；
					1）、判断是否是 FactoryBean ；是否是实现 FactoryBean 接口的Bean；
					2）、不是工厂Bean。利用 getBean(beanName);创建对象
						0、 getBean(beanName) ； ioc.getBean();
						1、 doGetBean(name, null, null, false);
						2、先获取缓存中保存的单实例Bean。如果能获取到说明这个Bean之前被创建过（所有创建过的单实例Bean都会被缓存起来）
							从 private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>(256);获取的
						3、缓存中获取不到，开始Bean的创建对象流程；
						4、标记当前bean已经被创建
						5、获取Bean的定义信息；
						6、【获取当前Bean依赖的其他Bean;如果有按照 getBean() 把依赖的Bean先创建出来；】
						7、启动单实例Bean的创建流程；
							1）、 createBean(beanName, mbd, args);
							2）、 Object bean = resolveBeforeInstantiation(beanName, mbdToUse);让 BeanPostProcessor 先拦截返回代理对象；
								【 InstantiationAwareBeanPostProcessor 】：提前执行；
								先触发： postProcessBeforeInstantiation()；
								如果有返回值：触发 postProcessAfterInitialization()；
							3）、如果前面的 InstantiationAwareBeanPostProcessor 没有返回代理对象；调用4）
							4）、 Object beanInstance = doCreateBean(beanName, mbdToUse, args); 创建Bean
								 1）、【创建Bean实例】； createBeanInstance(beanName, mbd, args);
									利用工厂方法或者对象的构造器创建出Bean实例；
								 2）、 applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
									调用 MergedBeanDefinitionPostProcessor 的 postProcessMergedBeanDefinition(mbd, beanType, beanName);
								 3）、【Bean属性赋值】 populateBean(beanName, mbd, instanceWrapper);
									1）、拿到 InstantiationAwareBeanPostProcessor 后置处理器；
										postProcessAfterInstantiation()；
									2）、拿到 InstantiationAwareBeanPostProcessor 后置处理器；
										postProcessPropertyValues()；
									=====第3步真正赋值了：===
									3）、应用Bean属性的值；为属性利用setter方法等进行赋值(反射原理)
										applyPropertyValues(beanName, mbd, bw, pvs);
								 4）、【Bean初始化】 initializeBean(beanName, exposedObject, mbd);
									1）、【执行Aware接口方法】 invokeAwareMethods(beanName, bean);执行xxxAware接口的方法
										BeanNameAware\BeanClassLoaderAware\BeanFactoryAware
									2）、【执行后置处理器初始化之前】 applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
										BeanPostProcessor.postProcessBeforeInitialization（）;
									3）、【执行初始化方法】 invokeInitMethods(beanName, wrappedBean, mbd);
										1）、是否是 InitializingBean 接口的实现；执行接口规定的初始化；
										2）、是否自定义初始化方法；
									4）、【执行后置处理器初始化之后】 applyBeanPostProcessorsAfterInitialization
										BeanPostProcessor.postProcessAfterInitialization()；
								 5）、注册Bean的销毁方法；
							5）、将创建的Bean添加到缓存中 singletonObjects ；
						ioc容器就是这些Map；很多的Map里面保存了单实例Bean，环境信息。。。。； 所有Bean都利用 getBean 创建完成以后；
						检查所有的Bean是否是 SmartInitializingSingleton 接口的；如果是；就执行 afterSingletonsInstantiated()；
		12、 finishRefresh();完成 BeanFactory 的初始化创建工作；IOC容器就创建完成；
				1）、 initLifecycleProcessor();初始化和生命周期有关的后置处理器； LifecycleProcessor
					默认从容器中找是否有 lifecycleProcessor 的组件【 LifecycleProcessor 】；如果没有 new DefaultLifecycleProcessor(); 加入到容器；
					
					写一个 LifecycleProcessor 的实现类，可以在 BeanFactory 就可以在 onRefresh 和 onClose 做一些事情
						void onRefresh();
						void onClose();	
				2）、	getLifecycleProcessor().onRefresh();
					拿到前面定义的生命周期处理器（ BeanFactory ）；回调 onRefresh()；
				3）、 publishEvent(new ContextRefreshedEvent(this));发布容器刷新完成事件；
				4）、 liveBeansView.registerApplicationContext(this);
	

2、servlet3.0规范
	1、自定义的servlet可以使用 @WebServlet("/hello") 注解代替 web.xml 的
	2、ServletContainerInitializer的应用
		1、根据servlet3.0的8.2.4Shared libraries / runtimes pluggability规则
			1、服务器启动(web应用启动)会创建当前web应用里面的每一个jar包里面的 ServletContainerInitializer 实例
			2、 ServletContainerInitializer 的实现必须放在jar包的META-INF/services文件夹下的javax.servlet.ServletContainerInitializer文件 有全类名
			3、还可以使用@HandlesTypes，在应用启动的时候加载我们刚兴趣的类
		2、代码体现
			// 容器启动的时候会将 @HandlesTypes 指定的这个类型下面的子类（实现类，子接口等）传递过来；
			@HandlesTypes(value={HelloService.class})
			public class MyServletContainerInitializer implements ServletContainerInitializer {

				/**
				 * 应用启动的时候，会运行onStartup方法；
				 * 
				 * Set<Class<?>> arg0： @HandlesTypes 定义的感兴趣的类型的所有子类型；
				 * ServletContext arg1: 代表当前Web应用的 ServletContext ；一个Web应用一个 ServletContext
				 * 
				 * 1）、使用 ServletContext 注册Web三大组件（ Servlet 、 Filter 、 Listener ）
				 * 2）、使用编码的方式，在项目启动的时候给 ServletContext 里面添加组件(注意：必须在项目启动的时候来添加)；
				 * 		1）、 ServletContainerInitializer 得到的 ServletContext ；
				 * 		2）、 ServletContextListener 得到的 ServletContext ；
				 */
				@Override
				public void onStartup(Set<Class<?>> arg0, ServletContext sc) throws ServletException {
					System.out.println("感兴趣的类型：");
					for (Class<?> claz : arg0) {
						System.out.println(claz);
					}
					
					//注册组件  ServletRegistration  
					ServletRegistration.Dynamic servlet = sc.addServlet("userServlet", new UserServlet());
					//配置servlet的映射信息
					servlet.addMapping("/user");
					
					
					//注册Listener
					sc.addListener(UserListener.class);
					
					//注册Filter  FilterRegistration
					FilterRegistration.Dynamic filter = sc.addFilter("userFilter", UserFilter.class);
					//配置Filter的映射信息
					filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
				}
			}
	3、servlet3.0与 springmvc 整合
		1、web容器在启动的时候，会扫描每个jar包下的META-INF/services/javax.servlet.ServletContainerInitializer
		2、加载这个文件指定的类 SpringServletContainerInitializer
		3、spring的应用一启动会加载感兴趣的 WebApplicationInitializer 接口的下的所有组件；
		4、并且为 WebApplicationInitializer 组件创建对象（组件不是接口，不是抽象类）
			1、 WebApplicationInitializer 主要继承结构
				WebApplicationInitializer
					-> AbstractContextLoaderInitializer ：创建根容器； createRootApplicationContext()；	// 抽象给子类实现
						-> AbstractDispatcherServletInitializer ：
							创建一个web的ioc容器； createServletApplicationContext();					// 抽象给子类实现
							创建了DispatcherServlet； createDispatcherServlet()；
							将创建的 DispatcherServlet 添加到 ServletContext 中；
								getServletMappings();													// 抽象给子类实现
							-> AbstractAnnotationConfigDispatcherServletInitializer ：注解方式配置的 DispatcherServlet 初始化器
								创建根容器： createRootApplicationContext()
										getRootConfigClasses();传入一个配置类							// 抽象给子类实现
								创建web的ioc容器： createServletApplicationContext();
										获取配置类； getServletConfigClasses();							// 抽象给子类实现
			2、从 WebApplicationInitializer 的继承结构看出只要继承 AbstractAnnotationConfigDispatcherServletInitializer 实现对应的方法来定制 DispatcherServlet
				// web容器启动的时候创建对象；调用方法来初始化容器以前前端控制器
				public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

					// 获取根容器的配置类；（Spring的配置文件）   父容器；
					@Override
					protected Class<?>[] getRootConfigClasses() {
						// TODO Auto-generated method stub
						return new Class<?>[]{RootConfig.class};
					}

					// 获取web容器的配置类（SpringMVC配置文件）  子容器；
					@Override
					protected Class<?>[] getServletConfigClasses() {
						// TODO Auto-generated method stub
						return new Class<?>[]{AppConfig.class};
					}

					// 获取DispatcherServlet的映射信息
					//  /：拦截所有请求（包括静态资源（xx.js,xx.png）），但是不包括*.jsp；
					//  /*：拦截所有请求；连*.jsp页面都拦截；jsp页面是tomcat的jsp引擎解析的；
					@Override
					protected String[] getServletMappings() {
						// TODO Auto-generated method stub
						return new String[]{"/"};
					}

				}

		5、定制SpringMVC；
			1、@EnableWebMvc:开启SpringMVC定制配置功能(和之前的<mvc:annotation-driven/>效果一样)
			2、配置组件（视图解析器、视图映射、静态资源映射、拦截器...） 只要继承 WebMvcConfigurerAdapter 实现对应的方法即可
		6、servlet3.0的异步处理
			1、原理：tomcat的线程为主线程A，A接到用户请求，然后开启一条新的线程a处理请求，A得到释放，这样tomcat就可以处理更多的请求了
			2、代码实现
				@WebServlet(value="/async",asyncSupported=true)
				public class HelloAsyncServlet extends HttpServlet {
					
					@Override
					protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
						//1、支持异步处理asyncSupported=true
						//2、开启异步模式
						System.out.println("主线程开始。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
						AsyncContext startAsync = req.startAsync();
						
						//3、业务逻辑进行异步处理;开始异步处理
						startAsync.start(new Runnable() {
							@Override
							public void run() {
								try {
									System.out.println("副线程开始。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
									sayHello();
									startAsync.complete();
									//获取到异步上下文
									AsyncContext asyncContext = req.getAsyncContext();
									//4、获取响应
									ServletResponse response = asyncContext.getResponse();
									response.getWriter().write("hello async...");
									System.out.println("副线程结束。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
								} catch (Exception e) {
								}
							}
						});		
						System.out.println("主线程结束。。。"+Thread.currentThread()+"==>"+System.currentTimeMillis());
					}

					public void sayHello() throws Exception{
						System.out.println(Thread.currentThread()+" processing...");
						Thread.sleep(3000);
					}
				}
		4、servlet3.0 整合 springmvc 的异步请求
			1、控制器返回 Callable
			2、Spring异步处理，将 Callable 提交到 TaskExecutor 使用一个隔离的线程进行执行
			3、 DispatcherServlet 和所有的 Filter 退出web容器的线程，但是 response 保持打开状态；
			4、 Callable 返回结果， SpringMVC 将请求重新派发给容器，恢复之前的处理；
			5、根据 Callable 返回的结果。 SpringMVC 继续进行视图渲染流程等（从收请求-视图渲染）。
			6、可以根据下面的日志说明 1-5
				preHandle 拦截请求.../springmvc-annotation/async01
				主线程开始...Thread[http-bio-8081-exec-3,5,main]==>1513932494700
				主线程结束...Thread[http-bio-8081-exec-3,5,main]==>1513932494700
				=========DispatcherServlet及所有的Filter退出线程============================
				
				================等待Callable执行==========
				副线程开始...Thread[MvcAsync1,5,main]==>1513932494707
				副线程结束...Thread[MvcAsync1,5,main]==>1513932496708
				================Callable执行完成==========
				
				================再次收到之前重发过来的请求，做一些视图渲染等等========
				preHandle  拦截请求.../springmvc-annotation/async01
				postHandle...（Callable的之前的返回值就是目标方法的返回值）
				afterCompletion...
			7、异步的拦截器:
				1、原生API的AsyncListener
				2、SpringMVC：实现AsyncHandlerInterceptor；
		5、 servlet3.0 整合springmvc 的异步请求之
			@ResponseBody
			@RequestMapping("/createOrder")
			public DeferredResult<Object> createOrder(){
				DeferredResult<Object> deferredResult = new DeferredResult<>((long)3000, "create fail...");		// 最多等3秒，超时返回第二个参数的消息
				DeferredResultQueue.save(deferredResult);														// 把保存到消息中间件， DeferredResultQueue 是我们模拟的
				return deferredResult;																			// 返回回去，让response等待，web线程释放
			}
			
			// 本来是有一个监听器，来监听消息中间件消息的添加的，现在我们自己请求来模拟吧，最后需要调用 deferredResult.setResult(order) 表示此消息处理完成
			@ResponseBody
			@RequestMapping("/create")
			public String create(){
				//创建订单
				String order = UUID.randomUUID().toString();
				DeferredResult<Object> deferredResult = DeferredResultQueue.get();
				deferredResult.setResult(order);
				return "success===>"+order;
			}



