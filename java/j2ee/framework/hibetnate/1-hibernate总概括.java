









1、什么是ORM框架：全称为(Object relation mapping)
	1、JDBC操作数据库很繁琐，简化编程
	2、Sql语句编写并不是面向对象的
	3、0/R Mapping跨越数据库平台
	4、ORM的框架有：Hibernate、toplink、jdo、ibatis、JPA(意愿统一天下)
2、hibernate332的环境搭建
	1、下载hibernate-distribution-3.3.2.GA-dist
	2、下载hibernate-annotations-3.4.0.GA
	3、下载slf4jl.5.8(slf4j日志框架自己的实现，还有很多框架的适配器)
	4、下载apache-log4j-1.2.15(log4j的框架)
	5、建User-library，命名为 hibernate,并加入相应的jar包
		1、引入mysql的驱动jar包
		2、使用hibernate框架必须要去hibernate-distribution-3.3.2.GA-dist获取如下jar包
			1、Hibernate3
			2、/required
			3、slf4-nop1.5.8.jar(slf4j日志框架自己的实现，不用去找这个，直接使用log4j的)
		3、在如果要使用注解，要去hibernate-annotations-3.4.0.GA获取相应的jar包
			1、Hibernate-annotaion.jar（实现）
			2、lib/ejb3-persistence.jar（ejb3的标准接口）
			3、lib/hibernate-common-annotations.jar(ejb3标准接口Hibernate帮他实现)
		4、搭建日志环境并配置显示DDL（data definition language）语句，使用log4j
			1、slf4j与log4j的关系：slf4j像是一个大管家，可以管理许多的日志框架，log4j是其中之一
			2、加入slf4j-log4j12-1.7.25.jar(适配器设计，把slf接口转成log4j的接口)
			3、加入log4j的jar包log4j-1.2.15.jar，去掉slf4-nop1.5.8.jar
			4、从hibernate/project/etc 目录拷贝log4j.properties
			5、査询hibernate文裆，日志部分，调整日志的输出策略，只看DDL语句和sql语句
		5、如果需要使用二级缓存
			1、加入：optional/ehcache/ehcache-1.2.3.jar（ehcache炸包）
			2、加入：commons-logging-1.1.1.jar（ehcache日志框架炸包）
3、hibernate项目开发的三种方式
	1、官方推荐的（映射文件->核心文件->自动创建DB表）
	2、中国最多人用的（DB表->自动创建映射文件和核心文件）
	3、第三种随便















