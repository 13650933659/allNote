



1、j2se篇
	1、安装jre（java运行环境），这个其实jdk就有了，但是装这个有好处（他修改系统的注册表，不用我们手动配置lib(一些需要依赖的类库)的环境变量）
	2、打jar包：到你的你编译好的class文件的最上层目录运行：C:\jdk1.7.0_80\bin\jar -cvf xx.jar *.*
	3、编译成.class文件，在命令行窗口下运行：C:\Program Files\Java\jdk1.7.0_80\bin\javac C:\javaCode\HelloWord.java
	4、执行编译后的文件，在命令行窗口下运行：java HelloWorld
		1、要运行你编译过后的文件，还要加一个环境变量：classpath=.;你编译后的class文件所在目录（点代表当前工作目录，但是jdk1.6已经把rt.jar和当前目录给设进去了）
		2、这个可以不用配置jdk的bin目录，他可能用的是jre的java.exe


2、j2ee篇
	1、看看hibernate的使用反射机制的源码
	2、区块链\比特币
	3、23中设计模式，掌握常用的
	4、junit和testNG、两种代码版本控制工具（SVN和Git）
	6、spring seculity
	7、java注解


	
	1、mybatis
		2、和spring整合之后，事务就给spring管理，但是针对那些没有事务的find方法，难道不需要我们关闭session吗？好像用了c3p0的连接池就不用我们自己关闭session了
	


https://blog.csdn.net/zsy3313422/article/details/52583091		// 去看一下idea部署使用debug




http://www.bxkc.com/dataInterventionAction!deleteByIds.do?docIds=13,14





妈妈开机密码：46454988l

1、数据库升级机制
	1、什么时候执行好呢
	2、分库
12、idea查看pom的依赖结构
13、换掉springboot的默认的日志框架
14、换掉springboot内嵌的tomcat(换成不是内嵌的，但是他启动springboot那个tomcat是哪里的呢？我还能直接从浏览器访问项目吗？)
15、spring-boot-starter-jdbc这个是什么
16、commons-codec是Apache开源组织提供的用于摘要运算、编码解码的包。常见的编码解码工具Base64、MD5、Hex、SHA1、DES等
17、字典研究，项目启动慢是不是他的原因，hanl
1、springboot里边的pom依赖的研究
1、maven编译一个java
1、@Autowired注解构造方法，还是使用类型吗？
1、看一下maven会不会把没用的bean编译进入target
1、数据库的触发器
1、idea自动编译java,和忽略警告

1、redisson的分布式锁
1、SubProjExtrRslt subProjExtrRslt : subProjExtrRslts 如果没有指标段，是怎么样的呢？
1、线程安全问题

已解决
	1、@Service bean的名称默认是被注解类的名称吗？
		如果有指定@Service("xx")就是xx，没有就是类名首字母小写





1、今天的
	6、事务测试
	7、spring boot的jar，测试maven的<scope>和jar是否打入包中
	1、拦截器和动态代理是什么关系
	1、数据库的存储过程，和触发器复习
	1、spring两个定时器会并发吗	// 默认不并发，但是可以配置并发
	1、mongodb的java使用
	
	1、Organization organization = organizationRepo.findByName(name);	// 这个如果有同名的公司就麻烦了
	1、neo4j没事使用事务
	8、下周的任务
		1、调用爬虫要改成集群(信用中国的)
		   

年后
	1、springboot -> spring原理 -> 注解原理 -> 动态代理(jdk的、cglib的)
	2、后台需要提供一个模块可以修改数据的
	3、有空去看top的头信息
	4、 java 大数据的集合 分批处理 https://blog.csdn.net/fzy629442466/article/details/84765070
		1、我自己写的算法
			public List<List<Long>> pa(List<Long> docIds, int maxNumber) {
				List<List<Long>> partition = new ArrayList<>();

				if (CollectionUtils.isNotEmpty(docIds) && maxNumber > 0) {
					int size = docIds.size();
					int limit = (size + maxNumber - 1) / maxNumber;

					for (int i = 0; i < limit; i++) {
						List<Long> list = new LinkedList<>();
						int start = i * maxNumber;
						int end = start + maxNumber;
						end = (end <= size) ? end : size;
						for (int j = start; j < end; j++) {
							list.add(docIds.get(j));
						}
						partition.add(list);
					}
				}
				return partition;
			}
	5、 spring 集成 junit https://blog.csdn.net/qq_15204179/article/details/82975127
	6、 git 修改提交信息 https://www.cnblogs.com/dudu/p/4705247.html

