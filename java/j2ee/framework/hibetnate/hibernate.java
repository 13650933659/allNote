








1、hibernate是什么
	1、hibernate是标准的orm框架，是jpa的实现
	2、什么是ORM框架：全称为(Object relation mapping)
		1、JDBC操作数据库很繁琐，简化编程
		2、Sql语句编写并不是面向对象的
		3、0/R Mapping跨越数据库平台
		4、ORM的框架有：Hibernate、toplink、jdo、ibatis、JPA(意愿统一天下)
2、开发环境的搭建
	1、hibernate332的环境搭建
		1、下载hibernate-distribution-3.3.2.GA-dist
		2、下载hibernate-annotations-3.4.0.GA
		3、下载slf4jl.5.8(slf4j日志框架自己的实现，还有很多框架的适配器)
		4、下载apache-log4j-1.2.15(log4j的框架)
		5、建User-library，命名为 hibernate,并加入相应的jar包
			1、引入mysql的驱动jar包
			2、使用hibernate框架必须要去hibernate-distribution-3.3.2.GA-dist获取如下jar包
				1、Hibernate3
				2、/required
				3、slf4-nop1.5.8.jar(slf4j日志框架自己的实现，不用去找这个，直接使用log4j的)
			3、在如果要使用注解，要去hibernate-annotations-3.4.0.GA获取相应的jar包
				1、Hibernate-annotaion.jar（实现）
				2、lib/ejb3-persistence.jar（ejb3的标准接口）
				3、lib/hibernate-common-annotations.jar(ejb3标准接口Hibernate帮他实现)
			4、搭建日志环境并配置显示DDL（data definition language）语句，使用log4j
				1、slf4j与log4j的关系：slf4j像是一个大管家，可以管理许多的日志框架，log4j是其中之一
				2、加入slf4j-log4j12-1.7.25.jar(适配器设计，把slf接口转成log4j的接口)
				3、加入log4j的jar包log4j-1.2.15.jar，去掉slf4-nop1.5.8.jar
				4、从hibernate/project/etc 目录拷贝log4j.properties
				5、査询hibernate文裆，日志部分，调整日志的输出策略，只看DDL语句和sql语句
			5、如果需要使用二级缓存
				1、加入：optional/ehcache/ehcache-1.2.3.jar（ehcache炸包）
				2、加入：commons-logging-1.1.1.jar（ehcache日志框架炸包）
	2、hibernate项目开发的三种方式
		1、官方推荐的（映射文件->核心文件->自动创建DB表）
		2、中国最多人用的（DB表->自动创建映射文件和核心文件）
		3、第三种随便（表->实体类->hibernate.cfg.xml）
3、表和字段的映射
	1、表的映射
		1、使用@Entity注解就可以表示为一个实体类(会映射成表)，
		2、使用@Table(name="stu")可以指定映射的表名称
	2、列的映射
		1、主键映射
			1、使用@Id注解的为表的主键
			2、主键的生成策略@GeneratedValue(strategy=填以下四种)，第二个参数是generator还是sequenceName???
				1、GenerationType.SEQUENCE：[oracle]，还要加第二个参数sequenceName="序列名称(自定义序列或者是hibernate_sequence)"
				2、GenerationType.IDENTITY:[mysql/sqlServer]
				3、GenerationType.TABLE：还要加第二个参数sequenceName="表生成器"
				4、GenerationType.AUTO：自适应的，这个是默认的
		2、实体类里边的属性使用指定表中对应的列的属性
			1、默认会给普通字段的get上@basic，如果想控制此字段就要换成@Column(这里面的属性看文档可以指定长度约束等)
			2、这里给个列的类型是java.util.Date类型的例子，你想控制到（日期，时间，日期时间默认）@Temporal(控制到日期的存储格式看文档)
