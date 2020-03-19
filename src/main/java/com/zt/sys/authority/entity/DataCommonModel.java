package com.zt.sys.authority.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/3/13
 * Time: 16:06
 */
@Data
public class DataCommonModel{
    private String roleId;
    private String resourceId;
    private List<SysColumnsModel> columnscontrollerList;
    private List<SysDataModel> datacontrollerList;
    //创建人
    private String createUser;

    //创建人姓名，非DB字段
    private String createUserName;

    //创建时间
    private Date createTime;
}
