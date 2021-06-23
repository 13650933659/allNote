

1、MaxCompute(数据库)
	（原名ODPS）是一种大数据计算服务，能提供快速、完全托管免运维的GB到EB级云数据仓库解决方案，已经与阿里云部分产品集成，快速实现多种业务场景。

1、 maxCompute(原名ODPS 开放数据处理服务)的基本概念
	project		 - 项目(类似mysql的schema或者database)
	table		 - 表(类似mysql的表)
 	partition    - 分区(类似我们平常的分表)
	resource     - 资源(这是maxcompute的新概念，这个可以直接调用java的jar的)
	一组概念
		task     - 单个 sqlQuery 或者 MapReduce 程序
		workflow - 是个有向无环图(DAG)，描述各个task之间的依赖关系和约束
		job      - 由一个或者多个task以及表示其执行次数关系的workflow组成(job是一个静态概念，作业对象对应一个xml文件)
		instance - 当 job 被提交到系统中执行时，该作业就会拥有一个个 job 多次运行就会有多个 instance ， instance 保存了执行的 snapshot ，返回状态等

2、 maxCompute 使用场景(数据的分析，类似人工智能)
	1、基于sql构建大规模数据仓库系统和BI系统
	2、基于DAG/Graph构建大型分布式应用系统
	3、基于统计和机器学习的大数据统计和数据挖掘(像pai框架)

3、计费、余额预警与停机策略与账单
	1、计费单元(project) ，周期为天
		1、存储计费(数据压缩后计费(压缩比一般在5倍左右)，每个小时采集一次数据大小，取出一天的平均值)
			1、阶梯计费
				1、0 < X <= 512MB           每个项目 0.01元/天
				2、512MB < X <= 10TB部分    0.0072元/GB/天
				3、10TB < X <= 100TB部分    0.006元/GB/天
				4、100TB < X部分			0.004元/GB/天
			2、例子
				10TB=10240G
				10240G * 0.0072  = 73.728元/天

				100TB=102400G
				10240 * 0.0072 + (102400-10240)*0.006 = 73.728 + 552.96 = 626.688元/天			
		2、计算计费(目前MC开发开放的计算任务类型有 sql/外部表/udf/mr/graph/lightning/spark/机器学习 等，其中 sql(不包括udf)/mr/new sql(mc2.0)/外部表/lightning/spark 已经收费，其他暂无收费计划)
			1、按量付费(即作业执行后收费，他可以共享公告资源)
				1、 sql 
					1、标准版
						1、一次sql计算费用 = 计算输入数据量 * sql复杂度 * sql价格（0.3元/GB）
							计算输入数据量 - 列裁剪，分区裁剪  // 查询时select时尽量少拿些没用的字段
							sql复杂度      - 关键字个数，我们可以使用 最高为1-4
							sql价格		   - 0.3元/GB
						2、可以使用 cost sql <SQL> 看出 计算输入数据量 和 sql复杂度 
					2、开发者版
						1、一次SQL计算费用 = 计算输入数据量 * 单价（0.15元/GB）
				2、 外部表(2018年10月31日开始支持)
					1、一次SQL计算费用 = 计算输入数据量 * 单价（0.03元/GB）
					
				3、 mapReduce 任务，目前只支持 标准版 
					1、MR任务当日计算费用 = 当日总计算时 * 单价（0.46元/计算时）
						当日总计算时  - 一个MR任务一次执行成功的计算时=任务运行时间（小时）*任务调用的核数量
				4、 lightning(交互式分析)
					1、一次Lightning查询费用 = 查询输入数据量 * 单价（0.03元/GB）
						1、Lightning服务使用单独的计算资源，即使您购买了MaxCompute包年包月服务，使用Lightning时也需要对Lightning按查询付费。
						2、Lightning按照每次SQL查询扫描的数据大小（每条查询至少10MB）付费。对于取消的查询，将按实际扫描的数据量收费。
						3、不查询不产生任何费用。
						4、MaxCompute默认对数据进行列式存储和数据压缩，Lightning的数据扫描大小按照压缩后的数据大小计算。
						5、查询分区表应用分区过滤条件，也将减少数据扫描量并提升查询性能。
						6、MaxCompute交互式分析（Lightning）服务现已在华东1、华东2、华北2、华南1正式开放使用，在中国（香港）、亚太东南1地区开放公测
				5、 spark
					1、Spark任务当日计算费用 = 当日总计算时 * 单价（0.66元/计算时）
						1、计算时  - 计算方法为Max(CPU×时长，向上取整(内存×时长/4))
						2、任务排队时间不计入计量计时。
						3、相同作业会根据提交作业时用户所指定资源大小的不同产生费用波动。
						4、如果您购买了MaxCompute包年包月服务，则在您购买的服务范围内您可以免费使用Spark计算任务，不用额外支付费用。
						5、如果您对Spark计算任务收费有疑惑，可以提工单咨询。
						6、MaxCompute Spark目前已经开通了华东1、华北2、华南1等Region，其他Region将陆续开放
				6、 Graph
				7、 机器学习
					
			2、包年包月(独占且只能享有购买的资源)
				1、 1CUCompute Unit = 4GB内存+1核cpu = 150元/月
				2、资源使用
					1进程占一个CU
					可以用命令调整每个进程占用的内存
					调用UDF需要额外占一个CU
			3、建议
				新用户   - 按量付费
				项目用户 - 开发环境按 包年包月 生产环境按按量付费
		3、下载计费，数据导入MaxCompute不计费(网络分：公网 VPC 经典网络)
			1、一次下载费用 = 下载数据量(http body 以 protobuff编码) * 下载价格(0.8元/GB)
			2、网络环境			// 所以尽量使用阿里云的产品配合就免费，而且速度快
				公网要收费
				通过经典网络下载不收费
				vpc下载 不收费
				有关MaxCompute服务连接的详情请参见配置Endpoint
	2、余额预警与停机策略
		1、余额预警
			1、系统自动预警
				最近24小时账单应付金额平均值
				下3个账期的费用
			2、自定义预警
				用户可设定预警值

		2、停机策略
			1、欠费     - 可用余额小于上个计费周期的账单金额
			2、停机     - 欠费超过24小时
			3、项目释放 - 停机超过14天
	3、账单
		1、出账时间
			1、以天为单位，计费周期结束后三个小时内，最长不超过六个小时
		2、用户中心
			1、消费记录
			2、使用详情
		3、作业详情
			1、 desc instance <!-- <instance id> -->;  // 查每个作业实例




