



1、linux的发展
2、安装linux
	1、安装VM
		1、如果有可以先卸载，自行安装即可
	2、安装centOS6.8(安装之前进入pc的bios->security->Intel..Technology->开启,要不然安装不了)
		1、在vm创建一个虚拟机，有人叫虚拟机空间(centOS的空间)
			1、创建虚拟机
			2、典型安装
			3、稍后安装操作系统
			4、选择Linux->CentOS64位
			5、安装目录：C:\Software\VMSpace\CentOS6.8->名称=Centos64
			6、最大磁盘空间=20G -> 选择将虚拟磁盘拆分成多个文件
			7、完成
		2、虚拟机硬件的配置
			1、内存=2G
			2、处理器数量=2，每个处理器的核数=2
			3、网络配置
				1、默认是NAT：不会ip冲突，linux可以通过主机服务和主机同一网的机器，但是反之不行
				2、桥连接：局域网通讯方便，但是容易ip冲突
				3、主机模式：不能访问外网
		3、真正的安装centOS6.8
			1、选择CD/DVD(IDE)->使用IOS映像文件CentOS-6.8-x86_64-bin-DVD1.iso->勾选启动时连接
			2、启动虚拟机->回车->disc found(注意：使用tab键选skip)->Next->中文->美式英语
			3、基本储存设备->设备警告(忽略)->主机名=hadoop1->选择亚洲上海->root的密码
			4、选择"创建自定义布局"->创建分区
				1、创建->标准分区->选择/boot(ext4)=200M
				2、创建->标准分区->选择(swap)=2048M(如果内存不够了他会虚拟成内存，但是慢)
				3、创建->标准分区->选择/(ext4)=使用全部可用的
			5、格式化->下一步
			6、现在自定义安装(10分钟)
				1、基本系统：留"兼容程序库"、"基本"、"调试工具"
				2、应用程序：留"互联网浏览器"
				3、桌面保留
				4、语言支持留中文
			7、重新引导
				1、前进->同意许可->用户不用创建->时间配置->Kdump(实际生产启用)->重启
			8、安装成功
	3、安装centos7时
		1、注意要选择桌面(兼容win)
