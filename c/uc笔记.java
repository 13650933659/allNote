14 下午午 00:32:38   (笔记看完 day14)



张立猛




1、gcc 原名叫做 GNU C Compiler(GNU C语言编译器)：C语言编译器，有的版本还可以编译 c++、java等
	1、gcc xxx.c主要包含以下4部分功能：
		1、预处理  - 主要实现对头文件的包含以及宏替换等
		2、编译    - 主要将高级语言转换为汇编语言
		3、汇编    - 主要将汇编语言翻译成机器指令，得到目标文件
		4、链接    - 主要将目标文件和库文件进行链接，生成可执行文件 
    
	2、常见的编译选项
	   -E - 进行预处理,预处理的结果默认输出到控制台,使用 gcc -E xxx.c -o xxx.i将预处理结果定位到xxx.i文件中
	   -S - 进行编译处理，生成汇编文件 xxx.s
	   -c - 进行汇编处理，生成目标文件 xxx.o
		案例
		   gcc/cc -E xxx.c -o xxx.i			// 预处理,生成xxx.i文件
		   gcc/cc -S xxx.i/xxx.c			// 编译,生成xxx.c文件
		   gcc/cc -c xxx.s/xxx.i/xxx.c		// 汇编，生成xxx.o文件
		   gcc/cc xxx.o/xxx.s/xxx.i/xxx.c	// 链接处理,生成a.out文件

	   -std    - 主要用于指定编译时遵循的C标准，-std=c89/-std=c99 默认是 c89
	   -Wall   - 主要输出警告信息
	   -Werror - 主要用于将警告当做错误进行处理
	   -v      - 主要用于查看gcc的版本信息
	   -g      - 主要用于生成调试信息(gdb调试)
	   -O      - 主要用于进行优化处理
	   -x      - 主要用于显式指定源代码的编程语言
        其他   - man/gcc/cc 查看gcc更多的选项等信息

2、多文件
	1、常见的文件后缀
		.h  -  头文件，主要存放结构体的定义，函数的声明等等
		.c  -  源文件，主要存放变量/函数的定义等等   
		.i  -  预处理之后的文件
		.s  -  汇编文件
		.o  -  目标文件
		.out-  最后生成的连接文件
		.a  -  静态库文件，主要对功能代码的打包
		.so -  共享库文件，主要对功能代码的打包
	2、多文件的编写
		1、 add.h 文件	// 这样写(条件编译)只有第一次被引入才会编译，节省编译时间
			#ifndef __ADD_H__
				#define __ADD_H__
				int add(int, int);
			#endif
		2、 add.c 文件
			#include "add.h"	// 如果没有调用其他函数，这个可以不写
			int add(int a, intb){return a + b;}
		3、 其他文件的使用 main.c
			#include "add.h"
			add(1, 2);
		4、 最后你的编译需要这样： gcc main.c add.c		// add.h 可以不用，因为main.c的预编译会处理，这样main.c可以连接到add.c，要么你就要提供 add.o编译后的文件
		5、 为了调用者使用方便，程序不能直接提供 .c或者.o文件，需要把相关的.o文件打包成一个或者多个库文件，然后提供库文件和头文件即可
		6、如果需要应用其他文件声明的全局变量，如果需要用到需要用到使用 extern 再声明一次 extern int v;
3、环境变量和C的依赖
	1、系统环境变量的概念和使用
		1、基本概念：PATH 就是一个环境变量,一般来说，应用程序的执行需要带上路径才可以运行, 在 PATH 中的路径系统会自动识别，因此对应的应用程序只需要程序名就可以运行
		2、常见的环境变量
			C_INCLUDE_PATH/CPATH - C头文件的附加搜索路径
			LIBRARY_PATH		 - 链接库文件时查找的路径
			LD_LIBRARY_PATH	     - 运行时查找共享库的路径
			CPLUS_INCLUDE_PATH	 - C++头文件的附加搜索路径
		3、修改 PATH 
			1、一次性的
				export PATH=$PATH:.		// 表示将当前目录的路径追加到PATH的环境变量值中(一次性的，只是针对当前终端生效)
					$PATH - 表示获取环境变量PATH中原来的数值
					:     - 多个路径之间的分隔符
					.     - 当前目录的路径
			2、永久生效的，source ~/.bashrc 或者重启机器生效
				vi ~/.bashrc 或者 ~/.bash_profile ,打开文件，在文件的最后增加代码，然后重启生效，或者 source .bashrc 生效
				   export PATH=$PATH:.	// 加 . 以后在程序的bin目录启动程序就不用加 ./
				   export PS1='\W$'		// 这好像不用加
	2、头文件的查找方式 
		1、 #include <...>						// 表示去系统默认的路径中查找该头文件，某些系统路径，默认可以查找头文件，比如： /usr/include 
		2、 #include "..."						// 表示优先在当前工作目录下查找该头文件
		3、 配置环境变量(C_INCLUDE_PATH/CPATH)	// export CPATH=$CPATH:..  => 一次性
		4、 使用编译选项 -I(建议使用)			// gcc/cc *.c -I 头文件的路径	例子： gcc/cc *.c -I ..  ，但是多个呢
		5、总结(1)(2)来说,缺点在于头文件的位置一旦发生改变，则需要修改源程序;对于方式(3)来说，缺点就在于多个目录/工程之间可能相互影响;所以建议使用方案(4) 
	3、库文件的概念和使用
		1、库文件类型
			1、静态库的特性
			   a.静态库在使用时，直接把代码 复制到 目标文件中
			   b.优点：不需要跳转，效率比较好;脱离静态库文件
			   c.缺点：目标文件会比较大;修改和维护都不太方便
			2、共享库的特性
			   a.共享库在使用时，直接把代码对应的地址 复制过来
			   b.优点：目标文件比较小;修改和维护都比较方便
			   c.缺点：需要跳转，效率比较低；不能脱离共享库文件
		2、基本命令
			ldd a.out		// 表示查看a.out所链接的共享库信息
			gcc -static xx.c => 表示以静态库方式进行处理
		   比较发现,静态库方式生成的文件比较大
		3、库文件的使用
			1、静态库的生成和使用步骤
				1、静态库的生成步骤
				   a.编写源代码(xxx.c文件)
					 vi add.c文件
				   b.只编译不链接生成目标文件(xxx.o文件)
					 gcc -c add.c
				   c.生成静态库文件
					 ar -r lib库名.a 目标文件	// 例子 ar -r libadd.a add.o
				2、静态库的使用步骤
				   a.编写测试源代码(xxx.c)
					 vi main.c文件
				   b.只编译不链接生成目标文件(xxx.o)
					 gcc -c main.c
				   c.链接测试文件和静态库文件，链接的方式有三种:
					 1)直接链接
					   gcc main.o libadd.a
					 2)使用编译选项进行链接(掌握)
					   gcc main.o -l 库名 -L 库文件所在的路径	// 例子 gcc main.o -l add -L .
					 3)配置环境变量LIBRARY_PATH
					   export LIBRARY_PATH=$LIBRARY_PATH:.
					   gcc/cc main.o -l add
			2、共享库的生成和使用步骤
				1、连接方式使用
					1、共享库的生成步骤
						a.编写源代码(xxx.c)
							vi add.c文件
						b.只编译不链接生成目标文件(xxx.o)
							gcc -c -fpic add.c
						c.生成共享库文件
							gcc -shared 目标文件(xxx.o) -o lib库名.so	// 例子 gcc -shared add.o -o libadd.so
					2、共享库的使用步骤
						a.编写测试源代码(xxx.c)
							vi main.c文件
						b.只编译不链接生成目标文件(xxx.o)
							gcc -c main.c
						c.链接测试文件和共享库文件，链接的方式有三种:
							 1)直接链接
							   gcc main.o libadd.so
							 2)使用编译选项进行链接(掌握)
							   gcc main.o -l 库名 -L 库文件所在的路径  // 例子 gcc main.o -l add -L .
							 3)配置环境变量LIBRARY_PATH
							   export LIBRARY_PATH=$LIBRARY_PATH:.
							   gcc main.o -l add
						d.注意：
						   共享库的使用要求配置环境变量 LD_LIBRARY_PATH 的值，主要解决运行时找不到共享库的问题
						   export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:.
				2、使用程序内部以操作文件的方式调用
					4、共享库的动态加载，编译链接时需要增加选项： -ldl
						1、dlopen函数	// 主要用于打开和加载共享库文件，成功返回共享库的地址，否则返回NULL
							#include <dlfcn.h> 
							void *dlopen(const  char  *filename,int flag);
								第一个参数：字符串形式的共享库文件名
								第二个参数：标志
								   RTLD_LAZY - 延迟加载
								   RTLD_NOW - 立即加载
						2、dlerror函数	// 主要用于获取dlopen等函数调用过程发生的最近一个错误的详细信息,返回NULL则表示没有错误发生
							char *dlerror(void);
						3、dlsym函数	// 主要用于根据句柄和函数名获取在内存中的地址
							void *dlsym(void *handle,const char *symbol);
								第一个参数：句柄，也就是dlopen函数的返回值
								第二个参数：字符串形式的符号,表示函数名
								返回值：成功返回函数在内存中的地址,失败返回NULL
						4、dlclose函数 // 主要用于关闭参数handle所指定的共享库,成功返回0，失败返回非0，当共享库不再被任何程序使用时，则回收共享库所占用的内存空间
							int dlclose(void *handle);
   

4、unix/linux常用的shell
	1、 sh   - 最早的shell，功能落后
	2、 bash - 是sh的增强版，应用最广的shell
		echo 字符串		// 字符串原样输出，回显
		echo $SHELL		// 获取SHELL的值，进行回显
		echo $PATH		// 获取PATH的值，进行回显打印
	3、 csh  - 是按照c程序员习惯写的shell


