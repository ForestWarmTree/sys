package com.zt.sys.authority.service;

import com.zt.sys.authority.entity.SysUserRoleSource;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色资源关系关联表 服务类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface ISysUserRoleSourceService extends IService<SysUserRoleSource> {

    /**
     * 新增用户角色资源关系
     * @param sysUserRoleSource
     */
    void saveUserRoleResource(SysUserRoleSource sysUserRoleSource);
}
