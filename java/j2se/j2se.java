


1、杂项
	1、单位转换：8bit(256种) = 1byte   1024byte = 1KB
	2、转化原则：低精度到高精度《byte<short<int<long<float<double》
1、java是什么
	1、Java是一种面向对象的编程语言、具有跨平台、多线程、GC智能回收机制
2、基本数据类型(四类八种)
	1、整型
		byte:-128~127《占1个字节》
		short:-32768~32768《占2个字节》
		int:-2147483648~2147483647《占4个字节》
		long:-?~? 《占8个字节》
	2、小数
		float:《定义时：float f=4.9f》
		double:
	3、字符
		char:《定义：char c=’中/a’》：如果采用”ISO-8859-1”编码，那么一个char只会有一个字节。如果采用”UTF-8”或者“GB2312”、“GBK”是动态长度，英文都是一个字节。中文，”UTF-8”是三个字节，而”GBK”和”GB2312”是两个字节。而”unicode”都是两个字节。 注意UTF-8是unicode的一种实现
		string: 一个中文等于(通常gbk/gb2312是2个字节，utf-8是3个字节)，一个英文等于 = 1字节
	4、布尔
		boolean:

2、运算符
	1、&&和&：两者都是与的关系(即两个条件都要符合)，前者只要第一个不符合第二个条件就不会判断，后者无论如何两者都会判断
	2、||和|：两者都是或的关系(即两个条件随便一条)，前者只要第一个符合第二个条件就不会判断，后者无论如何两者都会判断
3、数组和集合(数组排序算法看代码)
	1、数组：Student[] arr = new Student[];
	2、集合类
		1、总:1：136
			1、1个类：java.uitl.Collections（对java.util.Collection的排序）
			2、3个知识点
				增强的for循环
				泛型
				自动解包，自动拆包
			3、6个接口：
				java.util.Collection：Set和List两个子接口
				java.util.Set：HashSet一个实现类
				java.util.List：ArrayList和LinkedList两个实现类
				java.util.Map：key-value对
				java.util.Iterator：统一的对java.util.Collection的遍历，会锁住正在操作的元素，导致Collection无法对自己的元素进行操作
				java.lang.Comparable：定义两个对象的比较方式(Comparable和Comparator都可以用来做比较)
		2、总结2：
			Array读快改慢
			Linked改快读慢
			Hash两者之间
4、面向对象编程(OOP)
	1、类：
		1、构造方法：初始化属性，实例化子类时会自动调父类的无参构造方法，如果没有，子类有没有显示调用父类构造方法，就会会报错，不能无本之木，调用普通方法用super.fun()
	2、四大访问修饰符：
		private		同类
		不写		同类，同包
		protected	同类，同包，子类
		public		同类，同包，子类，不同包
	3、三大特性
		1、封装：把一类事物提取封装起来。
		2、继承：减少代码冗余，（一个类只能继承一个父类可以实现多个接口，但是接口可以多层继承，c++的类可以继承多个这样不好）
		3、多态：继承体现多态，接口实现多态，就像动态绑定，(1.继承或者实现 2.子类重写父类的方法 3.接口或者父类引用指向某个子类)
	4、抽象类和接口
		1、抽象类不可实例/可以有abstract的抽象方法也，可以有普通方法
		2、接口不可实例，方法默认就是public abstract，属性默认是final static String name，他们也只能是这样的修饰
	5、final修饰符
		1、为了安全，用final修饰的类不可继承，修饰方法不可重写，修饰属不可修改
	6、总结：
		1、看内存图（stack，heap，静态区=字符串常量，代码区）
5、异常处理：
	1、理解五个关键字（try,catch,finally,throw,throws）
	2、Throwable有两个子类Error,Exception，前者我们处理不了，后者(一定要处理的和RuntimeException)，处理的方式throw和throws
