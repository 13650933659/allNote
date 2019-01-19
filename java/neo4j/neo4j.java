











1、neo4j是什么
	1、OGM(Object-Graph-Mapping)
2、安装
	1、第一种安装版的自己看
	2、第二种解压版的
		1、去neo4j官网下载neo4j-community-3.4.10-windows.zip
		2、解压neo4j-community-3.4.10-windows.zip，改名目录为neo4j-C-3410
		3、配置环境变量
			NEO4J_HOME=C:\Software\OpenSource\neo4j-C-3410 PATH = ;%PATH%/bin
3、启动关闭
	1、启动：打开dos仓库输入neo4j.bat console即可，使用浏览器访问localhost:7474默认账号密码是neo4j=neo4j
	2、关闭：关掉黑窗口即可，或者Ctrl + C
4、使用
	1、杂项
		1、默认的数据库目录会在neo4jHome/data/，好像是可以通过neo4jHome/conf/的某一个文件可以改的
	2、创建节点
		1、增加
			1、创建空节点：create(s:Student) // s是节点的名称这个好像没什么用，但是最好给上不然查询不到，Student是节点标签(这个很有用)
			2、创建有属性的节点：create (s:Student {id:10,name:zs})
			3、创建关系：match(s:Student{name:'zs'}),(t:Teacher{name:'msb'}) create r=(s)-[b:Belong]->(t) return r;  // 如果要合并已有的关系可以使用merge
		2、查询
			match(d:Dept) where d.name='zs' return d.name,d limit 10 // 查询name=zs的，返回name和全部，十条
		3、删除属性：match(d:Dept) remove d.name 
		4、删除节点：match(d:Dept) delete d 
		5、更新属性：match(d:Dept{name:'zs'}) set d.name='zs2',d.age=10;	// 没有属性就会增加
		6、排序：match(d:Dept) return d order by d.id  // 用id排序默认升序，降序可以使用desc
		7、合并结果：match(d:Dept{name:'zs1'}) return d.name as name union match(d2:Dept{name:'zs1'}) return d2.name as name  // 如果要合并相同行可以使用union all
		8、分页：match(d:Dept) return skip 10 limit 10  // 跳过前10行，取十行
		9、处理空：match(d:Dept{name:'zs1'}) where d.id is not null return d
		10、in和not in：match(d:Dept) where d.name in['zs1','zs2']  return d
		11、字符串函数：MATCH (n:Student) RETURN upper(n.name)   // 把name转成大写的，如果要用小写的使用lower
		12、聚合函数：match(d:Dept) return count(*),min(id(d))  // 总数和最小id，注意这个id是neo4j自己维护的，所以要这样的写法
		
		14、关系的查询：match p=()-[r:Belong]->() RETURN p 
		15、使用startnode和endnode函数：match p=()-[r:Belong]->() return endnode(r)
		16、创建索引：create index on : Teacher(name)  // 在标签下面的所有节点的name属性创建索引
		17、删除索引：drop index on : Teacher(name) 
		18、创建唯一约束：create constraint on (t:Teacher) assert t.name is unique
		19、删除唯一约束：drop constraint on (t:Teacher) assert t.name is unique

		// 整理到索引

		create(p:Project{project_name:'testProject1',area:'华北',province:'内蒙古'});
		match(o:Organization{name:"testOrg1"}) set o.area='华北',o.province='内蒙古';

		match(p:Project{project_name:'testProject1'}),(o:Organization{name:'testOrg1'}) create r=(o)-[d:DaiLiRelation]->(p) return r;
		match(p:Project{project_name:'testProject1'}) set p.zhao_biao_uuid='uuid1';
16172048

5、集群
	远程一定要打开：org.neo4j.server.webserver.address=0.0.0.0

	// 参考网站：https://cloud.tencent.com/developer/article/1054930


// 这两个是干嘛的
 org.neo4j.server.webadmin.data.uri=/db/data/
 org.neo4j.server.webadmin.management.uri=/db/manage/

5、数据库的本分还原
	1、备份有空再去看看
		1、可能就是C:\Software\OpenSource\neo4j-C-3410\data\databases\graph.db这个目录了，拷贝即可


17021684
