


1、安装配置参考
	https://blog.csdn.net/helllochun/article/details/51313299

2020-03-03
98893875


2、使用
	1、启动
		cd C:\software\openSource\nginx-1.16.1 start nginx
		cd C:\Software\OpenSource\nginx-1.7.5 start nginx_basic
	2、停止
		cd到nginx的home目录 : nginx -s stop

	2、查看nginx是否启动
		1、命令方式   tasklist /fi "imagename eq nginx.exe"

	2、liunx使用
		https://www.cnblogs.com/fhen/p/5896105.html

完善

3、其他命令
	1. 首先利用配置文件启动nginx。
	命令: nginx -c /usr/local/nginx/conf/nginx.conf
	重启服务： service nginx restart
	2. 快速停止或关闭Nginx：nginx -s stop
	3. 正常停止或关闭Nginx：nginx -s quit
	4. 配置文件修改重装载命令：nginx -s reload


 

1、慕课网(nginx 1.12.1)

	1、四项确认工作
		1、防火墙 
			iptables -L 列出规则  -> iptables -F 关闭规则
			iptables -t nat -L 列出规则  -> iptables -t nat -F 关闭规则
		2、
			getenforce(查看) -> setenforce(停用)


	2、安装c和c++的一些依赖
		yum -y install gcc gcc-c++ autoconf pcre pcre-devel make automake

	3、配置nginx的yum源 vim /etc/yum.repos.d/nginx.repo
		[nginx-stable]
		name=nginx stable repo
		baseurl=http://nginx.org/packages/centos/7/$basearch/
		gpgcheck=1
		enabled=1
		gpgkey=https://nginx.org/keys/nginx_signing.key
		module_hotfixes=true

	4、列出nginx
		yum list | grep nginx

	5、安装最新稳定版本的 nginx
		yum install nginx 

	6、查看安装那些nginx包
		rpm -ql nginx
			1、 /etc/logrotate/nginx  // 用于日志轮转，logrotate日志切割
			2、 /etc/nginx/			  // nginx主配置目录
			3、 守护进程相关
				/usr/lib/systemd/system/nginx-debug.service
				/usr/lib/systemd/system/nginx.service
				/etc/sysconfig/nginx
				/etc/sysconfig/nginx-debug
			4、启动关闭
				/usr/sbin/nginx
				/usr/sbin/nginx-debug
			5、缓存目录
				/var/cache/nginx
	7、重启nginx服务
		systemctl restart nginx.service
		systemctl relad nginx.service		// 柔和重启

看完 2.12






