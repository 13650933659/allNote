


1、实体类对象三种的状态
	1、transient：内存中一个对象，没ID，缓存中也没有
	2、persistent：内存中有，缓存中有，数据库有，有ID 
	3、detached：内存有，缓存没有，数据库有，有ID


2、核心幵发接口介绍
	1、AnnotationConfiguration(就是hibernate-cfg.xml文件)，用new AnnotationConfiguration().configure().buildSessionFactory();产生SessionFactory
	2、SessoinFactory
		1、用来管理Session，每个应用只需要一个SessionFactory，除非要访间多个数据库
		2、关注两个方法即：openSession getCurrentsession
			1、openSession每次都是新的，需要close
			2、getCurrentsession从上下文找，如果有，用旧的，如果没有，建新的
				1、用途，定事务边界(两种事务单库、多库即分布式)
				2、事务提交自动close(不需要我们关闭，会报错)
				3、必须配置current_session_context_class (jta、thread常用 managed、custom.Class少用) 
					1、thread 使用connection 单数据库连接管理事务
					2、jta （全称java transaction api）-java分布式事务管理（多数据库访问），jta由中间件提供（jboss,WebLogic,spring等，tomcat不支持）
	3、Session：管理一个数据库的任务单元（简单说就是增 删 改 查）
		1、load：返回的是一个代理对象（延迟加载），get：马上发sql语句
		2、save：保存
		3、delete：只要这个对象有id就可以删除 （前提数据库有对应记录）
		4、update
			1、用来更新只要有id的对象（前提数据库有对应记录）
			2、更新部分更改的字段
				1、设定@Column的updatable属性，不过这种方式很少用，因为不灵活
				2、使用 HQL(EjBQL)(建议）
		5、saveOrUpdate：如果有id就执行update，没有则执行save
		6、clear方法：无论是load还是get,首先査找一级缓存(session级缓存)，如果没有才会去数据库査找，调用clear方法可以强制清除session缓存  //会清除二三级缓存？应该不会的
		7、refresh(obj)：发sql重新重数据库查询指定的po
		8、flush()方法
			1、默认的当事务提交后,如果发现属性有变化才会发sql将(session缓存)同步到数据库，但是用了fulsh，当内存有变化马上就会发sql将(session缓存)同步到数据库
			2、session的FlushMode设置,可以设定在什么时候同步缓存与数据库(很少用,性能调优用)，例如: session.setFlushMode(FlushMode.AUTO)
		9、find方法已经过时！
	4、SchemaExport (程序自动建表，有空再去看看，有了这个我们就可以不用hbm2ddl.auto)
	5、Query接口(hibernate的)，参考Hibernate査询(HQLEJBQL)的内容


1、Hibernate 查询(Query Language)，请看Hibernate332_0002_HQL_msb项目
	1、NativeSQL > HQL > EJBQL(JPQL 1.0,hql的子集) > QBC(Query By Criteria) > QBE(Query By Example, QBC的子集)
	2、总结：QL应该和导航关系结合，共同为査询提供服务。



