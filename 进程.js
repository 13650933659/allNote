

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
	1、看看hibernate的使用反射机制的源码
	2、区块链\比特币
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

2、小七给你的标讯快车的问题
3、修复标讯快车的修改等级的配置价格那里（参考梦辉的）
	1、更新时需要更新 sysLevelPrice 表
	


1、 待会修改 bxkc-api 的 judgeUserHavePermission
	levelResult.setStatus(levelResult.getResidue() > 0 ? SysMemberLevelResult.ISOK : SysMemberLevelResult.NO_HAVE_NUM);     // 如果次数已经用完了，需要给一个标记


http://localhost:8888




图片 160
手机淘宝到
360手机助手
喜乐动（备份）
QQ
美颜相机
微信
广东移动
豆果美食
百度地图
掌上生活（招商）
支付宝
全名k个
酷我音乐
钉钉
度小满金融（可要看可不要）
中国工商银行
微众银行

铁路12306
润钱包
中国建设银行
蛋壳公寓
京东
京东金融




写到推荐信息







function getData(){
    var bidiId=$("#bidiId").val();
    var residueDegree=$("#residueDegree").val();
    var seen=$("#seen").val();//是否已经看过
    $.ajax({
        type: "POST",
        url: basePath+"/zhaotoubiaohappening!getData.do",
        data: {
            bidiId:bidiId
        },
        success: function(msg){
            var jsonObject=msg.data;
            console.info(jsonObject);
            if(jsonObject != undefined && jsonObject != null){
                if(jsonObject.zhaobiaoProject.type=='zhaobiao'){
                    var zhaoList="";
                    zhaoList+="<h3 class=\"fl-title\">"
                    zhaoList+="<span class=\"text\">"+jsonObject.zhaobiaoProject.company+"已发布的招标项目 </span>"
                    if(jsonObject.zhaobiaoProject.list.length>0){
                        if (residueDegree=='0' && seen=='false'){
                            zhaoList+="<span class=\"count\">( <strong>***</strong> ) </span>"
                        }else{
                            zhaoList+="<span class=\"count\">( <strong>"+jsonObject.zhaobiaoProject.total+"</strong> ) </span>"
                        }
                        zhaoList+="<a class=\"more pull-right \" target=_blank style=\"cursor:pointer;\" onclick=\"getdeatil('zhaobiao')\">more</a>"
                    }else{
                        zhaoList+="<span class=\"count\">( <strong class=\"grey\">0</strong> ) </span>"
                        zhaoList+="<a class=\"more pull-right hide\" target=_blank style=\"cursor:pointer;\">more</a>"
                    }
                    zhaoList+="</h3>"
                    if (jsonObject.zhaobiaoProject.list.length>0) {
                        var zhaoData=jsonObject.zhaobiaoProject.list;
                        zhaoList+="<div class=\"border company-list\">"
                        zhaoList+="<ul>"
                        for(var i=0;i<zhaoData.length;i++){
                            zhaoList+="<li>"
                            zhaoList+="<strong class=\"city\">"+zhaoData[i].city+"</strong>"
                            zhaoList+="<strong class=\"type\">"+zhaoData[i].channelName+"</strong>"
                            zhaoList+="<a class=\"text pull-left\" target=_blank href=\""+basePath+"/info-"+zhaoData[i].projectId+".html\">"+zhaoData[i].projectName+"</a>"
                            zhaoList+="<span class=\"date pull-right\">"+zhaoData[i].pageTime+"</span>"
                            zhaoList+="</li>"
                        }
                        zhaoList+="</ul></div>"
                    }else{
                        zhaoList+="<div class=\"item-noContent\">"
                        zhaoList+="<img src=\""+basePath+"/client/img/qyhx/hx_wsj01.png\" alt=\"\">没有查找到相关的信息</div>"
                    }
                    $("#zhaoList").html(zhaoList);
                }
                if(jsonObject.zhongbiaoProject.type=='zhongbiao'){
                    var zhongList="";
                    zhongList+="<h3 class=\"fl-title\">"
                    zhongList+="<span class=\"text\">"+jsonObject.zhaobiaoProject.company+"已参与的中标项目 </span>"
                    if(jsonObject.zhongbiaoProject.list.length>0){
                        if (residueDegree=='0' && seen=='false') {
                            zhongList += "<span class=\"count\">( <strong>***</strong> ) </span>"
                        }else{
                            zhongList += "<span class=\"count\">( <strong>" + jsonObject.zhongbiaoProject.total + "</strong> ) </span>"
                        }
                        zhongList+="<a class=\"more pull-right \" target=_blank style=\"cursor:pointer;\" onclick=\"getdeatil('zhongbiao')\">more</a>";
                    }else{
                        zhongList+="<span class=\"count\">( <strong class=\"grey\">0</strong> ) </span>"
                        zhongList+="<a class=\"more pull-right hide\" target=_blank style=\"cursor:pointer;\">more</a>";
                    }
                    zhongList+="</h3>"
                    if (jsonObject.zhongbiaoProject.list.length>0) {
                        var zhongData=jsonObject.zhongbiaoProject.list;
                        zhongList+="<div class=\"border company-list\">"
                        zhongList+="<ul>"
                        for(var i=0;i<zhongData.length;i++){
                            zhongList+="<li>"
                            zhongList+="<strong class=\"city\">"+zhongData[i].city+"</strong>"
                            zhongList+="<strong class=\"type\">"+zhongData[i].channelName+"</strong>"
                            zhongList+="<a class=\"text pull-left\" target=_blank href=\""+basePath+"/info-"+zhongData[i].projectId+".html\">"+zhongData[i].projectName+"</a>"
                            zhongList+="<span class=\"date pull-right\">"+zhongData[i].pageTime+"</span>"
                            zhongList+="</li>"
                        }
                        zhongList+="</ul></div>"
                    }else{
                        zhongList+="<div class=\"item-noContent\">"
                        zhongList+="<img src=\""+basePath+"/client/img/qyhx/hx_wsj01.png\" alt=\"\">没有查找到相关的信息</div>"
                    }
                    $("#zhongList").html(zhongList);
                }
            }
        }
    });
}

