
可以参考 https://gitee.com/hong_ej/SpringSecurity/blob/master/pom.xml
看完 7-2


视频摘要(springSecurity 就直接从4开始即可，前面都是mvc的基础)
	1、 课程大纲概要 // 1-1
	2、 开发环境的搭建 (imooc-security/imooc-security-core/imooc-security-demo/imooc-security-app/imooc-security-browser 五个项目的搭建)
		2-1 至 2-3
	3、 3-1 至 3-12	// mvc的开发
	
	4、  第4和第5
		1、认证方式
			1、 用户名 + 密码
				4-2 至 4-9
			2、 手机号 + 短信验证码
				4-10 至 4-13
			3、 社交网账号登录（QQ或者微信等等）
				5-1 至 5-8
		2、认证之后的 session(基于cookies) 管理
			1、单机版的管理（直接放在服务器即可）
				5-9
			2、集群版的管理（借用spring-seesion模块然后借用redis存储）
				5-10
			3、退出登录功能的实现
				5-11



1、 spring-security 基础知识
	1、 security 的默认校验
		security.basic.enabled = false		// 禁用 security 的默认校验（即： .httpBasic() 的校验）
	2、 扩展 security 的校验 需要继承 WebSecurityConfigurerAdapter 并且实现 configure(HttpSecurity http) 方法，具体扩展配置看项目代码
		@Autowired
		private SecurityProperties securityProperties;		// security 属性的自定义配置(包括 browser/code/social/oauth 等等)
		http
			//.httpBasic()	// 这个就是 security 的默认检验方式（即：就是一个security的默认登录表单，和 .formLogin() 冲突）
			.formLogin()
			.loginPage("/authentication/require")		 // 自定义登录页面（我们使用一个控制器吧，比较灵活，可以把html和那种数据请求的分开处理）
			.loginProcessingUrl("/authentication/form")	 // 定义登录的表单提交时请求的url，默认是 /login 
			.successHandler(imoocAuthenticationSuccessHandler)								// 表单登录成功的处理器
			.failureHandler(imoocAuthenticationFailureHandler);								// 表单登录失败的处理器
			.authorizeRequests()	// 资源
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
			private RequestCache requestCache = new HttpSessionRequestCache();	// 请求缓存（springsecurity 跳转到这个 /authentication/require 会先把先前的http请求放入缓存）
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
					if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {	// 如果是pc端的请求，直接跳转到登录页面
						redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
					}
				}
				return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");	// 如果是api的请求，提示那边先去登录认证
			}

		}
	4、 自定义用户认证逻辑(默认的认证是 user=启动时生成的一个随机字符串)
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
	5、 自定义登录验证后的处理器
		1、 成功的处理器
			@Component("imoocAuthenticationSuccessHandler")
			public class ImoocAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {		// SavedRequest 是保存了之前的请求，所以他才会到之前请求页面
				@Autowired
				private ObjectMapper objectMapper;

				@Autowired
				private SecurityProperties securityProperties;

				/**
				 * 验证成功进入处理
				 * @param authentication		认证成功的对象
				 */
				@Override
				public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {  // 登录类型如果是发送的ajax请求，则返回json（例如：app请求，或者浏览器异步的登录请求）
						response.setContentType("application/json;charset=UTF-8");
						response.getWriter().write(objectMapper.writeValueAsString(authentication));
					} else {		// 默认的处理是跳转到刚刚请求的那个url（有一个种就是他就服务这个 认证的url【 /authentication/require 这种好像不太可能】 或者 直接访问登录页面,那么跳转到哪里呢）
						super.onAuthenticationSuccess(request, response, authentication);
					}
				}
			}
		2、 失败的处理器
			@Component("imoocAuthenctiationFailureHandler")
			public class ImoocAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
				@Autowired
				private ObjectMapper objectMapper;
				
				@Autowired
				private SecurityProperties securityProperties;


				/**
				 * 失败的处理器
				 * @param exception		认证失败的异常
				 */
				@Override
				public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {		// 登录类型如果是发送的ajax请求，则返回json（例如：app请求，或者浏览器异步的登录请求）
						response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
						response.setContentType("application/json;charset=UTF-8");
						response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
					}else{
						super.onAuthenticationFailure(request, response, exception);
					}
				}
			}

