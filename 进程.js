


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
	5、 nginx  、 Shiro 、 java 性能测试工具
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
			1、 bxkc-api 的改造小七测出的问题，还未修复，修复完成把代码部署到线上
			2、有一个复杂的场景待研究和测试
			3、支付宝的 
				return_url 和 notify_url 有什么区别		// 测试环境要改一下测试库
			4、在会员等级那里配置产品改造一下
			5、获取权限那边可能要改一下了（扣除次数就是不要滞后）
			6、 sysMemberSystemService.delUserRedis(userId);	// (不要注释测试一下) 因为 RedisContantKey.USER_INFO + "_"+userid 存放的是 BaseUser ，有效期是一天，不能每次登陆清一次，如果有此次登陆有效的信息，不要放在这里 
	5、要素提取（mongo 和 neo4j 的改造）
		1、 统计之后的各个项目的维护工作
			1、数据干预
				1、删除接口
		
	6、告诉佳豪 他那个 对手检测 取数方式可能需要改动
		1、先通过 es 拿到 organization （监测的公司）
		2、然后通过neo4j 查到对应 project		// 到这里就可以了
		3、找根据docid 再去 solr 拿

	






2020-01-22 17:14:43.248  WARN 12106 [pool-7-thread-1] : 处理成功的数量0
2020-01-22 17:14:43.248  WARN 12106 [pool-7-thread-1] : 定时器结束!
2020-01-22 17:14:43.896  WARN 12106 [pool-7-thread-1] : ===============  第 2 个定时器运行结束，耗时 802 ================
2020-01-22 17:14:44.896  WARN 12106 [pool-7-thread-1] : ==================== hist第 3 个定时器开始运行 ====================
2020-01-22 17:14:44.899  WARN 12106 [pool-7-thread-1] : 开始查询,sys_extraction_temp.status=2，查询数量1
2020-01-22 17:14:44.903  WARN 12106 [pool-7-thread-1] : 查询完毕,结果数目1
2020-01-22 17:14:44.915  WARN 12106 [pool-7-thread-1] : sys_document查询完毕,结果数目1
2020-01-22 17:14:45.122  WARN 12106 [       90663749] : jsonObj=
{title=淮阳县教育体育局淮阳县文正幼儿园环境创设工程-竞争性谈判公告, doc_id=90663749, content=<div> 
 <div>
   淮阳县教育体育局淮阳县文正幼儿园环境创设工程-竞争性谈判公告 
 </div> 
 <table border="1" style="border-collapse:collapse;"> 
  <tbody> 
   <tr> 
    <td colspan="2">一、采购项目名称：淮阳县文正幼儿园环境创设工程</td> 
   </tr> 
   <tr> 
    <td colspan="2">二、采购项目编号：2020-01-14</td> 
   </tr> 
   <tr> 
    <td colspan="2">三、项目预算金额：187705元</td> 
   </tr> 
   <tr> 
    <td colspan="2"> 最高限价：187705元 </td> 
   </tr> 
   <tr> 
    <td colspan="2"> 
     <table border="1" style="border-collapse:collapse;"> 
      <tbody> 
       <tr> 
        <td>序号</td> 
        <td>包号</td> 
        <td>包名称</td> 
        <td>包预算（元）</td> 
        <td>包最高限价（元）</td> 
       </tr> 
       <tr> 
        <td>1</td> 
        <td>1</td> 
        <td>淮阳县文正幼儿园环境创设工程</td> 
        <td>187705</td> 
        <td>187705</td> 
       </tr> 
      </tbody> 
     </table> </td> 
   </tr> 
   <tr> 
    <td colspan="2">四、采购项目需要落实的政府采购政策</td> 
   </tr> 
   <tr> 
    <td colspan="2">执行《财政部国家发展改革委关于印发〈节能产品政府采购实施意见〉的通知》（财库[2004]185号）；执行《财政部环保总局关于环境标志产品政府采购实施的意见》（财库[2006]90号）；《财政部 发展改革委 生态环境部 市场监管总局 关于调整优化节能产品、环境标志产品政府采购执行机制的通知》（财库﹝2019﹞9号）；执行《政府采购促进中小企业发展暂
行办法》（财库[2011]181号）；执行《财政部、司法部关于政府采购支持监狱企业发展有关问题的通知》（财库[2014]68号）；执行《三部门联合发布关于促进残疾人就业政府采购政策的通知》（财库[2017]141号）。</td> 
   </tr> 
   <tr> 
    <td colspan="2">五、项目基本情况（包括数量、规格描述等）</td> 
   </tr> 
   <tr> 
    <td colspan="2">淮阳县文正幼儿园环境创设工程，详见附件清单</td> 
   </tr> 
   <tr> 
    <td colspan="2">六、供应商资格要求</td> 
   </tr> 
   <tr> 
    <td colspan="2">1、符合《中华人民共和国政府采购法》第二十二条要求；<br>2、投标人具有房屋建筑施工总承包叁级及以上资质，并在人员、设备、资金等方面具有相应的施工能力，其中，投标人拟派项目负责人须具备房屋建筑工程专业三级及以上注册建造师资格，具备有效的安全生产考核合格证书，且未担任其他在施建设工程项目的项目经理；<br>3、根据《关于在政府采购活动中查询及使用
信用记录有关问题的通知》(财库[2016]125号)的规定，对列入失信被执行人、重大税收违法案件当事人名单、政府采购严重违法失信行为记录名单的供应商，拒绝参与本项目政府采购活动；【查询渠道：“信用中国”网站（www.creditchina.gov.cn）、中国政府采购网（www.ccgp.gov.cn）】；<br>4、本项目不接受联合体投标。</td> 
   </tr> 
   <tr> 
    <td colspan="2">七、获取竞争性 谈判文件 </td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月17日&nbsp;&nbsp;至&nbsp;&nbsp;2020年01月21日（北京时间，法定节假日除外。）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源电子交易服务平台会员系统（网址http://www.zkggzyjy.gov.cn/）。</td> 
   </tr> 
   <tr> 
    <td colspan="2">3.方式：周口市公共资源电子交易服务平台会员系统（网址http://www.zkggzyjy.gov.cn/）凭CA数字证书报名和下载谈判文件。</td> 
   </tr> 
   <tr> 
    <td colspan="2">4.售价：60元</td> 
   </tr> 
   <tr> 
    <td colspan="2">八、响应文件提交的截止时间及地点</td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月22日10时00分（北京时间）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源交易中心四楼407房间</td> 
   </tr> 
   <tr> 
    <td colspan="2">九、响应文件的开启时间及地点</td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月22日10时00分（北京时间）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源交易中心四楼407房间</td> 
   </tr> 
   <tr> 
    <td colspan="2">十、发布公告的媒介及招标公告期限</td> 
   </tr> 
   <tr> 
    <td colspan="2"> 本次招标公告在《河南省政府采购网》《全国公共资源交易平台（河南省？周口市）》上发布。 公告期限为三个工作日2020年01月17日至2020年01月21日。 </td> 
   </tr> 
   <tr> 
    <td colspan="2">十一、联系方式</td> 
   </tr> 
   <tr> 
    <td>1. 采购人：淮阳县教育体育局</td> 
   </tr> 
   <tr> 
    <td>地址：淮阳县文正路西段</td> 
   </tr> 
   <tr> 
    <td>联系人：李静</td> 
   </tr> 
   <tr> 
    <td>联系方式：13949978176</td> 
   </tr> 
   <tr> 
    <td>2.采购代理机构：周口市政府采购中心</td> 
   </tr> 
   <tr> 
    <td>地址：周口市东新区光明路和政通路交叉口北100米路东</td> 
   </tr> 
   <tr> 
    <td>联系人：李龙龙</td> 
   </tr> 
   <tr> 
    <td>联系方式：0394-8106517</td> 
   </tr> 
   <tr> 
    <td colspan="2">发布人：李龙龙</td> 
   </tr> 
   <tr> 
    <td colspan="2">发布时间：2020年01月17日</td> 
   </tr> 
  </tbody> 
 </table> 
</div>}
2020-01-22 17:14:45.142  WARN 12106 [       90663749] : result=localvariable'data_res'referencedbeforeassignment
2020-01-22 17:14:45.142  WARN 12106 [       90663749] : jsonObj={title=淮阳县教育体育局淮阳县文正幼儿园环境创设工程-竞争性谈判公告, doc_id=90663749, content=<div> 
 <div>
   淮阳县教育体育局淮阳县文正幼儿园环境创设工程-竞争性谈判公告 
 </div> 
 <table border="1" style="border-collapse:collapse;"> 
  <tbody> 
   <tr> 
    <td colspan="2">一、采购项目名称：淮阳县文正幼儿园环境创设工程</td> 
   </tr> 
   <tr> 
    <td colspan="2">二、采购项目编号：2020-01-14</td> 
   </tr> 
   <tr> 
    <td colspan="2">三、项目预算金额：187705元</td> 
   </tr> 
   <tr> 
    <td colspan="2"> 最高限价：187705元 </td> 
   </tr> 
   <tr> 
    <td colspan="2"> 
     <table border="1" style="border-collapse:collapse;"> 
      <tbody> 
       <tr> 
        <td>序号</td> 
        <td>包号</td> 
        <td>包名称</td> 
        <td>包预算（元）</td> 
        <td>包最高限价（元）</td> 
       </tr> 
       <tr> 
        <td>1</td> 
        <td>1</td> 
        <td>淮阳县文正幼儿园环境创设工程</td> 
        <td>187705</td> 
        <td>187705</td> 
       </tr> 
      </tbody> 
     </table> </td> 
   </tr> 
   <tr> 
    <td colspan="2">四、采购项目需要落实的政府采购政策</td> 
   </tr> 
   <tr> 
    <td colspan="2">执行《财政部国家发展改革委关于印发〈节能产品政府采购实施意见〉的通知》（财库[2004]185号）；执行《财政部环保总局关于环境标志产品政府采购实施的意见》（财库[2006]90号）；《财政部 发展改革委 生态环境部 市场监管总局 关于调整优化节能产品、环境标志产品政府采购执行机制的通知》（财库﹝2019﹞9号）；执行《政府采购促进中小企业发展暂
行办法》（财库[2011]181号）；执行《财政部、司法部关于政府采购支持监狱企业发展有关问题的通知》（财库[2014]68号）；执行《三部门联合发布关于促进残疾人就业政府采购政策的通知》（财库[2017]141号）。</td> 
   </tr> 
   <tr> 
    <td colspan="2">五、项目基本情况（包括数量、规格描述等）</td> 
   </tr> 
   <tr> 
    <td colspan="2">淮阳县文正幼儿园环境创设工程，详见附件清单</td> 
   </tr> 
   <tr> 
    <td colspan="2">六、供应商资格要求</td> 
   </tr> 
   <tr> 
    <td colspan="2">1、符合《中华人民共和国政府采购法》第二十二条要求；<br>2、投标人具有房屋建筑施工总承包叁级及以上资质，并在人员、设备、资金等方面具有相应的施工能力，其中，投标人拟派项目负责人须具备房屋建筑工程专业三级及以上注册建造师资格，具备有效的安全生产考核合格证书，且未担任其他在施建设工程项目的项目经理；<br>3、根据《关于在政府采购活动中查询及使用
信用记录有关问题的通知》(财库[2016]125号)的规定，对列入失信被执行人、重大税收违法案件当事人名单、政府采购严重违法失信行为记录名单的供应商，拒绝参与本项目政府采购活动；【查询渠道：“信用中国”网站（www.creditchina.gov.cn）、中国政府采购网（www.ccgp.gov.cn）】；<br>4、本项目不接受联合体投标。</td> 
   </tr> 
   <tr> 
    <td colspan="2">七、获取竞争性 谈判文件 </td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月17日&nbsp;&nbsp;至&nbsp;&nbsp;2020年01月21日（北京时间，法定节假日除外。）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源电子交易服务平台会员系统（网址http://www.zkggzyjy.gov.cn/）。</td> 
   </tr> 
   <tr> 
    <td colspan="2">3.方式：周口市公共资源电子交易服务平台会员系统（网址http://www.zkggzyjy.gov.cn/）凭CA数字证书报名和下载谈判文件。</td> 
   </tr> 
   <tr> 
    <td colspan="2">4.售价：60元</td> 
   </tr> 
   <tr> 
    <td colspan="2">八、响应文件提交的截止时间及地点</td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月22日10时00分（北京时间）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源交易中心四楼407房间</td> 
   </tr> 
   <tr> 
    <td colspan="2">九、响应文件的开启时间及地点</td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月22日10时00分（北京时间）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源交易中心四楼407房间</td> 
   </tr> 
   <tr> 
    <td colspan="2">十、发布公告的媒介及招标公告期限</td> 
   </tr> 
   <tr> 
    <td colspan="2"> 本次招标公告在《河南省政府采购网》《全国公共资源交易平台（河南省？周口市）》上发布。 公告期限为三个工作日2020年01月17日至2020年01月21日。 </td> 
   </tr> 
   <tr> 
    <td colspan="2">十一、联系方式</td> 
   </tr> 
   <tr> 
    <td>1. 采购人：淮阳县教育体育局</td> 
   </tr> 
   <tr> 
    <td>地址：淮阳县文正路西段</td> 
   </tr> 
   <tr> 
    <td>联系人：李静</td> 
   </tr> 
   <tr> 
    <td>联系方式：13949978176</td> 
   </tr> 
   <tr> 
    <td>2.采购代理机构：周口市政府采购中心</td> 
   </tr> 
   <tr> 
    <td>地址：周口市东新区光明路和政通路交叉口北100米路东</td> 
   </tr> 
   <tr> 
    <td>联系人：李龙龙</td> 
   </tr> 
   <tr> 
    <td>联系方式：0394-8106517</td> 
   </tr> 
   <tr> 
    <td colspan="2">发布人：李龙龙</td> 
   </tr> 
   <tr> 
    <td colspan="2">发布时间：2020年01月17日</td> 
   </tr> 
  </tbody> 
 </table> 
</div>}
2020-01-22 17:14:45.160  WARN 12106 [       90663749] : result=localvariable'data_res'referencedbeforeassignment
2020-01-22 17:14:45.160  WARN 12106 [       90663749] : jsonObj={title=淮阳县教育体育局淮阳县文正幼儿园环境创设工程-竞争性谈判公告, doc_id=90663749, content=<div> 
 <div>
   淮阳县教育体育局淮阳县文正幼儿园环境创设工程-竞争性谈判公告 
 </div> 
 <table border="1" style="border-collapse:collapse;"> 
  <tbody> 
   <tr> 
    <td colspan="2">一、采购项目名称：淮阳县文正幼儿园环境创设工程</td> 
   </tr> 
   <tr> 
    <td colspan="2">二、采购项目编号：2020-01-14</td> 
   </tr> 
   <tr> 
    <td colspan="2">三、项目预算金额：187705元</td> 
   </tr> 
   <tr> 
    <td colspan="2"> 最高限价：187705元 </td> 
   </tr> 
   <tr> 
    <td colspan="2"> 
     <table border="1" style="border-collapse:collapse;"> 
      <tbody> 
       <tr> 
        <td>序号</td> 
        <td>包号</td> 
        <td>包名称</td> 
        <td>包预算（元）</td> 
        <td>包最高限价（元）</td> 
       </tr> 
       <tr> 
        <td>1</td> 
        <td>1</td> 
        <td>淮阳县文正幼儿园环境创设工程</td> 
        <td>187705</td> 
        <td>187705</td> 
       </tr> 
      </tbody> 
     </table> </td> 
   </tr> 
   <tr> 
    <td colspan="2">四、采购项目需要落实的政府采购政策</td> 
   </tr> 
   <tr> 
    <td colspan="2">执行《财政部国家发展改革委关于印发〈节能产品政府采购实施意见〉的通知》（财库[2004]185号）；执行《财政部环保总局关于环境标志产品政府采购实施的意见》（财库[2006]90号）；《财政部 发展改革委 生态环境部 市场监管总局 关于调整优化节能产品、环境标志产品政府采购执行机制的通知》（财库﹝2019﹞9号）；执行《政府采购促进中小企业发展暂
行办法》（财库[2011]181号）；执行《财政部、司法部关于政府采购支持监狱企业发展有关问题的通知》（财库[2014]68号）；执行《三部门联合发布关于促进残疾人就业政府采购政策的通知》（财库[2017]141号）。</td> 
   </tr> 
   <tr> 
    <td colspan="2">五、项目基本情况（包括数量、规格描述等）</td> 
   </tr> 
   <tr> 
    <td colspan="2">淮阳县文正幼儿园环境创设工程，详见附件清单</td> 
   </tr> 
   <tr> 
    <td colspan="2">六、供应商资格要求</td> 
   </tr> 
   <tr> 
    <td colspan="2">1、符合《中华人民共和国政府采购法》第二十二条要求；<br>2、投标人具有房屋建筑施工总承包叁级及以上资质，并在人员、设备、资金等方面具有相应的施工能力，其中，投标人拟派项目负责人须具备房屋建筑工程专业三级及以上注册建造师资格，具备有效的安全生产考核合格证书，且未担任其他在施建设工程项目的项目经理；<br>3、根据《关于在政府采购活动中查询及使用
信用记录有关问题的通知》(财库[2016]125号)的规定，对列入失信被执行人、重大税收违法案件当事人名单、政府采购严重违法失信行为记录名单的供应商，拒绝参与本项目政府采购活动；【查询渠道：“信用中国”网站（www.creditchina.gov.cn）、中国政府采购网（www.ccgp.gov.cn）】；<br>4、本项目不接受联合体投标。</td> 
   </tr> 
   <tr> 
    <td colspan="2">七、获取竞争性 谈判文件 </td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月17日&nbsp;&nbsp;至&nbsp;&nbsp;2020年01月21日（北京时间，法定节假日除外。）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源电子交易服务平台会员系统（网址http://www.zkggzyjy.gov.cn/）。</td> 
   </tr> 
   <tr> 
    <td colspan="2">3.方式：周口市公共资源电子交易服务平台会员系统（网址http://www.zkggzyjy.gov.cn/）凭CA数字证书报名和下载谈判文件。</td> 
   </tr> 
   <tr> 
    <td colspan="2">4.售价：60元</td> 
   </tr> 
   <tr> 
    <td colspan="2">八、响应文件提交的截止时间及地点</td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月22日10时00分（北京时间）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源交易中心四楼407房间</td> 
   </tr> 
   <tr> 
    <td colspan="2">九、响应文件的开启时间及地点</td> 
   </tr> 
   <tr> 
    <td colspan="2">1.时间：2020年01月22日10时00分（北京时间）</td> 
   </tr> 
   <tr> 
    <td colspan="2">2.地点：周口市公共资源交易中心四楼407房间</td> 
   </tr> 
   <tr> 
    <td colspan="2">十、发布公告的媒介及招标公告期限</td> 
   </tr> 
   <tr> 
    <td colspan="2"> 本次招标公告在《河南省政府采购网》《全国公共资源交易平台（河南省？周口市）》上发布。 公告期限为三个工作日2020年01月17日至2020年01月21日。 </td> 
   </tr> 
   <tr> 
    <td colspan="2">十一、联系方式</td> 
   </tr> 
   <tr> 
    <td>1. 采购人：淮阳县教育体育局</td> 
   </tr> 
   <tr> 
    <td>地址：淮阳县文正路西段</td> 
   </tr> 
   <tr> 
    <td>联系人：李静</td> 
   </tr> 
   <tr> 
    <td>联系方式：13949978176</td> 
   </tr> 
   <tr> 
    <td>2.采购代理机构：周口市政府采购中心</td> 
   </tr> 
   <tr> 
    <td>地址：周口市东新区光明路和政通路交叉口北100米路东</td> 
   </tr> 
   <tr> 
    <td>联系人：李龙龙</td> 
   </tr> 
   <tr> 
    <td>联系方式：0394-8106517</td> 
   </tr> 
   <tr> 
    <td colspan="2">发布人：李龙龙</td> 
   </tr> 
   <tr> 
    <td colspan="2">发布时间：2020年01月17日</td> 
   </tr> 
  </tbody> 
 </table> 
</div>}
2020-01-22 17:14:45.176  WARN 12106 [       90663749] : result=localvariable'data_res'referencedbeforeassignment










