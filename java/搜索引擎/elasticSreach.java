










1、什么是ElasticSearch
	1、基于restfull风格，实时的，分布式，高可用的，使用java开发的搜索服务器，底层是lucene
	2、官网：https://www.elastic.co/
	3、概念
		索引库(数据库)
		类型(表)
		文档(数据行)
		字段(数据列)
		分片
		集群
	4、ES VS solr的比较 ES效率高于solr50倍
		–接口
			•类似webservice的接口
			•REST风格的访问接口
		–分布式存储
			•solrCloud solr4.x才支持
			•es是为分布式而生的
		–支持的格式
			•solr xml json
			•es json
		–近实时搜索
2、ES安装
	1、系统准备
		1、操作系统：centos6.4 64bit
		2、jdk8u60-linux-x64.tar.gz 和 elasticsearch-1.7.1.tar.gz
	2、安装
		1、安装jdk请参考linux-contos6.8.java
		2、安装elasticsearch： tar -zxvf elasticsearch-6.4.2.tar.gz
	3、ESHhome目录的介绍
		/bin	运行ElasticSearch实例和管理插件的一些脚本
		/config	配置文件，包含elasticsearch.yml
		/data	ElasticSearch使用的库（这我没有）
		/logs	日志文件夹
		/plugins	已安装的插件
	4、ES配置：config/elasticsearch.yml    (注意要顶格写，冒号后面要加一个空格)
		1、cluster.name: chenkl   (同一集群要一样)
		2、node.name： node-1  (同一集群要不一样)
		3、network.host: 192.168.216.129   这里不能写127.0.0.1
		4、防止脑裂的配置(集群时最好配上)
			discovery.zen.ping.multicast.enabled: false
			discovery.zen.ping_timeout: 120s
			client.transport.ping_timeout: 60s
			discovery.zen.ping.unicast.hosts: ["192.168.57.4","192.168.57.5", "192.168.57.6"]

