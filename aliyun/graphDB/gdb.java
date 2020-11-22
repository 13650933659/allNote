
全部白名单：0.0.0.0/0
gremlin文档 http://tinkerpop-gremlin.cn
http://tinkerpop.apache.org/docs/3.4.6/reference/

1、 gdb(graph database)
	是一种图模型、用于处理高度连接数据查询与存储的实时、可靠的在线数据库服务。它支持Apache TinkerPop Gremlin查询语言，可以帮您快速构建基于高度连接的数据集的应用程序。
	GDB非常适合社交网络、欺诈检测、推荐引擎、实时图谱、网络/IT运营这类高度互连数据集的场景。

2、 产品系列
	1、基础版(单节点)
	2、高可用版(集群)
3、产品定价
	1、性能计费
		1、 2核16GB 
			1、国内站点 
				1、450/月
				2、0.94/时
		2、 4核32GB 
			1、国内站点 
				1、880/月
				2、1.84/时

	2、存储计费
		1、SSD云盘
			1、国内站点 
				1、1元/G/月
				2、0.0014元/G/月
		2、ESSD云盘
			1、国内站点 
				1、1元/G/月
				2、0.0021元/G/月

	3、公网流量
		1、流入免费
		2、流出 优惠活动中，流量全免 （节省0.8元/GB）

4、使用限制
	1、数据库备份
		1、仅限通过图数据库控制台进行实例备份。
		2、备份最多可以保留60天
	2、数据库恢复
		1、仅限通过图数据库控制台进行数据库恢复。
		2、公测期间，只支持实例覆盖性恢复，具体操作请参见备份与恢复






1、创建 gdb 实例
	1、 2020年3月18日 20:35:37 创建的
	2、我创建了一个账号 root=a=123456





