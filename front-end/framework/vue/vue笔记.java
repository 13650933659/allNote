
看完 16

1、 作者：尤雨溪
2、 引入 <script type="text/javascript" src="../js/vue.js"></script>
3、 装谷歌浏览器的vue调试插件以后再去装


2、M V VM
	M = Model 是数据, data
	V = View 是模板
	VM = vue 的实例对象



3、语法
	1、 模板语法
      动态的html页面，包含了一些JS语法代码，大括号表达式，指令(以v-开头的自定义标签属性)
	2、双大括号表达式
		功能: 向页面输出数据，可以调用对象的方法
		语法: {{exp}} 或 {{content.toUpperCase()}}
	3、指令一: 强制数据绑定
		功能: 指定变化的属性值
		语法:
			v-bind:xxx='yyy' 或者 :xxx='yyy'  //yyy会作为表达式解析执行
		vm
			data: {
				yyy: 'yyy'
			}
	4、指令二: 绑定事件监听
		功能: 绑定指定事件名的回调函数
		语法:
			v-on:click='fun' 或者 @click='fun'
		vm
			methods: {
				fun () {
					alert('点击事件');
				}
			}

	5、计算属性：在页面中使用{{fullName1}} 或者 v-model="fullName1" 来显示计算的结果
		computed: {
			fullName1 () { // 执行： 1、初始化 2、 firstName 或者 lastName 变化时 
				console.log('fullName1()', this)
				return this.firstName + '-' + this.lastName
			}
         },
		
	6、监视属性
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
	7、计算属性高级: 通过getter/setter实现对属性数据的显示和监视，计算属性存在缓存, 多次读取只执行一次getter计算
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
	8、样式绑定
		1、在应用界面中, 某个(些)元素的样式是变化的 class/style 绑定就是专门用来实现动态样式效果的技术
		2、 class绑定
			xxx是字符串  <p :class="myClass">xxx是字符串</p>
			xxx是对象    <p :class="{classA: hasClassA, classB: hasClassB}">xxx是对象</p>
			xxx是数组	 <p :class="['classA', 'classB']">xxx是数组</p>
		3、 style绑定
		  <p :style="{color:activeColor, fontSize}">style绑定 ，其中 activeColor/fontSize 是data属性</p>
	9、条件渲染
		1、条件渲染指令
		   <p v-if="ok">表白成功</p>
		   <p v-else>表白失败</p>
		   <p v-show="ok">求婚成功</p>
		   <p v-show="!ok">求婚失败</p>
		2、比较 
			v-if 不符合条件的元素不存在， 而 v-show 不符合条件的则是隐藏效果（隐藏好像有两种，一种是会占位，一种不会占位置）
		    如果需要频繁切换 v-show 较好

	10、列表渲染（搜索、排序）
		1、列表显示
			数组: v-for / index
				<ul>
					<li v-for="(p, index) in filterPersons" :key="index">
						{{index}}--{{p.name}}--{{p.age}}
						<button @click="deleteP(index)">删除</button>	// persons.splice(index, 1) // 调用了不是原生数组的splice(), 而是一个变异(重写)方法（1. 调用原生的数组的对应方法 2. 更新界面）
						<button @click="updateP(index, {name:'Cat', age: 16})">更新</button>	// persons[index] = newP  这种vue根本就不知道，要调  persons.splice(index, 1, newP)
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

	11、事件处理
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
	12、表单数据的自动收集： 使用v-model(双向数据绑定)自动收集数据(text/textarea/checkbox/radio/select)
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

	13、 vue 生命周期
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
			beforeDestory(): 做收尾工作, 如: 清除定时器

	14、动画（有用到请看细看 14讲）
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
	15、过滤器
		1、定义过滤器
			Vue.filter(filterName, function(value [,format,arg2,...]){
				// 进行一定的数据处理
				// return moment(value).format(format || 'YYYY-MM-dd');		// 比如时间显示格式的转换，moment.js 依赖对时间的格式化处理，需要再去引入
				return newValue
			})
		2、使用过滤器
			<div>{{myData | filterName}}</div>
			<div>{{myData | filterName(arg)}}</div>

	16、指令
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
			11、 v-cloak : 使用它防止 {{content}} 从后台请求数据需要时间，出现闪现问题, 与css配合: [v-cloak] { display: none } 先隐藏，后面在显示
			12、 注册指令
				1. 注册全局指令
					Vue.directive('my-directive', function(el, binding){	// el: 指令所在的标签对象 binding: 包含指令相关数据的容器对象
						el.innerHTML = binding.value.toupperCase()
					})
				2. 注册局部指令
					directives : {
						'my-directive' : {
							bind: function(el, binding) {
								el.innerHTML = binding.value.toupperCase()
							}
						}
					}
				3. 使用指令
					v-my-directive='xxx'	// xxx好像没什么用

