public String getData() throws ParseException {
		JSONObject param=new JSONObject();
		param.put("bidiId",bidiId);
		param.put("pageSize",10);
		SysLoginUser lgUser = getLoginUser();
		UserLoginInfo ulInfo = lgUser.getUserLoginInfo();
		if (ulInfo != null && ulInfo.getUserid() != null) {
			param.put("userId",ulInfo.getUserid());
		}
        System.out.println(DateUtil.format(new Date(),"yyyy-MM-dd"));
		param.put("beginTime","2010-01-01");
		param.put("endTime",DateUtil.format(new Date(),"yyyy-MM-dd"));
		//已发布的招标信息
		zhaobiaoProject =zhaoZhongBiaoProjectService.zhaoZhongBiaoProject(param,ZHAOBIAO);
		//参与中标信息
		zhongbiaoProject =zhaoZhongBiaoProjectService.zhaoZhongBiaoProject(param,ZHONGBIAO);
		JSONObject object=new JSONObject();
		object.put("zhaobiaoProject",zhaobiaoProject);
		object.put("zhongbiaoProject",zhongbiaoProject);
//		object.put("recentZhaoBiao",recentZhaoBiao);
		sendToJson(object, true, "查询成功");
		return NONE;
	}


	public  String getAreaList(String list){
		String areas="";
		if(StringUtils.isNotBlank(list)){
			JSONArray areaList = JSONArray.fromObject(list);
			if(null!=areaList&&areaList.size()>0){
				String province ;
				String city;
				for(int i=0;i<areaList.size();i++){
					JSONObject area = areaList.getJSONObject(i);
					if (area != null) {
						province = area.getString("area");
						city =area.getString("city");
						if("全国".equals(province)){
							BxkcAreaset areaVo = new BxkcAreaset();
							areaVo.setCtype("20");
							List<BxkcAreaset> areasetList = bxkcAreasetService.getBxkcAreasets("", "", areaVo);
							if(areasetList.size()>0){
								for(BxkcAreaset bxkcAreaset:areasetList){
									if("".equals(areas)){
										areas=getArea(bxkcAreaset.getCname());
									}else{
										areas=areas+","+getArea(bxkcAreaset.getCname());
									}
								}
							}
							return areas;
						}
						if("".equals(city)){
							if("".equals(areas)){
								areas=getArea(province);
							}else{
								areas=areas+","+getArea(province);
							}
						}else{
							if("".equals(areas)){
								areas=city;
							}else{
								areas=areas+","+city;
							}
						}
					}
				}
			}
		}
		return areas;
	}