5、C语言中的错误处理
	1、C语言没有完善的异常机制但，是我们可以通过返回值自己协商好，使用返回值来判别是否异常
	2、错误编号(全局变量 errno)  // #include <errno.h> 如果函数调用失败，则会将错误原因设置到 errno 中
		1、错误信息处理
			1.strerror函数	// <string.h> 根据错误编号返回错误信息
				char *strerror(int errnum);
			2、perror函数(重点)		// <stdio.h> 主要用于函数调用期间最后一个错误的信息打印出来，如果参数不为空，则将参数内容原样输出，然后跟着打印一个冒号和空格，再跟着错误信息以及换行
				void perror(const char *s);
			3、printf函数	// 也可以直接使用 printf直接打印，不用带参数
				printf("%m\n");

2、环境表的概念和使用
	1、基本概念
		环境表主要是指环境变量的集合,每个进程中都有一个环境表，用于记录与当前进程相关的环境变量信息
		环境表采用字符指针数组的形式进行存储，然后使用全局变量 char** envrion 来记录环境表的首地址，使用NULL表示环境表的末尾

	2、相关函数
		1、 getenv函数	// <stdlib.h> 主要用于根据参数name指定的环境变量名进行查找,如果查找成功则返回对应的环境变量值，否则返回NULL
			char *getenv(const char *name);
		2、 setenv函数	// <stdlib.h>	// 主要用于 修改/增加 环境变量,如果参数name指定的环境变量不存在则增加，如果存在则修改
			int setenv(const char *name, const char *value, int overwrite);
				第一个参数：环境变量名
				第二个参数：环境变量值
				第三个参数：如果存在是否修改的标志
					非0 - 表示允许修改环境变量的值
					 0  - 表示不允许修改环境变量的值
				返回值：成功返回0，失败返回-1
		3、 putenv函数	// <stdlib.h> 主要用于修改/增加环境变量,其中参数string的格式如下：name=value,成功返回0，失败返回非0
			int putenv(char *string);
		4、 unsetenv函数	// <stdlib.h> 主要用于将参数name指定的环境变量从环境表中删除，如果删除一个不存在的环境变量时，则函数调用依然是成功的，只是环境表没有改变
			int unsetenv(const char *name);
		5、 clearenv函数	// <stdlib.h> 主要用于将整个环境表全部清空
			int clearenv(void);
		6、 main函数的原型
			int main(int argc,char* argv[],char* envp[])
				第一个参数：命令行参数的个数
				第二个参数：指针数组，存储命令行参数的地址
				第三个参数：指针数组，环境表的首地址
3、内存管理
	1、进程中的内存区域划分
		1、代码区		       - 存储功能代码,函数名所在的区域
		2、只读常量区		   - 存放字符串常量,以及const修饰的全局变量
		3、全局+静态区/数据区  - 存放已经初始化的全局变量和static修饰的局部变量
		4、BSS段               - 存放没有初始化的全局变量和静态局部变量，该区域会在main函数执行之前进行自动清零
		5、堆区                - 使用malloc/calloc/realloc/free函数处理的内存,该区域的内存需要程序员手动申请和手动释放
		6、栈区                - 存放局部变量(包括函数的形参)、const修饰的局部变量，以及块变量，该区域的内存由操作系统负责分配和回收
	2、总结：
		1、按照地址从小到大进行排列：代码区 -> 只读常量区 -> 全局区/数据区 -> BSS段 -> 堆区 -> 栈区
		2、其中代码区和只读常量区一般统称为代码区；其中全局区/数据区和 BSS 段一般统称为全局区/数据区
		3、栈区和堆区之间并没有严格的分割线，可以进行微调，并且堆区的分配一般按照地址从小到大进行，而栈区的分配一般从接近3G开始按照地址从大到小进行分配，这就减低了堆栈地址重复的问题
	3、虚拟内存管理技术
	   Unix/linux系统中的内存都是采用虚拟内存管理技术进行管理的,即：每个进程都有0~4G的内存地址(虚拟的，并不是真实存在的),由操作系统负责把虚拟内存地址和真实的物理内存映射起来
	   其中0~3G之间的虚拟地址空间叫做用户空间，其中3G~4G之间的虚拟地址空间叫做内核空间；用户程序一般运行在用户空间，内核空间只有系统内核才可以访问，用户程序不能直接访问内核空间，
	   但是系统内核提供了一些系统函数负责将程序从用户空间切换到内核空间执行,执行完毕之后再切换回到用户空间
	   内存地址的基本单位是字节,内存映射的基本单位是内存页， 目前主流的操作系统的内存页大小是 4Kb(4096 字节)
	   1Tb = 1024Gb
	   1Gb = 1024Mb
	   1Mb = 1024Kb
	   1Kb = 1024Byte(字节)
	   1Byte = 8个bit(二进制位)



4、段错误的由来
	1、使用未映射的虚拟内存地址
	   vi a.c文件：   
		  int num = 10;
		  printf("&num = %p\n",&num); //0x01
	   vi b.c文件：
		  int* pi = 0x01;
		  printf("*pi = %d\n",*pi); => 段错误
	2、对没有操作权限的内存进行操作
		对只读常量区进行写操作，可能引发段错误
5、内存管理
	1、内存相关函数与相关操作
		1、 getpagesize函数			// 主要用于获取当前系统中内存页的大小,一般为4Kb
		2、 getpid()				// <unistd.h> 获取当前进程的进程号
		3、 cat /proc/进程号/maps	// 查看指定进程的内存分配情况  
		4、 malloc/free
			1、使用malloc申请动态内存的注意事项
				使用malloc函数申请内存时，操作系统会一次性分配33个内存页，如果你申请的比33大，会给你比33稍多一点，为了提高效率
				除了申请所需要的内存大小之外，他malloc函数还会可能还会申请额外的12个字节,用于存储此次申请的内存信息，一个链表，比如内存的大小等等
				使用malloc申请的内存,一定要注意不要对所申请的内存空间进行越界访问，避免破坏额外空间，导致free失败
			2、使用free函数释放动态内存
				而对于使用free释放内存时，则释放多少就减少多少,当使用free释放完毕所有内存时，系统可能会保留33个内存页以备再次申请使用
		5、sbrk/brk
			1、 sbrk函数			// <unistd.h> 主要用于调整内存的大小
				void *sbrk(intptr_t increment);
					参数increment > 0,则表示申请内存空间
					参数increment < 0,则表示释放内存空间
					参数increment = 0,则表示获取内存空间的当前位置
					返回值：
					   成功返回调整内存大小之前的位置,失败返回(void*)-1
				注意：
				   一般来说，使用sbrk申请比较小的内存时，系统默认分配一个内存页的大小，一旦申请的内存超过一个内存页时，则再次分配一个内存页
				   而释放内存时，如果释放之后剩下的内存足够用一个内存页表示，则一次性释放一个内存页，这个区别于 malloc
				   使用sbrk申请内存时，不会申请额外的空间存储管理内存的相关信息
			2、brk函数	// 主要用于根据参数指定的目标位置调整内存大小
				int brk(void *addr);
					如果目标位置 > 之前的目标位置 => 申请内存
					如果目标位置 < 之前的目标位置 => 释放内存
					如果目标位置 = 之前的目标位置 => 内存不变
					注意：
					   使用brk函数释放内存比较方便，因此一般情况下都使用sbrk函数和brk函数搭配使用,使用sbrk函数负责申请内存，使用brk函数负责释放内存
		6、 mmap/munmap
			1、 mmap 函数	// include <sys/mman.h> 主要用于建立文件/设备 到内存的映射
				void *mmap(void *addr, size_t length, int prot, int flags,int fd, off_t offset);
					第一个参数：映射的地址,给NULL即可，由内核指定，最好是给内核指定，这样才能保证不会覆盖其他变量存的东西
					第二个参数：映射的大小
					第三个参数：映射的权限
					   PROT_EXEC  - 可执行
					   PROT_READ  - 可读
					   PROT_WRITE - 可写
					   PROT_NONE  - 不可访问
					  => 上述选项可以进行按位或运算
					第四个参数：操作标志
					   MAP_SHARED    - 共享的
					   MAP_PRIVATE   - 私有的
					   MAP_ANON      - 过时了，用MAP_ANONYMOUS替代
					   MAP_ANONYMOUS - 映射到物理内存，默认是映射文件
					第五个参数：文件描述符，对应着一个文件，不用时直接给0即可
					第六个参数：偏移量,映射物理内存时给0即可
					返回值：成功返回映射的地址，失败返回(void*)-1,就是 MAP_FAILED

			2、 munmap 函数
				int munmap(void *addr, size_t length);	// 主要用于解除参数指定的映射
					第一个参数：映射的地址,mmap函数的返回值
					第二个参数：映射的大小
	2、系统调用的基本概念
	   系统调用就是操作系统内核对外提供的一系列接口函数，当外部函数调用系统函数时，会通过软中断的方式把地址空间从用户空间切换到内核空间，
	   执行系统调用函数的功能，功能执行完毕之后，地址空间有内核空间切回到用户空间，注意：系统调用多了性能会减低


