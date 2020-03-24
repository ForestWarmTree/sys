package com.zt.sys.authority.entity;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.Date;
    import java.util.List;
    import java.util.Map;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysUsers extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户授权密钥
    private String token;

    // 主键
    private int id;
    // 用户ID
    @TableField("userId")
    private String userId;
    // 用户名
    private String username;
    // 密码
    private String password;
    // 有效期
    private Date indate;
    //可用状态
    private int type;
    //是否超级管理员
    @TableField("isSupper")
    private String isSupper;
    // 创建人
    @TableField("createUser")
    private String createUser;
    // 创建时间
    @TableField("createTime")
    private Date createTime;
    // 用户基础信息
    private SysUserinfo sysUserinfo;
    //用户组织基础信息
    private SysOrginfo sysOrginfo;
    //用户部门基础信息
    private SysDeptinfo sysDeptinfo;
    // 用户角色信息集合
    private List<SysRoleinfo> roleinfos;
    // 用户权限集合
    private List<SysUserRoleSource> sysUserRoleSourceList;
    // 用户资源基础信息
    private List<SysResourceinfo> permissions;

    private Map<String, Object> role;

    //当前登陆人姓名 非DB字段
    private String name;
    //资源ID 非DB字段
    private String resourceId;
    // 资源名称 非DB字段
    private String permissionName;
}
