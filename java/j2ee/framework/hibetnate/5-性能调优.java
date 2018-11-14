










1、性能优化
	1、注意session.clear()的运用，尤其在不断分页循环的时候  // 面试题：Java有内存泄漏吗？语法级别没有 但是可由java引起,例如:连接池不关闭,或io读取后不关闭
		1、在一个大集合中进行遍历，遍历msg，取出其中的含有敏感字样的对象
		2、另外一种形式的内存泄露
	2、1+N问题 比如十个Topic对应十个Category，你发一条hql="from Topic"，他会一条查Topic的sql，然后再发十条sql去查对应的Category查询，下列四种解决方案具体用哪一种看情况
		1、@ManyToOne(fetch=FetchType.LAZY)  // 只有当需要时(如:t.getCategory().getName()时)才会去获取关联表中数据 可以缓解1+N问题
		2、@BatchSize // 在与查询表(此例中为Topic类)关联的表类(此例中为Category类)头处加@BatchSize(size=5)
		3、join fetch // 修改hql语句为--"  from Topic t left join fetch t.category c  "
		4、QBC // 使用QBC的 createCriteria(*.class)执行查询 也可避免N+1问题其实底层就是第3中解决方案
	3、list和iterate不同之处
		1、list取所有
		2、iterate先取 ID,等用到的时候再根据ID来取对象
		3、session中list第二次发出，仍会到数据库査询
		4、iterate 第二次，首先找session 级缓存
	4、缓存(有空去看看sql会不会使用缓存机制)
		1、session缓存（一级缓存）
		   1、当（save/update/date/load/get/query.list/）会往缓存放入opjo对象
		   2、当（load/get）会从缓存取，这里注意：query.list等方法是雷锋只放不取因为他是语句查询条件不确定，但是他可以用查询级缓存(3级缓存)
		   3、session的生命周期：（evict/clear/关闭session就会结束）
		2、常见的二级第三方有hashtable/occache/ehcache等等（作用域是sessionFactory，他可以放在内存和磁盘），改动少，经常被访问(用户的权限，角色)才适合使用二级缓存机制
			1、请看项目：Hibernate332_0004_SecondCache_msb
			2、加入：optional/ehcache/ehcache-1.2.3.jar（ehcache炸包）、commons-logging-1.1.1.jar（ehcache日志框架炸包）
			3、去hibernate.cfg.xml启用
			4、把ect/ehche.xml文件拷贝到src目录
			5、去你想要做成二级缓存的实体类加注解
			6、缓存算法：（纯为了面试）ehcache.xml中配置memoryStoreEvictionPolicy = "LRU" ()
				1、Least Recently Used –最近很少被使用
				2、Least Frequently Used (命中率高低)
				3、First In First Out 按顺序替换
			7、如果想启动查询缓存(3级缓存)，一般不会用词缓存（作用域大于等于sessionFactory）
				1、使用背景：同样重复的查询执行N此会发送N次请求，使用了查询缓存，相同的查询只会发一次，注意一点，有交集也不算相同
				2、去hibernate.cfg.xml启用<property name="cache.use_query_cache">true</property>即可(注意此缓存是在二级缓存正常启用的情况下才可以生效)





