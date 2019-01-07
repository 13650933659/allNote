











1、spring是什么
      是Java轻量级的开源框架，他是分层的mvc，他使得我们的应用开发起来更加容易，使得我们有更多的时间专注于自己的业务

2、获取spring容器上下文
	1、得到ApplicationContext/AbstractApplicationContext(可关闭)，请参考API还有一些构造方法，比如可以加载多个beans.xml
	   1、ac=new ClassPathXmlApplicationContext("beans.xml");
	   2、ac=FileSystemXmlApplicationContext("G:\MyEclipse_code\J2EE-spring\myssh\src\beans.xml");
	   3、ac=new XmlWebApplicationContext("beans.xml");
	2、得到BeanFactory，bf是ac的父接口《懒加载》
	   1、BeanFactory bf=new XmlBeanFactory("beans.xml");

3、IOC(Inversion Of Control)或者说DI(Dependency Injection)
	1、xml配置(配合set方法注入的方式)
		1、bean:scope（默认就好）
			1、singleton：默认，单例，预先实例化
			2、prototype：多例《少用》
			3、request：多例，用于web项目
			4、session：单例，用于web项目
			5、global:
		2、beans:default-lazy-init和bean:lazy_init（针对singleton来说的）
			1、false：默认的，只要spring容器一初始化就会马上把所有（singleton）的bean初始化
			2、true：用时才会去初始化此（singleton）的bean
		3、beans:default-autowire：控制set方法注入的，但是不影响@Autowired注解，默认no，是用不了set方法的注入，可以改为byName或者byType，才可以使用set方法注入
		4、bean:autowire（这个不管了）
			1、byName《默认的，根据类set方法的名字匹配<property name="userDao" ref="userDao1"/>》
			2、byType《根据类set方法的参数类型匹配已注入的bean的类型，如果找到多个就报错》
			3、construcor《get+构造一起用》
			4、auto detect《优先选3在用2》
			5、default《取决于beans:default-autowire》
		5、bean:init-method="你的构造方法"和bean:destroy-method="你的析构方法"（这个不重要）
	2、annotation，这里用好推荐使用1,2
		1、 @Autowired（推荐set方法上加，也可以在属性加），这个不知道要不要和1-3配置使用验证(答：不会的互不影响)
			1、他只按照byType(具体还要看引用是的是接口还是实现类), 如果想用byName使用 @Qulifier("userDao1")在参数内部加
		2、 @Resource，要加入：j2ee/common-annotations.jar但是我的没加就有了（javax.annotation.Resource）
			1、默认按名称（set方法名称头字母小写，但是也可以直接在属性上使用），找不到，按类型（如果是使用接口引用，出现两个实现类也是出错）
			2、也可以指定特定哪个bean的名称： @Resource(name="userDaoImp")
		3、 @Component @Service @Controller @Repository在2.5.6中四个是一样的
			1、用第一个就好了，用法：载对应的类上加 @Component(value="userDao")，value默认是userDaoImp
		4、 @Required容错机制，就像@Override，可写可不写
		5、 @Scope、 @PreDestroy、 @PreDestroy、 @PostConstruct 指定bean是否懒加载、改造方法注解，自己去研究

4、AOP(Aspect Orient Programming)，Spring该我们提供了多切面比如声明式的事物管理
   1、底层是动态代理，这里所说的切面是面向一大堆的对象的，当这些对象在执行指定的方法时，这个切面就会切入做一些自定义的业务（比如：日志管理、性能测试、事务管理）

5、spring bean的什么周期
	1、IOC容器启动之后，我们的bean实例化(非懒加载的单例bean)
	2、设置bean的属性
	3、调用BeanNameAware.setBeanName()方法
	4、调用BeanFactoryAware.setBeanFactory()方法
	5、调用ApplicationContextAware()方法
	6、调用BeanPostProcessor.postProcessBeforeInitialization()方法  // 前置处理器，这是切面的方法(AOP)
	7、调用InitializingBean.afterPropertiesSet()方法  // 这里我们可以可以定制自己的init方法
	8、调用BeanPostProcessor.postProcessAfterInitialization()方法  //  后置处理器，这是切面的方法(AOP)
	9、Bean可以使用了
	10、当IOC容器关闭之后
	11、调用DisposableBean.destory()方法  // 这里我们可以定制自己的destory方法