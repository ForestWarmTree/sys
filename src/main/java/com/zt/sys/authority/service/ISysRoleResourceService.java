package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.sys.authority.entity.SysRoleinfo;

import java.util.List;

/**
 * <p>
 * 角色资源关系对应表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-21
 */
public interface ISysRoleResourceService extends IService<SysRoleResource> {


    /**
     * 新增角色资源对应关系 - 按角色分配
     * @param sysRoleResource
     */
    void saveRoleResource(SysRoleResource sysRoleResource);

    /**
     * 新增角色资源对应关系 - 按资源分配
     * @param sysRoleResource
     */
    void saveResourceRole(SysRoleResource sysRoleResource);

    /**
     * 复制功能数据保存
     * @param sysRoleinfo
     */
    void copyRoleSave(SysRoleinfo sysRoleinfo);

}
