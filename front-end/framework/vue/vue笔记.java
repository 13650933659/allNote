
路线就是es6,nodejs,vue基础语法,webpack,vuerouter,vuex

1、vue扩展知识介绍
	1、 作者：尤雨溪
	2、 引入 <script type="text/javascript" src="../js/vue.js"></script>
	3、 装谷歌浏览器的vue调试插件以后再去装
	4、 参考文档
		英文官网: https://vuejs.org/ 
		中文官网: https://cn.vuejs.org/
	5、MVVM
		M = Model 是数据, data
		V = View 是模板
		VM = view model vue 的实例对象



3、模板语法(基本语法)
	1、 模板理解
      1、动态的html页面
	  2、包含了一些JS语法代码
		a.双大括号表达式
		b.指令(以v-开头的自定义标签属性)
	2、基本语法
		1、双大括号表达式： {{exp}} 或 {{content.toUpperCase()}}		// 这个用于 p div 等标签， input 请用 v-model
		2、指令一: 强制数据绑定
			功能: 指定变化的属性值
			语法:
				v-bind:xxx='yyy' 或者 :xxx='yyy'  // yyy会作为表达式解析执行，xxx是html的一些属性例如：href 如果是input 的比较适合使用 v-model
			vm
				data: {
					yyy: 'yyy'
				}
		3、指令二: 绑定事件监听
			功能: 绑定指定事件名的回调函数
			语法:
				v-on:click='fun' 或者 @click='fun'
			vm
				methods: {
					fun () {
						alert('点击事件');
					}
				}

		4、计算属性：在页面中使用{{fullName1}} 或者 v-model="fullName1" 来显示计算的结果
			computed: {
				fullName1 () { // 执行： 1、初始化 2、 firstName 或者 lastName 变化时 
					console.log('fullName1()', this)	// this 是vm
					return this.firstName + '-' + this.lastName
				}
			 },
		5、监视属性
			watch: {
				firstName: function (value) { // 执行： firstName 变化时 
					console.log('watch firstName', value)
					// 更新fullName2
					this.fullName2 = value + '-' + this.lastName
				}
			}
			或者
			vm.$watch('lastName', function (value) {	// 执行： lastName 变化时 	， 可能第二个参数就是原始的值 originV
				console.log('$watch lastName', value)
				// 更新fullName2
				this.fullName2 = this.firstName + '-' + value
			})
		6、计算属性高级: 通过getter/setter实现对属性数据的显示和监视，计算属性存在缓存, 多次读取只执行一次getter计算
			computed: {
				fullName3: {
					get () {  // 执行： 当获取当前属性值时自动调用
						console.log('fullName3 get()')
						return this.firstName + '-' + this.lastName
					},
					
					set (value) { // 执行： 当属性值发生了改变时自动调用
						console.log('fullName3 set()', value)
						// 更新firstName和lastName
						const names = value.split('-')
						this.firstName = names[0]
						this.lastName = names[1]
					}
				}
			}
		7、样式绑定
			1、在应用界面中, 某个(些)元素的样式是变化的 class/style 绑定就是专门用来实现动态样式效果的技术
			2、 class绑定
				xxx是字符串  <p :class="myClass">xxx是字符串</p>
				xxx是对象    <p :class="{classA: true, classB: false}">xxx是对象</p>
				xxx是数组	 <p :class="['classA', 'classB']">xxx是数组</p>
			3、 style绑定
			  <p :style="{color:activeColor, fontSize:fontSize + 'px'}">style绑定 ，其中 activeColor/fontSize 是data属性</p>
		8、条件渲染
			1、条件渲染指令
			   <p v-if="ok">表白成功</p>
			   <p v-else>表白失败</p>
			   <p v-show="ok">求婚成功</p>
			   <p v-show="!ok">求婚失败</p>
			2、比较 
				v-if 不符合条件的元素不存在， 而 v-show 不符合条件的则是隐藏效果（隐藏好像有两种，一种是会占位，一种不会占位置）
				如果需要频繁切换 v-show 较好

		9、列表渲染（搜索、排序）
			1、列表显示
				数组: v-for / index		// 数组的变化要引起界面的重新渲染需要调用变异的方法
					<ul>
						<li v-for="(p, index) in filterPersons" :key="index">
							{{index}}--{{p.name}}--{{p.age}}
							<button @click="deleteP(index)">删除</button>	// persons.splice(index, 1) // 调用了不是原生数组的splice(), 而是一个变异(重写)方法（1. 调用原生的数组的对应方法 2. 更新界面）
							<button @click="updateP(index, {name:'Cat', age: 16})">更新</button>	// persons[index] = newP  这种vue根本就不知道，要调  persons.splice(index, 1, newP)	// 从index开始生成1个再加newP(相当于更新)
						</li>
					</ul>
					<button @click="addP({name: 'xfzhang', age: 18})">添加</button>		// persons.push(newP)
					<div>
						<button @click="setOrderType(2)">年龄升序</button>
						<button @click="setOrderType(1)">年龄降序</button>
						<button @click="setOrderType(0)">原本顺序</button>
						<input type="text" v-model="searchName">	// 搜索功能
					</div>
					data: {
						searchName: '',
						orderType: 0, // 0代表不排序, 1代表降序, 2代表升序
						persons: [
							{name: 'Tom', age:18},
							{name: 'Jack', age:17},
							{name: 'Bob', age:19},
							{name: 'Mary', age:16}
						]
					},

					computed: {
						filterPersons () {
							// 取出相关数据（只要有一个变动了，就会执行此方法）
							const {searchName, persons, orderType} = this
							// let arr = [...persons]	？？？ES6新语法
							let arr = persons
							
							if(searchName.trim()) { // 过滤数组（搜索功能）
								arr = persons.filter(p => p.name.indexOf(searchName)!==-1)
							}
							
							if(orderType) {	// 排序功能
								arr.sort(function (p1, p2) {
									if(orderType===1) { // 降序
										return p2.age-p1.age
									} else { // 升序
										return p1.age-p2.age
									}
								})
							}
							return arr
						}
					},

					methods: {
						setOrderType (orderType) {	// 排序按钮监听
							this.orderType = orderType
						}
					}
				对象: v-for / key
					<ul>
						<li v-for="(item, key) in persons[1]" :key="key">{{key}}={{item}}</li>
					</ul>

		10、事件处理
			<h2>1. 绑定监听</h2>
				<button @click="test1">test1</button>
				<button @click="test2('abc')">test2</button>
				<button @click="test3('abcd', $event)">test3</button>

			<h2>2. 事件修饰符</h2>
				<a href="http://www.baidu.com" @click.prevent="test4">百度一下</a>		// 阻止默认行为 相当于之前的 event.defaultPrevent
				<div style="width: 200px;height: 200px;background: red" @click="test5">
					<div style="width: 100px;height: 100px;background: blue" @click.stop="test6"></div>	// 阻止事件冒泡
				</div>

			<h2>3. 按键修饰符</h2>
				<input type="text" @keyup.13="test7">			// 监听确定按键（event.keyCode == 13）
				<input type="text" @keyup.enter="test7">		// 监听确定按键（event.keyCode == 13）
		11、表单数据的自动收集： 使用v-model(双向数据绑定)自动收集数据(text/textarea/checkbox/radio/select)
			<div id="demo">
				<form action="/xxx" @submit.prevent="handleSubmit">		// 阻止表单提交事件，调用 handleSubmit 方法
					<span>用户名: </span>
						<input type="text" v-model="username"><br>

					<span>密码: </span>
						<input type="password" v-model="pwd"><br>

					<span>性别: </span>	// 单选按钮
						<input type="radio" id="female" value="女" v-model="sex"><label for="female">女</label>
						<input type="radio" id="male" value="男" v-model="sex"><label for="male">男</label><br>
						

					<span>爱好: </span>	// 多选按钮
						<input type="checkbox" id="basket" value="basket" v-model="likes"><label for="basket">篮球</label>
						<input type="checkbox" id="foot" value="foot" v-model="likes"><label for="foot">足球</label>
						<input type="checkbox" id="pingpang" value="pingpang" v-model="likes"><label for="pingpang">乒乓</label><br>

					<span>城市: </span>	// 下拉单选（以后扩展成下拉多选）
						<select v-model="cityId">
						  <option value="">未选择</option>
						  <option :value="city.id" v-for="(city, index) in allCitys" :key="city.id">{{city.name}}</option>
						</select><br>
					<span>介绍: </span>
					<textarea rows="10" v-model="info"></textarea><br><br>

					<input type="submit" value="注册">
				</form>
			</div>

			new Vue({
				el: '#demo',
				data: {
					username: '',
					pwd: '',
					sex: '男',		// 初始值
					likes: ['foot'],	// 初始值
					allCitys: [{id: 1, name: 'BJ'}, {id: 2, name: 'SS'}, {id: 3, name: 'SZ'}],	// 初始值(以后考虑后台加载，而且要支持搜索)
					cityId: '2',	// 初始值（这个就选中了）
					info: ''
				},
				methods: {
					handleSubmit () {
						console.log(this.username, this.pwd, this.sex, this.likes, this.cityId, this.info)
						alert('提交注册的ajax请求')
					}
				}
			})

		12、 vue 生命周期
			1、 vue对象的生命周期
				1、初始化显示
					* beforeCreate()
					* created()
					* beforeMount()
					* mounted()
				2、更新状态
					* beforeUpdate()
					* updated()
				3、销毁vue实例: vm.$destory()
					* beforeDestory()
					* destoryed()
			2、常用的生命周期方法
				created()/mounted(): 发送ajax请求, 启动定时器等异步任务
				beforeDestory(): 做收尾工作, 如: 清除定时器，防止内存泄露

		13、动画（有用到请看细看 14讲）
			1. vue动画的理解
				操作css的 trasition 或 animation
				vue会给目标元素 添加/移除 特定的class
			2. 基本过渡动画的编码
				1). 在目标元素外包裹<transition name="xxx">
				2). 定义class样式
				1>. 指定过渡样式: transition
				2>. 指定隐藏时的样式: opacity/其它
			3. 过渡的类名
				xxx-enter-active: 指定显示的 （transition: opacity 1s 1秒展现）（transition: all 1s  1秒展现）
				xxx-leave-active: 指定隐藏的 （transition: opacity 1s 1秒隐藏）（transition: all 3s  3秒隐藏）
				xxx-enter: 指定显示时的样式    （opacity: 0;transform: translateX(120px) 透明到0-100 偏移 120px）
				xxx-leave-to: 指定隐藏时的样式 （opacity: 0;transform: translateX(120px) 透明到100-0 偏移 120px）
		14、过滤器
			1、定义过滤器
				Vue.filter(filterName, function(value [,format,arg2,...]){
					// 进行一定的数据处理
					// return moment(value).format(format || 'YYYY-MM-dd');		// 比如时间显示格式的转换，moment.js 依赖对时间的格式化处理，需要再去引入
					return newValue
				})
			2、使用过滤器
				<div>{{myData | filterName}}</div>
				<div>{{myData | filterName(arg)}}</div>

		15、指令
			1、常用内置指令
				1、 v:text : 更新元素的 textContent
				2、 v-html : 更新元素的 innerHTML
				3、 v-if : 如果为true, 当前标签才会输出到页面
				4、 v-else: 如果为false, 当前标签才会输出到页面
				5、 v-show : 通过控制display样式来控制显示/隐藏
				6、 v-for : 遍历数组/对象
				7、 v-on : 绑定事件监听, 一般简写为@
				8、 v-bind : 强制绑定解析表达式, 可以省略v-bind
				9、 v-model : 双向数据绑定
				10、 ref : 为某个元素注册一个唯一标识, vue对象通过$refs属性访问这个元素对象(this.$refs.msg.innerHTML)
				11、 v-cloak : 使用它防止 {{content}} 从后台请求数据需要时间，出现闪现问题, 与css配合: [v-cloak] { display: none } 先隐藏，后面在显示	// ([v-cloak]属性选择器)
				12、 注册指令(也叫自定义指令)
					1. 注册全局指令(针对索引Vue)
						Vue.directive('my-toupperCase', function(el, binding){	// el: 指令所在的标签对象 binding: 包含指令相关数据的容器对象
							el.innerHTML = binding.value.toupperCase()
						})
					2. 注册局部指令(针对当前Vue)
						directives : {
							'my-toupperCase' : {
								bind: function(el, binding) {
									el.innerHTML = binding.value.toupperCase()
								}
							}
						}
					3. 使用指令
						v-my-toupperCase='xxx'	// xxx好像没什么用

		16、插件
			1、 插件文件 vue-myPlugin.js 
				(function (window) {
				  const MyPlugin = {}
				  MyPlugin.install = function (Vue, options) {
					// 1. 添加全局方法或属性
					Vue.myGlobalMethod = function () {
					  console.log('Vue函数对象的myGlobalMethod()')
					}

					// 2. 添加全局资源
					Vue.directive('my-directive',function (el, binding) {
					  el.textContent = 'my-directive----'+binding.value
					})

					// 4. 添加实例方法
					Vue.prototype.$myMethod = function () {
					  console.log('vm $myMethod()')
					}
				  }
				  window.MyPlugin = MyPlugin
				})(window)
			2、 使用
					<script type="text/javascript" src="vue-myPlugin.js"></script>
					<script type="text/javascript">
					  // 声明使用插件(安装插件: 调用插件的install())
					  Vue.use(MyPlugin) // 内部会调用插件对象的install()

					  const vm = new Vue({
						el: '#test',
						data: {
						  msg: 'HaHa'
						}
					  })
					  Vue.myGlobalMethod()
					  vm.$myMethod()
					</script>



