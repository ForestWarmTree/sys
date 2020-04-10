package com.zt.sys.authority.entity;

    import java.time.LocalDateTime;
    import java.io.Serializable;
    import java.util.Date;

    import io.swagger.models.auth.In;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 操作记录表
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysRolelog extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户ID
    private String userId;
    //用户名称
    private String userName;
    //资源ID
    private String resourceId;
    //资源名称
    private String sourceName;
    //角色ID
    private String roleId;
    //角色名称
    private String roleName;
    //变更类型
    private String updateType;
    //变更类型描述
    private String updateTypeTips;
    //操作时间
    private Date sysTime;
    //操作人
    private String sysUser;
    //操作人姓名
    private String sysUserName;
}
