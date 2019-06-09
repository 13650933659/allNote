



1、 springcloud 实战
	1、Eureka
		1、引入 eureka-server 组件
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-starter-eureka-server</artifactId>
			</dependency>
		2、yml配置
			server: 
			  port: 7001
			 
			eureka: 
			  instance:
				hostname: eureka7001.com 			#eureka服务端的实例名称
			  client: 
				register-with-eureka: false     	#false表示不向注册中心注册自己。
				fetch-registry: false     			#false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
				service-url: 
				  #单机 defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
				  defaultZone: http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/	# 其他的eureka服务，比如7002 需要配置成 7001,7003
		3、 SpringBoot主程序 启用 @EnableEurekaServer	// EurekaServer服务器端启动类,接受其它微服务注册进来
	2、 其他的微服务需要注册到 Eureka
		1、 比如 provider 注册到 Eureka
			1、 eureka客户端支持
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-starter-eureka</artifactId>
				</dependency>
			2、 yml配置
				server: port: 8001
				spring: application: name: microservicecloud-dept 							# 服务名称
				 
				eureka:
				  client: #客户端注册进eureka服务列表内
					service-url: 
					  #单机版的：defaultZone: http://localhost:7001/eureka  
					  defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/	#集群的
				  instance:
					instance-id: microservicecloud-dept8001					#微服务的别名
					prefer-ip-address: true     							#访问路径可以显示IP地址
			 3、 SpringBoot主程序 启用 @EnableEurekaClient //本服务启动后会自动注册进eureka服务中

	3、 Ribbon (在 consume (消费者)这边配置，这个就服务就相当于我们的web项目直接给普通用户访问的)
		1、ribbon的支持
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-starter-ribbon</artifactId>
			</dependency>
		2、 注册到 eureka
			server: port: 80
			 
			eureka:
			  client:
				register-with-eureka: false		# 因为这个就服务就相当于我们的web项目直接给普通用户访问的
				service-url: 
				  defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
		3、 主配置类
			1、在启动该微服务的时候就能去加载我们的自定义Ribbon配置类， MySelfRule 是一个 @Configuration 配置类，里面定义了负载均衡算法
				@RibbonClient(name="MICROSERVICECLOUD-DEPT", configuration=MySelfRule.class)
			2、配置 RestTemplate
				@Configuration
				public class ConfigBean {
					@Bean
					@LoadBalanced  // 负载均衡的工具。Ribbon的默认的负载均衡算法是轮询，但是你想改掉这个负载均衡算法可以在在容器加入 IRule 的bean
					public RestTemplate getRestTemplate() {
						 return new RestTemplate();
					}
				}
			3、 配置 IRule 负载均衡规则
				@Configuration
				public class MySelfRule {
					@Bean
					public IRule myRule() {
						// return new RandomRule();// Ribbon默认是轮询，我自定义为随机
						// return new RoundRobinRule();// Ribbon默认是轮询，我自定义为随机
						// return new RetryRule();		//先按照RoundRobinRule算法获取服务，如果获取的服务不可用在指定的时间内进行重试N次?，获取可用的服务，这个有点问题
						return new RandomRule_ZY();// 我自定义为每台机器5次
					}
				}
			4、 调用通过 Ribbon 帮我集群好的 MICROSERVICECLOUD-DEPT 微服务
				@Autowired
				private RestTemplate restTemplate;
				@RequestMapping(value="/consumer/dept/get/{id}")
				public Dept get(@PathVariable("id") Long id) {
					 return restTemplate.getForObject("http://MICROSERVICECLOUD-DEPT/dept/get/"+id, Dept.class);
				}
	4、 feign
		1、feign依赖
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-starter-feign</artifactId>
			</dependency>
		2、 yml 配置
			server: port: 80

			feign: 
			  hystrix: 
				enabled: true	# 启用熔断

			eureka:
			  client:
				register-with-eureka: false
				service-url: 
				  defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
		3、 主程序
			// 启动用Feign支持，并且指定要调用的接口在那个包下（即：有 @FeignClient 注解的接口），
			// 这里需要扫描 DeptClientServiceFallbackFactory 组件，但是由于 spring 默认扫描主程序所在的包的组件，其他包如果有组件需要自己添加扫描
			@EnableFeignClients(basePackages= {"com.atguigu.springcloud"})
			@ComponentScan("com.atguigu.springcloud")
		
		4、 配置 @FeignClient ，这个客户端帮我们去调用 通过feign 集群的 MICROSERVICECLOUD-DEPT ，直接使用springmvc的注解调用 MICROSERVICECLOUD-DEPT 的相关方法
			// 此接口的方法异常了，就让DeptClientServiceFallbackFactory处理
			@FeignClient(value = "MICROSERVICECLOUD-DEPT", fallbackFactory=DeptClientServiceFallbackFactory.class)
			public interface DeptClientService {
				@RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
				public Dept get(@PathVariable("id") long id);

				@RequestMapping(value = "/dept/list",method = RequestMethod.GET)
				public List<Dept> list();

				@RequestMapping(value = "/dept/add",method = RequestMethod.POST)
				public boolean add(Dept dept);
			}
		5、 配置 DeptClientServiceFallbackFactory 组件，调用 MICROSERVICECLOUD-DEPT 发生异常时的处理，做熔断处理
			@Component	// 不要忘记添加，不要忘记添加
			public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
				@Override
				public DeptClientService create(Throwable throwable) {
					return new DeptClientService() {	// 每个方法都要实现，返回规则自己和调用者商量好
						@Override
						public Dept get(long id) {
							Dept dept = new Dept();
							dept.setDeptno(id);
							dept.setDname("该ID：" + id + "没有没有对应的信息-up,null--@HystrixCommand");
							dept.setDb_source("no this database in MySQL");
							return dept;
						}

						@Override
						public List<Dept> list() {
							return null;
						}

						@Override
						public boolean add(Dept dept) {
							return false;
						}
					};
				}
			}
		6、调用 @FeignClient 即可
			@Autowired
			private DeptClientService service = null;
			@RequestMapping(value = "/consumer/dept/get/{id}")
			public Dept get(@PathVariable("id") Long id) {
				return service.get(id);
			}
	5、 hystrix
		1、hystrix 的 @HystrixCommand 用法，好像上面的 DeptClientServiceFallbackFactory 比这个好用？
			1、 hystrix 的依赖	// 在provider配置
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-starter-hystrix</artifactId>
				</dependency>
			2、 yml配置
				server: port: 8001
				  
				spring: application: name: microservicecloud-dept 
				 
				eureka:
				  client: #客户端注册进eureka服务列表内
					service-url: 
					  #单机版的：defaultZone: http://localhost:7001/eureka  
					  defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/	#集群的
				  instance:
					instance-id: microservicecloud-dept8001-hystrix   		#自定义服务名称信息
					prefer-ip-address: true     							#访问路径可以显示IP地址
			3、 主程序  @EnableCircuitBreaker // 对hystrixR熔断机制的支持
			4、被调用的Controller配置
				@RequestMapping(value = "/dept/get/{id}", method = RequestMethod.GET)
				@HystrixCommand(fallbackMethod = "processHystrix_Get") // 增加了熔断处理，如果此方法调用失败，就去调用processHystrix_Get方法
				public Dept get(@PathVariable("id") Long id) {
					Dept dept = this.service.get(id);
					if (null == dept) {
						throw new RuntimeException("该ID：" + id + "没有没有对应的信息");
					}
					return dept;
				}

				public Dept processHystrix_Get(@PathVariable("id") Long id) {
					Dept dept = new Dept();
					dept.setDeptno(id);
					dept.setDname("该ID：" + id + "没有没有对应的信息,null--@HystrixCommand");
					dept.setDb_source("no this database in MySQL");
					return dept;
				}
	6、 zuul
		1、 zuul 的依赖
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-starter-zuul</artifactId>
			</dependency>
		2、 yml 配置
			server: port: 9527
			 
			spring: application: name: microservicecloud-zuul-gateway
			 
			eureka: 
			  client: 
				service-url: 
				  defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka,http://eureka7003.com:7003/eureka  
			  instance:
				instance-id: gateway-9527.com
				prefer-ip-address: true 

			zuul: 
			  prefix: /atguigu
			  ignored-services: "*"
			  #ignored-services: microservicecloud-dept	单个微服务,上面是全部微服务
			  routes: 
				mydept.serviceId: microservicecloud-dept
				mydept.path: "/mydept/**"	# ""不要了
				
				#访问 mydept.serviceId 就用 mydept.path 代替
		3、zuul的作用：路由+代理+过滤三大功能
			1、路由
				1、使用了路由地址变化：localhost/dept/get/2 -> myzuul.com:9527/微服务名称/dept/get/2  // 但是之前的地址还能访问吗？都可以
			2、代理
				1、代理之后的地址访问： myzuul.com:9527/microservicecloud-dept/dept/get/2 -> myzuul.com:9527/myDept/dept/get/2  // 都可以访问
			3、过滤
				1、难道这个是忽略的作用，过滤掉黑名单的ip访问，有空去看看
		4、主程序 @EnableZuulProxy	// 网关

	7、 config // 集中化配置，如果远程配置文件有变化，直接重启 config客户端，还是说重启config服务端，应该是客户端
		1、 config 服务
			1、 config-server 的依赖
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-config-server</artifactId>
				</dependency>
				<!-- 避免Config的Git插件报错：org/eclipse/jgit/api/TransportConfigCallback，这个可能不用 -->
				<dependency>
					<groupId>org.eclipse.jgit</groupId>
					<artifactId>org.eclipse.jgit</artifactId>
					<version>4.10.0.201712302008-r</version>
				</dependency>
				<!-- 这个可能不用 -->
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-starter-config</artifactId>
				</dependency>
			2、 yml 配置
				server: 
				  port: 3344 
				  
				spring:
				  application:
					name:  microservicecloud-config
				  cloud:
					config:
					  server:
						git:
						  uri: git@github.com:13650933659/microservicecloud-config.git #GitHub上面的git仓库名字
			3、主程序 @EnableConfigServer		// 启用config 服务
		
		2、 config 客户端
			1、 config客户端依赖
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-starter-config</artifactId>
				</dependency>
			2、 yml
				# bootstrap.yml 此文件是系统级的，优先级比application.yml高
				spring:
				  cloud:
					config:
					  name: microservicecloud-config-client # 需要从github上读取的资源名称，注意没有yml后缀名
					  profile: test   #本次通过microservicecloud-config-3344服务访问到在github对应的配置项，然后就会把远程的 microservicecloud-config-clien.yml test 环境的配置 的配置加载到程序的内存
					  label: master   
					  uri: http://config-3344.com:3344  #本微服务启动后先去找3344号服务，通过SpringCloudConfig获取GitHub的服务地址
				 # application.yml 
				 spring:
				  application:
					name: microservicecloud-config-client
	8、 stream 整合消息中间件	
		1、 消息生产者
			1、stream（rabbit\kafka）的依赖与消息中间件交互需要的
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-starter-stream-rabbit</artifactId>
				</dependency>
				
			2、yml
				server:
				  port: 8080
				spring:
				  application:
					name: spring-msg-producer
				  #这些是连接rabbitmq的默认连接参数，如果参数值和下面是一样的那么可以不用写了
				  #rabbitmq:
				  #  host: localhost
				  #  port: 5672
				  #  username: guest
				  #  password: guest
				eureka:
				  client:
					serviceUrl:
					  defaultZone: http://localhost:8761/eureka/
			3、 主程序
				// 绑定我们自己的发送消息服务类
				@EnableBinding(SendService.class)
			4、 SendService 接口的编写
				public interface SendService {
					// 使用这个注解表示，此方法可以向消息中间件发送消息， myInput 是消息频道的名称
					@Output("myInput")
					SubscribableChannel sendOrder();
				}
			5、 使用
				@Autowired
				private SendService sendService;

				/**
				 * 此方法会向消息中间件（rabbit\kafka）发送消息
				 * @return
				 */
				@RequestMapping(value = "/send", method = RequestMethod.GET)
				public String send() {
					Message msg = MessageBuilder.withPayload("Hello World".getBytes()).build();
					sendService.sendOrder().send(msg);	// 这个是阻塞吗？
					return "success";
				}
		2、 消息消费者，因为 #rabbitmq 都是默认的所以这里没配置
			1、 stream（rabbit\kafka）的依赖与消息中间件交互需要的
				<dependency>
					<groupId>org.springframework.cloud</groupId>
					<artifactId>spring-cloud-starter-stream-rabbit</artifactId>
				</dependency>
			2、 yml 
				server:
				  port: 8081
				spring:
				  application:
					name: spring-msg-consumer
				  #队列的组别
				  cloud:
					stream:
					  bindings:
						myInput:
						  group: groupA
				eureka:
				  client:
					serviceUrl:
					  defaultZone: http://localhost:8761/eureka/
			3、 主程序 @EnableBinding(ReceiveService.class)
			4、 ReceiveService 接口的编写
				public interface ReceiveService {
					// 接收 myInput 频道的消息
					@Input("myInput")
					SubscribableChannel myInput();
				}
			5、 接收到消息的处理
				// 加了 @StreamListener("myInput") 的方法就可以处理 myInput频道的消息了
				@StreamListener("myInput")
				public void onReceive(byte[] msg) {
					System.out.println("消息者1，接收到的消息：" + new String(msg));
				}

