package com.zt.sys.authority.entity;

    import java.time.LocalDate;
    import com.baomidou.mybatisplus.annotation.TableId;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.Date;
    import java.util.List;

    import com.baomidou.mybatisplus.core.metadata.IPage;
    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 资源基础信息表
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysResourceinfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    @TableId("ID")
    private Integer id;
    //资源ID
    @TableField("resourceId")
    private String resourceId;
    //资源名称
    @TableField("resourceName")
    private String permissionName;
    // 父级资源ID
    @TableField("parentId")
    private String parentId;
    //层级
    private String level;
    //资源类型
    @TableField("resourceType")
    private String resourceType;
    //资源路径
    @TableField("resourceUrl")
    private String resourceUrl;
    //按钮类型
    @TableField("btnType")
    private String permissionId;
    //创建人
    @TableField("createUser")
    private String createUser;
    //创建时间
    @TableField("createTime")
    private Date createTime;
    //创建人姓名 非DB字段
    private String createUserName;
    //修改人
    @TableField("updateUser")
    private String updateUser;
    //修改时间
    @TableField("updateTime")
    private Date updateTime;

    //按钮集合
    private List<SysResourceinfo> actions;
    //按钮ID
    private String action;
    //按钮描述
    private String describe;
    //所属角色，非DB字段
    private String roleId;
    //用户ID 非DB字段
    private String userId;
    //已分配角色集合
    private List<SysRoleinfo> roleinfos;
}
