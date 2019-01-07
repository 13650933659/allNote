















看完59
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
					4、parameterType(输入参数，好像这个可以不用写)
						1、例子：#{property,javaType=int,jdbcType=NUMERIC}或者#{height,javaType=double,javaType=NUMERIC,numericScale=2}
							1、javaType：可以从参数对象中来确定
							2、jdbcType：当参数值为空时默认的值，<setting name="jdbcTypeForNull" value="在这里改是全局，如果你去对应的#{}写JdbcType=OTHER就只影响自己(默认为OTHER，但是这个oracle不认识)"></setting>
							3、numericScale：保留的小数
							4、mode: 待看？？？
						2、一个参数的处理
							1、一般是：基本类型、pojo、Map类型，直接取值，或者通过OGNL取
						3、多个参数处理(java8和mybatis341可以考虑使用useActualParamName配置)
							1、使用注解
								Employee getEmpByIdAndLastName(@Param("id")Integer id,@Param("lastName")String lastName);
								<select id="getEmpByIdAndLastName" resultType="com.atguigu.mybatis.bean.Employee">
									select * from tbl_employee where id = #{id} and last_name=#{lastName}
								</select>
							2、使用硬编码#{param1},#{param2}...
								Employee getEmpByIdAndLastName(Integer id, String lastName);
								<select id="getEmpByIdAndLastName" resultType="com.atguigu.mybatis.bean.Employee">
									select * from tbl_employee where id = #{param1} and last_name=#{param2}
								</select>
					5、resultType(输出参数)
						1、pojo和List<pojo>
							Employee getById(Integer id); 或者 List<Employee> getById(Integer id);
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
					6、resultMap(输出参数的映射，用了这个就不用resultType了，用了这个字段和属性不对应也不用在sql写别名了)
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
					2、<choose>（<when><otherwise>）等标签，相当于if-else
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
								<foreach collection="ids" item="item_id" separator=","
									open="where id in(" close=")">
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
		1、一级缓存sqlSession级别的缓存。mybatis的一级缓存（作用域=SqlSession），他的一级缓存就是查询的缓存，默认就是启用的，无法关闭；
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
	  				<cache eviction="FIFO" flushInterval="60000" readOnly="false" size="1024"></cache> -->
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
	4、逆向工程
	5、整合spring




1、问题
	1、和springboot结合是怎么配置的
		1、接口和xml是怎么联合的
	2、maven的
		1、待会看看<scope>

	3、那个报错是不是我xml和注解混用的原因
	4、看mybatis的逆向工程  // 到时我重新生成的话，那个sqlProvider要重新指定位置，这个也有一些弊端，就是分页和多表查询不是很好用，而且表结构不能改改动太频繁
	1、mybatis会帮我们处理返回值的空异常的