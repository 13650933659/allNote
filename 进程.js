


1、j2se篇
	1、安装jre（java运行环境），这个其实jdk就有了，但是装这个有好处（他修改系统的注册表，不用我们手动配置lib(一些需要依赖的类库)的环境变量）
	2、打jar包：到你的你编译好的class文件的最上层目录运行：C:\jdk1.7.0_80\bin\jar -cvf xx.jar *.*  或者 C:\jdk1.7.0_80\bin\jar -cvf xx.jar *
		1、 可运行jar包 : jar -cef com.Dog Dog.jar test		// 执行此命令时要在class主目录下面
			c - 代表生成新的jar包；
			e - 代表可执行的类，亦即main方法所在的类。书写时要加上包名，在本例中是后面的 com.Dog；
			f - 代表生成的jar包的名称，在本例中是CardLayoutDemo.jar。此包名可以随意命名，没有规定；
			test - 最后面的这个参数表示将test目录下的所有文件都打包放到新的jar包中

	3、编译成.class文件，在命令行窗口下运行：C:\Program Files\Java\jdk1.7.0_80\bin\javac C:\javaCode\HelloWord.java
	4、执行编译后的文件，在命令行窗口下运行：java HelloWorld
		1、要运行你编译过后的文件，还要加一个环境变量：classpath=.;你编译后的class文件所在目录（点代表当前工作目录，但是jdk1.6已经把rt.jar和当前目录给设进去了）
		2、这个可以不用配置jdk的bin目录，他可能用的是jre的 java.exe
	5、 运行jar包 
		1、例子
			nohup java -jar app.jar -Dspring.profiles.active=dev >> log.out &
			nohup java -jar dataApi-produce-202001100830.jar >> dataApi.out &
		2、参数说明
			nohup	    - 意为后台不挂断运行，与是否账号退出无关 
			&		    - 代表后台运行  
			>> log.file - 输出的日志追加入log.file，
			-Dspring.profiles.active=dev		// 加入java的运行参数 好像也可以 --moose.task.maxThreadCount=4 这样
		


2、待学习
	1、23中设计模式，掌握常用的
	2、java注解
	3、数据库升级机制
		1、什么时候执行好呢
		2、分库
	4、数据库的触发器
	5、 nginx  、 druid 、 Shiro 、 java 性能测试工具
	6、 redis的使用场景
	7、 douker 自动化部署 




