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
    * 
    * </p>
*
* @author jobob
* @since 2020-02-17
*/
    @Data
        @EqualsAndHashCode(callSuper = false)
    @Accessors(chain = true)
    public class SysUsersgroup extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //组号
    @TableField("groupId")
    private String groupId;

    //用户ID
    @TableField("userId")
    private String userId;

    //创建人
    @TableField("createUser")
    private String createUser;

    //创建人姓名，非DB字段
    private String createUserName;

    //创建时间
    @TableField("createTime")
    private Date createTime;

    //用户ID集合
    private List<String> userIds;

}
