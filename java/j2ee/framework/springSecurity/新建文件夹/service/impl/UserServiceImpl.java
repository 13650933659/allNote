package la.iok.hzsvn.gateway.http.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import la.iok.hzsvn.gateway.http.dao.UserMapper;
import la.iok.hzsvn.gateway.http.entity.Authority;
import la.iok.hzsvn.gateway.http.entity.User;
import la.iok.hzsvn.gateway.http.service.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cjr
 * @since 2019-09-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public Set<Authority> findAuthority(Long userId) {
        // 需要根据我们系统的业务去查用户的所有权限
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("ROLE_USER"));        // 使用 auth2 用户必须有一个 ROLE_USER 角色
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username", username));
    }

}
