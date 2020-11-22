




1、mybatis plus：MyBatis-Plus(简称MP)是一个MyBatis的增强工具，提供给我们很多实用的插件。在Mybatis的基础上只做增强不做改变，为简化我们开发，提高工作效率。
2、安装
	<!-- mybatisPlus 会自动的维护Mybatis 以及MyBatis-spring整合包的 相关的依赖-->
	<dependency>
		<groupId>com.baomidou</groupId>
		<artifactId>mybatis-plus</artifactId>
		<version>2.3.2</version>
	</dependency>
3、使用
	1、mybatis直接和spring整合的
		1、spring的配置文件
			<!--  配置 SqlSessionFactoryBean 
				Mybatis提供的: org.mybatis.spring.SqlSessionFactoryBean
				MP提供的:com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean
			 -->
			<bean id="sqlSessionFactoryBean" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
				<!-- 数据源 -->
				<property name="dataSource" ref="dataSource"></property>
				<property name="configLocation" value="classpath:mybatis-config.xml"></property>
				<!-- 别名处理 -->
				<property name="typeAliasesPackage" value="com.atguigu.mp.beans"></property>		
				
				<!-- 注入全局MP策略配置 -->
				<property name="globalConfig" ref="globalConfiguration"></property>
			</bean>

			<!-- 定义MybatisPlus的全局策略配置就相当于mybatis的全局配置文件了-->
			<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
				<!-- 自动和db下划线命名做映射，如果你数据库是驼峰反而会报错了（你可以使用 @TableField("companyId") 解决报错，看谁少，少的就写 @TableField 注解 ），在2.3版本以后，dbColumnUnderline 默认值就是true -->
				<property name="dbColumnUnderline" value="true"></property>
				<!-- 全局的主键策略0代表自增，这样配置，其他如果是自增的就不用配置(@TableId(value="id" , type =IdType.AUTO)) -->
				<property name="idType" value="0"></property>
				<!-- 全局的表前缀策略配置，这样配置，他表如果加了tb1_前缀的不用配置(@TableName(value="tbl_employee")) -->
				<property name="tablePrefix" value="tbl_"></property>
			</bean>
		2、创建对应Mapper接口要继承 BaseMapper 接口：@Mapper public interface EmployeeMapper extends BaseMapper<Employee>{} ， 
			继承了他之后如果是单表的操作你甚至不用写xml文件了，你也可以写xml文件，写一些多表关联的sql，可以考虑多建一个VO为之服务
			但是这个xml和mapper.java是怎么关联的呢？
				1、mapper.xml和mapper.java
					1、 springboot 主配置文件使用 mybatis-plus.mapper-locations='classpath*:mapper/*.xml' 
					2、 然后对应xml的命名空间 namespace 写成对应的mapper.java
		3、实体类的处理
			1、@TableName(value="tbl_employee")			// 映射表名，如果表名和pojo名一样，可以不用写
			2、@TableId(value="id" , type =IdType.AUTO)	// 映射id，value表示字段名称，type表示id生成策略，如果在全局配置写了，这里可以不用写
			3、@TableField(value = "last_name")			// 其他字段名映射，如果名字不一样可以通过value指定，也可以解决了下划线命名自动映射
			4、@TableField(exist=false)					// 指定此字段不是数据库的
		4、通用的crud操作
			1、插入操作
				1、Integer insert(T e)	// 插入不为空的字段，插入之后主键的获取，原生mybatis要做特殊的配置，mybatisplus默认就有的，返回影响的条数
				2、Integer insertAllColumn(T e)	// 插入全部字段
			2、更新操作
				1、Integer updateById(@Param("et") T e);	//更新不为空的字段，返回影响条数
				2、Integer updateAllColumnById(@Param("et") T e);	//更新不为空的字段，返回影响条数
			3、查询操作
				1、T selectById(Serializable id);	// 根据id查找记录
				2、T selectOne(@Param("ew" T e));	// 查询一条记录，如果多条会报错
				3、List<T> selectBatchIds(List<? extends Serializable> id idList);	// 根据id集合获取记录
				4、List<T> selectByMap(@param("cm") Map<String, Object> columnMap);	// 根据Map<列名->值>获取记录，列名一定要和数据库一样
				5、List<T> selectByPage(RowBounds rowBounds, @param("ew") Wrapper<T> wrapper)	// 要在 SessionFactory 配置分页插件(推荐PageHelper插件，或者mybatisplus自己的)才会生效的
					List<Employee> emps =employeeMapper.	(
					new Page<Employee>(1, 2),
					new EntityWrapper<Employee>().between("age", 18, 50).eq("gender", 1).eq("last_name", "Tom")
					);
			4、删除操作
				1、Integer deleteById(Serializable id);
				2、Integer deleteByMap(@Param("cm") Map<String, Object> columnMap);
				3、Integer deleteBatchIds(List<? extends Serializable> idList);
		5、sql自动注入原理：AutoSqlInjector
			Configuration	// mybatis或者MP的全局配置文件对象
			MapperStatement	// 对应一个[增删改查]标签
			SqlMethod		// MP支持的sql枚举类
			SqlSource				// sql语句处理对象
			MapperBuilderAssistant	// 用于缓存，sql参数，查询方法结果处理等，最后创建MapperStatement并且放入configuration
		6、条件构造器EntityWrapper<>和Condition他俩都是继承Wrapper的，使用起来差不多，参考：https://mp.baomidou.com/guide/wrapper.html
			1、EntityWrapper
				1、eq、like、or、orNew方法
					new EntityWrapper<Employee>()
					.eq("gender", 0)
					.like("last_name", "老师")
					//.or()    // SQL: (gender = ? AND last_name LIKE ? OR email LIKE ?)    
					.orNew()   // SQL: (gender = ? AND last_name LIKE ?) OR (email LIKE ?) 
					.like("email", "a")
				2、between、orderBy、orderDesc、last方法
					new EntityWrapper<Employee>()
					.eq("gender", 0)
					.between("age", 18, 50)
					.orderBy("age")		//默认是asc 可以使用.orderDesc(Arrays.asList(new String [] {"age"}))
					.last("desc limit 1,3")		// 给sql末尾拼接sql子句，注意sql注入
			2、Condition把new EntityWrapper<Employee>()换成Condition.create()，其他一样的
		7、 对应的service 实现类 @Service public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {}	// 有了他之后，也可以直接使用他给我写好的一些方法，比如 save saveBatch
		
		7、ActiveRecord(AR)他是在实体类中继承Model<>抽象类，然后就可以使用此实体类型对象直接操作数据库了，不需要dao，我个人不推荐使用，直接使用dao的比较好，有空再去看吧
		8、MP的自动生成代码
			1、加入依赖
				<!-- Apache velocity -->
				<dependency>
					<groupId>org.apache.velocity</groupId>
					<artifactId>velocity-engine-core</artifactId>
					<version>2.0</version>
				</dependency>
				
				<!-- sfl4j -->
				 <dependency>
					<groupId>org.slf4j</groupId>
					<artifactId>slf4j-api</artifactId>
					<version>1.7.7</version>
				</dependency>
				<dependency>
					<groupId>org.slf4j</groupId>
					<artifactId>slf4j-log4j12</artifactId>
					<version>1.7.7</version>
				</dependency>

				<!--springmvc-->
				<dependency>
					<groupId>org.springframework</groupId>
					<artifactId>spring-webmvc</artifactId>
					<version>4.3.10.RELEASE</version>
				</dependency>
			2、java代码
				public void  testGenerator() {
					//1. 全局配置
					GlobalConfig config = new GlobalConfig();
					config.setActiveRecord(true) // 是否支持AR模式
						  .setAuthor("weiyunhui") // 作者
						  .setOutputDir("D:\\workspace_mp\\mp03\\src\\main\\java") // 生成路径
						  .setFileOverride(true)  // 文件覆盖
						  .setIdType(IdType.AUTO) // 主键策略
						  .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
											   // IEmployeeService
						  .setBaseResultMap(true)
						  .setBaseColumnList(true);
					
					//2. 数据源配置
					DataSourceConfig  dsConfig  = new DataSourceConfig();
					dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
							.setDriverName("com.mysql.jdbc.Driver")
							.setUrl("jdbc:mysql://localhost:3306/mp")
							.setUsername("root")
							.setPassword("1234");
					 
					//3. 策略配置
					StrategyConfig stConfig = new StrategyConfig();
					stConfig.setCapitalMode(true) //全局大写命名
							.setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
							.setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
							.setTablePrefix("tbl_")
							.setInclude("tbl_employee");  // 生成的表
					
					//4. 包名策略配置 
					PackageConfig pkConfig = new PackageConfig();
					pkConfig.setParent("com.atguigu.mp")
							.setMapper("mapper")
							.setService("service")
							.setController("controller")
							.setEntity("beans")
							.setXml("mapper");
					
					//5. 整合配置
					AutoGenerator  ag = new AutoGenerator();
					
					ag.setGlobalConfig(config)
					  .setDataSource(dsConfig)
					  .setStrategy(stConfig)
					  .setPackageInfo(pkConfig);
					
					//6. 执行
					ag.execute();
				}
		9、MP插件
			1、分页插件
				1、相当于在主配置文件加入插件(PaginationInterceptor有空去看里边的代码)
					1、xml配置方式
						<bean id="sqlSessionFactoryBean" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
							<!-- 插件注册 -->
							<property name="plugins">
								<list>
									<!-- 注册分页插件 -->
									<bean class="com.baomidou.mybatisplus.plugins.PaginationInterceptor"></bean>
									<!-- 注册执行分析插件，MySQL5.6.3以上才可以的，这个插件没什么用 -->
									<bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
										<property name="stopProceed" value="true"></property>
									</bean>
									<!-- 注册性能分析插件 -->
									<bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
										<property name="format" value="true"></property>
										<!-- <property name="maxTime" value="5"></property> -->
									</bean>
									<!-- 注册乐观锁插件 -->
									<bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor">
									</bean>
								</list>
							</property>
						</bean>
					2、java代码配置
						@Bean("mysqlSqlSessionFactory")
						public MybatisSqlSessionFactoryBean mysqlSqlSessiongFactory() throws Exception {
							MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();

							// 1、设置数据源
							mybatisSqlSessionFactoryBean.setDataSource(shardingTableDataSource());

							// 2、设置mybatis配置文件
							Resource mybatisConfig = new ClassPathResource("mybatis-config.xml");
							mybatisSqlSessionFactoryBean.setConfigLocation(mybatisConfig);

							// 3、分页插件
							mybatisSqlSessionFactoryBean.setPlugins(new Interceptor[]{
									paginationInterceptor()
							});

							return mybatisSqlSessionFactoryBean;
						}
						@Bean
						public PaginationInterceptor paginationInterceptor(){
							return new PaginationInterceptor();
						}
				2、java代码的使用
					@Test
					public void testPage() {
						Page<Employee> page = new Page<>(1,1);


						List<Employee > emps = 
								employeeMapper.selectPage(page, null);
						System.out.println(emps);

						System.out.println("===============获取分页相关的一些信息======================");
						System.out.println("总条数:" +page.getTotal());
						System.out.println("当前页码: "+  page.getCurrent());
						System.out.println("总页码:" + page.getPages());
						System.out.println("每页显示的条数:" + page.getSize());
						System.out.println("是否有上一页: " + page.hasPrevious());
						System.out.println("是否有下一页: " + page.hasNext());

						page.setRecords(emps);	//将查询的结果封装到page对象中,直接给前台返回

					}
			2、全表操作sql的终止插件(开发环境使用)
				1、在全局配置文件注册即可
					<!-- 注册执行分析插件，MySQL5.6.3以上才可以的，这个插件没什么用 -->
					<bean class="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
						<property name="stopProceed" value="true"></property>
					</bean>
			3、sql性能分析插件(开发环境使用)
				1、在全局配置文件注册即可
					<!-- 注册性能分析插件 -->
					<bean class="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
						<property name="format" value="true"></property>
						<!-- <property name="maxTime" value="5"></property> -->
					</bean>
			4、乐观锁插件
				1、在全局配置文件注册
					<!-- 注册乐观锁插件 -->
					<bean class="com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor">
					</bean>
				2、每个实体类和数据表要加一个version字段，并且加上@Version注解
				3、模拟；先读出数据，然后更新(在更新之前有另外的线程改了这行数据)，然后会报错
		10、自定义全局操作(解决不想增加方法，然后在xml写对应的sql，不推荐，推荐自己在xml写)
			1、编写AutoSqlInjector类
				public class MySqlInjector  extends AutoSqlInjector{
					/**
					 * 扩展inject 方法，完成自定义全局操作
					 */
					@Override
					public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass,
							Class<?> modelClass, TableInfo table) {
						//将EmployeeMapper中定义的deleteAll， 处理成对应的MappedStatement对象，加入到configuration对象中。
						
						//注入的SQL语句
						String sql = "delete from " +table.getTableName();
						//注入的方法名   一定要与EmployeeMapper接口中的方法名一致
						String method = "deleteAll" ;
						
						//构造SqlSource对象
						SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
						
						//构造一个删除的MappedStatement
						this.addDeleteMappedStatement(mapperClass, method, sqlSource);
						
					}
				}
			2、在spring.xml配置如下
				<!-- 定义自定义注入器 -->
				<bean id="mySqlInjector" class="com.atguigu.mp.injector.MySqlInjector"></bean>
				<!-- 定义MybatisPlus的全局策略配置，注入自定义全局操作-->
				<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
					<property name="sqlInjector" ref="mySqlInjector"></property>
				</bean>
		11、逻辑删除用的时候再去看(原理就是加一个逻辑删除字段 -1代表删除 1代表未删除)
			1、在spring.xml配置
				<!-- 逻辑删除 -->
				<bean id="logicSqlInjector" class="com.baomidou.mybatisplus.mapper.LogicSqlInjector"></bean>
				<!-- 定义MybatisPlus的全局策略配置，注入自定义全局操作-->
				<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
					<property name="sqlInjector" ref="logicSqlInjector"></property>
					<!-- 注入逻辑删除全局值 -->
					<property name="logicDeleteValue" value = "-1"></property>
					<property name="logicNotDeleteValue" value="1"></property>
				</bean>
			2、在实体类的逻辑删除字段写上注解
				@TableLogic   // 逻辑删除属性
				private Integer logicFlag;
		12、公共字段自动填充(这个是可能可以实现日期类型、枚举等的转换，应该不行，要去数据、类型转换)
			1、继承MetaObjectHandler类，实现插入方法和更新方法时字段的填充
				public class MyMetaObjectHandler extends MetaObjectHandler {
					/**
					 * 插入操作 自动填充
					 */
					@Override
					public void insertFill(MetaObject metaObject) {
						//获取到需要被填充的字段的值
						Object fieldValue = getFieldValByName("name", metaObject);
						if(fieldValue == null) {
							System.out.println("*******插入操作 满足填充条件*********");
							setFieldValByName("name", "weiyunhui", metaObject);
						}
						
					}
					/**
					 * 修改操作 自动填充
					 */
					@Override
					public void updateFill(MetaObject metaObject) {
						Object fieldValue = getFieldValByName("name", metaObject);
						if(fieldValue == null) {
							System.out.println("*******修改操作 满足填充条件*********");
							setFieldValByName("name", "weiyh", metaObject);
						}
					}
				}
			2、spring.xml的配置
				<!-- 公共字段填充 处理器 -->
				<bean id="myMetaObjectHandler" class="com.atguigu.mp.metaObjectHandler.MyMetaObjectHandler"> </bean>
				<!-- 定义MybatisPlus的全局策略配置，注入自定义全局操作-->
				<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
					<property name="metaObjectHandler" ref="myMetaObjectHandler"></property>
				</bean>
			3、在实体类的需要用到自动注入的属性加注解
				@TableField(fill=FieldFill.INSERT_UPDATE)
				private String name;
		13、解决oracle数据库主键不能自动自增的问题
			1、spring.xml的配置
				<!-- 配置Oracle主键Sequence -->
				<bean id="oracleKeyGenerator" class="com.baomidou.mybatisplus.incrementer.OracleKeyGenerator"></bean>
				<!-- 定义MybatisPlus的全局策略配置，注入自定义全局操作-->
				<bean id ="globalConfiguration" class="com.baomidou.mybatisplus.entity.GlobalConfiguration">
					<!-- 注入Oracle主键Sequence -->
	 				<property name="keyGenerator" ref="oracleKeyGenerator"></property>
				</bean>
			2、在实体类中使用对应序列(如果多个实体类使用同一个序列，可以使用一个父类)
				@KeySequence(value="数据库中序列名称",clazz=Integer.class)
				public class User {}
			3、id的策略要使用IdType.INPUT
				@TableId(type=IdType.INPUT)	// 如果全部一致，可以全局配置文件配置(idType)id生成策略
				private Integer id  ;
		14、mybatisX 的 idea快速快发插件(他可以把我们的mapper.java和mapper.xml互相连接，方便查找)
			1、安装
				1、在线安装：File -> Setting -> Plugins -> Browse Repositories... 输入mybatisx -> 点击安装下载重启即可
				2、本地安装：File -> Setting -> Plugins -> Install plugin from disk... 选中安装的jar文件 -> 点击安装重启即可










