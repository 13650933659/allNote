













1、线程
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





