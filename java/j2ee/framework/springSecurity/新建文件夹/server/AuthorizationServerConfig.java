package la.iok.hzsvn.gateway.http.security.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		// 给那些 第三方授权
		clients.inMemory()
				.withClient("lewin")
				.secret("lewin")
//				.accessTokenValiditySeconds(7200)	// 令牌的有效期
//				.authorizedGrantTypes("authorization_code", "refresh_token")
				.authorizedGrantTypes("password", "refresh_token")		// 用户密码模式 刷新token
				.scopes("all")
				;
	}


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// jwt 存储 jwtTokenStore() + jwtAccessTokenConverter()
		endpoints.tokenStore(jwtTokenStore()).accessTokenConverter(jwtAccessTokenConverter());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		// 所有资源都需要认证之后 isAuthenticated() 授权表达式
		security.tokenKeyAccess("isAuthenticated()");
	}
	
	@Bean
	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("lewin");
        return converter;
	}

}
