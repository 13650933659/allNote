



1、docker是什么，为什么要出现，官网
	1、docker是go写的，是一种隔离技术，使得软件本身和其运行环境能够做到"一次封装，到处运行"（他是迷你版的linux）
	2、为了减轻运维的工作量
	3、docker官网：
		1、原始官网：http:\\www.docker.com
		2、docker中文网站：https:\\www.docker-cn.com/
		3、Docker Hub官网: https:\\hub.docker.com/
2、docker的三要素
	1、镜像：是一种可独立安装的软件包，它包含软件本身和其运行环境。
	2、容器：镜像的实例
	3、仓库：存放镜像的场所
3、安装docker
	1、cnetos6.8的安装
		1、yum install -y epel-release
		2、yum install -y docker-io
		3、安装后的配置文件：/etc/sysconfig/docker
		4、启动docker后台服务：service docker start
		5、验证是否安装成功：docker version
	2、centos7+的参考其他笔记
4、镜像加速器(解决下载镜像慢的问题)
	1、去https:\\dev.aliyun.com/search.html阿里云注册一个账号，也可以使用淘宝账号直接登录
	2、得到属于自己的加速地址：https:\\l5k5u5op.mirror.aliyuncs.com
	3、在/etc/sysconfig/docker 文件加入：other_args="--registry-mirror=https://l5k5u5op.mirror.aliyuncs.com"
	4、重启docker：service docker restart
	5、ps -ef | grep docker：如果看到--registry-mirror参数说明阿里云的镜像配置成功
	6、测试一把：docker run hello-world(如果没有此镜像就会去镜像仓库下载)
