

1、 com.alibaba.fastjson
	1、安装
		<!-- fastjson（ali的json工具） -->
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>fastjson</artifactId>
			<version>1.2.39</version>
		</dependency>

	1、序列化
		1、 字符串转成 JSONObject
			JSONObject object = JSON.parseObject(result);
				String name = object.getString("name");			// 没有 name 则为空
				Boolean flag = object.getBoolean("success");    // 没有 success 则为空，如果是直接小写Boolean的话，就会报空指针
				Object e1 = obj.getOrDefault("username", "default");	// 如果没有 username 就使用默认值，注意：如果 username 为空的话，是不会用默认值的，注意空指针
			JSONArray jsonArray = JSON.parseArray("");
		2、 java对象转成 JSONObject
			JSONObject jsonObject = (JSONObject)JSON.toJSON(obj);		// 当你使用 jsonObject.toJSONString()或者toString 默认会把空的字段屏蔽，但是你遍历jsonObject时是有空字段的，
			JSON.toJSON(Object javaObject, SerializeConfig config)		// 这个是序列号配置，可以配置例如时间格式，空字段是否打印....
	2、反序列化
		1、JSONObject -> JavaObject
			Organization organization = JSON.toJavaObject(JSONObject, Organization.class);
		2、JSON字符串 -> JavaObject		// 驼峰和下划线都可以自动映射成java的驼峰法字段(aa -> aa 或者 aaBB/aa_bb -> aaBB 或者 aaBB/aa_bb -> aa_bb)
			Organization organization = JSON.parseObject(obj.toJSONString(), Organization.class);
	3、转json字符串(如需用到SerializeConfig全局一个即可)
		SerializeConfig serializeConfig = new SerializeConfig();
		serializeConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;		// 配置驼峰转下划线
		String text = JSON.toJSONString(value, serializeConfig);



广东马可波罗陶瓷有限公司
蒙娜丽莎集团股份有限公司