6、文件管理(重点)
	1、文件的基本概念
		在Unix/linux系统中，几乎所有的一切都可以看作文件,因此，对于文件的操作适用于各种输入输出设备等等，当然目录也可以看作文件
		如：
		   /dev/null  // 空设备
		   echo hello > /dev/null  // 表示丢弃处理的结果
		   cat /dev/null > a.txt   // 表示清空文件a.txt
	2、文件相关的读写函数(重点)
		1、标C： 
		   fopen()/fclose()/fread()/fwrite()/fseek()   
		2、uc的
			1、 open 函数
				#include <sys/types.h>
				#include <sys/stat.h>
				#include <fcntl.h>
				int open(const char *pathname, int flags);
				int open(const char *pathname, int flags, mode_t mode);
				int create(const char *pathname, mode_t mode);
				以第二个open函数为例，主要用于打开/创建 一个 文件/设备，解析如下：
					第一个参数：字符串形式的文件路径和文件名
					第二个参数：操作标志
					   必须包含以下访问模式中的一种：
						   O_RDONLY - 只读
						   O_WRONLY - 只写(难道不能读吗?)
						   O_RDWR   - 可读可写
					   还可以按位或以下的标志值：
						   O_APPEND - 追加，写入到文件的尾部
						   O_CREAT  - 文件不存在则创建，存在则打开
						   O_EXCL   - 与 O_CREAT 搭配使用，存在则open失败
						   O_TRUNC  - 文件存在且允许写,则清空文件
					第三个参数：当创建新文件时，需要指定的文件权限，八进制，但是如果你给了其他人写的权限，会被系统屏蔽掉
					   如：
						 0644 => rw-r--r--    
					返回值：成功返回新的文件描述符，失败返回-1
						描述符就是一个小的非负整数，用于表示当前文件

			2、 close 函数    // <unistd.h> 主要用于关闭参数fd指定的文件描述符，也就是让描述符fd不再关联任何一个文件，以便于下次使用
				int close(int fd);

			3、 read 函数   // <unistd.h> 表示从指定的文件中读取指定大小的数据(一般默认以二进制形式进行读写操作)
				ssize_t read(int fd, void *buf, size_t count);
					第一个参数：文件描述符(从哪里读)
					第二个参数：缓冲区的首地址(存到哪里去)
					第三个参数：读取的数据大小(单位为字节)
					返回值：成功返回读取到的字节数,返回0表示读到文件尾，失败返回-1

			4、 write 函数  // <unistd.h> 表示将指定的数据写入到指定的文件中(一般默认以二进制形式进行读写操作)
				ssize_t write(int fd,const void *buf,size_t count);
					第一个参数：文件描述符(写入到哪里去)
					第二个参数：缓冲区的首地址(数据从哪里来)
					第三个参数：写入的数据大小(单位为字节)
					返回值：成功返回写入的数据大小，0表示没有写入, 失败返回-1
			5、 lseek 函数	// 主要用于调整文件的读写位置
				#include <sys/types.h>
				#include <unistd.h>
				off_t lseek(int fd,off_t offset,int whence);
					第一个参数：文件描述符(表示在哪个文件中操作)
					第二个参数：偏移量(正数表示向后偏移，负数向前偏移)
					第三个参数：起始位置(从什么地方开始偏移)
					   SEEK_SET - 文件开头位置
					   SEEK_CUR - 文件当前位置
					   SEEK_END - 文件结尾位置
					返回值：成功返回距离文件开头位置的偏移量，失败返回-1
					注意：文件末尾位置指的是文件中最后一个字符的下一个位置
			6、 dup/dup2 函数
				1、 dup
					int dup(int oldfd);	// <unistd.h> 主要用于根据参数指定的描述符进行复制，成功返回新的文件描述符，失败返回-1
				2、 dup2
					int dup2(int oldfd, int newfd);	//  <unistd.h> 主要用于将oldfd参数指定的文件表地址拷贝到newfd中,如果newfd被占用，则考虑强制关闭,成功返回新的文件描述符，失败返回-1
				3、 注意： dup/dup2函数用于复制文件描述符时，实质上是复制文件描述符所对应的文件表地址，也就是让多个文件描述符对应了同一个文件表，从而对应同一个文件

			7、 fcntl 函数(重点，复杂)
				1、整体介绍
					#include <unistd.h>
					#include <fcntl.h>
					int fcntl(int fd, int cmd, ... /* arg */ );
						第一个参数：文件描述符(对哪个文件操作)
						第二个参数：命令/操作(执行什么样的操作)
						   F_DUPFD                  - 表示复制文件描述符,新的文件描述符会选择>= arg的可用的值作为参数fd的备份,不会强制关闭正在使用的描述符，区别dup2
						   F_GETFD/F_SETFD          - 获取/设置文件描述符标志
						   F_GETFL/F_SETFL          - 获取/设置文件的状态标志
						   F_SETLK/F_SETLKW/F_GETLK - 
								1、增加锁或者释放锁
								2、功能与 F_SETLK 类似，所不同的是加不上锁时并不会返回失败，而是等待，直到可以加上该锁为止
								3、此属性不会真正的加锁，如果能加上锁但不会去加，而是将该锁的类型改为 F_UNLCK,其他成员不变;如果不能加上锁，则将文件中已经存在锁，包括pid赋值到第三个参数，如果是-1说明可以加锁，否则不可以
						
						第三个参数：可变长参数，是否需要取决于第二个参数
						返回值：
						   F_DUPFD                  - 成功返回文件描述符，失败返回-1
						   F_GETFD/F_SETFD          - 成功返回获取的标志/0,失败-1
						   F_GETFL/F_SETFL          - 成功返回获取的标志/0,失败-1
						   F_SETLK/F_SETLKW/F_GETLK - 成功返回0，失败-1
						函数功能：
						   (1)主要用于复制文件描述符(了解)
						   (2)设置/获取文件描述符标志的功能(了解)
						   (3)设置/获取文件状态标志的功能(了解)
						   (4)主要用于实现建议型文件锁的功能(掌握)
				2、使用fcntl实现文件锁的功能
					1、文件锁的作用
					   当有多个进程同时对同一文件进行读写操作时，可能会造成数据的混乱，
					   文件锁就是读写锁，也就是一把读锁和一把写锁，其中读锁是一把共享锁，不允许其他进程加写锁，可以加读锁;
					   而写锁是一把互斥锁，不允许其他进程加读锁和写锁
					2、 fcntl 实现文件锁的功能
						1、则fcntl函数第二个参数取值： F_SETLK/F_SETLKW/F_GETLK
							fcntl函数的第三个参数取值：一个结构体类型的指针，结构体如下(主要看一下五个属性即可)：
								struct flock  {
								  short l_type;/*锁类型,F_RDLCK,F_WRLCK,F_UNLCK */
								  short l_whence;/*起始位置(从什么地方开始):SEEK_SET,SEEK_CUR, SEEK_END */
								  int l_start;/*偏移量*/
								  int l_len;/*加锁的字节数*/
								  pid_t l_pid;/*加锁的进程号(F_GETLK only),-1*/
								};
						2、发现：
						   对文件进行加写锁之后，还是可以向文件中写入数据内容的，结果说明文件锁独立于文件的，并没有真正锁定对文件的读写操作，也就是说文件锁只能用于锁定其他的锁(导致第二次加锁失败,两个读锁除外)       
							一般来说，可以在进行读写操作之前尝试加读写锁，根据能否加读写锁来判断是否进行读写操作，自己控制
			8、stat/fstat 函数		// 主要用于获取文件的详细状态信息
			   #include <sys/types.h>
			   #include <sys/stat.h>
			   #include <unistd.h>
			   int stat(const char *path,struct stat* buf);
			   int fstat(int fd, struct stat *buf);
				第一个参数：文件的路径/文件描述符
				第二个参数：结构体指针,结构体变量的地址 
					struct stat {
					   ...
					   mode_t  st_mode;		// 文件的类型和权限，unsigned int类型
					   off_t   st_size;		// 文件的大小,long int 类型
					   time_t  st_mtime;	// 文件最后一次修改时间 long int 类型
					   ...
					}
				扩展：
				   char *ctime(const time_t *timep);				// <time.h>主要用于将整数类型的时间转换为字符串类型的时间
				   struct tm *localtime(const time_t *timep);		// <time.h>主要用于将整数类型的时间转换为结构体类型
						struct tm {
						  int tm_sec;/*秒数*/
						  int tm_min;/*分钟*/
						  int tm_hour;/*小时*/
						  int tm_mday;/*日*/
						  int tm_mon;/*月 +1 */
						  int tm_year;/*年 + 1900 */
						  int tm_wday;/*星期,星期日为第一天  +1 表示天数*/
						  int tm_yday;/*年中的天数 +1 表示天数*/
						  int tm_isdst;/*夏令时 了解*/
						};
			9、 access 函数	// <unistd.h> 主要用于判断文件是否存在以及是否拥有指定的权限
			   int access(const char *pathname, int mode);   
					第一个参数：文件的路径和文件名
					第二个参数：操作模式
					   F_OK - 判断文件是否存在
					   R_OK - 判断文件是否可读
					   W_OK - 判断文件是否可写
					   X_OK - 判断文件是否可执行
			   

			10、 chmod 和 fchmod 函数   // <sys/stat.h> 主要用于修改文件的权限
				  int chmod(const char *path, mode_t mode);
				  int fchmod(int fd, mode_t mode);
						第一个参数:文件的路径/文件描述符
						第二个参数:文件的新权限，如：0644

			11、 truncate 和 ftruncate	// <unistd.h> <sys/types.h> 主要用于将指定的文件截取到指定的大小
				  int  truncate(const  char  *path,off_t length);
				  int ftruncate(int fd, off_t length);
						第一个参数：文件的路径/文件描述符
						第二个参数：文件的最新大小，如果文件变小了，则文件的数据产生丢失，如果文件变大了，则扩展的部分用'\0'填充

			12、 umask函数(了解)		// <sys/types.h><sys/stat.h> 主要用于设置参数指定的权限屏蔽字(也就是要屏蔽的权限),返回之前的权限屏蔽子,该函数针对文件的创建有效
				mode_t umask(mode_t mask);
				例子
					#include <stdio.h> <sys/stat.h> <unistd.h> <fcntl.h>
					int main() {
						mode_t old = nmask(0022);	// 设置系统新的权限屏蔽，返回的old是系统旧的屏蔽字
						int fd = open("abc", O_RDWR|O_CREAT, 0666);
						// 使用stat函数结合位运算取权限，得到权限为 0644 后面两个的写被屏蔽了
						umask(old);		// 记得回复为之前的权限屏蔽
					}
			13、其他函数
			   link()   => 主要用于创建硬链接
			   unlink() => 主要用于删除硬链接
			   rename() => 主要用于重命名的场合
			   remove() => 主要用于删除指定的文件(重点)	
			   注意： unlink 和 remove 只是删除硬链接，里面的数据没有被删除，所以可以使用数据恢复软件
			14、目录管理
				1、 opendir函数		// <sys/types.h><dirent.h>主要用于打开参数指定的目录,成功返回目录指针,失败返回NULL
				   DIR *opendir(const char *name);
						name	- 文件的路径
					   

				2、 readdir函数		// <dirent.h> 主要用于读取参数指定的目录中内容,成功返回结构体指针(代表所读取的内容)，失败返回NULL
					struct dirent *readdir(DIR *dirp);
						struct dirent {
						   ...
						   unsigned char  d_type;/*文件的类型 4=目录 8=文件*/
						   char d_name[256]; /*文件的名称*/
						};

				3、closedir函数		// <sys/types.h> <dirent.h> 主要用于关闭参数指定的目录
				   int closedir(DIR *dirp);
			   

				4、getcwd函数 // <unistd.h> 主要用于获取当前工作目录的绝对路径存放到buf中，buf的大小是size个字节,成功返回buf的首地址，失败返回NULL
					char *getcwd(char *buf, size_t size);
			   

				5、 其他函数
					mkdir() => 主要用于创建新的目录函数
					rmdir() => 主要用于删除指定的目录
					chdir() => 主要用于切换/改变 所在的目录
				6、递归遍历文件夹，情看 recurrenceShowDir.c
	3、补充
		1、标C的文件处理函数比UC的文件处理函数速度快，因为标C函数内部都有输入输出缓冲区,会积累一定数量之后再写入文件，因此读写效率比较高
			但是对于UC的文件处理函数来说，可以通过自定义缓冲区来提高读写的效率  
			1、使用time命令用于获取程序的运行时间,如：
				time a.out
				   real	0m0.074s  => 真实时间
				   user	0m0.052s  => 用户态时间
				   sys	0m0.016s  => 内核态时间
		2、文件描述符
		   文件描述符本质就是一个整数,代表一个打开的文件。但是文件的信息存在文件表等结构中，但是处于安全和效率的考虑，文件表等结构并不会直接被操作,而是给文件表对应一个编号，而该编号就叫做文件描述符
		   在进程内部维护着一张文件描述符的总表，当使用open函数打开文件时，就会加载该文件的信息到内存中，然后去文件描述符的总表中查找一个最小的未被使用的描述符建立和文件表的对应关系，而文件描述符是非负整数，
		   也就是从0开始一直到OPEN_MAX(在Linux中一般最大值是255)，其中0 1 2 被系统占用，分别代表标准输入、标准输出、以及标准错误
		   close函数的工作方式：表示先把对应关系从文件描述符总表中删除，不一定会删除文件表,只有当文件表不会和任何其他文件描述符有对应关系时(也就是一个文件表可以对应多个文件描述符),才会删除文件表，
		   也就是说close函数不会改变文件描述符的整数值，只是会让该描述符无法代表一个文件
			注意：在同一个进程/不同的进程中多次调用open打开同一个文件时，文件表会产生多个，但是v节点只有一个
			i节点就是文件在硬盘的地址



