
1、tomcat是什么
	1、tomcat算是一个java容器，把我们的servlet部署到tomcat就可以运行了
2、配置我们的默认主机(ie用ip访问时自动定位到此主机=域名)
	1、进入【conf/server.xml】在<Engine name="Catalina" defaultHost="localhost">去配
3、配置我们的网页首页面
	1、进入我们自己的web应用的【WEB-INF/web.xml】在最后一行之上加入下码：
	<welcome-file-list>
		<welcome-file>你的web应用首页</welcome-file>
	</welcome-file-list>
3、tomcat管理web应用的虚拟目录，解决磁盘空间紧张进入【conf/server.xml】文件
	1、在</Host>之上加<Context path="/你的web应用名字" docBase="你的web应用绝对路径" />
	2、Context 的reloadable属性如果开发中设为ture,发布了设为flase因为他有开销的
	3、Context 的UnpackWAR属性设为ture是自动解压上传的war包，默认是ture重启
4、域名配置DNS，有时为了节省ip地址就给一个ip解析成多个域名
	1、打开C:\Windows\System32\drivers\etc目录的【hosts文件】在最后加入【ip 域名】
	2、再打开tomcat/conf/server.xml文件 在</Host>之下加入下码:
	<Host name="域名" appBase="你的web应用的绝对路径">
			<Context path="/" docBase="你的web应用的绝对路径"/>
	</Host>重启《这里注意：path一定是/》
5、自动监控WEB-INF的变动(也叫热部署建议开发环境启用，生成环境禁用，因为他有开销)：
	1、针对tomcat管理的所有app：	在tomcatHome/conf/context.xml的第一句<Context>加属性reloadable="true"，
	2、针对具体的某个app：		去tomcatHome/conf/server.xml在</Host>之上加<Context path="/你的web应用名字" docBase="你的web应用绝对路径" reloadable="true" />，这种属于虚拟目录，要注意无论
	3、1已经配了，还是自己的Context配，都有一个bug，如果是web.xml变动，他没监控到