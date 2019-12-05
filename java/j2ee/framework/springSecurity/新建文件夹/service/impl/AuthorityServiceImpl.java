package la.iok.hzsvn.gateway.http.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import la.iok.hzsvn.gateway.http.dao.AuthorityMapper;
import la.iok.hzsvn.gateway.http.entity.Authority;
import la.iok.hzsvn.gateway.http.service.AuthorityService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author cjr
 * @since 2019-09-18
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

}
