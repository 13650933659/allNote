


1、 spring-security
	1、 security 的默认校验
		security.basic.enabled = false		// 禁用 security 的默认校验
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
	3、 自定义所有需要验证的处理器
		@RestController
		public class BrowserSecurityController {
			private RequestCache requestCache = new HttpSessionRequestCache();	// 请求缓存
			private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

			@Autowired
			private SecurityProperties securityProperties;	// 同上


			/**
			 * 当需要身份认证时，跳转到这里
			 */
			@RequestMapping("/authentication/require")
			@ResponseStatus(code = HttpStatus.UNAUTHORIZED) // 401
			public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
					throws IOException {
				SavedRequest savedRequest = requestCache.getRequest(request, response);
				if (savedRequest != null) {
					String targetUrl = savedRequest.getRedirectUrl();
					logger.info("引发跳转的请求是:" + targetUrl);
					if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
						redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
					}
				}
				return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
			}

		}
	3、 自定义用户认证逻辑(默认的认证是 user=启动时生成的一个随机字符串)
		1、 实现 UserDetailService
			@Component
			public class MyUserDetailsService implements UserDetailsService {
				@Autowired
				private PasswordEncoder passwordEncoder;

				@Override
				public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
					logger.info("表单登录用户名:" + username);
					String password = passwordEncoder.encode("123456"); 	// 这个密码是我们从数据库查出来，现在这里就直接模拟一下
					// 后面四个boolean自己写逻辑，有一个为false则就校验失败（我在这里就全部写true因为我没有这个业务）
					return new User(username, password,
								true, true, true, true,
							AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
				}
			}
		2、 密码加密处理，只要实现了 PasswordEncoder 的即可，这里使用 BCryptPasswordEncoder 加解密工具(就算同一个文本，生成结果都不一样，但是否相同他自己知道的)
			@Bean
			public PasswordEncoder passwordEncoder() {
				return new BCryptPasswordEncoder();
			}


2、

	6、  springSecurity 的拦截器链
		1、 SecurityContextPersistenceFilter	
			请求经过他时先去 session拿 SecurityContext(里面包含了用户认证信息)，如果有则取出放入当前请求的线程，没有则启用认证
			认证成功之后会把 SecurityContext 放入session
		2、 UsernamePasswordAuthenticationFilter	// 登录请求验证(即登录表单提交时的请求/authentication/form)
			

		3、 BasicAuthenticationFilter
		3、 SocialAuthenticationFilter			// social 的过滤器
		4、 RememberMeAuthenticationFilter		// 记住我的过滤器
		4、 ...
		5、 ExceptionTranslationFilter		// 专门开捕获最后一个过滤器抛出来的异常
		6、 FilterSecurityInterceptor		// 最后的一个过滤器
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