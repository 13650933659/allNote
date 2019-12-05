package la.iok.hzsvn.gateway.http.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import la.iok.hzsvn.gateway.http.dao.RoleMapper;
import la.iok.hzsvn.gateway.http.entity.Role;
import la.iok.hzsvn.gateway.http.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author cjr
 * @since 2019-09-18
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