7、进程管理
	1、基本概念和常用命令
		1、基本概念
		   程序   - 主要指存储在磁盘上的文件
		   进程   - 主要指在内存中运行的程序
		   子进程 - 如果进程A启动了进程B，那么进程A叫做进程B的父进程，其中进程0是系统内部的进程，负责启动进程1(init进程)和进程2，其他所有的进程都是由进程1和2直接/间接的启动起来
		2、常用命令
			1、windows系统：
			   调用任务管理器进行查看(ctrl+alt+delete)
			2、Unix/linux系统中：
				1、 ps - 主要用于查看当前终端中的进程(进程的快照)
					ps命令的执行结果如下：
					   PID  - 进程的编号(掌握)
					   TTY  - 终端的次要装置号码
					   TIME - 消耗CPU的时间
					   CMD  - 具体的命令以及参数(掌握)
	   
			   2、 ps -aux 表示显示所有包括其他使用者的进程/ps -aux | more 表示分屏显示所有的进程信息
				   USER - 属主信息(掌握)
				   PID  - 进程号(重点)
				   %CPU - 占用CPU的百分比
				   %MEM - 占用内存的百分比
				   VSZ  - 虚拟内存大小
				   RSS  - 物理内存大小
				   TTY  - 终端的次要装置号码
				   STAT - 进程的状态(留意)
					   S - 休眠状态
					   s - 进程的领导者(表示拥有子进程)
					   Z - 僵尸进程(进程已结束，资源没有回收)
					   R - 正在运行的进程
					   O - 可运行的状态(就绪状态)
					   T - 挂起状态
					   < - 优先级高的进程
					   N - 优先级低的进程
					   ...
				   START - 进程的启动时间
				   TIME - 消耗CPU的时间
				   COMMAND - 具体的进程名称以及选项(掌握)
	   

			 3、 ps -ef 表示使用全格式的方式显示进程信息\ps -ef | more 表示分屏显示进程的信息
				   PID - 进程号
				   PPID - 父进程的进程号
	2、各种ID的获取
	   PID       - 进程号，是进程在操作系统中的唯一标识，进程号的分配采用延迟重用的策略进行的，在每一个时刻都可以保证进程号唯一
	   getpid()  - 主要用于获取当前进程的进程号,pid_t
	   getppid() - 主要用于获取当前进程父进程的进程号
	   getuid()  - 主要用于获取用户的ID,uid_t
	   getgid()  - 主要用于获取用户所在用户组的ID,gid_t
	3、进程的创建
		1、fork 函数
			1、 fork 函数	// <unistd.h>
				pid_t fork(void);
				函数功能：
				   主要用于以复制正在运行进程的方式来创建新的进程，其中新进程叫做子进程，正在运行的进程叫做父进程
				   函数调用成功时，父进程返回子进程的PID，子进程返回0，函数调用出错时，父进程返回-1,子进程没有被创建
			2、 fork创建子进程的工作方式
				a.fork函数之前的代码,父进程执行一次
				b.fork函数的返回值,父子进程各自返回一次
				c.fork函数之后的代码,父子进程各自执行一次
			3、 fork 创建的父子进程之间的关系
				a.父进程启动子进程,父子进程同时启动,如果子进程先结束,会给父进程发信号等，让父进程帮助子进程回收资源
				b.如果父进程先结束，子进程变成孤儿进程,子进程会变更父进程(重新指定init进程为新的父进程)，init进程也叫做孤儿院
				c.如果子进程先结束，但是父进程由于各种原因并没有收到子进程发来的信号，也就是没有帮助子进程回收资源，那么子进程会变成僵尸进程
			4、 fork函数创建的父子进程之间的内存资源关系
				进程的内存区域划分：代码区、全局区、堆区、栈区
				使用fork函数创建的子进程会复制父进程中除了代码区之外的其他内存区域，而代码区和父进程共享
		2、其他函数
			1、 vfork函数
				#include <sys/types.h>
				#include <unistd.h>
				pid_t vfork(void);   
				函数功能：
				   该函数的功能与fork函数类似，返回值和出错原因等参考fork函数即可，但是所不同的是，该函数创建的子进程不会复制父进程中的内存区域，而是直接占用，导致父进程进入阻塞状态，直到子进程结束或者调用exec系列函数为止，而子进程的结束应该去调用_exit()函数
				注意：
				   vfork函数保证了创建的子进程先执行

			2、 exec 系列函数	// 是指以exec开头的6个函数，掌握以下一个即可
				int execl(const char *path,const char *arg, ...);
					第一个参数：执行的文件路径
					第二个参数：执行的参数，一般给文件名即可
					第三个参数：可变长参数，最后使用NULL结尾
					函数功能：
					   主要用于按照参数指定的路径和方式去执行文件
					如：
					   使用execl函数执行ls -l 命令时：
					   execl("/bin/ls","ls","-l",NULL);
					注意：
					   vfork函数本身没有太大的实际意义，一般与exec系列函数搭配使用,而vfork函数负责创建子进程，exec系列函数负责执行新的代码;
					   fork函数也可以和exec系列函数搭配使用，一般很少
			3、system函数
				#include <stdlib.h>
				int system(const char *command);
				函数功能：
				   主要用于按照参数指定的命令进行执行，失败返回-1
	4、进程的终止
		1、正常终止进程的方式：
			a.在main函数中执行了return 0;
			b.调用exit()函数终止进程
			c.调用_exit()/_Exit()函数终止进程
			d.最后一个线程返回
			e.最后一个线程调用了pthread_exit()函数
		2、非正常终止进程的方式：
			a.采用信号终止进程，比如：ctrl+c
			b.最后一个线程被其他线程调pthread_cancel()取消

		3、进程终止函数的比较
			1、 _exit()和_Exit()函数	//  他俩可以看成是同一个函数，_Exit 会调用 _exit
				void _exit(int status); => <unistd.h> UC的函数
				void _Exit(int status); => <stdlib.h> 标C的函数
			函数功能：
			   主要用于"立即"终止正在运行的进程，参数status的值会被返回给父进程，父进程调用wait系列函数进行获取,该参数作为终止进程的退出状态信息
			2、exit函数	// <stdlib.h>
				void exit(int status);
				int atexit(void (*function)(void));		// 主要用于对参数指定的函数进行注册/准备,而被该函数注册过的函数会在程序调用exit函数的期间被调用，或者在main函数执行return之后被调用
				函数功能：
				   主要用于引起正常进程的终止，并且将参数值 & 0377的结果返回给父进程
				   在终止进程的期间会调用由atexit()/on_exit()函数注册过的函数
	5、进程的等待
		1、 wait函数
			#include <sys/types.h>
			#include <sys/wait.h>
			pid_t wait(int *status);
			函数功能：
			   主要用于挂起当前正在运行的进程进入阻塞状态，直到有一个子进程终止为止(进程的终止作为进程状态发生改变的一种情况)
			   如果参数不为空，那么wait函数会将获得的状态信息存储到status指向的int类型空间中
			   成功返回终止的子进程ID，失败返回-1
			1、两个宏函数辅助子进程的返回状态
				WIFEXITED(status)   => 判断进程是否正常终止
				WEXITSTATUS(status) => 获取子进程的退出状态信息
		2、 waitpid函数
			pid_t waitpid(pid_t pid,int *status,int options);
				第一个参数：进程号(指定要等待哪一个进程)
					-1  - 等待任意一个子进程(重点掌握)
					>0  - 等待进程号为pid的子进程(重点掌握)
					<-1 - 等待进程组ID为pid绝对值的任意一个子进程(了解)
					0   - 等待和调用进程在同一个进程组的任意一个子进程(了解)
				第二个参数：指针变量，用于获取子进程的状态信息
				第三个参数：等待的方式(主线程阻塞)，也可以设置为不阻塞，一般用法使用默认给0即可
				返回值：成功返回等到的子进程ID，失败返回-1
				函数功能：主要用于等待指定的子进程状态发生改变

