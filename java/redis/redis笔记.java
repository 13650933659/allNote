





讲师：周阳


1、redis是什么：Remote Dictionary Server(远程字典服务器)
	1、开源免费的，用C写编写的，是一个高性能的kv键值对的内存数据库
	2、官网：
		1、原始官网：Http:\\redis.io/
		2、中国官网：Http:\\www.redis.cn/
	3、特点
		1、支持持久化
		2、kv的值支持string、list、set、zset、hash，这一点就淘汰了memcache，因为他只支持string
		3、支持备份：主从复制，读写分离的
2、安装redis因为他是c编写的 ，所以要先装gcc(自己安装吧)，windows下安装请参考：http://www.runoob.com/redis/redis-install.html
	1、在/opt目录下解压：tar -zxvf redis-3.0.4.tar.gz
	2、得到redis-3.0.4目录执行make(需要有gcc才行)
		1、redis-3.0.4/redis.conf很重要，启动时需要用到，这份别动，拷贝一份到/usr/local/bin/目录自定义配置
	3、再使用make install(说是检查安装)
	4、安装完成后的默认安装目录是：/usr/local/bin/
		1、redis-benchmark：性能测试(在服务启动测试)
		2、redis-check-aof：修复有问题的AOF文件
		3、redis-check-dump：修复有问题的dump.rdb文件
		4、redis-cli：客户端入口
		5、redis-sentinel：redis集群是用到的哨兵支持
		6、redis-server：redis服务器启动命令
3、启动
	1、linux
		1、最好把/opt/redis-3.0.4/reids.conf文件拷贝一份来使用
		2、vim reids.conf ：将daemonize no改为yes，后台启动
		3、进入安装目录启动：redis-server redis.conf
		4、测试是否启动成功
			1、进入安装目录登录： redis-cli -p 6379
			2、ping(如果看到PONG证明OK)
	2、windows
		1、 配置redisHome的环境变量
		2、 redis-server C:\Software\OpenSource\Redis-x64-3.0.504\redis.windows.conf		// 然后不要关闭此窗口，一旦关闭redis也关了
		3、 redis-cli -h localhost -p 6379	// 登录
4、关闭
	1、单例关闭：shutdown -> exit
	2、多实例关闭(指定端口关闭没试过)：shutdown -> exit
	3、也可以使用ctrl+c退出，但是没关闭redis服务