主讲：周阳
	1、五大神兽
	2、脑图：少了服务发现和Config，少了Ribbon的核心组件IRule(LB算法)，还有自定义IRule
	3、面试题：Eureka=AP和zookeeper=CP的比较
	4、面试题：关系型数据库：(A(Atomicity)C(Consistency)I(Isolation)D(Durability)和 NoSql：C(Consistency)A(Availability)P(Partition Tolerance))

1、Eureka的服务注册与发现
	1、Eureka是什么
		1、Eureka是Netflix的一个子模块，也是核心模块之一。Eureka是一个基于REST的服务，用于服务的注册与发现。
	2、Eureka的集群配置(eureka集群和provider(是服务)集群)
		1、参考：microservicecloud-eureka-7001/7002/7003和microservicecloud-provider-dept-8001/8002/8003
2、Ribbon/Feign的负载均衡
	1、Ribbon 是基于Netflix Ribbon实现的一套客户端负载均衡的工具(LB)，他支持多种负载算法，比如轮询和随机等等，也可以支持自定义的负载算法，
	2、Feign=Ribbon+RestTemplate，Feign也支持springmvc的@RequestMapping注解，Feign面向接口编程，使用起来比Ribbon更加简洁
3、Hystrix的服务熔断和服务降级(避免异常的蔓延，提高服务的高可用性，他俩的区别在于是否调用了B)
	1、服务熔断：A服务调用B服务，B出现异常，返回A可以处理的结果(这个配置是在A服务配置的)
	2、服务降级：B停掉，A访问B的客户端，那么Hystrix就不会调用B，返回一个调用者可处理的结果((这个配置是在A服务配置的))
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
7、bus总线	// 这个没看完
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
