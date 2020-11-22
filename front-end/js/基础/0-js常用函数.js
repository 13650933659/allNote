
//全局函数
001、typeof var1  //得到var1是什么数据类型
001、parseInt("92")  //把字符串转成number,如果转不成功就报错NaN 类型是number
001、parseFloat("92.4")  //把字符串转成number,如果转不成功就报错NaN 类型是number(这里说明一下他的number已经包括全部数值了，如果你用上面的函数就会四舍五入)
001、isNaN("f")  //结果是true,如果不是一个数（其他不是数的字符串、undefined）则返回true，是数（0、1、'0'、'1'、'001'、null、''）返回false 类型是boolean
001、encodeURI(var1)==>>decodeURI(var1)  //对var1进行重新URI编码，再用URI解回来，为了通讯时特殊字符什么的
001、escape(var1)==>>unescape(var1)  //对var1进行重新？？编码，再用？？解回来，为了通讯时特殊特殊字符什么的
001、eval("window.alert('hello world')")  //把字符串当成一个脚本来执行
001、object.hasOwnProperty("name")  //用于指示一个对象自身(不包括原型链)是否具有指定名称的属性。如果有，返回true，否则返回false
001、
001、
001、



//系统对象中的函数

//window对象
001、window.alert("hello world")  //弹出提示窗口，你不点确定程序就无法继续进行
001、window.prompt("请输入一个值")  //取消返回null,确定返回用户输入的值（但是这个是字符串需要用时害的强转）
001、window.confirm("确定吗")  //确定=true 取消=false
001、window.moveTo(x,y)  //给窗口的左上角移动到一个指定的坐标
001、window.resizeTo(400,600)  //把窗口的大小调整到指定的宽度和高度。
001、window.setInterval(test(),1000)=id/clearInterval(id)  //循环定时器，关掉这个页面，就会被处理掉的
001、window.setTimeout(test(),1000)=id/clearimeOut(id)  //一次定时器
001、window.status="我是状态栏"  //状态栏的文字
001、window.location.href="http://baidu.com?user_name=hsp"  //跳转
001、window.oper("url","_blank/_self/_top","目标网页的属性用逗号隔开")/window.close()/目标.window.colse()
001、



	//1、history对象，针对这个网站的历史
	001、history.back()/goback()  //返回前一页两个一样吗
	001、history.go(-index)  //返回指定的前index页
	001、history.forward()  //返回到后一个页面
	001、

	//2、location对象
	001、location.reload()  //刷新网页
	001、location.protocol/href/hostName/port  //协议/得到或者设置请求的url/请求的服务器主机名（ip或者域名）还有的去看文档
	001、

	//3、navigator对象
	001、navigator.appName  //浏览器的名称等信息去看文档
	001、

	//4、screen对象
	001、screen.width/screen.height  //客户机器的分辨率
	001、

	//5、event对象
	001、  //看文档和高级2

	//6、document对象和高级1
	001、 //看文档








//Math对象（静态类）
001、Math.abs(-12)  //结果：12
001、Math.ceil(4.1)  //结果：5天花板
001、Math.floor(4.9)  //结果：4地板
001、Math.raund(4.9)  //结果：5四舍五入
001、Math.random()  //结果：一个大于0小于1的16小数
001、Math.pow(2,3)  //结果：8，2的3次方
001、

//Number对象（类）
001、56.895.toString(默认是十进制你可以改)  //得到"56.895"字符串
001、56.895.toFixed(2)  //结果：56.90，保留精度为2，
001、Number.Max_VALUE  //得到当前浏览器支持的最大数值
001、Number(s, 10)  //把字符串转成数值
001、


//Date对象（类）
001、date.tolocaleString()  //得到客服端本地的时间表示格式
001、  //得到年份//得到月份//得到天份//得到时//得到分//得到秒
001、开始时间不能比结束时间大
		var stime = new Date(distributeStartDate);
		var etime = new Date(distributeEndDate);
		if (stime > etime) {
			ealert("资源分配开始时间不能大于结束时间！");
			return false;
		}


//String对象（类）
001、'bb,'.length  //结果等于3，返回字符串的长度
001、"2,1,h".split(',')  //把字符串切割成数组得到[2,1,"h"]数组
001、"nnyyt".substr(2,1)  //结果：y
001、"abnna".substring(2,3)  //结果：n  //请用：var a = "asd;".substring(0, "asd;".length - 1);
001、"jjhdu".indexOf("d",2)  //结果是3
001、str.replace(/\r\n/ig, '')  //替换全部
001、str.replace('\r\n', '')  //只替换第一个
001、aaddsad.lastIndexOf('a')  //结果：5最后出现的字符的位置
001、parseInt('13')/parseInt('13.6')  //等于数字的13/13（四舍五入）
001、parseFloat('13.6')  //结果：13.6
001、

//Array对象（类）
001、array.pop()  //方法用于删除数组的最后一个元素 并返回最好一个元素的值
001、array.push("1314")  //把"1314"追加到此栈的末尾,
001、array.length  //js的数组长度
001、  //方法用于删除数组的第一个一个元素 并返回最好一个元素的值
001、  //把"1314"追加到此栈的第一个,
001、

 
1、 JSON 对象
	1、 var jsonStr = JSON.stringify(subVos)	// 把js的对象、数组转成字符串，可以提交给后台
	2、 var jsonObj = JSON.parse(result);		// 把json字符串转成js对象、数组，一般用于解析后台返回的json字符串