5、docker的常用命令
	1、帮助命令
		1、docker --version
		2、docker --info
		3、docker --help
	2、镜像命令
		1、docker images：列出本机上的镜像
			1、参数说明
				1、-a 列出本地所有的镜像(含中间印像层)
				2、-q 只显示镜像id
				3、--digests 显示镜像的摘要信息
				4、--no-trunc 显示完整的镜像信息
			2、显示参数说明
				1、REPOSITORY：表示镜像的仓库源
				2、TAG：镜像的标签
				3、IMAGE ID：镜像id
				4、CREATED：创建时间
				5、VIRTUAL SIZE：镜像大小
		2、docker search [options] 某个XXX镜像名字：连网搜索镜像
			1、网站：https:\\hub.docker.com
			2、options说明
				--no-trunc 显示完整的镜像描述
				-s 列出收藏大于等于指定的值
				-automated 只列出automated build类型的镜像
		3、docker pull 某个镜像名称[:tag]：下载镜像默认是最新版本可以通过tag指定版本
		4、docker删除镜像
			1、docker rmi -f 镜像id1 镜像id2 ：支持批量删除
			2、docker rmi -f $(docker images -qa)：删除全部
	3、容器命令
		1、docker run [options] 镜像id [command] [arg...]：运行镜像实例(容器)
			1、options说明
				--name 容器新的名字，如果不指定他会随便给一个
				-d 后台运行，并返回容器id
				-i 以交互式运行容器，通常与-t同时使用
				-t 为容器重新分配一个伪输入终端，通常和-i同时使用
				-P 随机端口映射
				-p 指定端口映射，有以下四种
					ip:hostPort:containerPort
					ip::containerPort
					hostPort:containerPort
					containerPort
		2、启动contos容器的例子(第一次启动)
			1、docker run -it centos /bin/bash：启动交互式的容器，默认是/bin/bash
			2、docker run -d centos /bin/sh -c "while true;do echo hello zzyy;sleep 2;done"：因为后台启动需要前台界面，要不然会自动关闭，所以给一个while循环
		3、docker ps [options]：列出当前正在运行的容器
			1、options说明
				-a 列出当前所有正在运行的容器+历史运行过的
				-l 显示上一个容器
				-n 显示最近个容器(有包括正在运行的吗？包括了)
				-q 静默模式，只显示容器编号
				--no-trunc 不截断输出
		4、退出容器(适用于交互式的容器)
			1、exit：容器停止退出
			2、ctrl+P+Q：容器不停止退出
		5、重新进入正在运行的容器并以命令行交互
			1、docker exec -it 容器id /bin/bash：也可以把/bin/bash -> shell命令直接操作容器
			2、docker attach 容器id：重新进入容器(我喜欢用第1种)
			3、1,2的区别：attach直接进入容器启动命令终端，不会启动新的进程，exec是在容器中打开新的终端，并且可以启动新的进程
		6、启动历史的容器：docker start 容器名称或者id
		7、重启历史的容器：docker restart 容器名称或者id
		8、停止正在运行的容器：docker stop 容器名称或者id(如果想强制停止stop改为kill)
		9、删除已停止的容器(注意是已停止的)
			1、docker rm 容器id：删除一个容器
			2、批量删除
				1、docker rm -f $(docker ps -qa)
				2、docker ps -qa | xargs docker rm
		10、查看日志：docker logs -ft --tail 10 容器id:
			-t 显示时间
			-f 跟随最新的日志打印
			--tail N 显示最后N条
		11、查看容器内运行的进程：docker top 容器id
		12、查看容器内部细节：docker inspect 容器id
		13、从容器内拷贝文件到主机上：docker cp 容器id:容器内路径 主机的目标路径
	4、tomcat容器使用案例
		1、docker run -it -p 8888:8080 tomcat：运行tomcat容器外部使用8888端口访问
		2、docker exec -it /bin/bash：进入tomcat容器的home目录可以进行操作，比如删除docs
		3、docker commit -a="cjr" -m="del tomcat docs" 容器id cjr/tomcat01:1.8：提交我们制定好的tomcat(只提交到本机，没有到远程仓库)
	5、数据卷
		1、方法1直接使用命令添加
			1、docker run -it -v /宿主机目录a:/容器内目录b centos /bin/bash：带数据卷功能的启动容器，ab之间可以共享，默认是两边都可读写
			2、docker run -it -v /宿主机目录a:/容器内目录b:or centos /bin/bash：带数据卷功能的启动容器，ab之间可以共享，主机可读写，容器只读
			3、通过1,2的启动之后，可以使用docker inspect 容器id验证数据卷是否绑定成功，挂载主机目录出现cannot open directory .: Permission denied 解决办法：在挂载目录后多加一个--privileged=true参数即可
		2、使用dockerfile添加
			1、创建文件/mydocker/dockerfile，增加如下内容
				# 解释：继承centos的dockerfile,在容器中创建dataVolume_c1和dataVolume_c2两个数据卷，主机对应的卷直接使用docker inspect查
				FROM centos
				VOLUME ["/dataVolume_c1","/dataVolume_c2"]
				CMD echo "finished,--------success1"
				CMD /bin/bash
			2、docker build -f /mydocker/dockerfile -t zzyy/centos：生成新的centos镜像(增强了启动之后就会创建两个数据卷)
		3、数据卷-容器间的数据传递共享(--volumes-from：就像主从复制一样，下列三者之间互相同步)
			1、启动父容器：docker run -it --name dc01 zzyy/centos
			2、dc02继承dc01：docker run -it --name dc02 --volumes-from dc01 zzyy/centos
			3、dc03继承dc01：docker run -it --name dc03 --volumes-from dc01 zzyy/centos
	6、Dockerfile文件：是由一系列命令和参数构成的脚本
		1、构建的三部曲
			1、编写Dockerfile文件
			2、docker build
			3、docker run
		2、Dockerfile内容基础知识
			1、每条保留字指令都必须大写字母
			2、指令从上到下执行
			3、#表示注释
			4、每一条指令都会创建一个新的镜像层，并且对镜像进行提交
		3、保留字指令
			1、FROM：基础镜像，像父亲
			2、MAINTAINER：镜像的维护者姓名和邮箱地址
			3、RUN：容器构建时需要运行的命令
			4、EXPOSE：当前容器对外暴露的端口
			5、WORKDIR：指定创建容器后，终端的落脚点
			6、ENV：用来构建进行过程中设置的环境变量
			7、COPY：可以将宿主机的文件拷贝到容器
			8、ADD：带解压缩功能的copy，可以将宿主机的文件解压拷贝到容器
			9、VOLUME：数据卷，用于容器数据持久化
			10、CMD：指定容器启动时要运行的命令，只有最后一个生效，也会被docker run参数替换
			11、ENTRYPOINT：指定容器启动是要运行的命令，和docker run参数一并起作用
			12、ONBUILD：当被继承时触发的脚本
		4、base镜像(scratch)：docker hub中99%的镜像都是从base镜像构建出来的
		5、自定义镜像的案例(vim支持 网络配置支持 登录后默认路径)
			1、自定义mycentos镜像
				1、编写
					1、mkdir -p /zzyyuse/mydockerfile/
					2、vim Dockerfile 加入如下内容
						FROM centos
						MAINTAINER zzyy<zzyy167@126.com>
						ENV MYPATH /usr/local
						WORKDIR $MYPATH
						RUN yum -y install vim
						RUN yum -y install net-tools
						EXPOSE 80
						CMD echo $MYPATH
						CMD echo "success--------------ok"
						CMD /bin/bash
				2、构建成本地镜像：docker build -t mycentos:1.3 .(如果你的文件名为Dockerfile就不用-f 文件名)
				3、运行：docker run -it mycentos:1.3
				4、列出镜像变更历史：docker history 镜像名:tag
			2、CMD和ENTRYPOINT的案例(都是指定容器启动是要运行的命令)
				1、CMD案例(run参数会覆盖cmd)
					1、以tomcat为例，因为tomcat有一句CMD ["catalina.sh", "run"]
					2、启动tomcat容器：docker run -it -p 8888:8080 tomcat ls ls覆盖了CMD，结果没启动tomcat
				2、ENTRYPOINT案例(run参数和entrypoint一并起作用)
					1、制作一个可以查询ip信息的centos容器(按照三部曲自己做)
					2、启动容器：docker run myip -i：可以看到entrypoint和run参数一并起作用
			3、自定义Tomcat9镜像
				1、mkdir -p /zzyyuse/mydockerfile/tomcat9/
				2、在上面目录新建c.txt文件：touch c.txt
				3、把apache-tomcat-9.0.8.tar.gz和jdk-8u171-linux-x64.tar.gz放在上面目录
				4、在上面目录创建Dockerfile文件加入如下内容
					FROM         centos
					MAINTAINER    zzyy<zzyybs@126.com>
					#把宿主机当前上下文的c.txt拷贝到容器/usr/local/路径下
					COPY c.txt /usr/local/cincontainer.txt
					#把java与tomcat添加到容器中
					ADD jdk-8u171-linux-x64.tar.gz /usr/local/
					ADD apache-tomcat-9.0.8.tar.gz /usr/local/
					#安装vim编辑器
					RUN yum -y install vim
					#设置工作访问时候的WORKDIR路径，登录落脚点
					ENV MYPATH /usr/local
					WORKDIR $MYPATH
					#配置java与tomcat环境变量
					ENV JAVA_HOME /usr/local/jdk1.8.0_171
					ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
					ENV CATALINA_HOME /usr/local/apache-tomcat-9.0.8
					ENV CATALINA_BASE /usr/local/apache-tomcat-9.0.8
					ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/lib:$CATALINA_HOME/bin
					#容器运行时监听的端口
					EXPOSE  8080
					#启动时运行tomcat
					# ENTRYPOINT ["/usr/local/apache-tomcat-9.0.8/bin/startup.sh" ]
					# CMD ["/usr/local/apache-tomcat-9.0.8/bin/catalina.sh","run"]
					CMD /usr/local/apache-tomcat-9.0.8/bin/startup.sh && tail -F /usr/local/apache-tomcat-9.0.8/bin/logs/catalina.out
				5、构建：docker build -t t9 .
				6、运行：docker run -d -p 9080:8080 --name t9 
					-v /t9/test:/usr/local/apache-tomcat-9.0.8/webapps/test 
					-v /t9/logs/:/usr/local/apache-tomcat-9.0.8/logs 
					--privileged=true t9
				7、验证：localhost:9080
				8、结合上述的容器新增一个web应用test(自己做)
		6、安装常用软件
			1、安装mysql5.6
				1、下载镜像：docker pull mysql:5.6
				2、运行：docker run -d -p 3306:3306 --name mysql 
					-v /volumes/mysql/conf/:/etc/mysql/conf.d/ 
					-v /volumes/mysql/logs/:/logs/ 
					-v /volumes/mysql/data/:/var/lib/mysql/ 
					-e MYSQL_ROOT_PASSWORD=123456 
					mysql:5.6
				3、进入容器内：docker exec -it 容器id /bin/bash
				4、备份数据库测试：docker exec myql服务容器ID sh -c ' exec mysqldump --all-databases -uroot -p"123456" ' > /volumes/mysql/all-databases.sql
			2、安装redis
				1、下载镜像：docker pull redis:3.2
				2、运行：docker run -d -p 6379:6379 
					-v /volumes/myredis/data/:/data/ 
					-v /volumes/myredis/conf/redis.conf:/usr/local/etc/redis/redis.conf  
					redis:3.2 
					redis-server /usr/local/etc/redis/redis.conf 
					--appendonly yes
				3、进入容器：docker exec -it 容器id /bin/bash
				4、登录进入redis：redis-cli -p 6379
				5、set k1 v1 -> set k2 v2 -> shutdown
				6、/volumes/myredis/data/目录下是否有.aof文件
		7、将我们的本机镜像推送到阿里云
			1、自己做好本地镜像
			2、去https:\\dev.aliyun.com/search.html仓库
			3、仓库->管理->将镜像推送到Registry(执行三条命令，如果是root用户不用sudo)
				$ sudo docker login --username=1282654205@qq.com registry.cn-hangzhou.aliyuncs.com  //使用的是Registry密码
				$ sudo docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/cjr/mycentos:[镜像版本号]
				$ sudo docker push registry.cn-hangzhou.aliyuncs.com/cjr/mycentos:[镜像版本号]
			4、推送之后可以在：https:\\dev.aliyun.com/search.html查询到，我这里找不到



问题
	1、如果没有持久化，重启容器数据还在吗
		1、在的，只要容器没被删除，重启之后数据还在的