1、sdk参考
	1、 gremlin console(liunx/windows)
		1、  Gremlin控制台需要Java 8 先按照java8 : sudo yum install java-1.8.0-devel
		2、 从Apache Tinkerpop官方网站下载最新版本Gremlin控制台 : wget https://archive.apache.org/dist/tinkerpop/3.4.2/apache-tinkerpop-gremlin-console-3.4.2-bin.zip
		3、 解压并进入apache-tinkerpop-gremlin-console-3.4.2目录
			unzip apache-tinkerpop-gremlin-console-3.4.2-bin.zip
			cd apache-tinkerpop-gremlin-console-3.4.2
		4、 创建conf/gdb-remote.yaml文件，该文件为Gremlin控制台与GDB图数据库建立连接的配置文件，并且填写如下代码
			hosts: [ ${your_gdb_endpoint} ]
			port: 8182
			username: ${username}
			password: ${password}
			serializer: {
			  className: org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerV3d0,
			  config: { serializeResultToString: true }
			}
		5、启动：  ./bin/gremlin.sh
		6、连接到GDB图数据库实例 
			:remote connect tinkerpop.server conf/gdb-remote.yaml		// 正常情况下，这里会显示当前Gremlin控制台所连接的GDB图数据库实例的Host和Port。
		7、 切换到远程模式，此后您在Grmlin控制台输入的所有DSL均将发向远端的GDB图数据库实例 
			:remote console
		8、退出 :exit
		8、开始和 gdb 交互 
			1、增
				1、创建点
					1、创建点 label=user  zs/ls/ww
						g.addV('user').property(id, 'zs').property('name', 'zs').property('age', 20)		// id 不给默认是 uuid ,这个id内置的，且整个图实例中唯一，也必须是string类型，id重复插入报错
						g.addV('user').property(id, 'ls').property('name', 'ls').property('age', 21)
						g.addV('user').property('name', 'ww').property('age', 21)

					2、创建点 label=software  bxkc/xique
						g.addV('software').property('name', 'bxkc').property('lang', 'java')
						g.addV('software').property('name', 'xique').property('lang', 'object-c')
				2、创建边(可以理解成点和点的关系)		// 会重复创建，如需要合并，项目操作
					1、 label=knows   
						g.addE('knows').from(V().has('user', 'name', 'zs')).to(V().has('user', 'name', 'ls')).property('weight', 0.5f)		// 相当于 (zs) -[knows:{'weight', 0.5f}]-> (ls)
						g.addE('knows').from(V().has('user', 'name', 'ls')).to(V().has('user', 'name', 'ww')).property('weight', 0.4f)		// 相当于 (ls) -[knows:{'weight', 0.4f}]-> (ww)
					2、 label=created   
						g.addE('created').from(V().has('user', 'name', 'zs')).to(V().has('software', 'name', 'bxkc')).property('weight', 0.5f)		// 相当于 (zs) -[created:{'weight', 0.5f}]-> (bxkc)		两个点任意一个不存在会报错
						g.addE('created').from(V().has('user', 'name', 'ls')).to(V().has('software', 'name', 'bxkc')).property('weight', 0.5f)		// 相当于 (ls) -[created:{'weight', 0.5f}]-> (bxkc)
						g.addE('created').from(V().has('user', 'name', 'ls')).to(V().has('software', 'name', 'xique')).property('weight', 0.5f)		// 相当于 (ls) -[created:{'weight', 0.5f}]-> (xique)
						g.addE('created').from(V().has('user', 'name', 'ww')).to(V().has('software', 'name', 'bxkc')).property('weight', 0.5f)		// 相当于 (ww) -[created:{'weight', 0.5f}]-> (bxkc)
			2、删
				1、删除 指定 label 的点和边
					g.E().hasLabel('knows').drop()
					g.V().hasLabel('user').drop()	// 会顺带删除关系的,不存在不会报错
				2、删除 zs 和 ls 的认识关系
					g.V('zs').outE('knows').where(inV().has(id, 'ls')).drop()
			
			3、改
				g.V('1').property('age', 50)		// 根据id修改(或新增)属性，
			4、查
				1、合计查询结果 : g.V().count()		
				2、关联查询(获取 marko 认识的人, marko认识的人created的software)
					g.V('zs').outE('knows').inV().hasLabel('user')
					g.V('zs').outE('knows').inV().hasLabel('user').outE('created').inV().hasLabel('software')
				3、 查看所有点： g.V().valueMap(true)		// 如果不加 true 就不会显示 id 和 label 
				1、其他
					g.V().hasLabel('user').has('age', gt(29))			// >29岁的人, 
					g.V().has('user','name',within('a','b','c'))		// P.within = within = in
					g.V().hasLabel('user').order().by('name', decr)		// 按name降序排列所有人
					g.V().has('name', 'zs')								// 所有 name=zs 的点
					g.V().has('user', 'name', 'zs')						// 所有 label=user and name=zs  的点
					g.V('zs').out('knows')								// zs 认识的人
					g.V('zs').outE('knows')								// zs 出去的边(label=knows)
					g.V('zs').in('knows')								// 认识zs的人
					g.V('zs').inE('knows')								// zs 为末端的边(label=knows)
					g.V().hasLabel('Project').not(has('name'))			// name is null


	2、java
		1、加入 pom 依赖
			<dependency>
			  <groupId>org.apache.tinkerpop</groupId>
			  <artifactId>gremlin-driver</artifactId>
			  <version>3.4.0</version>
			</dependency>
		2、初始化 Cluster
			1、使用yaml文件
				String yaml = "C:\\Users\\Administrator\\Desktop\\allNote\\maxCompute\\gdb-remote.yaml";
				Cluster cluster = Cluster.build(new File(yaml)).create();
			2、非配置文件
				Cluster cluster = Cluster.build(host).port(port).credentials(username, password)
                    .serializer(Serializers.GRAPHBINARY_V1D0)
					// 其他配置以后再去研究
					.create();
		3、添加一个节点
			public void testGDB() {
				try{
					String yaml = "C:\\Users\\Administrator\\Desktop\\allNote\\maxCompute\\gdb-remote.yaml";
					Cluster cluster = Cluster.build(new File(yaml)).create();
					Client client = cluster.connect().init();
					String dsl = "g.addV(yourlabel).property(propertyKey, propertyValue)";
					Map<String,Object> parameters = new HashMap<>();
					parameters.put("yourlabel", "user");
					parameters.put("propertyKey", "name");
					parameters.put("propertyValue", "zl");
					ResultSet results = client.submit(dsl, parameters);
					List<Result> result = results.all().join();
					result.forEach(p -> System.out.println(p.getObject()));
					cluster.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}


2、数据导入
	
	1、使用 dataX 工具
		1、 安装DataX环境   https://github.com/alibaba/DataX/blob/master/userGuid.md?spm=a2c4g.11186623.2.20.192c3dc3B0vshT&file=userGuid.md
		2、 配置GDB同步任务 https://github.com/alibaba/DataX/blob/master/gdbwriter/doc/gdbwriter.md?spm=a2c4g.11186623.2.21.192c3dc3B0vshT&file=gdbwriter.md



1、看不懂的语法
	g.V().project('user','degree').by().by(inE().count()).order().by(select('degree'), desc).limit(3)
	g.V('u01').store('x').repeat(out().where(without('x')).aggregate('x')).until(hasId('u02').or().loops().is(eq(10))).hasId('u02').path()
	g.inject('a').choose(__.V(11),__.V(11).property('a','c'),__.addV("11").property(id,11).property('a','b'))  // 这个是点存在，更新属性； 不存在插入点的dsl






返回结果记录
	1、Result one = result.one();  // 这是 add一个新Vertex 返回的结果 result{object=v[170911493075898368] class=org.apache.tinkerpop.gremlin.structure.util.reference.ReferenceVertex}
	2、List<Result> records = result.all().join();		// 添加边返回的结果 每个 Result 就是 result{object=e[49179765-f2b3-42be-b894-58698c193c6c][170963092800409600-ZhaoBiaoRelation->170962991755431936] class=org.apache.tinkerpop.gremlin.structure.util.reference.ReferenceEdge}
	3、 更新或者删除返回下面的两个怎么看（删除的都是没有值的）
		List<Result> records = result.all().join();
        Result one = result.one();


1、问题总结
	1、把整个 点/边 所有属性覆盖更新 如 ('a':'a','b':'b','c':'c') => ('a':'aa','d':'d')
		g.V(2).sideEffect(properties().drop()).property("a", "aa").property("d", "d")
	2、ResultSet result = client().submit("g.V('1').property('name', 'zs')");  更新之后我怎么知道我成功更新的数量，删除操作也类似
		返回删除的数量 g.V(2).sideEffect(__.drop()).count()		// 删除时需要知道影响的数量 result.one().getInt()
		更新数量 g.V(1).property("key", "value").count()	    // 更新后需要知道影响的数量 result.one().getInt()
	3、创建边 (比地1)-[ZhaoBiaoRelation]->(p2) 如果存在完成覆盖
		g.V().has('Organization','name','比地1').coalesce(__.outE('ZhaoBiaoRelation').where(inV().has('Project','projectName','p2')), addE('ZhaoBiaoRelation').to(V().has('Project','projectName','p2')).property(id,'1')).sideEffect(properties().drop()).property('name','改的')
		
		g.V().has('Organization', 'name', orgName).coalesce(outE(r).where(inV().has('~id', pId)), addE(r).from(V().has('Organization', 'name', orgName)).to(V(pId)).property(id,generateId))
		
		g.addE(r).from(V().has('Organization', 'name', '%s')).to(V('%s'))

		g.addE(docChannelRelation).from(V(startId)).to(V(endId))
		g.V(startId).coalesce(outE(docChannelRelation).where(inV().has('~id', endId)), g.addE(docChannelRelation).from(V(startId)).to(V(endId)).property(id,'1'))
	
	4、g.addE('ZhaoBiaoRelation').from(V().has('Organization', 'name', '比地1')).to(V().has('Project', 'projectName', 'p2'))   最多只会创建一条关系
		是的，这是gremlin的语义，如果想用gremlin批量加边，需要先拿出所有的点，然后一个个点添加边
	5、没有唯一约束，会有一些隐患
		1、插入时在判断是否有存在的
	6、g.addV('Project').property('name',null)		// 不支持设置null，但可以置空 g.V('1').sideEffect(properties('name','age',...).drop())
	7、返回的边，可以获取整个结构吗？ (o)-[ZhaoBiaoRelation]->(p) 所有点和边的属性
	8、空的查询
	9、那样设计可能有线程安全问题



2、第二次问题
	2、match (o:Organization)-[r]->(p:Project) where id(o) in ['比地1','比地2'] return o.name as name,type(r) as relationType,count(*) as num;  怎么使用gremlin实现


3、第三次问题
	1、我想知道某个点进和出的边


1、以后待研究的问题
	4、何时发送请求
		ResultSet result = client().submit(dsl);		// 这句发的请求，还是下面发的
		List<Result> records = result.all().join();
	3、时间字符串支持排除排序吗？例如 2020-01-01/2020-01-03/2020-01-02 经过升序排列顺序为 2020-01-01/2020-01-02/2020-01-03
	1、创建边之后返回结果集合如何orm
	









1、场景
	1、统计 O -> P 的关系下面3种实现（他们建议我用第三种）
		match (o:Organization)-[r]->(p:Project) where id(o) in ['1','2'] return o.name as name,type(r) as relationType,count(*) as num;
		g.V().as('o').hasLabel('Organization').has('~id', within('172094692669919232', '172094692913188864')).outE().as('r').inV().hasLabel('Project').select('o', 'r').group().
			by(
				__.project('name', 'relationType').
					by(__.select('o').values('name')).
					by(__.select('r').label().is(neq('vertex')))).
			by(
				__.fold().project('name', 'relationType', 'num').
					by(__.unfold().select('o').values('name')).
					by(__.unfold().select('r').label()).
					by(__.unfold().count())
			).unfold().select(values)

		g.V('172068400851456000').as("o").outE().as("r").inV().as("p").select('o', 'r')
			.group()
			.by(
				__.project('name', 'relationType').
				by(__.select('o').values('name')).
				by(__.select('r').label().is(neq('vertex')))
			)
			.by(
				__.fold()
				.project('name', 'relationType', 'num').
				by(__.unfold().select('o').values('name')).
				by(__.unfold().select('r').label()).
				by(__.unfold().count())
			).unfold().select(values)

		g.V().as('o')
		.hasLabel('Organization').has('~id', within(ids)).values('name').as('n')
		.select('o').outE().hasLabel(neq('vertex')).as('r')
		.label().as('l')
		.select('r').inV().hasLabel('Project').select('n', 'l').group().by()
		.by(
			fold().project('name', 'relationType', 'num')
			.by(unfold().select('n'))
			.by(unfold().select('l'))
			.by(unfold().count())
		).unfold().select(values)

	2、在指定的Project找出没有和Document有关系的Project
		g.V('226283160048185346', "226279109751345152", '1').where(__.in().hasLabel('Document').count().is(eq(0L)))

1、场景1 获取前三个关联的招标人
	cypher
		match(o1:Organization)-[r1]->(p1:Project)<-[r2]-(o2:Organization)
		where o1.bidiId=211471170539573248 and type(r1) in['TouBiaoRelation','ZhongBiaoRelation'] 
		and type(r2) in['ZhaoBiaoRelation'] and o2.province='广东' and o2.city in['广东','深圳']
		return 
		o1.name as targetOrgName,o2.name as name,o2.province as province,count(*) as num 
		order by num desc 
		skip 1 limit 3 

	gremlin
		g.V().has('Organization', 'bidiId', 211471170539573248).as('o1')
		.outE('TouBiaoRelation','ZhongBiaoRelation').as('r1')
		.otherV().hasLabel('Project').as('p1')
		.inE('ZhaoBiaoRelation').as('r2')
		.otherV().has('Organization','province', '广东').has('city', within('广东','深圳')).as('o2')
		.group()
		.by(
			__.project('targetOrgName', 'name', 'provice')
			.by(select('o1').values('name'))
			.by(select('o2').values('name'))
			.by(select('o2').values('province'))
		)
		.by(
			__.project('targetOrgName', 'name', 'provice', 'num')
			.by(__.unfold().select('o1').values('name'))
			.by(__.unfold().select('o2').values('name'))
			.by(__.unfold().select('o2').values('province'))
			.by(__.unfold().count())
		)
		.unfold().select(values)
		.order().by(select('num'), desc).skip(1).limit(3)


2、场景2
	1、cypher
		match(o1:Organization)-[r1]->(p1:Project)<-[r2]-(o2:Organization)
		where 
		o1.bidiId=211471170539573248 and type(r1) in['TouBiaoRelation','ZhongBiaoRelation'] 
		and type(r2) in['ZhaoBiaoRelation'] and o2.bidiId=211659447624224768
		return 
		o1.name as targetOrgName,
		o2.name as name,
		(CASE WHEN p1.zhongBiaoPageTime is not null THEN p1.zhongBiaoPageTime ELSE p1.zhaoBiaoPageTime END) as pageTime,
		order by pageTime desc 
		skip 1 limit 3 
	2、gremlin
		g.V().has('Organization', 'bidiId', 211471170539573248).as('o1')
		.outE('TouBiaoRelation','ZhongBiaoRelation').as('r1')
		.otherV().hasLabel('Project').as('p1')
		.inE('ZhaoBiaoRelation').as('r2')
		.otherV().has('Organization','bidiId', 211659447624224768).as('o2')
		.by(
			__.fold().project('targetOrgName', 'name', 'pageTime')
			.by(__.unfold().select('o1').values('name'))
			.by(__.unfold().select('o2').values('name'))
			.by(__.unfold().select('p1').values('zhongBiaoPageTime'))
		)
		.unfold().select(values)
		.order().by(select('pageTime'), desc).skip(1).limit(3)



g.V().has('docid',within(1L,2L)).aggregate('x').hasLabel('Document')

g.V().has('~id', within('1','2')).in().hasLabel('Document')



// 需要 id为1,2的Project 不存在和 Document 有in关系的 Project(第二句是正确的)
g.V().has('~id', within('1','2')).aggregate('x').hasLabel('Project').not(in().hasLabel('Document'))
g.V('1', '2').hasLabel("Project").where(__.in().hasLabel('Document').count().is(eq(0L)))

// 删除 docid 为1的Document 和 Document-[r]->Project 关系 
g.V('1').hasLabel('Document').outE().where(otherV().hasLabel('Project')).drop()


gremlin 参考文档：http://tinkerpop-gremlin.cn
阿里gdb文档：https://help.aliyun.com/product/102714.html?spm=a2c4g.750001.list.48.7b967b13duqLxm


// 批量修改 price 为string类型的数据(如果price是字符串 相当于 price is not null)		// 明天问一下巧工，因为这个慢
g.V().hasLabel('user').has('price',gte(7)).limit(10).property('price',12.2D) 

// 如果 province 为空 则使用 '' 默认值，要不然会报错 
g.V('user').as('u').project('province','price')
.by(select('u').coalesce(values('province'), constant('默认值')))
.by(select('u').coalesce(values('price'), constant(0D)))


// 获取相关项目(竞争对手)
g.V('沈阳东软医疗系统有限公司').as('o1')
.outE('TouBiaoRelation','ZhongBiaoRelation').as('r1')
.otherV().hasLabel('Project').as('p')
.inE('TouBiaoRelation','ZhongBiaoRelation').as('r2')
.otherV().has('~id','上海西门子医疗器械有限公司').as('o2')
.select('o1','r1','p','r2','o2')
.project('targetOrgName','r1','pid','pageTime','province','city','r2','relationOrgName')
.by(select('o1').coalesce(values('name'),constant('')))
.by(select('r1').label())
.by(select('p').id())
.by(select('p').coalesce(values('zhongBiaoPageTime'), coalesce(values('zhaoBiaoPageTime'), constant(''))))		// 优先取 zhongBiaoPageTime->zhaoBiaoPageTime->''
.by(select('p').coalesce(values('province'),constant('未知')))
.by(select('p').coalesce(values('city'),constant('未知')))
.by(select('r2').label())
.by(select('o2').coalesce(values('name'),constant('')))
.order().by(select('pageTime'), desc)
.skip(0).limit(100)




