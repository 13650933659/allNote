


2021-04-23
2021-04-23 21:10:10

1、安装配置参考
	https://blog.csdn.net/helllochun/article/details/51313299




2、使用
	1、检查语法
		nginx -t
	2、启动
		cd C:\software\openSource\nginx1161 start nginx
	2、停止
		cd到nginx的home目录 : nginx -s stop
	2、查看nginx是否启动
		1、命令方式   tasklist /fi "imagename eq nginx.exe"
	2、liunx使用
		https://www.cnblogs.com/fhen/p/5896105.html
	3、windows强杀端口
		1、查看使用端口的PID：输入命令： netstat -aon|findstr "端口号"
			例子 netstat -aon|findstr "433"
		2、通过pid直接杀死进程： taskkill /pid pid号 -t -f
			例子 taskkill /pid 5272 -t -f



4、frp的穿墙技术
	1、frp配置好
	2、nginx启动(http和https)
		1、如果是https的话，对应的域名需要对应证书

 http://bxkc-pc.biaoxunkuaiche.com/organizationOfNotLoginAction!searchOrgInfo.do


1、徐亮伟的nginx视频2018
	1、nginx是什么：一个开源且高性能（支持海量并发请求）、可靠的http中间件、代理服务、常见的http服务 httpd(apache基金会)、iis(微软)、gws(google)、tengline(淘宝基于nginx开发的)
	2、nginx优秀特性
		1、轻量级：功能模块少，代码模块化
		2、io多路复用(一条线程处理所有)
		3、使用epool模型的(老的使用的是select模型)：select是挨个遍历，epool是精准查询
		4、cpu亲和(affinity)：将cpu核心和nginx工作进程绑定的方式，把每一个worker进程固定在一个cpu上执行，减少切换cpu的cache miss
		5、使用sendfile的文件传输方式：直接内核之间的操作，不需要经过用户空间
	3、nginx应用场景
		1、静态分离
		2、反向代理
		3、负载均衡
		4、资源缓存
		5、安全防护
		6、访问限制
		7、访问认证
		8、站点下载
	4、nginx的安装
		1、linux(centos7)
			1、四项确认工作
				1、网络可用
				2、yum可用
				3、关闭防火墙(这个可以不关，开放端口就可以了)
					iptables -L 列出规则  -> iptables -F 关闭规则
					iptables -t nat -L 列出规则  -> iptables -t nat -F 关闭规则
				4、确认停用selinux		// 安全增强型 Linux（Security-Enhanced Linux）简称 SELinux，它是一个 Linux 内核模块，也是 Linux 的一个安全子系统。
					getenforce(查看) -> setenforce(停用)
			2、准备安装
				1、yum源安装其他工具包
					yum -y install gcc gcc-c++ autoconf pcre pcre-devel make automake  // 安装c和c++的一些依赖
					yum -y install wget httpd-tools vim		// 一些工具包 httpd-tools(ab压测工具和加解密工具...)
				2、配置nginx的yum源 vim /etc/yum.repos.d/nginx.repo  // 去nginx给我找yum源
					[nginx-stable]
					name=nginx stable repo
					baseurl=http://nginx.org/packages/centos/7/$basearch/
					gpgcheck=1
					enabled=1
					gpgkey=https://nginx.org/keys/nginx_signing.key
					module_hotfixes=true
				3、列出nginx
					yum list | grep nginx
				4、安装最新稳定版本的 nginx
					yum install nginx 
				5、查看安装那些nginx包 rpm -ql nginx
					1、 /etc/logrotate/nginx  // 用于日志轮转，logrotate日志切割
					2、 /etc/nginx/			  // nginx主配置目录
					3、 守护进程相关
						/usr/lib/systemd/system/nginx-debug.service
						/usr/lib/systemd/system/nginx.service
						/etc/sysconfig/nginx
						/etc/sysconfig/nginx-debug
					4、启动关闭相关
						/usr/sbin/nginx
						/usr/sbin/nginx-debug	// debug用的
					5、缓存目录
						/var/cache/nginx
			3、使用
				1、系统的方式使用
					systemctl restart nginx.service
					systemctl reload nginx.service		// 柔和重启
				2、nginx的方式使用
					nginx -t	// 检查语法
					nginx -c /etc/nginx/conf/nginx.conf // 启动 默认也是使用 /etc/nginx/conf/nginx.conf 配置文件
					nginx -s reload		// 重新加载配置文件
					nginx -s stop		// 柔和关闭
					nginx -s quit		// 强制关闭
	5、nginx的配置(nginx.conf)
		1、http请求常用变量
			$uri				// 当前请求的uri，不带参数
			$request_uri		// 请求的uri，带完整参数
			$remote_addr		// 客户端ip地址
			$remote_user		// 客户端请求nginx认证用户名
			$time_local			// nginx的时间
			$request			// request请求行，get等方法、http协议版本
			$status				// 返回状态码
			$body_bytes_sent	// 返回body信息大小
			$http_referer		// 上一次请求页面，可用于防盗链，用户行为分析
			$http_user_agent	// 客户端访问设备
			$http_x_forwarded_for	// http请求携带的http信息，（真实的ip，可以看到通过代理服务器访问的情况）
			...
		2、常用的标签
			1.main位于nginx.conf配置文件的最高层
			2.main层下可以有events(事件模块)、http层
			3.http层下允许有多个server层, 用于对不同的网站做不同的配置
			4.server层也允许有多个location(规则), 用于对不同的路径进行不同模块的配置
			5.main.user			// 设置nginx服务的系统使用用户
			6.worker_processes	// 工作进程，配置和cpu个数保持一致
			7.error_log			// 错误日志输出路径
			8.pid				// nginx服务的进程id
		3、location的优先级
			1、location的匹配方式
				1、全等
					location = /static/img/ {deny all;}
				2、普通匹配
					location /static/img/ {deny all;}
					location ^~ /static/img/ {deny all;}
				3、正则匹配
					location ~*/static/img/ {deny all;}		// 正则不区分大小写
					location ~ /static/img/ {deny all;}		// 正则区分大小写
			2、匹配步骤
				1.匹配到全等时，终止后续所有匹配
				2.步骤1未匹配上时，遍历所有的普通匹配，按照最长匹配原则找到最满足的匹配项，如果匹配项前面有^~符号，则终止后续正则匹配，采用该匹配项；反之则继续后续的正则匹配
				3.步骤1,2都未匹配上时，此时进行正则匹配，找到第一个满足的正则匹配项，直接返回，若都不满足，则返回步骤二中的最长匹配项（所以说正则匹配和loaction的顺序有关系）
		4、配置实践
