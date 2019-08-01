

妈妈开机密码：46454988l

1、j2se篇
	1、安装jre（java运行环境），这个其实jdk就有了，但是装这个有好处（他修改系统的注册表，不用我们手动配置lib(一些需要依赖的类库)的环境变量）
	2、打jar包：到你的你编译好的class文件的最上层目录运行：C:\jdk1.7.0_80\bin\jar -cvf xx.jar *.*  或者 C:\jdk1.7.0_80\bin\jar -cvf xx.jar *
	3、编译成.class文件，在命令行窗口下运行：C:\Program Files\Java\jdk1.7.0_80\bin\javac C:\javaCode\HelloWord.java
	4、执行编译后的文件，在命令行窗口下运行：java HelloWorld
		1、要运行你编译过后的文件，还要加一个环境变量：classpath=.;你编译后的class文件所在目录（点代表当前工作目录，但是jdk1.6已经把rt.jar和当前目录给设进去了）
		2、这个可以不用配置jdk的bin目录，他可能用的是jre的 java.exe
	5、 运行jar包 ： nohup java -jar dataApi-produce.jar  > dataApi.out  &		// 
		nohup	    - 意为后台不挂断运行，与是否账号退出无关 
		&		    - 代表后台运行  
		> log.file  - 输出的日志加入log.file，
		-Dspring.profiles.active=dev		// 还可以加入java的运行参数 比如 -Dspring.profiles.active=dev
2、j2ee篇
	1、看看hibernate的使用反射机制的源码
	2、区块链\比特币
	3、23中设计模式，掌握常用的
	5、java注解








1、数据库升级机制
	1、什么时候执行好呢
	2、分库
2、commons-codec是Apache开源组织提供的用于摘要运算、编码解码的包。常见的编码解码工具Base64、MD5、Hex、SHA1、DES等
3、字典研究，项目启动慢是不是他的原因，hanl
4、数据库的触发器
5、idea自动编译java,和忽略警告








	
		   
	

2019年后半年
	1、 nginx 、 springsecrity 、 Shiro 、 接口文档框架(Swagger) 、 java 性能测试工具
	2、redis的使用场景
	3、mongo的使用场景
	4、neo4j的使用场景
	5、 douker 自动化部署 


1、问题
	1、报错了 Caused by: com.fasterxml.jackson.databind.JsonMappingException: (was java.lang.NullPointerException) (through reference chain: java.util.HashMap["0"]->java.util.LinkedList[19])
			int count = organizationRepo.updateMergeStatusByIds(successOrgIds, 1);
	2、3的报错可能和我的线上项目差不多
	3、感觉 去除了多线程的原因 organization = organizationRepo.findByName(orgName); // 还是很多为空的，会导致创建关系不成功
	4、 relativeProject = projectRepo.save(endNode); // 如果是更新的话，为空的话会被清空吗？
	5、 redis端口/sentinel端口
		172.16.147.15:6300/26300
		172.16.57.178:6300/26300
		172.16.146.243:6300/26300


待做
	1、 dataApi接口 启动脚本
	2、 redis做集群
	3、 企业更新时间，再查一下天眼查的更新时间
	4、 neo4/es bidiId为空（因为在enterpriseprofile没找到对应的企业）
	5、 把合并的更新相关企业的改成 update对应的不要update全部，而且其他实体类要注意新增的字段
	6、 有空去看 http://www.bxkc.com/gjjs/B00208.html 这里的多选地区
 

1、PC端的企业画像功能
	
	 5、 问题
		1、 32.png 找企业搜索地区不准确（这个比较麻烦）
		2、 企业查询列表（查询地区要改一下）
		3、 ie乱码问题									// 解决了
		5、 搜索结果页面，没登录最多翻看两页
		6、 提示登录之后要回到原来的页面				// 解决了
		7、 右边的轮播不动


1、 ie乱码问题									// 解决了
2、 企业画像主页提示登录之后要回到原来的页面	// 解决了
3、 搜索结果页面，没登录最多翻看两页



1、前端经验
	1、 jsObj.forEach(fun{});不兼容ie8，但是jqObj.each();支持
	2、 idea安装less插件 https://www.cnblogs.com/liaojie970/p/6653593.html
	3、 A.jsp使用 struts 异步引用的jsp里面的引用的js代码，在A.jsp的其他js可以用使用





问题
	1、把 更新 Organization 节点的改了
	2、把 java的要素提取的war解压了





