
1、ignorance width property		//忽略宽度属性，可以用做自定义报表
2、ignorance pagination			//忽略分页


4、属性->text field->pattern	//这里可以编辑数据模式，时间、保留小数位...
5、属性->text field properties  //这里的数据类型和fields那里的不一样的
6、要分页建议使用：高度33px/12字体大小 高度38px/12字体大小
7、查出来的数据有可能为null,两种解决方案（1-修改可能为null的数据把他转为但是所有数据库要通用，2-在ireport写表达式,但是这一种不会计入总数的）
8、汇总时保留小数位为2时，会出现少了0.01
9、交叉报表的开发
	1、注意：行分组、列分组
10、父子报表的开发
	1、subreport expression=$P{SUBREPORT_DIR} + "tousseWorkLoad_child.jasper"
	2、data source=new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource($F{childrens})
	3、connection type=use a datasource expression
	4、如果需要使用子报表的返回值，去return values 配置，但是你可能需要复制粘贴

合计









JasperPrint jsPrint = jasperReportManager.getJasperPrint(realPath, map);
JasperPrint jsPrint = JasperFillManager.fillReport(jasperReport, parametMap, dataSource);













