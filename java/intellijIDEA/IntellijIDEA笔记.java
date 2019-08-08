













1、设置
	1、自动编译项目
	2、字体设置
	3、注释、字符串等等的字体颜色
	4、代码模板

2、创建web模块
	1、创建web模块
	2、tomcat的镜像和web模块的部署

3、git的集成和使用
	1、settings -> git -> path to Git executable:配置你的git.exe(C:\Program Files\Git\cmd\git.exe)
	2、settings -> github -> 连接你的github代码仓库
	3、clone(从远程仓库clone代码到本地) -> 【add(加到缓存区)】 -> commit(提交到本地仓库) -> push(远程提交) -> pull(发起pull request等待同意)
	4、把我们的项目分享至github

4、maven集成和使用
	1、maven->Maven home diretory:(使用我们自己下载的maven)->User settings file:(也使用我们自己的)->Local repository(默认C盘也好吧)
	2、修改我们的settings.xml
		1、通过maven下载过来的依赖可以关联到源码吗？
		2、<localRepository>本地maven仓库的位置，默认是C盘</localRepository>
		3、mirror的配置(alimaven)，为了提高访问速度
	3、Maven->importing->import Maven projects automatically(勾上说明我们pom.xml文件一旦修改就会马上更新依赖jar包)->automatically download(Sources和Documentation)不建议勾上
	4、使用maven projects窗口的Lifecycle使用maven的命令
5、下载Idea插件

6、取消自动更新Idea

7、 插件安装
	7、  lombok 插件的安装和使用 : 请参考 ：https://jingyan.baidu.com/article/0a52e3f4e53ca1bf63ed725c.html
	8、 安装  free mybatis plugin（实现mybatis的 xml 和 mapper的跳转） : https://www.wandouip.com/t5i73222/
	9、 安装less插件 https://www.cnblogs.com/liaojie970/p/6653593.html