2019年后半年
	1、数据结构
	2、算法原理
	3、网络原理
	4、c语言

百度会员 2019-03-26 到期

<!--数据干预 -->
	<bean id="dataInterventionService" class="com.i2f.edoc.bxkc.service.impl.DataInterventionServiceImpl">
	</bean>


questurl=bolt://121.46.18.113:17087
username=bxkc
password=bxkc


// 等下记得看一下子标段的其他属性是否一样
	docId				// 公告文档编号
	docTitle			// 公告文档标题
	docPageTime			// 公告文档发布时间
	industry			// 项目归属行业分类(小类）
	docInfoType			// 项目归属行业分类(大类）
	projectName			// 项目名称
	projectCode			// 项目编号
	projectAddr			// 项目行政区划
	anounceReleaseDate	// 项目发布时间
	anounceEndDate		// 项目结束时间
	tenderee			// 招标单位
	tendereeAddr		// 招标单位地址
	tendereeContact		// 招标单位联系人
	tendereePhone		// 招标单位联系电话
	agency				// 代理结构
	agencyContact		// 代理机构联系人
	agencyPhone			// 代理机构联系电话



	// 招标特有
		biddingBudget			// 招标预算（或招标控制价）
		registerBeginDate		// 报名开始时间
		registerEndDate			// 报名结束时间
		List<ProcurementGoodManifest> procurementGoodManifestList;	// 采购货物列表

	// 中标特有
		winTenderer			// 中标单位
		winTendererManager	// 中标单位项目经理
		winBidPrice			// 中标单位投标价
		List<ProcurementGoodManifest> procurementGoodManifestList;	// 中标货物
		openBidDate			// 开标日期
		judgeDate			// 评标日期
		judgeCommittee		// 评标委员会成员
		judgeAddr			// 评标地点
		isEffective			// 是否废标

5c7cc1ef5cbb6b72cc70b156
"proclamationId":{"timestamp":1551679983,"machineIdentifier":6077291,"processIdentifier":29388,"counter":7385430},"subProjectCode":"不得同时参加同一标段或者同一招标项目"}

5c7cc1ef5cbb6b72cc70b156


// 修改		， 可以考虑给 mongo的文档加一个属性biddingBudget(招标预算)，这个在招标公告已经有了，但是中标公告没有，其实第一种标人可以忽略掉，以中标人为准
	1、mysql
		doctitle, dochtmlcon, docchannel, area, province, city, district, industry,info_type	// 9
		projectName, projectCode, projectAddr, tenderee, tendereeAddr, tendereeContact, tendereePhone, agency, agencyContact, agencyPhone	// 10
	2、mongodb
		1、公共属性
			id					// id属性
			docTitle			// 公告文档标题
			industry			// 项目归属行业分类(小类）
			docInfoType			// 项目归属行业分类(大类）
			projectName			// 项目名称
			projectCode			// 项目编号
			projectAddr			// 项目行政区划
			tenderee			// 招标单位
			tendereeAddr		// 招标单位地址
			tendereeContact		// 招标单位联系人
			tendereePhone		// 招标单位联系电话
			agency				// 代理结构
			agencyContact		// 代理机构联系人
			agencyPhone			// 代理机构联系电话
		2、招标
			biddingBudget			// 招标预算（或招标控制价）
		3、中标
			winTenderer			// 中标单位
			winTendererManager	// 中标单位项目经理
			winBidPrice			// 中标单位投标价

	3、neo4j
		1、Project节点
			nodeId				// 节点id
			projectName			// 项目名称
			projectCode			// 项目编码
			industry			// 项目归属行业分类(小类）
			infoType			// 项目归属行业分类(大类）
			projectAddr			// 项目地址
			zhaoBiaoName		// 招标公告名称
			zhaoBiaoUuid		// 招标公告uuid
			zhaoBiaoPageTime	// 招标公告发布时间
			biddingBudget		// ???
			zhongBiaoName		// 中标公告名称
			zhongBiaoUuid		// 中标公告uuid
			zhongBiaoPageTime	// 中标公告发布时间
			winBidPrice			// 中标价
			isFeiBiao			// 是否废标
			subProjectCode		// 子项目编码
			subProjectName		// 子项目名称
			subProjectNameAlias	// 子项别名
			area				// 区域
			province			// 省份
			city				// 城市
			district			// 县
			status				// 此属性用于解决bug 将一些数据重新跑入其他库的时候使用 用完就可以删除对应的属性










