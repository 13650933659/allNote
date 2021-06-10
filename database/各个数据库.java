

1、通用与区别
	1、通用的case when then写法
		1、case when statusSequence is null then 0 else statusSequence end ASC,time DESC  //把null转成0,好像还有一个方法可以把null转成0但是不知道会不会通用（要注意在排序时o的null是最大的数字，s的null是最小的数字）
		2、select * from aa a where a.name=case when a.name='a' then 'a' else 'b' end;
		3、select * from aa a where case when a.name is not null then a.name else a.alias end='a';

	2、的数值型如果是'' （sqlServer他是0，mysql他报错，oracle他已经把''直接当成null处理了）

	3、select * from aa a where a.name not in(null,'zs');  //这样查不出数据来的，用时注意，需要使用 not exists

	4、各个数据库的判空（即：''和null）（oracle和mysql用length,sqlserver用len）


	4、各个数据库的修改字段长度


	5、 not in 和 not exists
		参考 https://www.cnblogs.com/xuyufengme/p/9175929.html

	6、批量插入只需要 docid 即可
		insert into sys_extraction_temp(docid) select docid from sys_document_0 limit 0,100;

	7、各个数据库的分页算法
		公用算法：pageCount=(rowCount-1)/pageSize+1;
		1、mysql；
			select * from user limit ?,?;  //始于0
			String startRow=(pageSize*(pageNow-1))+"";
			String endRow=pageSize+"";
			String[] parementrs={startRow,endRow};

		2、oracle：为什么要三重查询？因为他要拿到固定的rownum，但是有的老师只写了3层
			select * from (select t1.*,rownum rn from (select * from user) t1 where rownum<=?) t2 where t2.rn>=?; // rownum始于1
			String startRow=(pageSize*(pageNow-1)+1)+"";
			String endRow=pageNow*pageSize+"";
			String[] parementrs={endRow,startRow};

		3、sqlServer:
			--1、找出第三个到第六个雇员，（按入职时间先后排序）两次排序（因为sqlServer默认排序）
			select top ? * from emp where empNo not in(select top ? empNo from emp order by hiredate) order by hiredate;
			String startRow=pageSize+"";
			String endRow=(pageSize*(pageNow-1))+"";
			String[] parementrs={startRow,endRow};