1.事件模块
	events {
		worker_connections 20000;  // 每个worker进程支持最大的连接数
		use	epoll;				// 内核模型：select/poll/epoll
	}
2.网站的配置，支持配置多个网站
	http {
		server {
			listen 80;		// 监听端口，默认80
			server_name localhost;	// 主机名称，一般和域名一致

			location / {
				root	/usr/share/nginx/html;	// 存放网站的根路径
				index	index.html index.htm;	// 默认访问首页
			}

			error_page	500 502 503 504	/50x.html;	// 指定错误代码，统一页面
			location = /50x.html {
				root html;
			}

		}
	}
3.日志配置，包括 error.log access.log
	http {
		log_format main '$remote_addr - $remote_user ...'
	}
4.nginx状态信息
	server {
		location = /mystatus {
			stub_status on;
			access_log off;
			allow 127.0.0.1;	# 为了安全只能让本机查看
		}
	}
	配置了之后访问 http://localhost/mystatus 即可得到如下信息
		Active connections:2	// nginx当前活跃连接数
		server accepts handled requests
		16	   16      19
		server	// 表示nginx处理接收握手总次数
		accepts	// nginx处理接收总连接数
		请求丢失数=server-accepts;
		handled requests  // 表示总共处理了19次请求
		Reading		// nginx读取数据
		Writing		// nginx写的情况
		Waiting		// nginx开启keep-alive长连接情况下，即没有读也没有写，建立连接情况
5.站点下载
	location / {
		root	html;
		autoindex	on;				// Nginx默认是不允许列出整个⽬录浏览下载。配置on则可以
		autoindex_localtime on;		// on=单位是byte off=单位是 KB\MB\GB
		autoindex_exact_size off;	// on=文件的GMT时间 off文件的服务器时间
		charset	utf-8,gbk;			// 如果乱码需要配置这句
	}
