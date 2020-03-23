package com.zt.sys.authority.entity;

    import java.time.LocalDate;
    import com.baomidou.mybatisplus.annotation.TableField;
    import java.io.Serializable;
    import java.util.Date;

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
    public class SysUserinfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    // 用户ID
    @TableField("userId")
    private String userId;

    // 工号
    private String userCardNo;

    // 姓名
    private String name;

    // 岗位
    private String position;
    // 岗位名称，非DB字段
    private String positionName;

    // 所属组织
    private String orgId;
    //所属组织名称，非DB字段
    private String orgName;

    //所属部门
    private String deptId;
    //所属部门名称，非DB字段
    private String deptName;

    // 籍贯
    private String nativePlace;

    // 工资卡开户行
    private String bank;

    // 工资卡账号
    private String cardNums;

    // 入职日期
    private Date entryTime;

    // 就职状态
    private Integer assumptionFlag;

    // 身份证号
    private String identityCard;

    // 联系电话
    private String phone;

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

    //组ID，非DB字段
    private String groupId;
    //角色ID，非DB字段
    private String roleId;
}