4、开发
	1、odpscmd客户端，可以直接操作 maxCompute 一般以后我们会使用 maxCompute studio 来操作 maxCompute
	2、 sql 的支持
		1、支持
			1、支持各类运算符。
			2、通过DDL语句对表、分区以及视图进行管理。
			3、通过SELECT语句查询表中的记录，通过WHERE语句过滤表中的记录。
			4、通过INSERT语句插入数据、更新数据。
			5、通过等值连接JOIN操作，支持两张表的关联，并支持多张小表的MapJOIN。
			6、支持通过内置函数和自定义函数来进行计算。
			7、支持正则表达式。
		2、限制
			1、不支持事务、索引及UPDATE/DELETE等操作，同时MaxCompute的SQL语法与Oracle、MySQL有一定差别
			2、MaxCompute作业提交后会有几十秒到数分钟不等的排队调度，所以适合一次批量处理海量数据的跑批作业，不适合直接对接需要每秒处理几千至数万笔事务的前台业务系统
			3、支持的类型有限
				1、但是2.0以后可以使用下面两种方式开启新数据类型的使用
					1、session级别的开启：可以设置在每一句的sql前加入 set odps.sql.type.system.odps2=true;
					2、项目级别的开启   ： setproject odps.sql.type.system.odps2=true;			// 这个怎么设置？
				2、数据类型请查看： https://help.aliyun.com/document_detail/27821.html?spm=a2c4g.11186623.2.13.658f2bfbMu4917#section-evs-red-8r3
	3、表操作
		1、非分区表操作
			1、创建表
				create table bank_data(age bigint comment '年龄', job string comment '工作', emp_var_rate DOUBLE COMMENT '就业变化速率') comment '银行表';
			2、 查看表结构： desc bank;
			3、 删除表： drop table bank_data;
			4、 insert into table result_table SELECT 1,2;	// 这种是全表插入，列的顺序和定义时一样， 
				1、你也可以指定列插入 insert into t(name) values('zs'); , 
				2、他的主键可以重复插入的，因为他没有主键的概念，但是 ots 如果主键重复是覆盖插入，但是外部表到ots的话，也是覆盖了
				3、他的列是定义时指定的，不是动态的，插入不存在的列会报错
			4、 insert OVERWRITE TABLE result_table SELECT 1,2;	// 这种插入会把整个表都覆盖成一行的记录 1,2  但是前面不支持 values 只能按字段顺序插入 insert into 就可以
		2、分区表操作
			1、分区有以下使用限制：
				1、MaxCompute2.0对分区支持的字段类型进行了扩充，目前支持字段类型为 TINYINT/SMALLINT/INT/BIGINT/VARCHAR/STRING 的分区。
				2、单表分区层级最多为6级。				// 建议用到 二三级就可以了
				3、单表分区数最多允许60000个分区。
				4、单次查询允许查询最多的分区个数为10000个。
				5、STRING分区类型的分区值不支持使用中文。
			
			2、创建具有分区的表 parttest 表
				CREATE TABLE parttest (a bigint) PARTITIONED BY (pt bigint);
			3、增加两行数据
				INSERT INTO parttest partition(pt) SELECT 1, 2;		// 意为：向 parttest 的所有列+分区列 插入数据  如果dual表不存在就 不用from dual,
				INSERT INTO parttest partition(pt) SELECT 1, 10;
				insert into src parttion(pt,region) select 'zs',20,'20181010','gz';
			4、查询表中字段pt大于等于2的行
				SELECT * FROM parttest WHERE pt >= '2';		// 返回的结果只有一行，因为10被当作字符串和2比较，所以没能返回。把 '2' 改成 2 就可以返回
			5、创建一个二级分区表，以日期为一级分区，地域为二级分区
				CREATE TABLE src (key string, value bigint) PARTITIONED BY (pt string,region string);
			6、查询二级分区表
				1、正确使用方式，也可以只指定到第一级分区，但好像导入数据时需要指定到最后的分区
					select * from src where pt='20170601'and region='hangzhou'; 
				2、错误的使用方式。在这样的使用方式下，MaxCompute并不能保障分区过滤机制的有效性。pt是STRING类型，当STRING类型与BIGINT（20170601）比较时，MaxCompute会将二者转换为DOUBEL类型，此时有可能会有精度损失。
					select * from src where pt = 20170601; 
	4、 tunnel 功能
		1、 tunnel upload 功能
			1、 tunnel upload 命令只要用于数据上传
				1、支持文件目录(指一级目录)的上传，每一次上传只支持数据上传到一张表或者表的一个分区，有分区的表一定要指定上传的分区，多级分区一定要指定到末级分区
				2、 tunnel upload log.txt test_project.test_table/p1='b1',p2='b2';
					-fd  - 本地数据文件的列分隔符，默认是逗号
					-rd  - 本地数据文件的行分隔符，默认\r\n
					-bs  - 每次上传至 tunnel 的数据块大小，默认100MB
					-dbr - 是否忽略脏数据(多列、少列、列数据类型不匹配等情况)
					-s   - 是否扫描本地数据文件
				2、 tunnel upload 命令上传限制
					1、上传不限速，看你的自己网络和磁盘io的读写速度
					2、由于各种原因可能上传失败或者超时(600s)，重传最多5次(应该加本身的是6次)，就会继续上传下一个block，上传完毕可以使用 select count(*) 查询数据是否丢失
					3、 tunnel 命令不支持上传下载 Array/Map/Struct 数据类型
					4、每个 tunnel 的session 在服务端的生命周期为 24H,创建后24H内均可使用，也可以跨进程/线程共享使用，但是必须保证同一个 BlockId 没有重复使用
		2、 tunnel download 功能
			1、 tunnel download role c:/roleData.txt; // 下载 role 的数据到 c:/roleData.txt
	5、外部表的创建
		1、ots的外部表
			1、完成MaxCompute计算服务对ots数据访问的授权。当MaxCompute和TableStore的所有者是同一个账号时，您可以单击此处一键授权。如果不是，您可以自定义授权，详情请参见访问OTS非结构化数据。
			2、创建 ots_user_trace_log 外部表(使用经典网络，网络需要调试一下)
				set odps.sql.type.system.odps2=true;
				CREATE EXTERNAL TABLE user_external (
					id string COMMENT '用户id',
					name string COMMENT '系统版本ios xxx/android xxx',
					at bigint COMMENT '用户申请时间'
				)
				STORED BY 'com.aliyun.odps.TableStoreStorageHandler'
				WITH SERDEPROPERTIES (
					'tablestore.columns.mapping'=':id,name,at',  -- ots的字段映射，最好同名，主键需要使用 : 打头，在dataworks也是要的
					'tablestore.table.name'='user'			     -- ots的表名映射
					,'odps.properties.rolearn'='acs:ram::1670944310185319:role/loujiehua'  -- 如果 maxCompute和ots同一个账号就通过一键授权即可，如果不同账号需要通过 ram 方式访问
				)
				LOCATION 'tablestore://cjr-test.cn-hangzhou.ots-internal.aliyuncs.com/';	-- ots的接入点地址，在dataworks也是要 使用 tablestore 打头
	6、 mapReduce 功能
		1、确保您购买的资源非按量计费开发者版(标准版本比开发版更高级)
	
	7、 UDF
		1、访问外网情况：目前只支持 北京、上海 两个区域， 并且不允许以内网的形式访问ots和gdb，只能走外网的形式，就算maxCompute 和 ots/gdb 内网互通 也是不行的
		2、开发 Java UDF
			1、 udf
			2、 udaf
			3、 udtf
				1、javaSDK
					1、java代码
						@Resolve({"string,bigint->string,bigint"})		// 表示函数输入 f(string name, bigint num) return string,bigint;
						public class TestUdtf extends UDTF {
							@Override
							public void process(Object[] args) throws UDFException {
								String a = (String) args[0];
								Long b = (Long) args[1];
								for (String t: a.split("\\s+")) {       // 使用空格切分参数1，
									forward(t, b);  // 每一次调用forward就是一行记录， t和b 分别是 第一列和第二列
								}
							}
						}
				2、sql使用案例： select testUdtf('a b', 1) as(c1, c2)
				3、使用限制(udf没有下面限制)
					1、同一个select子句中不允许有其他表达式。  select name,f('a b', 1) as(c1, c2) from t;
					2、UDTF不能嵌套使用
					3、不支持在同一个select子句中与group by、distribute by、sort by联用
	7、 user defined type - 可以在sql中直接嵌入第三方语言（目前只支持java也就是整个jdk都可以用了）
		1、使用小例子： select new java.util.ArrayList<!-- <String> -->(array(1,2,3)).size();
		2、除了整个jdk，你还可以通过 set odps.sql.session.resource=test.jar 或者 set odps.sql.session.java.imports=com.test 来引用第三方的jar和包
	8、 dataworks 工具
		1、业务流程开发
			1、点击数据开发进入 dataStudio 进入数据开发，这里可以创建业务流程，每个业务流程的表是共用的(无论是使用sql创建或者在MaxCompute/表/创建的都是公用)
			2、在 MaxCompute/表/创建的 如果删除需要手动去 MaxCompute/表/(相当于删除快捷方式) 删除并且还需要使用sql 真正的delete from table


	9、数据源的配置
		1、找到对应的工作空间并且点击 “进入数据集成”
		2、找到左边的“数据源”
		3、点击右上角的“新增数据源”
		4、选择数据库类型，这里以 ots 数据为例子
			适用环境   - 开发 或者 生产
			endpoint   - ots的地址
			ots实例id  - ots的实例名称
			AccessKey ID -  AccessKey ID
			AccessKey secret -  AccessKey secret 
		5、点击“测试连通性”，如果成功则直接点击“完成”即可创建ots数据源
	10、数据集成功能
		1、ots同步数据到maxCompute
			1、ots全量到maxCompute
				{
					"type":"job",
					"version":"1.0",
					"configuration":{
						"setting":{
							"errorLimit":{
								"record":"0"    # 能够允许的最大错误数
							},
							"speed":{
								"throttle": false, # 不受限制（速度最快）
								"mbps":"1",   # 最大的流量，单位MB。
								"concurrent":"1"  # 并发数。可以配置大一点
								
							}
						},
						"reader":{
							"plugin":"ots",  # 读取的插件名称
							"parameter":{
								"datasource":"ots_cjr_test",  # 数据源名称
								"table":"document_temp",  # 表名
								"column":[  # 需要导出到MaxCompute中去的表格存储中的列名
									{ "name":"page_time"},
									{ "name":"docid"},
									{ "name":"docchannel"},
									{ "name":"dochtmlcon"},
									{ "name":"doctitle"}
								],
								"range":{  # 需要导出的数据范围，如果是全量导出，则需要从INF_MIN到INF_MAX
									"begin":[ # 需要导出数据的起始位置，最小的位置是INF_MIN。begin中的配置项数目个数和表格存储中相应表的主键列个数一致（下面是四列主键）。
										{
											"type":"INF_MIN"
										},
										{
											"type":"INF_MIN"
										},
										{
											"type":"STRING",  # 这个配置项的意思是：第三列的起始位置是begin1。
											"value":"begin1"
										},
										{
											"type":"INT",  # 这个配置项的意思是：第四列的起始位置是0。
											"value":"0"
										}
									],
									"end":[
										{
											"type":"INF_MAX"
										},
										{
											"type":"INF_MAX"
										},
										{
											"type":"STRING",
											"value":"end1"
										},
										{
											"type":"INT",
											"value":"100"
										}
									],
									"split":[  # 这一项配置分区范围，一般可以不配置，如果性能较差，可以提工单或者加入钉钉群：11789671 找工作人员协助您设置（这个目前不需要配置）
										{
											"type":"INF_MIN"
										},
										{
											"type":"STRING",
											"value":"splitPoint1"
										},
										{
											"type":"STRING",
											"value":"splitPoint2"
										},
										{
											"type":"STRING",
											"value":"splitPoint3"
										},
										{
											"type":"INF_MAX"
										}
									]
								}
							}
						},
						"writer":{
							"plugin":"odps",  # MaxCompute写入的插件名
							"parameter":{
								"datasource":"odps_test_project_cjr",  # MaxCompute的数据源名称
								"column":[  # MaxCompute中的列名，这个列名顺序对应TableStore中的列名顺序
									"page_time","docid","docchannel","dochtmlcon","doctitle"
								],
								"table":"ods_document",  # MaxCompute中的表名，需要提前创建好，否则任务会失败。
								"partition":"",  # 如果表为分区表，则必填。如果表为非分区表，则不能填写。需要写入数据表的分区信息，必须指定到最后一级分区。（这个目前不需要配置）
								"truncate":false  # 是否清空之前的数据
							}
						}
					}
				}
			2、ots增量到maxCompute	
				
				{
					"type": "job",
					"steps": [
						{
							"stepType": "otsstream",    # Reader插件的名称。
							"parameter": {
								"mode": "single_version_and_update_only",    # 配置导出模式，默认不设置，为列模式。
								"statusTable": "TableStoreStreamReaderStatusTable",    # 存储TableStore Stream状态的表，一般不需要修改。
								"maxRetries": 30,    # 从TableStore中读增量数据时，每次请求的最大重试次数，默认为30。重试之间有间隔，重试30次的总时间约为5分钟，通常无需更改。
								"isExportSequenceInfo": false,    # 是否导出时序信息，时序信息包含了数据的写入时间等。默认该值为false，即不导出。single_version_and_update_only模式下只能是false。
								"datasource": "ots_cjr_test",    # Tablestore的数据源名称，如果有此项则不再需要配置endpoint，accessId，accessKey和instanceName。
								"column": [    # 按照需求添加需要导出TableStore中的列，您可以自定义设置配置个数。可以是主键或属性列。
									{"name": "page_time"},
									{"name": "docid"},
									{"name": "dochtmlcon"}
								],
								"table": "sys_document_temp",    # TableStore中的表名。
								"startTimeString": "20200325000000",    # 增量数据的时间范围（左闭右开）的左边界，格式为yyyymmddhh24miss，单位毫秒。	// 应该是 (yyyymmddhhmmss) 格式，开始时间不允许小于创建表的时间
								"endTimeString": "20200329000000"    # 增量数据的时间范围（左闭右开）的右边界，格式为yyyymmddhh24miss，单位为毫秒。		// 应该是 (yyyymmddhhmmss) 格式，结束时间不允许大于最后的时间，工作人员说，是要比任务调度小5分钟以上（最好6分钟，但是有时不知6分钟几个小时，要不然会报错）
							},
							"name": "Reader",
							"category": "reader"
						},
						{
							"stepType": "odps",    # Writer插件的名称。
							"parameter": {
								"partition": "",    # 需要写入数据表的分区信息。
								"truncate": false,    # 清理规则，写入前是否清理已有数据。
								"compress": false,    # 是否压缩。
								"datasource": "odps_test_project_cjr",    # 数据源名。
								"column": [    # 需要导入的字段列表。
									"page_time","docid","dochtmlcon"
								],
								"emptyAsNull": false,    # 空字符串是否作为null，默认是。
								"table": "ods_document"    # 表名。
							},
							"name": "Writer",
							"category": "writer"
						}
					],
					"version": "2.0",
					"order": {
						"hops": [
							{
								"from": "Reader",
								"to": "Writer"
							}
						]
					},
					"setting": {
						"errorLimit": {
							"record": "0"    # 允许出错的个数，当错误超过这个数目的时候同步任务会失败。
						},
						"speed": {
							"throttle": false,    #同步速率不限流。
							"concurrent": 2    # 每次同步任务的并发度。
						}
					}
				}
			3、参数描述请参考 (https://help.aliyun.com/knowledge_detail/142875.html#concept-m4h-5gr-p2b)
				1、注意 startTimestampMillis/endTimestampMillis  和 startTimeString/endTimeString 是一样的，是指数据的时间
				2、startTime=$[yyyymmddhh24miss-10/24/60] endTime=$[yyyymmddhh24miss-5/24/60]		// 表示调度开始时间减去10分钟 结束时间减去5分钟
				3、系统参数
					bizdate：获取到业务日期，展示格式为：yyyymmdd；
					cyctime：获取到任务实例的定时时间，格式为：yyyymmddhh24miss；

	11、内置函数
		1、 rand()		// [0.0-1.0) 区间，不包括1.0，java的 int x=(int)(Math.random()*100); 也是如此
		2、 ceil()		// 向上取整
		3、 floor()		// 向下取整
		4、 round()		// round(Double/Decimal number, [Bigint Decimal_places])  number目标值，Decimal_places 小数保留的位数默认为0，负数代表依次类推到整数部分
		5、 trunc()		// 和 round 一致，但是没有四舍五入的效果


5、问题
	1、执行的sql针对的环境
		开发：select table_name 
		生产：select project_name.table_name
	2、maxcompute的UDF里面的代码可以发送http请求吗
		1、可以的但是要申请，目前只支持 北京、上海 两个区域， 并且不允许以内网的形式访问ots和gdb，只能走外网的形式，就算maxCompute 和 ots/gdb 内网互通 也是不行的
		2、但是这个没必要，一般udf是一些算法计算
	3、调度配置
		1、基础属性
		2、时间属性
			1、生成实例的方式 T+1次日生成 什么意思？
		3、调度依赖
		4、节点上下文
	4、从 idea 提交的资源 和 创建函数，是直接在生产环境操作的，但是你可以通过控制台添加开发环境，然后再提交，生产环境才会生效
		1、如果重新提交资源，会影响引用的函数


1、问题
	1、感觉 yyyymmddhh24miss 代表的并不是当前时间，而是 yyyymmdd代表当前日期，而 hh24miss 固定是 00:00:00 
	2、endTimeString 是要比任务调度时间小5分钟以上（最好6分钟，但是有时不知6分钟几个小时，要不然会报错）
	3、生成实例 T+1次日生成 发布后即时生成 什么意思
	4、依赖上一周期，意思是上一周期没完，下一周期不启动吗
	5、提交 和 提交并解锁 发布		要先提交，再发布才会在生产环境上运行
	6、定时任务，最短多久可以启动一次，最小5分钟




