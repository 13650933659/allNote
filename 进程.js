



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
	
		   

年后
	1、springboot -> spring原理 -> 注解原理 -> 动态代理(jdk的、cglib的)
	2、后台需要提供一个模块可以修改数据的
	3、有空去看top的头信息
	5、 spring 集成 junit https://blog.csdn.net/qq_15204179/article/details/82975127
	

2019年后半年
	1、数据结构
	2、算法原理
	3、网络原理
	4、c/c++
	5、nginx、springsecrity、接口文档框架、jenkins、java 性能测试工具
	6、redis的使用场景
	7、mongo的使用场景
	8、neo4j的使用场景

百度会员 2019-03-26 到期

3、报错了 Caused by: com.fasterxml.jackson.databind.JsonMappingException: (was java.lang.NullPointerException) (through reference chain: java.util.HashMap["0"]->java.util.LinkedList[19])
		int count = organizationRepo.updateMergeStatusByIds(successOrgIds, 1);
4、3的报错可能和我的线上项目差不多

9、neo4j的集群配置一个，这个有点危险，有空改成多个
9、感觉 去除了多线程的原因 organization = organizationRepo.findByName(orgName); // 还是很多为空的，会导致创建关系不成功








