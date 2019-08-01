

看完 5-8




1、所以登陆验证拦截器AuthenticationProcessingFilter要讲
2、资源管理拦截器AbstractSecurityInterceptor




但拦截器里面的实现需要一些组件来实现，所以就有了 AuthenticationManager 、accessDecisionManager等组件来支撑。

单点登录是？


1、 图片验证码 拦截器
2、 记住我功能
3、 实现短信验证码登录
	1、 spring的依赖搜索





1、 知识点
	1、 获取分页参数 size\page\sort @PageableDefault(page = 2, size = 17, sort = "username,asc") Pageable pageable	// 依赖springdata
	2、 @PathVariable					// springmvc 的一个rest风格的 {id:\d+} 参数的值，看他还可以使用 正则过约束参数
	3、 jsonpath 语法 去github看文档	// 这是在测试springmvc对返回结果的断言
	4、 swagger2						// html文档生成框架
	5、 wiremock						// 伪造服务器，和前端并行开发
	6、 @JsonView(User.UserDetailView.class) //  有标注 @JsonView(UserDetailView.class) 的属性才会显示给前台(选择性的给用户返回属性)

	6、  springSecurity 的拦截器链
		1、 SecurityContextPersistenceFilter	
			请求经过他时先去 session拿 SecurityContext(里面包含了用户认证信息)，如果有则取出放入当前请求的线程，没有则启用认证
			认证成功之后会把 SecurityContext 放入session
		2、 UsernamePasswordAuthenticationFilter	// 登录请求验证(即登录表单提交时的请求/authentication/form)
			

		3、 BasicAuthenticationFilter
		3、 SocialAuthenticationFilter			// social 的过滤器
		4、 RememberMeAuthenticationFilter		// 记住我的过滤器
		4、 ...
		5、 ExceptionTranslationFilter
		6、 FilterSecuityInterceptor		// 最后的一个过滤器
		7、 我们的 rest 服务
	

		1、原理
			AbstractAuthenticationProcessingFilter.doFilter
				1、 UsernamePasswordAuthenticationFilter.attemptAuthentication // 使用 username 和 password 构建一个 authRequest=UsernamePasswordAuthenticationToken->Authentication(用户的登录认证信息) 类，到这还未验证
					1、 getAuthenticationManager().authenticate(authRequest);	// ProviderManager 实现
						for (AuthenticationProvider provider : getProviders()) {}	// 遍历所有的 AuthenticationProvider(其中一个实现 AbstractUserDetailsAuthenticationProvider)
							authenticate
								retrieveUser		// 这个方法是抽象方法，实现是 DaoAuthenticationProvider.retrieveUser
									loadedUser = this.getUserDetailsService().loadUserByUsername(username);		// 这个就是调用 UserDetailsService 《重要》
								preAuthenticationChecks.check(user);	// 获取到 UserDetails 对他进行预检查 (是否过期、是否可用、是否锁定)
								additionalAuthenticationChecks(user, authentication);	// 附加的检查，就是调用加密解密器验证密码
								postAuthenticationChecks.check(user);			// 最后的检查，认证凭证是否过期
								return createSuccessAuthentication(principalToReturn, authentication, user);	// 所有认证成功了，就创建认证成功的 Authentication
					2、 successfulAuthentication()
						AbstractRememberMeServices.loginSuccess
							PersistentTokenBasedRememberMeServices.onLoginSuccess()		// 创建 PersistentRememberMeToken 放入数据库 和 客户端的cookie	// 这样下次就不需要重新登录了(完成了记住我的功能)
								PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(
								username, generateSeriesData(), generateTokenData(), new Date());
								tokenRepository.createNewToken(persistentToken);
								addCookie(persistentToken, request, response);





