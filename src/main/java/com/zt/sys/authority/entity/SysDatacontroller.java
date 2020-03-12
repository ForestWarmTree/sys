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
    public class SysDatacontroller extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private int id;

    //角色
    @TableField("roleId")
    private String roleId;

    //资源
    @TableField("resourceId")
    private String resourceId;

    //数据权限值
    @TableField("dataValue")
    private String dataValue;

    //数据权限类型
    @TableField("dataType")
    private String dataType;

    //字段名称
    private String name;

    //创建人
    @TableField("createUser")
    private String createUser;
    //创建人姓名,非DB字段
    private String createUserName;

    //创建时间
    @TableField("createTime")
    private Date createTime;

}