3、待做
	1、 redis做集群
	2、一键登录做扩展（比如要区分用户是通过一键登录自动注册的）
	3、 用户行为分析报表
		1、报表字段
			1、基础信息(除内部)
				日期						// 当天日期
				当天注册					// 当天注册的用户
				日积累					    // 截止今天所有的注册量
			2、登录情况(除内部)
				当天登录					// 当天登录			（同一个用户一天登录多次算1次）
				当天付费登录				// 登录（付费的用户，过期不算付费）
				当天未付费登录				// 当天登录 - 付费登录
				当天去除付费、新增			// 当天登录 - 付费登录 - 当天新增的用户
				活跃率					    // 当天登录/日积累
			3、浏览行为(除内部)
				当天登录无浏览				// 当天登录没有浏览公告的
				当天未付费看满1条			// 当天看满1条，没有付费的
				当天未付费看满2条			 
				当天未付费看满5条
				当天未付费看满10条
				当天未付费看满15条
				当天未付费看满20条
			4、营收(除内部)
				付费客户					// 当天付费（同一用户多次算一次）
				收入						// 所有收入
				注册转换付费率				// 付费客户/当天注册
		2、涉及到的表
			1、 b2c_mall_staff_basic_info(用户表)
				applyTime(DATETIME)	// 注册时间
			2、 bxkc_login_info
				loginTime		// 登录时间
				operate_type	// 操作类型(10=登陆,20=唤醒)
				userType		// 用户类型（01内部用户 02供应商 03分销商）+上打了内部标签的
			3、 bxkc_payment_order(订单表)
			4、 bxkc_mybrowse(公告浏览记录)
				1、 新加 bxkc_mybrowse_day 公告浏览统计（每个用户按天统计）		
					1、表结构
						create table bxkc_mybrowse_day(
						  id bigint(20) primary key auto_increment comment '主键',
						  userid varchar(100) NOT NULL COMMENT '用户id' ,
						  browse_date varchar(20) NOT NULL COMMENT '浏览日期，精确到天',
						  num int COMMENT '当天浏览的数量'
						) comment '公告浏览统计（每个用户按天统计）'
						engine=InnoDB collate=utf8_bin;
						create index bxkc_mybrowse_day_userid on bxkc_mybrowse_day (userid);
						create index bxkc_mybrowse_day_browse_date on bxkc_mybrowse_day (browse_date);
					2、代码维护
						新增
						删除需要删除吗

			3、剩余要做的事情
				1、 等富哥代码写好了，把 report 代码合并到 dev 并且测试
				2、 bxkc_mybrowse_day 这个表生产已经有了，但是最好验证一下他的增删改查




			16550000		// 开头全是小七的

			13533542703		// 小七
			13216628864		// 小七
			18344560792		// 张景霞
			15622774803		// 周乐贤
			13312856589		// 蒋祖富
			15767878598		// 胡艳艳
			13430741690		// 邓佳豪
			13265101824		// 林锦兴
			15018782056		// 吴静江
			13650933659		// 陈家儒
			15290170262		// 张梦辉
			18520595873		// 唐国才
	4、  jira 问题：  BDZBW-7 会员权限和支付，购买等修改
		1、 bxkc-pc
			1、支付的改造 支付宝(ChargeAction) 、微信(PayAction) 
				1、点击立即支付 先创建 waiting 的 BxkcPaymentOrder ，并且 productCode = sysLevelPriceId 
			2、用户支持成功后，回调升级会员等级时的改造
				1、支付成功之后升级时候需要查到 waiting 的BxkcPaymentOrder，再拿到 productCode）
				2、自动延期等级比较低的
			3、用户权限(服务项目)判断的改造
				1、获取用户当前生效服务项目包括增值服务项目 
				2、同时把用户过期的服务项目停用
				3、使用的地方改造
					1、用户登录				// 刷新一次，不清除 ok
					2、公告详情				// ok
					3、招标定制  			// ok
					4、拟在建项目			// ok
					5、独家项目				// ok
					6、审批项目				// ok
					7、企业画像				// ok
		2、 bxkc-api
			1、点击立即支付，创建业务订单 （为了兼容 查出 sysLevelPriceId）
			2、支付成功后的回调（做升级 取到 sysLevelPriceId）
			3、自动刷新用户权限
			4、使用权限
				1、用户登录				// 刷新一次，不清除 ok
				2、公告详情				// ok
				3、招标定制  			// ok
				4、拟在建项目			// ok
				5、独家项目				// ok
				6、审批项目				// ok
				7、企业画像				// ok

		4、待做
			1、 bxkc-api 的改造小七测出的问题，还未修复，修复完成把代码部署到线上
			2、有一个复杂的场景待研究和测试
			3、支付宝的 
				return_url 和 notify_url 有什么区别		// 测试环境要改一下测试库
			4、在会员等级那里配置产品改造一下
			5、获取权限那边可能要改一下了（扣除次数就是不要滞后）
			6、 sysMemberSystemService.delUserRedis(userId);	// (不要注释测试一下) 因为 RedisContantKey.USER_INFO + "_"+userid 存放的是 BaseUser ，有效期是一天，不能每次登陆清一次，如果有此次登陆有效的信息，不要放在这里 
			7、 bxkc-pc 上线了，但是 bxkc-api 还未上线
	5、要素提取（mongo 和 neo4j 的改造）
		1、 统计之后的各个项目的维护工作
			1、bidi-data-api(数据干预)			// 已完成
			2、bxkc_data_distinct(公告去重)
				1、删除公告从mongo入手有点问题的，这个需要跟兴哥讨论 
			3、document-push(公告推送项目)	   // 已完成
			4、moose-enterprise-report-api	   // 已完成
			3、bxkc-pc
				1、各种公告详情		// 这个是涉及到mongo比较好改
				2、企业画像功能
					1、企业搜索
						1、企业搜索结构列表-最新招标信息
					2、企业主页
						1、 企业图谱《关联招标人&潜在对手》 和 《关联中标人&投标人》
							1、相关的项目
						2、 已发布的招标项目
						3、 已发布的招标项目-点击 更多 里面的二次搜索
						4、 最近招标历程
						5、 已参与的中标项目
						6、 已参与的中标项目-点击 更多 里面的二次搜索
						7、 中标业绩-整体趋势
				
		bidi-data-api(数据干预)\bxkc_data_distinct(公告去重)\document-push(公告推送项目)\moose-enterprise-report-api
		bxkc-pc\bxkc-api\xique\zhongzhao


			4、bxkc-api
			5、xique
			6、zhongzhao
			7、可能 富哥、佳豪、江哥 自己负责的项目有用到 mongo 和 neo4j 的


	6、告诉佳豪 他那个 对手检测 取数方式可能需要改动
		1、先通过 es 拿到 organization （监测的公司）
		2、然后通过neo4j 查到对应 project		// 到这里就可以了
		3、找根据 docid 再去 solr 拿


