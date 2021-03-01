













1、RedisTemplate<String, Object> redisTemplate;

	1、 存对象和字符
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		
		// 缓存60s
		operations.set(key, value);
        redisTemplate.expire(key, 60, TimeUnit.SECONDS);
		
		// 获取缓存
		Object value = operations.get(key);

	2、	存数组
		ListOperations<String, Object> operations = redisTemplate.opsForList();
		operations.rightPushAll(key, data.toArray());		// 要转成数组，要不然会出问题？
		redisTemplate.expire(key, expire, timeUnit);

		// 获取
		Long total = operations.size(key);		// 获取数量
		List<?> objectList = operations.range(key, pageStartIndex, pageEndIndex);	// 分页获取，但是全取呢？






