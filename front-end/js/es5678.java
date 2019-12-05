看完12


1. ECMA Script 它是一种由ECMA组织（前身为欧洲计算机制造商协会）制定和发布的脚本语言规范
2. 而我们学的JavaScript是ECMA的实现, 但术语ECMAScript和JavaScript平时表达同一个意思
3. JS包含三个部分：
	1). ECMAScript（核心）
	2). 浏览器端扩展
		1). DOM（文档对象模型）
		2). BOM（浏览器对象模型）
	3). 服务器端扩展
		1). Node
4. ES的几个重要版本
	ES5 : 09年发布
	ES6(ES2015) : 15年发布, 也称为ECMA2015
	ES7(ES2016) : 16年发布, 也称为ECMA2016  (变化不大)
5、浏览器对ES5的支持:
	1. IE8只支持defineProperty、getOwnPropertyDescriptor的部分特性和JSON的新特性
	2. IE9不支持严格模式, 其它都可以
	3. IE10和其他主流浏览器都支持了
	4. PC端开发需要注意IE9以下的兼容, 但移动端开发时不需要


6、es5的扩展
	1、严格模式
		1. 理解:
			- 除了正常运行模式(混杂模式)，ES5添加了第二种运行模式："严格模式"（strict mode）。
			- 顾名思义，这种模式使得Javascript在更严格的语法条件下运行
		2.  目的/作用
			- 消除Javascript语法的一些不合理、不严谨之处，减少一些怪异行为
			- 消除代码运行的一些不安全之处，保证代码运行的安全
			- 为未来新版本的Javascript做好铺垫
		3. 使用
			- 在全局或函数的第一条语句定义为: 'use strict';
			- 如果浏览器不支持, 只解析为一条简单的语句, 没有任何副作用
		4. 语法和行为改变
			- 必须用var声明变量
			- 创建eval作用域
			- 禁止this指向window
			- 对象不能有重名的属性
			- 函数不能有重名的形参
		5. 学习参考:
			http://www.ruanyifeng.com/blog/2013/01/javascript_strict_mode.html
	2、 JSON对象
		1. JSON.stringify(obj/arr)
			js对象(数组)转换为json对象(数组)
		2. JSON.parse(json)
			json对象(数组)转换为js对象(数组)
	3、 Object 的扩展
		S5给Object扩展了好一些静态方法, 常用的3个:
		1. Object.create(prototype[, descriptors]) : 创建一个新的对象
			1). 以指定对象为原型创建新的对象
			2). 指定新的属性, 并对属性进行描述
				value : 指定值
				writable : 标识当前属性值是否是可修改的, 默认为true
				get : 用来得到当前属性值的回调函数
				set : 用来监视当前属性值变化的回调函数
		2. Object.defineProperties(object, descriptors) : 为指定对象定义扩展多个属性
		3. 或者在对象中直接定义属性的set/get 方法 如： {name:'zs', set name(name){this.name=name}}
	4、 Array 扩展
		ES5给数组对象添加了一些方法, 常用的5个:
		1. Array.prototype.indexOf(value) : 得到值在数组中的第一个下标
		2. Array.prototype.lastIndexOf(value) : 得到值在数组中的最后一个下标
		3. Array.prototype.forEach(function(item, index){}) : 遍历数组
		4. Array.prototype.map(function(item, index){ }) : 遍历数组返回一个新的数组
		5. Array.prototype.filter(function(item, index){ }) : 遍历过滤出一个新的子数组

	5、 Function 扩展
		Function.prototype.bind(obj) : 将函数内的this绑定为obj, 并将函数返回
		面试题: 区别 bind()与 call()和 apply()?
			bind - 绑定函数的 this ,不执行函数，返回绑定后的函数对象，适合改变回调函数的this
				fun.bind(obj, arg1, arg2...)();
			apply - 绑定函数的 this ，并且立即执行函数
				fun.apply(obj, [arg1,arg2...]);
			call  - 绑定函数的 this ，并且立即执行函数
				fun.call(obj, arg1,arg2...);
7、 es6
	1、 let 关键字
		* 作用:  与var类似, 用于声明一个变量
		* 特点:
			* 在块作用域内有效
			* 不能重复声明
			* 不会预处理, 不存在提升
		* 应用:
			* 循环遍历加监听

	2、 const 关键字
		* 作用:
			* 定义一个常量
		* 特点:
			* 不能修改
			* 也是块作用域有效
		* 应用:
			* 保存应用需要的常量数据

	3、 变量的解构赋值
		* 理解: 
			* 从数组或对象中提取值, 对多个变量进行赋值
		* 数组的解构赋值
			let [a,b] = [1, 'atguigu'];
		* 对象的解构赋值
			let {n, a} = {n:'tom', a:12}
		* 用途
			* 交换2个变量的值
			* 从函数返回多个值
	4、 模板字符串 : 简化字符串的拼接
		1). 模板字符串必须用``
		2). 变化的部分使用${xxx}定义
	5、对象增强表达式
		* 省略同名的属性值
		* 省略方法的function
		* 例如:
		  let x = 1;
		  let y = 2;
		  let point = {
			x,
			y,
			setX (x) {this.x = x}
		  };
	6、 函数扩展
		1. 箭头函数	// 一个参数()可以省略，一条语句{}和return一起省略
			1、 用法： var fun2 = (v) => v+3;
			2、 优点
				1、 简洁
				2、 this 的指向上一级普通函数
		2. 形参的默认值
			function Point(x = 1,y = 2) {
				this.x = x;
				this.y = y;
			}
		3. rest(可变)参数
			function add(... values) {
				let sum = 0;
				for(value of values) {
					sum += value;
				}
				return sum;
			}
		4. 扩展运算符(...)
			var arr1 = [2,3];
			var arr2 = [1,...arr1,4];	// arr2 = [1,2,3,4]
			arr2.push(...arr1);

