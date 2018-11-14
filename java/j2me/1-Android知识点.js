
1、Android开发环境准备
	1、用Eclipse做为IDE
		1、安装jdk1.6以上
		2、安装eclipse
		3、下载安装SDK（即：software development kit需要翻墙，他会自动帮我们下载Android开发需要的东西）=API
		4、下载安装ADT（即：Android Development Tools，是eclipse开发Android的一款插件，安装方式有两种1、把ADT下载到本地安装 2、通过给定的地址在线安装，需要翻墙）--google不再支持了
			4.1、这时eclipse就有一个Android的一个选项，要配置SDK的路径
			4.2、创建虚拟设备AVD（即：android virtual device安卓虚拟设备）  //要不要都可以，因为我可以直接连接手机测试
		6、创建android项目
		7、启用DDMS视图界面全称是Dalvik Debug Monitor Service，是 Android 开发环境中的Dalvik虚拟机调试监控服务


	2、用Android Studio为IDE
		1、安装JDK(不需要安装java_home没问题)
		2、安装Android Studio
			1、SDK
			2、AVD-工作上我们不用，因为他慢，一般我们用真机或者Gynemotion第三方虚拟设备
			3、HAXM：Hardware Accelerated Execution Manager 硬件加速过滤器（实际加速效果很差）
			4、Android Studio常用的快捷键
				1、Ctrl+Alt+B			查看一个接口、类、被继承或者被实现的情况
				2、Ctrl+B				查看一个接口、类、方法、变量在整个工程中的使用情况
				3、Ctrl+Alt+H			查看一个方法在整个工程中的使用情况(有 Hierarchy)
				4、Ctrl+Shift+N			查找文件
				5、shift+F6				重构
				7、Alt+Insert			生成代码(如get,set方法,构造函数等) 
				8、Ctrl+Alt+T			选中一块代码，按此组合键，可快速添加if 、for、try/catch等语句。
				9、Ctrl+Q				查看方法的说明文档
				10、Ctrl+R				替换文本 
				11、Ctrl+E				最近打开的文件
				12、Ctrl+Alt+shift+N	快速打开输入的方法或变量(整个工程以后看看js代码能不能支持)
				13、Ctrl+F12			在本类中查找变量或者方法
				14、F4					本类的继承关系

	3、真机调试
		1、不需需要360等工具->开启开发者模式->启用USB调试->最好手机有root权限

1、android四大控件（用Intent来通讯）
	1、Activity：就是一个屏幕界面
		1、AndroidManifest.xml就像javaweb的web.xml,Activity就像Servlet，也需要在AndroidManifest.xml注册
		2、Activity生命周期
			onCreate()				//完成初始化工作和，按钮的事件绑定   
			onRestart()				//被覆盖不可见的A重新出现时触发（返回/直接到主页->听歌->回来会执行吗）  
			onStart()				//这个方法好像不怎么有用
			onResume()				//当A被覆盖可见时触发（对话框）
			onSaveInstanceState()	//可能在onPause之前之后触发（被系统杀死后面三个方法则他就有价值了）
			onPause()				//发生交互时触发
			onStop()				//当A被覆盖不可见时触发
			onDestroy()				//当A没用时（返回）

	2、Service：是一个应用程序组件，它可以在后台执行长期运行的操作，而不提供用户界面。
		1、如何使用service
			1、在manifest文件中声明服务：<service android:name=".Service"/>
			2、如何启动服务
				1、startService：	与调用者没有联系，即使调用者退出了，服务仍然进行 [onCreate-->onStart(过时了用onStartCommand替代)-->startService-onDestory]
				2、bindService：	与调用者绑在一起，调用者一旦退出服务也就终止[onCreate-->onBind-->onUnbind-->onDestory]
			3、如何关闭服务
				1、使用stopService方法关闭
				2、得到服务进程id把他关闭
		2、onStartCommand方法的返回值
			1、START_STICKY：			当服务进行在运行时被杀死，系统将会把它值为started状态，但是并不保存其传递的Intent对象
			2、START_NOT_STICKY：		当服务进行在运行时被杀死，并且没有新的Intent对象传递过来，统将会把它值为started状态，但是并不会再次创建进程，直到startService(Intent)方法被调用。
			3、START_REDELIVER_INTENT：	当服务进行在运行时被杀死，它将会间隔一段时间后重新被创建，并且最后一个传递的Intent对象将会再次传递过来。
		3、解决耗时的操作的2种方法
			1、每一次是一个新的线程
			2、继承IntentService并且重写构造方法和onHandleIntent方法，原理是放全部的intent对象放在一个工作队列里面，其实还是同一个线程，但不是主线程
		4、消息的处理
			1、每一个线程只可以拥有一个MessageQueue。在创建Looper对象会创建一个MessageQueue对象
			2、Message.obtain()或者Handler.obtainMessage()获取Message对象。他先从"消息池"找Message实例，存在则直接取出用之，没有则创建一个新的Message对象。调用removeMessages()时，将Message从MessageQueue中删除，同时放入到"消息池"中。
			3、操作MessageQueue：通过调用Looper.myLooper()或者Looper.getMainLooper()可以获得当前线程的Looper对象。Looper从MessageQueue中取出Message然后，交由Handler的handleMessage()进行处理。处理完成后，调用Message.recycle()将其放入"消息池"中。
			4、消息的处理者：handler负责将需要传递的信息封装成 Message对象，然后调用sendMessage()方法将消息放入MessageQueue中。当MessageQueue循环到该Message，调用相应的handler对象的handleMessage()方法对其进行处理。Handler都可以共享同一Looper和MessageQueue
	3、broadcast receiver(广播接收者)
		1、广播接收者
			1、在xml文件注册的广播接收者，则每次接收都会创建一个新的接收对象
			2、在java文件注册的广播接收者，则每次接收都同一个接收对象
	4、ContextProvider（内容提供者）
		1、他可以在sqlite操作数据，通过他可以看其他程序的数据，访问方法（我们要继承ContentProvider重写增删改查等方法，然后其他程序通过我们指定的url来进行调用）

