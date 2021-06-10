






1、Pattern.matches(pattern, content) 匹配
	String content = "I am noob from runoob.com.";
	String pattern = ".*runoob.*";
	boolean isMatch = Pattern.matches(pattern, content);
	System.out.println("字符串中是否包含了 'runoob' 子字符串? " + isMatch);




2、捕获组
	1、说明
		捕获组是把多个字符当一个单独单元进行处理的方法，它通过对括号内的字符分组来创建。
		例如
			(dog)			==>> (dog)/(d)/(o)/(g)	四个组
			((A)(B(C)))		==>> ((A)(B(C)))/(A)/(B(C))/(C) 四个组
		可以通过调用 matcher.groupCount 方法来查看表达式有多少个分组。还有一个特殊的组 group(0)，它总是代表整个表达式。该组不包括在 groupCount 的返回值中。
	2、代码示例
		// 创建 Pattern 对象
		String p = "(\\D*)(\\d+)(.*)";
		Pattern pattern = Pattern.compile();

		// 现在创建 matcher 对象
		String str = "This order was placed for QT3000! OK?";
		Matcher m = pattern.matcher();
		if (m.find()) {		// 如果在 str 没有在p的整个表达式匹配到，就返回false
			System.out.println("Found value: " + m.group(0) );		// Found value: This order was placed for QT3000! OK?	， 这个和 m.group() 一样
			System.out.println("Found value: " + m.group(1) );		// Found value: This order was placed for QT
			System.out.println("Found value: " + m.group(2) );		// Found value: 3000
			System.out.println("Found value: " + m.group(3) );		// Found value: ! OK?
		} else {
			System.out.println("NO MATCH");
		}

http://www.bidizhaobiao.com/excel_detail.do?code=a9ac2bd5d593adc69aae0c2b72c8f3e504e65c17735138e7858f9b1e4016a664


3、问题
	1、 (?<replace>第二名)		// 就是 第二名 而 ?<replace> 就是标签相当于注释
	2、() 和 [] 的区别
		"^.*(df|e|f).*$"	// 匹配 df/e/f 一个即可
		"^.*[df|e|f].*$"	// []不是这么用的， 正确用法 [dfe] 即 d/f/e 任意一个字都可以 

