1、j2se
	1、Integer的valueOf()和parseInt()有什么不同，
		1、前者是返回Integer后者的返回值是int
	2、java的设计模式，并且写实例代码
		1、策略模式(strategy)
		2、观察者模式(observer)
		3、装饰者模式(decorator)
		4、单例模式(singleton)
		5、工厂模式(factory)
		6、适配器模式(adapter)
		7、模板模式
		8、代理模式(proxy)
		9、责任链模式
	3、为什么要重写对象的equals方法，重写了equals方法为什么还要重写hashCode方法呢
		1、一般的equals方法判断的是两个对象的地址是否相等，有时我们需要按照我们的业务来判断两个对象是否相等，就要重写他了
		2、一般的地方不需要重写hashCode，当在HashMap和HashSet这集合集合类是，他们的key是以hashCode为准，这时就需要重写hashCode
	4、反射机制的用途
		1、在运行时可以动态地操作类对应的对象里面的方法和属性，反射最大的应用就是框架
	5、java代理
		1、我自己模拟的是静态代理
		2、动态代理
			1、通过JDK提供的InvocationHandler接口来做
			2、CGLIB
			3、两者的区别，前者只能代理实现接口的类，后者可以代理没有实现接口的类
	6、静态代码块、构造代码块、构造方法
		1、优先级：静(类加载时)>构块(被实例化时)>构方(被实例化时)
	7、线程和锁
	8、newInstance和new的区别
		1、newInstance是弱类型，效率低，他通过反射机制创建对象，分解成两步（即：加载类再创建），只能调用此类的无参构造方法
		2、new是强类型，效率相对高，他可以调用任何public的构造方法	
	9、你对MVC的看法？
		答：他是model/view/controller的缩写，把数据/业务逻辑/界面显示分离开，降低了代码耦合度，利于的管理和扩展。
	10、你对面向对象编程(OOP)的看法？
		答：这种编程思想更加趋向人的思维，（封装/继承/多态）更利于项目的管理/扩展/代码复用
		比如老太太养了十只鸡，分别统计他们的体重和颜色，你怎么用这种编程思想，更加对这些数据的管理。
	11、你对多线程的看法？
		答：线程就是一个应用程序内部的顺序控制流(比如就java来说int a=1+2;int b=3+4;cpu会自上而下的执行)，
		进程就像一个大型的线程，一个应用程序运行，随之产生进程，由此进程会创建多条线程，并行执行，各线程的执行顺序不一定按照你代码启动的顺序，而是由操作系统决定他们的执行顺序和执行时间片
		应用场景：
			1、利用它完成重复性的工作（实现动画，声音等的播放）
			2、利用它完成一次较耗时的操作（如文件下载，网络连接）
			3、即时通讯软件开发，游戏软件开发
			4、即时通讯软件，每当用户上线启动：每个用户与服务器的通讯连接线程（用socket对象来得到输入输出流得到信息）
	12、对super和super()的看法？
		答：super()是调用父类的构造方法的只能在子类的构造方法的第一行调用，别的方法不可以调用，
		子类别的方法只能用super调用父类的普通方法，父类的方法和属性如（super.name/super.show()这时就可以不用在首行）
		记得父类的方法要不是private才可以。
	13、对ArrayList和Vector
		答：ArrayList和Vector都是数组实现（有序可重复），但不同的是，Vector是线程安全，加了同步，所以原则上ArrayList比Vector比快；
		LinkekList是链表实现，增删快，查找慢，所以你要是插入数据时，显然LinkedList是最快的，其次是ArrayList，再者Vector
		HashSet是异步（无序的不可重复的）的
	14、对final关键字的理解？
		答：
		修饰类：此类不可以被继承，但是可以实例化
		修饰属性：此属性不可以修改，可以结合static做常量
		修饰方法：此方法不可以重写
	15、interface和abstract的区别
		1、接口的方法全部是抽象的（不可以实现），而抽象类的方法则不一定（可以实现）。
		2、接口的方法不可以用private/protected，而抽象类可以用任何一种修饰符。
		3、接口可以实现多继承，而抽象类只能继承一个类。
	16、int和Integer的区别
		1、Integer是java为int提供的封装类
		2、int是原始类型，Integer是封装类
		3、他们的不同体现在：大小和访问速度问题，底层的数据结构和存储方式（int在stack，Integer在heap）
	17、值传递和引用传递的区别
		1、值传递：方法调用时，实际参数把它的值传递给对应的形式参数，方法执行中形式参数值的改变不影响实际参数的值。
		2、也称为传地址：方法调用时，实际参数的引用(地址，而不是参数的值)被传递给方法中相对应的形式参数，
		在方法执行中，对形式参数的操作实际上就是对实际参数的操作，方法执行中形式参数值的改变将会影响实际参数的值。
	18、String和StringBuffer区别
		1、他俩最大的区别是，他们在拼接字符串时，String是重新new一个String覆盖旧的，而StringBuffer是在原来的基础之上追加
		2、StringBuffer是同步的， StringBuilder 是异步的
	19、ArrayList源码分析
		1、默认长度为10的空数组
		2、elementData、size、modCount是真正的数组和长度和被修改的次数(改和查不会修改modCount)
		3、add方法：如果需要扩容的话，默认扩容一半。如果扩容一半不够，就用目标的size作为扩容后的容量。
		4、delete方法：使用复制效率低下
		5、set：效率比较高了
		6、clear:里边的全部清空，但是容量还是那么多
		7、工具方法
			1、方法：public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) // 如果是泛型就使用new Object[];
			2、方法：System.arraycopy(Object[] src, int srcStart, Object[] target, targetStart, int len);	// 从src的srcStart开始复制len个元素到target
	20、有序的HashMap是LinkedHashMap
	21、hashMap的底层
		1、HashMap底层是数组和链表的结合体，key的hashcode数组下标，
		并每个元素指向下一个元素构成一个链表(类型为Map.Entry 其实就是一个key-value对)，
	22、同步的Map：HashTable和HashMap都是实现Map，但是前者是同步的后者是异步的
	23、String str = new String("abc");创建了多个字符串对象
		1、把new String("abc")看成2部分，如果字符串常量池中没有"abc"则创建之，并且放在栈中，new的时候把这个字符串对象拷贝一份到堆中，所以一共创建2次
	24、注意：Double a = 0d;Double b = 0d;a==b为false，和Integer不一样
	25、一个栈的序列是12345，根据栈是先进后出的(像装子弹一样，队列就反之)5开头的不可能
	26、wait和sleep方法的区别
		1、这两个方法来自不同的类分别是Thread和Object
		2、最主要是sleep方法没有释放锁，而wait方法释放了锁
		3、sleep必须捕获异常，而wait不用
	
