


1、 spring-security
	1、 security 的默认校验
		security.basic.enabled = false		// 禁用 security 的默认校验（即： .httpBasic() 的校验）
	2、 扩展 security 的校验 需要继承 WebSecurityConfigurerAdapter 并且实现 configure(HttpSecurity http) 方法，具体扩展配置看项目代码
		@Autowired
		private SecurityProperties securityProperties;		// security 属性的自定义配置(包括 browser/code/social/oauth 等等)
		http
//			.httpBasic()	// 这个就是 security 的默认检验方式（即：就是一个security的默认登录表单，和 .formLogin() 冲突）
			.formLogin()
			.loginPage("/authentication/require")		 // 自定义登录页面（我们使用一个控制器吧，比较灵活，可以把html和那种数据请求的分开处理）
			.loginProcessingUrl("/authentication/form")	 // 定义登录的表单提交时请求的url，默认是 /login 
			.authorizeRequests()	// ???
			.antMatchers(
				"/authentication/require",				// 需要登录的处理请求
				securityProperties.getBrowser().getLoginPage()	// 默认登录页面
			 ).permitAll()	// 不需要认证的请求(处理所有需要验证的控制器、登录页面等等)
			.anyRequest()			// 任何请求
			.authenticated()		// 都需要认证
			;
	