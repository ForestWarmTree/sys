package com.zt.sys.authority.entity;

    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.Date;

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
    public class SysDeptinfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private int id;

    //部门编码
    @TableField("deptId")
    private String deptId;

    //部门名称
    @TableField("deptName")
    private String deptName;

    //所属组织
    @TableField("orgId")
    private String orgId;

    //创建人
    @TableField("createUser")
    private String createUser;

    //创建时间
    @TableField("createTime")
    private Date createTime;

    //创建/修改人，非DB字段
    private String createUserName;

    //修改人
    @TableField("updateUser")
    private String updateUser;

    //修改时间
    @TableField("updateTime")
    private Date updateTime;

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


}