2、j2ee
	1、对j2ee的，struts/spring/hibernate三大框架的看法？
		答：
		struts（基于MVC的web框架）负责前台数据的显示和处理请求，
		hibernate（orm框架：对象关系映射）对jdbc轻量级的封装，aspect
		spring（容器框架：核心是AOP,IOC）用于配置各个bean,并且维护各个bean之间的关系。
	2、hibernate核心接口的注意事项
		答：
		1、AnnotationConfiguration才可以读注解的核心文件继承了Configuration
		2、用sessionFactory.getCurrentSession();得到session要设<property name="current_session_context_class">thread</property>
			常用thread，在当前线程找session没有则创建新的，但是结合spring就不用管了。
		3、理解好三态（transient（瞬态）/persistent(持久态)/detached(脱管态)）
			瞬：内存有，没ID，缓存没，DB没
			持：内存有，有ID，缓存有，DB有
			脱：内存有，有ID，缓存没，DB有
		4、session常用的方法有：save/delete/get或者load/update
		5、session不常用的方法有：merger(teacher)/saveorupdate(teacher)/flush(强制同步)/clear(清除session级缓存)
		6、对4,5的总结：
			1、get和load区别：load是懒加载（还有级联的的两个参数：cascade要级联吗/fetch取要懒吗）
			2、update他改一个会改全部字段浪费效率，最好用hql语句，还有其他两种方法看马士兵的教程。
		7、事务提交则自动关闭session自动关闭，这时缓存也没有了。
	3、AOP(Aspect Orient Programming)，Spring该我们提供了多切面比如声明式的事物管理
		1、 切面类(包括切点=@Before@Around等注解=使用指定的表达式说在哪些方法起作用+什么时候起作用)
		2、 可以看出来切面是面向一大堆对象的（比如：日志管理、性能测试、事务管理）
		3、 原理是动态代理+spring的后置处理器
	4、mybatis如何做批处理的(用forEech标签)
	5、mybatis的引入
	6、springcloud和dubbo最大的区别就是，服务调用方式前者使用的是rest API风格，后者使用的RPC
	7、IOC的注入方式有几种（上面的注解注入先不管了）
		1、3种（接口注入、构造方法注入、set方法注入）
	8、Spring有几种配置方式？
		1、3种（xml配置、注解配置、java类的配置这个要在此类加 @Configuration） 
	9、如何以注解的方式配置spring，2.5以后才支持的
		1、默认是关闭，我们要在xml中启用<context:annotation-config/>，然后就可以使用注解来配置bean了
	10、什么是IOC(Inversion of control)和DI(Dependency Injection)
		1、IOC：创建对象的控制权由我们转IOC向容器
		2、DI：我们需要使用的属性对象依赖IOC容器注入
	11、mybatis和hibernate的有什么区别
		1、h是标准的orm框架，严格说m不是，但是m比h使用起来更加简单一点，m可以非常灵活的编写动态的sql，这样就避免了一些额外的查询，
		虽然h也可以直接写sql但是这样就破坏了h的原来的思想了orm
		2、如果项目中表关系比较复杂，而且查询的逻辑复杂建议使用m，如果业务简单可以使用h
	12、docker容器里面的环境怎么访问外网
		1、可以访问外网的，但是他没有硬件的驱动呢(配置文件)
	13、servlet的生命周期
		1、init"只在第一次初始化时调用"->service再去调用doGet或者doPost方法"每次"->destroy方法"销毁"
	14、为什么要注册到eureka
		1、微服务交给eureka管理，就能其他微服务通讯
	15、tomcat如何优化
		1、jvm内存配置的优化，去$TOMCAT_HOME/bin/catalina.sh(win是.bat)配置参数，也可以在eclipse那边配置
			1、解决java.lang.OutOfMemoryError: Java heap space  // 堆内存溢出(对象过多)
				-Xms512M(JVM堆初始内存) 
				-Xmx1024M(JVM堆最大内存) 
			2、解决java.lang.OutOfMemoryError: PermGen space  // 静态区溢出(加载的类过多)
				java8以下版本使用XX:PermSize和-XX:MaxPermSize 两个参数，限制静态区最小和最大内存范围
				java8开始已经将类定义存放到了元数据（MetaspaceSize）空间，而元数据空间是与堆空间共享同一块内存区域的，所以java8开始没这个问题了
			3、解决堆栈溢出的另一个方法
				-XX:MaxNewSize=256m // 当tomcat内存不足时使用此分配的内存；从而达到调优的目的
			4、解决java.lang.StackOverflowError异常 // 表示栈内存溢出(一般是死循环、无限递归导致，自己查看代码)
		2、并发配置优化，去$TOMCAT_HOME/conf/server.xml配置，详情参考：https://blog.csdn.net/cjr2015/article/details/84279916
	16、微服务的优势
		1、解耦，他把一个大型的应用按业务拆分成多个小服务，服务之间互相调用，又互相独立运行在独立的进程中，可以单独的部署
	15、git冲突了，要回滚使用什么命令
		1、使用：git merge –abort，可以回滚到刚刚没合并之前的状态

