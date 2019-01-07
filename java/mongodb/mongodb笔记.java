



1、mongodb是什么
	1、是由c++编写的文档形的非关系型数据库，以js为引擎，数据文件存储格式为bson(json的二进制格式)，他的collection就行表一样
2、安装mongodb数据库
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
6、使用
	1、DDL语法
		1、show dbs 查看当前的数据库
		2、use databaseName 选库如果不存在则创建
		3、show tables/collections 查看当前库下的collection
		4、db.createCollection("stu");创建集合，如果创建固定集合加上第二个参数{capped:true,size:100000,max:100}  
		5、db.collectionName.drop() 删除collection
		6、db.dropDatabase();删除当前使用的库
		7、db 查看当前库
	2、CRUD语法
		1、增：db.collectionName.insert({age:78,name:zs});也可以多个[{},{}]，_id自动赋值，也可以手动指定
		2、删：db.collectionName.remove({name:"zs"});默认会删除所有name为zs的文档，如果只想删除一条可以加第二个参数值为true
		3、改：db.collectionName.update(查询表达式,新值,选项)
			1、db.stu.update({name:"zs"},{name:"ls"});会把zs的文档变成{name:"ls"}这个慎用
			2、db.stu.update({name:"zs"},{$set{name:"ls"}});把zs改为ls
				$unset 删除某个列
				$rename 重命名某个列
				$inc 增长某个列
				$setOnInsert 当第三个参数upsert为true时,并且发生了insert操作时,可以补充的字段
			3、db.stu.update({name:"zs"},{$set:{name:"ls"}},{upsert:true});如有zs则更新，如果没有则插入
			4、db.stu.update({name:"zs"},{$set:{name:"ls"}},{multi:true});如果有多行zs一并改了
		4、查：find, findOne，语法：db.stu.find(表达式,{列1:1,列2:1,_id:0});
			1、db.stu.find();全查
			2、db.stu.find({name:"zs"},{sex:1,age:1,_id:0});查zs的age和sex
			3、db.stu.find().skip(9000).limit(10);使用skip和limit分页
		5、查询表达式
			1、最简单的：{filed:v} 
			2、$ne(不等于)：{field:{$nq:v}}
			3、$nin(not in)：{field:{$nin(v1,v2)}}
			4、$all(用于数组)：{field:{$all:[v1,v2..]}}
			5、$exists(不存在field列的)：{field:{$exists:1}}
			6、$nor(所有条件不满足返回true)：{$nor,[条件1,条件2]}
			7、也可以直接写正则表达式：db.stu.find({name:/zs.*/},{name:1});
			8、$where(bson->json再来比效率低)：db.goods.find({$where:'this.id != 3 && this.id != 11'});


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
		2、索引的常用命令
			1、db.stu.getIndexes();查看stu的所有索引
			2、db.collection.ensureIndex({field:1});创建普通索引1升序，-1降序，如果一下创建多个索引使用逗号分隔
				1、唯一索引{unique:true}值不能重复
				2、稀疏索引{sparse:true}如果没有此字段，则此文档没有索引
				3、db.stu.ensureIndex({file:"hashed"});创建hash索引2.4新增
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