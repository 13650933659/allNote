






1、持续集成： 一提交代码服务器触发钩子函数，自动完成重新编译部署
2、持续集成工具
	1、jenkins 和 hudson ， hudson 可以说是jenkins的前身
3、 工作流程
	-> 开发人员提交代码到代码库(版本控制服务器)
	-> 触发钩子程序通知 jenkins(jenkins服务器) 
	-> jenkins 调用 git/svn插件从(版本控制服务器)获取源码
	-> jenkins 调用 maven 插件 打包war包
	-> kenkins 调用 deploy web container 插件 把war部署到web应用服务器
	-> 用户和测试人员测试
4、 技术组合 
	1、 jenkins 整合 subversion
		1、 subversion 版本控制服务器的搭建(自己看)
		2、 应用服务器的搭建
			1、 jdk/tomcat
		3、 持续集成(jenkins)服务器的搭建
			1、 jdk\tomcat\maven
			2、 jenkins
				1、 把 jenkins.war 放在tomcat 的 webapps 目录下
				2、 vim /opt/tomcat/conf/server/xml 
					在 <Connector port="8080" /> 标签加入 URIEncoding="UTF-8" 解决get请求中文乱码文件
				3、 启动tomcat
				4、 访问jenkins http://192.168.216.129:8080/jenkins
				5、 解锁jenkins : vim ~/.kenkins/secrets/initialAdminPassword 拿到 admin 的密码
				6、 选择安装插件的方式
					1、 安装推荐的插件 或者 选择插件来安装 或者 点击 × 关掉 都可以，因为后期我们可以自己安装插件，这里我为了方便就选择了安装推荐的插件
					2、 推荐的插件安装需要jenkins的服务连网，安装时间需要一些时间，请耐心等候
				7、 创建第一个管理员用户，这里不创建了 点击 直接使用 admin 账号继续使用jenkins
				8、 点击左边的 系统管理 
					1、 全局工具配置
						-> 安全域 允许用户注册 钩上
						-> 授权策略 如果是学习的话 任何用户可以做任何事 钩上 生产环境选择其他模式
					2、 全局工作配置
						-> Maven Configuration -> Default settings provider -> 选择 Settings file in filesystem 然后会触发 File path 填 /opt/apache-maven-3.5.0/conf/settings.xml
						-> Maven Configuration -> Default global settings provider -> 选择 Global settings file on filesystem 然后会触发 File path 填 /opt/apache-maven-3.5.0/conf/settings.xml
						-> JDK -> 点击新增JDK -> 自动安装去掉 -> 写上别名 MyJDK -> JAVA_HOME 填 /opt/jdk1.8.0_181
						-> Maven -> 点击新增Maven -> 自动安装去掉 -> 写上别名 MyMaven -> JMAVEN_HOME 填 /opt/apache-maven-3.5.0
						-> Git 暂时先点击 Delete Git 后续需要用再钩上
						-> Save
				9、安装的插件
					1、 svn 插件		// 在初始化那边已安装
					2、 maven 插件		// 在初始化那边已安装
					3、 deploy to web container 插件
						点击左边的 系统管理 -> 管理插件 -> 可选插件 -> ctrl + f 搜索 deploy to web container -> 直接安装 -> 稍等片刻
		
	2、 jenkins 整合 github(gitlab\gogs)	// 这个自己再看
5、其他可以再参考视频的资料




