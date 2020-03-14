

1、 com.alibaba.fastjson
	1、 字符串转成 JSONObject
		JSONObject object = JSON.parseObject(result);
			String name = object.getString("name");			// 没有 name 则为空
			Boolean flag = object.getBoolean("success");    // 没有 success 则为空，如果是直接小写Boolean的话，就会报空指针
		JSONArray jsonArray = JSON.parseArray("");
	2、 java对象转成 JSONObject
		public static Object toJSON(Object javaObject)
		public static Object toJSON(Object javaObject, SerializeConfig config)





