




问题
	1、Ribbon可以自动以自己的负载均衡算法
	2、Ribbon的默认的负载均衡算法是轮询，但是我想改怎么改呢





主讲：周阳
	1、五大神兽
	2、脑图：少了服务发现和Config，少了Ribbon的核心组件IRule(LB算法)，还有自定义IRule
	3、面试题：Eureka=AP和zookeeper=CP的比较
	4、面试题：关系型数据库：(A(Atomicity)C(Consistency)I(Isolation)D(Durability)和 NoSql：C(Consistency)A(Availability)P(Partition Tolerance))

主讲：杨恩雄
	1、rabbitmq
		1、下载安装：Erlang，然后配置ERLANG_HOME
		2、下载安装：rabbitmq-server-3.6.11.exe
			1、查看插件：rabbitmq-plugins list
			2、开启管理插件：rabbitmq-plugins enable rabbitmq_management，然后就可以使用http://localhost:15672/在浏览器管理了，默认账号和密码是guest=guest
	2、kafka(注意这两个软件安装的目录不要太长了)
		1、解压：zookeeper-3.4.6.tar.gz和解压：kafka_2.11-0.11.0.0.tgz
		2、把C:\zookeeper-3.4.10\conf\zoo_sample.cfg 复制一份改名为zoo.cfg放在此目录
		3、进入C:\zookeeper-3.4.10\bin，启动zookeeper服务输入：zkserver，如果启动成功是占用2181端口
		4、进入C:\kafka_2.11-2.0.0\bin\windows，启动kafka输入：kafka-server-start ../../config/server.properties如果启动成功是占用9092端口
		6、如果有生产者向kafka发送消息了(topic)，查看：进入C:\kafka_2.11-2.0.0\bin\windows输入：kafka-topics --list --zookeeper localhost:2181

理解
	1、什么是“微服务”和“微服务架构”
		1、微服务：是一个小型的服务程序，可以理解成一个Maven module，他关注的是一个点，他可以运行在单独的进程中，甚至拥有自己的数据库
		2、微服务架构：是一种架构模式，他把一个大型的服务程序，按业务拆分成若干个小服务（即微服务），各自运行在单独的进程中，并且他们之间可以互相通信
	2、什么是spring cloud，和Dubbo的比较
		1、spring cloud他是微服务架构的实现，他集成了多种微服务架构技术，比如Eureka(做服务注册和发现的)、Ribbon\Feign(做负载均衡)、
		Hystrix(做服务熔断和服务降级的)、zuul(做微服务网关的)、config(微服务配置管理)等等。
	3、Ribbon和Feign区别
		1、Ribbon他是做负载均衡的，他支持多种负载算法，比如轮询和随机等等，也可以支持自定义的负载算法，
		2、Feign=Ribbon+RestTemplate，继承了Ribbon和Rest风格地远程的调用，Feign也支持springmvc的@RequestMapping注解，Feign面向接口编程，使用起来比Ribbon更加简洁
	4、Hystrix的服务熔断和服务降级
		1、服务熔断：比如A服务调用B服务，B出现异常，此时Hystrix他会帮我做服务器熔断处理，避免异常的蔓延，提高服务的高可用性
		2、服务降级：比如张三在维护A服务程序，突然有一天，张三有事请假一个星期，老板不得不忍痛把B停掉，此时还有访问B的客户端，
		那么Hystrix就会在做一些容错处理，返回一个调用者可处理的结果(这个处理是在客户端处理的)，避免故障的蔓延，提高服务的高可用性
	5、spring boot和spring cloud的关系
		1、SpringBoot专注于快速方便的开发单个个体微服务。
		2、SpringCloud是关注全局的微服务整理、协调的框架，SpringBoot可以离开SpringCloud独立使用开发项目，但是SpringCloud离不开SpringBoot.
 



		
