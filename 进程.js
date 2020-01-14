

妈妈开机密码：46454988l

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
	5、 运行jar包 ： nohup java -jar dataApi-produce.jar  > log.out  &		// 
		nohup	    - 意为后台不挂断运行，与是否账号退出无关 
		&		    - 代表后台运行  
		> log.file  - 输出的日志加入log.file，
		-Dspring.profiles.active=dev		// 还可以加入java的运行参数 比如 -Dspring.profiles.active=dev  好像也可以 --moose.task.maxThreadCount=4 这样
	 -Djoey.elasticsearch.profiles.active=prod -Djoey.neo4j.profiles.active=prod 
	nohup java -jar app.jar --moose.task.maxThreadCount=4 >> log.out &

nohup java -jar bxkc_crawl_config.jar >> log.out &

2、j2ee篇
	1、区块链\比特币
	3、23中设计模式，掌握常用的
	5、java注解








1、数据库升级机制
	1、什么时候执行好呢
	2、分库
2、 commons-codec 是Apache开源组织提供的用于摘要运算、编码解码的包。常见的编码解码工具Base64、MD5、Hex、SHA1、DES等
4、数据库的触发器


	
		   

2019年后半年
	1、 nginx 、 springsecrity 、 Shiro 、 接口文档框架(Swagger) 、 java 性能测试工具
	2、 redis的使用场景
	3、 mongo的使用场景
	4、 neo4j的使用场景
	5、 douker 自动化部署 


待做
	1、 redis做集群
	3、 sys_document 分表后，负责维护的项目
		1、 bxkc_data_bigdata(要素提取)
			
		2、 bidi-data-api
		3、 moose.document-push
		4、 moose.enterprise-report		// 目前还未用到 sys_document 后续可能会用到


1、 bxkc_data_bigdata(要素提取)
	用户名 bxkc 数据库 bxkc
2、 bidi-data-api(数据干预)
	用户名 bxkc 数据库 bxkc
3、 moose.document-push(公告推送)
	用户名 bxkc 数据库 bxkc
	用户名 bxkc 数据库 moose
4、 moose.enterprise-report(企业报表接口)
	用户名 bxkc 数据库 bxkc
5、 bxkc_data_distinct(公告去重)
	1、 mysql bxkc_read=bxkc_20RE18AD
		sys_document(只读)
	2、 mongo
		

	5、要素提取， 招标预算价 说没保存，但是我第二次去跑一次又可以了
		1、 biddingBudget 中标公告没有这个字段，但是会有合并公告的动作
	6、一键登录做扩展（比如要区分用户是通过一键登录自动注册的）
	7、标讯快车的企业搜索变成模糊搜索
	8、m站详情页要用404
	9、 啊彪改了 bxkc 的密码 对 enterprise-report 可能需要修改
		1、 这个项目只对 EnterpriseReport 读的操作
	9、企业报表生成 bidNumber > 10 生成
	9、000345075-1574933065273-46 富哥说叫我去看一下订单完成后，延迟升级
	
	1、 企业画像需要做的事情
		1、 热门关键字 和 推荐企业 做后台配置界面	// 好了，但是推荐企业需要改成去读 mongo的
		4、 增加四个表
			bxkc_popular_keywords(热搜) 
			bxkc_myrecommend(推荐) 
			bxkc_mysearchkeyword_org(搜索历史) 
			bxkc_mycollection_enterprise(我的收藏(企业))
			bxkc_mybrowse_org(企业浏览记录)
		5、 用户+会员等级+服务(权限) 整理
			1、 柳裕新加的 bxkc_price(标讯没有的) 可能要废弃了，使用原有的 sys_level_price
		6、企业画像推荐列表 的提示冲突了
		7、公告详情的改造
		8、会员权限的改造(这个需要下单那边配合)
			有空去整一下会员权限的文档
		8、 标讯快车手工录入的没有uuid需要补上
1、问题
	

1、前端经验
	1、 jsObj.forEach(fun{});不兼容ie8，但是jqObj.each();支持
	2、 idea安装less插件 https://www.cnblogs.com/liaojie970/p/6653593.html
	3、 A.jsp使用 struts 异步引用的jsp里面的引用的js代码，在A.jsp的其他js可以用使用

2、后端经验
	1、struts 的标签 <s:iterator
		<s:iterator var="obj" value="tenderCount.dcountArr" status="st">		// tenderCount.dcountArr 是数组 obj 是字符串
		<s:iterator id="picture" value="loadpicture_a1" status="st">			// loadpicture_a1 是数组	并且 picture 是对象
			<div class="item ${st.index==0?'active':''}">
				<a href="${picture.ahref == null || picture.ahref == '' ? '#' : picture.ahref}" style="display: block; width: 100%;height: 378px; background: url('${picture.imageurl}') no-repeat center top;"></a>
			</div>
		</s:iterator>
		<s:iterator var="city" value="#request.citylist" status="st" >		// 有的是这种写法
			<li id="danXuan_<s:property value="#st.index"/>" ><s:property value="#city"/></li>
		</s:iterator>


1、 用户行为分析报表
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








1、  jira 问题：  BDZBW-7 会员权限和支付，购买等修改
	1、 标讯快车官网
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
	2、 标讯快车app
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
		2、有一个复杂的场景待研究和测试
		3、支付宝的 
			return_url 和 notify_url 有什么区别		// 测试环境要改一下测试库
		4、在会员等级那里配置产品改造一下
		5、获取权限那边可能要改一下了（扣除次数就是不要滞后）
		6、 sysMemberSystemService.delUserRedis(userId);	// (不要注释测试一下) 因为 RedisContantKey.USER_INFO + "_"+userid 存放的是 BaseUser ，有效期是一天，不能每次登陆清一次，如果有此次登陆有效的信息，不要放在这里 




1、 待会修改 bxkc-api 的 judgeUserHavePermission
	levelResult.setStatus(levelResult.getResidue() > 0 ? SysMemberLevelResult.ISOK : SysMemberLevelResult.NO_HAVE_NUM);     // 如果次数已经用完了，需要给一个标记





中招项目的问题
	1、[ 信息检索管理 ] -[ 信息检索列表 ] 和 喜鹊首页的数据对不上
	1、启信宝删除接口联调
	2、要素提取（mongo 和 neo4j 的改造）
	3、告诉佳豪 他那个 对手检测 取数方式可能需要改动
		1、先通过 es 拿到 organization （监测的公司）
		2、然后通过neo4j 查到对应 project		// 到这里就可以了
		3、找根据docid 再去 solr 拿
	4、  jira 问题：  BDZBW-7 会员权限和支付，购买等修改 (标讯快车api的待修复)