public String getArea(String cname){
		if("".equals(cname)||cname==null){
			return "";
		}
		long parentId = -1;
		if (!StringUtil.isEmpty(cname)) {
			BxkcAreaset areaVo = new BxkcAreaset();
			areaVo.setCname(cname);
			areaVo.setCtype("20");
			List<BxkcAreaset> list = bxkcAreasetService.getBxkcAreasets("", "", areaVo);
			if (list != null && list.size() > 0) {
				parentId = list.get(0).getId();
			}
		}
		BxkcAreaset vo = new BxkcAreaset();
		vo.setParentid(parentId);
		List<BxkcAreaset> areaList = bxkcAreasetService.getBxkcAreasets("", "", vo);
		String areas="";
		if(areaList.size()>0){
			for (BxkcAreaset bxkcAreaset:areaList){
				if("".equals(areas)){
					areas=bxkcAreaset.getCname();
				}else{
					areas=areas+","+bxkcAreaset.getCname();
				}
			}
		}
		return areas;

	}


function getTable(data) {
        if(!$.isEmptyObject(data)){
            var nums=data.zbNums.split(",");
            var zbSumPrice=data.zbSumPrice.split(",");
            var dataList="";
            dataList+="<tr><th>1月</th><th>2月</th><th>3月</th><th>4月</th><th>5月</th><th>6月</th><th>7月</th><th>8月</th><th>9月</th><th>10月</th><th>11月</th><th>12月</th></tr>"
            dataList+="<tr>";
            for(var i=0;i<nums.length;i++){
                dataList+="<td>";
                dataList+="<span>中标</span>";
                dataList+="<strong>"+nums[i]+"</strong>个";
                dataList+="</td>";
            }
            dataList+="</tr>";
            dataList+="<tr class=\"tr-line\">";
            for(var i=0;i<nums.length;i++){
                dataList+="<td>";
                dataList+="<span>金额</span>";
                dataList+="<span><strong>"+zbSumPrice[i]+"</strong>万</span>";
                dataList+="</td>";
            }
            dataList+="</tr>";
        }else{
            dataList+="<tr>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>中标</span>\n" +
                "                                        <strong>0</strong>个\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr class=\"tr-line\">\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                    <td>\n" +
                "                                        <span>金额</span>\n" +
                "                                        <strong>0</strong>万\n" +
                "                                    </td>\n" +
                "                                </tr>";
        }
        $("#tableId").html(dataList)
    }



relationTendereeAndCompetitor
relationWinTendererAndTenderer

relationWinTenderer
tenderer


portrait.js





61407649872728064



中招项目的问题
	1、 中标业绩-整体趋势 选择年份换成动态获取

	2、[ 信息检索管理 ] -[ 信息检索列表 ] 和 喜鹊首页的数据对不上



http://www.bxkc.com/bdqyhx/115234022961434624/zhaobiao/zbxm.html

http://www.bxkc.com/bdqyhx/115234022961434624/zhongbiao/zbxm.html








1、解决jira问题 PC-109 1.企业画像详情》已发布的招标项目与企业已发布的招标项目列表的数据对应不上
2、解决jira问题 PC-108 1.企业画像详情》已参与的中标项目拿错数据了



http://www.bxkc.com/bdqyhx/115234022961434624/zhaobiao/zbxm.html






request.loadpicture_a00201