6、多线程
	1、线程\进程：线程就是一个应用程序内部的顺序控制流(比如就java来说int a=1+2;int b=3+4;cpu会自上而下的执行)，
		进程就像一个大型的线程，一个应用程序运行，随之产生进程，由此进程会创建多条线程，并行执行，各线程的执行顺序不一定按照你代码启动的顺序，而是由操作系统决定他们的执行顺序和执行时间片
	2、线程安全
		1、各线程之间有可能同时访问同一个资源，这时就有可能出现数据安全问题(比如一个售票系统，此系统刚好剩下最后一张ticket，这时线程1,2同时访问都得到剩余票数为1，结果1先提交，随后2也提交，这时2提交就会出现各种问题了)
		2、同步访问某个类的属性，要在所有访问此属性的方法加上synchronized(注意要在此类的方法才有效，在其他类上的方法是无效的)
	3、创建启动线程两种方法：选用接口，不要Thread继承，因为后者不灵活
		1、实现Runnable接口
			class MyRunnable implements Runnable{
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());
				}
			}
			
			Thread myThread = new Thread(new MyRunnable());
			myThread.setName("t2");
			myThread.start();
		2、继承Thread，其实他也实现了Runnable接口看如下代码
			class MyThread extends Thread {
				@Override
				public void run() {
					System.out.println(this.getName());
				}
			}
			
			MyThread myThread = new MyThread();
			myThread.setName("t1");
			myThread.start();
	4、创建固定的线程池：ExecutorService s = Executors.newFixedThreadPool(10);	// 并发10条，但是可以放入10+线程，10+以后的线程就可以等待其他线程运行完成才可以执行
		1、线程池的好处 与 问题
			1、好处
				1、管理线程
				2、线程的复用
			2、问题
				1、如果某条线程卡住了，怎么办？（如果给每条线程加超时时间）
		2、同时执行4个线程
			1、先把我们的线程加一句CyclicBarrier.await()，然后submit给线程池
			2、先把我们的线程加一句 countDownLatch.wait()，然后submit给线程池，然后执行countDownLatch.down();	// 参考 https://zapldy.iteye.com/blog/746458
				CountDownLatch countDownLatch = new CountDownLatch(1);
				for(4){submit()}
				countDownLatch.down();	// down 之后countDownLatch就会减1，然后线程池里面的线程就可以并发了
		3、终止线程池里边有某个线程(超时、异常)，注意 submit()一定要传 Callable.call ，如果传 Runnable 调用 task.get()是没有返回值的
			public static void main(String[] arr) {
				ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);

				List<Future<String>> list = new LinkedList<>();
				for (int i = 0; i < 10; i++) {
					Future<String> future = fixedThreadPool.submit(() -> {
						Thread.sleep(2000);
						return "我是" + getCount();
					});
					list.add(future);
				}

				// 每一条线程的超时时间为1s
				for (Future<String> f : list) {
					try {
						System.out.print("开始取返回值：");
						String s = f.get(1, TimeUnit.SECONDS);	
						// 此方法会阻塞到，任何一条线程执行完毕，否则的话，抛异常，然而 catch 中的 f.cancel(true); 会随机的调用活跃的某一条线程，此方法还不如使用，线程池整体超时限制
							/*
							try {
								if (!fixedThreadPool.awaitTermination(600, TimeUnit.SECONDS)) {  // 最多阻塞等待600秒，否则超时
									log.warn("运行时间超过600秒，线程池将关闭！");
									for (Future<?> task : tasks) {
										if (!task.isDone()) {
											task.cancel(true);
										}
									}
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							} finally {
								fixedThreadPool.shutdownNow();
							}
							*/
						System.out.println(s);
					} catch (Exception e) {
						f.cancel(true);		// 这个会触发线程中断，注意：如果线程里边有处理中断异常的话，线程后续代码还是会继续跑的，慎处理
						System.out.println("超时");
					}
				}


				// 由于cpu核数有限，调用shutdownNow也不一定马上终止所有线程，所以要while小等待一下，真的所有线程停止之后isTerminated才等于true
				fixedThreadPool.shutdownNow();
				while (!fixedThreadPool.isTerminated());
				System.out.println("定时器结束...");
			}
		4、线程中断的知识		// 下面三个方法的区别参考 https://www.cnblogs.com/w-wfy/p/6414801.html
			1、 Thread.currentThread().interrupt();			// 中断(Thread.currentThread())线程
				1、使线程的状态为将被置为"中断"状态。不是终止
				2、你可以使用 Thread.currentThread().isInterrupted(); 方法监视，中断了此方法返回 true
				3、你也可以使用那些抛 InterruptedException 来监视，一旦中断，此方法立即抛 InterruptedException 异常
			2、 Thread.currentThread().isInterrupted();		
				// 判断(Thread.currentThread())线程是否中断，使用这个方法就可以，比如 fixedThreadPool.shutdownNow() 会导致调用活跃的线程的 interrupt() 方法
				// 这时就可以使用此方法，做一些善后处理了
			3、 Thread.interrupted();						// 判断当前线程是否中断，并且取消中断状态
			
			4、 fixedThreadPool.shutdown() 和 fixedThreadPool.shutdownNow()		的比较
				1、 shutdown()   // (阻塞方法)会等到 活跃的线程和等待的线程全部执行完才会关闭线程池
				2、 shutdownNow	 // 会立即关闭线程池(但是也是稍微的延迟一小会，但是可以忽略)，会调用活跃的线程的 interrupt() 方法，等到的线程就不启动了

			1、 问题
				1、 主线程被kill了，那么子线程应该也会被kill，比如tomcat被kill 那么那些子线程也会被kill(好像之前学linux时是这样的，有空去验证一下)
				2、 能设置线程池里面的每一条线程的超时时间吗？