2、其他知识
	1、总知识
		1、DDMS视图：可以看到已知的设备的一些运行状态
		2、Intent：一个Intent就是一次对将要执行的操作的抽象描述
		3、Log类：可以打印日志：Log.v("tab", "冗余信息");Log.d("tab", "调试信息");Log.i("tab", "静态信息");Log.w("tab", "警告信息");Log.e("tab", "错误信息");
		4、如果是宽高用dp=dip或者sp如果是字体用sp，dp和密度无关、sp和密度比例都无关，在160时1px=1dp=1sp，但是大于160时1px=1*实例宽度/160
		5、使用DDMS的emulator control视图来和我们的AVD来通讯（打电话和发短信），还有File Explorer视图设备和explise的一些文件互传，logcat视图
	2、控件
		1、TextView、EditText、Button、ImageView、RadioButton-CheckBox
		6、日期、时间控件看33讲
		7、ListView和GridView、TabWidget、Spinner、Auto.Complete看34-37
	3、布局（5种）
		1、LinearLayout：	线性布局可以分为水平线性布局和垂直线性布局两种android:orientation设置
		2、AbsoluteLayout：	绝对布局用的少，因为他不灵活不会自适应手机屏幕(不推荐使用)
		3、FrameLayout：	框架布局，他说是堆栈一样会覆盖的最后的在最前面
		4、RelativeLayout：	相对布局
		5、TableLayout：	表格布局
	4、数据存储(如果想看Android Junit 测试请看若水23讲 28:00)
		1、Android自带的sqlite数据库
		2、文件IO流操作
		3、SharedPreferences，对应的xml文件保存在"包/shared_prefs"目录下面，注意一点的是，需要调用commit方法
	5、xml解析器
		1、SAX解析器主要掌握着5个方法
			startDocument：	当遇到文档的开头时候，调用这个方法，可以在该方法中进行预处理工作
			endDocument：	当文档结束的时候，调用这个方法，可以在该方法中进行善后工作
			startElement：	当读到一个开始标签的时候，会触发该方法
			endElement：	当遇到结束标签的时候，会调用该方法
			characters：	该方法用来处理在XML文件中读到的内容（请注意，回车和Tab建连在一起时他会执行两次）
		2、DOM解析器：主要看看readXml方法的代码就知道了
		3、Pull解析器主要掌握eventType的0、1、2、3、4代表什么即可
			0 文档开始、1 文档结束、2 元素开始，此时可以使用parser.nextText()拿到文本，但是下一次的读取文本事件就不会触发了、3 元素结束、4 文本
	6、什么情况会产生Log文件，一般在data\log下面，到时我们程序奔溃了，我们可以通过它排查
		1、ANR(程序无响应 Application No Response)
		2、FC(程序强制关闭Force Closed)
		3、程序异常退出
		2、压力测试猴子
	7、Android五大进程
		1、Foreground Process(前台进程)
		2、Visible Process(可见进程，和前台进程差不多)
		3、Service Process(服务进程)
		4、Background Process(后台进程)
		5、Empty Process(空进程，为了缓存用的)
	8、系统启动时PackagedManageService读取AndroidManifest信息，应用启动时，Launcher进行系统级判断，比如最小sdk等

问题：
	1、sqlite3 data.db(打开数据库)怎么用adb shell查看之，还有一个办法就是用手机助手，去那个路径把数据库文件拿出来查看
	2、不同的activity可以相同的id吗，可以的，但是同一个不行
