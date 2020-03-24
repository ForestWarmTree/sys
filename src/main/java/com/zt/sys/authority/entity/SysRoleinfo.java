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
    * 角色基础信息表
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysRoleinfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private int id;
    //角色ID
    @TableField("roleId")
    private String roleId;
    //角色名称
    @TableField("roleName")
    private String roleName;
    //所属组织
    @TableField("orgId")
    private String orgId;
    //创建人
    @TableField("createUser")
    private String createUser;
    //创建时间
    @TableField("createTime")
    private Date createTime;
    //创建人姓名，非DB字段
    private String createUserName;
    //修改人
    @TableField("updateUser")
    private String updateUser;
    //修改时间
    @TableField("updateTime")
    private Date updateTime;
    // 用户拥有角色下的资源基础信息
    private List<SysResourceinfo> permissions;

    //角色ID集合,非DB字段
    private List<String> roleIds;
    //资源ID集合,非DB字段
    private List<String> resourceIds;
    //该角色下已有得全部资源的ID
    private List<String> selectKeys;
    //树结构结果集
    private List<TreeModel> treeData;
    //资源ID，非DB字段
    private String resourceId;
    //资源名称，非DB字段
    private String resourceName;
    //数据、字段权限信息
    private DataCommonModel dataCommonModel;
}
