















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
	5、mvn package					// 把我们的项目打成jar/war包 这个取决于 <packaging>jar</packaging> 如果是父工程使用pom
	6、mvn install					// 安装jar包到我们本地仓库中，其项目需要引用就可以根据此项目的坐标引用，这个好像包括mvn的功能
	7、mvn deploy					// 通过插件自动部署到我们本机的tomcat容器，远程的不知道可以不，此命令最好是在命令行使用
	8、mvn site
4、Eclipse安装maven，如果是Eclipse4.0以上就不用安装了
	1、配置jdk，一定要用jdk里边的jre
	2、去 preferences -> Maven -> installations -> add我们自己安装的 maven -> User Settings配置我们的 setting.xml
	3、创建maven项目 -> 选择maven-archetype-quickstart，或者跳过快捷创建
	4、运行定位到pom.xml右键run as -> build...，然后写自己的maven命令
5、maven的依赖传递
	1、一处引入即可，compile的才有传递性，依赖的排除：<exclusion>GA就行了</exclusion>
	2、scope的取值，默认值是compile
		1、 compile   {main=√,test=√,package=√,example=spring core}
		2、 test      {main=×,test=√,package=×,example=junit}
		3、 provided  {main=√,test=√,package=×,example=servlet}
		4、 runtime   {main=×,test=×,package=√,example=mysql-connector-java}
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
11、maven 安装jar包到本地
	1、安装指定文件到本地仓库命令： mvn install:install-file
		-DgroupId=<groupId>       : 设置项目代码的包名(一般用组织名)
		-DartifactId=<artifactId> : 设置项目名或模块名 
		-Dversion=1.0.0           : 版本号
		-Dpackaging=jar           : 什么类型的文件(jar包)
		-Dfile=<myfile.jar>       : 指定jar文件路径与文件名(同目录只需文件名)
	2、安装命令实例：
		mvn install:install-file -DgroupId=com.baidu -DartifactId=ueditor -Dversion=1.0.0 -Dpackaging=jar -Dfile=ueditor-1.1.2.jar
		mvn install:install-file -DgroupId=net.sf.dozer -DartifactId=dozer -Dversion=5.5.1 -Dpackaging=jar -Dfile=C:/Software/net/sf/dozer/dozer/5.5.1/dozer-5.5.1.jar
12、打包，测试环境根本就不要打包，直接把配置文件考过去，让后他会自动编译java文件(新增的java他会加入的)
	1、打包命令：clean package -Dmaven.test.skip=true 或者  clean compile -Dmaven.test.skip=true
		1、打包参数
			-DskipTests ：不执行测试用例，但编译测试用例类生成相应的class文件至target/test-classes下。
			-Dmaven.test.skip=true：不执行测试用例，也不编译测试用例类。
			-Dname=zs：springboot配置文件可以使用@name@来获取值
		2、打包的Profiles(separated with space)：这里可以指定pom.xml文件指定的profile的id，然后里面的属性springboot配置文件可以使用@name@来获取值
			 <profiles>
					<profile>
						<id>dev-historical-datasource-mode</id>
						<properties>
							<profile.active>dev</profile.active>
							<application.datasource-mode>historical</application.datasource-mode>
						</properties>
						<activation>
							<activeByDefault>true</activeByDefault>
						</activation>
					</profile>
					<profile>
						<id>dev-daily-datasource-mode</id>
						<properties>
							<profile.active>dev</profile.active>
							<application.datasource-mode>daily</application.datasource-mode>
						</properties>
					</profile>
				</profiles>
		3、打包时资源文件的管理（先去掉全部，然后在引入，参考下面的）
			<resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <excludes>
                    <exclude>*</exclude>
                </excludes>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <filtering>true</filtering>
                <includes>
                    <include>application.properties</include>
                    <include>*-${profile.active}.*</include>
                    <include>mybatis-config.xml</include>
                </includes>
            </resource>
		4、打包指定war的名称加上时间戳
			1、指定时间格式
				<properties>
					<maven.build.timestamp.format>yyyyMMddHHmmss</maven.build.timestamp.format>
				</properties>
			2、war的名称
				<build>
					<finalName>${project.artifactId}-${project.version}_${maven.build.timestamp}</finalName>
				</build>
		5、资源文件的改名插件
			<plugin>
                <groupId>com.coderplus.maven.plugins</groupId>
                <artifactId>copy-rename-maven-plugin</artifactId>
                <version>1.0.1</version>
                <executions>
                    <execution>
                        <id>copy-and-rename-hanlp</id>
                        <phase>compile</phase>
                        <goals>
                            <goal>rename</goal>
                        </goals>
                        <configuration>
                            <sourceFile>${project.build.outputDirectory}/hanlp-${profile.active}.properties</sourceFile>
                            <destinationFile>${project.build.outputDirectory}/hanlp.properties</destinationFile>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
		6、 打成可运行的 jar包
			 1、 加入以下插件和配置（前提是： <packing>jar</packing>）
				 <plugin>
					<groupId>org.springframework.boot</groupId>
					<artifactId>spring-boot-maven-plugin</artifactId>
					<version>1.3.3.RELEASE</version>
					<executions>
						<execution>
							<goals>
								<goal>repackage</goal>
							</goals>
						</execution>
					</executions>
				</plugin>



			2、打完包之后
				target目录下会两个jar包 带 a.jar.original 和 a.jar, 其中 a.jar 里面包含了全部依赖，在 lib目录， 如果是springboot的项目，直接有内嵌的tomcat了，直接可以使用 java -jar a.jar 运行，然后应用就运行起来了
    </build>
	3、运行：
		1、直接使用spring-boot:run命令(需要maven的插件)
		2、使用springboot的主程序运行
			在VM options：-Dspring.profiles.active=dev -Dapplication.datasource-mode=daily
		3、 java -jar a.jar // 运行可运行的jar包