4、我负责的项目
	1、自己负责的项目
		1、 bidi-data-api(数据干预)
		2、 bxkc_data_bigdata (要素提取)
		3、 bxkc_data_distinct(公告去重)
	2、moose我负责的项目
		1、 document-push(给启信宝、天眼查推送公告的项目，目前在 47.98.49.4 运行)
		2、 moose-enterprise-report-api(生成企业报表+给百度获取生成公告的接口)	
			1、生成企业报表，目前不运行，有运行是在 47.98.49.4 运行   // 注意生成报表的代码如果要运行需要对应改造 mongo + neo4j 的数据，改好，但是需要再次测试数据的准确性
			2、给百度获取生成公告的接口 这个再moose运行
	3、协同参与的项目
		1、 bxkc-pc
		2、 bxkc-api
		3、 xique
		4、 xique-m
		5、 zhongzhao(中招联合)
		6、 zhongzhao-frontend(和艳艳交互代码的项目)
	4、私服项目
		1、 bidi-common(公司私服-存放 EnterpriseProfile 等实体)
		2、 business_bxkc(公司私服-存放 mongo\neo4j\es 等实体)
		3、 joey-elasticsearch(公司私服-es访问工具类)







1、临时记录
	1、整理支付流程（移动端、pc端、H5）
	2、去重项目的优化(当天的公告一条条的处理)
	3、moose.document_push_log.pushTime 记得创建索引










// 修改 bxkc 的用户缓存(登录对应的后台)
	http://www.bidizhaobiao.com/basedata/member_list!redisGet.do?key=user_info_00013065620200226&type=object
	http://www.bidizhaobiao.com/basedata/member_list!redisDel.do?key=user_info_00013065620200226&type=object


	http://admin.xqzhaobiao.com/basedata/member_list!redisGet.do?key=user_info_00037944020200312&type=object
	http://admin.xqzhaobiao.com/basedata/member_list!redisDel.do?key=user_info_00037507620200303&type=object









1、 mongo 同步到 solr 
	<!--mongo fields-->
	<field column="tenderee" name="tenderee"></field>
	<field column="win_tenderer" splitBy=";" sourceColName="win_tenderer"></field>
	<field column="agency" name="agency"></field>
	<field column="win_bid_price" name="win_bid_price" splitBy=";" sourceColName="win_bid_price"></field>
	<field column="bidding_budget" name="bidding_budget"></field>
















