package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.DataCommonModel;
import com.zt.sys.authority.entity.SysColumnsModel;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.sys.authority.entity.SysRoleinfo;

import java.util.List;

/**
 * <p>
 * 数据权限控制表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-14
 */
public interface ISysColumnscontrollerService extends IService<SysColumnsModel> {
    /**
     * 保存
     * @param dataCommonModel
     */
    void saveColumn(DataCommonModel dataCommonModel);

    /**
     * 根据角色编码与资源编码查询对应的字段权限信息
     * @param sysRoleinfo
     * @return
     */
    List<SysColumnsModel> selectByRoleIdAndResourceId(SysRoleinfo sysRoleinfo);
}
