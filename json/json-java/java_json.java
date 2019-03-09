





















1、json工具的选用请参考：https://blog.csdn.net/vickyway/article/details/48157819
	1、我这边使用com.alibaba.fastjson.JSONObject	// 可以参考 https://blog.csdn.net/qq_36688143/article/details/79791544
		1、序列化
			1、转换的代码
				1、String s1 = JSON.toJSONString(vo);											// 如果属性为空则不显示
				2、String s2 = JSON.toJSONString(vo, SerializerFeature.WriteMapNullValue);	// 如果属性为空也会显示，值为null，如果还有其他需求(比如null显示"")也可以继续加SerializerFeature，他是数据
			2、数据格式转换
				1、日期转换处理：在日期属性加上注解@JSONField(format = DateUtil.YYYYMMDDHHMM)
		2、反序列化
			1、转换代码
				User user1 = JSON.parseObject(userJson, User.class);
			2、数据格式处理
				日期格式：自动识别，但是2019-01不能写成2019-1

	2、 com.google.gson.JsonObject 工具
		1、序列化和反序列化请参考：https://blog.csdn.net/qq_32938483/article/details/78878494
		1、序列化
			1、 转换的代码
				String jsonStr = new Gson().toJson(vo);		// 如果属性为空则不显示，他会把<>特殊字符改成其他字符，但是他在反序列化时会转换，fastjson则不会
			2、返序列化
				UserInfo u = new Gson().fromJson(jsonStr, UserInfo.class);	// 1、jsonStr=null 或者 ""时u=null， 2、jsonStr=不是json字符串会报错 3、json=json字符串解析成功
			

















		