2、 各种数据库
	1、 mysql
		1、 mysql 启动关闭-还原备份-创建表案例
			1、mysql的dos系统的启动、关闭、登录（启动和关闭是利用window系统的程序，登录使用mysql自带的程序所以要去bin执行）
				1、启动：C:\Windows\system32>net start mysql
				2、关闭：C:\Windows\system32>net stop mysql
				3、登录：C:\Windows\system32>mysql -hlocalhost -uroot -pp2ssword
			2、mysql的备份还原（利用mysql自带的程序所以要切换到mysql/bin目录）
				2.1、在dos命令行备份
				命令：mysqldump –uroot –ppassword dbName > dbName.sql
				例子：C:\MySQL Server 5.5\bin>mysqldump -uroot -proot --default-character-set=utf8 akai0506 > E:\mysqlDatabaseBackFile\akai0506.sql
				
				2.2、还原，还原先创建一个空的 dbName 数据库
					2.1.1、mysql命令行还原
						1，将要导入的.sql文件移至bin文件下，这样的路径比较方便
						2，进入MySQL：mysql -uroot -proot
						3，在MySQL-Front中新建你要建的数据库，这时是空数据库，如新建一个名为news的目标数据库
						4，输入：mysql>use news
						5，导入文件：mysql>source mysql>source news.sql;
					2.1.2、dos命令行还原(这一种比较好用)
						1、cd/d到C:\mysql5.6\bin> mysql -uroot -proot dbName < E:\installFile\jNeVfPWUyfeFbgUPUCYv.sql
			3、mysql数据表的创建案例
				create table hw_users(
					_user_id bigint primary key auto_increment,  --主键默认不能为空,自增长
					_dept_id references hw_dept(_dept_id),  --外键列
					_user_name varchar(32) not null,  --用户名不能为空
					_password varchar(32) not null,  --密码名不能为空
					_sex enum('男','女')  default '男',  --人的性别默认男，要填只能在男女选一个
					_age tinyint,  --年龄从0-255的整型数据。存储大小为 1 字节
					_salary float(8,2) unsigned,  --无符号值（丢掉负值）
				);
				
				create table PluginInfo(
					id bigint(20) primary key auto_increment comment '插件id，主键',  --主键默认不能为空唯一，一般都是设置为自增，bigint长度默认20，int默认11
					version  bigint NOT NULL COMMENT '插件版本' ,
					code int NOT NULL COMMENT '插件编码' ,
					deviceType varchar(255) NOT NULL COMMENT '设备类型' ,
					name  varchar(255) NOT NULL COMMENT '插件名称' ,
					url  varchar(2000) NOT NULL COMMENT '插件文件存储的url',
					price decimal(8,2)					-- 代表整数和小数的位数<=8,小数 <= 2  默认 DECIMAL(10)
					// DATETIME yyyy-MM-dd hh:mm:ss
				) comment '插件信息表' 
					
				 create index code on PluginInfo (code);		// 创建索引


				insert into hw_users values(1,1,'hsp','mm','男',18,10000.00);
			4、mysql查看数据的结构
				1、create table table_name/desc table_name
			5、创建索引
				1、create index 索引名称 on 表名(列名1,列名2)
				2、还有unique index唯一的索引，两个值不可以相同有点像主键了
				3、查看是否创建成功：show index from 表名，也可以在UI客户端清晰的看到
			6、TRUNCATE,DELETE,DROP放在一起比较
				TRUNCATE TABLE：删除内容、释放空间但不删除定义。
				DELETE TABLE:删除内容不删除定义，不释放空间。
				DROP TABLE：删除内容和定义，释放空间。
		2、 mysql 的使用经验
			1、是auto_increment就一定是主键，是主键不一定是A_I
			2、order by（默认是asc升序的，desc就是降序） 的后面，至少mysql可以用别名（别的数据库就不知道了）
			3、select sum(num) count from t group by _num order by count desc limit 1,1;
				这句的意思：安_num分组计算每组的_num的总数别名为n,再用n降序，再从结果的第二条数据开始取一条(下标从0开始)
			4、order by和group by 可以用1,2来指定你是要用结果集的第几列
			5、如果多表查询，两个表的相同字段名时必须用表别名，不然报错 ambiguous
			6、mysql 查看最大允许的包
				show VARIABLES like '%max_allowed_packet%';
		3、 mysql 的常用函数
			1、日期类型
				now()  //得到：2016-05-12 21:34:50当前系统时间，
			1、 group_concat(distinct search.KEYWORD separator ',') 实现交叉表
				select temp.phone 电话,temp.keyword 搜索关键词,group_concat(distinct monitor.KEYWORDS separator ',') 定制关键词 from
				(
				  select user.PHONE phone,login.LOGIN_ID loginId,group_concat(distinct search.KEYWORD separator ',') keyword,count(*) keywordNum from b2c_mall_staff_basic_info user
					join b2c_user_login_info login on login.userid=user.userid
					join bxkc_mysearchkeyword search on search.CRUSER=login.LOGIN_ID

				  where user.USERTYPE='02'
				  GROUP BY user.PHONE,login.LOGIN_ID
				) temp
				left join bxkc_monitor monitor on monitor.CRUSER=temp.loginId
				GROUP BY temp.phone
			1、查某个表的大小： select concat(round(sum(DATA_LENGTH/1024/1024),2),'M') from information_schema.tables where table_schema='bxkc' and table_name='sys_document_0';
			2、update bxkc_monitor set PROVINCES=CONCAT('全国,', PROVINCES) where 1=1;		// PROVINCES 前面拼接 '全国,'
			3、update bxkc_monitor SET PROVINCES=REPLACE(PROVINCES,'全国','') ;			// 把全国替换成'' 如 全国abc 变成 abc
	2、 sqlServer
		1、 sqlServer 启动关闭-还原备份-创建表案例
			1、启动关闭
			2、备份还原
			3、创建语句：
				create table hw_users(
					_user_id int identity (1,1) primary key,  --主键默认不能为空,自增长（从1开始步长为1）
					_dept_id int references hw_dept(_dept_id),  --外键列
					_user_name varchar(32) not null,  --用户名不能为空
					_password varchar(32) not null,  --密码名不能为空
					_sex varcahr(1)  default '男',  --人的性别默认男，要填只能在男女选一个(这里可以用枚举吗)
					_age int check(_age>0 and _age<255),  --年龄从0-255的整型数据。存储大小为 1 字节
					_hire_date datetime,  --入职时间
					_salary numeric(10,2)  --薪水最大99999999.99
				);
				insert into hw_users values(1,1,'hsp','mm','男',18,'2016-12-12 12:00:00',10000.00);

			4、查看数据表结构：sp_help table_name/sp_columns table_name
		2、 sqlServer 使用经验
			1、自增长的字段不能赋值，这一点区别别的数据库
			2、sqlserver游标
				declare @userId numeric(19,0) 
				declare mycursor cursor for select id from ss_users

				open mycursor
				fetch next from mycursor into @userId      
				while (@@fetch_status=0)  
				begin      
					insert into ss_user_role(user_id,role_id) values(@userId,2)
					fetch next from mycursor into @userId  
				end
				close mycursor  
				deallocate mycursor
		3、 sqlServer 的常用函数
			1、日期类型

	3、 oracle
		1、 oracle 启动关闭-还原备份-创建表案例
			1、oracle的启动：我不太会用dos命令（一个服务一个监听既可以）
				1、启动：
					net start oracle服务名称
					net start oracle客户端监听名称
				2、关闭：
					net stop oracle OracleServiceORCL(服务名称)
					net stop oracle OracleOraDb11g_home1TNSListener(客户端监听名称)

			2、oracle的备份还原：我不太会用dos命令
				1、逻辑备份还原：（记得最后不用分好）
					1、在控制台输入：exp 用户名/密码@数据库名 owner=用户名 file=导出文件路径 [full=y]  //可能要cd/d到oracle的bin目录要用他的工具exp.exe（导出有可能不成功因为有空表没有表空间）
					2、在控制台输入：imp 用户名/密码@数据库名 fromuser=导入文件的用户名 touser=导入到的用户名 file=导入文件路径 [full=y]  //可能要cd/d到oracle的bin目录要用他的工具imp.exe
					例子：exp 用户名/密码@数据库名 owner=用户名 file=导出文件路径 [full=y]
					例子：imp system/p2ssword@orcl fromuser=zsykzx touser=zsykzx file=C:\b\zsykzx201805021236.dmp

			3、oracle的创建数据表
				
				--3.1、先创建以后再给他两个角色吧：
					create user cjr identified by 123456;
					grant connect to cjr with admin option;
					grant resource to cjr with admin option;

				--3.2、创建用户数据表(varchar()/varchar2()是：非unicode码1中文=2字母=2char)《创建序列：从1开始，每次加1，最小值1，最大值不限，不循环，不缓存》
				create table User(
					id number primary key,  --主键默认不能为空,用序列
					deptId references Dept(id),  --外键列
					name varchar2(32) not null,  --用户名不能为空
					password varchar2(32) not null,  --密码不能为空
					sex varchar2(2)  default '男' check(_sex in ('男','女')),  --人的性别默认男，要填只能在男女选一个
					age number(3) check(age<300),  --人的年龄不能超过300
					salary number(8,2),  --薪水6位，小数2位
					grade number(2) default 1  --用户级别默认为1
					registerTime date default sysdate,  --注册日期+时间默认是系统时间
				);

				create table user(id number primary key,name varchar2(255),age number(3));

				create sequence hw_users_s
				start with 1
				increment by 1
				minvalue 1
				nomaxvalue
				nocycle
				nocache
				/

				insert into hw_users values(hw_users_s.nextval,1,'hsp','mm','男',18,10000.00,1,'2016-12-12 12:00:00');
			4、oracle查看数据的结构
				1、create table table_name/desc table_name(和mysql一样吗)
			5、游标
				DECLARE
				  CURSOR emp_cur IS SELECT * FROM bxkc.emp FOR UPDATE ;
				  emp_row emp_cur%ROWTYPE;
				BEGIN
				  OPEN emp_cur;
				  LOOP
					FETCH emp_cur INTO emp_row;
					IF emp_cur%NOTFOUND THEN
					  EXIT;
					ELSE
					  UPDATE bxkc.emp SET name='abc' WHERE CURRENT OF emp_cur;
					END IF;
				  END LOOP;
				  COMMIT;
				  CLOSE emp_cur;
				END;




