package la.iok.hzsvn.gateway.http.security.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 认证服务器安全配置
 */
@Configuration
@EnableResourceServer // 启用资源服务器
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService lewinUserDetailsService;

	/**
	 * 密码加解密工具
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder()	{
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(lewinUserDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
				.loginPage("/authentication/require")
				.loginProcessingUrl("/authentication/form")
				.and().authorizeRequests()
				.antMatchers("/authentication/require",
						"/authentication/form",
						"/**/*.js",
						"/**/*.css",
						"/**/*.jpg",
						"/**/*.png"
				)
				.permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().disable();
	}

}
