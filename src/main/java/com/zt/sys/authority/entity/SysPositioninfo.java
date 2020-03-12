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
    public class SysPositioninfo extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private int id;
    //岗位编码
    @TableField("positionNo")
    private String positionNo;
    //岗位名称
    @TableField("positionName")
    private String positionName;
    //创建人
    @TableField("createUser")
    private String createUser;
    //创建/修改人姓名
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