6.访问限制
	1、请求限制
		http {
			limit_req_zone $binary_remote_addr zone=req_zone:10m rate=1r/s; # 10m用来存储二进制的客户端ip，1m 可以储存 32000 个并发会话
			server {
				location / {
					limit_req zone=req_zone;	// 请求超过1r/s，其余的请求拒绝。并且返回错误代码给客户端
					# limit_req zone=req_zone burst=3 nodelay; //请求超过1r/s,剩下的将被延迟处理,请求数超过burst定义的数量,	多余的请求返回503
				}
			}
		}
	2、连接限制
		http {
			limit_conn_zone $binary_remote_addr zone=conn_zone:10m;
			server {
				location / {
					limit_conn conn_zone 1;	// 同一时刻只能同一个ip连接，如果想改为整个服务器只能并发一个连接把 $binary_remote_addr 改为 $server_name
					# limit_rate 100k; # 每个连接限速为 100KB/秒
				}
			}
		}


	可以使用 压力测试工具 yum install -y httpd-tools
	ab -n 50 -c 20 http://127.0.0.1/index.html
	总结： 因为同一时刻只允许一个连接，但是同一时刻多个请求可以通过一个连接进入，所以请求限制才是比较好的解决方案
7.基于ip的访问控制
	1、拒接 192.168.56.1 允许所有
	location ~ ^/1.html {
		root /usr/share/nginx/html;
		index index.html;
		deney 192.168.56.1;
		allow all;
	}
	2、只允许某一个网段访问，其他拒绝
	location / {
		root html;
		index index.html;
		allow 192.168.56.0/24;
		deny all;
	}

	总结：http_access_module局限性，通过代理的访问的限制不到
8.基于登录认证
	1、 安装httpd-tools加解密工具 // yum install httpd-tools;
	2、 创建用户和密码			  // htpasswd -c /etc/nginx/auth_conf zs 回车输入密码123 既可以
	3、 在 http,server,location 添加下面信息
		auth_basic "auth access Blog input your Passwd!"
		auth_basic_user_file /etc/nginx/auth_conf;
	4、 总结
		1、用户认证局限性
			1.用户信息依赖文件形式
			2.用户管理文件多了，无法联动
			3.操作管理机械，效率低下
		2、解决方案
			1、 nginx + lua 实现高效验证
			2、 nginx + ldap 利用 nginx-auth-ldap 模块
9.虚拟主机配置方式
	1、 同端口，不同server_name		// 一般真正的使用这一种
	2、 不同端口，同server_name
	3、 虚拟主机别名配置， server_name www.bd.com bd.com; // 访问 www.bd.com 和 bd.com 是一样的
10.静态资源
	1、 文件读取高效，启用sendfile
		server{
			sendfile on;	// 默认是关闭
		}
	2、 提高网络传输效率 nopush
		server {
			tcp_nopush on;	// 默认是关闭，提高网络包的传输
		}
	3、 在keepalive连接下，提高网络传输的实时性
		server {
			tcp_nodelay on;	// 默认时开启的
		}
	4、 静态资源的压缩
		server {
			location ~ .*\.(jpg|gif|png)$ {
				gizp on;		// 默认是off
				gizp_http_version 1.1;	// 压缩使⽤在http哪个协议,	主流版本1.1
				gizp_comp_level 1;		// gzip 压缩比率配置
				gizp_types text/plain applicaion/json image/jpg; // 需要压缩饿文件类型
				root /soft/code/doc;
			}
		}
		
		总结：
			优点：提高响应速度，减少带宽
			确定：压缩耗费服务器cpu
			总的来说：优点大于缺点
	5、 静态资源的缓存
		1、 http1.0使用 Expires http1.1 使用 Cache-Control(max-age) 使用协议中 Etag 头信息校验Etag.Last-Modified
		2、 配置案例
			location ~ .*\.(js|css|html)$ {	// 1小时 如果配置为七天 expires 7d;
				root /soft/code/js;
				expires 1h;
			}
			
			location ~ *.\.(css|js)$ {		// 不缓存案例，但是有的浏览器默认会有缓存
				add_header Cache-Control no-store;
				add_header Pragma no-store;
			}
	6、静态资源跨域访问		// a、img 这些访问不叫跨域，只能说是盗链，ajax中的请求才是跨域
		location ~ *.\.(html)$ {		// 目标服务配置允许跨域请求
			add_header Access-Control-Allow-Origin http://wwww.zs.com;
			add_header Access-Control-Allow-Methods	GET.POST,PUT,DELETE,OPTIONS;
			root /soft/code;
		}
	7、静态资源防盗链
		location ~ *.\.(jpg|gif|png)$ {	// 只允许www.zs.com访问，其他域名访问返回403，但是我们直接在浏览器访问没有这个限制
			valid_referers node blocked www.zs.com;
			if ($invalid_referer){
				return 403;
			}
		}
