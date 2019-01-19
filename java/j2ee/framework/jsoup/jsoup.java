
























1、jsoup的依赖
	1、httpclient依赖
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
			<version>${httpclient.version}</version>
		</dependency>
	2、jsoup依赖
		<dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>${jsoup.version}</version>
        </dependency>

2、操作
	1、一般先使用httpClient获取网页的html
		CloseableHttpClient httpclient = HttpClients.createDefault(); // 创建httpclient实例
        HttpGet httpget = new HttpGet("http://www.cnblogs.com/"); // 创建httpget实例
        CloseableHttpResponse response = httpclient.execute(httpget); // 执行get请求
        HttpEntity entity=response.getEntity(); // 获取返回实体
        String content=EntityUtils.toString(entity, "utf-8");
        response.close(); // 关闭流和释放系统资源
	2、解析
		Document doc=Jsoup.parse(content); // 解析网页 得到文档对象
		

		1、获取元素
			Elements elements=doc.getElementsByTag("title"); // 获取tag是title的所有DOM元素，是一个Element数组，可以使用first/last等方法取对应的元素
			Element element=doc.getElementById("site_nav_top"); // 获取id=site_nav_top的DOM元素
			Elements elements = doc.getElementsByClass("item");// 根据样式名称为item查找
			Elements elements = doc.getElementsByAttribute("width");     // 有width属性的元素
			Elements elements = doc.getElementsByAttributeValue("width", "10px");               // 根据属性名=属性值查找

		2、css样式选择器和jq的选择器(这些选择器语法有空去研究)
			Elements elements = doc.select("#list .item .item_body h3 a");  // 获取id=list -> class=item -> class=item_body -> h3标签 -> a标签
			Elements imgElements=doc.select("img[src$=g]");					// img标签 并且 src属性值为g结尾的
		
		2、Element的方法
			String s = e.data(); // 返回元素的所有文本(是脚本标签才有内容的，也可以是里面的脚本，好像就只script标签，以后再去看)
			String s = e.text(); // 返回元素的所有文本(包括子节点的所有文本)
			String s = e.attr("width");		// 获取width属性的值
			Element width1 = e.attr("width", "10px");	// 设置width属性的值，有则会覆盖
			Element e = e.html("<div>" + e.html() + "</div>");	// 无参数的是取html返回字符串，有参数是设置返回此e
			Node width = bodyEle.removeAttr("width");		// 删除width属性，并且返回此节点
        
		
       
        

