7、IO流看代码(看代码)
8、 获取路径的问题
	this.getClass().getResource("/").getPath()	// 获取类的根路径
	this.getClass().getResource("").getPath()	// 获取当前类的路径
	ClassLoader.getSystemResources("")			// 获取类的根路径 和 this.getClass().getResource("/").getPath() 一样（但是 jar包的话用 ClassLoader.getSystemResources("") 才能取到路径）
	Thread.currentThread().getContextClassLoader().getResourceAsStream(configFileName);		// ClassLoader.getSystemResources("") 和一样
8、二进制运算(以后再去学吧，等学到数据类型时再学)
	1、记住七句话：
		1、二进制的最高位是符号为：0表示正数，1表示负数。
		2、正数的的原码=反码=补码
		3、负数的反码=他的原码符号位不变，其他取反。
		4、负数的补码=他的反码+1
		5、0的反码=补码=0
		6、Java和php中没有无符号数，换言之php中的数都是有符号的。
		7、在运算中，都是以补码的方式来运算的。//这句话一定要理解好（如果是负数就要换成补码，正数就不用因为第4点）
	2、六种运算：四种位运算，两种位移运算
		1、按位与（&）：两者全部为1结果为1.
		2、按位或（|）:两个一个为1，结果为1.
		3、按位异或（^）：一个为0一个为1，结果为1.
		4、按位取反（~）：{0—>1,10}
		5、算术右移（>>）:符号为不变，低位溢出，并用符号位补溢出高位。右移一次实际除2
		6、算术左移（<<）:符号位不变，低位补0。左移表示乘以2
	3、例子：
		1、案例1：~2=？
			2的原码：00000000 00000000 00000000 00000010
			~2的码： 11111111 11111111 11111111 11111101  要换成原码
			~2反码： 11111111 11111111 11111111 11111100  补码-1=反码
			~2原码： 10000000 00000000 00000000 00000011  - 反码=原码结果= - 3
			总结：~2 = - 3.  运算得出来的只是补码。正数：原码=反码=补码
		2、案例2：1>>2=?
			1的原码：00000000 00000000 00000000 00000001
			1>>2:    00000000 00000000 00000000 00000000  所以结果等于0
		3、案例3：13的二进制如下：
			13/2=6余1  ，6/2=3余0  ，3/2=1余1  ，1/2=0余1
			所以：13的二进制=1101
9、网络编程(以后再去，理解下面的三个协议(他是j2ee的底层))
	1、ip(internet protocol)
	2、tcp(transmission control protocol)：可靠但是慢于udp
	3、udp(user data protocol)：不可靠但是快
10、图形用户界面（gui）编程-这个暂时不管












