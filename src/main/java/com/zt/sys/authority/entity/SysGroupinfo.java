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
public class SysGroupinfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    // 组号
    @TableField("groupId")
    private String groupId;

    // 组名
    @TableField("groupName")
    private String groupName;

    //所属组织
    @TableField("orgId")
    private String orgId;

    //所属部门
    @TableField("deptId")
    private String deptId;

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

}