3、表关系映射（铁律：凡是双向关联要设mappedBy）
	1、一对一关联:     
		1、一对一单向外键关联：在Husband类中的getWife配置
			@OneToOne
			@JoinColumn(name="wifeid") //指定生成的数据库字段名，不用注解默认是 wife_id
			public Wife getWife() {
				return wife;
			}
		2、一对一双向外键关联（双向关联是指java程序的关系，数据库还是一个外键）
			1、在Husband类中的getWife配置
				@OneToOne
				@JoinColumn(name="wifeid")
				public Wife getWife() {
					return wife;
				}
			2、并在Wife类中的getHusband配置
				@OneToOne(mappedBy="wife")
				public Husband getHusband() {
					return husband;
				}
			3、一对一单向主键关联（不重要，可以不用看了）
				1、@primaryKeyJoinColumn  //好像是bug，他没把我们建立主键关联（不管）
			4、一对一双向主键关联（不重要，可以不用看了）
				1、@primaryKeyJoinColumn
	2、多对一与一对多
		1、多对一单向关联：只需要在多的一端User的getGroup配置
			@ManyToOne
			@JoinColumn(name="groupid")
			public Group getGroup() {
				return group;
			}
		2、一对多单向关联：只需要在一的这一端Group的getUsers配置
			@OneToMany
			@JoinColumn(name="groupid") //Hibernate默认将OneToMany理解为ManyToMany的特殊形式，如果不指定生成的外键列，则会默认生成多对多的关系,产生一张中间表。
			public List<User> getUsers() {
				return users;
			}
		3、一对多（多对一）双向关联
			1、一般以多的一端为主,先配置多的一端,在多的一端User的getGroup配置
				@ManyToOne
				@JoinColumn(name="groupid")
				public Group getGroup() {
					return group;
				}
			2、在一的一端Group的getUsers配置
				@OneToMany(mappedBy="group")
				public List<User> getUsers() {
					return users;
				}
	3、多对多
		1、单向关联：老师->学生(老师需要知道自己教了哪些学生)
			@ManyToMany //多对多关联 Teacher是主的一方 Student是附属的一方 
			@JoinTable(
				name="t_s", //指定中间表表名
				joinColumns={@JoinColumn(name="teacherid")},//本类在中间表生成的外键
				inverseJoinColumns={@JoinColumn(name="studentid")}//对方类主键在中间表生成的对应字段名
			)
			public Set<Student> getStudents(){
				return students;
			}
		2、双向关联：老师<->学生(这里采用的是自动产生中间表，但是也可以自己创建中间表然后自己配置两个多对一)
			1、在Teacher这一端的students上配置
				@ManyToMany
				@JoinTable(
					name="t_s",
					joinColumns={@JoinColumn(name="teacherid")},
					inverseJoinColumns={@JoinColumn(name="studentid")}
				)
				public Set<Student> getStudents(){
					return students;
				}   
			2、在Student一端的teachers只需要配置
				@ManyToMany(mappedBy="students")
				public Set<Teacher> getTeachers(){
					return teachers;
				}
	4、继承映射（不太重要，他的CRUD有空再去研究）
		1、三种方式(如果非用不可，请考虑用1,3方法，不要用多态去load，他会连接去查询造成浪费，最好能用具体的某一个子类去load)
			1、一张总表SINGLE_TABLE(只会生成一张表)，请参考hibernate_1900_lnheritence_Mapping_Single_Table
			2、每个类分别一张表(包括父类和子类)TABLE_PER_CLASS，请参考hibernate_2000_lnheritence_Mapping_Table_Per_Class
			3、每个子类一张表jOINED(包括父类生成3张表)，请参考hibernate_2100_lnheritence_Mapping_JOINED
