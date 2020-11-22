


1、redisson
	1、redisson是redis的客户端
	2、redisson的分布式锁：他的锁是控制一段代码
		1、首先，为了确保分布式锁可用，我们至少要确保锁的实现同时满足以下四个条件：
			1、互斥性			// 在任意时刻，只有一个客户端能持有锁。
			2、不会发生死锁		// 即使有一个客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁。例如使用 lock.lock(1, TimeUnit.SECONDS); 就会可能死锁
			3、具有容错性		// 只要大部分的Redis节点正常运行，客户端就可以加锁和解锁。
			4、解铃还须系铃人	// 加锁和解锁必须是同一个客户端，客户端自己不能把别人加的锁给解了
		2、使用
			1、配置自己查，然后先spring容器注入RedissonClient
			2、代码使用（1、2秒自动解锁 2、redisson已经考虑到了解铃还须系铃人）
				int[] arr = {1,2,3,4,5};
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
				List<Future<?>> tasks = new ArrayList<>();
				for (int i : arr) {
					Future<?> task = fixedThreadPool.submit(() -> {
						Thread.currentThread().setName("线程-" + i);
						RLock rLock = redisson.getLock("testRedissonCjr");
						try {
							if (rLock.tryLock(20, 30, TimeUnit.SECONDS)) {	// 阻塞等待，最多等20秒，获取锁后30秒自动释放
								log.warn("线程名称{}，获取锁成功，开始工作...", Thread.currentThread().getName());
								Thread.sleep(5000);
								log.warn("线程名称{}，结束工作...", Thread.currentThread().getName());
							} else {
								log.warn("线程名称{}，获取锁超时", Thread.currentThread().getName());	// 可以在次回调此方法
							}
						} catch (Exception e) {
							e.printStackTrace();
						}  finally {
							if (rLock != null && rLock.isLocked()) {
								rLock.unlock();
								log.warn("线程名称{}，成功释放锁...", Thread.currentThread().getName());
							}
						}
					});
					tasks.add(task);
				}






$('#deleteItemBatch').click(function(e){
		var _deleteListContent = $('#deleteListContent');
		var chkList = $(':checkbox[name="chkone"]:checked');
		if(chkList.length == 0 ){
			ealert('没有操作的记录');
			return false;
		}
		if(econfirm('确定要删除这'+chkList.length+'条记录吗')){
			_deleteListContent.submit();
		}
	});






