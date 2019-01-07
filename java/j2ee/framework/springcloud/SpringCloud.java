




问题
	1、Ribbon可以自动以自己的负载均衡算法
	2、Ribbon的默认的负载均衡算法是轮询，但是我想改怎么改呢





主讲：周阳
	1、五大神兽
	2、脑图：少了服务发现和Config，少了Ribbon的核心组件IRule(LB算法)，还有自定义IRule
	3、面试题：Eureka=AP和zookeeper=CP的比较
	4、面试题：关系型数据库：(A(Atomicity)C(Consistency)I(Isolation)D(Durability)和 NoSql：C(Consistency)A(Availability)P(Partition Tolerance))

1、Eureka的服务注册与发现
	1、Eureka是什么
		1、Eureka是Netflix的一个子模块，也是核心模块之一。Eureka是一个基于REST的服务，用于服务的注册与发现。
	2、Eureka的集群配置(eureka集群和service provider集群)
		1、参考：microservicecloud-eureka-7001/7002/7003和microservicecloud-provider-dept-8001/8002/8003
2、Ribbon/Feign的负载均衡
	1、Ribbon：是基于Netflix Ribbon实现的一套客户端负载均衡的工具(LB)。
	2、Feign
	3、Ribbon和Feign区别
		1、Ribbon他是做负载均衡的，他支持多种负载算法，比如轮询和随机等等，也可以支持自定义的负载算法，
		2、Feign=Ribbon+RestTemplate，Feign也支持springmvc的@RequestMapping注解，Feign面向接口编程，使用起来比Ribbon更加简洁
3、Hystrix的服务熔断和服务降级(避免异常的蔓延，提高服务的高可用性)
	1、服务熔断：A服务调用B服务，B出现异常，此时Hystrix他会帮我做熔断处理（返回A可以处理的结果）
	2、服务降级：张三在维护A服务程序，张三有事请假一个星期，老板不得不忍痛把B停掉，还有想访问B的客户端，
	那么Hystrix就不会调用B，返回一个调用者可处理的结果(这个处理是在客户端处理的)
4、zuul网关
	1、参考项目：microservicecloud-zuul-gateway-9527
	2、zuul的作用：路由+代理+过滤三大功能
		1、路由
			1、使用了路由地址变化：localhost/dept/get/2 -> myzuul:9527/微服务名称/dept/get/2  // 但是之前的地址还能访问吗？都可以
		2、代理
			1、代理之后的地址访问：myzuul.com:9527/microservicecloud-dept/dept/get/2 -> myzuul.com:9527/myDept/dept/get/2  // 都可以访问
		3、过滤
			1、难道这个是忽略的作用，有空去看看
5、集中化配置config
	1、参考下列四个项目：
		microservicecloud-config-3344
		microservicecloud-config-client-3355
 		microservicecloud-config-dept-client-8001
 		microservicecloud-config-eureka-client-7001
6、Stream整合消息中间件
	1、参考：spring-msg-server\spring-msg-producer\spring-msg-consumer
	2、参考杨恩雄的
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
7、bus总线
	1、他用于实现微服务之间的通信，整合了 java的事件处理机制和消息中间件消息的发送和接受
	2、bus总线就像消息的生产者，各个微服务就像消息的消费者，然后我refresh这个bus微服务，
	他就会发消息给rabbitMQ服务器，然后各个消费者就会自动去叫配置中心微服务去远程获取最新配置文件



面试题
	1、什么是“微服务”和“微服务架构”
		1、微服务：是一个小型的服务程序，可以理解成一个Maven module，他关注的是一个点，他可以运行在单独的进程中，甚至拥有自己的数据库
		2、微服务架构：是一种架构模式，他把一个大型的服务程序，按业务拆分成若干个小服务（即微服务），各自运行在单独的进程中，并且他们之间可以互相通信
	2、什么是spring cloud，和Dubbo的比较
		1、spring cloud他是微服务架构的实现，他集成了多种微服务架构技术，比如Eureka(做服务注册和发现的)、Ribbon\Feign(做负载均衡)、
		Hystrix(做服务熔断和服务降级的)、zuul(做微服务网关的)、config(微服务配置管理)等等。
	3、Ribbon和Feign区别
		1、Ribbon他是做负载均衡的，他支持多种负载算法，比如轮询和随机等等，也可以支持自定义的负载算法，
		2、Feign=Ribbon+RestTemplate，Feign也支持springmvc的@RequestMapping注解，Feign面向接口编程，使用起来比Ribbon更加简洁
	4、Hystrix的服务熔断和服务降级(避免异常的蔓延，提高服务的高可用性)
		1、服务熔断：A服务调用B服务，B出现异常，此时Hystrix他会帮我做服务器熔断处理
		2、服务降级：比如张三在维护A服务程序，张三有事请假一个星期，老板不得不忍痛把B停掉，此时还有想访问B的客户端，
		那么Hystrix就不会调用B，而是返回一个调用者可处理的结果(这个处理是在客户端处理的)
	5、spring boot和spring cloud的关系
		1、SpringBoot专注于快速方便的开发单个个体微服务。
		2、SpringCloud是专注服务的治理，SpringBoot可以离开SpringCloud独立使用开发项目，但是SpringCloud离不开SpringBoot.
