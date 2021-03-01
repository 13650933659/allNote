








1、计费
	1、存储计费
	2、读写计费
		1、计费单位为 cp 相当于我们的斤，克，千克...
	3、外网下载计费


2、实例规格
	高性能实例
	容量型实例




3、限制
	1、单个阿里云账号下最多10实例数
	2、单实例中表最多64个
	3、其他限制，请看文档


4、javaSDK
	1、pom.xml
		<dependency>
			<groupId>com.aliyun.openservices</groupId>
			<artifactId>tablestore</artifactId>
			<version>5.4.0</version>
		</dependency> 

4、模型
	1、 wide column 模型
		1、主要组成部分
			1、主键：主键是表中的每一行的唯一标识，主键由 1-4个主键列组成
			2、分区键：主键的第一列为分区键，相同的分区键会被分为同一个分区，实现数据访问负载均衡(可以实现范围的分区吗？)
			3、属性列：一行除主键外，其余的都是属性列，他可以有多个版本的值，每行的属性列没有个数限制，是动态的
			4、版本：每一个值对应不同，版本的值是一个时间戳，用于定义数据的生命周期
			5、数据类型：目前支持 String/Binary/Double/Integer/Boolean 5种数据类型
			6、生命周期：每个表可以定义生命周期，例如定义为一天，则该表数据中在一个月之前写入的数据会被自动清理
			7、最大版本数：每个表可以定义每个属性列最多保存的版本数，当属性列的版本个数超过这个数时，最早的版本将被异步删除
	2、 timeline 模型
	3、 timestream 模型(使用场景如快递物流信息的治理，这个暂时用不到，以后再去了解)
	4、 grid 模型(使用场景如：气象台数据的治理，这个暂时用不到，以后再去了解)



5、mysql数据同步到ots 参考 (https://help.aliyun.com/document_detail/124751.html?spm=a2c4g.11174283.6.737.379330afR7by4C)
	1、table模式（多task），您无需自己写select语句，而是由DataX根据JSON中的的column、table、spliPk配置项，自行拼接SQL语句，




1、问题
	2、在创建索引时 RoutingFields：自定义路由字段，这个是什么？




2、使用经验
	1、一个表建议一个索引
	2、删除更新最多200，GetRow 最多 100
	3、多元素索引的 limit最多100否则报错， offset 最多 10000 相当于只能查前100页
	4、ShouldQueries 和 MustQueries 不能同时使用，但是 MustQueries 和 mustNotQueries 可以一起使用
	5、ots插入是覆盖更新，但是通过maxCompute的外部表，没有在创建外部表时定义的字段是不会被覆盖更新的
	6、一个表最多15个预定列，但是我的能添加到32列(可能我的实例被升级过)
	7、如果使用二级索引表，刚刚开始他们分区比较慢，如需快速操作，需要联系工作人员手动分区，以后会自动分区，但是比较慢
	8、每个字段的值不能超过 2M 即 2097152 个字节 2 * 1024 = 2048KB * 1024 = 2097152byte, 即能容下 2097152/3约699050 个中文， 2097152 英文和数字
	4、ots创建二级索引时，包含存量和不包含存量是什么意思呢
	5、获取记录时 投影的字段不存在，就算记录存在也是不会返回记录的，特别注意
	1、ots聚合统计 (通用 )
	1、通用
		SearchQuery.setLimit(0);								// 聚合统计提高性能
		SearchQuery.setAggregationList(List<Aggregation>);		// 聚合统计
		SearchQuery.setGroupByList(List<GroupBy>);				// 分组
		GroupByField.Builder.size(2000);						// 最多取2000个分组，默认拿前10个， offset+limit 和 nextToken 两种方式都不可以的
		having(条件过滤)										// 也是实现不了的
	2、案例
		1、 去重的合计  select count(distinct name) from user;
			DistinctCountAggregation distinctCount = AggregationBuilders.distinctCount("tendereeDistinctCount", "tenderee").build();
			SearchQuery.setAggregationList(aggregationList.add(distinctCount));

		2、  select win_tenderer winTendererGroup,sum(win_bid_price) winBidPriceSum from document group by win_tenderer limit 2000;
			GroupByField.Builder builder = GroupByBuilders.groupByField("winTendererGroup", "win_tenderer").size(2000);	// 最多2000;
			builder.addSubAggregation(AggregationBuilders.sum("winBidPriceSum", "win_bid_price"));
			SearchQuery.setGroupByList(Arrays.asList(builder.build()));
		3、 select win_tenderer winTendererGroup, count(1) num,sum(win_bid_price) winBidPriceSum from document group by win_tenderer order by num desc,winBidPriceSum desc limit 2000;
			GroupByField.Builder builder = GroupByBuilders.groupByField("winTendererGroup", "win_tenderer").size(2000);	// 最多2000;
			builder.addSubAggregation(AggregationBuilders.sum("winBidPriceSum", "win_bid_price"));

			// 排序 ,  项目数量排序倒叙, 再根据项目金额倒叙.
			List<GroupBySorter> groupBySorter = new ArrayList<>();
			groupBySorter.add(GroupBySorter.rowCountSortInDesc());					// 数量倒序
			groupBySorter.add(GroupBySorter.subAggSortInDesc("winBidPriceSum"));	// 总金额倒序
			builder.addGroupBySorter(groupBySorter);
			searchQuery.setGroupByList(Arrays.asList(builder.build()));



2、 Query
	1、 TermQuery 和 TermsQuery		// 精准查询
	2、 MatchAllQuery				// 用于匹配所有行，常用于查询表中数据总行数，或者查看表中任意几条数据。
	3、 MatchQuery					// 匹配查询（用于分词字符串-最大数量语义分词）
	4、 MatchPhraseQuery			// 短语匹配查询（用于分词字符串-最大数量语义分词）
	5、 RangeQuery					// 范围条件查询也是用时间
	6、 WildcardQuery				// 通配符查询
	7、 BoolQuery					// 多条件组合查询
	8、 NestedQuery					// 嵌套查询
	9、	ExistsQuery					// 查询表不为空的数据




