


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
	



4、frp的穿墙技术
	1、frp配置好
	2、nginx启动(http和https)
		1、如果是https的话，对应的域名需要对应证书

 

	

// 第二份视频
	1、看完 3-5D


1、徐亮伟的nginx视频2018
	1、nginx是什么：一个开源且高性能（支持海量并发请求）、可靠的http中间件、代理服务、常见的http服务 httpd(apache基金会)、iis(微软)、gws(google)、tengline(淘宝基于nginx开发的)
	2、nginx应用场景
		1、静态分离
		2、反向代理
		3、负载均衡
		4、资源缓存
		5、安全防护
		6、访问限制
		7、访问认证
	3、nginx优秀特性
		1、io多路复用(一条线程处理所有)
		2、使用epool模型的(老的使用的是select模型)：select是挨个遍历，epool是精准查询
		3、轻量级：功能模块少，代码模块化
		4、cpu亲和(affinity)：将cpu核心和nginx工作进程绑定的方式，把每一个worker进程固定在一个cpu上执行，减少切换cpu的cache miss
		5、使用sendfile的文件传输方式：直接内核之间的操作，不需要经过用户空间



	4、nginx的安装（Mainline version 开发版、Stable version 稳定版、Legacy version 历史版本）
		1、linux(centos7)
			1、四项确认工作
				1、网络可用
				2、yum可用
				3、关闭防火墙(这个可以不关，开发端口就可以了)
					iptables -L 列出规则  -> iptables -F 关闭规则
					iptables -t nat -L 列出规则  -> iptables -t nat -F 关闭规则
				4、确认停用selinux		// 安全增强型 Linux（Security-Enhanced Linux）简称 SELinux，它是一个 Linux 内核模块，也是 Linux 的一个安全子系统。
					getenforce(查看) -> setenforce(停用)
			2、准备安装
				1、yum源安装其他工具包
					yum -y install gcc gcc-c++ autoconf pcre pcre-devel make automake  // 安装c和c++的一些依赖
					yum -y install wget httpd-tools vim		// 一些工具包
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
					systemctl relad nginx.service		// 柔和重启
				2、nginx的方式使用
					nginx -t	// 检查语法
					nginx -c /etc/nginx/conf/nginx.conf // 启动 默认也是使用 /etc/nginx/conf/nginx.conf 配置文件
					nginx -s reload		// 重新加载配置文件
					nginx -s stop		// 柔和关闭
					nginx -s quit		// 强制关闭

	5、nginx的http请求变量
		$uri		// 当前请求的uri，不带参数
		$request_uri	// 请求的uri，带完整参数
		...
	6、nginx的基本配置
		1、 主配置文件/etc/nginx/nginx.conf
			1、主要的标签
				1.main位于nginx.conf配置文件的最高层
				2.main层下可以有event、http层
				3.http层下允许有多个server层, 用于对不同的网站做不同的配置
				4.server层也允许有多个location, 用于对不同的路径进行不同模块的配置
			1、其他
				1.user		// 设置nginx服务的系统使用用户
				2.worker_processes		// 工作进程，配置和cpu个数保持一致
				3.error_log			// 错误日志输出路径
				4.pid					// nginx服务的进程id
				5.事件模块
					events {
						worker_connections  // 每个worker进程支持最大的连接数
						use					// 内核模型：select/poll/epoll
					}
				6.网站的配置，支持配置多个	// http.server
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
				7.http 的日志配置，包括 error.log access.log
					http {
						log_format main '$remote_addr - $remote_user ...'
					}
					$remote_addr		// 客户端ip地址
					$remote_user		// 客户端请求nginx认证用户名
					$time_local			// nginx的时间
					$request			// request请求行，get等方法、http协议版本
					$status				// 返回状态码
					$body_bytes_sent	// 返回body信息大小
					$http_referer		// 上一次请求页面，可用于防盗链，用户行为分析
					$http_user_agent	// 客户端访问设备
					$http_x_forwarded_for	// http请求携带的http信息，（真实的ip，可以看到通过代理服务器访问的情况）
				8.nginx状态信息		// server.location
					server {
						location /mystatus {
							stub_status on;
							access_log off;
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
				9.站点下载 // http.server.location
					location / {
						root	html;
						autoindex	on;				// Nginx默认是不允许列出整个⽬录浏览下载。配置on则可以
						autoindex_localtime on;		// on=单位是byte off=单位是 KB\MB\GB
						autoindex_exact_size off;	// on=文件的GMT时间 off文件的服务器时间
						charset	utf-8,gbk;			// 如果乱码需要配置这句
					}
				10.访问限制 // http.server.location
					// 连接限制
					http {
						limit_conn_zone $binary_remote_addr zone=conn_zone:10m;
						server {
							location / {
								limit_conn conn_zone 1;	// 同一时刻只允许一个客户端ip连接(应该叫同一时刻只能一个连接吧？)
							}
						}
					}
					// 请求限制
					http {
						limit_req_zone $binary_remote_addr zone=req_zone:10m rate=1r/s;(这个好像就是qps)
						server {
							location / {
								limit_req zone=req_zone;	// 请求超过1r/s，其余的请求拒绝。并且返回错误代码给客户端
								// limit_req zone=req_zone burst=3 nodelay; //请求超过1r/s,剩下的将被延迟处理,请求数超过burst定义的数量,	多余的请求返回503
							}
						}
					}
					
					
					可以使用 压力测试工具 yum install -y httpd-tools
					ab -n 50 -c 20 http://127.0.0.1/index.html
					总结： 因为同一时刻只允许一个连接，但是同一时刻多个请求可以通过一个连接进入，所以请求限制才是比较好的解决方案

				11.基于ip的访问控制  // http.server.location 
					// 拒接 192.168.56.1 允许所有
					location ~ ^/1.html {
						root /usr/share/nginx/html;
						index index.html;
						deney 192.168.56.1;
						allow all'
					}
					// 只允许某一个网段访问，其他拒绝
					location / {
						root html;
						index index.html;
						allow 192.168.56.0/24;
						deny all;
					}
					
					总结：http_access_module局限性，通过代理的访问的限制不到
				12.基于登录认证
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
				13.虚拟主机配置方式
					1、 同端口，不同server_name		// 一般真正的使用这一种
					2、 不同端口，同server_name
					3、 虚拟主机别名配置， server_name www.bd.com bd.com; // 访问 www.bd.com 和 bd.com 是一样的
						




http://www.bidizhaobiao.com/info-40754938.html