4、关联关系中的CRUD(CUD归cascade管，R归fetch管)：@ManyToOne(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	1、fetch（如果是eager单个对象会立即加载关联对象）
		1、铁律：双向不要两边设置Eager(会有多余的査询语句发出)
		2、一般默认就好，@ManyToOne的fetch默认FetchType.EAGER，@OneToMany是FetchType.LAZY
		3、对多方设置fetch的时候要谨慎，结合具体应用，一般用Lazy不用eager，特殊情况（多方数量不多的时候可以考虑，提高效率的时候可以考虑）
	2、cascade
		1、参数值说明
			1、CascadeType.PERSIST 
				级联保存，A类新增时会级联新增B，JPA规范中的persist(√)，Hibernate的save(×)
			2、CascadeType.MERGE
				级联级联更新，指A新增或者变化，会级联B对象，JPA规范中merge(√)，Hibernate的update(×)
			3、CascadeType.REMOVE 
				级联删除，删除A会级联B，JPA规范中的remove(√)，Hibernate的delete(√)
			4、CascadeType.REFRESH 
				级联刷新，获取order同时也重新获取最新的items，调用JPA规范中的refresh(√)时，适用于Hibernate的flush(√)方法
			5、CascadeType.ALL 
				包含所有持久化方法
		2、三种方法可避免全部删除的情况：
			1、去掉@ManyToOne(cascade={CascadeType.All})设置；
			2、直接写Hql语句执行删除；
			3、将他们关联的对象设置为空，打断关系

5、核心接口开发
	1、实体类对象三种的状态
		1、transient：内存中一个对象，没ID，缓存中也没有
		2、persistent：内存中有，缓存中有，数据库有，有ID 
		3、detached：内存有，缓存没有，数据库有，有ID
	2、核心幵发接口介绍
		1、AnnotationConfiguration(就是hibernate-cfg.xml文件)，用new AnnotationConfiguration().configure().buildSessionFactory();产生sf
		2、SessoinFactory：用来管理Session，每个应用只需要一个SessionFactory，除非要访间多个数据库
			1、关注两个方法：openSession getCurrentsession
				1、openSession每次都是新的，需要close
				2、getCurrentsession从上下文找，如果有，用旧的，如果没有，建新的
					1、用途，定事务边界(两种事务单库、多库即分布式)
					2、事务提交自动close(不需要我们关闭，会报错)
					3、必须配置current_session_context_class (jta、thread常用 managed、custom.Class少用) 
						1、thread 使用connection 单数据库连接管理事务
						2、jta （全称java transaction api）-java分布式事务管理（多数据库访问），jta由中间件提供（jboss,WebLogic,spring等，tomcat不支持）
	3、Session：和数据库的连接（简单说就是增 删 改 查）
		1、load：返回的是一个代理对象（延迟加载），get：马上发sql语句
		2、save：保存
		3、delete：只要这个对象有id就可以删除 （前提数据库有对应记录）
		4、update
			1、用来更新只要有id的对象（前提数据库有对应记录）
			2、更新部分更改的字段
				1、设定@Column的updatable属性，不过这种方式很少用，因为不灵活
				2、使用 HQL(EjBQL)(建议）
		5、saveOrUpdate：如果有id就执行update，没有则执行save
		6、clear方法：无论是load还是get,首先査找一级缓存，如果没有才会去数据库査找，调用clear会清除一级缓存  //会清除二三级缓存？应该不会的
		7、refresh(obj)：发sql重新重数据库查询指定的po
		8、flush()方法
			1、默认的当事务提交后,如果发现属性有变化才会发sql将(session缓存)同步到数据库，但是用了fulsh，当内存有变化马上就会发sql将(session缓存)同步到数据库
			2、session的FlushMode设置,可以设定在什么时候同步缓存与数据库(很少用,性能调优用)，例如: session.setFlushMode(FlushMode.AUTO)
		9、find方法已经过时！
	4、SchemaExport (程序自动建表，有空再去看看，有了这个我们就可以不用hbm2ddl.auto)

6、Hibernate 查询(Query Language)，请看Hibernate332_0002_HQL_msb项目
	1、NativeSQL > HQL > EJBQL(JPQL 1.0,hql的子集) > QBC(Query By Criteria) > QBE(Query By Example, QBC的子集)
	2、总结：QL应该和表映射关系结合，共同为査询提供服务。

7、性能调优
	1、注意session.clear()的运用  // 面试题：Java有内存泄漏吗？语法级别没有 但是可由java引起,例如:连接池不关闭,或io读取后不关闭
		1、在一个大集合中进行遍历，比如取了10w条数据，而且里边又有懒加载的关联对象，应该1W条就要clear一下
	2、1+N问题 比如十个Topic对应十个Category，你发一条hql="from Topic"，然后再发十条sql去查对应的Category，下列四种解决方案具体用哪一种看情况
		1、@ManyToOne(fetch=FetchType.LAZY)  // 只有当需要时(如:t.getCategory().getName()时)才会去获取关联表中数据 可以缓解1+N问题
		2、@BatchSize // 在与查询表(此例中为Topic类)关联的表类(此例中为Category类)头处加@BatchSize(size=5)
		3、join fetch // 修改hql语句为--"  from Topic t left join fetch t.category c  "
		4、QBC // 使用QBC的 createCriteria(*.class)执行查询 也可避免N+1问题其实底层就是第3中解决方案
	3、list和iterate不同之处
		1、list取所有
		2、iterate先取 ID,等用到的时候再根据ID来取对象
		3、session中list第二次发出，仍会到数据库査询
		4、iterate 第二次，首先找session 级缓存
	4、缓存(有空去看看sql会不会使用缓存机制)
		1、session缓存（一级缓存）
			1、当（save/update/date/load/get/query.list/）会往缓存放入opjo对象
			2、当（load/get）会从缓存取，这里注意：query.list等方法是雷锋只放不取因为他是语句查询条件不确定
			3、session的生命周期：（evict/clear/关闭session就会结束）
		2、常见的二级缓存有hashtable/occache/ehcache等等（作用域是sessionFactory，他可以放在内存和磁盘），改动少，经常被访问(用户的权限，角色)才适合使用二级缓存机制
			1、请看项目：Hibernate332_0004_SecondCache_msb
			2、加入：optional/ehcache/ehcache-1.2.3.jar（ehcache炸包）、commons-logging-1.1.1.jar（ehcache日志框架炸包）
			3、去hibernate.cfg.xml启用
			4、把ect/ehche.xml文件拷贝到src目录
			5、去你想要做成二级缓存的实体类加注解
			6、缓存算法：（纯为了面试）ehcache.xml中配置memoryStoreEvictionPolicy = "LRU" ()
				1、Least Recently Used –最近很少被使用
				2、Least Frequently Used (命中率高低)
				3、First In First Out 按顺序替换
		3、如果想启动查询缓存(3级缓存)，一般不会用词缓存（作用域大于等于sessionFactory）
			1、使用背景：同样重复的查询执行N此会发送N次请求，使用了查询缓存，相同的查询只会发一次，注意一点，有交集也不算相同
			2、去hibernate.cfg.xml启用<property name="cache.use_query_cache">true</property>即可(注意此缓存是在二级缓存正常启用的情况下才可以生效)
 

8、事务并发问题
	1、事务：特性Atomistic(原子性)、Consistency(一致性)、Isolation(隔离性)、Durability(持久性)
	2、事务并发时可能出现的问题
		1、lost update(丢失更新)，这个只要数据库支持事务就已经解决了   //模拟的结果还是被事务B冲掉了？
			事务A -> start -> 取得余额1000                                             -> 再加100=1100 -> 提交
			事务B                          -> start -> 取得余额1000再加100=1100 - 提交
		2、dirty read(脏读)   //算是模拟出来了
			事务A -> start                                      -> 取得余额1100         -> 取出1100
			事务B          -> start -> 取得余额1000再加100=1100                 -> 回滚
		3、non-repeatable read(不可重复读)  //我模拟不出来
			事务A -> start -> 取得余额1000                                              -> 再次取得余额1100 -> 提交
			事务B                          -> start -> 取得余额1000再加100=1100 -> 提交
		4、phantom read(幻读)，我倒觉得这种情况是正常的（这个可以不用管）
			事务A -> start -> 取得十个学生                                   -> 再次取得11个学生 -> 提交
			事务B                           -> start -> 插入一个学生 -> 提交
	3、数据库的事务隔离机制
		1、原生JDBC的处理查看 java.sql.Connection 文档
		2、hibernate的处理
			1、去hibernate.cfg.xml配置<property name="hibernate.connection.isolation"></property>
			2、取值1：read-uncommitted  2：read-committed  4：repeatable read  8：serializable，为什么取值要使用 1 2 4 8 而不是 1 2 3 4，因为前者位移计算效率高
				1、read-uncommitted(允许读取未提交的数据) 会出现dirty read, phantom-read,non-repeatable read 问题 
				2、read-commited(读取已提交的数据 项目中一般都使用这个)不会出现dirty read,但仍然会出现 non-repeatable read 和 phantom-read，可用悲观锁 乐观锁来解决
					1、悲观锁(Pessimistic Lock)解决repeatable read的问题（依赖于数据库的锁，表锁还是行锁，其他事务能查不能改）
						1、select ... for update
						2、使用另一种load方法--load(xx.class , i , LockMode.Upgrade)
							1、LockMode.None无锁的机制，Transaction结束时，切换到此模式
							2、LockMode.read在査询的时候hibernate会自动获取锁
							3、LockMode.write insert  update hibernate 会自动获取锁
							4、以上3种锁的模式，是hibernate内部使用的(不需要设)
							5、LockMode.UPGRADE_NOWAIT是 ORACLE 支持的锁的方式
					2、乐观锁(Optimistic Lock)，请参考Hibernate332_0007_OptimisticLock_msb
				3、repeatable read(加锁事务执行中其他事务无法执行修改或插入操作     较安全)
				4、serializable解决一切问题(顺序执行事务 不并发，实际中很少用，各自事物没有访问相同资源也不并发吗？)
  
 
