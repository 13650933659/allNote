




c+u 文本替换












1、schema.xml
	1、
2、solrConfig.xml和XX-data-config.xml


3、solr的索引创建查询方式
	1、solrJ
	2、DIH
4、查询可使用solrJ或者js发起http请求



1、 公司线上的solr 
	1、http://47.97.221.63:8983/solr/#/document/query		// 只有 document 集合是集群的

		q:
			dochtmlcon:"黑龙江尚米电力设备有限责任公司"
		fq
			publishtime:[2016-11-04T00:00:00Z TO 2019-12-03T23:59:59Z]		// 时间过滤
		start,row
			0 10	// 从0开始取10条
		fl
			id		// 投影id也就是 docid

	2、 47.97.210.202		// 这个有 designed_project 集合







