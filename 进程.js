


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
			nohup java -jar bxkc-report.jar --Dspring.config.location=application.yml >> log.out &
			nohup java -jar persistence-hist.jar >> log.out &

			nohup java -jar app.jar >> app.log &
		2、参数说明
			nohup	    - 意为后台不挂断运行，与是否账号退出无关 
			&		    - 代表后台运行  
			>> log.file - 输出的日志追加入log.file，
			-Dspring.profiles.active=dev		// 加入java的运行参数 好像也可以 --moose.task.maxThreadCount=4 这样
			--Dspring.config.location=application.yml		// 指定外部文件

	6、 增量jar https://www.cnblogs.com/wangyayun/p/11725351.html
		1、把源jar包cp到一个空文件夹里，解压	jar -xf persistence-extract-202105260424.jar
		2、进入BOOT-INF/class，替换
		3、把该文件夹里的源jar包删除			rm -rf persistence-extract-202105260424.jar
		4、重新打包								jar -cfM0 persistence-extract-202105260424.jar *
		5、把打好的jar包cp到启动目录，启动就ok
	7、  powerDesigner关联数据库显示中文注释  https://www.cnblogs.com/timeflying/p/11409416.html

2、待学习
	1、23中设计模式，掌握常用的
	2、java注解
	3、数据库升级机制
		1、什么时候执行好呢
		2、分库
	4、数据库的触发器
	5、  druid 、 Shiro 
	6、java线程，锁 jul包
	6、java 性能测试工具
	6、 redis的使用场景
	7、 douker 自动化部署 
	8、 wireshark抓包工具详细说明及操作使用 https://blog.csdn.net/qq78069460/article/details/79153895
	1、 redis做集群
	1、整理支付流程（移动端、pc端、H5）
	5、抽空去了解一下 frp和nginx的https请求
	6、spring的定时器第一次立即运行，怎么配置
	


1、修改 bxkc/xique 的用户缓存(登录对应的后台)
	http://admin.bidizhaobiao.com/basedata/member_list!redisGet.do?key=user_info_00000339120210522
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=user_info_00072213820210520&type=object

	http://admin.bidizhaobiao.com/basedata/member_list!redisGet.do?key=user_info_${userid+today}
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=user_info_${userid+today}&type=object
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=seo_hot_focus&type=batch


	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=memberlevel_list&type=object
	
	// 清菜单
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=bxkc_back_user_all_memu_000370740&type=object
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=MOST_SPECIFY_ORGS&type=object

	http://admin.xqzhaobiao.com/basedata/member_list!redisGet.do?key=user_info_00041042320201208
	http://admin.xqzhaobiao.com/basedata/member_list!redisDel.do?key=user_info_00039861820210430&type=object
	


1、优先处理记录
	7、enterprise.products 更新之后没有同步
	7、8-28(星期六做兄弟)
	9、公告需要再重新划分一下类型
	
	4、预定义字段满了，看看要怎么扩展
	1、合并企业
		15、合并企业联系方式可以改成，一天同步一次，而且跑历史数据是不应该再次合并了
		1、英文括号换成中文括号
	
	3、被删除的企业需要重新算招投标数量吗？要的，已经测试待上线
	2、中标数据二次导出后台报错 // 看一下api的
	1、企业报表标识业主、供应商
	2、附件处理的
	3、contacts增加来源区分、和是插入时间，到时可以做排序
	2、重构企业查询	// 只做了pc的，其他的记得用pc为准 bxkc-api 已经打好包了
	3、地区匹配需要多加一个规则
		这里要多加一个规则，如果匹配出多个，而且有跟原来爬虫的city一致的，要保留原来的
	3、迁移到gitlab
		bxkc-pc
		bxkc-maven-core
		tablestoreutil
		bxkc-api
		persistence-extract
		bidi-data-intervention
		moose-report-api
		moose-document-push
		easy-tablestore
	
	1、最近的任务（2021.6.15）
		1、入库程序增把公告纯文本和附件纯文本分别存储到（doctextcon、attachmenttextcon）				// 已上线
		2、后台增加修改附件的功能
		3、document预定义字段满了，需要调整document表结构
		4、地区匹配多加一个规则，如果匹配出多个，而且有跟原来爬虫的city一致的，要保留原来的
		5、柏和
			1、企业报告业主版需求开发、测试、上线
	

