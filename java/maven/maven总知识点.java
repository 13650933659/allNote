















问题
	1、B->A，B里边有A吗的jar包吗?
		1、没有，但是pom文件有指定A的坐标，到时开发时maven就会自动下载，但是如果是web项目的话就有了
	2、maven怎么把我的test测序打包部署到web根目录(wtpwebapps)呢？
		1、这个不算maven的打包，这是eclipse的作用而已，他把我们编译好的文件自动同步到web根目录
		2、maven的打包是jar\war
	3、tomcat运行只关心：web应用的名称，即根目录(里边WEB-INF/classes就是类路径，很重要)

1、maven是什么
	1、Maven项目是项目对象模型(Project Object Module)，可以通过描述信息来管理项目的构建(可以说成项目自动构建工具)
2、maven环境搭建
	1、去https:\\maven.apache.org/download.cgi下载最新的maven工具，下载完直接安装就可以了
	2、环境变量(历史原因) M2_HOME = C:\apache-maven-3.5.4 -> path += %M2_HOME%\bin
	3、验证maven是否安装成功mvn -v
3、maven的目录结构
	Hello
		src/main/java
		src/main/resources
		src/test/java
		src/test/resources
		pom.xml
4、常用命令
	1、mvn -v						// 查看maven的版本
	2、mvn clean					// 删除target目录
	3、mvn compile\mvn test-compile	// 编译项目
	4、mvn test						// 运行测试用例
	5、mvn package					// 把我们的项目打成jar包(clean->compile->)
	6、mvn install					// 安装jar包到我们本地仓库中，其项目需要引用就可以根据此项目的坐标引用，这个好像包括mvn的功能
	7、mvn deploy					// 通过插件自动部署到我们本机的tomcat容器，远程的不知道可以不，此命令最好是在命令行使用
	8、mvn site
4、Eclipse安装maven，如果是Eclipse4.0以上就不用安装了
	1、配置jdk，一定要用jdk里边的jre
	2、去preferences -> Maven -> installations -> add我们自己安装的maven -> User Settings配置我们的setting.xml
	3、创建maven项目 -> 选择maven-archetype-quickstart，或者跳过快捷创建
	4、运行定位到pom.xml右键run as -> build...，然后写自己的maven命令
5、maven的依赖传递
	1、一处引入即可，compile的才有传递性，依赖的排除：<exclusion>GA就行了</exclusion>
	2、scope的取值，默认值是compile
		1、compile： {main=√,test=√,package=√,example=spring core}
		2、test：    {main=×,test=√,package=×,example=junit}
		3、provided：{main=√,test=√,package=×,example=servlet}
6、依赖冲突
	1、短路优先-先依先用
7、继承
	1、父工程的打包是pom，统一管理依赖的版本
		1、父亲的manage依赖不会被继承(子类按需引入,也可以替换版本)，但是其他的依赖可以直接继承
8、聚合：modules，一键安装即可管理子模块
9、maven构建web项目
	1、还是跳过那个快捷的创建，然后properties->project facets->Dinamic web module(重新启动点击下面链接创建src/main/webapp和web.xml)



10、settings.xml的配置说明
	1、配置创建maven项目默认使用的jdk版本
		<profile>
		  <id>jdk-1.8</id>
		  <activation>
			<activeByDefault>true</activeByDefault>
			<jdk>1.8</jdk>
		  </activation>

		  <properties>
			<maven.compiler.source>1.8</maven.compiler.source>
			<maven.compiler.target>1.8</maven.compiler.target>
			<maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
		  </properties>
		</profile>
	2、maven的仓库
		1、全球中心仓库(配置镜像提高下载速度，优先阿里的再去中央仓库)
			<mirror>
				<id>alimaven</id>
				<name>aliyun maven</name>
				<url>http://maven.aliyun.com/nexus/content/groups/public/</url>
				<mirrorOf>central</mirrorOf>
			</mirror>
			<mirror>
				<id>repo2</id>
				<name>repo2 maven</name>
				<url>http://repo2.maven.org/maven2</url>
				<mirrorOf>central</mirrorOf>
			</mirror>
		2、本地仓库
			1、默认位置：C:\Users\Chenjiaru\.m2\repository，修改<localRepository>新的位置</localRepository>











1、以继承的方式的springboot
	1、我的 ->> spring-boot-starter-parent ->> spring-boot-dependencies
	2、然后自己的dependencies中去按需引入
2、以引入的方式的springboot
	1、我的 -> spring-boot-dependencies



