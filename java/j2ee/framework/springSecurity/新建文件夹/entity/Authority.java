package la.iok.hzsvn.gateway.http.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author cjr
 * @since 2019-09-18
 */
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 备注
     */
    private String remark;

    /**
     * 对应的url
     */
    private String url;

    public Authority() {

    }

    public Authority(String name) {
        this.name = name;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Authority{" +
        "id=" + id +
        ", name=" + name +
        ", code=" + code +
        ", remark=" + remark +
        ", url=" + url +
        "}";
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
