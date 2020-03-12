package com.zt.sys.authority.entity;

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
    * 角色资源关系对应表
    * </p>
*
* @author jobob
* @since 2020-02-21
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private int id;
    //角色ID
    @TableField("roleId")
    private String roleId;
    //资源ID
    @TableField("resourceId")
    private String resourceId;
    //创建人
    @TableField("createUser")
    private String createUser;
    //创建人姓名
    private String createUserName;
    //创建时间
    @TableField("createTime")
    private Date createTime;
    //资源编码集合
    List<String> resourceIds;
    //角色编码集合
    List<String> roleIds;

}