2、 spring-security 的拦截器链
	1、 SecurityContextPersistenceFilter		// 这是第一个过滤器
		doFilter
			SecurityContext contextBeforeChainExecution = repo.loadContext(holder);		// 取 session 获取 SecurityContext key=SPRING_SECURITY_CONTEXT
			SecurityContextHolder.setContext(contextBeforeChainExecution);				// 放入 SecurityContextHolder (当前线程的一个全局变量)，是否有 SecurityContext 后续的过滤器自行处理
			chain.doFilter(holder.getRequest(), holder.getResponse());					// "调用其他过滤器"
			repo.saveContext(contextAfterChainExecution, holder.getRequest(),holder.getResponse());		// 当前线程请求执行完毕后，把后面认证好了的 SecurityContext 放入 session 以备此session的再次请求
	2、 UsernamePasswordAuthenticationFilter	// 登录请求验证(即登录表单提交时的请求/authentication/form)
		

	3、 BasicAuthenticationFilter
	3、 SocialAuthenticationFilter			// social 的过滤器
	4、 RememberMeAuthenticationFilter		// 记住我的过滤器
	4、 ...
	5、 ExceptionTranslationFilter		// 专门开捕获最后一个过滤器抛出来的异常，并且处理，比如 用户没有经过认证 并且我们配置表单登录，他就好跳转到 /authentication/require 让用户去认证
	6、 FilterSecurityInterceptor		// 最后的一个过滤器
	7、 我们的 rest 服务


3、 spring-security 认证方式
	1、 表单登录认证 (内置实现好了的)
		1、原理
			AbstractAuthenticationProcessingFilter.doFilter
				1、 attemptAuthentication // UsernamePasswordAuthenticationFilter 实现 使用 username 和 password 构建一个 authRequest=UsernamePasswordAuthenticationToken->Authentication(用户的登录认证信息) 类，到这还未验证
					1、 getAuthenticationManager().authenticate(authRequest);	// ProviderManager 实现
						for (AuthenticationProvider provider : getProviders()) {}	// 遍历所有的 AuthenticationProvider(其中一个实现 AbstractUserDetailsAuthenticationProvider)
							authenticate			// AbstractUserDetailsAuthenticationProvider 实现
								retrieveUser		// 这个方法是抽象方法，实现是 DaoAuthenticationProvider.retrieveUser
									loadedUser = this.getUserDetailsService().loadUserByUsername(username);		// 这个就是调用 UserDetailsService 《重要》
								preAuthenticationChecks.check(user);	// 获取到 UserDetails 对他进行预检查 (是否过期、是否可用、是否锁定)
								additionalAuthenticationChecks(user, authentication);	// 附加的检查，就是调用加密解密器验证密码
								postAuthenticationChecks.check(user);			// 最后的检查，认证凭证是否过期
								return createSuccessAuthentication(principalToReturn, authentication, user);	// 所有认证成功了，就创建认证成功的 Authentication
				2、 successfulAuthentication()		// 认证成功
					SecurityContextHolder.getContext().setAuthentication(authResult);
						SecurityContextHolder // ThreadLocal 的一个封装（即：线程级的全局变量，每个线程一个）
						SecurityContext			// 里面是存放着 Authentication
						1、 取得 Authentication
							1、 然后我们在此线程的任何地方都可以取得 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
							2、 借助 mvc 获取
								@GetMapping("/authentication")
								public Object getCurrentUser(Authentication authentication) {
									return authentication;
								}
								或者
								@GetMapping("/authentication")
								public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
									return user;
								}
					rememberMeServices.loginSuccess(request, response, authResult);		// AbstractRememberMeServices 实现记住我的功能，这个如果不是浏览器应该就有点浪费了，可能里面有判断的吧，可能开销也不大
						PersistentTokenBasedRememberMeServices.onLoginSuccess()		// 创建 PersistentRememberMeToken 放入数据库 和 客户端的cookie	// 这样下次就不需要重新登录了(完成了记住我的功能)
							PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, generateSeriesData(), generateTokenData(), new Date());
							tokenRepository.createNewToken(persistentToken);		// 根据用户名称创建token(即：一个字符串)，存入数据库中
							addCookie(persistentToken, request, response);			// 往浏览器的cookie写入
					successHandler.onAuthenticationSuccess(request, response, authResult);		// 这个就是认证成功回调的处理器（我们自己定义的 ImoocAuthenticationSuccessHandler ）
		2、 记住我功能的认证（基于1、认证原理 的成功 ，创建的 token 然后写入cookie）
			RememberMeAuthenticationFilter
				doFilter
				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					Authentication rememberMeAuth = rememberMeServices.autoLogin(request, response);	// 自动登录
						user = processAutoLoginCookie(cookieTokens, request, response);		// PersistentTokenBasedRememberMeServices 实现
							PersistentRememberMeToken token = tokenRepository.getTokenForSeries(presentedSeries);	// 去数据库拿 Token 
							// 经过一系列的校验，如果不对则抛异常
							return getUserDetailsService().loadUserByUsername(token.getUsername());		
							// 最后调用我们的 UserDetailsService 《重要》 ,这种他拿到  UserDetails 就不是比对密码了，而且比对 token，认证成功 返回 RememberMeAuthenticationToken
					// 下面三句就是人证成功之后执行了，和 1、认证原理 人证成功一样的处理，也要把  Authentication 放入 SecurityContext 
					rememberMeAuth = authenticationManager.authenticate(rememberMeAuth);
					// Store to SecurityContextHolder
					SecurityContextHolder.getContext().setAuthentication(rememberMeAuth);
					onSuccessfulAuthentication(request, response, rememberMeAuth);
				}
	2、 自定义 手机短信登录认证（看代码既可，核心的流程还是和 表单登录认证 一样）
	3、 社交网账号登录（QQ或者微信等等）
		1、 qq
			1、 qq互联的传的参数
				access_token
				oath_consumer_key		// 申请qq登录成功后，分配给应用的 appid
				openid					// 最终用户id
			2、 qq授权登录认证
				1、 用户在我们系统点击 QQ登录 按钮
				2、 SocialAuthenticationFilter 拦截我们的 qq登录的请求 /auth/provideId 这是默认的
					OAuth2AuthenticationService.getAuthToken
						String code = request.getParameter("code");		// 这是在请求参数拿授权码，如果没有则是用户点击 qq登录操作，如有则是用户在qq那边授权成功的回调
							code==null	// 直接跳转到qq那边去授权
							code!=null
								AccessGrant accessGrant = getConnectionFactory().getOAuthOperations().exchangeForAccess(code, returnToUrl, null);	// 利用这个授权码创建请求参数，以备下面的再去次请求qq得到 SocialAuthenticationToken
					ProvideManager	// 来到我们属性的类，他会选到 SocialAuthenticationProvider
						SocialAuthenticationProvider
							authenticate(Authentication authentication)
								String userId = toUserId(connection);		// 去 UserConnection 表查 工具 providerId和providerUserId 查 userId
									List<String> userIds = usersConnectionRepository.findUserIdsWithConnection(connection);
										List<String> localUserIds = jdbcTemplate.queryForList("select userId from " + tablePrefix + "UserConnection where providerId = ? and providerUserId = ?", String.class, key.getProviderId(), key.getProviderUserId());
										if (localUserIds.size() == 0 && connectionSignUp != null) {
											String newUserId = connectionSignUp.execute(connection);		// 利用社交找号信息，偷偷摸摸的给用在我们系统注册一个账号 connectionSignUp.execute(connection) 自己去写
											if (newUserId != null){
												createConnectionRepository(newUserId).addConnection(connection);
												return Arrays.asList(newUserId);
											}
										}
								UserDetails userDetails = userDetailsService.loadUserByUserId(userId);		// 有来到了获取用户信息的接口
						