3、ES卸载：直接删除 elasticsearch-6.4.2目录即可
4、ES启动(不要使用root用户)
	1、命令： /opt/elasticsearch-6.4.2/bin/elasticsearch // 如果想要后台启动使用-d
	2、启动失败的解决方案(这个应该是高版本的一些限制，参考：https://www.cnblogs.com/configure/p/7479988.html)
		1、切换到root用户修改 vim /etc/security/limits.conf	// 在末尾加上一下两行
			zs hard nofile 65536
			zs soft nofile 65536
		2、设置/etc/security/limits.conf	// 线程属性限制
			zs soft nproc 5000
			zs hard nproc 5000
			重启服务器生效
		3、设置/etc/sysctl.conf添加如下配置 vm.max_map_count=655360
			并执行：sysctl -p
		4、vim ESHome/config/elasticsearch.yml 加入下列配置
			# ----------------------------------- Memory -----------------------------------
			bootstrap.memory_lock: false
			bootstrap.system_call_filter: false
		5、使用ulimit -a // 查看当前用户的限制
5、访问
	1、谷歌浏览器输入：http://192.168.216.129:9200/
6、安装插件
	1、Head：相当于Elasticsearch的客户端	// 高版本不行了
		1、低版本(es5以前)
			1、cd elasticsearch-6.4.2/bin 使用./plugin install mobz/elasticsearch-head
			2、使用浏览器访问：http//:192.168.216.129/_plugin/head/
		2、高版本需要安装nodejs、npm
			yum -y install nodejs npm	// 安装nodejs和npm
			yum -y install git			// 如果没有安装git，还需要先安装git：
			git clone https://github.com/mobz/elasticsearch-head.git	// git下载完成插件，会得到elasticsearch-head目录
			cd elasticsearch-head/ 执行npm install	// 安装有点久慢慢等
			// 配置
				vim ESHome/config/elasticsearch.yml加入配置:
					http.cors.enabled: true
					http.cors.allow-origin: "*"
				vim elasticsearch-head/Gruntfile.js
					在port: 9100上面加上 hostname: 本机ip
				vim elasticsearch-head/_site/app.js
					this.base_uri = this.config.base_uri || this.prefs.get("app-base_uri") || "http://localhost:9200"; // 把localhost改为192.168.216.129
	2、Bigdesk插件：是Elasticsearch的一个集群监控工具，他可以来查看各种状态，如cpu、内存使用、索引数据、搜索情况、http连接数等
		1、cd elasticsearch-6.4.2/bin 使用./plugin install lukas-vlcek/bigdesk/2.5.0
		2、使用浏览器访问：http//:192.168.216.129/_plugin/bigdesk/
	3、Marvel插件是集head和bigdesk有点于一身的插件，但是他收费的
		1、cd elasticsearch-6.4.2/bin 使用./plugin install elasticsearch/marvel/latest
		2、使用浏览器访问：http//:192.168.216.129/_plugin/marvel/
	4、解压安装kibana
		1、tar -zxvf kibana-4.4.1-linux-x64.tar.gz
		2、再去KibanaHome/congfig目录下的kibana.yml中修改elasticsearch.url=ES的ip:9200
		3、安装插件
			1、ESHome/bin/plugin install license			// 在ES安装licence插件
			2、ESHome/bin/plugin install marvel-agent		// 在ES安装marvel-agent插件
				install	// 安装插件
				remove	// 卸载插件
				list	// 查看当前已安装的插件
			3、KibanaHome/bin/kibana plugin --install elasticsearch/marvel/latest	// 在Kibana安装marvel插件
			4、启动ES和kibana
				1、ESHome/bin/elasticsearch
				2、KibanaHome/bin/kibana
		4、谷歌浏览器访问kibana：http://192.168.216.129:5601
7、操作
	1、使用curl命令集合(简单的理解就是命令行对ES的操作)
		1、restFull风格的说明：PUT是幂等方法(所谓幂等是指不管进行多少次操作，结果都一样)，POST不是。所以PUT用于更新、POST用于新增比较合适
			HEAD=头信息 
			GET=查 
			POST=增		// 这个你带id也是更新
			PUT=改		// 他也可以做增，但是如果有对应的id会更新，没有才新增
			DELETE=删
		2、参数描述
			-X 指定http请求的方法
			-d 指定要传输的数据
		3、案例演示，索引库名称必须要全部小写，不能以下划线开头，也不能包含逗号
			1、新增
				1、创建索引库bjsxt: curl -H 'Content-Type: application/json' -XPUT 'http://192.168.216.129:9200/bd' -d '{setting:{number_of_shards:3,number_of_replicas:2},mappings:{doc:{dynamic:"strict",properties:{name:{type:"string"},nicknams:{type:"string"}}}}}'	// PUT和POST都可以记得大写，返回{"acknowledged": true}就说明成功了
					1、setting：shards数量以及replicas数量，之后shards不可改，replicas可以改
					2、mappings：就像约束一样关系型数据库的scheme，但是这里好像没有约束，也是随便可以存入数据，不过这样也可以理解，他本来就是NoSql
				2、创建索引bjsxt.employee._id=1:  curl -XPOST 'http://192.168.216.129:9200/organizations/Organization/1' -d '{name:"比地1",nicknames:"比地1"}'	// 如果不带1，ES自动自动分配一个_id，但是只能使用POST请求
					curl -h 'Content-Type: application/json' -XPOST 'http://192.168.216.129:9200/organizations/Organization//1' -d '{name:"比地1",nicknames:"比地1"}'
					curl -XPUT 'http://192.168.216.129:9200/bd'
					// 以上的命令不能使用了
			2、查询：
				1、普通语言
				curl -XGET 'http://192.168.216.129:9200/bd/org/1?pretty'
					curl -XGET 'http://192.168.216.129:9200/bjsxt/employee/1?pretty'					// pretty是说明要以json格式展现
					curl -XGET 'http://192.168.216.129:9200/bjsxt/employee/_search?pretty'				// 查询全部，你可以再返回的hits 中发现我们录入的文档。搜索会默认返回最前的10个数值
					curl -XGET 'http://192.168.216.129:9200/bjsxt/employee/_search?q=name:zs'			// 根据条件进行查询
					curl -XGET 'http://192.168.216.129:9200/bjsxt/employee/1?_source=name,age'			// _source只显示的字段，但是不能和pretty一起使用，有点奇怪
					curl -i 'http://192.168.216.129:9200/bjsxt/emp/1?pretty'							// 这样可以看到更多头信息
				2、特定语言
					curl -XGET 'http://192.168.216.129:9200/bjsxt/employee/_search' -d '{query:{match:{name:"zs"}}}'			// 根据条件进行查询
					curl -XGET 'http://192.168.216.129:9200/_mget?pretty' -d '{"docs":[{"_index":"bjsxt","_type":"employee","_id":2,"_source":"name"},{"_index":"website","_type":"blog","_id":2}]}'	// 混合查询，也可以在url加上默认的索引库bjsxt/employee，这样里边没有指定的_index和_type就使用这个默认的
					curl -XGET 'http://192.168.216.129:9200/bjsxt/employee/_mget?pretty' -d '{ids:["1","2"]}'	// 使用id的in
			curl -XGET 'http://localhost:9200/organizations/Organization/1?pretty'
			
			3、修改：
				1、修改原理
					ES可以使用PUT或者POST对文档进行更新，如果指定ID的文档已经存在，则执行更新操作
						•注意:执行更新操作的时候
							–ES首先将旧的文档标记为删除状态
							–然后添加新的文档
							–旧的文档不会立即消失，但是你也无法访问
							–ES会在你继续添加更多数据的时候在后台清理已经标记为删除状态的文档
				2、案例演示
					curl -XPUT 'http://192.168.216.129:9200/bjsxt/employee/3' -d '{name:"zs",age:25}'										// 有就修改没有新增，带了?op_type=create或者?_create参数还是一样的
					curl -XPOST 'http://192.168.216.129:9200/bjsxt/employee/1/_update' -d '{"doc":{"city":"beijing","car":"BMW",a:"adf"}}'	// 新增字段，注意：使用POST而且要带上_update不然会覆盖整条文档
			4、删除：删除一个文档也不会立即生效，它只是被标记成已删除。ES将会在你之后添加更多索引的时候才会在后台进行删除内容的清理
				curl -XDELETE 'http://192.168.216.129:9200/bjsxt/employee/1'											// 删除_id为1的文档，如果想删除，多种索引库：bjsxt,index，多种type就：emp,user 所有index和所有type使用_all
				curl -XDELETE 'http://192.168.216.129:9200/bjsxt/employee/_query?q=name:zs'								// 根据条件进行查询，这样好像没删掉有空再去看
				curl -XDELETE 'http://192.168.216.129:9200/bjsxt/employee/_query' -d '{query:{term:{name:"zs"}}}' 		// 根据条件进行查询，这样好像没删掉有空再去看
			5、Elasticsearch的批量操作bulk有空再去看
			6、ES的版本控制，他是乐观锁的原理
				curl -XPUT 'http://192.168.216.129:9200/bjsxt/employee/20?version=10&version_type=external' -d '{name: "laoxiao"}' // 这个是使用外部版本号，注意：此处url前后的引号不能省略，否则执行的时候会报错
		
17037208

7、集群
	1、只要是统一网段他会自动发现(10->11)

8、ik中文分词器
	1、在ES中加入ik中文分词器
		1、创建ESHome/plugins/ik目录，然后把编译好的ik分词器的elasticsearch-analysis-ik-1.8.0.zip包放进来，然后使用unzip解压他，得到一些jar包和配置文件
		2、使用scp命令把ik目录复制到从节点的主机
	2、javaAPI
		1、引入ES的依赖	// 版本要和安装的ES是一样的
			<dependency>
				<groupId>org.elasticsearch</groupId>
				<artifactId>elasticsearch</artifactId>
				<version>2.2.0</version>
			</dependency>






1、获取一条的
	GetResponse response = client.prepareGet("bjsxt", "emp", "1").execute().actionGet();
	GetResponse response = client.prepareGet("bjsxt", "emp", "1").get();
2、添加获取一条
	//        String name = "广东曦达工程咨询公司";
	//        IndexResponse response = client.prepareIndex("bd", "org", "3")
	//                .setSource(jsonBuilder()
	//                        .startObject()
	//                        .field("name", name)
	//                        .field("nicknames", name)
	//                        .endObject()
	//                ).get();


1、java操作es
	java操作使用 http://47.97.210.202:9200/ 或者 9300端口去访问

	1、依赖
		<!-- ES的依赖 -->
		<dependency>
			<groupId>org.elasticsearch.client</groupId>
			<artifactId>transport</artifactId>
			<version>${elasticsearch.version}</version>
		</dependency>
		<dependency>
			<groupId>org.elasticsearch</groupId>
			<artifactId>elasticsearch</artifactId>
			<version>${elasticsearch.version}=6.4.1</version>
		</dependency>
	2、java代码可以参考 要素提取的项目和bxkc的项目
		1、复杂查询
			BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
			MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("name", keyword);	// 短语匹配
			TermsQueryBuilder termsQueryBuilderCitys = QueryBuilders.termsQuery("city.keyword", citys);			// 全字匹配
			boolQueryBuilder.must(matchPhraseQueryBuilder);								// 相当于 and
			boolQueryBuilderArea.should(boolQueryBuilderProvinceAndCity);				// 相当于 or

2、访问使用 http://47.97.210.202:9100/
	1、这是短语型的不分词的
		{"query":{"multi_match":{"query":"比地","type":"phrase","slop":0,"fields":["nicknames"],"max_expansions":1}}}
	2、 正则匹配
		{
		"query": {
			"bool": {
				"must": [
					{"prefix": {"name.keyword": "比地"}}
				]
			}
		},
		"from": 0,
		"size": 10
		}

		也可以把 {"prefix": {"name.keyword": "比地"}} 
		替换成   {"regexp": {"name.keyword": "比地.*"}}