4、基于脚手架编写vue项目
	1、使用vue创建项目
		1、安装好 nodejs 和 npm 
			1、 https://www.jianshu.com/p/13f45e24b1de	// 最新的node包括了 npm
			2、 使用 npm 全局安装的包都会放在 C:\Users\Administrator\AppData\Roaming\npm\node_modules\ 目录（默认）
		2、 npm install -g cnpm --registry=https://registry.npm.taobao.org 听说这个淘宝的 npm 镜像仓库，会比较快 使用命令 把 npm 改为 cnpm
		2、安装 vue-cli : cnpm install -g vue-cli 安装之后就可以使用 vue 命令了
		3、创建我们的本地项目
			1、 vue init webpack vue_demo	// 初始化项目 webpack 是一个模板
				? Project name <vue_demo> // 项目名称必须小写
				? Project description 项目描述
				? Author 项目作者
				? Vue build standalone				// 回车即可
				? Install vue-router? No			// 暂时不按照路由，要的话自己安装
				? Use ESLint to lint your code? Yes	// 先用一下
				? Pick an ESLint preset Standard	// 回车即可
				? Set up unit tests No
				? Setup e2e tests with Nightwatch? No
				? Should we run npm install for you after the project has been create	// 问你怎么下载你的项目
					Yes, use NPM
					Yes, use Yarn
					No, I will handle that myself	
				// 我这里就选择 No, I will handle that myself 
				cd vue_demo
				cnpm install		// 执行这个会安装到 node_modules
					30 packages are looking for funding  run `npm fund` for details  found 60 vulnerabilities (22 low, 14 moderate, 22 high, 2 critical) run `npm audit fix` to fix them, or `npm audit` for details
					如果报了上面的错误 请参考 https://blog.csdn.net/u014641168/article/details/106398182 执行  npm fund 即可
				cnpm run dev
				cnpm run build	// 打包，然后会生成 dist 文件夹，就可以交给后端工程师了
	2、项目结构
		build/				// webpack 相关的配置文件夹(基本不需要修改)
		config/				// webpack 相关的配置文件夹(基本不需要修改)
		node_modules/		// node模块的依赖
		package.json		// 应用包配置文件
		static/				// 静态资源文件夹
		index.html			// 主页面
		src/				// 源码文件夹
			components/		// 组件的文件夹
			main.js			// 已经配置好了，这是主js  /build/webpack.base.conf.js app: './src/main.js'
			App.vue			// 主组件
		
		
		.babelrc			// babel的配置文件 
		.eslintignore		// eslint 检查忽略的配置
		.eslintrc.js		// eslint 检查的配置 
		.gitignore			// git 版本管制忽略的配置
		README.md			// 应用描述说明的 readme 文件
	3、Vue组件的开发(局部的功能页面(html+js+css))
		1、组件的开发
			<template>
			  <div>
				<h1>{{msg}}</h1>
			  </div>
			</template>

			<script>
			  export default {		// 默认写法 export default 其他配置和 new Vue的差不多
				name: '组件名称',
				data () {			// 数据，但是在这里必须使用方法
				  return {
					msg: '世界你好!'
				  }
				}
			  }
			</script>

			<style>
			  h1 {
				color: red;
			  }
			</style>
		2、组件的引用
			<template>
			  <div id="app">
				<img src="./assets/logo.png">
				<!--界面的引用-->
				<HelloWorld/>
			  </div>
			</template>

			<script>
				// 1、引入
				import HelloWorld from './components/HelloWorld'

				export default {
				  name: 'App',
				  components: {		// 2、声明
					HelloWorld: HelloWorld
				  }
				}
			</script>

			<style></style>
	
	4、打包: npm run build  
		1、发布 
			1、使用静态服务器工具包 
				npm install -g serve 
				serve dist 
				访问: http://localhost:5000 
			2、使用动态 web 服务器(tomcat) 
				修改配置: webpack.prod.conf.js output: { publicPath: '/xxx/' //打包文件夹的名称 } 
				重新打包: npm run build 
				修改 dist 文件夹为项目名称: xxx 将 xxx 拷贝到运行的 tomcat 的 webapps 目录下 
				访问: http://localhost:8080/xxx
	
	5、开发技巧
		去掉 eslint jslint jshint 去掉校验
		vue的模板
	6、组件间的通讯
		1、组件间通讯的基本原则
			1、不要在子组件中直接修改父组件的状态数据
			2、数据在哪里，更新数据的行为（函数就应该在哪里定义）
		2、通讯方式
			1、props	
				1、父组件			
					<my-component :age='3' :set-name='setName' />	// 例如 :age='3' :setName='setName'
				2、子组件(在组件内声明所有的 props) 
					1、方式一: 只指定名称 props: ['name', 'age', 'setName'] 
					2、方式二: 指定名称和类型 props: { name: String, age: Number, setNmae: Function }
					3、方式三: 指定名称/类型/必要性/默认值 props: { name: {type: String, required: true, default:xxx}, }
				3、评价
					1) 此方式用于父组件向子组件传递数据 
					2) 所有标签属性都会成为组件对象的属性, 模板页面可以直接引用 
					3) 问题: 
						a. 如果需要向非子后代传递数据必须多层逐层传递 
						b. 兄弟组件间也不能直接 props 通信, 必须借助父组件才可以
			
			2、vue的自定义事件
				1、绑定事件
					1、方式一: 通过 v-on 绑定 @delete_todo="deleteTodo"
					2、方式二: 通过 $on() this.$refs.xxx.$on('delete_todo', function (todo) { this.deleteTodo(todo) })
				2、触发事件(只能在父组件中接收) this.$emit(eventName, data)
				3、评价
					1、此方式只用于子组件向父组件发送消息(数据)
					2、问题: 隔代组件或兄弟组件间通信此种方式不合适
			3、消息订阅与发布（如：pubsub库）
				1、PubSub.subscribe('msg', function(msg, data){})	 // 订阅消息（相当于绑定事件），在 挂载那里做
				2、PubSub.publish('msg', data)						 // 发布消息（相当于触发事件）
				3、评价：优点: 此方式可实现任意关系组件间通信(数据)
			4、slot		// 此方式用于父组件向子组件传递`标签数据`
				1、子组件
					<slot name="xxx">不确定的标签结构 1</slot>		// 占位 xxx
					<div>组件确定的标签结构</div> 
					<slot name="yyy">不确定的标签结构 2</slot>		// 占位 yyy
				2、父组件
					<child>
						<div slot="xxx">xxx 对应的标签结构</div>
						<div slot="yyy">yyyy 对应的标签结构</div>
					</child>
			5、vuex(后面单独讲)

		3、本地存储 window.localStorage 类似 Map<string,string>		// https://www.cnblogs.com/ricolee/p/localstorage-expiretime.html
			1、 设置
				watch: {
					dotos: {
						deep: true,	//深度监测
						handle: function(value) {
							window.localStorage.set('key', JSON.stringify(value))		// 5秒有效期请使用 localStorage.setExpire('userId','zhangsan',5000); 
						}

					}
				}
			2、 获取 JSON.parse(window.localStorage.get('key') || '[]')
		4、vue 项目中常用的 2 个 ajax 库
			1、vue-resource(vue 插件, 非官方库, vue1.x 使用广泛)
				1、安装 cnpm install vue-resource --save
				2、编码
					// 引入模块 import VueResource from 'vue-resource' 
					// 使用插件 Vue.use(VueResource) 
					// 通过 vue/组件对象发送 ajax 请求 
					this.$http.get('/someUrl')
						.then((response) => { // success callback 
							console.log(response.data) //返回结果数据 
							}, (response) => { // error callbac 
								console.log(response.statusText) //错误信息
					})
			2、axios(通用的 ajax 请求库, 官方推荐, vue2.x 使用广泛)
				1、安装 cnpm install axios --save
				2、编码
					// 引入模块 import axios from 'axios' 
					// 发送 ajax 请求 
					axios.get(url) .then(response => { console.log(response.data) })
									.catch(error => { console.log(error.message) })
		5、路由器
			1、官方提供的用来实现 SPA(单页应用) 的 vue 插件 
			2、github: https://github.com/vuejs/vue-router 
			3、中文文档: http://router.vuejs.org/zh-cn/ 
			4、下载: cnpm install vue-router --save
			5、编码(三个步骤)
				1、 index.js 定义路由
					import Vue from 'vue'
					import VueRouter from 'vue-router'
					import Home from '../views/Home'
					import About from '../views/About'
					Vue.use(VueRouter)
					export default new VueRouter({ // 多个配置项 
						routes: [	// 路由	
							{ path: '/home', component: Home },		// 路由 /home  如果需要子路由 children: [ { path: 'news', component: News },{ path: '/home/message', component: Message } ]
							{ path: '/about', component: About },	// 路由 /about 如果需要传参数 使用 /about/:id
							{path: '/', redirect: '/about' }		// 根路由默认重定向到 /about
						]
					})
				
				2、 main.js 注册路由
					import Vue from 'vue'
					import App ue from './App.vue'
					import router ue from './router'	// 引用到index.js 默认暴露的 router

					new Vue({
						el: '#app'
						components: {App},
						router: router
					})
				3、使用
					1、router-link : 用来生成路由链接 <router-link to="/xxx">Go to XXX</router-link>	// url 访问 /xxx 即显示他 /xxx 路由
					2、router-view : 用来显示当前路由组件界面 <router-view></router-view>				// 显示，如果需要缓存数据 使用 <keep-alive> <router-view></router-view> </keep-alive>
			6、路由传递数据
				1、路由路径携带参数
					1、路由配置
						{
							path: 'mdetail/:id',
							component: MessageDetail
						}
					2、路由传参数
						<router-link :to="`/home/message/mdetail/${m.id}`">
					3、路由组件中读取
						this.$router.params.id
				2、<router-view>属性携带
					1、<router-view>传参
						<router-view :msg="msg"></router-view>
					2、路由组件中读取
						this.$router.params.id
			7、编程式路由（即：路由的跳转）
				1、相关api
					1、 this.$router.push(path)    // 相当于点击路由连接
					2、 this.$router.replace(path)    // 用新的路由替换当前路由（不可以返回到当前路由界面）
					3、 this.$router.back()    // 返回上一个记录路由
					4、 this.$router.go(-1)    // 返回上一个记录路由
					5、 this.$router.go(1)    // 请求下一个路由

	7、vuex
		1、介绍
			github 站点: https://github.com/vuejs/vuex
			在线文档: https://vuex.vuejs.org/zh-cn/	
			简单来说: 对 vue 应用中多个组件的共享状态进行集中式的管理(读/写)
		2、编码
			1、 store.js		// 为了避免文件越写越大，使用一个store 文件来管理 index.js/state/mutations/actions/actions 单独写一个文件
				import Vue from 'vue'
				import Vuex from 'vuex'
				Vue.use(Vuex)			// vue的插件才需要这句话

				const state = {
					count: 0
				}
				const mutations = {
					// 增加的mutation
					INCREMENT (state) {
						state.count ++;
					}
					// 减少的mutation
					DECREMENT (state) {
						state.count --;
					}
				}
				const actions = {
					increment ({commit,state}) {
						// 提交一个 mutation 
						commit('INCREMENT', [obj]);
					},
					// 异步的action
					incrementAsync ({commit,state}) {
						setTimeout(() => {
							commit('INCREMENT', [obj]);
						})
					},
				}
				const getters = {
					evenOrOdd (state) {		// 不需要调用，读取属性就可以了自动调用
						return state.count%2===0 ? '偶数' : '奇数';
					}

				}

				export default new Vuex.Store({
					state,		// 状态对象
					mutations,	// 包含多个更新state函数的对象
					actions,	// 包含多个对应事件回调函数对象
					getters		// 包含多个getters计算数属性函数对象 
				})
			2、main.js
				import Vue from 'vue'
				import App from './App'
				import store from './store'
				import {mapState, mapGetters, mapActions} from 'vuex'

				export default {
					computed : {
						// 新写法(简化编码)
						...mapState(['count']),				// 返回 {count () {return this.$store.getters['count']}}
						...mapGetters(['evenOrOdd']),		// 返回 {evenOrOdd () {return this.$store.getters['evenOrOdd']}}

						// 老的写法
						evenOrOdd () {
							return this.$store.getters.evenOrOdd
						},
						count () {
							return this.$store.getters.count
						}
					},
					methods: {
						...mapAction(['increment','decrement','incrementIfOdd'])	// (简化编码) 或者 ...mapAction({increment:'increment',decrement:'decrement'})
						
					}
				}


				new Vue({
					el: '#app',
					components: {App},
					template: '<App/>',
					store: store		// 这样一配置所有的组件对象都拥有一个 $store 对象
				})
			3、引用的地方 使用 App.vue 来举例子
				{{$store.state.count}}		// 读取数据
				this.$store.dispatch('increment',[obj])		// 触发 store 中对应的action 调用

	8、新写法 render
		new Vue({
			el: '#app',
			components: {App},
			template: '<App/>',
			store: store		// 这样一配置所有的组件对象都拥有一个 $store 对象
		})
		等于
		new Vue({
			el: '#app',
			render: h => h(App),
			render: function(createElement){		// 渲染函数
				return createElement(App);	//返回 <App/>
			}, 
			store: store		// 这样一配置所有的组件对象都拥有一个 $store 对象
		})

		
