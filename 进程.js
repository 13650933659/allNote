


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
			nohup java -jar persistence-hist.jar >> log.out &

			nohup java -jar app.jar >> app.log &
		2、参数说明
			nohup	    - 意为后台不挂断运行，与是否账号退出无关 
			&		    - 代表后台运行  
			>> log.file - 输出的日志追加入log.file，
			-Dspring.profiles.active=dev		// 加入java的运行参数 好像也可以 --moose.task.maxThreadCount=4 这样
	6、 增量jar https://www.cnblogs.com/wangyayun/p/11725351.html
		1、把源jar包cp到一个空文件夹里，解压	jar -xf persistence-extract-20201112074211.jar
		2、进入BOOT-INF/class，替换
		3、把该文件夹里的源jar包删除			rm -rf persistence-extract-20201112074211.jar
		4、重新打包								jar -cfM0 persistence-extract-20201112074211.jar *
		5、把打好的jar包cp到启动目录，启动就ok
	7、  powerDesigner关联数据库显示中文注释  https://www.cnblogs.com/timeflying/p/11409416.html

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
	8、 wireshark抓包工具详细说明及操作使用 https://blog.csdn.net/qq78069460/article/details/79153895





3、待做
	1、 redis做集群
	2、要素提取（mongo 和 neo4j 的改造）
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
			4、bxkc-api
			5、xique
			6、zhongzhao
			7、可能 富哥、佳豪、江哥 自己负责的项目有用到 mongo 和 neo4j 的



1、临时记录
	1、整理支付流程（移动端、pc端、H5）
	2、去重项目的优化(当天的公告一条条的处理)
	5、抽空去了解一下 frp和nginx的https请求
	6、spring的定时器第一次立即运行，怎么配置
	7、改造之后redis那个 del delObject batchDel 三个方法有时会不生效


1、修改 bxkc/xique 的用户缓存(登录对应的后台)
	http://admin.bidizhaobiao.com/basedata/member_list!redisGet.do?key=user_info_00046644120201027
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=user_info_00038913820201106&type=object
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=seo_hot_focus&type=batch
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=user_info_&type=batch
	
	// 清菜单
	http://admin.bidizhaobiao.com/basedata/member_list!redisDel.do?key=bxkc_back_user_all_memu_000370740&type=object


	http://admin.xqzhaobiao.com/basedata/member_list!redisGet.do?key=user_info_00044996320201117
	http://admin.xqzhaobiao.com/basedata/member_list!redisDel.do?key=user_info_00047034520201110&type=object
	


1、补上
	自动放弃定时器要去掉删除领取记录
	



			

1、数据迁移问题汇总
	1、数据处理
		1、数据分段
			1、之前的数据 -> 2020-04 
			2、2020-04(98759403 -> 99999903)    -> 某个时间点(X) 
				表 _20 已同步,startDocid=100000001 -> 105000000
				表 _21 -, 105000001 -> 110000000
				表 _22 -, 110000001 -> 113055364 -> 115000000
				表 _23 -, 115000001 -> 115773215 -> 116395814 -> 116678124(以这个为点)

			3、 116678124          -> 未知


	1、把数据搬到 document2 to document(下面已处理完成)
		1-40000000			zhong1	// 完成了
		40000001-80000000	zhong2	
		80000001-117089968	114
		第二次
			117089968 - 118491094	zhong1
			118491094 - 118596067	zhong1

			118596067 - 118689952	zhong1
			
	2、企业里面的招投标数据和取ots对不上
	3、获取企业时就要计算招投标数量
	4、手动把 document_extract2 的数据改为待提取状态
		98759406 -> 116939615
			98759406 -> 98779406 -> 100000000 -> 116939615
	5、数据干预的接口和admin的数据干预
		1、招标信息发布(原逻辑)
			1、后台的项目发布和审核
				1、发布：新增 sys_document_second
				2、更新：更新 sys_document_second	// 发布后再更新， sys_document_second.docstatus=待审核 ，可以再做审核
				3、删除：删除 sys_document_second 和 sys_document
				4、审核：都会更新 sys_document_second 
					1、通过：新增 sys_document ，如果存在则更新
					2、驳回：删除 sys_document
			2、前台的项目发布
				1、发布：新增 sys_document_second
				2、更新：更新 sys_document_second
				3、删除：更新 sys_document_second.docstatus=22 ，但是还可以再次编辑再次审核 ，并且删除已生成的 sys_document
		2、删除公告		// 记日志
			1、删除ots.document
			2、删除对应的gdb.Document ，如果对应的 Project 没有 Document 则需要删除此 Project，如果有删除Project则需要把相关企业重新计算招投标数量
			3、删除成功之后向队列发送待调用 启信宝 删除接口的消息队列
			4、记日志
		3、更新
			改了之后直接更新 document.status=[1,50] 既可
		4、修改企业
			1、ots
				把 B 的招投标换成 A
			2、gdb
				把 B 和P的所有关系，挂到 A

	6、环境
		zhong1
		zhong2
			实时要素提取
			数据干预项目
				重新计算企业的招投标

	

