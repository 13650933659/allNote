

1、java：控件/容器/布局
	[1] JFrame jf=new JFrame(); 最大的面板
	 <1> jf.setLocation(200,200);    ==>>设置窗口左上角的坐标
	 <2> jf.setTitle("用户登录");    ==>>设置窗口标题
	 <3> jf.setSize(800,200);        ==>>设置窗口大小
	 <4> jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       ==>>设置关闭窗口就退出jvm虚拟机
	 <5> jf.setResizable(false);     ==>>窗口大小不允许改变
	 <6> jf.setVisible(true);        ==>>显示窗口
	 <7> jf.setLayout("边界");       ==>>设置布局方式，默认为边界布局BorderLayout.CENTER == "Center"
	 <9> jf.setLayout("流式");       ==>>设置流式布局，jf.setLayout(new FlowLayout(FlowLayout.RIGHT右靠/.LEFT左靠/默认居中));
	 <9> jf.setLayout("卡片");       ==>>设置卡片布局，cl=jf.setLayout(new CardLayout());A.如果那个容器是卡片布局的话，默认显示第一张加入的卡片，用cl.show(jp1,"卡片的名字");
	 <10> jf.setLayout("网格");      ==>>设置网格布局，jf.setLayout(new GridLayout(3行,3列,10垂直,10水平));
	 <11> jf.add("组件/小容器");     ==>>添加组件或者小容器
	 <12> jf.setUndecorated(true);   ==>>不使用上下框一定要放在显示之前
       
	[2] JPanel jp1=new JPanel();                          容器面板：A.选择布局方式 B.添加组件 C.jp1.setBackground(Color.BLUE/new Color(0,0,255))设置背景颜色
	[3] JLabel jl1=new JLabel("你喜欢",JLabel.CENTER);    标签：A.图片/文字/html标签,居中 B.jl1.setFont(new Font("宋体",Font.PLAIN,16))宋体粗体16px C.jl1.setForeground(Color.BLUE/new Color(r,g,b))设置颜色  D.jl1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR))设置鼠标经过就变小手
	[4] JTextArea jta=new JTextArea();                    文本域：A.也要用到JScrollPane创造滚动效果
	[4] JTextField jtf1=new JTextField(6);                文本框：A.6是厘米 B.setText()/getText() C.setEditable(false)不可修改的
	[5] JPasswordField jpf1=new JPasswordField(6);        密码框：A.6是厘米
	[6] JRadioButton jrb1=new JRadioButton("男");         创建单选按钮1,要放到单选按钮组的
	[7] ButtonGroup jg1=new ButtonGroup(jrb1);            装单选按钮的小容器 
	[8] JCheckBox jchb1=new JCheckBox("香蕉");            创建复选框1,不用像单选按钮装到组
	[9] JButton jb1=new JButton("确认登陆");              按钮：A.图片/文字按钮  B.jb1.setToolTipText("新建1");设置鼠标经过提示的文字
	[10] JComboBox jcb1=new JComboBox(nxh[]);             下拉列表
	[11] JList jLst1=new JList(nxh[]);                    普通列表
	[11] JScrollPane jsp1=new JScrollPane(jList);         普通列表滚动效果 
	[13] ImageIcon imi=new ImageIcon("images/a.jpg");     创建图片 
	[14] JTabbedPane jtp=new JTabbedPane();               创建标签容器：A.jtp.add("QQ号码",jp2);jtp.add("手机号码",jp3);jtp.add("邮箱号码",jp4);这样设置后就把他加到容器他就会自动在容器的左上角不用参与布局 
	[15] JMenuBar jmb=new JMenuBar();                     菜单条容器（树干）：A.add(一级菜单);加到父容器它自动会加到父容器的左上角的,和标签容器一样
	[16] JMenu jm1=new JMenu("文件(F)","这里可以加图片"); 菜单（树枝）：A.add(二级菜单/菜单项);jm1.setMnemonic('F');设置打开该菜单的快捷键为"F"
	[17] JMenuItem jmi1=new JMenuItem("打开");            菜单项（树叶）：《设置打开该菜单项的快捷键我不会？？？》《设置单项和菜单的分割线我不会？？？》
	[18] JToolBar jtb=new JToolBar();                     工具条容器：A.jb1.setToolTipText("新建1");  B.设置工具栏不可以浮动setFloatable(false);
	[19] JTable jt=new Jtable(rowData,columnName);        表格组件：A.也要用到JScrollPane创造滚动效果
	[20] JSplitPane jsp=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jList,jl1);拆分容器 A.垂直/水平拆分  B.jsp.setOneTouchExpandable(true)=>可以收缩的


2、java：绘图技术

	1、.在继承JPanel时要重写paint方法也一定要用super.paint(g)初始化代码如下：*//
		public void paint(Graphics g){
			super.paint(g);
			//接下来就写你的画图代码了
			//在这里最好不要初始化你要画的那个图像对象
			//初始化最好在MyPanel的构造函数做
			//要不然你在用repaint()时又会初始化,低效还有会覆盖之前的xy值
			//paint()：1.第一次系统自己调用 2.窗口小->大 3.窗口大小变化 4.repaint()函数调用
		}
		[1] 直线：    g.drawLine(x1,y1,x2,y2);
		[2] 矩形：    g.drawRect(x1,y1,width,height);
		[3] 椭圆：    g.drawOval(x1,y1,width,height);   A.注意这里的(x1,y1)不是圆形和php不一样
		[4] 填充矩形：g.fillRect(x1,y1,width,height);
		[5] 填充椭圆：g.fillOval(x1,y1,width,height);
		[5] 弧线：    g.drawArc(x1,y1,w,h,50弧度,100);  A.一个椭圆的50弧度==>> +表示逆时针-表示顺时针
		[6] 字符串：  g.drawString(str,x,y);            A. g.setFont(new Font());设置画笔的字体
		[7] 画笔颜色：g.setColor(new Color(r,g,b));     A.上面的还可以绘3D的只是最后加一个参数bool值
		[8] 画图：    g.drawImage(ImageIO.read(new file("login.gif"),0,0,360,360,this)); A.记得引包<javax imageio.*和java.io.*>并处理异常
		[9] 放图：    jl1=new JLabel(new ImageIcon("login.gif"))  A.不用处理异常，引一个<javax.swing.*>即可
       

3、java：事件处理机制

	1、java的事件处理机制<委派处理机制>：大致步骤 1.实现监听接口的方法   2.注册监听的对象
	事件源：java的对象                                 事件监听者
	比如 jb JFrame         ==>>    事件对象     ==>>   他必须实现接口
	等等表单组件                                       然后一定要实现它一些方法
	记得要注册监听的对象                               其实是把你的逻辑写到这些方法内

       