11.nginx代理服务
	1、正向代理和反向代理：主要区别是代理的对象不一样，正向代理代理的是客户端，反向代理代理的是服务器
	2、正向代理案例
		服务器  // 只允许 2.0 -> 2.25 访问
			location ~ .*\.(jpg)$ {
				allow 192.168.2.0/25;
			}
		代理服务器
			server {
				listen 80;
				resolver 233.5.5.5;	// dns服务器
				location / {
					proxy_pass http://$http_host$request_uri;
					proxy_set_header Host $http_host;
					proxy_set_header X-Real-IP $remote_addr;
					proxy_set_header X-Forwared-For $proxy_add_x_forwarded_for;
				}
			}
			客户端 使用SwitchySharp浏览器插件配置正向代理
	3、反向代理案例
		服务器		// 同上
		代理服务器
		vim	/etc/nginx/proxy_params 增加下面代理配置餐数
			proxy_redirect	default;
			proxy_set_header	Host	$http_host;
			proxy_set_header	X-Real-IP	$remote_addr;
			proxy_set_header	X-Forwarded-For	$proxy_add_x_forwarded_for;
			proxy_connect_timeout	30;
			proxy_send_timeout	60;
			proxy_read_timeout	60;
			proxy_buffer_size	32k;
			proxy_buffering	on;
			proxy_buffers	4	128k;
			proxy_busy_buffers_size	256k;
			proxy_max_temp_file_size	256k;	
		server {
				listen 80;
				server_name zs.com;
				index index.html;
				location / {
					proxy_pass http://192.168.2.100;
					include proxy_params;
				}
		}
12.nginx负载均衡	// 提升吞吐率，提升性能，提高容灾
	1、划分
		1、负载均衡按范围划分： gslb(全局负载均衡)和slb(用的最多)
		2、负载均衡按层级划分：分为四层（tcp/udp）和七层负载（http）
	2、负载案例1（七层代理-http）
		upstream node {		// 虚拟服务器池（反向代理）
			server zs1.com weight=5;
			server zs2.com;
			server 192.168.2.2:8080;
		}
		server {
			location / {
				proxy_pass http://node;
				include proxy_params;
			}
		}
		1、负载状态
			down		// 当前节点不可用
			weight		// 加权节点 weight=5表示 代表5次，其他节点1次，
			backup		// 预留节点，等其他服务器都挂了，他才会启用
			max_fails	// 允许请求失败的次数
			fail_timeout// 经过max_fails失败后，服务器暂停时间
			max_conns	// 限制最大的接收连接数
			keepalive	// 指定每个nginxworker可以保持的最大长连接数量，默认不设置，即nginx作为client时keepalive未生效
		2、负载调度策略
			轮询			// 平均分流
			ip_hash			// ip的hash值分流
			url_hash		// url的hash值分流
			least_conn		// 连接数少的分流
			hash关键数值	// hash自定义key
	3、负载案例2（四层代理-tcp）可以实现访问没有外网的服务器里面的应用
		stream {
				upstream ssh_proxy {		// ssh代理
					hash $remote_addr consistent;
					server 192.168.56.103:22;
				}
				upstream ssh_proxy {		// mysql代理
					hash $remote_addr consistent;
					server 192.168.56.103:3306;
				}
				server {
					listen 6666;
					proxy_connect_timeout 1s;	// 连接超时
					proxy_timeout 300s;			// 发呆时间
					proxy_pass ssh_proxy;
				}
				server {
					listen 5555;
					proxy_connect_timeout 1s;	// 连接超时
					proxy_timeout 300s;			// 发呆时间
					proxy_pass mysql_proxy;
				}
		}
