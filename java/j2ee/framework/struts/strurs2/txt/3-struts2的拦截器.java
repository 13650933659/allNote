

拦截器的工作原理：他的本质是过滤器，工作原理一样

1、自定义拦截器和拦截器栈（很少自定义拦截器）

2、token拦截器的防止刷新（还有其他三防刷新的方法：两个A/重定向到jsp或者A/post提交让浏览器提示）
	1、token拦截器的原理：
		当用户第一次访问添加jsp页时拦截器就会在session加入随机token，并加入到用了<s:token/>的表单加入一个隐藏域，
		提交时又经过此拦截器则判断此隐藏域的值是否等于session的值，匹配到则给提交并且马上清掉此session当用户按刷新时，
		这时已经没有session和隐藏域的值匹配了，则放回invalid.token,所以我们还要在此A加一个:
		<result name="invalid.token">error.jsp</result>为了友好

3、DefaultTypeConverter数据类型转化
	1、但是有时他转化出错，我们可以自己继承他然后再用写转化方法，再加配置文件（XXAction-conversion.propertyes/xwork-conversion.propertyes）
	2、还可以用request/session等可以得到jsp传过来的域对象

4、文件上下传听说也是用拦截器实现的。可能要用到servletConfigInterptor第三个拦截器来得到web元素，实现文件上传下载