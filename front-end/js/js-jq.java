






1、前台 json 字符串 和 对象的互转  参考https://www.cnblogs.com/alsf/p/7528104.html







1、选择器
    1、简单选择器
        1、	最基本的5个
            $('#id') $('.class') $('E') $('*')
        2、	层次选择器
            1、 $( 'p').children('span')《p的所有span标签，如果不带参数则是全部子元素》
            2、 $( 'p').find('span')《一定要带参数》
            3、 $( 'p').next()《下一个》
            4、 $( 'p').prev()《上一个》
            5、 $( 'p').siblings()《所有的兄弟元素》

    2、 过滤选择器《$("p:not(':first')")就是和下面的相反》
        1、	基本过滤器
            1、 $('p:first')/ $('p:last')《第一个/最后一个》
            2、 $('p:odd')/ $('p:even')《奇数个/偶数个》
            3、 $('p:eq(n)')/$('p:gt(n)')/$('p:lt(n)')《第n个/大于第n个的/小于第n个的》
        2、	内容和内容过滤器
            1、 $('p:contains(hi)')《有hi文本的p》
            2、 $('p:has(strong)')《有strong子元素的p》
            3、 $('p:empty')《没有子元素和文本的p》
        3、	动和可见选择器3个
            1、 $(':animated')《动的元素》$('div:hidden')《隐藏的元素》$('div:visible')《可见的的元素》
        4、	属性选择器
            1、 $('p:[title=js]')《有title=js的属性的p属性可以自己定的，用的少》
        5、	表单选择器
            1、 $(':text') $(':password') $(':radio') $(':checkbox')

    3、 DOM方法筛选《支持链式操作：因为他执行完会返回此对象》
        1、	DOM方法筛选
            1、 $( 'p').filter(这里就是上面的过来条件如'：first')《第一个p》
            2、 $( 'p').not(':first');《相反》

			table.children('tr').not(':first').remove();

2、 常用的函数
    1、 DMO操作
        1、	o.html()/o.val()《设置获取都可以 .html 就直接覆盖的》
        2、	$( 'p').text('hi')《设置获取元素的文本》
        3、	$( 'p').end()《链式操作》
        4、	$( 'p').each()《循环上面的结果》
        5、	$( 'p').attr('属性名','属性值')《更新p元素属性值》
        6、	$( 'p').remove()/$( 'p').empty()《移除p元素/清空p元素的所有孩子》
        7、	o=$("<div>我是新建的元素</div>");《创建元素》
        8、	$('body').append(o)/o.appendTo($('body'))《在body元素最后追加某元素》
        9、	$( 'p').after(o)/ o.insertAfter($('p'))《在p元素后面加》
        10、 $( 'p').before(o)/o. insertBefore($('p')) 《在p元素前面加/后面加》
        11、 $('p').wrap（o）《每个p都加一个父元素o》
        12、 $('p').wrapAll（o）《全部p加一个父元素o》
        13、 $('p'). wrapInner（o）《每个p的文本都加一个父元素o》
        14、 $('p').replaceWith(o)/ $(o).replaceAll($('p'))《把p元素替换成o》
    2、 样式操作
        1、	$('p').hasClass('a')《有这个类属性，返回真用于切换样式》
        2、	$('p').addClass('a b')《添加两个类属性，用空格隔开》
        3、	$('p'). removeClass ('a b')《移除两个类属性，用空格隔开》
        4、	$('p'). toggleClass ('a b')《切换样式》
        5、	$('p') .css({'background':'red','color':'yellow'})《可以这样指定样式》
        6、	$('p').height()《p元素的高度》
        7、	$( 'p').outerHeight(ture) 《6+边框*2+padding如果参数是true就再加margin》
        8、	$( 'p').innerHeight()《6+padding》
        9、	$('p').offset().left/$('p').offset().top《相对浏览器的左距离/头距离》
        10、 $('p'). position ().left/$('p'). position ().top《相对父元素左距离/头距离》


3、 事件《高级/和第二种绑定》
	1、 高级绑定事件的方法：
		1、 o.bind(事件.个人空间,匿名函数，boolean)《默认是true,如果设为true就有防止冒泡和表单提交，一般第三个参数我不写》
		2、 o.one(事件.个人空间, 匿名函数，boolean)《只执行一次的绑定事件方法》
		3、 o.unbind(事件)《移除事件，可以移除多个》
	2、 大事件
		1、 $(document).ready(匿名函数)
		2、 $holdReady(boolean)《暂停或恢复ready事件，远程获取js》
	3、 鼠标事件
		1、	o.click(匿名函数)《单击事件》
		2、	o.dbclick(匿名函数)《双击击事件》
		3、	o.focusin(匿名函数)《获取焦点事件》
		4、	o.mousedown(匿名函数)《单击事件不放开也会执行》
		5、	o.mouseove(匿名函数)《鼠标移动事件》
		6、	o.mouseover(匿名函数)/ o.mouseout(匿名函数)《鼠标移入/移出事件》
		7、	o.mouseenter(匿名函数)/ o.mouseleave(匿名函数)《鼠标移入/移出事件,阻止了冒泡》
	4、 键盘事件
		1、	o.keydown(匿名函数)/ o.keypress(匿名函数)《键盘一按就触发》
		2、	o.keyup(匿名函数)《键盘一按一放就触发》
	5、 表单事件
		1、	o.focus(匿名函数)《当控件获得焦点是触发》
		2、	o.blur(匿名函数)《当控件失去焦点是触发》
		3、	o.change(匿名函数)《当有focus和blur一个轮回的事件用于文件上传》
		4、	o.select(匿名函数)《当控件被选中》
		5、	o.submit(匿名函数)《表单提交》
	6、 浏览器事件
		1、	$(window).resize(匿名函数)《浏览器窗口大小改变事件》
		2、	$(window). scroll (匿名函数) 《滚动条改变事件》





