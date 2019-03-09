
log4j(这个不管了) -> log4j2 -> logback

1、日志等级
	off		// 关掉所有=fatal
	fatal	// 致命信息
	error	// 错误信息
	warn	// 警告信息
	info	// 正常信息
	debug	// 调试信息
	trace	// 这个好像是slf4j才有的
	all		// 最底下程度=debug

2、配置语法
	1、log4j2的xml配置	// 这个抽空再去研究
		<RollingFile name="RollingFileInfo" fileName="${baseDir}/@project.artifactId@-info.log"
                     filePattern="${baseDir}/$${date:yyyy-MM-dd}/@project.artifactId@-%d{yyyy-MM-dd-HH}-info-%i.log.gz">	// 如果同名会%i就有用了，但是最多只能到7，你可以使用<DefaultRolloverStrategy max="20"/>改为20个


		<!--控制台只输出level及以上级别的信息（onMatch），其他的直接拒绝（onMismatch）-->   // 还是说 单单输出info这一个级别的日志   
        <ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>

	

	3、其他详解请看：https://blog.csdn.net/zheng0518/article/details/69558893
	4、最优配置：https://blog.csdn.net/fujaja/article/details/81530284


	1、配置详解
		1、根节点Configuration有两个属性
			status用来指定log4j本身的打印日志的级别.
			monitorinterval用于指定log4j自动重新配置的监测间隔时间，单位是s,最小是5s.
		2、Appenders节点，常见的有三种子节点:Console、RollingFile、File.
			Console节点用来定义输出到控制台的Appender.
			File节点用来定义输出到指定位置的文件的Appender.
			RollingFile节点用来定义超过指定大小自动删除旧的创建新的的Appender.
				name:指定Appender的名字.
				fileName:指定输出日志的目的文件带全路径的文件名.
				PatternLayout:输出格式，不设置默认为:%m%n.
				filePattern:指定新建日志文件的名称格式.
				Policies:指定滚动日志的策略，就是什么时候进行新建日志文件输出日志.
					TimeBasedTriggeringPolicy:Policies子节点，基于时间的滚动策略，interval属性用来指定多久滚动一次，默认是1 hour。modulate=true用来调整时间：比如现在是早上3am，interval是4，那么第一次滚动是在4am，接着是8am，12am...而不是7am.
					SizeBasedTriggeringPolicy:Policies子节点，基于指定文件大小的滚动策略，size属性用来定义每个日志文件的大小.
				DefaultRolloverStrategy:用来指定同一个文件夹下最多有几个日志文件时开始删除最旧的，创建新的(通过max属性)。
		3、Loggers节点，常见的有两种:Root和Logger.
			Root节点用来指定项目的根日志，如果没有单独指定Logger，那么就会默认使用该Root日志输出
				level:日志输出级别，共有8个级别，按照从低到高为：All < Trace < Debug < Info < Warn < Error < Fatal < OFF.
				AppenderRef：Root的子节点，用来指定该日志输出到哪个Appender.
			Logger节点用来单独指定日志的形式，比如要为指定包下的class指定不同的日志级别等。
				level:日志输出级别，共有8个级别，按照从低到高为：All < Trace < Debug < Info < Warn < Error < Fatal < OFF.
				name:用来指定该Logger所适用的类或者类所在的包全路径,继承自Root节点.
				AppenderRef：Logger的子节点，用来指定该日志输出到哪个Appender,如果没有指定，就会默认继承自Root.如果指定了，那么会在指定的这个Appender和Root的Appender中都会输出，此时我们可以设置Logger的additivity="false"只在自定义的Appender中进行输出。



	2、最后能用log4j就行，至于RollingFile的研究下次再去研究