3、使用
	1、终端的使用：鼠标右键即可看到
	2、连网：点击右上角的两个电脑图标选择eth0(无效网卡)->即可连接网络
	3、安装VMTool：他可以实现vm和主机的复制粘贴和文件共享
		1、虚拟机->把VMwareTools-V.tar.gz文件复制到/opt
		2、解压：进入/opt:tar -zxvf VMwareTools-V.tar.gz
		3、进入：vmware-tools-distrib执行：./vmware-install.pl->一直回车即可
		4、重启：reboot
		6、现在可以实现复制粘贴了
		7、实现与主机的文件共享VM-setting->选项->添加即可(在centos的/mnt/hgfs/下)
	4、linux的文件系统结构，linux中一切皆文件
		1、/《根目录》
		2、/bin《存放常用命令的,命令vi也在这》
		3、/sbin《存放root用户的常用命令》
		4、/home《存放普通用户的信息》
		5、/root《存放root用户的信息》
		6、/boot《系统启动的引导目录》
		7、/proc《是一个虚拟目录，存放系统内存的映射》
		8、/srv《service的缩写，存放一些服务启动之后需要提取的数据》
		9、/sys《linux2.6内核很大的一个变化》
		10、/tmp《存放临时文件》
		11、/dev《类似windows的设备管理器，把所有硬件以文件的形式管理》
		12、/medis《自动识别一些设备，如U盘、光驱等等，识别之后会挂载到此目录》
		13、/mut《挂载外部目录，比如主机共享的目录》
		14、/opt《额外的安装软件存放位置，类似我的InstallFile目录》
		15、/usr/local《默认的软件安装路径像win的program》
		16、/var《存放经常变化的工具》
		17、/selinux《安全子系统类似360》
		18、/etc《存放配置的相关文件比如win的环境变量》
	5、安装XShell5(linux远程访问客户端)
		1、windows安装XShell5(安装程序Xshell_5.0.1325.exe，自行安装)
		2、连接centos,主机和虚拟机必须在同一网段，并且centos开启了sshd:22服务
	6、安装Xftp5(远程上传下载文件)
		1、免费->自行安装
		2、连接方式要选择SFTP:22，如果中文乱码在属性-修改成UTF-8编码
	7、vi和vim编辑器，vim是vi的增强版
		1、3种模式
			1、正常模式以vim(esc)：默认的模式，可以删除复制行快速定位到某一行
			2、编辑模式\插入模式(i)：按下i, I, o, O, a, A, r, R等任何一个字母之后才会进入编辑模式, 一般来说按i即可.
			3、命令行模式(:)：可以提供你相关指令，完成读取、存盘、替换、离开vim 、显示行号等的动作
		2、快捷键(其他的去看文档)
			1、正Nyy：复制行
			2、正p：粘贴行
			3、正Ndd：删除行
			4、正G和gg：到文件末行和到首行
			5、正20+shift+g：定位到第20行
			6、正u：撤销上个动作
			7、命:/zs：查找zs按下n进入下一个zs
			8、命:set nu\nonu：显示行号和取消
			9、命:q：退出(在没有修改的情况下)
			10、命:q!：强制退出(修改了也无效)
			11、命:wq：保存后退出
	8、开机、重启、用户登录
		1、shutdown
			1、shutdown -h now：立即关机
			2、shutdown -h 1：一分钟后关机
			3、shutdown -r now：立即重启
		2、halt：关机
		3、reboot：重启
		4、sync：一般关机之前最好先执行此命令，会把内存的数据写入磁盘
		5、logout：注销用户，但是在桌面下无效，因为桌面的直接使用界面的注销
	9、用户的管理
		1、添加用户：useradd [选项] 用户名
			1、useradd zs：添加zs默认会在/home目录添加一个zs的目录作为zs的home目录
			2、useradd -d zsD zs：添加zs并且制定他的home目录为/home/zsD
		2、给用户添加密码：需要root权限才可以
			1、passwd zs -> 123 -> 123
		3、删除用户
			1、userdel zs：删除zs保留home目录，实际开发中不会删除他的home目录的
			2、userdel -r zs：删除zs不保留home
		4、用户信息的查看，用户切换
			1、id root：查看root的用信息，可以查看用户id和所属组
			2、su zs：切换用户，从高到底不需要密码，反之需要，可以使用exit切回到原来那个用户
			3、who am i|whoami：查看我是谁
	10、用户组的管理
		1、groupadd wd：添加wd组
		2、groupdel wd：删除wd组
		3、useradd -g wd zwj：添加用户zwj并且制定wd组
		4、usermod -g sl zwj：把zwj换到sl组
	11、etc的三个文件，管理用户、用户口令、用户组的
		1、/etc/passwd(用户的配置文件)：zs:x:500:500::/home/zs:/bin/bash
		2、/etc/shadow(口令配置文件)：不管先
		3、/etc/group(用户组配置文件)：wd:x:502
	12、7中运行级别
		1、0=关机
		2、1=单用户
		3、2=多用户无网络
		4、3=多用户有网络
		5、4=保留的级别，目前没用
		6、5=图形界面
		7、6=重启
		8、可以去/etc/inittab文件的(id:5:initdefault)改默认的运行级别，也可以使用init [012356]切换
		9、强制进入1级别(修改root密码和修改inittab文件)
			在GRUB界面快速按e -> 选 kernel~ -> 输入e -> kernel~ -> 输入1 -> 回车 -> 再按b重启 -> 成功进入1级别
	13、文件目录的命令
		1、pwd：显示当前工作目录的绝对路径
		2、ls
			1、ls：显示当前目录下的所文件和目录
			2、ls -a：显示当前目录下的所文件和目录包括隐藏的
			3、ls -l：显示当前目录下的所文件和目录，以列表方式
			4、ls -al：显示当前目录下的所文件和目录a+l
		3、cd
			1、cd ~：切到用户的home
			2、cd ..：切到上一级目录
		4、目录的创建与删除
			1、mkdir：创建目录，可批量(如果想要多级使用-p参数)
			2、rmdir：删除空目录，可批量(如果想要删除非空的使用rm -rf)
		5、拷贝
			1、cp /home/a.txt /home/aa/：把a.txt文件拷贝到aa目录，如果拷贝目录用下面的
			2、cp -r /home/aa/ /home/bb：把aa目录拷贝到bb，如果有相同文件会提示是否覆盖，使用\cp不提示
		5、远程拷贝（只能linux对linux吧）
			1、scp root@10.10.10.10:/opt/a.txt /opt/  // 从10.10.10.10拷贝/opt/a.txt文件到本地的/opt/目录
			2、scp -r root@10.10.10.10:/opt/ /opt/  // 从10.10.10.10拷贝/opt/目录到本地的/opt/目录
			3、scp /opt/a.txt root@10.10.10.10:/opt/  // 本地文件/opt/a.txt拷贝到远程
			4、scp -r root@10.10.10.10:/opt/ /opt/  // 本地目录/opt/拷贝到远程
			5、参数
				-1：使用ssh协议版本1；
				-2：使用ssh协议版本2；
				-4：使用ipv4；
				-6：使用ipv6；
				-B：以批处理模式运行；
				-C：使用压缩；
				-F：指定ssh配置文件；
				-l：指定宽带限制；
				-o：指定使用的ssh选项；
				-P：指定远程主机的端口号；
				-p：保留文件的最后修改时间，最后访问时间和权限模式；
				-q：不显示复制进度；
				-r：以递归方式复制。
		6、删除目录和文件
			1、rm -rf a.txt aa：批量删除a.txt和aa,-r递归删除 -f不提示
		7、移动、重命名
			1、mv a.txt b.txt：改名
			2、mv a.txt aa/：把a.txt移动到aa目录下，如果需要改名使用aa/newName.txt
		8、文件的浏览
			1、cat：只读的方式打开(enter下一行/空格下一页)
				1、cat -n /etc/profile：带上行号的并且分页打开
			2、more：自带分页(空格=下一页\enter下一行\ctrl+f=下一页\ctrl+b=下一页\n=显示行号\:f显示文件和行\q=退出)
				1、more /etc/profile
			3、less(这个功能比more更强他还支持搜索"/zs"或者"?zs"使用n和N定位下一个和上一个)
				1、例子：less /etc/profile
				2、参数
					-N: 显示行号
				3、快捷键
					[pagedown/pageup]： 向下翻动一页，向上翻动一页
					q: 退出
					G和gg：到文件末行和到首行
					/zs或者?zs: 向前或者向后检索zs，可以使用n向前一个和N反向一个

		9、">"(覆盖)和">>"(追加)指令
			1、(cat a.txt\cal\echo abc) > b.txt：前面的内容覆盖b.txt文件的内容，如果b.txt不存则创建之
			2、(cat a.txt\cal\echo abc) >> b.txt：前面的内容追加到b.txt文件的内容，如果b.txt不存则创建之
		10、echo和head和tail指令
			1、echo $path：输出环境变量的
			2、head -n 5 a.txt：输出a.txt的前5行，默认10
			3、tail -n 5 a.txt：输出a.txt的后5行，默认10
			4、tail -f a.txt：实时监控a.txt文件的变动，很实用
		11、ln(软连接)
			1、ln -s /root/ linkToRoot：创建linkToRoot软连接，实际目录为/root/目录
			2、rm -rf linkToRoot：删除软连接，注意末尾不要带斜杆
	13、history(显示历史命令)
		1、history：显示所有命令
		2、history 10：显示最近10行
		3、!10：执行第10条命令
	14、时间日期指令
		1、date "+%Y-%m-%d %H:%M:%S"：以2000-10-10 10:10:10的格式显示时间
		2、date -s "2000-10-10 10:10:10"：重置系统时间
		3、cal：查看日历
		4、cal 2018：查看2018年的日历
	15、find指令
		1、find /home -name a.txt：查找文件名为a.txt的也可以使用通配符
		2、find /home -user zs：查找属于zs的文件
		3、find /home -size +20M：按文件大小查找(+=大于/-=小于/默认等于)
	16、locate指令
		1、updatedb -> locate a.txt：很快速的定位到a.txt文件(好像是模糊查询)
	17、grep指令
		1、cat a.txt|grep -ni zs：在a.txt文件查找zs并显示行号不区分大小写(n=显示行号i=不区分大小写)
	18、压缩和解压
		1、gzip/gunzip(压缩只能压缩成.zg文件)
			1、gzip a.txt：把a.txt文件压缩成a.txt.gz文件放在此目录(注意a.txt不保留)
			2、gunzip a.txt.gz：把a.txt.gz文件解压到成a.txt存放于当前目录
		2、zip/unzip	// 可以解压war、jar、zip、tar.gz等包
			1、zip -r a.zip /home/：将/home/目录压缩成a.zip(如果是压缩目录需要带-r)
			2、unzip -d /test/  a.zip：将a.zip文件解压到test目录(如果test不存在则创建之)
		3、tar
			1、tar -zcvf a.tar.gz /home/：将home目录压缩成a.tat.gz
			2、tar -zxvf a.tar.gz：将a.tar.gz文件解压到当前目录
			3、tar -zxvf a.tar.gz -C /test/：将a.tar.gz文件解压到test目录(如果test不存在则报错)
	19、文件的所有者和组的管理
		1、chown zs a.txt：把a.txt文件的所有者改成zs(如果是文件可以使用-R参数递归修改子目录和子文件)
		2、chgrp police a.txt：把a.txt文件的所有组改成police(如果是文件可以使用-R参数递归修改子目录和子文件)
	20、文件和目录的权限管理(-rwxr--r--. 1 tom  police   49 10月 28 19:03 a.txt)
		1、rwx(421)的详解
			1、rwx作用到文件
				r: 代表可读(查看文件内容)
				w: 代表可修改(修改+删除=看父目录)
				x: 代表可以执行(执行)
			2、rwx作用到目录
				r: 代表可读(查看目录结构ll)
				w: 代表可修改(创建+删除+重命名)
				x: 代表可以进入该目录
		2、前10位的描述
			1、第1位：-=文件、d=目录、l=连接、c=字符设备(键盘鼠标)、b=块文件(硬盘)
			2、后面的每3为一组
				1、第一组：所有者的权限
				2、第二组：所在组用户的权限
				3、第三组：1,2除外的其他用户权限
		3、1的含义：如果是文件则表示硬链接数(一般都是1)，如果是目录则表示目录的子目录数
		4、tom和police：所有者和所在组
		5、49的含义：如果是文件代表文件大小，如果是目录则固定是4096
		6、10月 28 19:03文件的最后修改时间
	21、权限的管理(u=所有者 g=所在组 o=其他人 a=所有人)
		1、使用ugoa修改(可以使用-R参数递归修改子目录和子文件)
			1、chmod u=rwx,g=rx,o=x 文件/目录
			2、chmod o+w
			3、chmod a-x
		2、使用421修改(可以使用-R参数递归修改子目录和子文件)
			1、chmod 755
	22、crond任务调度
		1、crontab的相关指令
			1、crontab -e：编辑定时任务
			2、crontab -l：查询当前用户所有定时任务
			3、crontab -r：删除当前用户所有定时任务
			4、service crond restart：重启任务调度
		2、5个占位符的说明
			1、第一个：一小时中的第几分钟(0-59)
			2、第二个：一天中的第几小时(0-23)
			3、第三个：一个月中的第几天(1-31)
			4、第四个：一年中的第几个月(1-12)
			5、第五个：一周中的星期几(0-7,0和7代表星期日)
		3、特殊字符的说明
			1、*：代表任务时间，比如第一个*就代表每一分钟
			2、,：代表不连续的时间比如：0 1,2 * * *代表每天1,2
			3、-代表连续的时间段比如：0 5 * * 1-6代表周一到周六
			4、*/1每个多久比如*/10 * * * *代表每个10分钟
	23、linux磁盘分区和挂载
		1、经典案例(给linux增加一块硬盘然后挂载到/home/newdisk)
			1、虚拟机增加硬盘
				1、在虚拟机 -> 设置 -> 一路下一步只有磁盘大小改一下(2G即可) 
			2、分区刚刚那块硬盘
				1、fdisk /dev/sdb -> n(新增分区) -> p(主分区) -> 两次下一步(把剩余的空间分给此分区) -> w(保存退出，若不想保存使用q)
			3、格式化分区
				1、mkfs -t ext4 /dev/sdb1
			4、挂载
				1、mount /dev/sdb1 /home/newdisk/：把sdb1分区挂载到/home/newdisk/目录
				2、umount /dev/sdb1或者umount /home/newdisk：卸载
			5、永久挂载
				1、vim /etc/fstab：打开fstab在最后一行加入：/dev/sdb1 /home/newdisk ext4 defaults 0 0
	24、磁盘使用情况查询
		1、df -lh：查看系统所有磁盘的使用情况
		2、查看指定目录的磁盘占用情况
			1、du -ach /home/
				-s 指定目录占用大小汇总
				-h 带计量单位
				-a 含文件
				-max-depth=1 子目录深度为1
				-c 列出明细的同时增加汇总值
		3、ls -l /home/ | grep "^d" | wc -l：统计home目录的文件个数(如果想要统计文件把d改成"-"，想要递归统计使用-R)
		4、以树状的方式显示先安装tree：yum install tree -> 安装完成就可以使用tree了
	24、内存使用情况：free -m
	25、网络配置(NAT模式)
		1、虚拟机与主机的VMnet8(虚拟网卡要统一网段)
		2、linux下的ip获取方案
			1、自动获取：缺点重启之后ip会变
				1、系统->首先项->网络连接->双击eth0->选中自动获取ip
			2、静态ip(推荐使用这一种)
				1、vim /etc/sysconfig/network-scripts/ifcfg-eth0(centos7是ifcfg-ens33文件)
				2、最少要改下面5句
					ONBOOT=yes(原来是no)
					BOOTPROTO=static(原来是dhcp)
					IPADDR=192.168.216.130
					GATEWAY=192.168.216.2
					DNS1=192.168.216.2
				3、service network restart：重启网络服务
	26、进程管理
		1、显示系统执行的进程：ps -aux | more
			1、参数说明
				-a 显示当前终端的所有进程信息
				-u 以用户的格式显示进程信息
				-x 显示后台进程运行的参数
			2、显示的参数说明
				USER：用户名
				PID：进程id
				%CPU：占用cpu
				%MEM：占用内存
				VSZ：使用虚拟内存
				RSS：使用物理内存
				TTY：使用终端
				STAT：进程状态(S=休眠 R=运行)
				START：启动时间
				TIME：占用cpu的总时间
				COMMAND：启动的执行命令
		2、如果需要显示父进程使用：ps -ef | more
			1、ps -ef | grep sshd
		3、终止进程
			1、kill [-9] 1：终止1号进程-9强制终止(这个应该也可以使用进程名称终止吧？)
			2、killall 进程名称：通过进程名称终止，也可以使用通配符
		4、查看进程树
			1、pstree -p：以树状显示进程信息(-p=显示进程号 -u=显示用户id)
		5、服务进程
			1、service 服务名 [start/stop/restart/reload/status]：注意centos7以后使用systemctl
		6、查看服务名称
			1、setup：星号代表已经启动，可以使用空格关闭之
			2、去/etc/init.d/目录下看
		7、chkconfig命令(使用此目录需要重启)
			1、chkconfig --list：查看当前系统所有服务的各个运行级别的运行状态
			2、chkconfig --level 5 sshd off：设置sshd服务在5级别不自启
			3、chkconfig iptables off：设置防火墙在任何级别都不自启
		8、top动态监控进程(和ps类似)
			1、top：动态监控进程(-d=设置动态更新时间 -i=不显示僵死进程 -p=指定进程id)
			2、top的交互
				P：以cpu的使用率排序，默认是此项
				M：以内存的使用排序
				N：以PID排序
				k：输入k -> 输入进程id即可终止进程
				q：退出
			3、top的显示详解
				1、头信息自己查
				2、表格信息和ps差不多
		9、查看网络情况
			1、netstat -anp | more：查看所有的网络服务(-an=按一定顺序排序 -p=显示调用者)
	27、rpm和yum
		1、rpm包的管理(是RedHat package Manager，因为做的好所以大家都用)
			1、rpm -qa | firefox：查看火狐包
				1、rpm qi firefox：查询火狐包信息
				2、rpm ql firefox：查询火狐包的所有文件
				3、rpm qf a.txt：2的反过来，查询a.txt的所属包
			2、rpm -e firefox：卸载firefox(如果提示有依赖此包可以使用--nodeps强制卸载)
			3、rpm ivh rpm包：安装rmp包
		2、yum：他基于rpm的，他可以自动处理依赖关系，用起来比较简单
			1、yum list|grep xx：连网查找xx包
			2、yum install xx：连网下载安装xx包
	28、j2ee定制版
		1、安装jdk1.7
			1、先将软件通过xftp5上传到/opt/下
			2、解压：tar -zxvf 包
			3、配置环境变量：
				1、vim /etc/profile
				2、文件末尾加入三行 
					JAVA_HOME=/opt/jdk1.7.0_79
					PATH=/opt/jdk1.7.0_79/bin:$PATH
					export JAVA_HOME PATH
			4、需要重新登录才会生效
			5、测试是否成功：java -version
		2、安装tomcat7
			1、解压：tar -zxvf apache-tomcat-7.0.70.tar.gz
			2、使用./startup.sh和./shutdown.sh启动关闭之
			3、防火墙开放端口8080
				1、vim /etc/sysconfig/iptables，添加一行-A INPUT -m state --state NEW -m tcp -p tcp --dport 8080 -j ACCEPT
				2、重启防火墙：service iptables restart
				3、查看开放的端口：service iptables status
			4、测试是否成功
				1、http://localhost:8080
		3、安装eclipse(有空再去安装)
			1、tar -zxvf 包：解压即可用
		4、安装mysql(通过源代码安装高版本的5.6.14)(有空再去安装)
			1、卸载旧版本
				1、rpm -qa | grep mysql：查看如果有安装过使用步骤2卸载
				2、rpm -e --nodeps mysql_libs：强制卸载
			2、安装编译代码需要的包：yum -y install make gcc-c++ cmake bison-devel  ncurses-devel
			3、tar xvf mysql-5.6.14.tar.gz
			4、cd mysql-5.6.14
			5、编译安装[源码=》编译]
				cmake -DCMAKE_INSTALL_PREFIX=/usr/local/mysql -DMYSQL_DATADIR=/usr/local/mysql/data -DSYSCONFDIR=/etc -DWITH_MYISAM_STORAGE_ENGINE=1 -DWITH_INNOBASE_STORAGE_ENGINE=1 -DWITH_MEMORY_STORAGE_ENGINE=1 -DWITH_READLINE=1 -DMYSQL_UNIX_ADDR=/var/lib/mysql/mysql.sock -DMYSQL_TCP_PORT=3306 -DENABLED_LOCAL_INFILE=1 -DWITH_PARTITION_STORAGE_ENGINE=1 -DEXTRA_CHARSETS=all -DDEFAULT_CHARSET=utf8 -DDEFAULT_COLLATION=utf8_general_ci
			6、编译并安装(30分钟左右)：make && make install
			7、配置mysql
				1、设置权限
					1、cat /etc/group：查看用户组
					2、cat /etc/passwd：查看用户列表
					3、如果没有mysql组和用户创建之
					4、groupadd mysql
					5、useradd -g mysql mysql
					6、chown -R mysql:mysql /usr/local/mysql
				2、mysql的初始化配置
					1、加入安装路径：cd /usr/local/mysql/
					2、执行：scripts/mysql_install_db --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data --user=mysql 
				3、启动mysql
					1、cp support-files/mysql.server /etc/init.d/mysql：拷贝服务脚本到init.d目录(此命令在/usr/local/mysql下执行)
					2、chkconfig mysql on：并设置开机启动
					3、service mysql start：启动MySQL
				4、执行下面的命令修改root密码
					1、cd /usr/local/mysql/bin
					2、./mysql -uroot  
					3、mysql> SET PASSWORD = PASSWORD('root');
			8、注意：在启动MySQL时，先在/etc/my.cnf目录下找，找不到则会搜索"$basedir/my.cnf"，为了防止干扰，修改名称：mv /etc/my.cnf /etc/my.cnf.bak

常用命令
	1、查看内核：uname -r
	2、查看自己安装的contos的版本信息：lsb_release -a(centos7无此命令)
	3、getconf LONG_BIT：查看系统的是32为还是64位

问题
	1、用户属于多个组怎么弄呢？
	2、删除组，然后组用户怎么办？
	3、文件的恢复
	2、curl命令的使用
	5、使用ulimit -a // 查看当前用户的限制



看完57


