



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
		url  varchar(2000) NOT NULL COMMENT '插件文件存储的url'
	) comment '插件信息表' 
	engine=InnoDB collate=utf8_bin;
	 // ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;	先用 上面的，有空再去研究这种
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




// 只需要 docid 即可

insert into sys_extraction_temp(docid) select docid from sys_document_0 limit 0,100;