1、已完成
	14、协助申请专利
	1、重建 document_index 索引		// 佳豪说暂时先用mysql来做
		+ page_attachments			// 这个才是预定义字段
		- attachment
	2、使用178的redis需要清理掉
	3、bxkc_monitor_web=970 改成带前缀的
	12、新需求
		bxkc-admin项目新增需求
			1、更新公告，需要做金额字段的校验
			2、新增删除企业
	5、明天处理 enterprise-report.jar
	1、enterprise新增索引字段
		new_bidi_id			// 被合并后的bidi_id
		status				// 空和[200,300]都是成品数据 [400,450]表示被删除
		baidu_check_status	// 是否去百度获取过数据
	3、企业报表（业主、供应商）需要加附录
	2、enterprise.contacts的phone_no和mobile_no有一小部分位置填错了
	2、开发一个更新公告立即生效的接口(杰华有一个接口，专门针对后台录入的公告进行生效的)
	3、https的ssl证书申请
		去阿里云的ssl证书申请(应用安全)
	4、企业主页的注册资金对不上
		bxkc-pc已处理，工作台袁也处理了
	2、上线联系人
	3、升级企业的改为10s
	4、明天把 persistence-extract 看一下
	4、moose平台技术方案
	5、企业报表（业主版）			// 2021.4.25-2021.4.9 负责人：陈家儒、蔡柏和
	16、有几家公司的联系方式被删除了，补一下
	13、bxkc_enterprise_listing_info表的数据同步到ots
	4、拟在建需求
		1、搜索增加id搜索
		2、修改增加page_time
	3、北京crm接口需要改变
	10、重建 document_index 
		"计量数据更新时间"       "存储"				  "行数"		 "预留读CU"
		<2021年5月13日 15:19:30> <493,148,300,449字节> <116,848,537> <4,600>		// 改前情况
		<2021年5月14日 08:56:05> <488,507,396,049字节> <117,053,051> <4,550>		// 改后情况（索引的doctitle和doctextcon改为数字切割）
		<2021年6月9日 21:55:23>  <525,405,251,025字节> <125,802,532> <4,900>		// 改前情况
		<2021年6月10日 21:49:24> <521,752,640,353字节> <126,179,029> <4,860>		// 改后情况（+attachmenttextcon+page_attachments.fileMd5 -attachments）





	11、重建 enterprise_index 索引 ()
		<2021年5月13日 21:54:58> <48,149,024,132 字节>  <38,735,702>  <450>
		<2021年6月2日 21:25:26>  <57,364,566,996 字节>  <38,611,848>  <540>			// （增加 updateTime 索引） 38611910
																// 2021-06-02 22:41:09 增加 new_bidi_id/status/baidu_check_status 索引


		
		中文+数字	// 不完整搜索不出来
		
		doctitle 是单字分词，数字切割
		场景
		doctitle=强光充电式手电筒90738
			1、我使用 "强光充电式手电筒90738"		// 能搜索到
			2、我使用 "强光充电式手电筒9073"		// 不能搜索到，因为是一个整体
	
	14、删除没用到的企业（总共有：243036）
		(bid_numberi is null or bid_number=0)				// 没有招投标记录
		and tyc_id is null									// 天眼查那边找不到的企业
		and (contact_number is null or contact_number=0)	// 没有联系方式的企业
	13、搜索企业 bid_number>0 or tyc_id is not null 才会显示
	13、搜索企业 有招投标记录或者有天眼查那边的工商信息才会显示
		bxkc-pc			// 已合并master
		bxkc-api		// 已合并master
		xique			// 已合并master
		zhongzhao		// 已合并master
	1、oracle同步ots数据补漏（补漏时间范围 [2021-04-23, 2021-05-11] ）
		1.锁定docid范围 [4.23最小docid, 5.11最大docid]=docids_全
		2.docids_全-ots.document=docids_漏
		3.去mysql.sys_document找对应的 docids_漏 记录
		4.直接插入ots.document
	6、新增企业 新增接口 和 修改接口
	7、把报表项目移动到moose
	9、替换行业报表模板
	5、同步地区
		select * from bxkc_schoolandhospital
	6、 合并项目漏了docid		// swf重複做要素提取的問題
	7、 企业主页下载报表按钮提示和实际情况不一致的问题
	6、去掉 web_source_no_org_rel  规则
	8、moose.report逻辑修改	// enterprise.ru_wei_number > 0 然后会提示 近一年无数据
	7、删除公告需要重新更新Project
	5、那个分解招标中标的，要不要把这个“入围”关键字去掉吧
	6、更新document联动更新project的程序修复了，已经上线了，明天把袁也提出的六个数据也修复一下
	4、province\city 会出现两种情况 比如 广东/广东省 广州/广州市
	2、删除project表
	2、修復地区匹配
	3、帮佳豪重建索引
	10、把 中铁建发(成都)建设工程有限公司 融合到 中铁建发（成都）建设工程有限公司
	3、 更新公告 合并实体 删除实体 公告删除 也要 同步一下 ots.project
			更新公告	// 已处理，已上线
			合并实体	// 
				下一步就要合并实体的改造一下
					荣联科技集团股份有限公司		// 正确的一家公司
					北京荣之联科技股份有限公司
					中钢招标有限责任公司			// 这个也要改一下
					// 
					湖南省财政厅
					中华人民共和
	3、公告记录谁删除的
		1、bxkc-admin 补上
	1、重新处理公告数据
		1、前提条件
			我把zhongzhao1的 deleteDupDocumentSchedule 停了，记得启动
		2、历史数据入库document_extract(104617594 最大 docid=135625999) -> document  // 开始时间 2021-03-05 21:34:39.292
	1、 project -> project2
		我的项目
		工作台
		bxkc-pc
		bxkc-api
		中招		// 没有project
	1、拟在建自动跟进要获取最大版本
	1、enterrise.products保留1000个  
		2,687,838,313,771 字节
		25,040 cu
		1、处理
			solr5   1->38581822  4000(24)  // 停了 mergeProject 
				1 -> 30000000				solr5
				30000001 -> 38581822		solr4
	2、处理 project.bidway
		1-76990170
			1 -> 38495085		solr5
			38495086 -> 76990170		solr5
	12、 2021.0309.0923 升级tablestore-util（去掉docchannel的权重排序）
		bxkc-api
		bxkc-pc
		xique
		zhongzhao
		moose
	9、删除公告改成定时器
	10、ru_wei_number 重建索引 String -> Long 
	9、增加字段(已改好，已经上线)
		match_enterprise
		match_enterprise_type				
		fingerprint
	8、把 enterprise.nicknames 改成单字分词
	4、crm接口
			规则是根据客户去重后的订阅词获取各个地区的公告量及各个订阅词下各个地区的招标单位，招标数量，招标金额和中标单位， 中标数量 ，中标金额等
			INPUT:关键词、地区  OUTPUT：
				top10招标单位、招标数量、预算总金额
				top10中标单位、中标数量、中标金额
	10、增加 采购系统(procurement_system)的提取
		1、增加字段
			1、 enterprise 增加 procurement_system
			2、 document 增加 procurement_system
			3、 project 增加 procurement_system
		2、维护
			1、入库程序
				1、增加企业、更新企业都要做（更新就判断是否有值了）
				2、新增公告
			2、项目合并
				1、增加企业、更新企业都要做（更新就判断是否有值了）
				2、新增项目					// 全部已完成，已经上线
			3、其他地方新增企业的不用先
		3、部署
			1、入库程序
			2、项目合并
	10、天眼查推重叠的数据	// 已解决，排查反了
	9、bxkc-job项目的
		tablestore.yaml 改成内网 而且是使用外部文件的
		application.yml 也是使用外部的 application-prod.yml，但是里面的 application.yml 是不用的
	2、拟在建还有一个修改发布逻辑定时器	// 已部署 自动发布 1200 自动跟进 1200
	7、ots.project 和 gdb.project 对不上，企业的招投标数据量要使用ots		// 已经在重跑
		1、重新计算企业的招投标数量
			1、bidi-data-intervention项目
				1、改为使用gdb.project
				2、enterprise.upgrade_status=[91,120]
			2、persistence-extract项目
				1、UpgradeEnterpriseBidNumberSchedule  // 查 [1,90]
				2、enterprise.upgrade_status=[91,120]
	2、后台的 拟建项目管理
			列表页面
				1、数据显示 PAGE_TIME倒序       // 查ots
				2、批量发布 和 撤销（也是批量）
				3、同步 // 同步数据就是启动增量同步定时器 // 这个可以去掉了，因为改为两个小时同步一次了
				4、新增、修改		// 做到这里 projectNum;//建筑物层数 去掉
				5、删除
				6、项目概况 dataObj.projectDescription
				7、项目跟进
	20、企业增加 create_time
		1、升级 tablestore-util
		2、数据干预		//
		2、入库程序		//
		3、合并项目		//
	1、规范仓库管理
		bxkc-pc
	4、删除公告，需要清除ots的Project
		1、找到对应的 Project 
		2、删除对应 docid
		3、如果没有docid的project需要删除		// 对应gdb（直接删除Project即可）
		4、如果还有docid的project需要更新		// 对应gdb（直接删除Document即可）
		5、把所有的企业重新计算招投标数量
	8、删除公告，加一个真正删除
	1、公告去重、项目去重		// 已完成
		历史去重的程序		// 4603483 (2021年1月19日 10:21:40)
	1、迁移拟在建		// 1、全量数据已经迁移到ots 2、增量同步定时器已经迁移到 bxkc-job ，也停了旧的增量同步定时器
	2、解决方案
		1、把 contact 合并到 enterprise 并且使用 contact=[{contactPerson.index,phone.index,mobile.index}] 来存储他们联系方式， contact_number 总数
			1、把 company_contact_dumplicate 的数据同步过来，以后 company_contact_dumplicate 可能不维护了	// 同步了 1-38691254 的数据总数量 38691246
				1、2021.01.05 11:10 新开的服务器 172.16.147.69(8.16)
				2、第二波同步 id = (38691254,48337430] 总数量 9646176
			2、开发一个接口同步 company_contact_dumplicate
			3、修复(去除无效的数据)		// 无效的定义(mobile_no 和 phone_no 都没有的 如果连)
				1、从 company_contact_dumplicate 入手
		2、新增enterprise.ru_wei_number(入围数量)=zhong_biao_number+tou_biao_number		// 已处理 ru_wei_number 待建索引
		3、按产品搜索企业
			新增 enterprise.products ， 格式如 电梯,天然气		// 按项目发布时间？		// 暂时不做阳光再次评估
	1、删除企业的功能
		1、ots 
			1、删除 enterprise
			2、更新所有公告
		2、gdb直接删除 Organization 即可
	4、非中标公告清空 中标字段
	6、生成一批企业报表
	7、晚上重新创建索引
		info_type
		industry
		page_time
		sub_docs_json
			bidding_budget
			win_bid_price

	8、检查去重的结果
	2、记得把所有的项目gogs地址换掉 新的
		persistence-extract
		moose-enterprise-report-api
		bidi-data-intervention
		easy-tablestore			// github 但是佳豪已经迁移到局域网，也已经改了
		tablestoreutil
		easy-gdb
		bidi/bxkc-workbench
		前端/bxkc-workbench
		document-push
		bxkc-api
		bxkc-scheduler
		bidi-gateway-business
		xique
		zhongzhao
	3、这个项目尽快数据迁移document-push
		// 检查推送的结果

	6、历史去重的表结构
	2、删除修改status=[401,450]
	3、入库行业分类
	4、合并 bxkc-scheduler 分支 ，叫啊彪的jenkins配置也要改分支
	3、三期需求的整理
	5、地区出现“无”
	4、删除 document2 
	5、梦说的 一个 bidiId 对应多个企业
		dup_enterprise(id,name,bidi_id)
		1、把全部的 enterprise 都处理一次	// 已处理
			2020-12-06 07:51:42.005  WARN 3917 --- [           main] c.m.e.s.UpgradeEnterpriseBidiIdScheduler : 第9796次读取结果，size=196
			2020-12-06 07:51:43.370  WARN 3917 --- [           main] c.m.e.s.UpgradeEnterpriseBidiIdScheduler : 第9796次处理结果,dupNumCount=25828,dupNum=0
	11、升级 bid_number		= 70089694，解决方案新增一个定时器去处理，其他的请求去掉
		切入 ： upgrade_status=[1,60]
		成功 ： [61,90]
	8、删除 tyc_id=null || bid_number=null/0
	1、document-push内存过大，每次1000改为200
	4、后台修改 新增 status
	2、弄明白下面三个项目类型
		拟在建项目		- designed_project		// 独立的表
		审批项目		- shen_pi_xiang_mu		// 独立的表
		独家项目		- exclusive_project		// 这个没用了，直接调用 designed_project 只是有一个类型区分
	4、 2021.1.4 下班前给接口文档喜江
		增加 新增公告的接口   // 参考 com.ccb.operation.module.basedata.bidinfosecond.action.ListAction 130 行  已经把 docid 生成器弄好了
		// 已开发完成，等待部署
			1、因为有一个定时器在计算企业招投标数量了，索引其他端的请求去掉吧
				bxkc-admin		// 没有
				bxkc-pc			// 3、计算招投标数据（2020.12.31 去掉 因为有自动计算的定时器了）
				bxkc-api		// 3、计算招投标数据（2020.12.31 去掉 因为有自动计算的定时器了）
			2、上线

	10、处理下面，并且排查
		原先名称
			青矩工程顾问有限公司
			211660511309410304 
			3435898629
		改名
			天职(北京)国际工程项目管理有限公司				     
			211660511309410304			-> 266136154444795904

	7、 把 金额字段由 string 变成 double
		1、 入库程序
		2、 全部更新
		3、 重建索引，测试，更新的地方要注意
		4、把工具包对应的实体金额字段也改成 double
	7、project新增一个 page_time 优先使用 中标时间
		1、 page_time 建索引
		2、 入库程序升级
		3、 升级 project.pageTime 字段 

	10、建林反馈修改公告报错 128714239	// 又可以了，可能是dochtmlcon的问题
	6、一个 bidi_id 对应多个企业了 还有		// 解决了，因为公司改名称了，天眼查接口

	12、数据干预优化	// 已经上线 bxkc-admin 需要替换
		1、接口迁移
			mergeOrg		// 迁移 /dataIntervention/mergeOrg -> /enterpriseIntervention/mergeOrg
				// bxkc-admin 需要替换		// 已经替换
		2、增加日志
			deleteOrg\mergeOrg
				1、增加 enterprise_intervention_log
					create table enterprise_intervention_log(
						id bigint(20) primary key auto_increment comment '主键',
						name  varchar(255)  COMMENT '企业名称',
						update_type tinyint default 1 COMMENT '干预的类型 1为删除 2为合并',
						operating_time DATETIME  COMMENT '操作时间',
						remark varchar(5000)  COMMENT '备注'
					) comment '企业干预记录' 
					 ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
					 create index enterprise_intervention_log_name on enterprise_intervention_log (name);
					 create index enterprise_intervention_log_update_type on enterprise_intervention_log (update_type);

	4、 tw_indus_enterprise_score 的联系方式补上company_contact_dumplicate
	5、新建索引	
		enterprise.ru_wei_number
		enterprise.products
		enterprise.contacts.mobile_no
	3、dup_document ctime创建索引
	4、搜索Project					// 已处理		
			1、 10点左右停了开始新开gdb
				1、停了要素提取
					crontab -e
					#* * * * * /home/appuser/persistence-extract/start.sh
				2、立即备份// 原本是 星期二,星期五
				3、克隆（2020.12.26 8:39 克隆了 gds-bp150g6509035e9o 实例 ）
					1、 备份 2020-12-25 02:00:26~2020-12-25 04:50:29
					2、 4核 150G
					3、配置
						1、设置白名单
							172.16.147.49,172.16.147.59,172.16.147.48,172.16.147.15,172.16.147.13,172.16.146.244,172.16.147.68,172.16.74.77,172.16.147.67,172.16.146.241,172.16.146.242,172.16.147.62,172.16.146.243,172.16.147.60,127.0.0.1,183.6.26.209
						2、申请外网
						3、创建用户
				4、数据源重新连接
					1、我负责的
						要素提取					// 记住时间点(2020.12.26 9:16)， 已切换
						数据干预					// 已切换
						升级企业的招标数量			// 已切换
						document-push				// 没用到
						moose-enterprise-report-api	// 未上线
					2、其他所有应用			// 记得改服务器上的覆盖文件
						bxkc-pc
						bkkc-api
						xique
						zhongzhao
						标老板
						象查查
						佳豪，富哥自己负责的项目			// 他们说没有
						... 等等有用到 gdb的都得切换数据源
				5、开始项目合并的工作
					1、删除历史重复公告的程序				// 这个可以后一点
					1、删除实时要素提取对gdb操作的代码		// 这个需要切回旧的gdb时才做
					2、清空gdb的数据
					3、上线项目融合的代码					// 正式上线时间 2020-12-28 17:28:47.147 大于等于 1608893602 数量 79415384/小于 1608893602 数量：40625937
						优化		// 现在使用主表查询需要8天左右

				6、记得开启守护进程

	2、所有应用的gdb切回原来的实例
		1、看一下企业画像是否还用bidiId
		2、入库切回，去掉gdb的处理
			1、我负责的
				数据干预					// 已切换记住时间点(2021-01-11 20:22:32.760)
				要素提取					// 已切换
				升级企业的招标数量			// 已切换
				document-push				// 没用到
				moose-enterprise-report-api	// 未上线
			2、其他所有应用			// 记得改服务器上的覆盖文件
				bxkc-pc
				bkkc-api
				xique
				zhongzhao
				标老板
				象查查
				佳豪，富哥自己负责的项目			// 他们说没有
				... 等等有用到 gdb的都得切换数据源
	11、重新计算企业的招投标数量的定时器需要更新
		persistence-extract.UpgradeEnterpriseBidNumberSchedule // zhongzhao1
		bidi-data-intervention.recalculateBidNumber			   // zhongzhao2



中电系统建设工程有限公司
中国电子系统工程第二建设有限公司



private static boolean exceedRequestMaxLengthForUpdate(SyncClient client, RowUpdateChange rowUpdateChange, StringBuilder stringBuilder, Object value) {
	stringBuilder.append(value);
	if (stringBuilder.length() > requestMaxLength) {        // 超过了限制，则请求一次
		Condition condition = new Condition(RowExistenceExpectation.EXPECT_EXIST);
		rowUpdateChange.setCondition(condition);
		try {
			UpdateRowResponse updateRowResponse = client.updateRow(new UpdateRowRequest(rowUpdateChange));
		}catch (TableStoreException e){
			if ("OTSConditionCheckFail".equals(e.getErrorCode())) {     // 期望不一致返回 num=0即可

			} else {
				e.printStackTrace();
				throw e;
			}
		}
		return true;
	}
	return false;
}