1、理解
	eslint		// 规范的检查
	webpack		// 模块的打包工具（把 .js/.sass/.vue/.less 等等 打包成 .js/.css 浏览器可以运行的代码）


1、 element UI的使用：
	1、找官网  http://element.eleme.io/#/zh-CN/component/quickstart
	2、安装  cnpm i element-ui -S         -S表示  --save
	3、 main.js 引入element UI的css 和 插件，这种事全部引入，可以考虑使用按需引入
		import ElementUI from 'element-ui';
		import 'element-ui/lib/theme-chalk/index.css';
		Vue.use(ElementUI);
	4、/webpack.config.js  配置file_loader	（我没这个问题）      http://element.eleme.io/1.4/#/zh-CN/component/quickstart
		去 /package.json 检查是否安装了 file-loader ，如果没安装则使用 cnpm install file-loader --save	// 或者 --save-dev
		  {
			test: /\.(eot|svg|ttf|woff|woff2)(\?\S*)?$/,
			loader: 'file-loader'
		}
	5、看文档直接使用。
		1、提示组件
			<TransparentBubble v-show="isShowRelation">
			  <template v-slot:content>
				<div class="tips-row">
				  <span>并关系：</span>指公告中同时包含所添加的所有关键词才会被匹配到
				</div>
				<div class="tips-row">
				  <span>或关系：</span>指公告中包含所添加的其中任意关键词都会被匹配到
				</div>
			  </template>
			</TransparentBubble>
		2、form表单
			<el-form label-position="right" label-width="110px">

			1、label
				<el-form-item label="信息标题">
					<div>招标订阅{{ titleIndex }}</div>
				</el-form-item>
			2、下拉多选
				<el-form-item>
				  <div slot="label" class="labelbox"><span class="redstar">*</span>信息类型</div>
				  <el-select v-model="formData.docchnnel" multiple collapse-tags placeholder="请选择信息类型" class="width240">
					<el-option
					  label="全部类型"
					  value=""
					/>
					<el-option
					  v-for="item in infoType"
					  :key="item.value"
					  :label="item.label"
					  :value="item.value"
					/>
				  </el-select>
				</el-form-item>
			3、下来单选
				<div class="row-item">
					<el-form-item>
					  <div slot="label" class="labelbox"><span class="redstar">*</span>搜索范围</div>
					  <el-select v-model="formData.scope" placeholder="请选择搜索范围" class="width240">
						<el-option
						  v-for="item in options"
						  :key="item.detailId"
						  :label="item.label"
						  :value="item.label"
						/>
					  </el-select>
					</el-form-item>
					<el-form-item>
					  <div slot="label" class="labelbox"><span class="redstar">*</span>搜索模式</div>
					  <el-select v-model="formData.pattern" placeholder="请选择搜索模式" class="width240">
						<el-option
						  v-for="item in serachPattern"
						  :key="item.detailId"
						  :label="item.label"
						  :value="item.value"
						/>
					  </el-select>
					</el-form-item>
				  </div>
			4、切换组件
				 <el-form-item label="订阅推送">
					<el-switch
					  v-model="formData.status"		// boolean
					  active-color="#1FC236"
					  inactive-color="#CCCCCC"
					  active-text="开启"
					/>
				  </el-form-item>
			5、多选(checkbox)
				<el-form-item label="推送方式">
					<el-checkbox-group v-model="checkPushList">
					  <el-checkbox label="短信" />
					  <el-checkbox label="邮箱" />
					</el-checkbox-group>
				  </el-form-item>
			6、input文本框
				<el-form-item v-if="showEmailOrPhone.isPhone" label="手机号码">
				  <el-input v-model.trim="formData.phome" type="text" onkeyup="value=value.replace(/[^\d]/g,'')" maxlength="11" class="info-input" placeholder="请输入手机号码" />
				</el-form-item>
			7、表格
				<el-table
					:data="tableData"
					border
					style="width: 100%"
				  >
					<el-table-column
					  fixed
					  type="index"
					  label="序号"
					  width="50"
					/>
					<el-table-column
					  label="网站名称"
					  width=""
					>
					  <template slot-scope="scope">
						<el-input v-model="scope.row.webName" class="info-input" placeholder="请输入网站名称" />
					  </template>
					</el-table-column>
					<el-table-column
					  label="网站地址"
					  width=""
					>
					  <template slot-scope="scope">
						<el-input v-model="scope.row.webUrl" class="info-input" placeholder="请输入网站名称" />
					  </template>
					</el-table-column>
					<el-table-column
					  prop="status"
					  label="审核状态"
					  width="92"
					>
					  <template slot-scope="scope">
						<span v-if="scope.row.status===0 && !scope.row.id">-</span>
						<span v-if="scope.row.status===0 && scope.row.id">未审核</span>
						<span v-if="scope.row.status===1">审核通过</span>
						<span v-if="scope.row.status===2">审核不通过</span>
					  </template>
					</el-table-column>
					<el-table-column
					  label="操作"
					  width="64"
					>
					  <template slot-scope="scope">
						<el-button type="text" size="small" @click="deleteWebsite(scope.$index)"><img v-if="!scope.row.id" src="@/assets/images/tj_shanchu.png"></el-button>
					  </template>
					</el-table-column>
				  </el-table>
				  <div class="add-website" @click="addWebsite">
					<i class="el-icon-plus" />添加源网站
				  </div>
				addWebsite() {	 // 表格添加数据
				  this.tableData.push({ id: 0, webName: '', webUrl: '', status: 0 })
				}
			8、单选 radio
				<el-radio-group v-model="sjType" @change="timeTypeChange">
					<el-radio label="10" value="10">今日更新</el-radio>
					<el-radio label="20" value="20">近三天更新</el-radio>
					<el-radio label="30" value="30">近一周更新</el-radio>
					<el-radio label="40" value="40">所有更新</el-radio>
				  </el-radio-group>
			9、分页导航栏
				<div v-show="dataList.list.length > 0" class="pagingPage">
				  <div class="block">
					<el-pagination
					  background
					  :current-page="currentPage"
					  :page-size="12"
					  layout="prev, pager, next, jumper"
					  :total="
						Number(dataList.totalCount) > 600
						  ? 600
						  : Number(dataList.totalCount)
					  "
					  prev-text="上一页"
					  next-text="下一页"
					  :pager-count="5"
					  @current-change="handleCurrentChange"
					/>
					<p class="totalNumber">
					  共找到 <span>{{ dataList.totalCount }}</span> 条数据
					</p>
					<p class="aKeyexport" @click="handleExport">一键导出项目</p>
				  </div>
				</div>

this.$toast.show('请勿重复添加排除词', 3000)
16551000557 