





1、表映射： @Table(里面的属性看文档)
2、主键映射
	1、注解方式： @id @GeneratedValue(strategy=填下面四种, generator="sequence生成器名称/也可以填表生成器")
		1、GenerationType.SEQUENCE【oracle】，在实体类上加：SequenceGenerator(name="序列生成器名称",sequenceName="序列名称")，此表就用这个sequence，而不是用全局的hibernate_sequence
		2、GenerationType.IDENTITY【mysql或者sqlServer】
		3、GenerationType.TABLE，要创建一个表的生成器=TableGenerator可跨平台（看马士兵23讲）
		4、GenerationType.AOTO，自适应（默认，这个好用）
3、普通字段映射
	1、注解的方式
		1、默认会给普通字段的get上 @basic，如果想控制此字段就要换成 @colum(这里面的属性看文档可以指定长度约束等)
		2、这里给个：列的类型是java.util.Date类型时，你想控制到（日期，时间，日期时间默认）@Temporal(控制到日期的存储格式看文档)
			



