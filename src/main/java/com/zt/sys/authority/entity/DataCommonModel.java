package com.zt.sys.authority.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/3/13
 * Time: 16:06
 */
@Data
public class DataCommonModel {
    private String roleId;
    private String resourceId;
    private List<SysColumnsModel> columnscontrollerList;
    private List<SysDataModel> datacontrollerList;
}
