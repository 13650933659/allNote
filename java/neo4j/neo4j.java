

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
4、使用		// 重要问题，neo4j自动生成的id是全部标签都是唯一的吗
	1、杂项
		1、默认的数据库目录会在neo4jHome/data/，好像是可以通过neo4jHome/conf/的某一个文件可以改的
	2、创建节点
		1、增加
			1、创建空节点：create(s:Student) // s是节点的名称这个好像没什么用，但是最好给上不然查询不到，Student是节点标签(这个很有用) 会动态的创建节点
			2、创建有属性的节点：create (s:Student {id:10,name:zs})
			3、创建关系：match(s:Student{name:'zs'}),(t:Teacher{name:'msb'}) create r=(s)-[b:Belong{这里还可以写关系的属性}]->(t) return r;  // 如果要合并已有的关系可以使用merge，s->[b]->t必须全部一样才合并
		2、查询
			match(d:Dept) where d.name='zs' return d.name,d limit 10 // 查询name=zs的，返回name和全部，十条
		3、删除属性：match(d:Dept) remove d.name 
		4、删除节点：match(d:Dept) delete d				// 如果有关系的话使用 match(d:Dept) detach delete d 他会清除所有关系然后在删除
		5、更新属性：match(d:Dept{name:'zs'}) set d.name='zs2',d.age=10;	// 没有属性就会增加
		6、排序：match(d:Dept) return d order by d.id  // 用id排序默认升序，降序可以使用desc
		7、联合多个查询结果：match(d:Dept{name:'zs1'}) return d.name as name union match(d2:Dept{name:'zs1'}) return d2.name as name  // 如果不合并相同行可以使用union all
		7、合并相同数据：match (o:Organization)-[r]->(p:Project) return distinct  id(o);
		8、分页：match(d:Dept) return skip 10 limit 10  // 跳过前10行，取十行
		9、处理空is null和is not null：match(d:Dept{name:'zs1'}) where d.id is not null return d	// 为空或者没有此字段的，不包括空字符串的，要判断空字符串使用下面的
		9、处理空字符串：match(d:Dept) where d.name<>''					// 
		10、in和not in：match(d:Dept) where d.name in['zs1','zs2']  return d
		11、字符串函数：MATCH (n:Student) RETURN upper(n.name)   // 把name转成大写的，如果要用小写的使用lower
		12、聚合函数：match(d:Dept) return count(*),min(id(d))  // 总数和最小id，注意这个id是neo4j自己维护的，所以要这样的写法
		12、 delete 联合 return 的使用 match (o:Organization)-[r:ZhongBiaoRelation]->(p:Project) delete r return o;
		
		14、关系的查询：match p=()-[r:Belong]->() RETURN p 
		14、查询项目(id=17036888)所有的关系：match result=(o:Organization)-[r]->(p:Project) where id(p)=17036888 return result
		15、使用startnode和endnode函数：match p=()-[r:Belong]->() return endnode(r)
		16、创建索引：create index on : Teacher(name)  // 在标签下面的所有节点的name属性创建索引
		17、删除索引：drop index on : Teacher(name) 
		18、创建唯一约束：create constraint on (t:Teacher) assert t.name is unique
		19、删除唯一约束：drop constraint on (t:Teacher) assert t.name is unique
		20、列出所有label的索引以及约束：  :schema
		// 整理到索引
		21、查询不存在关系的节点： match (n:Project) where (n.zhao_biao_page_time='2019-05-08' or n.zhong_biao_page_time='2019-05-08') and  (not (n)–[]-())  return count(n)
		21、自动分组查询： match (o:Organization{name:'比地1'})-[r]->(p:Project) return type(r) as type ,count(type(r)) as nb,sum(toFloat(replace(p.bidding_budget,'元','')));	// 如果你没加 count(type(r)) 就不会自动分组
		22、 使用子查询 ： 
			1、 分批删除不存在关系的企业节点 ： match(o:Organization) where  (not (o)–[]->())  with o limit 5000 delete o;
		22、 合并加排序案例
			Match (o:Organization)-[r]->(p:Project) where o.name in['比地1','比地2','比地3'] and type(r) in['ZhaoBiaoRelation','ZhongBiaoRelation']  return o.name as 名称,count(*) as 招投标数 order by  count(*) desc




5、集群
	远程一定要打开：org.neo4j.server.webserver.address=0.0.0.0

	// 参考网站：https://cloud.tencent.com/developer/article/1054930
	// 参考项目，但是如果使用事务的话，可能报错


// 这两个是干嘛的
 org.neo4j.server.webadmin.data.uri=/db/data/
 org.neo4j.server.webadmin.management.uri=/db/manage/

5、数据库的本分还原
	1、备份有空再去看看
		1、可能就是C:\Software\OpenSource\neo4j-C-3410\data\databases\graph.db这个目录了，拷贝即可









neo4j java客户端
	1、neo4j-jdbc-driver
		<dependency>
			<groupId>neo4j-jdbc-driver</groupId>
			<artifactId>neo4j</artifactId>
			<version>3.1.0</version>
		</dependency>
	2、使用spring data





1、 慢的查询 
	查多种关系 type(r)='TouBiaoRelation' 或者 type(r) in ['ZhaoBiaoRelation'] 比 (o:Organization)-[r:ZhaoBiaoRelation]->(p:Project) 慢几十倍，但是也要看情况，再去优化




match (o:Organization)-[r:ZhaoBiaoRelation]->(p:Project2) where o.bidiId=61407576317218816  
with p,(CASE WHEN p.zhongBiaoPageTime is not null THEN p.zhongBiaoPageTime ELSE p.zhaoBiaoPageTime END) as date 
where date>'2018-01-01' return id(p),p.zhongBiaoPageTime,p.zhaoBiaoPageTime,date  order by  date desc


match (o:Organization)-[r:ZhaoBiaoRelation]->(p:Project2) where o.bidiId=61407576317218816 and (p.zhongBiaoPageTime is not null or p.zhaoBiaoPageTime is not null) 
return id(p),p.zhongBiaoPageTime,p.zhaoBiaoPageTime,(CASE WHEN p.zhongBiaoPageTime is not null THEN p.zhongBiaoPageTime ELSE p.zhaoBiaoPageTime END) as date order by  date desc