13.nginx动静分离
	1、案例1
		upstream static {
			server 192.168.56.113:80;
		}
		upstream java {
			server 192.168.56.113:8080;
		}
		server {
			listen 80;
			server_name 192.168.56.112;
			location / {
				root /soft/code;
				index index.html;
			}
			location ~ .*\.(png|jpg|git)$ {
				proxy_pass http://static;
				include proxy_params;
			}
			location ~ .*\.(jsp)$ {
				proxy_pass http://java;
				include proxy_params;
			}
		}
14.nginx缓存
	1、nginx代理缓存配置案例
		upstream cache {
			server 192.168.69.113:8081;
			server 192.168.69.113:8082;
			server 192.168.69.113:8083;
		}
		// 缓存参数说明
		#proxy_cache - 存放临时间
		#levels	    - 按照两层目录分级
		#keys_zone   - 开辟空间名，10m开辟空间大小，1m可存放8000key
		#max_size    - 控制最大大小，超过以后nginx会启用淘汰规则
		#inactive    - 60分钟没有被访问，缓存会被清理
		#use_temp_path - 临时文件，会影响性能，建议关闭
		proxy_cache_path /soft/cache levels=1:2 keys_zone=code_cache:10m max_size=10g inactive=60m use_temp_path=off;
		server {
			listen 80;
			server_name 192.168.69.112;
			# proxy_cache	 开启缓存
			# proxy_cache_valid	 状态码200|300的过期时间为12h，其余的10m
			# proxy_cache_key	 缓存key
			# add_header	 增加头信息，观察客户端的response头信息是否命中
			# proxy_next_upstream	 出现502-504或者错误，会跳过此节点，访问下一个节点
			location / {
				proxy_pass http://cache;
				proxy_cache code_cache;
				proxy_cache_valid 200 304 12h;
				proxy_cache_valid any 10m;
				proxy_ca
				add_header Nginx-Cache "$upstream_cache_status";
				proxy_next_upstream error timeout invalid_header http_500 http_502 http_503 http_504;
				include proxy_params;		// 同上
			}
		}
	2、nginx代理缓存和部分页面不缓存配置案例
		upstream cache {
			server 192.168.69.113:8081;
			server 192.168.69.113:8082;
			server 192.168.69.113:8083;
		}
		// 缓存参数说明
		proxy_cache - 存放临时问价
		levels	    - 按照两层目录分级
		keys_zone   - 开辟空间名，10m开辟空间大小，1m可存放8000key
		max_size    - 控制最大大小，超过以后nginx会启用淘汰规则
		inactive    - 60分钟没有被访问，缓存会被清理
		use_temp_path - 临时文件，会影响性能，建议关闭
		
		proxy_cache_path /soft/cache levels=1:2 keys_zone=code_cache:10m max_size=10g inactive=60m use_temp_path=off;
		server {
			listen 80;
			server_name 192.168.69.112;
			# proxy_cache	 开启缓存
			# proxy_cache_valid	 状态码200|300的过期时间为12h，其余的10m
			# proxy_cache_key	 缓存key
			# add_header	 增加头信息，观察客户端的response头信息是否命中
			# proxy_next_upstream	 出现502-504或者错误，会跳过此节点，访问下一个节点
			location / {
				proxy_pass http://cache;
				proxy_cache code_cache;
				proxy_cache_valid 200 304 12h;
				proxy_cache_valid any 10m;
				add_header Nginx-Cache "$upstream_cache_status";
				proxy_next_upstream error timeout invalid_header http_500 http_502 http_503 http_504;
				include proxy_params;		// 同上
			}
		}
	3、nginx代理缓存清除
		1、手动清除缓存文件 rm -rf /soft/cache/
		2、通过ngx_cache_purge扩展模块清理，需要编译安装nginx
	4、利用 $upstream_cache_status 做缓存统计		// 用于分析 
