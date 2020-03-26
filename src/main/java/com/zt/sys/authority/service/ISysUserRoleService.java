package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zt.sys.authority.entity.SysUsers;

import java.util.List;

/**
 * <p>
 * 用户基础信息表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 新增用户角色关系-按用户分配
     * @param sysUserRoles
     * @param sessionUser
     */
    void saveUserRole(List<SysUserRole> sysUserRoles, SysUsers sessionUser);

    /**
     * 用户管理——复制用户角色功能，数据保存
     * @param sysUserRole
     */
    void copyUserRoleSave(SysUserRole sysUserRole);

    /**
     * 新增用户角色关系-按角色分配
     * @param sysUserRole
     */
    void saveRoleUser(SysUserRole sysUserRole);
}