3、数据库
	1、创建表索引
		1、以mysql为例
			1、create index 索引名称 on 表名(列名1,列名2);
			2、查看是否创建成功：show index from 表名，也可以在UI客户端清晰的看到
	2、java如何调用视图和存储过程
		1、CallableStatement来调用存储过程
		2、视图直接使用select语句即可(create view v1 as select id from user; -> select * from v1)
	3、redis的场景应用
		1、读频率比较高的数据适合存入，提高读的效率，以空间换取时间
	4、常用语法
		1、字符串拼接：mysql使用+，oracle好像是|
		2、获取当前时间：mysql使用now
		3、一个张的数据移动到另一张表(以mysql为例)
			1、表结构完全一样：insert into 表1 select * from 表2
			2、表结构不一样（这种情况下得指定列名）：insert into 表1 (列名1,列名2,列名3) select  列1,列2,列3 from 表2
			3、只从另外一个表取部分值：insert into 表1 (列名1,列名2,列名3) values(列1,列2,(select 列3 from 表2));
	5、引起索引失效，可以使用explain select * from sys_document where docid like '1';语法查看索引是否实现
		1．隐式转换导致索引失效tu_mdn为varchar2(20),但在查询时把该字段作为number类型以where条件传给Oracle,这样会导致索引失效.
			 错误的例子：select * from test where tu_mdn=13333333333;
			 正确的例子：select * from test where tu_mdn='13333333333'
		2. 对索引列进行运算导致索引失效,我所指的对索引列进行运算包括(+，-，*，/，! 等)
			 错误的例子：select * from test where id-1=9;
			 正确的例子：select * from test where id=10;
		3. 使用Oracle内部函数导致索引失效.对于这样情况应当创建基于函数的索引.
			 错误的例子：select * from test where round(id)=10; 说明，此时id的索引已经不起作用了
			 正确的例子：首先建立函数索引，create index test_id_fbi_idx on test(round(id));然后 select * from test where round(id)=10; 这时函数索引起作用了
		4. 以下使用会使索引失效，应避免使用；
			 a. 使用 <> 、not in 、not exist、!=
			 b. like "%_" 百分号在前（可采用在建立索引时用reverse(columnName)这种方法处理）
			 c. 单独引用复合索引里非第一位置的索引列.应总是使用索引的第一个列，如果索引是建立在多个列上, 只有在它的第一个列被where子句引用时，优化器才会选择使用该索引。
			 d. 字符型字段为数字时在where条件里不添加引号.
	6、对sql的优化(总之一句就是不要全表扫描，要让索引起作用)
		1、首先应考虑在 where 及 order by 涉及的列上建立索引(避免全表扫描)。
		2、应尽量避免在 where 子句中对字段进行 null 值判断，否则将导致引擎放弃使用索引而进行全表扫描(也就是索引失效)，
		如：select id from t where num is null，可以在num上设置默认值0，确保表中num列没有null值，然后这样查询：	select id from t where num=0
		3、应尽量避免在 where 子句中使用!=或<>,like操作符，否则将引擎放弃使用索引而进行全表扫描。
		4、应尽量避免在 where 子句中使用 or 来连接条件，否则将导致引擎放弃使用索引而进行全表扫描，如：	
			select id from t where num=10 or num=20	
			可以这样查询：	
			select id from t where num=10	
			union all	
			select id from t where num=20
		5、in 和 not in 也要慎用，否则会导致全表扫描，如：	
			select id from t where num in(1,2,3)	
			对于连续的数值，能用 between 就不要用 in 了：	
			select id from t where num between 1 and 3
		6、很多时候用 exists 代替 in 是一个好的选择：	
			select num from a where num in(select num from b)	
			用下面的语句替换：	
			select num from a where exists(select 1 from b where num=a.num)	
		7、索引并不是越多越好，提高select的效率，但同时也降低了 insert 及 update 的效率，一个表最多不要超过6个
		8、任何地方都不要使用 select * from t ，用具体的字段列表代替“*”
	7、创建存储过程
		1、关键字：CREATE PROCEDURE
	8、什么是关系型数据库和非关系型数据库（acid和cap）
		1、关系型：依据关系模型来构建数据库，所谓的关系就是：一对一，一对多，多对多等，比如人和身份证1-1，老师和同学n-n
			安全（因为存储在磁盘中，不会说突然断电数据就没有了）、
			容易理解（建立在关系模型上）、
			但不节省空间（因为建立在关系模型上，就要遵循某些规则，好比数据中某字段值即使为空仍要分配空间）
			有约束条件（比如在插入子表记录时必须要有在父表有对应的记录，数据格式的约束等等）
		2、非关系型：主要是基于"非关模型"的数据库
			1、列模型：存储的数据是一列列的（数据即索引io操作很快），比如Hbase
			2、键值对模型：存储的数据是一个个“键值对”，比如redis，MemcacheDB
			3、文档型的：以文档格式来存储数据比如mongodb
			4、非关系型数据库特点：
				效率高：因为存储在内存中，
				不安全：断电数据会丢失，现在很多都开始支持数据持久化了，像redis的rdb和aof
	9、redis，mongodb适用场景
		1、redis他是内存k-v的数据库，适合高频访问的数据，比如淘宝的关键字
		2、mongodb他是文档型分布式数据库，他可以分片就行微服务那样把一个库分成多个小库，通过路由去实现负载，
			MongoDB更侧重高数据写入性能，而非事务安全，高可用复制集
	10、oracle有几种索引
		1、B-tree索引(默认的)
		2、位图索引
		3、基于函数的索引(有一些字段查询时带函数的，这是就有意义了)
		4、hash索引
		5、反向索引
		6、分区索引和全局索引
	11、交叉用sql怎么实现
	12、修改字段
		1、 mysql : alter table bxkc_operation_log modify content longtext null comment '日志内容';
		2、 oracle 新增字段 : alter table test1 add (name varchar2(30) default ‘无名氏’ not null);
