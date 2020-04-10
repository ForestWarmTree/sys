package com.zt.sys.authority.entity;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.Date;
    import java.util.List;

    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 用户角色资源关系关联表
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysUserRoleSource extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    //用户ID
    @TableField("userId")
    private String userId;
    //角色ID
    @TableField("roleId")
    private String roleId;
    //资源ID
    @TableField("resourceId")
    private String resourceId;
    //创建人
    @TableField("createUser")
    private String createUser;
    //创建时间
    @TableField("createTime")
    private Date createTime;
    //角色ID集合
    private List<String> roleIds;
    //资源ID集合
    private List<String> resourceIds;


}
