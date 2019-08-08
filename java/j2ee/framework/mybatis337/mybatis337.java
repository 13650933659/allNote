















1、什么是mybatis
	1、他的前身是ibatis，之后ibatis团队转到google就变成mybatis(3.0以后都叫mybatis)，他轻量级的半orm框架
	2、官网：https://github.com/mybatis/mybatis-3
2、安装
	1、maven(下面是最基本的支持，其他支持请自己找相关依赖)
		<dependency>
		  <groupId>org.mybatis</groupId>
		  <artifactId>mybatis</artifactId>
		  <version>x.x.x</version>
		</dependency>
3、使用
	1、开发方法
		1、原始的使用方法(已经很少用了，为了老的项目学一下)
			1、创建config/sqlSessionFactory.xml和config/sqlmap/User.xml文件，在User.xml上面写上命名空间User
			2、java代码在使用时
				String resource = "sqlSessionFactory.xml";
				InputStream is = Resources.getResourceAsStream(resource);
				SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(is);
				
				SqlSession session = ssf.openSession();
				User user = session.selectOne("User.findUserById", 1);
				// 如果是增删改需要session.commit();
				session.close();
		2、Mapper开发(推荐使用xml，改sql不要编译，而且复杂的sql写比注解的方便一点)
			1、接口->xml<select><insert>等标签
				1、基本处理
					1、id: 称为statement的id
					2、#{id}：表示一个占位符号，可能有中文乱码问题，如果输入参数是简单类型，#{}中的参数名可以任意
					3、${value}：接收输入参数的内容，如果传入类型是简单类型，${}中只能使用value，注意：有sql注入的问题，一般使用在原生sql不支持占位符的
					4、 parameterType(输入参数，好像这个可以不用写)
						1、例子：#{property,javaType=int,jdbcType=NUMERIC}或者#{height,javaType=double,javaType=NUMERIC,numericScale=2}
							1、 javaType ：可以从参数对象中来确定
							2、 jdbcType ：当参数值为空时默认的值，<setting name="jdbcTypeForNull" value="在这里改是全局，如果你去对应的#{}写JdbcType=OTHER就只影响自己(默认为OTHER，但是这个oracle不认识)"></setting>
							3、 numericScale ：保留的小数
							4、mode: 待看？？？
						2、一个参数的处理
							1、一般是：基本类型、pojo、Map类型，直接取值，或者通过OGNL取
						3、多个参数处理(java8和mybatis341可以考虑使用 useActualParamName 配置)
							1、使用注解
								Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
								<select id="getEmpByIdAndLastName" resultType="com.atguigu.mybatis.bean.Employee">
									select * from tbl_employee where id = #{id} and last_name=#{lastName}
								</select>
							2、使用硬编码 #{param1},#{param2}...
								Employee getEmpByIdAndLastName(Integer id, String lastName);
								<select id="getEmpByIdAndLastName" resultType="com.atguigu.mybatis.bean.Employee">
									select * from tbl_employee where id = #{param1} and last_name=#{param2}
								</select>
					5、 resultType(输出参数)
						1、pojo和List<pojo>
							Employee getById(Integer id); 或者 List<Employee> findById(Integer id);
							<select id="getEmpById" resultType="com.atguigu.mybatis.bean.Employee">
								select * from tbl_employee where id = #{id}
							</select>
						2、Map<field,value>和Map<field,record>
							1、Map<field,value>
								Map<String, Object> getEmpByIdReturnMap(Integer id);
								<select id="getEmpByIdReturnMap" resultType="map">
									select * from tbl_employee where id=#{id}
								</select>
							2、Map<field,record>
								@MapKey("id")
								Map<Integer, Employee> getEmpByLastNameLikeReturnMap(String lastName);
								<select id="getEmpByLastNameLikeReturnMap" resultType="com.atguigu.mybatis.bean.Employee">
									select * from tbl_employee where last_name like #{lastName}
								</select>
					6、 resultMap(输出参数的映射，用了这个就不用 resultType 了，用了这个字段和属性不对应也不用在sql写别名了)
						1、单个pojo的映射
							<resultMap type="com.po.User" id="userResultMap">
								<!-- 
									id表示查询结果集中唯一标识 ，result对普通名映射定义，字段和属性对应的可以不用写，这里好像没写数据类型
										column		查询出来的列名
										property	type指定的pojo类型中的属性名
								-->
								<id column="id_" property="id"/>
								<result column="username_" property="username"/>
							 </resultMap>
						2、关联pojo的映射
							1、父pojo(订单 -> 用户)
								<resultMap type="com.po.Orders" id="OrdersUserResultMap">
									<id column="id" property="id"/>
									<result column="user_id" property="userId"/>
									<result column="note" property="note"/>
									
									<!-- 配置映射的关联的用户信息 -->
									<!-- association：用于映射关联查询单个对象的信息
											property：要将关联查询的用户信息映射到Orders中哪个属性
									 -->
									<association property="user" javaType="com.po.User">
										<id column="user_id" property="id"/>
										<result column="username" property="username"/>
									</association>
								</resultMap>
							2、子pojo(订单 -> 订单明细)
								<resultMap type="com.po.Orders" id="OrdersAndOrderDetailResultMap" extends="OrdersUserResultMap">
									 <collection property="orderdetails" ofType="com.po.Orderdetail">
										<id column="orderdetail_id" property="id"/>
										<result column="items_id" property="itemsId"/>
										<result column="items_num" property="itemsNum"/>
										<result column="orders_id" property="ordersId"/>
									 </collection>
								</resultMap>
						3、关联对象的懒加载
							1、父pojo的懒加载
								<resultMap type="com.po.Orders" id="OrdersUserLazyLoadingResultMap">
									<id column="id" property="id"/>
									<result column="user_id" property="userId"/>
									<result column="number" property="number"/>
									<result column="note" property="note"/>
									<!-- 
									1、全局配置lazyLoadingEnabled，aggressiveLazyLoading如果没配置，默认是积极加载的
									2、配置了上面两个属性，则默认是懒加载的，如果要积极使用fetchType="eager"
									3、column="user_id"是传的参数，但是多列可以使用：column="{id=值,name=值}"-->
									<association property="user" javaType="com.po.User" select="com.mapper.UserMapper.findUserById" column="user_id">
									</association>
								</resultMap>
							2、值pojo的懒加载
								<resultMap type="com.po.Orders" id="OrdersAndOrderDetailResultMap" extends="OrdersUserResultMap">
									 <collection property="orderdetails" ofType="com.po.Orderdetail"  select="com.mapper.OrderItemMapper.findItemByOrderId" column="id">
										<id column="orderdetail_id" property="id"/>
										<result column="items_id" property="itemsId"/>
										<result column="items_num" property="itemsNum"/>
										<result column="orders_id" property="ordersId"/>
									 </collection>
								</resultMap>
						4、discriminator(鉴别器)
							<!-- <discriminator javaType=""></discriminator>
								鉴别器：mybatis可以使用discriminator判断某列的值，然后根据某列的值改变封装行为
								封装Employee：
									如果查出的是女生：就把部门信息查询出来，否则不查询；
									如果是男生，把last_name这一列的值赋值给email;
							 -->
							 <resultMap type="com.atguigu.mybatis.bean.Employee" id="MyEmpDis">
								<id column="id" property="id"/>
								<result column="last_name" property="lastName"/>
								<result column="email" property="email"/>
								<result column="gender" property="gender"/>
								<!--
									column：指定判定的列名
									javaType：列值对应的java类型  -->
								<discriminator javaType="string" column="gender">
									<case value="0" resultType="com.atguigu.mybatis.bean.Employee">
										<association property="dept" 
											select="com.atguigu.mybatis.dao.DepartmentMapper.getDeptById"
											column="d_id">
										</association>
									</case>
									<case value="1" resultType="com.atguigu.mybatis.bean.Employee">
										<id column="id" property="id"/>
										<result column="last_name" property="lastName"/>
										<result column="last_name" property="email"/>
										<result column="gender" property="gender"/>
									</case>
								</discriminator>
							 </resultMap>
				2、动态sql
					1、<if>标签，可以参考下面的，<!-- test：判断表达式（OGNL）OGNL参照PPT或者官方文档，特殊符号要转义字符：比如&&-->
					2、<choose><when><otherwise>等标签，相当于if-else
						<select id="getEmpsByConditionChoose" resultType="com.atguigu.mybatis.bean.Employee">
							select * from tbl_employee 
							<where>
								
								<choose>
									<when test="id!=null">
										id=#{id}
									</when>
									<when test="lastName!=null">
										last_name like #{lastName}
									</when>
									<when test="email!=null">
										email = #{email}
									</when>
									<otherwise>
										gender = 0
									</otherwise>
								</choose>
							</where>
						 </select>
					3、<foreach>标签，批量是发了几条sql了
						1、批量查询
							<select id="getEmpsByConditionForeach" resultType="com.atguigu.mybatis.bean.Employee">
								select * from tbl_employee
								<!--
									collection：指定要遍历的集合，list类型的参数会特殊处理封装在map中，map的key就叫list
									item：将当前遍历出的元素赋值给指定的变量
									separator:每个元素之间的分隔符
									open：遍历出所有结果拼接一个开始的字符
									close:遍历出所有结果拼接一个结束的字符
									index:遍历list的时候是index就是索引，遍历map的时候index表示的就是map的key，item就是map的值
								  -->
								<foreach collection="ids" item="item_id" separator="," open="where id in(" close=")">
									#{item_id}
								</foreach>
							 </select>
						2、批量插入：void addEmps(@Param("emps")List<Employee> emps);
								1、MySQL批量保存
									1、第一种
										<insert id="addEmps">
											insert into tbl_employee(
												lastName,email,gender,dept_id
												<!--<include refid="insertColumn"></include>-->
											) 
											values
											<foreach collection="emps" item="emp" separator=",">
												(#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
											</foreach>
										 </insert>
									2、第二种这种方式需要数据库连接属性allowMultiQueries=true；而且这种分号分隔多个sql可以用于其他的批量操作（删除，修改）
										<insert id="addEmps">
											<foreach collection="emps" item="emp" separator=";">
												insert into tbl_employee(last_name,email,gender,d_id) values(#{emp.lastName},#{emp.email},#{emp.gender},#{emp.dept.id})
											</foreach>
										</insert>
								2、Oracle批量保存：注意Oracle不支持values(),(),()
									1、第一种：多个insert放在begin - end里面
										<insert id="addEmps" databaseId="oracle">
											<foreach collection="emps" item="emp" open="begin" close="end;">
												insert into employees(employee_id,last_name,email) values(employees_seq.nextval,#{emp.lastName},#{emp.email});
											</foreach>
										</insert>
									2、第二种：利用中间表
										<insert id="addEmps" databaseId="oracle">
											insert into employees(employee_id,last_name,email)
											<foreach collection="emps" item="emp" separator="union"
												open="select employees_seq.nextval,lastName,email from(" close=")">
												select #{emp.lastName} lastName,#{emp.email} email from dual
											</foreach>
										 </insert>
								3、但是2的批量不算是真正的批量(而且数据有可能不会接收那么长的sql)，所以另外的批量处理方式
									1、全局配置文件的defaultExecutorType，但是这样的话就会控制全部的执行器（不推荐）
									2、没有整合spring的开发
										SqlSession openSession = sqlSessionFactory.openSession(ExecutorType.BATCH);	// 选用批量执行的执行器
										long start = System.currentTimeMillis();
										try{
											EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
											for (int i = 0; i < 10000; i++) {
												mapper.addEmp(new Employee(UUID.randomUUID().toString().substring(0, 5), "b", "1"));
											}
											openSession.commit();
											long end = System.currentTimeMillis();
											//批量：预编译sql一次==>设置参数10000次===>执行1次==>4598s
											//非批量：（预编译sql=设置参数=执行）==>执行10000===>10200s
											System.out.println("执行时长："+(end-start));
										}finally{
											openSession.close();
										}
									3、和spring整合的
										1、配置一个可以进行批量执行的sqlSession
											<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
												<constructor-arg name="sqlSessionFactory" ref="sqlSessionFactoryBean"></constructor-arg>
												<constructor-arg name="executorType" value="BATCH"></constructor-arg>
											</bean>
										2、注入service，直接和上面一样的使用
					4、<where>标签
						<select id="getEmpsByConditionIf" resultType="com.atguigu.mybatis.bean.Employee">
							select * from tbl_employee
							<where>
								
								
								<if test="id!=null">
									id=#{id}
								</if>
								<if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
									and last_name like #{lastName}
								</if>
								<if test="email!=null and email.trim()!=&quot;&quot;">
									and email=#{email}
								</if> 
								<!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
								<if test="gender==0 or gender==1">
									and gender=#{gender}
								</if>
							</where>
						 </select>
					5、<set>标签
						<update id="updateEmp">
							<!-- Set标签的使用（会自动去掉后面多余的逗号） -->
							update tbl_employee 
							<set>
								<if test="lastName!=null">
									last_name=#{lastName},
								</if>
								<if test="email!=null">
									email=#{email},
								</if>
								<if test="gender!=null">
									gender=#{gender}
								</if>
							</set>
							where id=#{id} 
						 </update>
					6、<trim>标签
						<select id="getEmpsByConditionTrim" resultType="com.atguigu.mybatis.bean.Employee">
							select * from tbl_employee
							<!-- 后面多出的and或者or where标签不能解决 
							prefix="":给拼串后的整个字符串加一个前缀 
							prefixOverrides="": 前缀覆盖： 去掉整个字符串前面多余的字符
							suffix="": 给拼串后的整个字符串加一个后缀 
							suffixOverrides="": 后缀覆盖：去掉整个字符串后面多余的字符
							-->
							<!-- 自定义字符串的截取规则 -->
							<trim prefix="where" suffixOverrides="and">
								<if test="id!=null">
									id=#{id} and
								</if>
								<if test="lastName!=null &amp;&amp; lastName!=&quot;&quot;">
									last_name like #{lastName} and
								</if>
								<if test="email!=null and email.trim()!=&quot;&quot;">
									email=#{email} and
								</if> 
								<!-- ognl会进行字符串与数字的转换判断  "0"==0 -->
								<if test="gender==0 or gender==1">
									gender=#{gender}
								</if>
							 </trim>
						 </select>
					7、<bind>标签，解决模糊查询不想带百分号的，但是不推荐，还是推荐传的时候带上
						<select id="getEmpsTestInnerParameter" resultType="com.atguigu.mybatis.bean.Employee">
							<!-- bind：可以将OGNL表达式的值绑定到一个变量中，方便后来引用这个变量的值 -->
							<bind name="_lastName" value="'%'+lastName+'%'"/>
							select * from tbl_employee where last_name like #{_lastName}
						</select>
					8、<sql>和<include>标签
						<!-- 
							1、sql抽取：经常将要查询的列名，或者插入用的列名抽取出来方便引用
							2、include来引用已经抽取的sql：
							3、include还可以自定义一些property，sql标签内部就能使用自定义的属性
									include-property：取值的正确方式${prop},#{不能使用这种方式}
									<include refid="insertColumn">
										<property name="testColomn" value="abc"/>
									</include>
						  -->
						  <sql id="insertColumn">
								employee_id,last_name,email
						  </sql>
					9、我们也可以使用mybatis内置的两个参数：
						1、_parameter:代表整个参数
							单个参数：_parameter就是这个参数
							多个参数：参数会被封装为一个map；_parameter就是代表这个map
						2、_databaseId:如果配置了databaseIdProvider标签。
							_databaseId就是代表当前数据库的别名，比如oracle
			2、接口->注解@Select@Insert等注解
	2、缓存
		1、一级缓存sqlSession级别的缓存。mybatis的一级缓存（作用域=SqlSession），他的一级缓存就是查询的缓存，默认就是启用的；
		原理是SqlSession级别的一个Map，key = -924794387:565096923:com.mapper.UserMapper.findUserById:0:2147483647:SELECT * FROM USER WHERE id = ?:27
		从他的key可以看出来，不仅sql要相同，而且方法statement也要相同
	  		1、一级缓存失效情况（没有使用到当前一级缓存的情况，效果就是，还需要再向数据库发出查询）：
				1、sqlSession不同。
				2、sqlSession相同，查询条件不同.(当前一级缓存中还没有这个数据)
				3、sqlSession相同，两次查询之间对此表执行了增删改操作(因为这次增删改可能对当前数据有影响，但是其他线程可能的修改，他可能不知道)
				4、sqlSession相同，手动清除了一级缓存：openSession.clearCache();
		2、二级缓存：基于namespace级别的缓存：一个namespace对应一个二级缓存：key设计的和一级缓存一样，ehcache的key也是一样的
	  		1、注意：查出的数据都会被默认先放在一级缓存中。只有会话提交或者关闭以后，一级缓存中的数据才会转移到二级缓存中
	  		2、使用：
	  			1、开启全局二级缓存配置：<setting name="cacheEnabled" value="true"/>
	  			2、去对应的mapper.xml中配置使用二级缓存：
	  				<cache eviction="FIFO" flushInterval="60000" readOnly="false" size="1024"></cache>
						1、eviction:缓存的回收策略：
							• LRU – 最近最少使用的：移除最长时间不被使用的对象。
							• FIFO – 先进先出：按对象进入缓存的顺序来移除它们。
							• SOFT – 软引用：移除基于垃圾回收器状态和软引用规则的对象。
							• WEAK – 弱引用：更积极地移除基于垃圾收集器状态和弱引用规则的对象。
							• 默认的是 LRU。
						2、flushInterval：缓存刷新间隔
							缓存多长时间清空一次，默认不清空，设置一个毫秒值
						3、readOnly:是否只读：
							true：只读；mybatis认为所有从缓存中获取数据的操作都是只读操作，不会修改数据。
									 mybatis为了加快获取速度，直接就会将数据在缓存中的引用交给用户。不安全，速度快
							false：非只读：mybatis觉得获取的数据可能会被修改。
									mybatis会利用序列化&反序列的技术克隆一份新的数据给你。安全，速度慢
						4、size：缓存存放多少元素；
						5、type=""：指定自定义缓存的全类名；实现Cache接口即可；
	  			3、我们的POJO需要实现序列化接口：implements Serializable
		3、和缓存有关的设置/属性：
	  			1、全局-cacheEnabled=true：false：关闭缓存（二级缓存关闭）(一级缓存一直可用的)
	  			2、每个select标签都有useCache="true"：false：不使用缓存（一级缓存依然使用，二级缓存不使用）
	  			3、每个增删改标签的flushCache默认为true，查询标签为false，为true则清除一二级缓存
	  			4、sqlSession.clearCache();只是清楚当前session的一级缓存；
	  			5、全局-localCacheScope：一级缓存的作用域，默认为SESSION表示当前session，STATEMENT相当于禁用了V331+；
		4、第三方缓存整合（因为mybatis自己的二级缓存功能简陋，所以使用第三方的缓存）：
	 		1、选用ehcache
				1、导入第三方缓存包和日志包即可；ehcache-core-2.6.7.jar、slf4j-1.2.17.jar、slf4j-log4j12-1.6.2.jar
				2、导入与第三方缓存整合的适配包；官方有；mybatis-ehcache-1.0.3.jar
				3、在某mapper.xml中使用自定义缓存：<cache type="org.mybatis.caches.ehcache.EhcacheCache"></cache>
				4、引用缓存某个namespace的二级缓存：<cache-ref namespace="com.atguigu.mybatis.dao.EmployeeMapper"/>
				5、在类路径加入ehcache的配置文件：ehcache.xml，具体的参数说明请参考ehcache.xml
	3、事务
	4、逆向工程(代码生成器)
		1、使用java程序生成
			1、generatorConfig.xml请参考笔记
			2、maven依赖
				<!-- mybatis自动生成代码依赖 -->
				<dependency>
					<groupId>org.mybatis.generator</groupId>
					<artifactId>mybatis-generator-core</artifactId>
					<version>1.3.2</version>
				</dependency>
			2、java代码
				@Test
				public void generateMybatis() throws Exception{
					// 指定 逆向工程配置文件
					File configFile = new File("mybatisReverse/generatorConfig.xml"); 
					
					List<String> warnings = new ArrayList<String>();
					ConfigurationParser cp = new ConfigurationParser(warnings);
					Configuration config = cp.parseConfiguration(configFile);
					DefaultShellCallback callback = new DefaultShellCallback(true);
					MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
					myBatisGenerator.generate(null);
				}
		2、使用maven插件
				1、generatorConfig.xml请参考笔记
				2、pom.xml的maven插件匹配
					<plugin>
						<groupId>org.mybatis.generator</groupId>
						<artifactId>mybatis-generator-maven-plugin</artifactId>
						<version>1.3.6</version>
						<configuration>
							<verbose>true</verbose>
							<overwrite>true</overwrite>
						</configuration>
						<dependencies>
							<dependency>
								<groupId>mysql</groupId>
								<artifactId>mysql-connector-java</artifactId>
								<version>${mysql.driver.version}</version>
								<scope>runtime</scope>
							</dependency>
							<dependency>
								<groupId>com.oracle</groupId>
								<artifactId>ojdbc6</artifactId>
								<version>${oracle.driver.version}</version>
								<scope>runtime</scope>
							</dependency>
						</dependencies>
					</plugin>
				3、以idea为例：去Plugins/mybatis-generator右键运行，即可
	5、mybatis的运行原理
		1、五大对象
			1、Executor		// 做增删改查的
			2、StatementHandler(BaseStatementHandler)	// 控制（下面的3个对象的）最后还执行操作数据库
				this.parameterHandler = configuration.newParameterHandler(mappedStatement, parameterObject, boundSql);	// 设置预编译参数的
				this.resultSetHandler = configuration.newResultSetHandler(executor, mappedStatement, rowBounds, parameterHandler, resultHandler, boundSql);	// 处理数据库返回的结果
				TypeHandler	// 在整个过程进行数据库和javaBean的数据类型映射的
					TypeHandler.setParameter(ps,i,value,jdbcType)	// 设置预编译参数
					TypeHandler.getResult(rs, coulum)	// 映射结果的数据类型
		2、创建SqlSessionFactory对象（里面有刚刚创建的Configuration对象）
			new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("sqlMapConfig.xml"));
				SqlSessionFactoryBuilder.build(InputStream inputStream, String environment, Properties properties)
					XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);  // 创建sqlMapConfig.xml的XMLConfigBuilder对象
					parser.parse()  // 解析sqlMapConfig.xml文件
						parseConfiguration(parser.evalNode("/configuration"));  // 解析sqlMapConfig.xml文件的configuration标签
							mapperElement(root.evalNode("mappers"));	// 以解析mappers标签为例子，循环遍历每个子节点(package/mapper)，以mapper.resource="mapper/UserMapper.xml"为例
								XMLMapperBuilder mapperParser = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());	// 创建UserMapper.xml的XMLMapperBuilder对象
								mapperParser.parse();	// 解析UserMapper.xml文件
									configurationElement(parser.evalNode("/mapper"));  // 解析UserMapper.xml的mapper节点
										buildStatementFromContext(context.evalNodes("select|insert|update|delete"));  // 以mapper/增删改查标签为例，其他不管
											buildStatementFromContext(List<XNode> list, String requiredDatabaseId);		// 循环解析所有的增删改查标签
												final XMLStatementBuilder statementParser = new XMLStatementBuilder(configuration, builderAssistant, context, requiredDatabaseId);	// 创建XMLStatementBuilder对象
												statementParser.parseStatementNode();	// 解析Statement
													builderAssistant.addMappedStatement()	// 创建MappedStatement，创建之后放入configuration.addMappedStatement(statement);，到这里我们要知道每一个MappedStatement就是对应一个增删改查
			SqlSessionFactory build(Configuration config)		// 上面解析完sqlMapConfig.xml的全部属性封装到Configuration，然后return new DefaultSqlSessionFactory(config);
		3、创建SqlSession（里面有Configuration和Executor对象）
			sqlSessionFactory.openSession();	//
				SqlSession openSessionFromDataSource(ExecutorType execType, TransactionIsolationLevel level, boolean autoCommit)	// 
					final Executor executor = configuration.newExecutor(tx, execType);	// 看一下创建Executor的逻辑，根据execType创建对应的(BatchExecutor/ReuseExecutor/SimpleExecutor/CachingExecutor)
						executor = (Executor) interceptorChain.pluginAll(executor);		// 这句很重要，把我们的executor放入全部拦截器
				return new DefaultSqlSession(configuration, executor, autoCommit);	// 最后返回DefaultSqlSession
		4、创建mapper(里面有SqlSession对象)
			sqlSession.getMapper(UserMapper.class);
				configuration.<T>getMapper(type, this);
					mapperRegistry.getMapper(type, sqlSession);		// 调用MapperRegistry的getMapper方法
						final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);	// 先创建代理的工厂
						return mapperProxyFactory.newInstance(sqlSession);	// 在创建代理的实例
							final MapperProxy<T> mapperProxy = new MapperProxy<T>(sqlSession, mapperInterface, methodCache);	// MapperProxy<T>其实就是jdk的InvocationHandler
								return newInstance(mapperProxy);
									return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface }, mapperProxy);	// 最终返回代理的对象(其实是继承被代理对象的)
		5、执行mapper的增删改查(Executor -> )
			userMapper.findUserById(1);
				MapperProxy<T>.invoke(Object proxy, Method method, Object[] args)	// 会先调用代理对象的invoke方法，如果不是被代理对象的方法就跳过
					return mapperMethod.execute(sqlSession, args);	// 最后会调用execute方法
						Object param = method.convertArgsToSqlCommandParam(args);	// 解析参数
						result = sqlSession.selectOne(command.getName(), param);	// 根据方法的返回值调用sqlSession.selectOne，最后还是调用list返回一个
							List<T> list = this.<T>selectList(statement, parameter);
								MappedStatement ms = configuration.getMappedStatement(statement);	// 先拿到方法对应的MappedStatement
								return executor.query(ms, wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER);	// 使用Executor执行
									BoundSql boundSql = ms.getBoundSql(parameterObject);	// 获取sql对象
									CacheKey key = createCacheKey(ms, parameterObject, rowBounds, boundSql);	// 创建一二级缓存的key
									return query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
										Cache cache = ms.getCache();	// 先去二级缓存拿
											return delegate.<E> query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);	// 二级缓存拿不到，直接调用这个SimpleExecutor或者其他，看你的配置
												list = resultHandler == null ? (List<E>) localCache.getObject(key) : null;	// 先去一级缓存拿去
												list = queryFromDatabase(ms, parameter, rowBounds, resultHandler, key, boundSql);	// 拿不到就去查询
													list = doQuery(ms, parameter, rowBounds, resultHandler, boundSql);		// 查到之后放入缓存localCache.putObject(key, list);
														Configuration configuration = ms.getConfiguration();
														StatementHandler handler = configuration.newStatementHandler(wrapper, ms, parameter, rowBounds, resultHandler, boundSql);	// 创建StatementHandler对象，顺带创建另外两大对象
															StatementHandler statementHandler = new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
																通过ms.getStatementType()来创建(SimpleStatementHandler/PreparedStatementHandler/CallableStatementHandler)对象，对应标签的statementType属性
															statementHandler = (StatementHandler) interceptorChain.pluginAll(statementHandler);	// 放入拦截器连
														stmt = prepareStatement(handler, ms.getStatementLog());		// 就是预编译参数的设置
															Connection connection = getConnection(statementLog);
															stmt = handler.prepare(connection, transaction.getTimeout());
															handler.parameterize(stmt);
																delegate.parameterize(statement);	// 参数预编译
																	TypeHandler typeHandler = parameterMapping.getTypeHandler();
																	JdbcType jdbcType = parameterMapping.getJdbcType();
																	typeHandler.setParameter(ps, i + 1, value, jdbcType);	// 通过TypeHandler来预编译参数
														return handler.<E>query(stmt, resultHandler);	// 
															return delegate.<E>query(statement, resultHandler);	//委派到对应的statement去执行
																ps.execute();
																return resultSetHandler.<E> handleResultSets(ps);	// 
																	ResultSetWrapper rsw = getFirstResultSet(stmt);	// 这里会调用原生jdbc的请求方法ResultSet rs = stmt.getResultSet();，让包装成ResultSetWrapper
		6、总结
			/**
			 * 1、获取sqlSessionFactory对象:
			 * 		解析文件的每一个信息保存在Configuration中，返回包含Configuration的DefaultSqlSession；
			 * 		注意：【MappedStatement】：代表一个增删改查的详细信息
			 * 
			 * 2、获取sqlSession对象
			 * 		返回一个DefaultSQlSession对象，包含Executor和Configuration;
			 * 		这一步会创建Executor对象；
			 * 
			 * 3、获取接口的代理对象（MapperProxy）
			 * 		getMapper，使用MapperProxyFactory创建一个MapperProxy的代理对象
			 * 		代理对象里面包含了，DefaultSqlSession（Executor）
			 * 4、执行增删改查方法
			 * 
			 * 总结：
			 * 	1、根据配置文件（全局，sql映射）初始化出Configuration对象
			 * 	2、创建一个DefaultSqlSession对象，
			 * 		他里面包含Configuration以及
			 * 		Executor（根据全局配置文件中的defaultExecutorType创建出对应的Executor）
			 *  3、DefaultSqlSession.getMapper（）：拿到Mapper接口对应的MapperProxy；
			 *  4、MapperProxy里面有（DefaultSqlSession）；
			 *  5、执行增删改查方法：
			 *  		1）、调用DefaultSqlSession的增删改查（Executor）；
			 *  		2）、会创建一个StatementHandler对象。
			 *  			（同时也会创建出ParameterHandler和ResultSetHandler）
			 *  		3）、调用StatementHandler预编译参数以及设置参数值;
			 *  			使用ParameterHandler来给sql设置参数
			 *  		4）、调用StatementHandler的增删改查方法；
			 *  		5）、ResultSetHandler封装结果
			 *  注意：
			 *  	四大对象每个创建的时候都有一个interceptorChain.pluginAll(parameterHandler);
			 * 
			 * @throws IOException
			 */
	6、mybatis的插件
		1、插件原理
			/**
			 * 插件原理
			 * 在四大对象创建的时候
			 * 1、每个创建出来的对象不是直接返回的，而是
			 * 		interceptorChain.pluginAll(parameterHandler);
			 * 2、获取到所有的Interceptor（拦截器）（插件需要实现的接口）；
			 * 		调用interceptor.plugin(target);返回target包装后的对象
			 * 3、插件机制，我们可以使用插件为目标对象创建一个代理对象；AOP（面向切面）
			 * 		我们的插件可以为四大对象创建出代理对象；
			 * 		代理对象就可以拦截到四大对象的每一个执行；
			 * 
				public Object pluginAll(Object target) {
					for (Interceptor interceptor : interceptors) {
					  target = interceptor.plugin(target);
					}
					return target;
				  }
				
			 */
		2、插件编写：
			1、编写Interceptor的实现类（我们可以写多个，形成连接器链，就是层层代理了）
				/**
				 * 完成插件签名：
				 *		告诉MyBatis当前插件用来拦截哪个对象的哪个方法
				 */
				@Intercepts(
						{
							@Signature(type=StatementHandler.class,method="parameterize",args=java.sql.Statement.class)
						})
				public class MyFirstPlugin implements Interceptor{

					/**
					 * intercept：拦截：
					 * 		拦截目标对象的目标方法的执行；
					 */
					@Override
					public Object intercept(Invocation invocation) throws Throwable {
						// TODO Auto-generated method stub
						System.out.println("MyFirstPlugin...intercept:"+invocation.getMethod());
						//动态的改变一下sql运行的参数：以前1号员工，实际从数据库查询3号员工
						Object target = invocation.getTarget();
						System.out.println("当前拦截到的对象："+target);
						//拿到：StatementHandler==>ParameterHandler===>parameterObject
						//拿到target的元数据
						MetaObject metaObject = SystemMetaObject.forObject(target);
						Object value = metaObject.getValue("parameterHandler.parameterObject");
						System.out.println("sql语句用的参数是："+value);
						//修改完sql语句要用的参数
						metaObject.setValue("parameterHandler.parameterObject", 11);
						//执行目标方法
						Object proceed = invocation.proceed();
						//返回执行后的返回值
						return proceed;
					}

					/**
					 * plugin：
					 * 		包装目标对象的：包装：为目标对象创建一个代理对象，可以借助Plugin的wrap方法来使用当前Interceptor包装我们目标对象
					 */
					@Override
					public Object plugin(Object target) {
						System.out.println("MyFirstPlugin...plugin:mybatis将要包装的对象"+target);
						Object wrap = Plugin.wrap(target, this);
						return wrap;
					}

					/**
					 * setProperties：
					 * 		将插件注册时 的property属性设置进来
					 */
					@Override
					public void setProperties(Properties properties) {
						// TODO Auto-generated method stub
						System.out.println("插件配置的信息："+properties);
					}

				}

				// Plugin.wrap方法代碼如下，可以看出来Plugins是InvocationHandle对象，里边的invoke方法也回调interceptor.interceptor方法
				public static Object wrap(Object target, Interceptor interceptor) {
				Map<Class<?>, Set<Method>> signatureMap = getSignatureMap(interceptor);
				Class<?> type = target.getClass();
				Class<?>[] interfaces = getAllInterfaces(type, signatureMap);
				if (interfaces.length > 0) {
					  return Proxy.newProxyInstance(
						  type.getClassLoader(),
						  interfaces,
						  new Plugin(target, interceptor, signatureMap));
					}
					return target;
				 }
			2、将写好的插件注册到全局配置文件中
				<!--plugins：注册插件  -->
				<plugins>
					<plugin interceptor="com.atguigu.mybatis.dao.MyFirstPlugin">
						<property name="username" value="root"/>
						<property name="password" value="123456"/>
					</plugin>
					<plugin interceptor="com.atguigu.mybatis.dao.MySecondPlugin"></plugin>
				</plugins>
		3、分页插件
			1、github项目：https://github.com/pagehelper/Mybatis-PageHelper		// 使用方法也是在里面
			2、使用	// 这个是真正的分页，靠的是 PageHelper.startPage(3, 2);
				1、引入依赖
					<dependency>
						<groupId>com.github.pagehelper</groupId>
						<artifactId>pagehelper</artifactId>
						<version>最新版本</version>
					</dependency>
				2、以mybatis全局配置文件为例(spring和springboot自己看文档)
					<plugins>
						<plugin interceptor="com.github.pagehelper.PageInterceptor"></plugin>
					</plugins>
				3、代码使用
					// 创建UserMapper对象，mybatis自动生成mapper代理对象
					UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
					Page<Object> page = PageHelper.startPage(3, 2);
					List<User> list = userMapper.findAllUser();

					// 使用Page取分页信息
			//		System.out.println("当前页码："+page.getPageNum());
			//		System.out.println("总记录数："+page.getTotal());
			//		System.out.println("每页的记录数："+page.getPageSize());
			//		System.out.println("总页码："+page.getPages());

					// 使用PageInfo取分页信息，更加强大（推荐使用）
					PageInfo<User> info = new PageInfo<>(list, 2);
					System.out.println("当前页码："+info.getPageNum());
					System.out.println("总记录数："+info.getTotal());
					System.out.println("每页的记录数："+info.getPageSize());
					System.out.println("总页码："+info.getPages());
					System.out.println("是否第一页："+info.isIsFirstPage());
					System.out.println("连续显示的页码：");
					int[] nums = info.getNavigatepageNums();
					for (int i = 0; i < nums.length; i++) {
						System.out.println(nums[i]);
					}
	7、mybatis调用存储过程
		1、以oracle的为例子
				1、hello_test存储过程的语法自己写
				2、<select>标签的调用：statementType="CALLABLE":表示要使用存储过程的Statement
					// public void getPageByProcedure(); ,jdbcType请参考JdbcType枚举，CURSOR是专门支持oracle的
					<select id="getPageByProcedure" statementType="CALLABLE" databaseId="oracle">
						{call hello_test(
							#{start,mode=IN,jdbcType=INTEGER},
							#{end,mode=IN,jdbcType=INTEGER},
							#{count,mode=OUT,jdbcType=INTEGER},
							#{emps,mode=OUT,jdbcType=CURSOR,javaType=ResultSet,resultMap=PageEmp}
						)}
					</select>
					<resultMap type="com.atguigu.mybatis.bean.Employee" id="PageEmp">
						<id column="EMPLOYEE_ID" property="id"/>
						<result column="LAST_NAME" property="email"/>
						<result column="EMAIL" property="email"/>
					</resultMap>
				3、java代码调用
					EmployeeMapper mapper = openSession.getMapper(EmployeeMapper.class);
					OraclePage page = new OraclePage();
					page.setStart(1);
					page.setEnd(5);
					mapper.getPageByProcedure(page);

					// 就直接有信息了
					System.out.println("总记录数："+page.getCount());
					System.out.println("查出的数据："+page.getEmps().size());
					System.out.println("查出的数据："+page.getEmps());
	8、自定义类型处理器
		1、以枚举类为例：默认EnumTypeHandler，他是以枚举的名字为准，EnumOrdinalTypeHandler以序号为准，推荐我们自定义枚举处理器
			1、编写我们的枚举类
				/**
				 * 希望数据库保存的是100,200这些状态码，而不是默认0,1或者枚举的名
				 * @author lfy
				 *
				 */
				public enum EmpStatus {
					LOGIN(100,"用户登录"),LOGOUT(200,"用户登出"),REMOVE(300,"用户不存在");
					
					
					private Integer code;
					private String msg;
					private EmpStatus(Integer code,String msg){
						this.code = code;
						this.msg = msg;
					}
					public Integer getCode() {
						return code;
					}
					
					public void setCode(Integer code) {
						this.code = code;
					}
					public String getMsg() {
						return msg;
					}
					public void setMsg(String msg) {
						this.msg = msg;
					}
					
					//按照状态码返回枚举对象
					public static EmpStatus getEmpStatusByCode(Integer code){
						switch (code) {
							case 100:
								return LOGIN;
							case 200:
								return LOGOUT;	
							case 300:
								return REMOVE;
							default:
								return LOGOUT;
						}
					}
					
					
				}
			2、编写我们自定义的枚举处理器
				/**
				 * 1、实现TypeHandler接口。或者继承BaseTypeHandler
				 * @author lfy
				 *
				 */
				public class MyEnumEmpStatusTypeHandler implements TypeHandler<EmpStatus> {

					/**
					 * 定义当前数据如何保存到数据库中
					 */
					@Override
					public void setParameter(PreparedStatement ps, int i, EmpStatus parameter,
							JdbcType jdbcType) throws SQLException {
						// TODO Auto-generated method stub
						System.out.println("要保存的状态码："+parameter.getCode());
						ps.setString(i, parameter.getCode().toString());
					}

					@Override
					public EmpStatus getResult(ResultSet rs, String columnName)
							throws SQLException {
						// TODO Auto-generated method stub
						//需要根据从数据库中拿到的枚举的状态码返回一个枚举对象
						int code = rs.getInt(columnName);
						System.out.println("从数据库中获取的状态码："+code);
						EmpStatus status = EmpStatus.getEmpStatusByCode(code);
						return status;
					}

					@Override
					public EmpStatus getResult(ResultSet rs, int columnIndex)
							throws SQLException {
						// TODO Auto-generated method stub
						int code = rs.getInt(columnIndex);
						System.out.println("从数据库中获取的状态码："+code);
						EmpStatus status = EmpStatus.getEmpStatusByCode(code);
						return status;
					}

					@Override
					public EmpStatus getResult(CallableStatement cs, int columnIndex)
							throws SQLException {
						// TODO Auto-generated method stub
						int code = cs.getInt(columnIndex);
						System.out.println("从数据库中获取的状态码："+code);
						EmpStatus status = EmpStatus.getEmpStatusByCode(code);
						return status;
					}

				}
			3、在全局配置文件中修改类型处理器
				<typeHandlers>
					<!--1、配置我们自定义的TypeHandler  -->
					<typeHandler handler="com.atguigu.mybatis.typehandler.MyEnumEmpStatusTypeHandler" javaType="com.atguigu.mybatis.bean.EmpStatus"/>
					<!--2、也可以在具体的增删改查处理某个字段的时候告诉MyBatis用什么类型处理器
							保存：#{empStatus,typeHandler=com.atguigu.mybatis.typehandler.MyEnumEmpStatusTypeHandler}
							查询：
								<resultMap type="com.atguigu.mybatis.bean.Employee" id="MyEmp">
									<id column="id" property="id"/>
									<result column="empStatus" property="empStatus" typeHandler="com.atguigu.mybatis.typehandler.MyEnumEmpStatusTypeHandler"/>
								</resultMap>
					  -->
				</typeHandlers>
			4、java代码和数据库就会得到你想要数据类型
	9、整合spring
		1、需要mybatis和spring的整合(适配)包：mybatis-spring-1.3.0.jar
		2、配置请看，ssm里边的配置




1、问题
	1、和springboot是怎么结合 接口和xml是怎么联合的
		1、 可以直接使用 mapperConfig.xml 的 package/mapper标签配置： mapper.resource="mapper/UserMapper.xml"， 
		springboot直接使用 mybatis.mapper-locations=classpath*:mapper\/*.xml 但是mapper.xml的命名空间一定要对应的mapper.java的全路径
		使用springboot后 mapperConfig.xml 可以不要了，可以要 使用 mybatis.configLocation=classpath:mapper/mybatis-config.xml
	2、maven的
		1、待会看看<scope>

	3、那个报错是不是我xml和注解混用的原因
	4、看mybatis的逆向工程  // 到时我重新生成的话，那个sqlProvider要重新指定位置，这个也有一些弊端，就是分页和多表查询不是很好用，而且表结构不能改改动太频繁
	1、mybatis会帮我们处理返回值的空异常的