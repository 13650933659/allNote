1、事务：特性Atomic(原子性)、Consistency(一致性)、Isolation(隔离性)、Durability(持久性)
2、事务并发时可能出现的问题：
	1、lost update(丢失更新)，这个只要数据库支持事务就已经解决了	//模拟的结果还是被事务B冲掉了
		事务A -> start -> 取得余额1000                                              -> 再加100=1100 -> 提交
		事务B						    -> start -> 取得余额1000再加100=1100 - 提交
	2、dirty read(脏读)	//算是模拟出来了
		事务A -> start                                       -> 取得余额1100         -> 取出1100
		事务B          -> start -> 取得余额1000再加100=1100                  -> 回滚
	3、non-repeatable read(不可重复读)  //我模拟不出来
		事务A -> start -> 取得余额1000                                              -> 再次取得余额1100 -> 提交
		事务B                          -> start -> 取得余额1000再加100=1100 -> 提交
	4、phantom read(幻读)，我倒觉得这种情况是正常的（这个可以不用管）
		事务A -> start -> 取得十个学生                                  -> 再次取得11个学生 -> 提交
		事务B                          -> start -> 插入一个学生 -> 提交
3、数据库的事务隔离机制
	1、原生JDBC的处理查看 java.sql.Connection 文档
	2、hibernate的处理
		1、去hibernate.cfg.xml配置<property name="hibernate.connection.isolation"></property>
		2、取值1：read-uncommitted  2：read-committed  4：repeatable read  8：serializable，为什么取值要使用 1 2 4 8 而不是 1 2 3 4，因为前者位移计算效率高
			1、read-uncommitted(允许读取未提交的数据) 会出现dirty read, phantom-read,non-repeatable read 问题 
			2、read-commited(读取已提交的数据 项目中一般都使用这个)不会出现dirty read,但仍然会出现 non-repeatable read 和 phantom-read，可用悲观锁 乐观锁来解决
				1、悲观锁(Pessimistic Lock)解决repeatable read的问题（依赖于数据库的锁，表锁还是行锁，其他事务能查不能改）
					1、select ... for update
					2、使用另一种load方法--load(xx.class , i , LockMode.Upgrade)
						1、LockMode.None无锁的机制，Transaction结束时，切换到此模式
						2、LockMode.read在査询的时候hibernate会自动获取锁
						3、LockMode.write insert  update hibernate 会自动获取锁
						4、以上3种锁的模式，是hibernate内部使用的(不需要设)
						5、LockMode.UPGRADE_NOWAIT是 ORACLE 支持的锁的方式
				2、乐观锁(Optimistic Lock)，请参考Hibernate332_0007_OptimisticLock_msb
			3、repeatable read(加锁事务执行中其他事务无法执行修改或插入操作     较安全)
			4、serializable解决一切问题(顺序执行事务 不并发，实际中很少用，各自事物没有访问相同资源也不并发吗？)



		
