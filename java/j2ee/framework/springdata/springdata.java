1、加入jar包
2、applicationcontext.xml
	1、自动扫描的包
	2、数据源
	3、jap的EntityManagerFactory
	4、配置事务管理类
	5、配置支持注解的事物
	6、配置SpringData
3、db.properties
4、测试jap的EntityManagerFactory，创建一个Person实体类,直接启动spring容器既可以。看看数据库有没有生成表
5、创建ProsonRepository(两种方式)
	1、继承Repository
	2、@RepositoryDefinition
6、在ProsonRepository定义方法的规范，或者使用注解直接写Hql或者sql
	1、查询：直接使用规范的方法，或者使用@Query
	2、更新：直接使用规范的方法，或者使用@Query+@Modifying(注意这个需要事务，不支持insert语句)
7、JpaRepository->>PagingAndSortingRepository->>CrudRepository->>Repository
	1、方法规范
		1、QiYeZiZhi findFirstByCompanyName(String companyName);	// 根据companyName获取第一条记录


8、JpaSpecificationExecutor接口(可以查询带查询条件的分页)
