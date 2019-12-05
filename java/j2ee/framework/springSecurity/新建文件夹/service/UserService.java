package la.iok.hzsvn.gateway.http.service;

import com.baomidou.mybatisplus.extension.service.IService;
import la.iok.hzsvn.gateway.http.entity.Authority;
import la.iok.hzsvn.gateway.http.entity.User;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cjr
 * @since 2019-09-18
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户id获取用所有的权限
     * @param userId
     * @return
     */
    Set<Authority> findAuthority(Long userId);

    /**
     * 根据用户名称获取用户
     * @param username
     * @return
     */
    User getByUsername(String username);

}