4、 spring-security 认证之后的 authentication 的保存
	1、 seesion
		1、 spring.session.timeout=10	// 配置10秒，默认30分钟，最小1分钟
		2、 集群 session 的处理




5、 spring-security 扩展知识点
	1、 基于 OAuth2 协议的认证
		1、 授权码模式
				imooc.security.oauth2.client.clientId = imooc
				imooc.security.oauth2.client.clientSecret = imoocsecret
			1、 最终用户登录
			2、 ??? ，会请求我们的后台 [get] localhost/oauth/authorize?respone_type=code&client_id=imooc&redirect_uri=http://example.com&scope=all
			3、 这时最终用户被我转到后台填写 用户名+密码 ==> 然后点击 确认授权
			4、 最终用确认之后，后台给一个 授权码 abc （注意这里在 UserDetails 需要给一个 ROLE_USER 角色才可以访问）
			5、 ??? 此时拿着授权码请求后台 [post] localhost/oauth/token
				1、 请求头 Authorization:Basic [clientId+clientSecret 的base64加密]
				2、 带的参数
					grant_type=authorization_code
					client_id=imooc
					code=abc
					redirect_uri=http://example.com
					scope=all
				3、 返回值
					access_token=传说中的 token
					token_type=bearer
					refresh_token=刷新 access_token 的 token
					expires_in=过期的秒数
					scope=all
		
		2、 密码模式
			1、 最终用户 用户名+密码 点击登录 到我们的 ???
			2、 ??? [post] localhost/oauth/token
				1、 请求头 Authorization:Basic [clientId+clientSecret 的base64加密]
				2、 带的参数
					grant_type=password
					username=jojo
					password=123456
					scope=all
				3、 返回值
					access_token=传说中的 token		// 同一个用反复(同种/不同 授权模式)请求时 access_token 一样
					token_type=bearer
					refresh_token=刷新 access_token 的 token
					expires_in=过期的秒数
					scope=all
		5、 自定义的认证模式
			grant_type=password		// 这个就不需要带了
	2、 通过上面的授权模式 得到 access_token
		获取然后就可以请求 后台服务了
			1、 但是请求头 Authorization:bearer access_token


		解释
			我们的app或者前端(或者其他第三方) = ???
			http://example.com = 我们的app或者前端(获取其他第三方)
			access_token 默认是存在内存的
				1、 优化
					1、 存 redis 
					2、 不需要 redis 直接使用 jwt 规范
						1、 自包含 + 密签名 + 扩展
						2、 去 官网 https://www.jsonwebtoken.io/ 可以解析我们的 jwt 但是好像不需要秘钥？？？






