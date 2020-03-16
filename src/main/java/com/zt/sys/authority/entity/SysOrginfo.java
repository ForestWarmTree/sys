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
    * 组织基础信息表
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysOrginfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private int id;
    //组织编码
    @TableField("orgId")
    private String orgId;
    //组织名称
    @TableField("orgName")
    private String orgName;
    //父级组织编码
    @TableField("parentId")
    private String parentId;
    //父级组织编码
    @TableField("parendName")
    private String parendName;
    // 组织类型
    @TableField("orgType")
    private String orgType;
    //冗余字段
    private String ry1;
    //冗余字段
    private String ry2;
    //冗余字段
    private String ry3;
    //冗余字段
    private String ry4;
    //冗余字段
    private String ry5;
    //冗余字段
    private String ry6;
    //冗余字段
    private String ry7;
    //冗余字段
    private String ry8;
    //冗余字段
    private String ry9;
    //冗余字段
    private String ry10;
    //创建人
    @TableField("createUser")
    private String createUser;
    //创建人姓名，非DB字段
    private String createUserName;
    //创建时间
    @TableField("createTime")
    private Date createTime;
    //修改人
    @TableField("updateUser")
    private String updateUser;
    //修改时间
    @TableField("updateTime")
    private Date updateTime;

    //当前登陆人ID，非DB字段
    private String userId;
    //组织ID集合
    private List<String> orgIds;
}
