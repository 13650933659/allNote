


看完3
1、mongodb是什么
	1、是由c++编写的文档形的非关系型数据库，以js为引擎，数据文件存储格式为bson(json的二进制格式)，他的collection就行表一样
	2、他的三大概念：数据库、集合、文档
2、安装mongodb数据库
	1、windows安装(安装版安装版一直下一步即可，解压版的解压即可)
		1、windows版本的下载地址(偶数为稳定版本，奇数为开发版本)：https://www.mongodb.org/dl/win32/
		2、配置系统环境变量 MONGODB_HOME=C:\Software\OpenSource\mongodb  PATH=%MONGODB_HOME%\bin
		3、在docs窗口输入：mongod 如果看到信息则说明配置成功，如果C:/data/db 就是直接启动mongodb了(因为他的数据库目录默认是C:/data/db)
		4、我们也可以做成windows的一个服务(但是我不做也可以，启动时就使用 --dbpath等参数指定)
			1、在mongo安装目录的server下的3.X目录下创建mongod.cfg文件添加如下内容
				systemLog:
					destination:file
					path:c:\data\log\mongod.log
				storage:
					dbpath:c:\data\db
			2、以管理员默认打开docs窗口输入一下命令
				sc.exe create MongoDB binPath= "\"mongod的bin目录\mongod.exe\" --service --config=\"mongo的安装目录\mongod.cfg\"" DisplayName= "MongoDB" start= "auto"
			3、启动mongodb服务，如果启动失败，证明上边的操作有误，在控制台输入 sc delete MongoDB 删除之前配置的服务，然后从第一步再来一次
	2、linux安装
		1、使用wget命令下载，或者通过其他方式得到安装包
		2、解压：tar zxvf mongodb-linux-x86_64-4.0.3.tgz
		3、解压得到 mongodb-linux-x86_64-4.0.3以后会改名为mongodb
		4、mongodb/bin目录的详解
			1、bsondump：    导出bson格式的数据
			2、mongo:        客户端登录(相当于mysql.exe)
			3、mongod:       服务端(相当于mysqld.exe)
			4、mongos：      路由器(分片会用到)
			
			5、mongodump：   整体数据库导出(二进制格式，相当于mysqldump)
			6、mongorestore：数据库整体导入
			7、mongoexport： 导出易识别的json文档或者csv文档
			8、mongoimport： 导入
3、启动mongodb数据库
	1、rm -rf /usr/local/mongodb/                                                           // 删除mongodb
	2、mv mongodb-linux-x86_64-4.0.3 /usr/local/mongodb                                     // 把mongodb-linux-x86_64-4.0.3移到mongodb
	3、mkdir -p /home/m17 /home/mlog                                                        // 在home创建两个目录
	4、./mongod --dbpath /home/m17/ --logpath /home/mlog/m17.log --port 27017 --fork	    // 启动mongodb
		--dbpath 数据存储目录
		--logpath 日志存储目录
		--port 运行端口(默认27017)
		--replSet 所属的副本集
		--fork 后台进程运行
		--auth 启用权限验证
		--smallfiles 最小的空间启动