8、信号
	1、中断的概念
	   表示暂时停止当前进程的执行转而去执行新的进程或者处理意外情况的过程,叫做中断，中断的方式分为：硬件中断 和 软件中断
	2、信号
		1、基本概念：信号本质就是一种软件中断的方式，信号既可以作为进程间的一种通信方式,更重要的是，可以用于处理一些意外情况
		2、特点
			a.信号是异步的,进程并不知道信号何时会到达
			b.进程既可以处理信号，也可以发送信号
			c.每个信号都有一个名字,并且使用SIG开头
		3、基本命令和分类
			1、基本命令
				使用命令:  kill -l 查看当前系统所支持的信号
		4、掌握的信号：
		   SIGINT  2   采用ctrl+c产生   终止进程
		   SIGQUIT 3   采用ctrl+\产生   终止进程
		   SIGKILL 9   采用kill -9产生  终止进程
		5、分类
			一般来说，在linux系统中支持的信号有1 ~ 64，其中1~31之间的信号叫做不可靠信号，不支持排队，可能会丢失，也叫做非实时信号
			34~64之间的信号叫做可靠信号，支持排队，不会丢失，也叫做实时信号
		6、注意：
		   其中信号SIGKILL(信号9)不允许被用户捕捉
		7、信号的处理方式
			1、默认处理，绝大多数信号都是终止进程
			2、忽略处理
			3、自定义处理
				1、信号的处理函数
					1、 signal(int signum, sighandler_t handler);		// <signal.h> handler 是函数指针=typedef void (*sighandler_t)(int)
						第一个参数：信号值/信号名称(对哪个信号处理)
						第二个参数：信号的处理方式(怎么处理)
							SIG_IGN  - 忽略处理
							SIG_DFL  - 默认处理
							函数指针 - 具有int参数和void返回值函数的指针
						返回值：成功返回之前的处理方式，失败返回SIG_ERR
						函数功能：主要用于设置指定信号的处理方式
						注意：
							1、对于fork创建的子进程来说,完全照搬父进程对信号的处理方式
							2、对于vfork和exec系列创建的子进程来说，父进程忽略，子进程也忽略；父进程默认，子进程也默认；父进程自定义，子进程采用默认处理(但是可以使用exec系列实现了跳转)
					2、 sigaction 函数 => signal 函数的增强版
						#include <signal.h> 
						int sigaction(int signum,const struct sigaction *act,struct sigaction *oldact);
						第一个参数：信号值/信号名称
						第二个参数：设置信号的新处理方式
							struct sigaction 
							{
							   void (*sa_handler)(int);		// 函数指针类型,信号的处理方式之一，和signal函数参数二一致,SIG_IGN SIG_DFL 函数名
							   void (*sa_sigaction)(int, siginfo_t *, void *);	// 函数指针，信号的处理方式之二 ，是否采用该处理方法，取决于下面的sa_flags的值
									siginfo_t{
										pid_t si_pid;/*发送者的进程号*/
										sigval_t si_value;/*随信号一起发送的附加数据*/
										...
									};
							   sigset_t sa_mask;	// 存放在执行信号处理函数期间需要屏蔽的信号，自动屏蔽与处理信号相同的信号，除非使用SA_NODEFER
							   int sa_flags;
									=> SA_SIGINFO	- 表示采用第二个函数指针处理信号
									=> SA_NODEFER	- 表示解除与处理信号相同的信号屏蔽
									=> SA_RESETHAND - 表示信号处理函数执行后恢复默认方式

							   void     (*sa_restorer)(void);	// 保留成员，暂时不使用,为了扩展方便
							};

						第三个参数：获取信号之前的处理方式
						函数功能：主要用于获取/修改信号的处理方式
		8、信号的发送方式
			1、采用键盘发送信号(只能发送部分特殊的信号)
				ctrl+c  信号2  SIGINT
				ctrl+\  信号3  SIGQUIT
				...
			2、程序出错(只能发送部分特殊的信号)
				段错误   信号11 SIGSEGV
				总线错误 信号7  SIGBUS
				...
			3、使用命令发送信号(所有信号都能发)
				kill -信号值 进程号
			4、采用系统函数发送信号(重点)：raise()/kill()/alarm()/sigqueue()
				1、 raise 函数
					#include <signal.h>
					int raise(int sig);
					函数功能：主要用于将参数指定的信号发送给正在调用的进程/线程

					#include <unistd.h>
					unsigned int sleep(unsigned int seconds);
					函数功能：
					   主要用于按照参数指定的秒数进入睡眠，当指定的秒数已经睡够了则返回0，当指定的秒数没有睡够就被信号打断，则返回还没有来得及睡的秒数

				2、 kill 函数
					#include <sys/types.h>
					#include <signal.h>
					int kill(pid_t pid, int sig);
						第一个参数：进程号(给谁发)
						   >0 发送给进程号为pid的进程(掌握)
						   0  发送给和调用进程同组的每一个进程(了解)
						   -1 发送给每一个可以被发送信号的进程(了解)
						  <-1 发送给进程组ID为-pid的每一个进程(了解)
						第二个参数：信号值(发送什么样的信号)
						   0 不会发送信号,用于检查指定的进程/进程组的存在
						函数功能：主要用于给指定的进程发送指定的信号

				3、 alarm 函数
					#include <unistd.h>
					unsigned int alarm(unsigned int seconds);
					函数功能：
					   主要用于根据参数指定的秒数之后，给正在运行的进程发送SIGALRM信号，每次设置新的闹钟之后之前的闹钟会被取消，并且返回之前闹钟没有来得及响的剩余时间，如果之前没有设置闹钟，则返回0
					   参数为0时不会设置新的闹钟,用于取消之前的闹钟		
				4、 sigqueue 函数
					#include <signal.h>
					int sigqueue(pid_t pid,  int  sig,  const union sigval value);
						第一个参数：进程号(给谁发信号)
						第二个参数：信号值(发什么样的信号)，这里注意可靠信号才支持排队
						第三个参数：联合变量，发送的附加数据
						函数功能：主要用于给指定的进程发送信号和附加数据
		9、信号的屏蔽
			1、基本概念：在某些特殊的情况下,执行一些关键的代码时，不允许被信号打断的，这个时候需要借助信号的屏蔽来解决
			2、信号集
				1、信号集 sigset_t 的类型,是8*16=128位,如下：
					typedef struct{
						unsigned long int __val[(1024 / (8 * sizeof (unsigned long int)))];
					} __sigset_t;

				2、信号集的相关处理
					sigemptyset() => 主要用于清空信号集
					sigfillset()  => 主要用于填满信号集
					sigaddset()   => 主要用于增加信号到信号集
					sigdelset()   => 主要用于删除信号
					sigismember() => 主要用于判断信号是否存在
			3、 sigprocmask 函数
				#include <signal.h>
				int sigprocmask(int how,const sigset_t *set, sigset_t *oldset);
					第一个参数：屏蔽的方式(怎样屏蔽)
						SIG_BLOCK   - ABC + CDE => ABCDE（旧+新,了解）
						SIG_UNBLOCK - ABC-CDE => AB(旧-新,了解)
						SIG_SETMASK - ABC CDE => CDE(新替换旧，掌握)
					第二个参数：新的信号集(存放要屏蔽的信号)
					第三个参数：旧的信号集(获取之前屏蔽的信号)
					函数功能：主要用于修改/检查屏蔽的信号集

			4、 sigpending 函数
				#include <signal.h>
				int sigpending(sigset_t *set);
				函数功能：
				   主要用于获取信号屏蔽期间来过却没有被处理的信号，将获取的信号填充到参数set中
		10、计时器的概念和使用
			1、计时器的基本概念：Unix/linux系统中,系统内核为每一个进程都维护着三种计时器：真实计时器、虚拟计时器、以及实用计时器
			2、计时器的基本使用
				#include <sys/time.h>
				int getitimer(int which, struct itimerval *curr_value);
				int setitimer(int which, const struct itimerval *new_value,struct itimerval *old_value);
					第一个参数：计时器的类型(哪一种计时器)
						ITIMER_REAL    - 真实计时器，以系统真实的时间计算，通过产生SIGALRM信号工作的(掌握)
						ITIMER_VIRTUAL - 虚拟计时器，以该进程在用户空间下执行的时间来计算的，产生SIGVTALRM信号工作(了解)
						ITIMER_PROF    - 实用计时器，以该进程在用户空间和内核空间花费的总时间计算,产生SIGPROF工作(了解)
							
					第二个参数：计时器的新值(将要设置给计时器的)
						struct itimerval{
						   struct timeval it_interval; /*间隔时间*/
						   struct timeval it_value;    /*启动时间*/
						};
						struct timeval{
						   long tv_sec; /*秒数*/
						   long tv_usec;/*微秒*/
						};

					第三个参数：计时器的旧值(返回之前的数值)
					函数功能：主要用于获取/设置计时器的数值


