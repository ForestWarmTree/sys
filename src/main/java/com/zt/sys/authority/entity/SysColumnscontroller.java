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
    * 数据权限控制表
    * </p>
*
* @author jobob
* @since 2020-02-14
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysColumnscontroller extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    @TableField("id")
    private int id;

    //数据名称
    @TableField("dataName")
    private String dataName;

    //字段信息
    @TableField("cloumnName")
    private String cloumnName;

    //角色ID
    @TableField("roleId")
    private String roleId;

    //资源ID
    @TableField("sourceId")
    private String sourceId;

    //创建人
    @TableField("createUser")
    private String createUser;

    //创建人姓名，非DB字段
    private String createUserName;

    //创建时间
    @TableField("createTime")
    private Date createTime;

}