15.nginx rewrite
	1、rewrite使用场景
		1、url访问跳转：支持开发设计、页面跳转、兼容支持、展现效果
		2、seo优化：简洁的路径便于搜索引擎录入
		3、维护：后台维护流量转发
		4、安全：伪地址
	2、rewrite标记flag
		1、 last		// 停止rewrite检查，代理服务器重新请求跳转
		2、 break		// 停止rewrite检查，代理服务器直接转发定位资源
		3、 redirect	// 返回302临时重定向，地址栏会显示跳转后的地址
		4、 permanent	// 返回301永久重定向，地址栏会显示跳转后的地址，你的服务器停了，他也会跳，上面则不会
	3、对rewrite的flag中 break 与 last 案例
		server {
			listen 80;
			server_name localhost;
			root /usr/share/nginx/html;
			location ~ ^/break {
				rewrite ^/break  /test/ break;
			}
			location ~ ^/last {
				rewrite ^/last  /test/ last;
			}
			location /test/ {		// 实际没有 /test/ 目录，所以break会报404
				default_type application/json;
				return 200 '{"status":"success"}';
			}

		}
16.nginx https
	1、环境前提条件
		1、 >openssl version		// 必须是 openssl 1.0.2版本
		2、 >nginx -V				// nginx必须要有ssl模块 --with-http_ssl_module
	2、创建私钥
		>openssl genrsa -idea -out server.key 2048		// 生成私钥输入密码
			Generating RSA private key, 2048 bit long modulus
			.....+++
			// 记住配置密码，我这里是1234
			Enter pass phrase for server.key:
				Verifying - Enter pass phrase for server.key:
	3、生成使用签名请求证书和私钥生成的自签证书
		>openssl req -days 36500 -x509 \ -sha256 -nodes -newkey rsa:2048 -keyout server.key out server.crt
			Conutry Name (2 letter code)[XX]:CN
			State or Province Name (full name) []:WH
			Locality Name (eg, city) [Default City]:WH
			Organization Name (eg, company) [Default] Company L td:edu
			Organizational Unit Name (eg, section) []:SA
			Common Name (eg, your name or your server's hostname) []:bgx
			Email Address []:bgx@foxmail.com
	4、配置nginx
		server {
			listen 443;
			server_name localhost;
			ssl on;
			index index.html;
			#ssl_session_cache share:SSL:10m;
			ssl_session_timeout 10m;
			ssl_certificate ssl_key/server.crt;
			ssl_certificate_key ssl_key/server.key;
			ssl_ciphers ECDHE-RSA-AES128-GCM-SHA256:ECDHE:ECDH:AES:HIGH:!NULL:!aNULL:MD5:!ADH:!RC4;
			ssl_protocols TLSv1 TLSv1.1 TLSv1.2;
			ssl_prefer_server_ciphers on;
			location / {
				root /soft/code;
				access_log /logs/ssl.log main;
			}
		}
		server {	// 把http强制转到https
			listen 80;
			server_name localhost;
			rewrite ^(.*) https://$server_name$1 redirect;
		}
	5、测试，由于该证书是我自己生成的，所以浏览器会警告，真正的是要去权威机构申请证书的 下载的证书好像是 .pem和.key文件
17.nginx+lua 
	1、nginx+lua优势:充分的结合nginx的并发处理epool优势和lua轻量级实现简单的功能且高并发的场景
		1、统计ip
		2、统计用户信息
		3、安全waf
	2、安装lua: yum install lua -y
	3、lua是一个简洁、轻量、可扩展的脚本语言，语法 具体用到时再去查
	4、nginx加载lua环境， 具体用到时再去查
	5、nginx调用lua指令
		set_by_lua,set_by_lua_file				// 设置nginx变量，可以实现负载的赋值
		access_by_lua,access_by_lua_file		// 请求阶段处理，用于访问控制
		content_by_lua,content_by_lua_file		// 内容处理器，接受请求处理并输出响应
	6、nginx调用luaAPI
		ngx.var				// nginx变量
		ngx.req.get_headers				// 获取请求头
		ngx.req.get_uri_args				// 获取url请求参数
		ngx.redirect				// 重定向
		ngx.print				// 输出响应内容体
		ngx.say				// 输出响应内容体，最后一个换行符
		ngx.header				// 输出响应头
	7、nginx+lua实现代码灰度发布：即最新功能给部分用户测试使用，部分用户还是用旧的系统，等测试通过再全面部署，实现功能平滑上线 具体用到时再去查
	8、lua调用memcached 具体用到时再去查
	9、nginx+lua实现waf应用防火墙	还可以配置黑白名单 具体用到时再去查
		1、防爬虫，防盗链
			
				




13502586666