1、尽量不要动ots.document的数据
2、历史要素提取的合并去掉了，update.ots.document.status=[51,100]的动作，会生效吗






3、报表的开始
4、bxkc-admin的删除接口地址替换了吗
5、util.GremlinUtil.deleteOrUpdate(GremlinUtil.java:222)	// 报错了
6、跑的时候打开计算企业的招投标数量的


7、处理历史数据 document_extract(新的gdb实例) - 2020-10-30 15点 完成时间  2020-11-06 10:12:49 大概6天
	最多docid 118601790 数量(87692898)
	118601790 -> 120795618
8、把上的时间差数据补上	
	1、把 document(docid > 118601790) -> document3
	2、启用 历史数据的要素提取






8、数据做升级
	bxkc_mybrowse_org



10、bxkc-amdin不生效？顺便把修改补上
11、公告去重没做
12、119794035是要素提取破坏了标签吗？
13、http://192.168.2.170:4999/ showdoc
14、周总反馈 江苏省 不是一家企业
15、公告去重，项目去重


1、测试企业合并
	1、数据干预上线 -> bxkc-admin 上线
		mergeOrg.url=http://172.16.147.49:8002/dataIntervention/mergeOrg
	3、上午评审的需求都列下功能



1、共同竞标的项目
	都是要取gdb

2、换成新的数据库
	1、实时的要素提取换成新库
	2、document.maxDocid 记下 118601790 -> 120795618->122178814
	3、数据同步到document3
	4、启动历史要素提取
	5、启动历史入库		// 有时拿不到数据
	6、重新部署所有端
		1、bxkc-admin 替换common.properties
			mergeOrg.url=http://172.16.147.49:8002/dataIntervention/mergeOrg
		2、bxkc-pc/bxkc-api
			替换gdb.yaml

3、bidi-data-intervention做自动化部署和守护进程，补上日志
4、persistence-extract做自动化部署和守护进程
5、gdb慢的问题
6、合并脏数据
7、公告去重、项目去重
8、专业版后台
	企业画像-协助
9、把documentUtils抽取
	easy-tablestore
	
	com.bidizhaobiao
	tablestore-util

com.bidizhaobiao.tablestoreutil.entity.enterprise









1、全局替换
	import com.i2f.edoc.ots.util.DocumentUtil;
	import com.bidizhaobiao.tablestoreutil.document.DocumentUtil;



	import com.i2f.edoc.utils.search.SearchCondition;
	import com.bidizhaobiao.tablestoreutil.document.search.SearchCondition;

	import com.i2f.entity.Doc;
	import com.bidizhaobiao.tablestoreutil.vo.Doc;


	import site.dunhanson.aliyun.tablestore.entity.bidi.Document;
	import com.bidizhaobiao.tablestoreutil.entity.Document;


	import site.dunhanson.aliyun.tablestore.entity.bidi.enterprise.Enterprise;
	import com.bidizhaobiao.tablestoreutil.entity.enterprise.Enterprise;


	import site.dunhanson.aliyun.tablestore.entity.bidi.SubDocument;
	import com.bidizhaobiao.tablestoreutil.entity.SubDocument;


	import com.i2f.edoc.utils.pages.DatabasePaginatedList;
	import com.bidizhaobiao.tablestoreutil.util.DatabasePaginatedList;



2、清除redis缓存
	BXKC_PC:HOME_PAGE:ZBZS:TYPE:40
	BXKC_PC:HOME_PAGE:DOCUMENTS:KEYWORDS:邀标		// 二次删除 10分钟

1、今天工作
	1、tablestoreutil jar
	2、pc改造 切分分支(upgradeOtsutil)
	3、api和admin的改造
	4、学习vue
