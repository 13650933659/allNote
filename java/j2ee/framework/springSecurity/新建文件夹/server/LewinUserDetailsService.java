package la.iok.hzsvn.gateway.http.security.server;

import la.iok.hzsvn.gateway.http.entity.Authority;
import la.iok.hzsvn.gateway.http.entity.User;
import la.iok.hzsvn.gateway.http.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 获取用信息接口（注意：使用 auth2 用户必须有一个 ROLE_USER 角色）
 */
@Component
public class LewinUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;



	/**
	 * 获取用户信息
	 * @param username		用户名称
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);
		if (user != null) {
			Set<Authority> authoritys = userService.findAuthority(user.getId());
			return new User(username, passwordEncoder.encode(user.getPassword()), authoritys);
		}

		throw new UsernameNotFoundException(String.format("用户=%s，没找到", username));
	}

}