4、连接mongodb数据库：./mongo --host localhost:27017  // 连接到mongo无需密码
5、关闭mongodb数据库：./mongod --shutdown --dbpath=/home/m17，也可以登录mongodb的admin库使用db.shutdownServer()关闭服务
6、使用(客户端自己安装)
	1、DDL语法
		1、show dbs 查看当前的数据库
		2、use databaseName 选库如果不存在则创建（在第一次插入数据时）
		3、show tables/collections 查看当前库下的collection
		4、db.createCollection("stu");创建集合，如果创建固定集合加上第二个参数{capped:true,size:100000,max:100}  
		5、db.collectionName.drop() 删除collection
		6、db.dropDatabase();删除当前使用的库
		7、db 查看当前库
		8、db.version();查看当前数据库版本
	2、CRUD语法(其他语法以后有空再去看教程)
		1、增：db.collectionName.insert({age:78,name:'zs'});也可以多个[{},{}]，_id自动赋值，也可以手动指定，这里也会隐形创建数据库
			1、insertOne()		// 只能插入一条 3.2+
			2、insertMany()		// 只能插入多条 3.2+
			3、批量插入，兼容js
				var arr = [];
				for(var i=1; i<=10; i++){arr.push({name:'zs'+i})}
				db.stu.insert(arr)
		2、删：db.collectionName.remove({name:"zs"});默认会删除所有name为zs的文档，
			1、db.stu.remove({},true);	// 如果只想删除一条可以加第二个参数值为true
		3、改：db.collectionName.update(查询表达式,新值,选项)
			1、db.stu.update({name:"zs"},{name:"ls"});会把zs的文档变成{name:"ls"}这个慎用
			2、db.stu.update({name:"zs"},{$set:{name:"ls"}});把zs改为ls
				1、第一个参数使用说明
					$unset 删除某个列			// db.user.update({name:'zs'},{$unset:{age:null}}); 值会删除zs的这个字段，其他记录不会影响
					$rename 重命名某个列
					$inc 增长某个列
					$setOnInsert 当第三个参数upsert为true时,并且发生了insert操作时,可以补充的字段
					$push	// 向数组末尾添加一个元素：{$push:{'a'}}，如果要去重加入使用$addToSet
				2、第二个参数使用说明
					db.stu.update({name:"zs"},{$set:{name:"ls"}},{upsert:true});如有zs则更新，如果没有则插入
					db.stu.update({name:"zs"},{$set:{name:"ls"}},{multi:true});如果有多行zs一并改了
		4、查：find, findOne，语法：db.stu.find(表达式,{列1:1,列2:1,_id:0});
			1、db.stu.find();全查
			2、db.stu.find({name:"zs"},{sex:1,age:1,_id:0});查zs的age和sex(投影：1显示 0不显示 _id是默认为1)
			3、db.stu.find().skip(9000).limit(10);使用skip和limit分页
		5、查询表达式
			1、最简单的：{f:v}	// f如果是数据，则只要f包含一个v即可
			1、{'f.name':'a'}	// 如果是内嵌的对象，则一定要使用引号包起来
			1、var stu_id = db.stu.findOne()._id; db.order.find({stu_id:stu_id})	// 可以结合js语法查询
			2、$ne(不等于)：{field:{$ne:v}}
				db.enterprise_profile.find({tycUpdateTime: {$ne:null}}).count();		// tycUpdateTime != null
			3、$nin(not in)：{field:{$nin: [v1,v2]}}
			4、$all(用于数组)：{field:{$all:[v1,v2..]}}
			5、$exists(存不存在field列的)：
				1、 db.stu.find({name:{$exists:0}})		// 不存在name字段的，但是不包括 name=null的，如果需要包括则使用 db.stu.find({"check":null})（如果需要查存在的，把0改成1）
				2、 db.stu.find({name:null})			// name为null，包括了不存在此字段的，但不包括 name=''
			6、$nor(所有条件不满足返回true)：{$nor: [条件1,条件2]}
			7、{$or: [ { status: "A" }, { qty: { $lt: 30 } } ] }	// or
			7、也可以直接写正则表达式：db.stu.find({name:/zs.*/},{name:1});
			8、$where(bson->json再来比效率低)：db.goods.find({$where:'this.id != 3 && this.id != 11'});
			9、使用内置的_id查询：db.stu.find({_id: ObjectId("5bfff2548fbbcf4d7c482684")});
			10、时间类型
				Date()			// Mon Sep 07 2020 16:57:27 GMT+0800 		显示当前的时间
				new Date()		// ISODate("2020-09-07T08:57:28.429Z")		构建一个格林尼治时间   可以看到正好和Date()相差8小时，我们是+8时区，也就是时差相差8，所以+8小时就是系统当前时间
				ISODate()		// ISODate("2020-09-07T08:57:28.864Z")		也是格林尼治时间 和 new Date() 一样
		6、聚合函数
			1、db.stu.find().count();	// 查询结果条数
			1、db.stu.find().sort({_id:1});	// 排序1为升序，-1是降序


	3、游标
		1、用while来循环游标
			> var mycursor = db.stu.find();
			> while(mycursor.hasNext()) {
			... printjson(mycursor.next());
			... };
		2、cursor.forEach(回调函数);
			> var f = function(obj) {print(obj.name)}
			> var cursor = db.goods.find();
			> cursor.forEach(f);
		3、使用toArray
			1、printjson(cursor.toArray());查看所有
			1、printjson(cursor.toArray()[2]);查看第二个

	4、索引：提高查询速度,降低写入速度,自己权衡，默认是用btree来组织索引文件
		1、查看查询计划
			db.find(query).explain();
			"cursor" : "BasicCursor", ----说明没有索引发挥作用，如果是BtreeCurso则说明发挥作用了
			"nscannedObjects" : 1000 ---理论上要扫描多少行
		2、索引的常用命令	// 参考 https://www.cnblogs.com/a-du/p/7805951.html
			1、db.stu.getIndexes();查看stu的所有索引
			2、db.collection.ensureIndex({field:1});创建普通索引1升序，-1降序，如果一下创建多个索引使用逗号分隔
				1、唯一索引{unique:true}值不能重复
					db.user.ensureIndex({"name":1},{"unique":true})
				2、稀疏索引{sparse:true}如果没有此字段，则此文档没有索引
				3、db.stu.ensurendex({file:"hashed"});创建hash索引2.4新增
			3、db.collection.dropIndex({filed:1/-1});不带条件则删除所有索引不包括_id
			4、重建索引(索引多次修改会出现碎片)使用修复：db.stu.reIndex();
	5、备份和恢复=针对库(导出导入=针对集合)
		1、备份：mongodump -h dbhost -d dbname -o dbdirectory
			-h：MongoDB所在服务器地址，例如：127.0.0.1，当然也可以指定端口号：127.0.0.1:27017
			-d：需要备份的数据库实例，例如：test
			-o：备份的数据存放位置，例如：c:\data\dump
		2、还原：mongorestore -h dbhost -d dbname -directoryperdb dbdirectory
			-h：MongoDB所在服务器地址
			-d：需要恢复的数据库实例，例如：test，当然这个名称也可以和备份时候的不一样，比如test2
			-directoryperdb：备份数据所在位置，例如：c:\data\dump\test
		3、导出：mongoexport -h dbhost -d dbname -c collectionName -o output
			-h  数据库地址
			-d 指明使用的库
			-c 指明要导出的集合
			-o 指明要导出的文件名
		4、导入：mongoimport -h dbhost -d dbname -c collectionname output 
			-h 数据库地址
			-d 指明使用的库
			-c 指明要导入的集合
	6、集群(垂直扩张)
		1、主从复制：主节点写的操作会同步到从节点
			启动主节点：mongod --dbpath /master --port 10000 --master
			启动从节点：mongod --dbpath /slave --port 10001 --slave --source localhost:10000
		2、副本集(推荐)：主节点挂了，备份的副机顶上
			1、启动3个实例,且声明实例属于某复制集
				./mongod --dbpath /data/node1 --logpath /data/node1/mongo17.log --port 27017 --smallfiles --replSet rsa --fork
				./mongod --dbpath /data/node2 --logpath /data/node2/mongo18.log --port 27018 --smallfiles --replSet rsa --fork
				./mongod --dbpath /data/node3 --logpath /data/node3/mongo19.log --port 27019 --smallfiles --replSet rsa --fork
			2、配置并初始化
				rsconf = {_id:'rsa', members:[
					{_id:1,host:'localhost:27017',"priority":3},
					{_id:2,host:'localhost:27018',"priority":2},
					{_id:3,host:'localhost:27019',"priority":1}]};
				rs.initiate(rsconf);
			3、后续的添加删除节点
				rs.add('192.168.1.201:27018');
				rs.remove('192.168.1.201:27019');
			4、查看集群的转态：rs.status();
			5、副本机需要查看数据要：rs.slaveOk();
			6、至此副本集群已经搭建好了
	7、分片(水平扩展-有空再去测试)
		1、创建目录：mkdir -p /data/config_node /data/test_shard1 /data/test_shard2
		2、开启config服务器2222端口：./mongod --dbpath /data/config_node --port 2222
		3、开启mongos路由服务器3333端口(3.4以下版本才可以，以上的就要使用分片了)：./mongos --port 3333 --configdb=localhost:2222
		4、启动mongod服务器(分2片)
			mongod --dbpath /data/test_shard1 --port 4444
			mongod --dbpath /data/test_shard2 --port 5555 
		5、client直接跟mongos打交道
			1、进入mongos/admin执行添加片：db.runCommand({"addshard":"localhost:4444",allowLocal:true})
			2、进入mongos/admin执行添加片：db.runCommand({"addshard":"localhost:5555",allowLocal:true})
			3、进入mongos/admin执行开启分片功能：db.runCommand({"enablesharding":"test"})
			4、进入mongos/admin执行指定stu.name为片键：db.runCommand({"shardcollection":"test.stu","key":"name"})
		6、使用db.printShardingStatus()查看分片的状态
	8、安全认证：MongoDB会将普通的数据作为admin数据库处理，其他库的只能自己库的用户才能处理。admin数据库中的用户被视为超级用户(即管理员)。
		1、在对应的库加入用户：db.addUser("root","root");
			db.createUser({user:"root",pwd:"root",roles[{role:"root",db:"admin"}]})
		2、启动加参数：--auth
		3、db.auth("root","root");在相应的数据库中认证
	9、java操作mongodb，看项目演示

	db.auth("bxkc","bidizhaobiaowang2017")


	




