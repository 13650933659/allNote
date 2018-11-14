
1、得到xml文件的根目录元素节点：然后你想干嘛就干嘛
	//套路
	org.dom4j.io.SAXReader sAXReader=new org.dom4j.io.SAXReader();
	org.dom4j.Document document=sAXReader.read(new java.io.File("D:/pennyengin/5/5-27/chapter1_1.xml"));

	//得到文件的_row下的(_e和_g)
	java.util.List<org.dom4j.Element> _rowElements=document.getRootElement();
		

2、保存并且关闭：注意乱码
	//保存
	org.dom4j.io.OutputFormat outputFormat=org.dom4j.io.OutputFormat.createPrettyPrint();
	outputFormat.setEncoding("utf-8");
	
	org.dom4j.io.XMLWriter xMLWriter=new org.dom4j.io.XMLWriter(new java.io.FileOutputStream(new java.io.File("src/classes.xml")),outputFormat);
	xMLWriter.write(document);
	xMLWriter.close();