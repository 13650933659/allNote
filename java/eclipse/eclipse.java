






























1、使用
	1、eclipse禁止console窗口自动跳出
		1、Preferences -> Run/Debug -> Console里边
		取消勾选 Show when program writes to standard out（当console中有值时弹出）前的选项
		和 Show when program writes to standard error（当console中有错误时弹出） 前的选项
		2、Console窗口右边有一个左下角有小黄锁的图标"Scroll Lock"点它就可，不管console窗口怎么打印新的日志，Console窗口总是停留在你刚浏览的位置不动，除非你手动滚屏。




2、eclipse继承tomcat来部署web项目
	1、配置一个tomcat的运行环境
		1、Windows -> Server -> Tuntime Environment -> add -> 选择tomcat的安装目录 -> 选择使用的jre
		2、到Server窗口 -> 新建一个tomcat镜像 -> 这里也可以直接添加web项目(和Add Web Module一样)
		3、部署项目到tomcat的方法
			1、到tomcat镜像右键 -> open (或者双击打开)配置 -> 切到Modules视图 -> Add Web Module... 然后server.xml对应的配置如下		// 这种可能会比2的慢，他要拷贝很多的文件，而第二种会快一点，他只考 classes 和 lib
				<Context docBase="bxkc" path="/" reloadable="true" source="org.eclipse.jst.jee.server:bxkc"/></Host>
				也就是说web项目的主目录在： C:\Software\EclipseCode\bd\bxkc\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps
			2、到tomcat镜像右键 -> open (或者双击打开)配置 -> 切到Modules视图 -> Add External Web Module... 选到到你项目源码的src/main/webapp目录
				<Context docBase="C:\Software\EclipseCode\bd\bxkc\bxkc\src\main\webapp" path="/" reloadable="true"/></Host>
				
				1、但是用这种指定项目的住目录，我们的 WEB-INF/classes 和 WEB-INF/lib 目录没有东西，需要解决，而且最好好热部署
				2、(没解决)项目右键 -> Java Build Path -> 把 source下的 bxkc/src/main/java 和 bxkc\src\main\resources 的 Output folder 由target/classes -> src/main/webapp/WEB-INF/classes

3、禁用一些费时的校验
	1、windows -> Validation -> Disable All

4、Eclipse安装maven，如果是Eclipse4.0以上就不用安装了
	1、配置jdk，一定要用jdk里边的jre
	2、去 preferences -> Maven -> installations -> add我们自己安装的 maven -> User Settings配置我们的 setting.xml
	3、创建maven项目 -> 选择maven-archetype-quickstart，或者跳过快捷创建
	4、运行定位到pom.xml右键run as -> build...，然后写自己的maven命令

5、安装jre
	Window -> Preferences -> Installed JREs -> Add  -> jre home 最好使用 C:\Program Files\Java\jdk1.8.0_71\jre

6、git的使用，高版本的eclipse已经有git的插件了
	1、引入git远程仓库的项目
		1、File -> import -> Git -> Projects from Git -> 其余步骤自己看


external source