5、使用：参考Http:\\redisdoc.com/
	1、杂项
		1、默认有16个库通过databases:16配置的，下标以0开始，切换到7号数据库：select 6
		2、查看数据库有多少个元素：dbsize
		3、清库： flushdb(清空当前库) flushall(清除16个库)
		4、可以设置密码：config set requirepass "123456"，登录之后需要使用 auth 123456验证之后才可以继续操作redis
		5、redis不像mongodb有集合的概念，redis直接使用库开工
	2、key的是使用案例
		1、keys *：列出所的key
		2、exists k1：是否存在k1
		3、move k1 1：把k1移动到1号库中
		4、del k1：删除k1
		5、expire k1 10：设置k1的过期时间为10秒
		6、ttl k1：查看k1还有多少秒过期，-1表示永不过期，-2表示已过期
		7、type k1：查看k1的数据类型
	3、五种数据类型的使用案例
		1、string
			1、set/get/del/append/strlen：自己看
			2、Incr/decr/incrby/decrby：一定要是数字才能加减
			3、setrange/getrange的使用
				1、set k1 abcdefg
				2、getrange k1 3 4：结果为de
				3、setrange k1 5 xxx：k1结果为abcdexxx
			4、setex k1 10 v1：设值并且设置好过期时间为10秒
			5、setnx k1 v1：如果不存在k1就设置，存在不设置(避免了覆盖)
			6、mset k1 v1 k2 v2：批量设值
			7、mget k1 k2：批量获取值
			8、msetnx k1 v1 k2 v2：批量设值，但是前提是k1k2都不存在
			9、getset k1 v1：先设值获取
		2、List再去看看
		3、Set再去看看
		4、Hash再去看看
		5、Zset再去看看
	4、redis-3.0.4/redis.conf配置文件的解析
		1、通用的
			1、include /a/redis2.conf：可以包含其他的配置文件
			2、daemonize：值为yes为后台运行
			3、pidfile：当redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件
			4、port：redis服务监听的端口默认是6379
			5、tcp-backlog：511默认值就好，什么三次握手
			6、timeout：超时时间默认就好
			7、bind：默认就好
			8、tcp-keepalive 60：60秒检查一次是否redis服务是否活着，0则不检查，建议设置为60
			9、loglevel：日志的级别默认notice就好
			10、logfile：日志文件路径默认""就好
			11、syslog-enabled：是否把日志输出到syslog中
			12、syslog-ident：指定syslog里的日志标志
			13、syslog-ident：指定syslog设备，值可以是USER或LOCAL0-LOCAL7
			14、databases 16：默认16个库就好
		2、snapshot(对应默认支持的rdb持久化技术)
			1、save：控制rdb持久化的：默认把内存的数据集快照15m改1次 5m改10次 1m改10000次就持久化到dump.rdb文件
				save 900 1
				save 300 10
				save 60 10000
			2、stop-writes-on-bgsave-error：设置成no，表示不在乎数据不一致或者有其他手段发现和控制
			3、rdbcompression：是否对数据队形压缩存储，默认yes，会有cpu的消耗
			4、rdbchecksum：是否启用数据检验，默认为yes，会有性能消耗
			5、dbfilename：rdb持久化的文件，默认为dump.rdb
			6、dir：默认./
		3、append only file(aof)
			1、appendonly：是否开启aof数据持久化支持，默认是no
			2、appendfilename：aof的文件名称，默认为appendonly.aof
			3、appendfsync：同步的策略
				1、always：立即同步，性能较差
				2、everysec：每秒同步一次，默认推荐
				3、no：不同步
			4、no-appendfsync-on-rewrite：重写时是否可以运用Appendfsync，用默认no即可，保证数据安全性。
			5、auto-aof-rewrite-min-size：设置重写的基准值
			6、auto-aof-rewrite-percentage：设置重写的基准值
		4、限制
			1、maxclients：最大客户端连接数默认10000
			2、maxmemory：最大内存
			3、maxmemory-policy：当超过最大内存时的移除策略
				（1）volatile-lru：使用LRU算法移除key，只对设置了过期时间的键
				（2）allkeys-lru：使用LRU算法移除key
				（3）volatile-random：在过期集合中移除随机的key，只对设置了过期时间的键
				（4）allkeys-random：移除随机的key
				（5）volatile-ttl：移除那些TTL值最小的key，即那些最近要过期的key
				（6）noeviction：默认不进行移除。针对写操作，只是返回错误信息
			4、maxmemory-samples：？？？
		5、redis.conf详细说明
			1. daemonize no Redis默认不是以守护进程的方式运行，可以通过该配置项修改，使用yes启用守护进程 
			2. pidfile /var/run/redis.pid 当Redis以守护进程方式运行时，Redis默认会把pid写入/var/run/redis.pid文件
			3. port 6379 指定Redis监听端口
			4. bind 127.0.0.1 绑定的主机地址  
			5. timeout 300 当客户端闲置多长时间后关闭连接，如果指定为0，表示关闭该功能 
			6. loglevel verbose 指定日志记录级别，Redis总共支持四个级别：debug、verbose、notice、warning，默认为verbose
			7. logfile stdout 日志记录方式，默认为标准输出，如果配置Redis为守护进程方式运行，而这里又配置为日志记录方式为标准输出，则日志将会发送给/dev/null
			8. databases 16 设置数据库的数量，默认数据库为0，可以使用SELECT <dbid>命令在连接上指定数据库id
			9. 指定在多长时间内，有多少次更新操作，就将数据同步到数据文件，可以多个条件配合
				  save <seconds> <changes>
				  Redis默认配置文件中提供了三个条件：
				  save 900 1
				  save 300 10
				  save 60 10000
				  分别表示900秒（15分钟）内有1个更改，300秒（5分钟）内有10个更改以及60秒内有10000个更改。
			10. rdbcompression yes 指定存储至本地数据库时是否压缩数据，默认为yes，Redis采用LZF压缩，如果为了节省CPU时间，可以关闭该选项，但会导致数据库文件变的巨大
			11. dbfilename dump.rdb 指定本地数据库文件名，默认值为dump.rdb
			12. dir ./ 指定本地数据库存放目录
			13. slaveof <masterip> <masterport> 设置当本机为slav服务时，设置master服务的IP地址及端口，在Redis启动时，它会自动从master进行数据同步
			14. masterauth <master-password> 当master服务设置了密码保护时，slave服务连接master的密码
			15. requirepass foobared 设置Redis连接密码，如果配置了连接密码，客户端在连接Redis时需要通过AUTH <password>命令提供密码，默认关闭
			16. maxclients 128 设置同一时间最大客户端连接数，默认无限制，Redis可以同时打开的客户端连接数为Redis进程可以打开的最大文件描述符数，如果设置 maxclients 0，表示不作限制。当客户端连接数到达限制时，Redis会关闭新的连接并向客户端返回max number of clients reached错误信息
			17. maxmemory <bytes> 指定Redis最大内存限制，Redis在启动时会把数据加载到内存中，达到最大内存后，Redis会先尝试清除已到期或即将到期的Key，当此方法处理 后，仍然到达最大内存设置，将无法再进行写入操作，但仍然可以进行读取操作。Redis新的vm机制，会把Key存放内存，Value会存放在swap区
			18. appendonly no 指定是否在每次更新操作后进行日志记录，Redis在默认情况下是异步的把数据写入磁盘，如果不开启，可能会在断电时导致一段时间内的数据丢失。因为 redis本身同步数据文件是按上面save条件来同步的，所以有的数据会在一段时间内只存在于内存中。默认为no
			19. appendfilename appendonly.aof 指定更新日志文件名，默认为appendonly.aof
			20. 指定更新日志条件，共有3个可选值： 
				  no：表示等操作系统进行数据缓存同步到磁盘（快） 
				  always：表示每次更新操作后手动调用fsync()将数据写到磁盘（慢，安全） 
				  everysec：表示每秒同步一次（折衷，默认值）
				  appendfsync everysec
			21. vm-enabled no 指定是否启用虚拟内存机制，默认值为no，简单的介绍一下，VM机制将数据分页存放，由Redis将访问量较少的页即冷数据swap到磁盘上，访问多的页面由磁盘自动换出到内存中（在后面的文章我会仔细分析Redis的VM机制）
			22. vm-swap-file /tmp/redis.swap 虚拟内存文件路径，默认值为/tmp/redis.swap，不可多个Redis实例共享
			23. vm-max-memory 0 将所有大于vm-max-memory的数据存入虚拟内存,无论vm-max-memory设置多小,所有索引数据都是内存存储的(Redis的索引数据 就是keys),也就是说,当vm-max-memory设置为0的时候,其实是所有value都存在于磁盘。默认值为0
			24. vm-page-size 32 Redis swap文件分成了很多的page，一个对象可以保存在多个page上面，但一个page上不能被多个对象共享，vm-page-size是要根据存储的 数据大小来设定的，作者建议如果存储很多小对象，page大小最好设置为32或者64bytes；如果存储很大大对象，则可以使用更大的page，如果不 确定，就使用默认值
			25. vm-pages 134217728 设置swap文件中的page数量，由于页表（一种表示页面空闲或使用的bitmap）是在放在内存中的，，在磁盘上每8个pages将消耗1byte的内存。
			26. vm-max-threads 4 设置访问swap文件的线程数,最好不要超过机器的核数,如果设置为0,那么所有对swap文件的操作都是串行的，可能会造成比较长时间的延迟。默认值为4
			27. glueoutputbuf yes 设置在向客户端应答时，是否把较小的包合并为一个包发送，默认为开启
			28. 指定在超过一定的数量或者最大的元素超过某一临界值时，采用一种特殊的哈希算法
				  hash-max-zipmap-entries 64
				  hash-max-zipmap-value 512
			29. activerehashing yes 指定是否激活重置哈希，默认为开启（后面在介绍Redis的哈希算法时具体介绍）
			30. include /path/to/local.conf 指定包含其它的配置文件，可以在同一主机上多个Redis实例之间使用同一份配置文件，而同时各个实例又拥有自己的特定配置文件
	5、持久化技术
		1、rdb(redis database)：在指定的时间间隔把内存的数据集快照写入磁盘，写的操作是fork出来的子进程干的，这样性能高，因为会把数据在复制一份内存翻了一倍
			1、dump.rdb是默认的数据库文件
			2、同步数据的时间间隔参考，上面的save配置
			3、手动持久化
				1、save：只管保存，其它不管，全部阻塞
				2、bgsave：异步的操作。可以通过lastsave命令获取最后一次成功执行快照的时间
				3、flushall/flushdb，但里面是空的，无意义
			4、数据的恢复：只要有./dump.rdb启动redis时即可自动恢复
			5、停止rdb支持：redis-cli config set save ""
			6、优劣势：性能高，适合大规模数据恢复，但是有可能会丢失最后一次的数据，而且内存的数据被克隆了一份，内存大了一倍
		2、aof(Append Only File)：以日志的形式记录每一个写的操作



待完善的
	1、五种数据类型的使用案例
	2、aof的持久化技术
	3、master\slave(主从复制，读写分离，哨兵模式)
	4、redis的事物支持
	5、redis的发布订阅
	6、java使用jedis操作redis



moose-enterprise-report-api
