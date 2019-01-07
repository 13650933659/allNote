



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
	







叔叔：1000+200







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
1、看一下springboot打出来的war里面的结构
1、maven编译一个java
1、@Autowired注解构造方法，还是使用类型吗？
1、看一下maven会不会把没用的bean编译进入target
1、数据库的触发器
1、idea自动编译java,和忽略警告
1、maven打包resource怎么看
1、idea的modify table研究，新增注释

1、redisson的分布式锁
1、fixedThreadPool.shutdownNow();的再次研究
1、SubProjExtrRslt subProjExtrRslt : subProjExtrRslts 如果没有指标段，是怎么样的呢？
1、线程安全问题

已解决
	1、@Service bean的名称默认是被注解类的名称吗？
		如果有指定@Service("xx")就是xx，没有就是类名首字母小写





1、今天的
	4、地区匹配的最好写一下测试用例
	4、mongodb的集群，写成自适应的（暂时不处理）
	5、mybatis plus




