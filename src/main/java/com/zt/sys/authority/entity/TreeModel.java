package com.zt.sys.authority.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/3/13
 * Time: 9:32
 */
@Data
public class TreeModel extends BaseModel {

    //树节点名称
    private String title;
    //树节点编码
    private String key;
    //层级
    private String level;
    //资源类型
    private String resourceType;
    //子节点集合
    private List<TreeModel> children;
}
