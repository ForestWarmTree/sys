package com.zt.sys.authority.entity;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.Date;
    import java.util.List;

    import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
    import lombok.Data;
    import lombok.EqualsAndHashCode;
    import lombok.experimental.Accessors;

/**
* <p>
    * 用户基础信息表
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
    @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysUserRole extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private int id;
    //用户ID
    @TableField("userId")
    private String userId;
    //角色ID
    @TableField("roleId")
    private String roleId;
//    //有效期开始日期
//    @TableField("startDate")
//    private Date startDate;
//    //有效期结束日期
//    @TableField("endDate")
//    private Date endDate;
    //创建人
    @TableField("createUser")
    private String createUser;
    //创建人姓名
    private String createUserName;
    //创建时间
    @TableField("createTime")
    private Date createTime;
    //角色ID集合
    private List<String> roleIds;
    //用户ID集合
    private List<String> userIds;

    /**
     * 非数据库字段信息,用户b编码，
     * 用于接口：把A用户权限复制给B用户
     */
    private String userIdB;


}
