




1、分库分表
	就是原本存于一个库的数据，分块存入多个库中，原本把一个存于一个表的数据，分块存入多个表中



2、为什么要分表
	数据量也来越大的解决方案
		1、换成oracle或者把一部分数据存入redis
		2、把历史数据进行归档
		3、主从复制读写分离
		4、分库分表
		5、云数据库TIDB
3、如何取分库
	1、垂直拆分
	2、水平拆分
		1、每个库（表）的结果都一样
		2、每个库（表）的数据都不一样
		3、问题
			1、扩容麻烦
			2、分片规则难抽取出来

总结：垂直拆分 = 表结构的拆分，水平拆分 = 表内容的拆分 合二为一 垂直拆库，水平拆表


sharding-sphere(之前叫 sharding-jdbc)
	proxy代理层： mycat 、 atals 、 mysql-proxy 、 sharding-proxy		// 请求多了一倍，跨语言的，不跨数据库
	jdbc直连层：  sharding-jdbc 、 tddl									// 只能用java，跨数据库

sharding-jdbc
	SQLType 来路由到数据库节点

增删改查

java代码
	ShardingRuleConfiguration ruleConfig = new ShardingRuleConfiguration();
	ruleConfig.getTableRuleConfig().add(getOrderTableRuleConfiguration());
	ruleConfig.getTableRuleConfig().add(getOrderItemTableRuleConfiguration());
	ruleConfig.getBindingTableGroups().add("t_order", "t_order_item");