9、进程间的通信
	1、基本概念: 两个/多个进程之间的数据交互 叫做进程间的通信
	2、进程间的通信方式
		1、 文件
		2、 信号
		3、 管道
		4、 共享内存
		5、 消息队列(重点)
		6、 信号量集
		7、 网络(重点)
		其中(4)(5)(6)三种通信方式统称为 XSI IPC通信(X/open System Interface Inter-Process Commucation)
	3、实现方式
		1、使用 管道 实现进程间的通信(了解)
			1、基本概念和分类
				管道本质还是文件,是一种比较特殊的文件
				管道分为两大类：有名管道  和 无名管道
				有名管道 ：主要由程序员手动创建，实现任意两个进程间的通信
				无名管道 ：主要由系统创建，用于父子进程之间的通信
			2、使用 有名管道 实现进程间的通信
				使用命令： mkfifo xxx.pipe  创建有名管道
				如：
				   mkfifo a.pipe
				   终端中输入：echo hello > a.pipe  
				   另起一个终端中输入：cat a.pipe 
				   此时，数据“hello”会通过管道传递过去，但是管道文件本身并不会存储数据
			3、使用无名管道实现进程间的通信
				#include <unistd.h>
				int pipe(int pipefd[2]);
				函数功能：
				   主要用于创建一个无名管道，作为进程间通信的数据通道，通过参数带出两个文件描述符，其中pipefd[0]代表读端；pipefd[1]代表写端
		2、使用 共享内存 实现进程间的通信
			1、基本概念 : 由系统内核维护一块内存区域，该内存区域共享在两个/多个进程之间，从而实现进程间的通信
			2、通信的基本步骤
				1、获取key值，使用 ftok 函数
				2、创建/获取共享内存，使用 shmget 函数
				3、挂接共享内存,使用 shmat 函数
				4、使用共享内存
				5、脱接共享内存，使用 shmdt 函数
				6、如果不再使用，则删除共享内存，使用 shmctl 函数
			3、相关函数的解析
				1、 shmget 函数
					#include <sys/ipc.h> <sys/shm.h>
					int shmget(key_t key, size_t size, int shmflg);
						第1个参数  - key值，ftok函数的返回值
						第2个参数  - 共享内存的大小
						第3个参数：操作标志，这个参数需要加上八进制的权限描述
							IPC_CREAT - 创建
							IPC_EXCL  - 与IPC_CREAT搭配使用,存在则创建失败
							0         - 获取已存在的共享内存
						返回值：成功返回共享内存的ID，失败返回-1
						函数功能：主要用于创建/获取共享内存
						注意：
						   当创建新的共享内存时，需要指定权限，如0644

				2、 shmat 函数
					#include <sys/types.h> <sys/shm.h>
					void *shmat(int shmid, const void *shmaddr, int shmflg);
						第1个参数  - 共享内存的ID， shmget 函数的返回值
						第2个参数  - 共享内存的挂接地址，给NULL由系统指定
						第3个参数  - 操作共享内存的权限，给0即可
						返回值     - 成功返回共享内存的首地址，失败返回(void*)-1
						函数功能   - 主要用于将共享内存挂接到当前进程中

				3、 shmdt 函数
					int shmdt(const void *shmaddr);
					函数功能 - 主要用于按照参数指定的地址进行脱接，也就是 shmat 函数的返回值

				4、 shmctl 函数
					#include <sys/ipc.h> <sys/shm.h>
					int shmctl(int shmid, int cmd, struct shmid_ds *buf);
						第1个参数  - 共享内存的编号
						第2个参数  - 具体的操作命令
						   IPC_RMID - 删除共享内存，第三个参数给NULL即可
						第3个参数  - 结构体指针
						函数功能   - 主要用于对指定的共享内存进行控制
			4、基本命令
				ipcs -m				  - 查看系统中存在的共享内存
				ipcrm -m 共享内存的ID - 表示删除指定的共享内存
		3、使用 消息队列 实现进程间的通信
			1、使用消息队列通信的基本流程
				1、获取key值，使用ftok函数
				2、创建/获取消息队列，使用msgget函数
				3、发送/接受消息队列中的消息,使用msgsnd/msgrcv函数
				4、如果不再使用，则删除消息队列,使用msgctl函数
			2、相关函数的解析
				1、 ftok 函数
					#include <sys/types.h> <sys/ipc.h>
					key_t  ftok(const char *pathname,int proj_id);
						第1个参数 - 字符串形式的路径名(必须存在，可以访问)
						第2个参数 - 项目ID，取低8位(必须是非0)，随便给个100也行
						返回值    - 成功返回生成的key值，失败返回-1
						函数功能：- 主要用于根据参数指定的路径和项目ID,生成一个key值
						注意：
						   如果使用相同的路径名和项目ID，则生成相同的key值
				2、 msgget 函数
					#include <sys/types.h> <sys/ipc.h> <sys/msg.h>
					int msgget(key_t key, int msgflg);
						第1个参数	 - key值，ftok函数的返回值
						第2个参数    - 操作标志
						   IPC_CREAT - 创建消息队列
						   IPC_EXCL  - 与IPC_CREAT搭配使用，存在则创建失败
							0        - 表示获取已存在的消息队列
						返回值		 - 成功返回消息队列的ID，失败返回-1
						函数功能     - 主要用于获取一个消息队列的ID
						注意：
						   当创建一个新的消息队列时，需要在第二个参数中指定消息队列的权限,如：0644
				3、 msgsnd 函数
				   #include <sys/types.h> <sys/ipc.h> <sys/msg.h>
				   int msgsnd(int msqid, const void *msgp, size_t msgsz, int msgflg);
						第1个参数  - 消息队列的ID，msgget函数的返回值
						第2个参数  - 消息的首地址,消息的格式如下：这个结构体是我们自己定义的
							struct msgbuf{
							   long mtype;/*消息的类型, must be > 0 */
							   char mtext[1];/*消息的内容,可以是其他数据类型*/
							};
						第2个参数 - 指消息内容的大小，不包括消息的类型 mtype 的大小，所以需要 -8，但是使用 msgrcv 取时也需要-8
						第3个参数 - 发送的方式，一般给0即可阻塞（即：等消息队列满了之后会等待， NO_WAIT 则满了会报错）
						函数功能  - 主要用于发送指定的消息到指定的消息队列中
				4、 msgrcv 函数
					ssize_t msgrcv(int msqid, void *msgp, size_t msgsz, long msgtyp, int msgflg);
						第1个参数   - 消息队列的ID，msgget函数的返回值
						第2个参数   - 存放接受到消息的首地址
						第3个参数   - 消息的大小，记得-8
						第4个参数   - 消息的类型
							0  - 读取消息队列中的第一个消息
							>0 - 读取消息队列中第一个类型为msgtyp的消息
							<0 - 读取消息队列中<=msgtyp绝对值的消息，最小的优先读取
						第5个参数   - 接受的方式，一般给0即可阻塞（即：等消息队列满了之后会等待， NO_WAIT 则满了会报错）
						返回值      - 成功返回接受的消息大小，失败返回-1
						函数功能    - 主要用于从指定的消息队列中接受消息
				5、 msgctl 函数
					#include <sys/types.h> <sys/ipc.h> <sys/msg.h>
					int msgctl(int msqid,int cmd,struct msqidds *buf);
						第1个参数  - 消息队列的ID，msgget函数的返回值
						第2个参数  - 操作的命令
							IPC_RMID - 删除指定的消息队列,第三个参数给NULL
						第3个参数 - 结构体指针
						函数功能  - 主要用于对指定的消息队列执行指定的操作，删除，修改等
			3、基本命令
				ipcs -q              - 表示查看当前系统中的消息队列
				ipcrm -q 消息队列ID  - 表示删除指定的消息队列
		4、使用 信号量集 实现进程间的通信
			1、基本概念
				1、什么叫做 信号量 ： 信号量本质就是一个计数器，用于控制同时访问共享资源的进程总数,也就是解决有限资源的分配问题
				2、什么叫做 信号量集 ： 信号量集本质就是信号量的集合，也就是多个计数器，主要用于解决多种有限资源的分配问题

			2、信号量的工作方式
				1、首先给信号量进行初始化，初始化为最大值
				2、如果有进程申请到资源，那么信号量的值减 1
				3、当信号量的值为0时，停止共享资源的分配，申请共享资源的进程进入阻塞状态
				4、如果有进程释放资源，那么信号量的值加 1
				5、当信号量的值 > 0时，则阻塞的进程继续抢占资源，抢占不到资源的进程继续进入阻塞状态

			3、使用信号量集实现进程间通信的流程
				1、获取key值，使用ftok函数
				2、创建/获取信号量集，使用 semget 函数
				3、初始化信号量集，使用 semctl 函数
				4、操作信号量集，使用 semop 函数
				5、如果不再使用，则删除信号量集，使用 semctl 函数

			4、相关函数的解析
				1、 semget 函数
					#include <sys/types.h> <sys/ipc.h> <sys/sem.h>
					int semget(key_t key, int nsems, int semflg);
						第1个参数  - key值，ftok函数的返回值
						第2个参数  - 信号量集的大小,也就是信号量的个数
						第3个参数  - 操作标志
							IPC_CREAT - 创建
							IPC_EXCL  - 与 IPC_CREAT 搭配使用，存在则创建失败
							 0        - 获取已存在的信号量集
						返回值	   - 成功返回信号量集的ID，失败返回-1
						函数功能   - 主要用于创建/获取一个信号量集
						注意：
						   当创建新的信号量集时，需要指定权限,如：0644  
				2、 semctl 函数
					#include <sys/types.h> <sys/ipc.h> <sys/sem.h>
					int semctl(int semid, int semnum, int cmd, ...);
						第1个参数   - 信号量集的ID(对哪个信号量集操作)
						第2个参数   - 信号量集的下标(从0开始)
						第3个参数   - 具体的操作命令
						   IPC_RMID - 删除信号量集，第二个参数 semnum 忽略 - 此时不需要第四个参数
						   SETVAL   - 使用第四个参数给第 semnu m个信号量初始化
						   ...
						第四个参数  - 可变长参数，是否需要取决于cmd
						函数功能：
						   主要用于对指定的信号量集/信号量执行指定的操作
				3、 semop 函数
					#include <sys/types.h> <sys/ipc.h> <sys/sem.h>
					int semop(int semid, struct sembuf *sops, unsigned nsops);
						第1个参数  - 信号量集的ID, semget 函数的返回值
						第2个参数  - 结构体指针，结构体数组首地址/变量的地址
						结构体中的成员有：
						struct sembuf{
						   unsigned short sem_num;/*信号量集的下标*/
						   short sem_op;/*信号量的操作 正数 增加；  负数 减少；  0 不变*/
						   short sem_flg;/*直接给0即可*/
						};
						第三个参数 - 结构体数组的大小
						函数功能   - 主要用于对指定的信号量集进行操作
				4、基本命令
					ipcs -s  - 查看当前系统中的信号量集
					ipcrm -s - 信号量集的ID 表示删除指定的信号量集
					ipcs -a  - 查看当前系统中所有的IPC结构
		5、使用 网络 实现进程间的通信
			1、网络常识: 目前主流的通讯软件：QQ 微信 飞信 阿里旺旺 ...  
				1、七层网络协议: 从逻辑上将互联网的通信划分为7层网络模型：
					1、 应用层     - 主要用于和应用程序进行通信，将数据交给程序
					2、 表示层     - 主要用于按照通用的格式对数据进行封装及加密
					3、 会话层     - 主要用于控制对话的开始和结束等等
					4、 传输层     - 主要用于提供数据传输的通道
					5、 网络层     - 主要用于提供一些传递数据时路径的选择等
					6、 数据链路层 - 主要用于错误检查,将数据转为高低电平信号
					7、 物理层     - 主要用于借助网卡驱动，网线 设备等发送数据

				2、常用的网络协议
					1、 TCP协议  - 传输控制协议，是一种面向连接的协议，类似打电话
					2、 UDP协议  - 用户数据报协议,是一种非面向连接协议,类似发短信
					3、 IP协议   - 互联网协议,是TCP/UDP协议的底层协议
					4、 FTP协议  - 文件传输协议

				3、 IP地址(重点)
					IP地址 - 是互联网中的唯一地址标识，本质就是一个32位的整数(IPv4),目前存在128位的整数(IPv6)       
					日常生活中，IP地址采用点分十进制表示法，也就是说：将每一个字节整数转换为十进制，然后多个字节之间采用小数点(.)进行分隔
					如：
					   IP地址：0x01020304 => 1.2.3.4  
					IP地址分成两部分：网络地址 + 主机地址
					根据IP地址网络地址和主机地址的划分，分为以下4类：
					   A类： 0 + 7位网络地址 + 24位主机地址
					   B类： 10 + 14位网络地址 + 16位主机地址
					   C类： 110 + 21位网络地址 + 8位主机地址
					   D类： 1110 + 28位多播地址

				4、子网掩码
				   一般来说，每一个有效的IP地址都会带着一个子网掩码，子网掩码主要用于划分一个IP地址中的网络地址和主机地址，可以使用 按位与(&)来判断两个ip是否在同一网段
						如：
							IP地址：172.30.11.191
						  子网掩码：255.255.255.0  &
						---------------------------------
									172.30.11.0  - 网络地址
									191          - 主机地址
				5、Mac地址
					Mac地址，又叫做物理地址,本质就是硬件网卡的地址，路由器中采用Mac地址进行过滤

				6、端口号
					通过IP地址  定位到 一台 具体的主机上
					通过端口号 定位主机上的具体某一个进程
					端口号的数据类型是：unsigned short类型，范围是0~65535，其中 0 ~ 1024之间的端口号被系统占用
					网络编程中提供：IP地址 + 端口号

				7、字节序
					小端系统：使用低位内存地址存放低位字节数据 
					大端系统：使用低位内存地址存放高位字节数据
					如： 0x12345678
					  小端系统中按照地址从小到大： 0x78 0x56 0x34 0x12
					  大端系统中按照地址从小到大： 0x12 0x34 0x56 0x78
						网络字节序：为了兼容不同的系统对多字节整数的存放问题,一般来说，在发送数据之前需要将原始数据转换为网络字节序，而在接受数据之后，需要将接受到的数据转换为主机字节序
						其中网络字节序，本质就是大端系统的字节序
			2、基于 socket 的一对一通信模型
				1、基本概念： socket 插座  通信载体 作为进程间通信的通信点
				2、通信的模型
					服务器：
					   (1)创建 socket ,使用 socke t函数
					   (2)准备通信地址，使用结构体类型
					   (3)绑定 socket 和通信地址,使用 bind 函数
					   (4)进行通信，使用 read/write 函数
					   (5)关闭 socket ,使用 close 函数
					客户端：
					   (1)创建 socket ,使用 socket 函数
					   (2)准备通信地址，使用服务器端的地址
					   (3)连接 socket 和通信地址,使用 connect 函数
					   (4)进行通信，使用 read/write 函数
					   (5)关闭 socket ,使用 close 函数

				3、相关函数的解析
					1、socket函数
						#include  <sys/types.h> <sys/socket.h>
						int socket(int domain,int type,int protocol);
							第1个参数  - 域/协议族 决定是本地通信还是网络通信
								AF_UNIX/AF_LOCAL - 本地通信
								AF_INET          - 基于IPv4的网络通信
								AF_INET6         - 基于IPv6的网络通信
							第2参数    - 类型，决定是采用的通信协议
								SOCK_STREAM  - 数据流方式的通信,基于TCP协议
								SOCK_DGRAM   - 数据报方式的通信,基于UDP协议
							第3个参数  - 特殊协议，一般给0即可
							返回值     - 成功返回新的描述符，失败返回-1
							函数功能   - 主要用于创建socket来进行通信

					2、通信地址的结构体类型
						struct sockaddr{
						 sa_family_t sa_family; //协议族
						 char        sa_data[14];//字符串的地址数据
						}
						注意：
						  该结构体主要用于作为函数的参数类型，很少去定义结构体变量进行使用，使用一下两个结果体，但是需要强转成 sockaddr
							1、实现本地通信的结构体类型
								#include <sys/un.h>
								struct sockaddr_un{
								  sa_family_t sun_family;//协议族,socket函数参数一
								  char        sun_path[];//socket文件的路径
								};
							2、实现网络通信的结构体类型
								#include <netinet/in.h>
								struct sockaddr_in{
								   sa_family_t sin_family;//AF_INET.
								   in_port_t   sin_port;//端口号
								   struct in_addr sin_addr;//IP地址
								}; 
								struct in_addr{
								   in_addr_t  s_addr;//整数类型的IP地址
								};

					3、 bind 函数
						#include <sys/types.h> <sys/socket.h>
						int bind(int sockfd, const struct sockaddr *addr,socklen_t addrlen);
							第1个参数 - socket 描述符, socket 函数的返回值
							第2个参数 - 通信地址,需要做类型转换
							第3个参数 - 通信地址的大小,使用 sizeof 计算即可
							函数功能  - 主要用于绑定 socket 和通信地址

					4、 connect 函数
						#include <sys/types.h> <sys/socket.h>
						int connect(int sockfd, const struct sockaddr *addr,socklen_t addrlen);
						函数功能：
						   主要用于连接socket和通信地址,参数请参考bind函数

					5、字节序转换函数
						#include <arpa/inet.h>
						uint32_t htonl(uint32_t hostlong);  => 主要用于将32位的主机字节序转换为网络字节序
						uint16_t htons(uint16_t hostshort); => 主要用于将16位的主机字节序转换为网络字节序
						uint32_t ntohl(uint32_t netlong);   => 主要用于将32位的网络字节序转换为主机字节序
						uint16_t ntohs(uint16_t netshort);  => 主要用于将16位的网络字节序转换为主机字节序

					6、IP地址的转换函数
						#include <sys/socket.h> <netinet/in.h> <arpa/inet.h>
						in_addr_t inet_addr(const char *cp);
						函数功能：
						   主要用于将字符串形式的IP地址转换为整数类型
			3、基于socket的一对多通讯
				1、基于TCP协议的编程模型
					1、编程模型(重点掌握）
						服务器：
						   (1)创建socket,使用socket函数
						   (2)准备通信地址，使用结构体类型
						   (3)绑定socket和通信地址,使用bind函数
						   (4)监听，使用listen函数
						   (5)响应客户端的连接请求，使用accept函数
						   (6)进行通信，使用send/recv函数
						   (7)关闭socket,使用close函数
						客户端：  
						   (1)创建socket,使用socket函数
						   (2)准备通信地址,是服务器的地址
						   (3)连接socket和通信地址,使用connect函数
						   (4)进行通信,使用send/recv函数
						   (5)关闭socket,使用close函数

					2、相关函数的解析
						1、 listen 函数
							#include <sys/types.h> <sys/socket.h>
							int listen(int sockfd, int backlog);
								第1个参数  - socket 描述符,socket函数的返回值
								第2个参数  - 允许排队等待被响应的最大连接数
								函数功能   - 主要用于监听指定socket上的连接请求
						2、 accept 函数
							#include <sys/types.h> <sys/socket.h>
							int accept(int sockfd, struct sockaddr *addr, socklen_t *addrlen);
								第1个参数  - socket 描述符, socket 函数的返回值
								第2个参数  - 结构体指针，用于存储客户端的通信地址
								第3个参数  - 通信地址的大小，客户端的地址大小
								返回值     - 成功返回一个socket描述符,失败返回-1
								函数功能   - 主要用于响应指定socket上的连接请求
								注意：
								   socket 函数创建的socket主要用于监听连接数
								   accept 函数创建的socket主要用于与客户端通信
						3、 inet_ntoa 函数
							char *inet_ntoa(struct in_addr in);
							=> 主要用于将结构体类型的IP地址转换为字符串类型
				2、基于UDP协议的通信模型
					1、通信模型(重点掌握)
						服务器：
						   (1)创建socket,使用socket函数  
						   (2)准备通信地址，使用结构体类型
						   (3)绑定socket和通信地址，使用bind函数
						   (4)进行通信，使用sendto/send/recvfrom/recv函数
						   (5)关闭socket,使用close函数
						客户端：
						   (1)创建socket，使用socket函数
						   (2)准备通信地址，使用服务器的地址
						   (3)进行通信,使用sendto/send/recvfrom/recv函数
						   (4)关闭socket,使用close函数

					2、相关函数的解析
						1、 sendto 函数
							#include <sys/types.h> <sys/socket.h>
							ssize_t send(int sockfd, const void *buf, size_t len, int flags);
							ssize_t sendto(int sockfd, const void *buf, size_t len, int flags,const struct sockaddr *dest_addr, socklen_t addrlen);
								第1个参数  - socket描述符,socket函数的返回值
								第2个参数  - 被发送的数据首地址
								第3个参数  - 发送的数据大小
								第4个参数  - 发送的标志，一般给0即可
								第5个参数  - 发送数据的目标地址(发到哪里去)
								第6个参数  - 目标地址的大小
								返回值     - 成功返回发送的数据大小，失败返回-1
								函数功能   - 主要用于向指定的地址发送指定的数据

						2、 recvfrom 函数
							#include <sys/types.h> <sys/socket.h>
							ssize_t recv(int sockfd, void *buf, size_t len, int flags);
							ssize_t recvfrom(int sockfd, void *buf, size_t len, int flags,struct sockaddr *src_addr, socklen_t *addrlen);
								第1个参数  - socket描述符,socket函数的返回值
								第2个参数  - 缓冲区首地址(接受到的数据存到哪里去)
								第3个参数  - 接受的数据大小
								第4个参数  - 接受的标志，一般给0即可
								第5个参数  - 数据发送方的地址(来电显示的功能)
								第6个参数  - 数据发送方的地址大小
								返回值     - 成功返回接受的数据大小，失败返回-1
								函数功能   - 主要用于接受数据并保存发送方地址
				3、TCP协议和UDP协议的比较
					1、TCP协议
						TCP协议 - 传输控制协议,面向连接的协议,类似打电话
						建立连接 => 数据传递和通信 => 断开连接
						在通信的整个全程保持连接
						优点：
							可以自动重发一切错误数据，保证数据的正确性和完整性
							数据接受方可以通知发送方进行数据流量的控制
						缺点：
							服务器端的压力比较大,资源占用率比较高
					2、UDP协议
						UDP协议 -用户数据报协议,非面向连接协议,类似发短信
						优点：
							服务器的压力比较小,资源占用率比较低
						缺点：
							不会自动重发错误数据，不能保证数据的正确性和完整性
							数据接受方也不能通知发送方进行流量的控制


10、线程
	1、基本概念
		线程 - 隶属于进程,是进程内部的程序流,目前主流的操作系统支持多进程，而在每一个进程的内部，又可以支持多线程
		进程 - 是重量级的，每个进程都需要独立的内存空间，所以新建进程对于系统中的资源消耗比较大;而线程是轻量级的，线程共享所在进程的内存资源等,但是每一个线程会需要一个很小的独立的栈区
 
	2、线程的创建
		1、 pthread_create 函数
			#include <pthread.h>
			int pthread_create(pthread_t *thread, const pthread_attr_t *attr,void *(*start_routine) (void *), void *arg);
				第1个参数   - 指针变量，存储线程的ID
				第2个参数   - 线程的属性，一般给NULL即可
				第3个参数   - 函数指针，表示新线程指定的功能函数
				第4个参数   - 用于作为第三个参数的实参
				返回值      - 成功返回0，失败返回错误编号
				函数功能    - 主要用于在调用的进程中启动新的线程
				注意：
				   a.编译链接时加选项： -pthread
				   b.在同一个进程内部可以启动新线程，其中新线程叫做子线程，原来的线程叫做主线程，进程中的多个线程之间相互独立又相互影响，当主线程结束时，进程结束，而进程一旦结束，进程中的所有线程都会结束

		2、 pthread_self 函数
			#include <pthread.h>
			pthread_t pthread_self(void);
			函数功能：
			   主要用于获取当前正在调用线程的ID
	3、线程的等待和分离，建议：启动线程之后,要么设置为分离状态，要么设置为等待/加入的状态
		1、 pthread_join 函数
			#include <pthread.h>
			int pthread_join(pthread_t thread, void **retval);
				第1个参数  - 线程的ID
				第2个参数  - 二级指针将目标线程的退出状态信息拷贝到*retval指向的位置
				函数功能   - 主要用于等待指定的线程终止，并且获取退出状态信息
		2、 pthread_detach 函数
			#include <pthread.h>
			int pthread_detach(pthread_t thread);
			函数功能：
			   主要用于将参数指定的线程标记为分离状态，分离状态的线程结束时，会自动释放资源给系统，不需要其他线程的等待/加入
	4、线程的终止和取消
		1、 pthread_exit 函数
			#include <pthread.h>
			void pthread_exit(void *retval);
			函数功能：
			   主要用于终止当前正在运行的线程,通过retval参数返回当前线程的退出状态，该退出状态被会同一个进程中的其他线程通过调用 pthread_join 函数进行获取

		2、 exit()/_exit()/_Exit() - 终止当前进程,进程中所有的线程也就随着进程终止
		3、 pthread_cancel 函数
				#include <pthread.h>
				int pthread_cancel(pthread_t thread);
				函数功能：
				   主要用于给参数指定的线程发送取消的请求,是否被取消以及何时被取消取决于两个属性： state 和 type

			#include <pthread.h>
			int pthread_setcancelstate(int state, int *oldstate);
				第1个参数  - 设置线程的取消状态,新状态
					PTHREAD_CANCEL_ENABLE   - 允许被取消(默认状态)
					PTHREAD_CANCEL_DISABLE  - 不允许被取消
				第2个参数  - 用于带出线程之前的旧状态，可以给NULL   
				函数功能   - 主要用于设置线程的取消状态,也就是是否允许被取消

			int pthread_setcanceltype(int type, int *oldtype);
				第1个参数 - 设置新的取消类型
					PTHREAD_CANCEL_DEFERRED     - 延迟取消(默认类型)
					PTHREAD_CANCEL_ASYNCHRONOUS - 立即取消
				第2个参数 - 获取旧类型,可以给NULL
				函数功能  - 主要用于设置线程的取消类型，也就是何时被取消		   
	5、线程的同步问题
		1、基本概念
			多线程之间共享进程中的资源，当多个线程同时访问进程中的共享资源时，可能引发数据的混乱，为了避免数据的混乱和不一致性，则需要将多个线程进行协调，而线程之间的协调和通信叫做线程的同步问题
		2、实现线程同步的基本思想
			让多线程分别串行访问共享资源，而不是并行

		3、使用互斥量(互斥锁)实现线程同步的基本流程
			1、 定义互斥量
				pthread_mutex_t mutex;
			2、 初始化互斥量
				pthread_mutex_init(&mutex,NULL);
			3、使用互斥量对共享资源的访问进行加锁
				pthread_mutex_lock(&mutex);
			4、访问共享资源
			5、使用互斥量对共享资源的访问进行解锁
				pthread_mutex_unlock(&mutex);
			6、如果不再使用，则销毁互斥量
				pthread_mutex_destroy(&mutex);

		4、使用信号量来实现线程的同步问题
			1、基本概念
				信号量 - 本质就是一个计数器，主要用于控制同时访问共享资源的进程数/线程数
				当信号量的值为1时，效果等同于互斥量
			2、使用信号量的基本流程
				#include <semaphore.h>
				a.定义信号量
				  sem_t sem;
				b.初始化信号量
				  sem_init(&sem,0,最大值);
				  => 0表示该信号量主要用于控制一个进程中的线程数
				  => 非0表示该信号量主要用于控制进程的个数
				c.获取信号量(信号量减1)
				  sem_wait(&sem);
				d.使用共享资源
				e.释放信号量(信号量加1)
				  sem_post(&sem);
				f.如果不再使用，则销毁信号量
				  sem_destroy(&sem);



1、问题
	ctrl + c 中断了for之后后面的代码会执行吗？



